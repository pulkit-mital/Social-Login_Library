package com.social.sociallogin.twitterSignin;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.models.User;

import io.fabric.sdk.android.Fabric;

/**
 * Created by pulkit on 26/12/16.
 */

public class TwitterHelper {

    private TwitterAuthClient mAuthClient;
    @NonNull
    private final Activity mActivity;

    @NonNull
    private TwitterResponse mListener;

    /**
     * Public constructor. This will initialize twitter sdk.
     *
     * @param twitterConsumerKey    twitter consumer key
     * @param twitterConsumerSecret twitter secrete key
     * @param context               instance of the caller.
     */
    public TwitterHelper(String twitterConsumerKey,
                         String twitterConsumerSecret,
                         @NonNull Activity context) {


        mActivity = context;

        //initialize sdk
        TwitterAuthConfig authConfig = new TwitterAuthConfig(twitterConsumerKey,
                twitterConsumerSecret);
        Fabric.with(context, new Twitter(authConfig));

        mAuthClient = new TwitterAuthClient();
    }

    /**
     * Result callback.
     */
    private Callback<TwitterSession> mCallback = new Callback<TwitterSession>() {
        @Override
        public void success(Result<TwitterSession> result) {
            TwitterSession session = result.data;
            mListener.onTwitterSignIn(session.getUserName(), session.getUserId() + " ");

            //load user data.
            getUserData();
        }

        @Override
        public void failure(TwitterException exception) {
            mListener.onTwitterError();
        }
    };

    /**
     * Perform twitter sign in. Call this method when user clicks on "Login with Twitter" button.
     *
     * @param response {@link TwitterResponse} response listener.
     */
    public void performSignIn(@NonNull TwitterResponse response) {
        //noinspection ConstantConditions
        if (response == null)
            throw new IllegalArgumentException("TwitterResponse cannot be null.");

        mListener = response;
        mAuthClient.authorize(mActivity, mCallback);
    }

    /**
     * This method handles onActivityResult callbacks from fragment or activity.
     *
     * @param requestCode request code received.
     * @param resultCode  result code received.
     * @param data        Data intent.
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mAuthClient != null)
            mAuthClient.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Load twitter user profile.
     */
    private void getUserData() {
        Twitter.getApiClient().getAccountService().verifyCredentials(true, false, new Callback<User>() {
            @Override
            public void success(Result<User> userResult) {
                //parse the response
                TwitterUser user = new TwitterUser();
                user.setName(userResult.data.name);
                user.setEmail(userResult.data.email);
                user.setDescription(userResult.data.description);
                user.setPictureUrl(userResult.data.profileImageUrl);
                user.setBannerUrl(userResult.data.profileBannerUrl);
                user.setLanguage(userResult.data.lang);
                user.setId(userResult.data.id);

                mListener.onTwitterProfileReceived(user);
            }

            @Override
            public void failure(TwitterException e) {
                mListener.onTwitterError();
            }
        });
    }
}
