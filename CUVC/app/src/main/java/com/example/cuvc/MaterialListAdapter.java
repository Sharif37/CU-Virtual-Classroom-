package com.example.cuvc;

import static android.app.PendingIntent.getActivity;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;



public class MaterialListAdapter extends RecyclerView.Adapter<MaterialListAdapter.MaterialViewHolder> {

    private List<ClassMaterial> materialList;

    public MaterialListAdapter(List<ClassMaterial> materialList) {
        this.materialList = materialList;
    }

    @NonNull
    @Override
    public MaterialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_resource_item, parent, false);
        return new MaterialViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MaterialViewHolder holder, int position) {
        final ClassMaterial currentMaterial = materialList.get(position);
        holder.materialNameTextView.setText(currentMaterial.getName());

        // Get metadata for the file
        StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(currentMaterial.getUrl());
        storageRef.getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
            @Override
            public void onSuccess(StorageMetadata storageMetadata) {
                // Set date and time to TextViews
                Date creationTime = new Date(storageMetadata.getCreationTimeMillis());
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy", Locale.getDefault());
                holder.dateTextView.setText(dateFormat.format(creationTime));
                SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a", Locale.getDefault());
                holder.timeTextView.setText(timeFormat.format(creationTime));
            }
        });

        holder.downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadFile(currentMaterial.getUrl(), currentMaterial.getName(), view.getContext());
            }
        });
    }

    @Override
    public int getItemCount() {
        return materialList.size();
    }

    static class MaterialViewHolder extends RecyclerView.ViewHolder {

        TextView materialNameTextView;
        ImageButton downloadButton;
        TextView dateTextView ;
        TextView timeTextView ;

        public MaterialViewHolder(View itemView) {
            super(itemView);
            materialNameTextView = itemView.findViewById(R.id.file_name_textview);
            downloadButton = itemView.findViewById(R.id.download_button);
            dateTextView = itemView.findViewById(R.id.date_textview);
            timeTextView = itemView.findViewById(R.id.time_textview);
        }
    }

    private void downloadFile(String url, String fileName, Context context) {
        StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(url);
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                downloadManager.enqueue(request);
                Toast.makeText(context, "Download started", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(context, "Download failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
