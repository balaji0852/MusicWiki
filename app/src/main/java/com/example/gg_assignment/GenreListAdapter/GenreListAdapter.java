package com.example.gg_assignment.GenreListAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gg_assignment.GenrePage;
import com.example.gg_assignment.R;
import java.util.ArrayList;

public class GenreListAdapter extends RecyclerView.Adapter<GenreListAdapter.MyViewHolder>{

    Context context;
    ArrayList<String> genres;



    public GenreListAdapter(Context context, ArrayList<String> genres) {
        this.context = context;
        this.genres = genres;
    }


    @NonNull
    @Override
    public GenreListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        @SuppressLint("ResourceType") View view = inflater.inflate(R.layout.genre_buttons, viewGroup, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final String data=  genres.get(position);
        holder.Genre.setText(data);

        holder.Genre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GenrePage = new Intent(v.getContext(), GenrePage.class);
                GenrePage.putExtra("GenreTitle",data);
                v.getContext().startActivity(GenrePage);
            }
        });

    }





    @Override
    public int getItemCount() {
        return genres.size();
    }






    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        Button Genre;
        public MyViewHolder(@NonNull final View itemView)
        {
            super(itemView);
            Genre = itemView.findViewById(R.id.Genre);
        }
    }




}
