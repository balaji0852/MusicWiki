package com.example.gg_assignment.GenreAdapter;
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

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.MyViewHolder>{

    Context context;
    ArrayList<genreClass> genres;



    public GenreAdapter(Context context, ArrayList<genreClass> genres) {
        this.context = context;
        this.genres = genres;
    }


    @NonNull
    @Override
    public GenreAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.genres, viewGroup, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final genreClass data=  genres.get(position);

        holder.genre1.setText(data.getGenre1());
        if(data.getGenre1().equals("empty")){
            holder.genre1.setVisibility(View.INVISIBLE);
        }

        holder.genre2.setText(data.getGenre2());
        if(data.getGenre2().equals("empty")){
            holder.genre2.setVisibility(View.INVISIBLE);
        }

        holder.genre3.setText(data.getGenre3());
        if(data.getGenre3().equals("empty")){
            holder.genre3.setVisibility(View.INVISIBLE);
        }

        holder.genre1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!data.genre1.equals("empty")){
                    Intent GenrePage = new Intent(v.getContext(), GenrePage.class);
                    GenrePage.putExtra("GenreTitle",data.genre1);
                    v.getContext().startActivity(GenrePage);
                }
            }
        });


        holder.genre2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!data.genre1.equals("empty")){
                    Intent GenrePage = new Intent(v.getContext(), GenrePage.class);
                    GenrePage.putExtra("GenreTitle",data.genre2);
                    v.getContext().startActivity(GenrePage);
                }
            }
        });


        holder.genre3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!data.genre1.equals("empty")){
                    Intent GenrePage = new Intent(v.getContext(), GenrePage.class);
                    GenrePage.putExtra("GenreTitle",data.genre3);
                    v.getContext().startActivity(GenrePage);
                }
            }
        });
    }





    @Override
    public int getItemCount() {
        return genres.size();
    }






    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        Button genre1,genre2,genre3;
        public MyViewHolder(@NonNull final View itemView)
        {
            super(itemView);
            genre1 = itemView.findViewById(R.id.tag1);
            genre2 = itemView.findViewById(R.id.tag2);
            genre3 = itemView.findViewById(R.id.tag3);

        }
    }




}
