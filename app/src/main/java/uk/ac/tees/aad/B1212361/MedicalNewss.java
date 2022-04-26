package uk.ac.tees.aad.B1212361;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.io.IOException;

public class MedicalNewss extends AppCompatActivity {


    ListView listViewforNews;
    String mainTitle[] ;
    String descriptionText[];
    String imagesOfNews[];
    String newsContent[];
    AdapterForNews adapter;


    OkHttpClient okHttpclient;

    public static int ACCEPT_AMOUNT=150;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_newss);

        okHttpclient = new OkHttpClient();
        listViewforNews = findViewById(R.id.listviewnews);



        Request request = new Request.Builder().url("https://newsapi.org/v2/top-headlines?q=health&apiKey=b2b51d9375d347f4a8c8c9368249d03e").build();


        okHttpclient.newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(Request request, IOException e) {
               //
            }

            @Override
            public void onResponse(com.squareup.okhttp.Response response) throws IOException {
                final String resNewsData = response.body().string().toString();


                MedicalNewss.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        JsonObject jsonObject = new JsonParser()
                                .parse( resNewsData)
                                .getAsJsonObject();

                        int sizeOfArray = jsonObject.get("articles").getAsJsonArray().size();

                        mainTitle=new String[sizeOfArray];

                        descriptionText = new String[sizeOfArray];

                        imagesOfNews = new String[sizeOfArray];

                        newsContent = new String[sizeOfArray];
                        int pos = 0;
                        while (pos<ACCEPT_AMOUNT)
                        {
                            try {
                                JsonElement loop = jsonObject.get("articles").getAsJsonArray().get(pos);
                                mainTitle[pos] = loop.getAsJsonObject().get("title").getAsString();
                                descriptionText[pos] = loop.getAsJsonObject().get("description").getAsString();
                                imagesOfNews[pos] = loop.getAsJsonObject().get("urlToImage").getAsString();
                                newsContent[pos] = loop.getAsJsonObject().get("source").getAsJsonObject().get("name").getAsString();

                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            pos++;
                        }
                        AdapterForNews adapter = new AdapterForNews(getApplicationContext(),mainTitle,  descriptionText,imagesOfNews);

                        listViewforNews.setAdapter(adapter);
                    }
                });
            }
        });
    }


}

class AdapterForNews extends ArrayAdapter<String> {

    Context newscontext;

    String newsheading[];
    String newssubheading[];
    String news;

    String ArrayListNews;

    String newsimages[];

    AdapterForNews (Context c, String headings[], String subheadingTitle[], String imageslist[]) {

        super(c, R.layout.single_record_design, R.id.heading_title, headings);

        this.newscontext = c;

        ArrayListNews = headings.toString();

        this.newsheading = headings;

        this.newssubheading = subheadingTitle;
        this.newsimages = imageslist;
    }

    @NonNull
    @Override
    public View

    getView(
            int position,
            @Nullable View convertView,
            @NonNull ViewGroup parent)
    {



        LayoutInflater InfilatedLayout = (LayoutInflater)getContext()
                .getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View record_view = InfilatedLayout
                .inflate(R.layout.single_record_design, parent, false);



        ImageView imageViewPoint = record_view.findViewById(R.id.photo);

             TextView headPoint = record_view.findViewById(R.id.heading_title);

        TextView subHeadPoint = record_view.findViewById(R.id.sub_title);

        Glide.with(getContext()
                        .getApplicationContext())
                            .load(newsimages[position])
                                .into(imageViewPoint);

        headPoint.setText(newsheading[position]);


        subHeadPoint.setText(newssubheading[position]);

        return record_view;
    }
}
