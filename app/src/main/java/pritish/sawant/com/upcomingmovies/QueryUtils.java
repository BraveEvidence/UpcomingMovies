package pritish.sawant.com.upcomingmovies;

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
 * Created by pritish on 29/9/17.
 */

public final class QueryUtils {

    private static final String LOG_TAG=QueryUtils.class.getSimpleName();
    /**
     * Sample JSON response for a USGS query
     */

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }
    public static List<Movies> fetchNewsData(String requestUrl){
        URL url=createUrl(requestUrl);
        String jsonResponse=null;
        try{
            jsonResponse=makeHttpRequest(url);

        }
        catch (IOException e){

        }

        List<Movies> newsList=extractFeatureFromJson(jsonResponse);
        return newsList;
    }

    private static URL createUrl(String stringUrl){
        URL url=null;
        try{
            url=new URL(stringUrl);
        }
        catch (MalformedURLException e){

        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException{
        String jsonResponse="";
        if(url==null){
            return jsonResponse;
        }

        BufferedReader bufferedReader=null;
        try {
            URL url1=new URL(url.toString());
            HttpURLConnection httpURLConnection=(HttpURLConnection)url1.openConnection();
            StringBuilder stringBuilder=new StringBuilder();
            bufferedReader=new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String line;
            while ((line=bufferedReader.readLine())!=null){
                stringBuilder.append(line+"\n");
            }
            return stringBuilder.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        finally {
            if(bufferedReader!=null){
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
    }




    private static List<Movies> extractFeatureFromJson(String newsJson) {
        if(TextUtils.isEmpty(newsJson)){
            return null;
        }
        // Create an empty ArrayList that we can start adding earthquakes to
        List<Movies> newsList = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            JSONObject object=new JSONObject(newsJson);
            JSONArray jsonArray=object.getJSONArray("results");
            Movies news;
            for(int i=0;i<jsonArray.length();i++){
                news=new Movies();
                JSONObject jsonObject1=jsonArray.getJSONObject(i);
                news.setTitle(jsonObject1.getString("title"));
                news.setReleasedate(jsonObject1.getString("release_date"));
                news.setPosterpath("http://image.tmdb.org/t/p/w500"+jsonObject1.getString("poster_path"));

                news.setAdult(jsonObject1.getBoolean("adult"));
                news.setId(jsonObject1.getLong("id"));
                news.setOverview(jsonObject1.getString("overview"));
                news.setPopularity(jsonObject1.getDouble("popularity"));


                newsList.add(news);
            }

        } catch (JSONException e) {
            }

        // Return the list of earthquakes
        return newsList;
    }


}