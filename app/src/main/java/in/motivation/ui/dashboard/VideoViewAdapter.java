package in.motivation.ui.dashboard;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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

import com.google.android.youtube.player.YouTubePlayer;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import agency.tango.android.avatarview.IImageLoader;
import agency.tango.android.avatarview.loader.PicassoLoader;
import agency.tango.android.avatarview.views.AvatarView;
import in.motivation.R;
import in.motivation.ui.util.DataHolder;

public class VideoViewAdapter extends RecyclerView.Adapter<VideoViewAdapter.ViewHolder> {
    Context context;
    ArrayList<VideoList> video_list;
    int progresscout=0;

    URL ImageUrl = null;
    InputStream is = null;
    Bitmap bmImg = null;
    ProgressDialog progress=null;
    IImageLoader imageLoader;

    public VideoViewAdapter(Context context, ArrayList<VideoList> video_list) {
        this.context = context;
        this.video_list = video_list;

        progress = new ProgressDialog(context);
        progress.setMessage("Please wait....");
        progress.setIndeterminate(false);
        progress.setIndeterminate(false);
        progress.setCancelable(true);
        //   progress.show();

    }


    public class  ViewHolder extends RecyclerView.ViewHolder
    {

        TextView desc;
        ImageView imageView;
        LinearLayout layout;
        AvatarView avatarView;
        TextView interviewer_name;



        public ViewHolder(View itemview ){
            super(itemview);
            imageView=(itemView.findViewById(R.id.video_imageView));
            desc=itemview.findViewById(R.id.textview);
            interviewer_name=(TextView) itemview.findViewById(R.id.interviewer_name);
            avatarView = (AvatarView)itemview.findViewById(R.id.avatar);
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

        ///Listinfo info =new Listinfo();
        //info.url=names.get(position);
        //info.view=holder;
        //info.pos=position;
      //  info.size=names.size()-1;
        progress.show();
      //  System.out.println(video_list.get(position).thum_url);
        holder.desc.setText(video_list.get(position).title);
        holder.interviewer_name.setText("Parmanand kumar");
      //  imageLoader = new PicassoLoader();
        Picasso.get().load(video_list.get(position).thum_url).noPlaceholder().memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(holder.avatarView);
        //imageLoader.loadImage(holder.avatarView, "http:/example.com/user/someUserAvatar.png", "User Name");
        Picasso.get().load(video_list.get(position).thum_url).noPlaceholder().into(holder.imageView,new Callback()
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
                progress.hide();
            }});


        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,YoutubePlayer.class);
                intent.putExtra("id",video_list.get(position).video_url);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return video_list.size();
    }




    class Listinfo{


        public  ViewHolder view;
        public  int pos;
        public  String url;
        public  int size;

    }
}



