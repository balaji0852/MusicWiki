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
import com.example.gg_assignment.GenreAdapter.genreClass;
import com.example.gg_assignment.GenreListAdapter.GenreListAdapter;
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


public class AlbumPage extends AppCompatActivity {

    RecyclerView GenreList;
    ArrayList<String> GenreListData = new ArrayList<>();
    GenreListAdapter genreAdapter;
    TextView AlbumDescription;
    String data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album_page);

        ImageView AlbumImage = findViewById(R.id.AlbumImage);
        ImageButton Back = findViewById(R.id.BackButton);
        TextView AlbumTitle = findViewById(R.id.AlbumTitle);
        TextView AlbumArtists = findViewById(R.id.AlbumArtist);
        AlbumDescription = findViewById(R.id.AlbumDescription);
        GenreList = findViewById(R.id.GenresList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(AlbumPage.this);
        ((LinearLayoutManager) layoutManager).setOrientation(RecyclerView.HORIZONTAL);
        GenreList.setLayoutManager(layoutManager);
        genreAdapter = new GenreListAdapter(AlbumPage.this, GenreListData);
        GenreList.setAdapter(genreAdapter);
        dataGetter();
        SummaryGetter();

        Picasso.get().load(getIntent().getStringExtra("ImgUrl")).placeholder(R.mipmap.ic_launcher).fit().centerCrop().into(AlbumImage);
        AlbumTitle.setText(getIntent().getStringExtra("Album"));
        AlbumArtists.setText(getIntent().getStringExtra("Artist"));

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent BackPage = new Intent(AlbumPage.this,GenrePage.class);
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
                                System.out.println(GenreListData.toString());

                                genreAdapter.notifyDataSetChanged();
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void SummaryGetter(){
        OkHttpClient client = new OkHttpClient();
        String url = "http://ws.audioscrobbler.com/2.0/?method=album.getinfo&api_key=2082c3e426f72cc7234dad4d153a23c7&artist="+getIntent().getStringExtra("Artist")+"&album="+getIntent().getStringExtra("Album")+"&format=json";
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
                        data = new JSONObject(response.body().string()).getJSONObject("album").getJSONObject("wiki").get("summary").toString();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AlbumDescription.setText(data);
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