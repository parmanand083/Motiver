package in.motivation.ui.dashboard;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import in.motivation.R;
import in.motivation.util.Constant;
import in.motivation.util.ErrorDialog;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;
/*

Helps to display all info related to a talent


*/

public class ProfileViwer extends AppCompatActivity {

    public String id;
    ImageView profileImage=null;
    ProgressDialog progressDialog=null ;
    TextView hq,nativePlace,about,t_name,fevQuote,age,currentDesigntion,achivements;
    ArrayList<Talent> talentList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_profile_viwer);
         profileImage=findViewById(R.id.imageView);
         t_name=findViewById(R.id.name);
         about=findViewById(R.id.about);
         hq=findViewById(R.id.hq);
         fevQuote=findViewById(R.id.fev_quote);
         nativePlace=findViewById(R.id.native_place);
         achivements=findViewById(R.id.achivements);
         currentDesigntion=findViewById(R.id.currentDesigntion);
         age=findViewById(R.id.age);
         progressDialog=new ProgressDialog(ProfileViwer.this);
        progressDialog.setMessage(Constant.MSG_LOADING);
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(true);
        progressDialog.show();
         /*
           getting data from the another activity
          */
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
               this.id= null;
            } else {
                this.id= extras.getString("id");
            }
        } else
            {
            this.id= (String) savedInstanceState.getSerializable("id");
           }
        //fun to fecth data from the api
         getData();

    }





     public  void getData() {


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, "http://crazywork.in:5000/profile/talent_id/"+id, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray array=(JSONArray)response.get("data");
                            Talent list=null;

                            for (int i = 0; i < array.length(); i++) {
                                list=new Talent();
                                try {

                                    JSONObject jsonObject = array.getJSONObject(i);
                                    String currentAchivementsText=" <b ><font color=#0676BE>Achivements: </font></b><br>";
                                    for (int j = 0; j < jsonObject.getJSONArray("achivements").length(); j++){
                                        String achivement = jsonObject.getJSONArray("achivements").getString(j);
                                        int index=j+1;
                                        currentAchivementsText = currentAchivementsText+"<b>"+index+".</b> "+achivement+"<br>";
                                    }


                                    achivements.setText(Html.fromHtml(currentAchivementsText));
                                    String currentDesignationText = "<b > <font color=#0676BE>Designation: </font></b>" + jsonObject.get("current_status").toString();
                                    currentDesigntion.setText(Html.fromHtml(currentDesignationText));
                                    t_name.setText(jsonObject.getString("name"));
                                    age.setText(jsonObject.getString("age"));
                                    String nativePlaceText = "<b > <font color=#0676BE>Native Place: </font></b>" + jsonObject.get("native").toString();
                                    nativePlace.setText(Html.fromHtml(nativePlaceText));
                                    String fev_quote = "<b > <font color=#0676BE>Fev. Quote: </font></b>" + jsonObject.get("quotes").toString();
                                    fevQuote.setText(Html.fromHtml(fev_quote));
                                    String aboutText = "<b > <font color=#0676BE>About: </font></b>" + jsonObject.get("about").toString();
                                   // mytextview.setText(Html.fromHtml(sourceString));
                                    about.setText(Html.fromHtml(aboutText));
                                    String hqText = "<b > <font color=#0676BE>Higest Qualification: </font></b>" + jsonObject.get("higest_qualification").toString();
                                    hq.setText(Html.fromHtml(hqText));
                                    Picasso.get().load(jsonObject.get("pic").toString()).noPlaceholder().memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(profileImage);
                                    progressDialog.dismiss();
                                //    talentList.add(list);
                                }catch (Exception e)
                                {
                                    System.out.println(e);
                                }

                            }


                        //
                      //      adapter.notifyDataSetChanged();



                        } catch (JSONException e) {


                            e.printStackTrace();
                        }

                        //    System.out.println("..............................................."+response.toString());
                        // adapter.notifyDataSetChanged();
                        //   progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error1) {

                        progressDialog.dismiss();
                        ErrorDialog error = new ErrorDialog(Constant.API_ERROR_MSG,ProfileViwer.this);
                        error.showLoader();

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);
    }




}

class Talent{

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    int id;
    String pic;


}