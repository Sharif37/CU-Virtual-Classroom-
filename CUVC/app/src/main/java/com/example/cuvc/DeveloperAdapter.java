package com.example.cuvc;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DeveloperAdapter extends RecyclerView.Adapter<DeveloperAdapter.DeveloperViewHolder> {
    private List<Developer> developerList;

    public DeveloperAdapter(List<Developer> developerList) {
        this.developerList = developerList;
    }

    @NonNull
    @Override
    public DeveloperViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.aboutus_item, parent, false);
        return new DeveloperViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeveloperViewHolder holder, int position) {
        Developer developer = developerList.get(position);
        holder.bind(developer);
    }

    @Override
    public int getItemCount() {
        return developerList.size();
    }

    public class DeveloperViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView developerImageView;
        private TextView developerNameTextView;
        private TextView developerDescriptionTextView;
        private ImageButton twitterImageButton;
        private ImageButton linkedinImageButton;

        public DeveloperViewHolder(@NonNull View itemView) {
            super(itemView);

            developerImageView = itemView.findViewById(R.id.developerImageView);
            developerNameTextView = itemView.findViewById(R.id.developerNameTextView);
            developerDescriptionTextView = itemView.findViewById(R.id.developerDescriptionTextView);
            twitterImageButton = itemView.findViewById(R.id.twitterImageButton);
            linkedinImageButton = itemView.findViewById(R.id.linkedinImageButton);

            twitterImageButton.setOnClickListener(this);
            linkedinImageButton.setOnClickListener(this);
        }

        public void bind(Developer developer) {
            developerImageView.setImageResource(developer.getImageResId());
            developerNameTextView.setText(developer.getName());
            developerDescriptionTextView.setText(developer.getDescription());
        }

        @Override
        public void onClick(View view) {
            Developer developer = developerList.get(getAdapterPosition());

            switch (view.getId()) {
                case R.id.twitterImageButton:
                    openLink(developer.getTwitterLink());
                    break;
                case R.id.linkedinImageButton:
                    openLink(developer.getLinkedinLink());
                    break;
            }
        }

        private void openLink(String url) {
            if (url != null && !url.isEmpty()) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                itemView.getContext().startActivity(intent);
            }
        }
    }
}

