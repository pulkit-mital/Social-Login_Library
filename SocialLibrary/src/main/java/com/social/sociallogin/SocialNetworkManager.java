package com.social.sociallogin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;

import com.social.sociallogin.facebookSignIn.FacebookHelper;
import com.social.sociallogin.googleSignIn.GoogleSignInHelper;
import com.social.sociallogin.pinterestSignin.PinterestSigninHelper;
import com.social.sociallogin.twitterSignin.TwitterHelper;

/**
 * Created by pulkitmital on 18/11/16.
 */

public class SocialNetworkManager {


    private Context context;
    private FragmentActivity activity;
    private GoogleSignInHelper googleSignInHelper;
    private String fieldString;
    private FacebookHelper mFacebookHelper;
    private String twitterConsumerKey;
    private String twitterConsumerSecret;
    private TwitterHelper mTwitterHelper;
    private PinterestSigninHelper pinterestSigninHelper;
    private String pinterestClientId;

    private String serverClientId;


    public SocialNetworkManager(Builder builder) {
        context = builder.context;
        activity = builder.activity;
        serverClientId = builder.serverClientId;
        fieldString = builder.fieldString;
        twitterConsumerKey = builder.twitterConsumerKey;
        twitterConsumerSecret = builder.twitterConsumerSecret;
        this.pinterestClientId = builder.pinterestClientId;
    }

    /**
     * @return object for GoogleSignInHelper - It is a helper class that will help to proceed with Google Login
     */
    public GoogleSignInHelper getGoogleSocialLogin() {

        if (googleSignInHelper == null)
            googleSignInHelper = new GoogleSignInHelper(activity, serverClientId);

        return googleSignInHelper;
    }

    public FacebookHelper getFacebookLogin() {

        if (mFacebookHelper == null)
            mFacebookHelper = new FacebookHelper(fieldString, activity);

        return mFacebookHelper;
    }

    public TwitterHelper getTwitterLogin() {
        if (mTwitterHelper == null)
            mTwitterHelper = new TwitterHelper(twitterConsumerKey, twitterConsumerSecret, activity);

        return mTwitterHelper;
    }

    public PinterestSigninHelper getPinterestLogin(){
        if(pinterestSigninHelper == null)
            pinterestSigninHelper = new PinterestSigninHelper(context,pinterestClientId);

        return pinterestSigninHelper;
    }

    public static class Builder {

        private Context context;
        private FragmentActivity activity;
        private String serverClientId;
        private String fieldString;
        private String twitterConsumerKey;
        private String twitterConsumerSecret;
        private String pinterestClientId;


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

        public Builder withFacebook(FragmentActivity activity, String fieldString) {

            this.activity = activity;
            this.fieldString = fieldString;

            return this;
        }

        public Builder withTwitter(FragmentActivity activity, String twitterConsumerKey, String twitterConsumerSecret) {
            this.activity = activity;
            this.twitterConsumerKey = twitterConsumerKey;
            this.twitterConsumerSecret = twitterConsumerSecret;
            return this;
        }

        public Builder withPinterest(@NonNull String clientId){
            this.pinterestClientId = clientId;
            return this;
        }
    }
}
