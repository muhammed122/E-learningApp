package com.example.e_learningapp.models;

public class ModelChat {


    String massage ;
    String senderId ;

    public ModelChat (){}
    public ModelChat(String massage, String senderId) {
        this.massage = massage;
        this.senderId = senderId;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }
}
