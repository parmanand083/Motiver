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

public class VideoFragment extends Fragment {


    ProgressDialog progress=null;
    ArrayList<String> names=new ArrayList< String>();
    VideoViewAdapter adapter=null;
    String clicked="";


    VideoFragment(String clicked){
        this.clicked=clicked;
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        progress=new ProgressDialog(getContext());
        progress.setMessage("Please wait....");
        progress.setIndeterminate(false);
        progress.setCancelable(true);
        progress.show();
         getData();
        System.out.println(".........Video_Fragment...........");

        View root = inflater.inflate(R.layout.video_fragment, container, false);

        //Log.d(TAG, "initRecyclerView: init recyclerview.");
        RecyclerView recyclerView = root.findViewById(R.id.video_recycleview);

        adapter = new VideoViewAdapter(getContext(), names);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        return root;
    }

    private void getData() {
        //   final ProgressDialog progressDialog = new ProgressDialog(getContext());
        // progressDialog.setMessage("Loading...");
        //progressDialog.show();
       // String url="http://crazywork.in:4000/project/"+6200672234+"/submitted";
        System.out.println(clicked);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, "http://crazywork.in:4000/project/6200672234/submitted", null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {


                            JSONArray array=(JSONArray)response.get("rows");
                            CatList list=new CatList();

                            for (int i = 0; i < array.length(); i++) {

                                try {
                                    JSONObject jsonObject = array.getJSONObject(i);
                                   // System.out.println("..........................."+ jsonObject.get("desc").toString());
                                    names.add(jsonObject.get("desc").toString());
                                    progress.hide();
                                    adapter.notifyDataSetChanged();

                                }catch (Exception e)
                                {
                                    System.out.println(e);
                                }

                            }



                        } catch (JSONException e) {

                            e.printStackTrace();
                        }

                        //    System.out.println("..............................................."+response.toString());
                        // adapter.notifyDataSetChanged();
                        //   progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progress.hide();
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext(),R.style.MyDialogTheme);
                        builder1.setMessage("Unable to load ...Check your internet connection");
                        builder1.setCancelable(true);
                        builder1.setNegativeButton(
                                "Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        builder1.setPositiveButton(
                                "Exit",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        getActivity().finish();
                                        System.exit(0);
                                    }
                                });



                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                        System.out.println( error.toString());
                        // progressDialog.dismiss();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonObjectRequest);
    }


}




