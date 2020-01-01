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



public class QuotesFragment extends Fragment {

 //   private NotificationsViewModel notificationsViewModel;

    Context context;
    ArrayList<QuotesList> quotes_list=new ArrayList();
    ProgressDialog progress=null;
    QuotesAdapter adapter=null;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        progress=new ProgressDialog(getContext());
        progress.setMessage("Please wait....");
        progress.setIndeterminate(false);
        progress.setCancelable(true);
        progress.show();
        getData();

        View root = inflater.inflate(R.layout.quotes_fragment, container, false);


        // Log.d(TAG, "initRecyclerView: init recyclerview.");
      RecyclerView recyclerView = root.findViewById(R.id.quotes_recycleview);

       adapter = new QuotesAdapter(getContext(), quotes_list);
        recyclerView.setAdapter(adapter);
       // recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        return root;
    }

    private void getData() {
        //   final ProgressDialog progressDialog = new ProgressDialog(getContext());
        // progressDialog.setMessage("Loading...");
        //progressDialog.show();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, "http://crazywork.in:5000/quotes/all", null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray array=(JSONArray)response.get("data");

                            QuotesList list=null;
                            for (int i = 0; i < array.length(); i++) {
                                list=new QuotesList();
                                try {
                                    JSONObject jsonObject = array.getJSONObject(i);
                                    list.setId(Integer.parseInt(jsonObject.get("quotes_id").toString()));
                                    list.setLang(jsonObject.get("language").toString());
                                    list.setUrl(jsonObject.get("quotes_path").toString());
                                    System.out.println("..........................."+ jsonObject.get("quotes_path").toString());
                                    //  names.add(jsonObject.get("desc").toString());
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



class QuotesList{
    int id;
    String lang;
    String url;

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public void setId(int id) {
        this.id = id;
    }


    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }



    public String getUrl() {
        return url;
    }
}