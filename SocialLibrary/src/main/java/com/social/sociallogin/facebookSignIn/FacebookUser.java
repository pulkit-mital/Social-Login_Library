package com.social.sociallogin.facebookSignIn;

import org.json.JSONObject;

/**
 * Created by pulkitmital on 02/12/16.
 */

public class FacebookUser {

    private String name;
    private String email;
    private String facebookId;
    private String gender;
    private String about;
    private String bio;
    private String coverPicUrl;
    private String profilePic;

    /**
     * JSON response received. If you want to parse more field
     */

    private JSONObject response;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getCoverPicUrl() {
        return coverPicUrl;
    }

    public void setCoverPicUrl(String coverPicUrl) {
        this.coverPicUrl = coverPicUrl;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public JSONObject getResponse() {
        return response;
    }

    public void setResponse(JSONObject response) {
        this.response = response;
    }
}
