package com.social.sociallogin.pinterestSignin;

/**
 * Created by pulkitmital on 28/12/16.
 */

public interface PinterestResponse {

    void onPinterestError();

    void onPinterestLoginSuccess(PinterestUser pinterestUser);
}
