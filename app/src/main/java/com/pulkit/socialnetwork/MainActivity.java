package com.pulkit.socialnetwork;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.social.sociallogin.SocialNetworkManager;
import com.social.sociallogin.facebookSignIn.FacebookResponse;
import com.social.sociallogin.facebookSignIn.FacebookUser;
import com.social.sociallogin.googleSignIn.GoogleResponse;
import com.social.sociallogin.googleSignIn.GoogleUser;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private static final String TAG = MainActivity.class.getSimpleName();
    private SocialNetworkManager msocialNetworkManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button google = (Button) findViewById(R.id.login_google);
        Button facebook = (Button) findViewById(R.id.login_facebook);
        google.setOnClickListener(this);
        facebook.setOnClickListener(this);
        msocialNetworkManager = new SocialNetworkManager.Builder(this)
                .withGoogle(this,null)
                .withFacebook(this, "id,name,email,gender,birthday,picture,cover")
                .build();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.login_google:
                loginWithGoogle();
                break;
            case R.id.login_facebook:
                loginWithFacebook();
        }
    }


    private void loginWithGoogle(){

        msocialNetworkManager.getGoogleSocialLogin().performSignIn(this, new GoogleResponse() {
            @Override
            public void onGoogleSignIn(GoogleUser user) {
                if(user !=null) {
                    Log.v(TAG, user.getName());
                }else{
                    Log.v(TAG,"else in on google sign in");
                }
            }

            @Override
            public void onGoogleSignInFailed() {

                Log.v(TAG,"sign in failed");
            }
        });
    }

    private void loginWithFacebook(){
        msocialNetworkManager.getFacebookLogin().performSignIn(this, new FacebookResponse() {
            @Override
            public void onFbSignInFail() {

                Log.v(TAG,"sign in failed");
            }

            @Override
            public void onFbSignInSuccess() {
                Log.v(TAG,"sign in success");
            }

            @Override
            public void onFbProfileReceived(FacebookUser facebookUser) {
                if(facebookUser!=null) {
                    Log.v(TAG, "sign in fsucces:" + facebookUser.getResponse().toString());
                }else{
                    Log.v(TAG,"else sign in failed");
                }
            }


        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        msocialNetworkManager.getGoogleSocialLogin().onActivityResult(requestCode,resultCode,data);
        msocialNetworkManager.getFacebookLogin().onActivityResult(requestCode,resultCode,data);
    }
}
