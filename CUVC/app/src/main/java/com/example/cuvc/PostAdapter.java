package com.example.cuvc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<Post> postList;
    private Context context;

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

        // load the image with Glide
        if (post.getImageUrl() != null && !post.getImageUrl().isEmpty()) {
            Glide.with(context).load(post.getImageUrl()).into(holder.postImageView);
        } else {
            holder.postImageView.setVisibility(View.GONE);
        }

        // show the file name if there is a file attachment
        if (post.getFileName() != null && !post.getFileName().isEmpty()) {
            holder.postFileNameTextView.setText(post.getFileName());
            holder.postFileNameTextView.setVisibility(View.VISIBLE);
        } else {
            holder.postFileNameTextView.setVisibility(View.GONE);
        }

        holder.postUserTextView.setText("Posted by " + post.getUserId());

        // format the timestamp
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String formattedDate = dateFormat.format(post.getTimestamp());

        holder.postTimestampTextView.setText(formattedDate);
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

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            postTextView = itemView.findViewById(R.id.post_text_view);
            postImageView = itemView.findViewById(R.id.post_image_view);
            postFileNameTextView = itemView.findViewById(R.id.post_file_name_text_view);
            postUserTextView = itemView.findViewById(R.id.post_user_text_view);
            postTimestampTextView = itemView.findViewById(R.id.post_timestamp_text_view);
        }
    }
}
