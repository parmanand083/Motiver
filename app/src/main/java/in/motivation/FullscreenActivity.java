package in.motivation;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.motivation.ui.dashboard.VideoList;
import in.motivation.ui.home.HomeFragment;
import in.motivation.ui.util.DataHolder;
import in.motivation.ui.util.ErrorDialog;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */



public class FullscreenActivity extends AppCompatActivity {

    private static int TIME_OUT = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
        loadVideos();
    }
    private void  loadVideos(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, "http://crazywork.in:5000/videos/topn", null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray array=(JSONArray)response.get("data");
                            VideoList list=null;


                            for (int i = 0; i < array.length(); i++) {
                                list=new VideoList();
                                try {

                                    JSONObject jsonObject = array.getJSONObject(i);
                                    list.setVideo_id(Integer.parseInt(jsonObject.get("video_id").toString()));
                                    list.setCat_id(Integer.parseInt(jsonObject.get("cat_id").toString()));
                                    list.setThum_url(jsonObject.get("thumb_url").toString());
                                    list.setVideo_url(jsonObject.get("video_url").toString());
                                    list.setTitle(jsonObject.get("video_title").toString());
                                    DataHolder.latestvideo_list.add(list);
                                }catch (Exception e)
                                {
                                    System.out.println(e);
                                }

                            }
                            loadQuotes();
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //   progress.hide();
                        ErrorDialog errorDialog=new ErrorDialog("Unable to access category list.Please try after sometime",getApplicationContext());
                        errorDialog.showLoader();
                    }


                });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);
    }
    private void loadQuotes() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, "http://crazywork.in:5000/quotes/topn", null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray array=(JSONArray)response.get("data");
                            Quote list=null;
                            for (int i = 0; i < array.length(); i++) {
                                list=new Quote();
                                try {
                                    JSONObject jsonObject = array.getJSONObject(i);
                                    list.setId(Integer.parseInt(jsonObject.get("quotes_id").toString()));
                                    list.setLang(jsonObject.get("language").toString());
                                    list.setUrl(jsonObject.get("quotes_path").toString());
                                    DataHolder data=new DataHolder();
                                    data.quote_list.add(list);

                                }catch (Exception e)
                                {
                                    System.out.println(e);
                                }

                            }

                            Intent intent=new Intent(FullscreenActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                            System.out.println("Data loaded");


                        } catch (JSONException e) {

                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ErrorDialog errorDialog=new ErrorDialog("Unable to access category list.Please try after sometime",getApplicationContext());
                        errorDialog.showLoader();
                    }

                });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);
    }



    public  class  Quote{

        int id;
        String lang;
        String url;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLang() {
            return lang;
        }

        public void setLang(String lang) {
            this.lang = lang;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }



    }
}
