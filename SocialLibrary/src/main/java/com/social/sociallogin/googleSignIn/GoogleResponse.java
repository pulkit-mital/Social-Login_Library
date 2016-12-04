package com.social.sociallogin.googleSignIn;

/**
 * Created by pulkitmital on 18/11/16.
 */

public interface GoogleResponse {

    void onGoogleSignIn(GoogleUser user);

    void onGoogleSignInFailed();
}
