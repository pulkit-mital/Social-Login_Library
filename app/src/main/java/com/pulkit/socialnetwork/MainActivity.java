package com.pulkit.socialnetwork;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.social.sociallogin.SocialNetworkManager;
import com.social.sociallogin.facebookSignIn.FacebookResponse;
import com.social.sociallogin.facebookSignIn.FacebookUser;
import com.social.sociallogin.googleSignIn.GoogleResponse;
import com.social.sociallogin.googleSignIn.GoogleUser;
import com.social.sociallogin.pinterestSignin.PinterestResponse;
import com.social.sociallogin.pinterestSignin.PinterestUser;
import com.social.sociallogin.twitterSignin.TwitterResponse;
import com.social.sociallogin.twitterSignin.TwitterUser;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private static final String TAG = MainActivity.class.getSimpleName();
    private SocialNetworkManager msocialNetworkManager;
    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "YOUR-CONSUMER-KEY";
    private static final String TWITTER_SECRET = "YOUR-CONSUMER-SECRET";
    private static final String PINTEREST_API_KEY = "YOUR-API_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button google = (Button) findViewById(R.id.login_google);
        Button facebook = (Button) findViewById(R.id.login_facebook);
        Button twitter = (Button) findViewById(R.id.login_twitter);
        Button pinterest = (Button) findViewById(R.id.login_pinterest);
        google.setOnClickListener(this);
        facebook.setOnClickListener(this);
        twitter.setOnClickListener(this);
        pinterest.setOnClickListener(this);
        msocialNetworkManager = new SocialNetworkManager.Builder(this)
                .withGoogle(this,null)
                .withFacebook(this, "id,name,email,gender,birthday,picture,cover")
                .withTwitter(this,TWITTER_KEY,TWITTER_SECRET)
                .withPinterest(PINTEREST_API_KEY)
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
                break;
            case R.id.login_twitter:
                loginWithTwitter();
                break;
            case R.id.login_pinterest:
                loginWithPinterest();
                break;
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

    private void loginWithTwitter(){

        msocialNetworkManager.getTwitterLogin().performSignIn(new TwitterResponse() {
            @Override
            public void onTwitterError() {
                Log.v(TAG,"onTwitterError");
            }

            @Override
            public void onTwitterSignIn(@NonNull String userId, @NonNull String userName) {
                Log.v(TAG, userName);
            }

            @Override
            public void onTwitterProfileReceived(TwitterUser user) {
                if(user !=null){
                    Log.v(TAG,user.getName());
                }
            }
        });
    }

    private void loginWithPinterest(){

        msocialNetworkManager.getPinterestLogin().performSignIn(new PinterestResponse() {
            @Override
            public void onPinterestError() {
                Log.v(TAG, "error");
            }

            @Override
            public void onPinterestLoginSuccess(PinterestUser pinterestUser) {

                Log.v(TAG,"success");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        msocialNetworkManager.getGoogleSocialLogin().onActivityResult(requestCode,resultCode,data);
        msocialNetworkManager.getFacebookLogin().onActivityResult(requestCode,resultCode,data);
        msocialNetworkManager.getTwitterLogin().onActivityResult(requestCode,resultCode,data);
        msocialNetworkManager.getPinterestLogin().onActivityResult(requestCode,resultCode,data);
    }
}
