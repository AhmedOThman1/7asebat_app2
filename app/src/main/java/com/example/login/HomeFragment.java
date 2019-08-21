package com.example.login;


import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.login.AccountFragment.StringToBitMap;
import static com.example.login.Home.navigationView;
import static com.example.login.launchActivity.SP_ACCOUNT;
import static com.example.login.launchActivity.SP_F_NAME;
import static com.example.login.launchActivity.SP_L_NAME;
import static com.example.login.launchActivity.SP_PROFILE_IMAGE;

public class HomeFragment extends Fragment {
    static public ArrayList<Post> posts = new ArrayList<Post>();
    PostAdapter adapter;
    CircleImageView UserImage_header;
    TextView username_name , email_header;
    AlertDialog.Builder builder;
    AlertDialog dialog;

    public HomeFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /** Here we must get the posts from  database and put it in the Array list **/
        posts.clear();  // to avoid duplicate

        posts.add(new Post(R.drawable.samar,"Samar Abdo","1 hr ago" ,"OPP chapter 1","this 's a mol5s for Dr / Ahmed Taha , this subject_sp name is oop and oop is important for us , the programmers ! Object-oriented programming (OOP) is a programming language model in which programs are organized around data, or objects" , 123 , R.drawable.post1_img , 421 ));
        posts.add(new Post(R.drawable.omnia,"Omnia Alwy","yesterday 30/07/2019" ,"Android summer training","this 's a mol5s for M / M7md Ibrhm , this subject_sp name is android and the android is important for us , the programmers ! " , 421 , R.drawable.post2_img , 1238 ));
        posts.add(new Post(R.drawable.samy,"Ahmed Samy","25/07/2019" ,"Web summer training","this 's a mol5s for M / Ahmed Yousry , this subject_sp name is web development and the web is important for all of us , the programmers ! it's ynksm to 2 parts frontend and backend" , 2 , R.drawable.post3_img ,713 ));
        posts.add(new Post(R.drawable.osman,"Ahmed Osman","30/09/2018" ,"Embedded system chapter 4","this 's a mol5s for Dr / Ahmed shalby , this subject_sp name is embedded system and embedded system is important for us , the programmers ! " , 713 , R.drawable.post4_img, 83712 ));
        posts.add(new Post(R.drawable.ziad,"Ziad Sakr","14/08/2019" ,"Security chapter 3","this 's a mol5s for M / Khaled , this subject_sp name is Security and Security is important for us , the programmers ! " , 113 , R.drawable.post5_img, 2421 ));
        posts.add(new Post(R.drawable.shawky,"Ahmed Shawky","11/06/2019" ,"Network chapter 5","this 's a mol5s for M / Ahmed , this subject_sp name is Network and Network is important for us , the programmers ! " , 13 , R.drawable.post6_img, 221 ));



        adapter = new PostAdapter(getActivity(),posts);



    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);


        final ListView list_post = view.findViewById(R.id.posts);

        list_post.setAdapter(adapter);


        /** show the user image , name and email in the drawer **/

        View headerView = navigationView.getHeaderView(0);

        username_name =(TextView) headerView.findViewById(R.id.username_header);
        username_name.setText(SP_F_NAME + " " + SP_L_NAME);

        email_header = (TextView) headerView .findViewById(R.id.email_header);
        email_header.setText(SP_ACCOUNT);

        UserImage_header = headerView.findViewById(R.id.UserImage_header);
        UserImage_header.setImageBitmap(StringToBitMap(SP_PROFILE_IMAGE));
        // if user click the image it 'll open in the dialog alert
        UserImage_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder = new AlertDialog.Builder(getActivity());

                View showZoomImage = getLayoutInflater().inflate(R.layout.show_zoom_image,null);
                PhotoView photoView = (PhotoView) showZoomImage.findViewById(R.id.photo_view);
                photoView.setImageBitmap(StringToBitMap(SP_PROFILE_IMAGE));

                builder.setView(showZoomImage);
                dialog = builder.create();
                dialog.show();

            }
        });

        /** delete the next comments it's mlha4 lazma **/
/*
        post_img = view.findViewById(R.id.post_img);
        post_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        list_post.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            public void onItemClick(AdapterView<?> adapterView, View view,final int position, long id) {


             //   number_of_ratting_post = view.findViewById(R.id.number_of_ratting_post);
            //    number_of_favorite_post = view.findViewById(R.id.number_of_favorite_post);

                list_postChildAt=(RelativeLayout) list_post.getChildAt(position);

                images_post = (RelativeLayout) list_postChildAt.getChildAt(5);
                continer = (LinearLayout) list_postChildAt.getChildAt(6);

                post_img =(ImageView) images_post.getChildAt(0);
           //     likeButton =(LikeButton) images_post.getChildAt(1);
          //     download_img =(ImageView) images_post.getChildAt(2);

                react_btn = (LinearLayout) continer.getChildAt(0);
                //number_of_ratting_post = (TextView) react_btn.getChildAt(0);

                love_btn = (LinearLayout) continer.getChildAt(1);                   ////////
               // number_of_favorite_post =(TextView) love_btn.getChildAt(0);


                post_img.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        mode = !mode;
                        likeButton =(LikeButton) images_post.getChildAt(1);
                        likeButton.setVisibility(mode ? View.VISIBLE : View.GONE);
                        likeButton.setLiked(mode);
                        number_of_favorite_post =(TextView) love_btn.getChildAt(0);
                        number_of_favorite_post.setCompoundDrawablesWithIntrinsicBounds(mode ? R.drawable.love24: R.drawable.nolove24, 0, 0, 0);
                        number_of_favorite_post.setText(mode ?""+(Integer.parseInt(number_of_favorite_post.getText().toString())+1) :""+Integer.parseInt(number_of_favorite_post.getText().toString()) );
                        Toast.makeText(getActivity(), mode ?"Adding to favorite.... , position =  " + position :"Remove from favorite.... , position =  " +position, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });

                post_img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        download_img =(ImageView) images_post.getChildAt(2);
                        download_img.animate().scaleX(1).scaleY(1).setDuration(800).start();
                        Toast.makeText(getActivity(), "Downloading pdf.... , position =  "+ position , Toast.LENGTH_SHORT).show();

                    }
                });


                love_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mode = !mode;
                        likeButton =(LikeButton) images_post.getChildAt(1);
                        likeButton.setVisibility(mode ? View.VISIBLE : View.GONE);
                        likeButton.setLiked(mode);
                        number_of_favorite_post.setCompoundDrawablesWithIntrinsicBounds(mode ? R.drawable.love24: R.drawable.nolove24, 0, 0, 0);
                        number_of_favorite_post.setText(mode ?""+(Integer.parseInt(number_of_favorite_post.getText().toString())+1) :""+Integer.parseInt(number_of_favorite_post.getText().toString()) );
                        Toast.makeText(getActivity(), mode ?"Adding to favorite.... , position =  " + position :"Remove from favorite.... , position =  " +position, Toast.LENGTH_SHORT).show();

                    }
                });

            }

        });

        Profile_image = view.findViewById(R.id.profile_image);
        account_name = view.findViewById(R.id.account_name);
        post_time = view.findViewById(R.id.time_post);
        post_header = view.findViewById(R.id.post_header);
        post_description = view.findViewById(R.id.post_description);
        number_of_ratting_post = view.findViewById(R.id.number_of_ratting_post);
        number_of_favorite_post = view.findViewById(R.id.number_of_favorite_post);
        post_img = view.findViewById(R.id.post_img);
        download_img = view.findViewById(R.id.download_img);
*/
        return view;
    }

}
