package com.social.sociallogin.googleSignIn;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by pulkitmital on 18/11/16.
 */

public class GoogleSignInHelper implements GoogleApiClient.OnConnectionFailedListener {


    private static final int RC_SIGN_IN = 100;
    private FragmentActivity mContext;
    private GoogleResponse mListener;
    private GoogleApiClient mGoogleApiClient;


    /**
     * Public constructor
     *
     * @param mContext       instance of caller activity
     * @param serverClientId The client ID of the server that will verify the integrity of the token. If you don't have clientId pass null.
     *                       For more detail visit {@link 'https://developers.google.com/identity/sign-in/android/backend-auth'}
     */

    public GoogleSignInHelper(FragmentActivity mContext, @Nullable String serverClientId) {

        this.mContext = mContext;


        //build api client
        buildGoogleApiClient(buildSignInOptions(serverClientId));
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        mListener.onGoogleSignInFailed();

    }


    private GoogleSignInOptions buildSignInOptions(@Nullable String serverClientId) {

        GoogleSignInOptions.Builder gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .requestId();
        if (serverClientId != null)
            gso.requestIdToken(serverClientId);

        return gso.build();
    }

    private void buildGoogleApiClient(@NonNull GoogleSignInOptions gso) {

        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .enableAutoManage(mContext, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    public void performSignIn(Activity activity, @NonNull GoogleResponse mListener) {
        this.mListener = mListener;
        if (this.mListener == null) {
            throw new RuntimeException("GoogleResponse listener cannot be null.");
        }
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        activity.startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    public void performSignIn(Fragment activity, @NonNull GoogleResponse mListener) {
        this.mListener = mListener;
        if (this.mListener == null) {
            throw new RuntimeException("GoogleResponse listener cannot be null.");
        }
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        activity.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private GoogleUser parseToGoogleUser(GoogleSignInAccount account) {
        GoogleUser user = new GoogleUser();
        user.setName(account.getDisplayName());
        user.setFamilyName(account.getFamilyName());
        user.setIdToken(account.getIdToken());
        user.setEmail(account.getEmail());
        user.setPhotoUrl(account.getPhotoUrl());
        return user;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Signed in successfully, show authenticated UI.
                GoogleSignInAccount acct = result.getSignInAccount();
                mListener.onGoogleSignIn(parseToGoogleUser(acct));
            } else {
                mListener.onGoogleSignInFailed();
            }
        }
    }
}
