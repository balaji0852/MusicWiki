package com.example.gg_assignment;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.gg_assignment.GenreAdapter.GenreAdapter;
import com.example.gg_assignment.GenreAdapter.genreClass;
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

public class MainActivity extends AppCompatActivity {

    RecyclerView genresList;
    GenreAdapter genreAdapter;
    ArrayList<genreClass> genres = new ArrayList<genreClass>();
    ArrayList<genreClass> genresFullList = new ArrayList<genreClass>();
    Boolean extended = false;


    @Override
    protected void onStart() {
        super.onStart();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataGetter();
        setContentView(R.layout.activity_main);
        final ImageButton extend = findViewById(R.id.extendButton);


        genresList = findViewById(R.id.genres);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        ((LinearLayoutManager) layoutManager).setOrientation(RecyclerView.VERTICAL);
        genresList.setLayoutManager(layoutManager);
        genreAdapter = new GenreAdapter(MainActivity.this, genres);
        genresList.setAdapter(genreAdapter);
        System.out.println("start1");




        extend.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if(!extended){
                    extended = true;
                    genreAdapter = new GenreAdapter(MainActivity.this, genresFullList);
                    genresList.setAdapter(genreAdapter);
                    extend.setImageDrawable(getDrawable(R.drawable.ic_baseline_keyboard_arrow_down_24));
                }
                else{
                    extended = false;
                    genreAdapter = new GenreAdapter(MainActivity.this, genres);
                    genresList.setAdapter(genreAdapter);
                    extend.setImageDrawable(getDrawable(R.drawable.ic_baseline_keyboard_arrow_up_24));
                }
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
                        for ( i = 0; i < data.length(); i = i + 3) {
                            genresFullList.add(new genreClass(i<data.length()? new JSONObject(data.get(i).toString()).get("name").toString():"empty",
                                                i+1<data.length()?new JSONObject(data.get(i+1).toString()).get("name").toString():"empty",
                                                i+2<data.length()?new JSONObject(data.get(i+2).toString()).get("name").toString():"empty"));
                            genres.add(new genreClass(i<=9?new JSONObject(data.get(i).toString()).get("name").toString():"empty",
                                    i+1<=9?new JSONObject(data.get(i+1).toString()).get("name").toString():"empty",
                                    i+2<=9?new JSONObject(data.get(i+2).toString()).get("name").toString():"empty"));
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
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







}