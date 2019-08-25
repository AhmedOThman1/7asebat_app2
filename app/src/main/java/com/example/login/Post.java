package com.example.login;


import android.view.View;

public class Post {

    private int mProfile_image ;
    private String mAccount_name ;
    private String mPost_time ;
    private String mPost_header ;
    private String mPost_description;
    private  int mNumber_of_ratting_post ;
    private  int level_of_ratting_post = 2;
    private int mNumber_of_favorite_post;
    private int mPost_img ;
    private int LikeVisibility = View.GONE;
    private int DownloadVisibility = View.GONE;
    private int LikeMode =-1;
    private boolean RateMode = false;


    Post(int profile_img , String account_name , String post_time , String post_header , String post_description , int number_of_ratting_post , int mpost_img , int number_of_favorite_post){
        mProfile_image = profile_img ;
        mAccount_name = account_name;
        mPost_time = post_time;
        mPost_header = post_header;
        mPost_description = post_description;
        mNumber_of_ratting_post = number_of_ratting_post;
        mNumber_of_favorite_post = number_of_favorite_post;
        this.mPost_img = mpost_img;



        //pub_id++;
        //id=pub_id;
    }

    public int getProfile_image() {
        return mProfile_image;
    }

    public String getAccount_name() {
        return mAccount_name;
    }

    public void setmAccount_name(String mAccount_name) {
        this.mAccount_name = mAccount_name;
    }

    public void setDownloadVisibility(int downloadVisibility) {
        DownloadVisibility = downloadVisibility;
    }

    public int getLevel_of_ratting_post() {
        return level_of_ratting_post;
    }

    public void setLevel_of_ratting_post(int level_of_ratting_post) {
        this.level_of_ratting_post = level_of_ratting_post;
    }

    public int getDownloadVisibility() {
        return DownloadVisibility;
    }

    public String getPost_time() {
        return mPost_time;
    }

    public String getPost_header() {
        return mPost_header;
    }

    public String getPost_description() {
        return mPost_description;
    }

    public int getNumber_of_ratting_post() {
        return mNumber_of_ratting_post;
    }

    public int getPost_img() {
        return mPost_img;
    }

    public int getmNumber_of_favorite_post() {
        return mNumber_of_favorite_post;
    }

    public void setmNumber_of_favorite_post(int mNumber_of_favorite_post) {
        this.mNumber_of_favorite_post = mNumber_of_favorite_post;
    }

    public int getLikeVisibility() {
        return LikeVisibility;
    }

    public void setLikeVisibility(int likeVisibility) {
        LikeVisibility = likeVisibility;
    }

    public int getLikeMode()
    {
        return LikeMode;
    }

    public void setLikeMode(int likeMode) {
        LikeMode = likeMode;
    }

    public void setRateMode(boolean rateMode) {
        RateMode = rateMode;
    }

    public boolean getRateMode()
    {
        return RateMode;
    }

    /*
    public int getId() {
        return id;
    }

    public boolean setLike(){

        return true;
    }
    */
}




