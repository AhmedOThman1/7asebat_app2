package com.example.login;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.like.LikeButton;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * this class for hold the views in the post , by using it we can access the views
 * يعني الكلاس ده هو اللي من خلاله بنعمل اكسس لمحتويات البوست زي الصورة و الكلام وما الي ذلك
 * **/
public class PostViewHolder {
    CircleImageView vProfile_image;
    TextView vAccount_name;
    TextView vPost_time;
    TextView vPost_header;
    TextView vPost_description;
    ImageView vPost_img;
    LinearLayout vRating;
    LinearLayout vFavorite;
    LinearLayout vSave;
    LinearLayout vShare;
    LikeButton vLikeButton;
    ImageView vDownload;
    TextView vNumber_of_ratting_post;
    TextView vNumber_of_favorite_post;


}
