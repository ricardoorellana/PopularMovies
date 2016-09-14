package com.popularmovies.rorellanam;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rorellanam on 9/4/16.
 */
public final class HttpConnectionUtil {

    private static final String LOG_TAG = HttpConnectionUtil.class.getSimpleName();

    public HttpConnectionUtil() {
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;


        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(1000);
            urlConnection.setConnectTimeout(1000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromInputStream(inputStream);
            } else {
                Log.i(LOG_TAG, " Error getting JSON response");
            }

        } catch (IOException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "Problem retrieving the movies JSON results.", e);
        } finally {

            if(urlConnection != null) {
                urlConnection.disconnect();
            }

            if(inputStream != null) {
                inputStream.close();
            }
        }

        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder outPutString = new StringBuilder();
        if(inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);

            String line = reader.readLine();
            while(line != null) {
                outPutString.append(line);
                line = reader.readLine();
            }
        }
        return outPutString.toString();
    }

    /**
     * Query the USGS dataset and return a list of {@link Movie} objects.
     */
    public static List<Movie> fetchMovieData(String requestUrl) {
        Log.i(LOG_TAG, "fetchMovieData");

        /**
         *  We are forcing the background thread to pause execution and wait for 2 seconds (which is 2000 milliseconds),
         *  before proceeding to execute the rest of lines of code in this method. If you try to add the Thread.sleep(2000);
         *  method call by itself, Android Studio will complain that there is an uncaught exception, so we need to
         *  surround that statement with a try/catch block.
         */
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Movie}s
        List<Movie> movies = extractFeatureFromJson(jsonResponse);

        Log.i(LOG_TAG, jsonResponse);

        // Return the list of {@link Movie}s
        return movies;
    }

    public static URL createUrl(String stringUrl){
        URL url = null;

        try {
            url = new URL(stringUrl);

        } catch (MalformedURLException e) {
//            e.printStackTrace();
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }

        return url;
    }

    /**
     * Return a list of {@link Movie} objects that has been built up from
     * parsing a JSON response.
     */
    public static List <Movie> extractFeatureFromJson(String movieJSON) {

        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(movieJSON)) {
            return null;
        }


        // Create an empty ArrayList that we can start adding movies to
        List<Movie> movies = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(movieJSON);

            JSONArray resultsArray = baseJsonResponse.getJSONArray("results");

            for(int i=0, total = resultsArray.length(); i < total; i++) {
                JSONObject resultObject = resultsArray.getJSONObject(i);

                String title = resultObject.getString("original_title");
                String posterPath = resultObject.getString("poster_path");
                String overview = resultObject.getString("overview");
                String date = resultObject.getString("release_date");


                // Create a new {@link Movie} object with the magnitude, location, time,
                // and url from the JSON response.
                Movie movie = new Movie(title, posterPath, overview, date);

                // Add the new {@link Movie} to the list of movies.
                movies.add(movie);
            }


        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the movie JSON results", e);
        }


        // Return the list of movies
        return movies;
    }
}
