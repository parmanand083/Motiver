package in.motivation.ui.dashboard;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import in.motivation.R;
import in.motivation.model.Video;
import in.motivation.util.Constant;
import in.motivation.util.ErrorDialog;

public class VideoFragment extends Fragment {

    ProgressDialog progress=null;
    ArrayList<Video> video_list=new ArrayList();
    VideoViewAdapter adapter=null;
    Integer video_id=null;
    VideoFragment(Integer  video_id){
        this.video_id=video_id;
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        progress=new ProgressDialog(getContext());
        progress.setMessage(Constant.MSG_LOADING);
        progress.setIndeterminate(false);
        progress.setCancelable(true);
        progress.show();
         getData();
        System.out.println(".........Video_Fragment...........");

        View root = inflater.inflate(R.layout.video_fragment, container, false);
        RecyclerView recyclerView = root.findViewById(R.id.video_recycleview);
        adapter = new VideoViewAdapter(getContext(),video_list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return root;
    }

    private void getData() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, Constant.API_GET_VIDEOS+this.video_id, null, new Response.Listener<JSONObject>() {
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
                                    list.setTalent_name(jsonObject.get("name").toString());
                                    list.setTalent_pic(jsonObject.getString("pic"));
                                    list.setTalent_id(jsonObject.get("talent_id").toString());
                                    list.setProfession(jsonObject.get("profession").toString());
                                    list.setViewscount(jsonObject.get("viewCount").toString());
                                    list.setTimestamp(jsonObject.get("timestamp").toString());

                                    video_list.add(list);
                                }catch (Exception e)
                                {
                                    System.out.println(e);
                                }

                            }
                            progress.hide();
                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progress.dismiss();
                        ErrorDialog errorDialog=new ErrorDialog(Constant.API_ERROR_MSG,getContext());
                        errorDialog.showLoader();

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonObjectRequest);
    }


}



