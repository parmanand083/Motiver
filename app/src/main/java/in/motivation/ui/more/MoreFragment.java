package in.motivation.ui.more;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import agency.tango.android.avatarview.IImageLoader;
import agency.tango.android.avatarview.loader.PicassoLoader;
import agency.tango.android.avatarview.views.AvatarView;
import in.motivation.R;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Date;


public class MoreFragment extends Fragment {

    ListView listView;
    TextView textView;
    String m_Text;
    String[] listItem;

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_more, container, false);

        String countryList[] = {"Feedback", "Contact us"};
        listView=(ListView)root.findViewById(R.id.list_view);
        //textView=(TextView)root.findViewById(R.id.textView);
        //listItem = getResources().getStringArray(R.array.list_more);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getContext(),R.layout.activity_list_more,R.id.textView,countryList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // TODO Auto-generated method stub
                String value=adapter.getItem(position);

                if(value=="Feedback")
                {
                    final Dialog myDialog;
                    myDialog = new Dialog(getContext());
                    TextView txtclose;
                    final EditText data;
                    Button btn;

                    myDialog.setContentView(R.layout.popup_feedback);
                    data=(EditText) myDialog.findViewById(R.id.data);
                    btn=(Button)myDialog.findViewById(R.id.send);
                    txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
                   // txtclose.setText("X");
                    String countryList[] = {"Feedback", "Contact us"};

                      txtclose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            myDialog.dismiss();

                        }
                     });
                    btn.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {

                         //   Toast.makeText(getContext(),data.getText(), Toast.LENGTH_SHORT).show();

                             if(data.getText().toString().length()==0)
                             {
                                 //Toast.makeText(getContext(),"Please write something",Toast.LENGTH_LONG).show();
                                 Toast.makeText(getContext(),"Please write something", Toast.LENGTH_SHORT).show();
                                 return;
                                }

                            try {
                                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                                String URL = "http://crazywork.in:5000/send/feedback";
                                JSONObject jsonBody = new JSONObject();
                                jsonBody.put("mesaage", data.getText());
                                jsonBody.put("timestamp", new Date().getTime());
                                final String requestBody = jsonBody.toString();

                                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        myDialog.dismiss();
                                        Toast.makeText(getContext(), "Feedback sent ", Toast.LENGTH_SHORT).show();
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(getContext(), "error,Please try after sometime", Toast.LENGTH_SHORT).show();
                                    }
                                }) {
                                    @Override
                                    public String getBodyContentType() {
                                        return "application/json; charset=utf-8";
                                    }

                                    @Override
                                    public byte[] getBody() throws AuthFailureError {
                                        try {
                                            return requestBody == null ? null : requestBody.getBytes("utf-8");
                                        } catch (UnsupportedEncodingException uee) {
                                            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                                            return null;
                                        }
                                    }

                                    @Override
                                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                                        String responseString = "";
                                        if (response != null) {
                                            responseString = String.valueOf(response.statusCode);

                                        }
                                        return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                                    }
                                };

                                requestQueue.add(stringRequest);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }




                        }
                    });
                    myDialog.show();
                }
                else if(value=="Contact us")
                {
                    final Dialog myDialog;
                    myDialog = new Dialog(getContext());
                    TextView txtclose;
                    myDialog.setContentView(R.layout.popup_contact);
                    txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
                    txtclose.setText("X");
                    String countryList[] = {"Feedback", "Contact us"};
                    txtclose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            myDialog.dismiss();
                        }
                    });


                 //   myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    myDialog.show();
                }

              //  Toast.makeText(getContext(),value, Toast.LENGTH_SHORT).show();

            }
        });



        return root;
    }
}