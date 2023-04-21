package com.example.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {

    private ArrayList<String> name ;
    private ArrayList<Integer>img ;
    private Context context ;

    public RecycleViewAdapter(ArrayList<String> name, ArrayList<Integer> img, Context context) {
        this.name = name;
        this.img = img;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
     holder.text.setText(name.get(position));
     holder.img.setImageResource(img.get(position));
     holder.cardView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             if(position==0)
                 Toast.makeText(context, "nature1", Toast.LENGTH_SHORT).show();
             if(position==1)
                 Toast.makeText(context, "nature2", Toast.LENGTH_SHORT).show();
             if(position==2)
                 Toast.makeText(context, "nature3", Toast.LENGTH_SHORT).show();
             //Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
         }
     });
    }

    @Override
    public int getItemCount() {
        return name.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView text ;
        ImageView img ;
        CardView cardView ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text=itemView.findViewById(R.id.text);
            img=itemView.findViewById(R.id.imageView);
             cardView=itemView.findViewById(R.id.cardview);
        }
    }


}
