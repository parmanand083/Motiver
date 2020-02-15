package in.motivation.ui.Quotes;

import android.app.ProgressDialog;
import android.content.Context;
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
import in.motivation.model.Quote;
import in.motivation.util.Constant;
import in.motivation.util.ErrorDialog;


public class QuotesFragment extends Fragment {

    Context context;
    ArrayList<Quote> quotes_list=new ArrayList();
    ProgressDialog progress=null;
    QuotesAdapter adapter=null;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        progress=new ProgressDialog(getContext());
        progress.setMessage(Constant.MSG_LOADING);
        progress.setIndeterminate(false);
        progress.setCancelable(true);
        progress.show();
        getData();

        View root = inflater.inflate(R.layout.quotes_fragment, container, false);
        RecyclerView recyclerView = root.findViewById(R.id.quotes_recycleview);
        adapter = new QuotesAdapter(getContext(), quotes_list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return root;
    }

    private void getData() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, Constant.API_GET_QUOTES, null, new Response.Listener<JSONObject>() {
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
                                    quotes_list.add(list);

                                }catch (Exception e)
                                {
                                    System.out.println(e);
                                }

                            }
                            progress.hide();
                            System.out.println("..........................."+ quotes_list.toString());
                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {

                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progress.hide();
                        ErrorDialog errormsg=new ErrorDialog(Constant.API_ERROR_MSG,getContext());
                        errormsg.showLoader();
                        System.out.println( error.toString());

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonObjectRequest);
    }




}

