package com.example.gg_assignment.TabViewAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gg_assignment.AlbumPage;
import com.example.gg_assignment.GenreAdapter.genreClass;
import com.example.gg_assignment.GenrePage;
import com.example.gg_assignment.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class TabViewAdapter  extends RecyclerView.Adapter<TabViewAdapter.MyViewHolder>{

    Context context;
    ArrayList<TopAlbums> data;
    String Type;
    String Genre;
    String ErrorImage = "https://lastfm.freetls.fastly.net/i/u/174s/2a96cbd8b46e442fc41c2b86b821562f.png";


    public TabViewAdapter(Context context, ArrayList<TopAlbums> data,String Type,String Genre) {
        this.context = context;
        this.data = data;
        this.Type = Type;
        this.Genre = Genre;
    }


    @NonNull
    @Override
    public TabViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.tab_view_content, viewGroup, false);
        return new MyViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final TopAlbums AlbumSet = data.get(position);



        if(Type=="Artists"||Type=="Tracks"){
            holder.TV_right.setTextColor(R.color.black);
            holder.TV_left.setTextColor(R.color.black);
            holder.Artist_R.setTextColor(R.color.black);
            holder.Artist_L.setTextColor(R.color.black);
            if(Type=="Artists"){
                holder.TV_left.setText(AlbumSet.getArtists1().equals("empty")?" ":AlbumSet.getArtists1());
                holder.TV_right.setText(AlbumSet.getArtists2().equals("empty")?" ":AlbumSet.getArtists2());
            }
            else{
                holder.TV_left.setText(AlbumSet.getTitle1().equals("empty")?" ":AlbumSet.getTitle1());
                holder.TV_right.setText(AlbumSet.getTitle2().equals("empty")?" ":AlbumSet.getTitle2());
                holder.Artist_L.setText(AlbumSet.getArtists1().equals("empty")?" ":AlbumSet.getArtists1());
                holder.Artist_R.setText(AlbumSet.getArtists2().equals("empty")?" ":AlbumSet.getArtists2());
            }
        }
        else{
            holder.TV_left.setText(AlbumSet.getTitle1().equals("empty")?" ":AlbumSet.getTitle1());
            holder.TV_right.setText(AlbumSet.getTitle2().equals("empty")?" ":AlbumSet.getTitle2());
            holder.Artist_L.setText(AlbumSet.getArtists1().equals("empty")?" ":AlbumSet.getArtists1());
            holder.Artist_R.setText(AlbumSet.getArtists2().equals("empty")?" ":AlbumSet.getArtists2());
        }

        if(!AlbumSet.ImgUrl1.equals("empty")) {
            try {
                Picasso.get().load(AlbumSet.ImgUrl1).placeholder(R.mipmap.ic_launcher).fit().centerCrop().into(holder.ImgL);
            }
            catch (Exception e){
                Picasso.get().load(ErrorImage).placeholder(R.mipmap.ic_launcher).fit().centerCrop().into(holder.ImgL);
            }
        }
        if(!AlbumSet.ImgUrl2.equals("empty")) {
            try {
                Picasso.get().load(AlbumSet.ImgUrl2).placeholder(R.mipmap.ic_launcher).fit().centerCrop().into(holder.ImgR);
            }
            catch (Exception e){
                Picasso.get().load(ErrorImage).placeholder(R.mipmap.ic_launcher).fit().centerCrop().into(holder.ImgR);
            }
        }


        holder.ImgL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!AlbumSet.ImgUrl1.equals("empty") && Type.equals("Albums")){
                    Intent AlbumPage = new Intent(v.getContext(), com.example.gg_assignment.AlbumPage.class);
                    AlbumPage.putExtra("ImgUrl",AlbumSet.ImgUrl1);
                    AlbumPage.putExtra("Artist",AlbumSet.getArtists1());
                    AlbumPage.putExtra("Album",AlbumSet.getTitle1());
                    AlbumPage.putExtra("GenreTitle",Genre);
                    v.getContext().startActivity(AlbumPage);
                }

                if(!AlbumSet.ImgUrl1.equals("empty") && Type.equals("Artists")){
                    Intent ArtistPage = new Intent(v.getContext(), com.example.gg_assignment.ArtistsPage.class);
                    ArtistPage.putExtra("Artist",AlbumSet.getArtists1());
                    ArtistPage.putExtra("ImgUrl",AlbumSet.getImgUrl1());
                    ArtistPage.putExtra("GenreTitle",Genre);
                    v.getContext().startActivity(ArtistPage);
                }


            }
        });

        holder.ImgR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!AlbumSet.ImgUrl2.equals("empty") && Type.equals("Albums")){
                    Intent AlbumPage = new Intent(v.getContext(), com.example.gg_assignment.AlbumPage.class);
                    AlbumPage.putExtra("ImgUrl",AlbumSet.ImgUrl2);
                    AlbumPage.putExtra("Artist",AlbumSet.getArtists2());
                    AlbumPage.putExtra("Album",AlbumSet.getTitle2());
                    AlbumPage.putExtra("GenreTitle",Genre);
                    v.getContext().startActivity(AlbumPage);
                }
                if(!AlbumSet.ImgUrl2.equals("empty") && Type.equals("Artists")){
                    Intent ArtistPage = new Intent(v.getContext(), com.example.gg_assignment.ArtistsPage.class);
                    ArtistPage.putExtra("Artist",AlbumSet.getArtists2());
                    ArtistPage.putExtra("ImgUrl",AlbumSet.getImgUrl2());
                    ArtistPage.putExtra("GenreTitle",Genre);
                    v.getContext().startActivity(ArtistPage);
                }
            }
        });




    }







    @Override
    public int getItemCount() {
        return data.size();
    }






    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView ImgR,ImgL;
        TextView TV_right,TV_left,Artist_L,Artist_R;
        public MyViewHolder(@NonNull final View itemView)
        {
            super(itemView);
            Artist_L = itemView.findViewById(R.id.ArtistsL);
            Artist_R = itemView.findViewById(R.id.ArtistsR);
            ImgR = itemView.findViewById(R.id.ImageR);
            ImgL = itemView.findViewById(R.id.ImageL);
            TV_left = itemView.findViewById(R.id.titleL);
            TV_right = itemView.findViewById(R.id.titleR);

        }
    }




}
