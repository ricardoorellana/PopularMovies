package com.popularmovies.rorellanam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Rorellanam on 9/4/16.
 */
public class MovieListAdapter extends ArrayAdapter<Movie> {

    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the List is the data we want
     * to populate into the lists
     *
     * @param context        The current context. Used to inflate the layout file.
     * @param movies A List of movie objects to display in a list
     */
    public MovieListAdapter(Context context, List <Movie> movies) {

        // initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single ImageView.
        // Because this is a custom adapter for two ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, movies);
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position The AdapterView position that is requesting a view
     * @param convertView The recycled view to populate.
     * (search online for "android view recycling" to learn more)
     * @param parent The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Gets the Movie object from the ArrayAdapter at the appropriate position
        Movie movie = getItem(position);

        View listItem = convertView;

        // Adapters recycle views to AdapterViews.
        // If this is a new View object we're getting, then inflate the layout.
        // If not, this view already has the layout inflated from a previous call to getView,
        // and we modify the View widgets as usual.
        if (listItem == null) {
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.list_item_movies, parent, false);
        }

        ImageView poster = (ImageView) listItem.findViewById(R.id.poster);
        Picasso.with(getContext()).load("http://image.tmdb.org/t/p/w185" + movie.getImagePoster()).into(poster);

//        ImageView posterRight = (ImageView) listItem.findViewById(R.id.right_poster);
//        Picasso.with(getContext()).load("http://image.tmdb.org/t/p/w185" + movie.getImagePoster()).into(posterRight);

        return listItem;
    }

}
