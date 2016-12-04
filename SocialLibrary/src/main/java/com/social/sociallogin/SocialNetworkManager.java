package com.social.sociallogin;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.social.sociallogin.facebookSignIn.FacebookHelper;
import com.social.sociallogin.googleSignIn.GoogleSignInHelper;

/**
 * Created by pulkitmital on 18/11/16.
 */

public class SocialNetworkManager {


    private Context context;
    private FragmentActivity activity;
    private GoogleSignInHelper googleSignInHelper;
    private String fieldString;
    private FacebookHelper mFacebookHelper;

    @Nullable
    private String serverClientId;


    public SocialNetworkManager(Builder builder) {
        context = builder.context;
        activity = builder.activity;
        serverClientId = builder.serverClientId;
        fieldString = builder.fieldString;
    }

    /**
     * @return object for GoogleSignInHelper - It is a helper class that will help to proceed with Google Login
     */
    public GoogleSignInHelper getGoogleSocialLogin() {

        if (googleSignInHelper == null)
            googleSignInHelper = new GoogleSignInHelper(activity, serverClientId);

        return googleSignInHelper;
    }

    public FacebookHelper getFacebookLogin(){

        if(mFacebookHelper == null)
            mFacebookHelper = new FacebookHelper(fieldString, activity);

        return mFacebookHelper;
    }
    public static class Builder {

        private Context context;
        private FragmentActivity activity;
        private String serverClientId;
        private String fieldString;


        /**
         * Constructor
         */

        public Builder(Context context) {
            if (context == null)
                throw new IllegalArgumentException("context cannot be null");

            this.context = context;
        }

        public SocialNetworkManager build() {

            if (activity == null)
                throw new IllegalArgumentException("FragmentActivity context cannot be null");

            return new SocialNetworkManager(this);
        }

        public Builder withGoogle(FragmentActivity activity, String serverClientId) {
            this.activity = activity;
            this.serverClientId = serverClientId;
            return this;
        }

        public Builder withFacebook(FragmentActivity activity, String fieldString){

            this.activity = activity;
            this.fieldString = fieldString;

            return this;
        }
    }
}
