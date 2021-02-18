package com.example.e_learningapp.models;

public class ModelAuth {




   private String userName ;
   private String email ;
   private String type ;
   private  String userImage;
   private String userId ;
   private String userType ;

   public ModelAuth(){}
    public ModelAuth(String userName, String email, String type, String userImage , String userType) {
        this.userName = userName;
        this.email = email;
        this.type = type;
        this.userImage = userImage;
        this.userType = userType ;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }
}
