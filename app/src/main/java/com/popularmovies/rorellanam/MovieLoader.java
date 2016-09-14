package com.popularmovies.rorellanam;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by Rorellanam on 9/4/16.
 */
public class MovieLoader extends AsyncTaskLoader<List<Movie>> {

    /** Tag for log messages */
    private static final String LOG_TAG = MovieLoader.class.getName();

    /** Query URL */
    private String mUrl;


    public MovieLoader(Context context, String url) {
        super(context);

        Log.i(LOG_TAG, "MovieLoader()");
        mUrl = url;
    }


    @Override
    public List<Movie> loadInBackground() {
        Log.i(LOG_TAG, "loadInBackground");

        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of earthquakes.
        List<Movie> movies = HttpConnectionUtil.fetchMovieData(mUrl);
        return movies;
    }

    /**
     * To call forceLoad() which is a required step to actually trigger the loadInBackground() method to execute.
     */
    @Override
    protected void onStartLoading() {

        Log.i(LOG_TAG, "onStartLoading");
        forceLoad();
    }

}
