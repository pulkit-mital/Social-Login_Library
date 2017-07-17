# Social-Login_Library
Social-Login-Library is library which makes working with social networks easier. If you sometime tried to work with social networks on android you should remember that this is a hell. You should read documentation for every social network, download SDK or use some libraries for OAuth and make http calls by yourself. This library should makes your life easier, it contains common interface for Facebook and Google, just build SocialNetworkManager and configure your AndroidManiferst and you can login users.


#Getting Started


Next you need to initialize mSocialNetworkManager. Build it with SocialNetworkManager.Builder and add to fragment manager.

    msocialNetworkManager = new SocialNetworkManager.Builder(this)
                    .withGoogle(this,null)
                    .withFacebook(this, "id,name,email,gender,birthday,picture,cover")
                    .withTwitter(this,<TWITTER_CONSUMER_KEY>,<TWITTER_CONSUMER_SECRET>)
                    .withPinterest(<PINTEREST_API_KEY>)
                    .build();


Now you can execute requests, for example login request:

####For Google:

    msocialNetworkManager.getGoogleSocialLogin().performSignIn(this, new GoogleResponse() {
                @Override
                public void onGoogleSignIn(GoogleUser user) {

                }

                @Override
                public void onGoogleSignInFailed() {


                }
            });


####For Facebook:

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


####For Twitter:

Include this in your base/build.gradle

    buildscript {
        repositories {
            jcenter()
            maven { url 'https://maven.fabric.io/public' }

        }
        dependencies {
            classpath 'com.android.tools.build:gradle:2.2.3'
            classpath 'io.fabric.tools:gradle:1.+'

            // NOTE: Do not place your application dependencies here; they belong
            // in the individual module build.gradle files
        }
    }

In your app module build.gradle include:

    apply plugin: 'com.android.application'
    apply plugin: 'io.fabric'

    repositories {
        jcenter()
        maven { url 'https://maven.fabric.io/public' }
    }

 In your activity to perform sign-in include:

         msocialNetworkManager.getTwitterLogin().performSignIn(new TwitterResponse() {
                     @Override
                     public void onTwitterError() {

                     }

                     @Override
                     public void onTwitterSignIn(@NonNull String userId, @NonNull String userName) {

                     }

                     @Override
                     public void onTwitterProfileReceived(TwitterUser user) {

                     }
                 });


#### For Pinterest:

Include this code in your manifest under login activity


               <intent-filter>
                    <action android:name="android.intent.action.VIEW" />

                    <category android:name="android.intent.category.DEFAULT" />
                    <category android:name="android.intent.category.BROWSABLE" />

                    <data android:scheme="pdk{api-key}" />
                </intent-filter>

change {api-key} with your pinterest api key

To signin using pinterest include:

    msocialNetworkManager.getPinterestLogin().performSignIn(new PinterestResponse() {
                @Override
                public void onPinterestError() {

                }

                @Override
                public void onPinterestLoginSuccess(PinterestUser pinterestUser) {

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
   	        compile 'com.github.pulkit-mital:Social-Login_Library:1.2'
   	}

[![](https://jitpack.io/v/pulkit-mital/Social-Login_Library.svg)](https://jitpack.io/#pulkit-mital/Social-Login_Library)


#Important

1) Library don't manage state, you need to do it yourself


    @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            msocialNetworkManager.getGoogleSocialLogin().onActivityResult(requestCode,resultCode,data);
            msocialNetworkManager.getFacebookLogin().onActivityResult(requestCode,resultCode,data);
            msocialNetworkManager.getTwitterLogin().onActivityResult(requestCode,resultCode,data);
            msocialNetworkManager.getPinterestLogin().onActivityResult(requestCode,resultCode,data);
        }



#Developed By

Pulkit Mital - pulkit.mital@gmail.com





