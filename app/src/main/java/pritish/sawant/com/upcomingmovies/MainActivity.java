package pritish.sawant.com.upcomingmovies;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Movies>> {
    private TextView textView;
    private RecyclerView recyclerView;
    private static final int NEWS_LOADER_ID = 1;
    private MoviesAdapter mAdapter;
    private static final String LOG_TAG = MainActivity.class.getName();
    private static String USGS_REQUEST_URL = "https://api.themoviedb.org/3/movie/upcoming?api_key=b7cd3340a794e5a2f35e3abb820b497f";


    @Override
    public Loader<List<Movies>> onCreateLoader(int id, Bundle args) {
        return new MoviesLoader(this, USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Movies>> loader, List<Movies> data) {
        View view = findViewById(R.id.loading_indicator);
        view.setVisibility(View.GONE);
        textView.setText("No Movies");
        if (data != null && !data.isEmpty()) {
            // mAdapter=new NewsAdapter(new ArrayList<News>());
            textView.setVisibility(View.GONE);
            mAdapter.setNewsData(data);
            recyclerView.setAdapter(mAdapter);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<Movies>> loader) {
        mAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textView = (TextView) findViewById(R.id.empty_view);
        recyclerView = (RecyclerView) findViewById(R.id.mainactivityrecyclerview);

        // Create a fake list of earthquake_list_item locations.
        // Find a reference to the {@link ListView} in the layout
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getSupportLoaderManager();
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);

        } else {
            View view = findViewById(R.id.loading_indicator);
            view.setVisibility(View.GONE);
            textView.setText("No internet");

        }
        mAdapter = new MoviesAdapter(new ArrayList<Movies>(), getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(mAdapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.aboutme:
                Intent intent=new Intent(MainActivity.this,AboutMeActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
