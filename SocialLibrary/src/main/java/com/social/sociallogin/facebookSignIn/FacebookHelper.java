package com.social.sociallogin.facebookSignIn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by pulkitmital on 02/12/16.
 * <p>
 * This class will initialize facebook login and handle
 * other sdk functions.
 */

public class FacebookHelper {

    private FacebookResponse mListener;
    private String mFieldString;
    private CallbackManager mCallBackManager;

    /**
     * Public constructor.
     *
     * @param mFieldString name of the fields required. (e.g. id,name,email,gender,birthday,picture,cover)
     *                     See {@link 'https://developers.facebook.com/docs/graph-api/reference/user'} for more info on user node.
     * @param context      instance of the caller activity
     */

    public FacebookHelper(@NonNull String mFieldString, @NonNull Activity context) {

        FacebookSdk.sdkInitialize(context.getApplicationContext());

        //noinspection ConstantConditions
        if (mFieldString == null)
            throw new IllegalArgumentException("field string cannot be null.");

        this.mFieldString = mFieldString;
        this.mCallBackManager = CallbackManager.Factory.create();


    }

    /**
     * Get user facebook profile.
     *
     * @param loginResult login result with user credentials.
     */
    private void getUserProfile(LoginResult loginResult) {
        // App code
        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

                        try {
                            mListener.onFbProfileReceived(parseResponse(object));
                        } catch (Exception e) {
                            e.printStackTrace();

                            mListener.onFbSignInFail();
                        }
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", mFieldString);
        request.setParameters(parameters);
        request.executeAsync();
    }

    /**
     * Get the {@link CallbackManager} for managing callbacks.
     *
     * @return {@link CallbackManager}
     */
    @NonNull
    @CheckResult
    public CallbackManager getCallbackManager() {
        return mCallBackManager;
    }

    /**
     * Parse the response received into {@link FacebookUser} object.
     *
     * @param object response received.
     * @return {@link FacebookUser} with required fields.
     * @throws JSONException
     */
    private FacebookUser parseResponse(JSONObject object) throws JSONException {
        FacebookUser user = new FacebookUser();
        user.setResponse(object);

        if (object.has("id"))
            user.setFacebookId(object.getString("id"));

        if (object.has("email"))
            user.setEmail(object.getString("email"));

        if (object.has("name"))
            user.setName(object.getString("name"));

        if (object.has("gender"))
            user.setGender(object.getString("gender"));

        if (object.has("about"))
            user.setAbout(object.getString("about"));

        if (object.has("bio"))
            user.setBio(object.getString("bio"));

        if (object.has("cover"))
            user.setCoverPicUrl(object.getJSONObject("cover").getString("source"));

        if (object.has("picture"))
            user.setProfilePic(object.getJSONObject("picture").getJSONObject("data").getString("url"));
        return user;
    }

    /**
     * Perform facebook sign in.<p>
     * NOTE: If you are signing from the fragment than you should call {@link #performSignIn(Fragment, FacebookResponse)}.<p>
     * This method should generally call when user clicks on "Sign in with Facebook" button.
     *
     * @param mListener {@link FacebookResponse} listener to get call back response.
     * @param activity  instance of the caller activity.
     */
    public void performSignIn(Activity activity, @NonNull FacebookResponse mListener) {


        if (mListener == null) {
            throw new IllegalArgumentException("FacebookResponse Listener cannot be null");
        }
        this.mListener = mListener;

        registerCallback();
        LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList("public_profile", "user_friends", "email"));
    }


    /**
     * Perform facebook login. This method should be called when you are signing in from
     * fragment.<p>
     * This method should generally call when user clicks on "Sign in with Facebook" button.
     *
     * @param mListener {@link FacebookResponse} listener to get call back response.
     * @param fragment  caller fragment.
     */
    public void performSignIn(Fragment fragment, @NonNull FacebookResponse mListener) {

        if (mListener == null) {
            throw new IllegalArgumentException("FacebookResponse Listener cannot be null");
        }
        this.mListener = mListener;

        registerCallback();
        LoginManager.getInstance().logInWithReadPermissions(fragment, Arrays.asList("public_profile", "user_friends", "email"));
    }


    /**
     * This method handles onActivityResult callbacks from fragment or activity.
     *
     * @param requestCode request code received.
     * @param resultCode  result code received.
     * @param data        Data intent.
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallBackManager.onActivityResult(requestCode, resultCode, data);
    }


    public void performSignOut() {
        LoginManager.getInstance().logOut();

    }


    private void registerCallback() {

        //get access token
        FacebookCallback<LoginResult> mCallBack = new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                mListener.onFbSignInSuccess();

                //get the user profile
                getUserProfile(loginResult);
            }

            @Override
            public void onCancel() {
                mListener.onFbSignInFail();
            }

            @Override
            public void onError(FacebookException e) {
                mListener.onFbSignInFail();
            }
        };
        LoginManager.getInstance().registerCallback(mCallBackManager, mCallBack);
    }
}
