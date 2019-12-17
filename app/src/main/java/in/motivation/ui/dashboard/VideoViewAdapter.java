package in.motivation.ui.dashboard;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import in.motivation.R;

public class VideoViewAdapter extends RecyclerView.Adapter<VideoViewAdapter.ViewHolder> {
    Context context;
    ArrayList<String> names;
    int progresscout=0;

    URL ImageUrl = null;
    InputStream is = null;
    Bitmap bmImg = null;
    ProgressDialog progress=null;

    public VideoViewAdapter(Context context, ArrayList<String> name) {
        this.context = context;
        this.names = name;

        progress = new ProgressDialog(context);
        progress.setMessage("Please wait....");
        progress.setIndeterminate(false);
        progress.setIndeterminate(false);
        progress.setCancelable(true);
        //   progress.show();

    }


    public class  ViewHolder extends RecyclerView.ViewHolder
    {

        TextView image_name;
        ImageView imageView;
        LinearLayout layout;



        public ViewHolder(View itemview ){
            super(itemview);
            imageView=(itemView.findViewById(R.id.video_imageView));
            image_name=itemview.findViewById(R.id.name);
            layout=itemview.findViewById(R.id.video_parent);
            System.out.println("..............."+"Video view adapter"+"...............");



        }


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_view, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        // holder.image_name.setText(names.get(position));
        //imageView.setImageResource(R.drawable.exam)

        Listinfo info =new Listinfo();
        info.url=names.get(position);
        info.view=holder;
        info.pos=position;
        info.size=names.size()-1;
        progress.show();

        Picasso.get().load(names.get(position)).noPlaceholder().into(holder.imageView,new Callback()
        {
            @Override
            public void onError(Exception e) {
                progress.hide();
                AlertDialog.Builder builder1 = new AlertDialog.Builder(context,R.style.MyDialogTheme);
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
                                ((Activity)context).finish();
                                System.exit(0);
                            }
                        });



                AlertDialog alert11 = builder1.create();
                alert11.show();


            }

            @Override
            public void onSuccess() {
              //  System.out.println("......................P.....................................   "+names.size());
                progress.hide();
                //  progress.dismiss();
                //  progressbar.setVisibility(View.GONE);
            }});


        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                System.out.println("...................onclick............videoAd");

                Fragment calendarFragment = ((AppCompatActivity)context).getSupportFragmentManager().findFragmentById(R.id.parent_layout);
                if (calendarFragment != null)
                {
                 //   System.out.println("...................onclick............removing video_list");
                    ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction().remove(calendarFragment).commit();
                }

                Toast.makeText(context, names.get(position), Toast.LENGTH_SHORT).show();
                Fragment newFragment = new YoutubeFragment("Z9XWbqxyn3E");
                FragmentTransaction ft = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();

                ft.replace(R.id.nav_host_fragment,newFragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack("video");
                ft.commit();

            }
        });
    }

    @Override
    public int getItemCount() {
        return names.size();
    }




    class Listinfo{


        public  ViewHolder view;
        public  int pos;
        public  String url;
        public  int size;

    }
}



