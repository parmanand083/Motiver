package in.motivation;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import in.motivation.model.Quote;
import in.motivation.model.Video;
import in.motivation.util.Constant;
import in.motivation.util.DataHolder;
import in.motivation.util.ErrorDialog;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * it gets data for home page i.e Latest video ,latest quotes,tagline
 */

public class FullscreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
        TextView msg=findViewById(R.id.msg);


            if (isNetworkAvailable())
                loadVideos();
            else
                msg.setText(Constant.MSG_INTERNET_NOT_AVAILABLE);

    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    private void  loadVideos(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, Constant.API_GET_LATEST_VIDEOS, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray array=(JSONArray)response.get("data");
                            Video list=null;
                            for (int i = 0; i < array.length(); i++) {
                                list=new Video();
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
                      //  ErrorDialog errorDialog=new ErrorDialog(Constant.API_ERROR_MSG,FullscreenActivity.this);
                        //errorDialog.showLoader();
                    }


                });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);
    }
    private void loadQuotes() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, Constant.API_GET_LATEST_QUOTES, null, new Response.Listener<JSONObject>() {
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
                            loadTagline();
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ErrorDialog errorDialog=new ErrorDialog(Constant.API_ERROR_MSG,getApplicationContext());
                        errorDialog.showLoader();
                    }

                });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);
    }
    private void loadTagline() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, Constant.API_GET_TAGLINE, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String tagLine=response.getString("message");
                            DataHolder.tagLine=tagLine;
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
                        ErrorDialog errorDialog=new ErrorDialog(Constant.API_ERROR_MSG,getApplicationContext());
                        errorDialog.showLoader();
                    }

                });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);
    }


}
