package com.social.sociallogin.pinterestSignin;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.pinterest.android.pdk.PDKCallback;
import com.pinterest.android.pdk.PDKClient;
import com.pinterest.android.pdk.PDKException;
import com.pinterest.android.pdk.PDKResponse;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by pulkitmital on 28/12/16.
 */

public class PinterestSigninHelper {

    private PDKClient pdkClient;
    @NonNull
    private PinterestResponse mListener;
    private Context context;


    public PinterestSigninHelper(@NonNull Context context, @NonNull String clientId){

        this.context = context;
        pdkClient = PDKClient.configureInstance(context, clientId);
        pdkClient.onConnect(context);
    }


    public void performSignIn(@NonNull PinterestResponse pinterestResponse){
        this.mListener = pinterestResponse;
        if(pinterestResponse == null)
            throw new IllegalArgumentException("PinterestResponse cannot be null");

        loginToPinterest();
    }

    /**
     * This method handles onActivityResult callbacks from fragment or activity.
     *
     * @param requestCode request code received.
     * @param resultCode  result code received.
     * @param data        Data intent.
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (pdkClient != null)
            pdkClient.onOauthResponse(requestCode, resultCode,
                    data);
    }

    private void loginToPinterest(){

        List scopes = new ArrayList<String>();
        scopes.add(PDKClient.PDKCLIENT_PERMISSION_READ_PUBLIC);
        scopes.add(PDKClient.PDKCLIENT_PERMISSION_WRITE_PUBLIC);

        pdkClient.login(context, scopes, new PDKCallback() {
            @Override
            public void onSuccess(PDKResponse response) {
                Log.d(getClass().getName(), response.getData().toString());
                //user logged in, use response.getUser() to get PDKUser object
                try{
                    JSONObject jsonObject = (JSONObject) response.getData();
                    PinterestUser user = new PinterestUser();
                    user.setId(jsonObject.optString("id"));
                    user.setFirstName(jsonObject.optString("first_name"));
                    user.setLastName(jsonObject.optString("last_name"));
                    user.setUrl(jsonObject.optString("url"));

                    mListener.onPinterestLoginSuccess(user);

                }catch (Exception ex){
                    mListener.onPinterestError();
                }

            }

            @Override
            public void onFailure(PDKException exception) {
                Log.e(getClass().getName(), exception.getDetailMessage());
                mListener.onPinterestError();
            }
        });
    }

}
