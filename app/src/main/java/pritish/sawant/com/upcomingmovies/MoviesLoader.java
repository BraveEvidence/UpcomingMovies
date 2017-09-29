package pritish.sawant.com.upcomingmovies;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

/**
 * Created by pritish on 29/9/17.
 */

public class MoviesLoader  extends AsyncTaskLoader<List<Movies>> {

    private String mUrl;

    public MoviesLoader(Context context, String url) {
        super(context);
        mUrl=url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Movies> loadInBackground() {
        if(mUrl==null) {
            return null;
        }
        List<Movies> newsList=QueryUtils.fetchNewsData(mUrl);
        return newsList;
    }
}