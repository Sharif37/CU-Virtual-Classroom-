package com.example.cuvc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Post_Activity extends AppCompatActivity {



    private ValueEventListener postListener;

    private ProgressBar spinner;
    RecyclerView recyclerView ;
    private PostAdapter postAdapter;
    private PostViewModel postViewModel;

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PICK_FILE_REQUEST = 2;
    SharedPreferences preferences ;
    private EditText postText;
    private TextView postImage;
    private TextView postFile;
    private Button selectImageButton;
    private Button selectFileButton;
    private Button submitButton;

    private Uri imageUri;
    private Uri fileUri;
    List<Post> postList  ;

    private FirebaseStorage storage;
    private StorageReference storageRef;
    private DatabaseReference databaseReference ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        postText = findViewById(R.id.post_text);
        postImage = findViewById(R.id.post_img_name);
        postFile = findViewById(R.id.post_file_name);
        selectImageButton = findViewById(R.id.select_image_button);
        selectFileButton = findViewById(R.id.select_file_button);
        submitButton = findViewById(R.id.submit_button);
        spinner=findViewById(R.id.spinner_post_activity);



        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("posts");
        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);





        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

        selectFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(intent, PICK_FILE_REQUEST);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner.setVisibility(View.VISIBLE);
                createPost();
                spinner.setVisibility(View.GONE);
            }
        });



    }


    private void createPost() {
        // get the post text
        String text = postText.getText().toString();

        // upload the image and file attachments (if any) to Firebase Storage and get their download URLs
        final String[] imageUrl = {null};
        final String[] fileUrl = {null};

        if (imageUri != null) {
            uploadImage(imageUri, new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    imageUrl[0] = uri.toString();
                    savePostToDatabase(text, imageUrl[0], fileUrl[0]);
                }
            });
        } else if (fileUri != null) {
            uploadFile(fileUri, new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    fileUrl[0] = uri.toString();
                    savePostToDatabase(text, imageUrl[0], fileUrl[0]);
                }
            });
        } else {
            savePostToDatabase(text, imageUrl[0], fileUrl[0]);
        }
    }

    private void uploadImage(Uri imageUri, OnSuccessListener<Uri> onSuccessListener) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("posts").child(getCurrentUserId()).child("images").child(UUID.randomUUID().toString());
        storageReference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        storageReference.getDownloadUrl().addOnSuccessListener(onSuccessListener);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Post_Activity.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void uploadFile(Uri fileUri, OnSuccessListener<Uri> onSuccessListener) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("posts").child(getCurrentUserId()).child("files").child(UUID.randomUUID().toString());
        storageReference.putFile(fileUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        storageReference.getDownloadUrl().addOnSuccessListener(onSuccessListener);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Post_Activity.this, "Failed to upload file", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void savePostToDatabase(String text, String imageUrl, String fileUrl) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("posts").push();
        String postId = databaseReference.getKey();
        String userId = getCurrentUserId();
        long timestamp = System.currentTimeMillis();

        Post post = new Post(postId, text, imageUrl, fileUrl, userId, timestamp,fileUrl);
        databaseReference.setValue(post)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(Post_Activity.this, "Post created successfully", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(Post_Activity.this, classroomActivity.class);
                        intent.putParcelableArrayListExtra("posts", new ArrayList<Post>(postViewModel.getPosts().getValue()));

                        startActivity(intent);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Post_Activity.this, "Failed to create post", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private String getCurrentUserId() {
        preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String   currentUser = preferences.getString("currentUser", "");
        return currentUser ;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            String imageName = getFileName(imageUri);
            postImage.setText(imageName);


        }
        if (requestCode == PICK_FILE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            fileUri = data.getData();
            String fileName = getFileName(fileUri);
            postFile.setText(fileName);

        }
    }

    @SuppressLint("Range")
    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public ProgressBar getSpinner() {
        return spinner;
    }




}

