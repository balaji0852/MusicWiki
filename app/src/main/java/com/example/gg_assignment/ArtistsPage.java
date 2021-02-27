package com.example.gg_assignment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.gg_assignment.GenreAdapter.GenreAdapter;
import com.example.gg_assignment.GenreListAdapter.GenreListAdapter;
import com.example.gg_assignment.TabViewAdapter.TabViewAdapter;
import com.example.gg_assignment.TabViewAdapter.TopAlbums;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ArtistsPage extends AppCompatActivity {

    TextView PlayCount,Followers,ArtistTitle,ArtistSummary;
    ImageView Artist;
    RecyclerView TopTracks,TopAlbums,GenreList;
    ArrayList<String> GenreListData = new ArrayList<>();
    GenreListAdapter GenreAdapter;
    String pc,f,ArtistSummaryData;
    int i;
    ArrayList<TopAlbums> TopTracksData = new ArrayList<>();
    ArrayList<TopAlbums> TopAlbumsData = new ArrayList<>();
    TabViewAdapter TopTracksAdapter,TopAlbumAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.artists_page);

        ImageButton BackButton  = findViewById(R.id.BackButton);
        Artist = findViewById(R.id.ArtistImage);
        PlayCount = findViewById(R.id.PlayCount);
        Followers = findViewById(R.id.Followers);
        ArtistTitle = findViewById(R.id.AlbumArtist);
        ArtistSummary = findViewById(R.id.ArtistDescription);
        TopTracks = findViewById(R.id.TopTracks);
        GenreList = findViewById(R.id.GenresList);
        TopAlbums = findViewById(R.id.TopAlbums);

        Picasso.get().load(getIntent().getStringExtra("ImgUrl")).placeholder(R.mipmap.ic_launcher).fit().centerCrop().into(Artist);
        ArtistTitle.setText(getIntent().getStringExtra("Artist"));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ArtistsPage.this);
        ((LinearLayoutManager) layoutManager).setOrientation(RecyclerView.HORIZONTAL);

        GenreList.setLayoutManager(layoutManager);
        GenreAdapter = new GenreListAdapter(ArtistsPage.this, GenreListData);
        GenreList.setAdapter(GenreAdapter);

        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(ArtistsPage.this);
        ((LinearLayoutManager) layoutManager2).setOrientation(RecyclerView.HORIZONTAL);
        TopTracks.setLayoutManager(layoutManager2);
        TopTracksAdapter = new TabViewAdapter(ArtistsPage.this, TopTracksData,"Tracks",getIntent().getStringExtra("GenreTitle"));
        TopTracks.setAdapter(TopTracksAdapter);

        RecyclerView.LayoutManager layoutManager3 = new LinearLayoutManager(ArtistsPage.this);
        ((LinearLayoutManager) layoutManager3).setOrientation(RecyclerView.HORIZONTAL);
        TopAlbums.setLayoutManager(layoutManager3);
        TopAlbumAdapter = new TabViewAdapter(ArtistsPage.this, TopAlbumsData,"Albums",getIntent().getStringExtra("GenreTitle"));
        TopAlbums.setAdapter(TopAlbumAdapter);


        dataGetter();
        ArtistDataGetter();

        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent BackPage = new Intent(ArtistsPage.this,GenrePage.class);
                BackPage.putExtra("GenreTitle",getIntent().getStringExtra("GenreTitle"));
                startActivity(BackPage);
                finish();
            }
        });




    }

    public void dataGetter(){
        OkHttpClient client = new OkHttpClient();
        String url = "http://ws.audioscrobbler.com/2.0/?method=tag.getTopTags&api_key=2082c3e426f72cc7234dad4d153a23c7&format=json";
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {

                        int i;
                        JSONArray data = new JSONObject(response.body().string()).getJSONObject("toptags").getJSONArray("tag");
                        for ( i = 0; i < data.length(); i++) {
                            GenreListData.add(new JSONObject(data.get(i).toString()).get("name").toString());
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                GenreAdapter.notifyDataSetChanged();
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    public void ArtistDataGetter(){
        OkHttpClient client = new OkHttpClient();
        String[] url = {"http://ws.audioscrobbler.com/2.0/?method=artist.getinfo&artist="+getIntent().getStringExtra("Artist")+"&api_key=2082c3e426f72cc7234dad4d153a23c7&format=json",
                        "http://ws.audioscrobbler.com/2.0/?method=artist.gettoptracks&artist="+getIntent().getStringExtra("Artist")+"&api_key=2082c3e426f72cc7234dad4d153a23c7&format=json",
                        "http://ws.audioscrobbler.com/2.0/?method=artist.gettopalbums&artist="+getIntent().getStringExtra("Artist")+"&api_key=2082c3e426f72cc7234dad4d153a23c7&format=json"};
        for(i=0;i<3;i++) {
            Request request = new Request.Builder()
                    .url(url[i])
                    .build();
            final int finalI = i;
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        try {
                            if(finalI==0){
                                JSONObject ArtistDetails = new JSONObject(response.body().string()).getJSONObject("artist");
                                f = ArtistDetails.getJSONObject("stats").get("listeners").toString();
                                pc = ArtistDetails.getJSONObject("stats").get("playcount").toString();
                                ArtistSummaryData = ArtistDetails.getJSONObject("bio").get("summary").toString();
                            }
                            else if(finalI==1){
                                JSONArray TopAlbum = new JSONObject(response.body().string()).getJSONObject("toptracks").getJSONArray("track");
                                for (int j = 0; j < TopAlbum.length(); j=j+2) {

                                    TopTracksData.add(new TopAlbums(j+1<TopAlbum.length()?new JSONObject(new JSONObject(TopAlbum.get(j+1).toString()).getJSONArray("image").get(3).toString()).get("#text").toString():"empty",
                                            j+1<TopAlbum.length()?new JSONObject(TopAlbum.get(j+1).toString()).get("name").toString():"empty",
                                            j+1<TopAlbum.length()?getIntent().getStringExtra("Artist"):"empty",
                                            j+2<TopAlbum.length()?new JSONObject(new JSONObject(TopAlbum.get(j+2).toString()).getJSONArray("image").get(3).toString()).get("#text").toString():"empty",
                                            j+2<TopAlbum.length()?new JSONObject(TopAlbum.get(j+2).toString()).get("name").toString():"empty",
                                            j+2<TopAlbum.length()?getIntent().getStringExtra("Artist"):"empty"));

                                }

                            }
                            else{
                                JSONArray TopAlbum = new JSONObject(response.body().string()).getJSONObject("topalbums").getJSONArray("album");
                                for (int j = 0; j < TopAlbum.length(); j=j+2) {
                                    TopAlbumsData.add(new TopAlbums(j+1<TopAlbum.length()?new JSONObject(new JSONObject(TopAlbum.get(j+1).toString()).getJSONArray("image").get(3).toString()).get("#text").toString():"empty",
                                            j+1<TopAlbum.length()?new JSONObject(TopAlbum.get(j+1).toString()).get("name").toString():"empty",
                                            j+1<TopAlbum.length()?getIntent().getStringExtra("Artist"):"empty",
                                            j+2<TopAlbum.length()?new JSONObject(new JSONObject(TopAlbum.get(j+2).toString()).getJSONArray("image").get(3).toString()).get("#text").toString():"empty",
                                            j+2<TopAlbum.length()?new JSONObject(TopAlbum.get(j+2).toString()).get("name").toString():"empty",
                                            j+2<TopAlbum.length()?getIntent().getStringExtra("Artist"):"empty"));
                                }


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(finalI==0) {
                                    PlayCount.setText(pc);
                                    Followers.setText(f);
                                    ArtistSummary.setText(ArtistSummaryData);
                                }
                                else if(finalI==1){
                                    TopTracksAdapter.notifyDataSetChanged();
                                }
                                else{
                                    TopAlbumAdapter.notifyDataSetChanged();
                                }
                            }
                        });
                    }
                }
            });
        }
    }
}