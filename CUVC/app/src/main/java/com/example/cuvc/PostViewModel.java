package  com.example.cuvc ;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PostViewModel extends ViewModel {

    private MutableLiveData<List<Post>> mPosts;




    public LiveData<List<Post>> getPosts() {
        if (mPosts == null) {
            mPosts = new MutableLiveData<>();
            loadPosts();
        }
        return mPosts;
    }





    private void loadPosts() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("posts");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Post> posts = new ArrayList<>();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Post post = postSnapshot.getValue(Post.class);
                    posts.add(post);
                }
                mPosts.postValue(posts);
                mPosts.setValue(posts);
            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("PostViewModel", "Error retrieving posts", error.toException());
            }
        });
    }
}
