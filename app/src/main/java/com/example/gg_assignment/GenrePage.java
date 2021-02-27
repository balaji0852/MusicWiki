package com.example.gg_assignment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.gg_assignment.GenreAdapter.GenreAdapter;
import com.example.gg_assignment.GenreAdapter.genreClass;
import com.example.gg_assignment.TabViewAdapter.TabViewAdapter;
import com.example.gg_assignment.TabViewAdapter.TopAlbums;
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

public class GenrePage extends AppCompatActivity {
    String summaryData;
    RecyclerView TabView;
    TabViewAdapter TVA;
    TextView summary,Album,Artists,Tracks,AlbumLine,TracksLine,ArtistsLine;
    Integer Page=1;
    ArrayList<TopAlbums> topAlbums = new ArrayList<>();
    ArrayList<TopAlbums> topArtists = new ArrayList<>();
    ArrayList<TopAlbums> topTracks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.genre_page);
        TextView title = findViewById(R.id.GenreTitle);
        summary = findViewById(R.id.GenreInfo);
        ImageButton BackButton = findViewById(R.id.BackButton);
        title.setText(getIntent().getStringExtra("GenreTitle"));

        TabView = findViewById(R.id.TabView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(GenrePage.this);
        ((LinearLayoutManager) layoutManager).setOrientation(RecyclerView.VERTICAL);
        TabView.setLayoutManager(layoutManager);
        TVA = new TabViewAdapter(GenrePage.this, topAlbums,"Albums",getIntent().getStringExtra("GenreTitle"));
        TabView.setAdapter(TVA);


        SummaryGetter();
        dataGetter();

        Album = findViewById(R.id.Albums);
        Artists = findViewById(R.id.Artists);
        Tracks = findViewById(R.id.Tracks);
        ArtistsLine = findViewById(R.id.ArtistsLine);
        TracksLine = findViewById(R.id.TracksLine);
        AlbumLine = findViewById(R.id.AlbumsLine);


        Album.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {

                if(Page!=1){
                    TVA = new TabViewAdapter(GenrePage.this, topAlbums,"Album",getIntent().getStringExtra("GenreTitle"));
                    TabView.setAdapter(TVA);
                    if(Page.equals(3)){
                        TracksLine.setBackgroundColor(getResources().getColor(R.color.white));
                    }
                    if(Page.equals(2)){
                        ArtistsLine.setBackgroundColor(getResources().getColor(R.color.white));
                    }
                    Page = 1;
                    AlbumLine.setBackgroundColor(getResources().getColor(R.color.black));
                }
            }
        });

        Artists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Page!=2){
                    TVA = new TabViewAdapter(GenrePage.this, topArtists,"Artists",getIntent().getStringExtra("GenreTitle"));
                    TabView.setAdapter(TVA);
                    if(Page.equals(1)){
                        AlbumLine.setBackgroundColor(getResources().getColor(R.color.white));
                    }
                    if(Page.equals(3)){
                        TracksLine.setBackgroundColor(getResources().getColor(R.color.white));
                    }
                    Page = 2;
                    ArtistsLine.setBackgroundColor(getResources().getColor(R.color.black));
                }
            }
        });

        Tracks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Page!=3){
                    TVA = new TabViewAdapter(GenrePage.this, topTracks,"Tracks",getIntent().getStringExtra("GenreTitle"));
                    TabView.setAdapter(TVA);
                    if(Page.equals(2)){
                        ArtistsLine.setBackgroundColor(getResources().getColor(R.color.white));
                    }
                    if(Page.equals(1)){
                        AlbumLine.setBackgroundColor(getResources().getColor(R.color.white));
                    }
                    Page = 3;
                    TracksLine.setBackgroundColor(getResources().getColor(R.color.black));
                }
            }
        });

        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GenrePage.this,MainActivity.class));
                finish();
            }
        });


    }

    public void dataGetter(){
        OkHttpClient client = new OkHttpClient();
        int i = 0;
        String[] urlData = {"http://ws.audioscrobbler.com/2.0/?method=tag.gettopalbums&tag="+getIntent().getStringExtra("GenreTitle")+"&api_key=2082c3e426f72cc7234dad4d153a23c7&format=json",
                            "http://ws.audioscrobbler.com/2.0/?method=tag.gettopartists&tag="+getIntent().getStringExtra("GenreTitle")+"&api_key=2082c3e426f72cc7234dad4d153a23c7&format=json",
                            "http://ws.audioscrobbler.com/2.0/?method=tag.gettoptracks&tag="+getIntent().getStringExtra("GenreTitle")+"&api_key=2082c3e426f72cc7234dad4d153a23c7&format=json"};
        for(i=0;i<3;i++) {
            Request request = new Request.Builder()
                    .url(urlData[i])
                    .build();
            final int finalI = i;
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    if (response.isSuccessful()) {
                                try {
                                        if(finalI==0){
                                            final JSONArray TopAlbum = new JSONObject(response.body().string()).getJSONObject("albums").
                                                    getJSONArray("album");
                                            System.out.println(TopAlbum.length());
                                            for (int j = 0; j < TopAlbum.length(); j=j+2) {
                                               topAlbums.add(new TopAlbums(j+1<TopAlbum.length()?new JSONObject(new JSONObject(TopAlbum.get(j+1).toString()).getJSONArray("image").get(3).toString()).get("#text").toString():"empty",
                                                       j+1<TopAlbum.length()?new JSONObject(TopAlbum.get(j+1).toString()).get("name").toString():"empty",
                                                       j+1<TopAlbum.length()?new JSONObject(TopAlbum.get(j+1).toString()).getJSONObject("artist").get("name").toString():"empty",
                                                       j+2<TopAlbum.length()?new JSONObject(new JSONObject(TopAlbum.get(j+2).toString()).getJSONArray("image").get(3).toString()).get("#text").toString():"empty",
                                                       j+2<TopAlbum.length()?new JSONObject(TopAlbum.get(j+2).toString()).get("name").toString():"empty",
                                                       j+2<TopAlbum.length()?new JSONObject(TopAlbum.get(j+2).toString()).getJSONObject("artist").get("name").toString():"empty"));
                                            }
                                        }
                                        else if(finalI==1){
                                            final JSONArray TopAlbum = new JSONObject(response.body().string()).getJSONObject("topartists").
                                                    getJSONArray("artist");
                                            for (int j = 0; j < TopAlbum.length(); j=j+2) {
                                                  topArtists.add(new TopAlbums(j+1<TopAlbum.length()?new JSONObject(new JSONObject(TopAlbum.get(j+1).toString()).getJSONArray("image").get(0).toString()).get("#text").toString():"empty",
                                                          "empty",
                                                               j+1<TopAlbum.length()?new JSONObject(TopAlbum.get(j+1).toString()).get("name").toString():"empty",
                                                               j+2<TopAlbum.length()?new JSONObject(new JSONObject(TopAlbum.get(j+2).toString()).getJSONArray("image").get(0).toString()).get("#text").toString():"empty",
                                                          "empty",
                                                                j+2<TopAlbum.length()?new JSONObject(TopAlbum.get(j+2).toString()).get("name").toString():"empty"));
                                            }
                                        }
                                        else{
                                            final JSONArray TopAlbum = new JSONObject(response.body().string()).getJSONObject("tracks").
                                                    getJSONArray("track");
                                            for (int j = 0; j < TopAlbum.length(); j=j+2) {
                                                topTracks.add(new TopAlbums(j+1<TopAlbum.length()?new JSONObject(new JSONObject(TopAlbum.get(j+1).toString()).getJSONArray("image").get(0).toString()).get("#text").toString():"empty",
                                                        j+1<TopAlbum.length()?new JSONObject(TopAlbum.get(j+1).toString()).get("name").toString():"empty",
                                                        j+1<TopAlbum.length()?new JSONObject(TopAlbum.get(j+1).toString()).getJSONObject("artist").get("name").toString():"empty",
                                                        j+2<TopAlbum.length()?new JSONObject(new JSONObject(TopAlbum.get(j+2).toString()).getJSONArray("image").get(0).toString()).get("#text").toString():"empty",
                                                        j+2<TopAlbum.length()?new JSONObject(TopAlbum.get(j+2).toString()).get("name").toString():"empty",
                                                        j+2<TopAlbum.length()?new JSONObject(TopAlbum.get(j+2).toString()).getJSONObject("artist").get("name").toString():"empty"));
                                            }
                                        }
                                    runOnUiThread(new Runnable() {
                                        @SuppressLint("SetTextI18n")
                                        @Override
                                        public void run() {
                                            TVA = new TabViewAdapter(GenrePage.this, topAlbums,"Albums",getIntent().getStringExtra("GenreTitle"));
                                            TabView.setAdapter(TVA);
                                        }
                                    });
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                    }
                }
            });
        }
    }


    public void SummaryGetter(){
        OkHttpClient client = new OkHttpClient();
        String url = "http://ws.audioscrobbler.com/2.0/?method=tag.getinfo&tag="+getIntent().getStringExtra("GenreTitle")+"&api_key=2082c3e426f72cc7234dad4d153a23c7&format=json";

            Request request = new Request.Builder()
                    .url(url)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }
                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    if (response.isSuccessful()) {
                        try {
                                summaryData = new JSONObject(response.body().string()).getJSONObject("tag").getJSONObject("wiki").get("summary").toString();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                summary.setText(summaryData);
                            }
                        });
                    }
                }
            });
    }



}