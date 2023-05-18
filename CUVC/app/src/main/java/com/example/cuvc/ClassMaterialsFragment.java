package com.example.cuvc;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class ClassMaterialsFragment extends Fragment {

    private static final int REQUEST_CODE_FILE_PICKER = 1;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private RecyclerView recyclerView;
    private MaterialListAdapter mAdapter;
    private List<ClassMaterial> fileList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Firebase Storage
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference().child("class_materials");

        // Set up RecyclerView
        mAdapter = new MaterialListAdapter(fileList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_class_materials, container, false);

        recyclerView = rootView.findViewById(R.id.materialsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open file picker
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(intent, REQUEST_CODE_FILE_PICKER);
            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        // Load list of class materials from Firebase Storage
        storageRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                fileList.clear();
                for (StorageReference item : listResult.getItems()) {
                    item.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String fileName = item.getName();
                            String url = uri.toString();
                            fileList.add(new ClassMaterial(fileName, url));
                            mAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getContext(), "Failed to load class materials", Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_FILE_PICKER && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri fileUri = data.getData();

            // Get file name and extension
            String fileName = getFileName(fileUri);
            String fileExtension = getFileExtension(fileName);

            // Create new file name using current timestamp
           // String newFileName = System.currentTimeMillis() + "." + fileExtension;
            String downloadUrl = fileUri.toString();
            // Upload file to Firebase Storage
            StorageReference fileRef = storageRef.child(fileName);
            UploadTask uploadTask = fileRef.putFile(fileUri);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // Create a new ClassMaterial object for the uploaded file

                    ClassMaterial classMaterial = new ClassMaterial(fileName, downloadUrl);

                    // Add the new ClassMaterial object to the list
                    fileList.add(classMaterial);
                    Toast.makeText(getContext(), "File upload successfully", Toast.LENGTH_SHORT).show();
                    // Notify the adapter that the data set has changed
                    mAdapter.notifyDataSetChanged();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), "File upload failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    @SuppressLint("Range")
    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null);
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

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex == -1) {
            return "";
        } else {
            return fileName.substring(dotIndex + 1);
        }
    }


}
