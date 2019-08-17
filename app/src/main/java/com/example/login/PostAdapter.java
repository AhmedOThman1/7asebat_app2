package com.example.login;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.chrisbanes.photoview.PhotoView;
import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;
import com.like.IconType;
import com.like.LikeButton;

import java.util.List;

import static com.example.login.HomeFragment.posts;

public class PostAdapter extends ArrayAdapter<Post> {

    private Activity activity;

    AlertDialog.Builder builder , builder2;
    AlertDialog dialog , dialog2;
    int mPosition;

    public PostAdapter(Activity context, List<Post> posts) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, posts);
        activity = context;
    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public Post getItem(int position) {
        return posts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    //    public void updateSelectedPosition (int selectedPosition) {
//       // this.selectedPosition = selectedPosition;
//        notifyDataSetChanged();
//    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view

        mPosition = position;
        View listItemView;
        final PostViewHolder postViewHolder;

        if (convertView == null) {

            /*
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.post, parent, false);
            */

            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
            listItemView = layoutInflater.inflate(R.layout.post, parent, false);

            postViewHolder = new PostViewHolder();
            postViewHolder.vProfile_image = listItemView.findViewById(R.id.profile_image);
            postViewHolder.vAccount_name = listItemView.findViewById(R.id.account_name);
            postViewHolder.vPost_time = listItemView.findViewById(R.id.time_post);
            postViewHolder.vPost_header = listItemView.findViewById(R.id.post_header);
            postViewHolder.vPost_description = listItemView.findViewById(R.id.post_description);
            postViewHolder.vPost_img = listItemView.findViewById(R.id.post_img);
            postViewHolder.vLikeButton = listItemView.findViewById(R.id.love);
            postViewHolder.vRating = listItemView.findViewById(R.id.rating_icon);
            postViewHolder.vFavorite = listItemView.findViewById(R.id.favorite_icon);
            postViewHolder.vSave = listItemView.findViewById(R.id.download_icon);
            postViewHolder.vShare = listItemView.findViewById(R.id.share_icon);
            postViewHolder.vDownload = listItemView.findViewById(R.id.download_img);
            postViewHolder.vNumber_of_ratting_post = listItemView.findViewById(R.id.number_of_ratting_post);
            postViewHolder.vNumber_of_favorite_post = listItemView.findViewById(R.id.number_of_favorite_post);

            listItemView.setTag(postViewHolder);

        } else {
            listItemView = convertView;
            postViewHolder = (PostViewHolder) listItemView.getTag();
        }
        final Post currentPost = getItem(mPosition);
        // Toast.makeText(activity, "poo "+ mPosition, Toast.LENGTH_SHORT).show();
        postViewHolder.vProfile_image.setImageResource(currentPost.getProfile_image());
        postViewHolder.vAccount_name.setText(currentPost.getAccount_name());
        postViewHolder.vPost_time.setText(currentPost.getPost_time());
        postViewHolder.vPost_header.setText(currentPost.getPost_header());
        postViewHolder.vPost_description.setText(currentPost.getPost_description());
        postViewHolder.vPost_img.setImageResource(currentPost.getPost_img());
        postViewHolder.vLikeButton.setVisibility(currentPost.getLikeVisibility());
        postViewHolder.vNumber_of_favorite_post.setCompoundDrawablesWithIntrinsicBounds(currentPost.getLikeMode() == 1 ? R.drawable.love24 : R.drawable.nolove, 0, 0, 0);
        postViewHolder.vNumber_of_favorite_post.setTextColor(activity.getResources().getColor(currentPost.getLikeMode() == 1 ? R.color.colorLove : android.R.color.black));
        postViewHolder.vNumber_of_favorite_post.setText((currentPost.getmNumber_of_favorite_post()) + "");
        postViewHolder.vNumber_of_ratting_post.setCompoundDrawablesWithIntrinsicBounds(currentPost.getRateMode() ? R.drawable.rate : R.drawable.norate, 0, 0, 0);
        postViewHolder.vNumber_of_ratting_post.setTextColor(activity.getResources().getColor(currentPost.getRateMode() ? R.color.colorRate : R.color.colorNo));
        postViewHolder.vDownload.setVisibility(currentPost.getDownloadVisibility());

        postViewHolder.vPost_header.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Post header",postViewHolder.vPost_header.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(activity, " \" Post header \" Saved to clip board", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        postViewHolder.vPost_description.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Post description",postViewHolder.vPost_description.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(activity, " \" Post description \" Saved to clip board", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        postViewHolder.vPost_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Download(mPosition, postViewHolder.vDownload, currentPost);

            }
        });
        postViewHolder.vPost_img.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Favorite(mPosition, postViewHolder.vLikeButton, postViewHolder.vNumber_of_favorite_post, currentPost);
                return true;
            }
        });
        postViewHolder.vRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Rate(mPosition, postViewHolder.vNumber_of_ratting_post, postViewHolder.vLikeButton,currentPost);
            }
        });

        postViewHolder.vFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Favorite(mPosition, postViewHolder.vLikeButton, postViewHolder.vNumber_of_favorite_post, currentPost);
            }
        });

        postViewHolder.vSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Download(mPosition, postViewHolder.vDownload, currentPost);
            }
        });
        postViewHolder.vShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, postViewHolder.vPost_header.getText().toString() + "\n\n" + postViewHolder.vPost_description.getText().toString() + "\n");
                shareIntent.setType("text/plain");
                activity.startActivity(shareIntent);

            }
        });
        postViewHolder.vProfile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder2 = new AlertDialog.Builder(activity);
                View showZoomImage = activity.getLayoutInflater().inflate(R.layout.show_zoom_image, null);
                PhotoView photoView = (PhotoView) showZoomImage.findViewById(R.id.photo_view);
                BitmapDrawable bitmapDrawable = ((BitmapDrawable) postViewHolder.vProfile_image.getDrawable());

                photoView.setImageBitmap(bitmapDrawable.getBitmap());

                builder2.setView(showZoomImage);
                dialog2 = builder2.create();
                dialog2.show();
            }
        });
//        View showZoomImage = activity.getLayoutInflater().inflate(R.layout.show_zoom_image,null);


        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;
    }

    void Favorite(int position, final LikeButton vLikeButton, TextView vNumber_of_favorite_post, final Post post) {


        Toast.makeText(activity, "Toast " + post.getAccount_name(), Toast.LENGTH_LONG).show();

        if (post.getLikeMode() == -1 || post.getLikeMode() == 0) {
            post.setLikeMode(1);
            post.setmNumber_of_favorite_post(post.getmNumber_of_favorite_post() + 1);
        } else if (post.getLikeMode() == 1) {
            post.setLikeMode(0);
            post.setmNumber_of_favorite_post(post.getmNumber_of_favorite_post() - 1);
        }


        vLikeButton.setIcon(IconType.Heart);

        post.setLikeVisibility(View.VISIBLE);

        vLikeButton.setVisibility(post.getLikeVisibility());

        vLikeButton.setLiked(post.getLikeMode() == 1);

        vNumber_of_favorite_post.setCompoundDrawablesWithIntrinsicBounds(post.getLikeMode() == 1 ? R.drawable.love24 : R.drawable.nolove, 0, 0, 0);
        vNumber_of_favorite_post.setTextColor(activity.getResources().getColor(post.getLikeMode() == 1 ? R.color.colorLove : android.R.color.black));
        vNumber_of_favorite_post.setText((post.getmNumber_of_favorite_post()) + "");
        Toast.makeText(activity, "Adding to favorite...., position =  " + position, Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                vLikeButton.animate().alpha(0).setDuration(800);
            }
        }, 1000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                vLikeButton.animate().alpha(1).setDuration(1);
                post.setLikeVisibility(View.GONE);

                vLikeButton.setVisibility(post.getLikeVisibility());

            }
        }, 1500);
    }

    void Rate(int position, final TextView vNumber_of_ratting_post, final LikeButton vLikeButton , final Post post) {

        post.setRateMode(true);

        builder = new AlertDialog.Builder(activity);

        View smileView = activity.getLayoutInflater().inflate(R.layout.smiley_rating, null);

        SmileRating smileRating = smileView.findViewById(R.id.smile_rating);
        smileRating.setSelectedSmile(BaseRating.OKAY);
        smileRating.setOnRatingSelectedListener(new SmileRating.OnRatingSelectedListener() {
            @Override
            public void onRatingSelected(int level, boolean reselected) {
                Toast.makeText(activity, "level :  " + level, Toast.LENGTH_SHORT).show();
                vNumber_of_ratting_post.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rate, 0, 0, 0);
                vNumber_of_ratting_post.setTextColor(activity.getResources().getColor(R.color.colorRate));
                dialog.cancel();

                vLikeButton.setIcon(IconType.Star);


                post.setLikeVisibility(View.VISIBLE);

                vLikeButton.setVisibility(post.getLikeVisibility());
                vLikeButton.setLiked(true);

/*
                vNumber_of_favorite_post.setCompoundDrawablesWithIntrinsicBounds(post.getLikeMode() == 1 ? R.drawable.love24 : R.drawable.nolove, 0, 0, 0);
                vNumber_of_favorite_post.setTextColor(activity.getResources().getColor(post.getLikeMode() == 1 ? R.color.colorLove : android.R.color.black));
                vNumber_of_favorite_post.setText((post.getmNumber_of_favorite_post()) + "");
                Toast.makeText(activity, "Adding to favorite...., position =  " + position, Toast.LENGTH_SHORT).show();
*/
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        vLikeButton.animate().alpha(0).setDuration(800);
                    }
                }, 1000);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        vLikeButton.animate().alpha(1).setDuration(1);
                        post.setLikeVisibility(View.GONE);

                        vLikeButton.setVisibility(post.getLikeVisibility());

                    }
                }, 1500);

            }

        });

        builder.setView(smileView);
        dialog = builder.create();
        dialog.show();
    }


    void Download(int position, final ImageView vDownload, final Post post) {

        post.setDownloadVisibility(View.VISIBLE);

        vDownload.setVisibility(post.getDownloadVisibility());

        vDownload.animate().translationX(0).translationY(300).setDuration(1000).start();

        Toast.makeText(activity, "Downloading pdf.... , position =  " + position, Toast.LENGTH_LONG).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                post.setDownloadVisibility(View.GONE);

                vDownload.setVisibility(post.getDownloadVisibility());
                vDownload.animate().translationX(0).translationY(0).setDuration(1).start();
            }
        }, 1000);


    }
}
