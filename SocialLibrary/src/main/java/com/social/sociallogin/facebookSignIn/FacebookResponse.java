package com.social.sociallogin.facebookSignIn;

/**
 * Created by pulkitmital on 02/12/16.
 */

public interface FacebookResponse {

    void onFbSignInFail();

    void onFbSignInSuccess();

    void onFbProfileReceived(FacebookUser facebookUser);

}
