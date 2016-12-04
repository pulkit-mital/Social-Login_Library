# Social-Login_Library
Social-Login-Library is library which makes working with social networks easier. If you sometime tried to work with social networks on android you should remember that this is a hell. You should read documentation for every social network, download SDK or use some libraries for OAuth and make http calls by yourself. This library should makes your life easier, it contains common interface for Facebook and Google, just build SocialNetworkManager and configure your AndroidManiferst and you can login users.


#Getting Started
First of all, you need to register you application, please check this links: Facebook, Google


Next you need to initialize mSocialNetworkManager. Build it with SocialNetworkManager.Builder and add to fragment manager.

    msocialNetworkManager = new SocialNetworkManager.Builder(this)
                    .withGoogle(this,null)
                    .withFacebook(this, "id,name,email,gender,birthday,picture,cover")
                    .build();


Now you can execute requests, for example login request:

For Google:

    msocialNetworkManager.getGoogleSocialLogin().performSignIn(this, new GoogleResponse() {
                @Override
                public void onGoogleSignIn(GoogleUser user) {

                }

                @Override
                public void onGoogleSignInFailed() {


                }
            });


For Facebook:

     msocialNetworkManager.getFacebookLogin().performSignIn(this, new FacebookResponse() {
                @Override
                public void onFbSignInFail() {


                }

                @Override
                public void onFbSignInSuccess() {

                }

                @Override
                public void onFbProfileReceived(FacebookUser facebookUser) {

                }


            });

#Including in your project

Add it in your root build.gradle at the end of repositories:

    allprojects {
    		repositories {
    			...
    			maven { url 'https://jitpack.io' }
    		}
    	}

Step 2. Add the dependency

   	dependencies {
   	        compile 'com.github.pulkit-mital:Social-Login_Library:1.0'
   	}

[![](https://jitpack.io/v/pulkit-mital/Social-Login_Library.svg)](https://jitpack.io/#pulkit-mital/Social-Login_Library)


#Important

1) Library don't manage state, you need to do it yourself


    @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            msocialNetworkManager.getGoogleSocialLogin().onActivityResult(requestCode,resultCode,data);
            msocialNetworkManager.getFacebookLogin().onActivityResult(requestCode,resultCode,data);
        }



#Developed By

Pulkit Mital - pulkit.mital@gmail.com





