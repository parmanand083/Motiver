package in.motivation.ui.dashboard;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
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
import in.motivation.util.Constant;
import in.motivation.util.ErrorDialog;


class CategoryList{
    int id;
    String name;
    String url;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}


public class DashboardFragment extends Fragment {


    ProgressDialog progress=null;
    ArrayList<CategoryList> category_list=new ArrayList();
    CategoryAdapter adapter=null;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        progress=new ProgressDialog(getContext());
        progress.setMessage(Constant.MSG_LOADING);
        progress.setIndeterminate(false);
        progress.setCancelable(true);
        progress.show();
        //call API
        getData();
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        RecyclerView recyclerView = root.findViewById(R.id.recycleview);
        adapter = new CategoryAdapter(getContext(), category_list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return root;
    }

    private void getData() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, Constant.API_GET_CAT, null, new Response.Listener<JSONObject>() {
        @Override
            public void onResponse(JSONObject response) {
            try {

                 JSONArray array=(JSONArray)response.get("data");
                 CategoryList list=null;
                 for (int i = 0; i < array.length(); i++) {
                    list=new CategoryList();
                    try {
                        JSONObject jsonObject = array.getJSONObject(i);
                        list.setId(Integer.parseInt(jsonObject.get("cat_id").toString()));
                        list.setName(jsonObject.get("cat_name").toString());
                        list.setUrl(jsonObject.get("pic_path").toString());
                        System.out.println("..........................."+ jsonObject.get("pic_path").toString());
                      //  names.add(jsonObject.get("desc").toString());
                        category_list.add(list);

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
                progress.hide();
                ErrorDialog errorDialog=new ErrorDialog(Constant.API_ERROR_MSG,getContext());
                errorDialog.showLoader();
            }


        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonObjectRequest);
    }


}




