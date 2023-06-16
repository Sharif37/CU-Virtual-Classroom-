package com.example.cuvc;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.MemoryCategory;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.List;


public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {


    private static List<Post> postList;
    private static Context context;


    public PostAdapter(List<Post> postList, Context context) {

        this.postList = postList;
        this.context = context;

    }

    public void setPostList(List<Post> postList) {
        this.postList = postList;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_recycler_view, parent, false);
        return new PostViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {

        Post post = postList.get(position);
        holder.postTextView.setText(post.getText());


       //  for loading image

        if (post.getImageUrl() != null && !post.getImageUrl().isEmpty()) {
            holder.postImageView.setVisibility(View.VISIBLE);
            Glide.get(context).setMemoryCategory(MemoryCategory.HIGH);
            Glide.with(context)
                    .load(post.getImageUrl())
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .skipMemoryCache(false)
                    .apply(new RequestOptions().override(Target.SIZE_ORIGINAL))
                    .into(holder.postImageView);

        } else {
            holder.postImageView.setVisibility(View.GONE);
        }




        // show the file name if there is a file attachment
        if (post.getFileName() != null && !post.getFileName().isEmpty()) {
            holder.postFileNameTextView.setText(post.getFileName());
            holder.postFileNameTextView.setVisibility(View.VISIBLE);

            holder.postFileNameTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // create an Intent to open the file
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    Uri fileUri = Uri.parse(post.getFileUrl());

                    intent.setData(fileUri);
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    context.startActivity(intent);
                    Toast.makeText(context, "downloading file..", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            holder.postFileNameTextView.setVisibility(View.GONE);
        }

        holder.postUserTextView.setText("Posted by " + post.getUserId());

        // format the timestamp
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String formattedDate = dateFormat.format(post.getTimestamp());

        holder.postTimestampTextView.setText(formattedDate);


        String currentUserId = new SharedPrefUtils(context).getCurrentUser();
        if (post.getUserId().equals(currentUserId)) {
            holder.deleteButton.setVisibility(View.VISIBLE);
        } else {
            holder.deleteButton.setVisibility(View.GONE);
        }

        // set onClickListener for the delete button

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(holder.itemView.getContext(), holder.deleteButton);
                popupMenu.inflate(R.menu.post_option);
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        int id = menuItem.getItemId();
                        if (id == R.id.action_delete) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
                            builder.setTitle("Delete Post")
                                    .setMessage("Are you sure you want to delete this post?")
                                    .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // remove the post data from Firebase Realtime Database
                                            String classKey = new SharedPrefUtils(context).getClassKey();
                                            DatabaseReference postRef = FirebaseDatabase.getInstance().getReference("class/" + classKey + "/posts/" + post.getPostId());
                                            Toast.makeText(context, classKey+" "+post.getPostId(), Toast.LENGTH_SHORT).show();
                                            postRef.removeValue();
                                        }
                                    })
                                    .setNegativeButton("Cancel", null)
                                    .show();
                            return true;
                        }
                        return false;
                    }
                });
            }
        });
    }



    @Override
    public int getItemCount() {
        if (postList == null) {
            return 0;
        }
        return postList.size();
    }


    public static class PostViewHolder extends RecyclerView.ViewHolder {
        public TextView postTextView;
        public ImageView postImageView;
        public TextView postFileNameTextView;
        public TextView postUserTextView;
        public TextView postTimestampTextView;
        ImageButton deleteButton ;



        public PostViewHolder(@NonNull View itemView ) {
            super(itemView);
            postTextView = itemView.findViewById(R.id.post_text_view);
            postImageView = itemView.findViewById(R.id.post_image_view);
            postFileNameTextView = itemView.findViewById(R.id.post_file_name_text_view);
            postUserTextView = itemView.findViewById(R.id.post_user_text_view);
            postTimestampTextView = itemView.findViewById(R.id.post_timestamp_text_view);
            deleteButton=itemView.findViewById(R.id.options_button) ;




        }


    }


}
