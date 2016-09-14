package com.popularmovies.rorellanam;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<Movie>> {

    public static final String LOG_TAG = MainActivity.class.getName();

    private String prefSortOption;

    /**
     * Constant value for the movie loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int MOVIE_LOADER_ID = 1;

    private final String TOP_RATED = "/movie/top_rated";
    private final String POPULARITY = "/movie/popular";

    private LoaderManager loaderManager;

    private ProgressBar mProgressbar;
    private TextView mEmptyStateTextView;
    private GridView mGridView;
    private MovieListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(myToolbar);

        mProgressbar = (ProgressBar) findViewById(R.id.loading_spinner);
        mProgressbar.setVisibility(View.VISIBLE);
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);

        getPreferences();

        // Get a reference to the LoaderManager, in order to interact with loaders.
        loaderManager = getLoaderManager();
        loaderManager.initLoader(MOVIE_LOADER_ID, null, this);

        mGridView = (GridView) findViewById(R.id.movies_gridview);
        mAdapter = new MovieListAdapter(this, new ArrayList<Movie>());
        mGridView.setAdapter(mAdapter);


        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Movie movie = mAdapter.getItem(i);

                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra(intent.EXTRA_TEXT,  movie);

                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        getPreferences();
        loaderManager.restartLoader(MOVIE_LOADER_ID, null, this);
    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int i, Bundle bundle) {
        Log.i(LOG_TAG, "onCreateLoader");

        final String BASE_URL = "http://api.themoviedb.org/3";
        final String QUERY_API = "?api_key=";

        StringBuilder urlString = new StringBuilder();

        switch (prefSortOption) {
            case "Popularity":
                urlString
                        .append(BASE_URL)
                        .append(POPULARITY)
                        .append(QUERY_API)
                        .append(BuildConfig.MOVIES_DB_API_KEY);
                break;
            case "Rating":
                urlString
                        .append(BASE_URL)
                        .append(TOP_RATED)
                        .append(QUERY_API)
                        .append(BuildConfig.MOVIES_DB_API_KEY);
                break;
        }

        // Create a new loader for the given URL
        return new MovieLoader(this, urlString.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> movies) {
        Log.i(LOG_TAG, "onLoadFinished");

        // Clear the mAdapter of previous earthquake data
        mAdapter.clear();

        mProgressbar.setVisibility(View.GONE);

        // data set. This will trigger the ListView to update.
        if (movies != null && !movies.isEmpty()) {
            mAdapter.addAll(movies);
            mEmptyStateTextView.setVisibility(View.GONE);
        } else {
            // Set empty state
            mEmptyStateTextView.setText(R.string.no_movies);
            mEmptyStateTextView.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {
        Log.i(LOG_TAG, "onLoaderReset");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.settings_action:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        prefSortOption = sharedPreferences.getString(getString(R.string.pref_sort_key), "");
    }
}
