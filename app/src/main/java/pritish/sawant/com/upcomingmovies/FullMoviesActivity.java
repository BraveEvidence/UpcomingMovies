package pritish.sawant.com.upcomingmovies;

import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class FullMoviesActivity extends AppCompatActivity {

    private TextView titleTextView,descriptionTextView;
    private RatingBar ratingBar;

    private Movies movies;
    private ViewPager mPager;
    private  int currentPage = 0;
    private  int NUM_PAGES = 0;
    private String[] myImageList;
    private ArrayList<ImageModel> imageModelArrayList;
    ArrayList<ImageModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_movies);
        list = new ArrayList<>();
        titleTextView=(TextView)findViewById(R.id.fullmoviestitlte);
        descriptionTextView=(TextView)findViewById(R.id.fullmoviesoverview);

        ratingBar=(RatingBar)findViewById(R.id.fullmoviesratingbar);

        Bundle b = getIntent().getExtras();
        movies = b.getParcelable("MyMovies");

        setTitle(movies.getTitle());

        titleTextView.setText(movies.getTitle());
        descriptionTextView.setText(movies.getOverview());



        double rating=movies.getPopularity();
        ratingBar.setRating(Float.parseFloat(String.valueOf(rating/100)));
        imageModelArrayList = new ArrayList<>();


        long id=movies.getId();
        String GITURL="https://api.themoviedb.org/3/movie/"+id+"/images?api_key=b7cd3340a794e5a2f35e3abb820b497f";

        StringRequest stringRequest=new StringRequest(GITURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                GsonBuilder gsonBuilder=new GsonBuilder();
                Gson gson= gsonBuilder.create();


                Images images = gson.fromJson(response,Images.class);
                List<Backdrop> backdrops=images.getBackdrops();




                if(backdrops.size()>4){
                    myImageList = new String[]{"http://image.tmdb.org/t/p/w500"+backdrops.get(0).getFilePath(),
                            "http://image.tmdb.org/t/p/w500"+backdrops.get(1).getFilePath(),
                            "http://image.tmdb.org/t/p/w500"+backdrops.get(2).getFilePath(),
                            "http://image.tmdb.org/t/p/w500"+backdrops.get(3).getFilePath(),
                            "http://image.tmdb.org/t/p/w500"+backdrops.get(4).getFilePath()};

                    for(int i = 0; i <5 ; i++){
                        ImageModel imageModel = new ImageModel();
                        imageModel.setImage_drawable(myImageList[i]);
                        Log.i("Pritish", "onResponse: "+myImageList[i]);
                        list.add(imageModel);
                    }
                }else{
                    myImageList = new String[]{"http://image.tmdb.org/t/p/w500"+backdrops.get(0).getFilePath()};
                    ImageModel imageModel = new ImageModel();
                    imageModel.setImage_drawable(myImageList[0]);
                    list.add(imageModel);
                }


                imageModelArrayList=list;

                init();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_LONG).show();
            }
        });


        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);



    }



    private void init() {

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new SlidingImage_Adapter(FullMoviesActivity.this,imageModelArrayList));

        CirclePageIndicator indicator = (CirclePageIndicator)
                findViewById(R.id.indicator);

        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
        indicator.setRadius(5 * density);

        NUM_PAGES =imageModelArrayList.size();

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 1000, 1000);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

    }

}