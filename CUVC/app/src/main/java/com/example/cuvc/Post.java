package com.example.cuvc;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Post implements Parcelable {
    private String text;
    private String imageUrl;
    private String fileUrl;
    private String userId;
    private String postId;
    private long timestamp;
    private String fileName;
    private String imgFileName ;

    public Post() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }


    public Post(String postId,String text, String imageUrl, String fileUrl, String userId, long timestamp, String fileName) {
        this.postId=postId ;
        this.text = text;
        this.imageUrl = imageUrl;
        this.fileUrl = fileUrl;
        this.userId = userId;
        this.timestamp = timestamp;
        this.fileName=fileName ;

    }

    public Post(String postId, String text, String imageUrl, String fileUrl, String userId, long timestamp) {
        this.postId=postId ;
        this.text = text;
        this.imageUrl = imageUrl;
        this.fileUrl = fileUrl;
        this.userId = userId;
        this.timestamp = timestamp;
    }


    protected Post(Parcel in) {
        text = in.readString();
        imageUrl = in.readString();
        fileUrl = in.readString();
        userId = in.readString();
        postId = in.readString();
        timestamp = in.readLong();
        fileName = in.readString();
        imgFileName = in.readString();
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getImgFileName() {
        return imgFileName;
    }

    public void setImgFileName(String imgFileName) {
        this.imgFileName = imgFileName;
    }

    public  String  getPostId() {
        return postId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(text);
        dest.writeString(imageUrl);
        dest.writeString(fileUrl);
        dest.writeString(userId);
        dest.writeString(postId);
        dest.writeLong(timestamp);
        dest.writeString(fileName);
        dest.writeString(imgFileName);
    }

    private String fileType;

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
