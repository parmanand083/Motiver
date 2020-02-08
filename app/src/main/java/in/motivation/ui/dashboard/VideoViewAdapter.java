package in.motivation.ui.dashboard;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import agency.tango.android.avatarview.IImageLoader;
import agency.tango.android.avatarview.views.AvatarView;
import in.motivation.BuildConfig;
import in.motivation.R;

public class VideoViewAdapter extends RecyclerView.Adapter<VideoViewAdapter.ViewHolder> {
    Context context;
    ArrayList<Video> video_list;
    int progresscout=0;

    URL ImageUrl = null;
    InputStream is = null;
    Bitmap bmImg = null;
    ProgressDialog progress=null;
    IImageLoader imageLoader;

    public VideoViewAdapter(Context context, ArrayList<Video> video_list) {
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
        TextView talent_name;
        ImageView share;

        public ViewHolder(View itemview ){
            super(itemview);
            imageView=(itemView.findViewById(R.id.video_imageView));

            desc=itemview.findViewById(R.id.textview);
            share=itemview.findViewById(R.id.share);
            talent_name=(TextView) itemview.findViewById(R.id.talent_name);
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
        holder.desc.setText(video_list.get(position).getTitle());
        holder.talent_name.setText(video_list.get(position).getTalent_name());
      //  imageLoader = new PicassoLoader();
        Picasso.get().load(video_list.get(position).getTalent_pic()).noPlaceholder().memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(holder.avatarView);
        //imageLoader.loadImage(holder.avatarView, "http:/example.com/user/someUserAvatar.png", "User Name");
        Picasso.get().load(video_list.get(position).getThum_url()).noPlaceholder().into(holder.imageView,new Callback()
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
        holder.share.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                    String shareMessage= "\nWatch motivational videos using this app.\nClick here to install:\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=in.motivation";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                   context.startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch(Exception e) {
                    //e.toString();
                }
                //   Intent intent = new Intent(context,YoutubePlayer.class);
             //   intent.putExtra("id",video_list.get(position).getVideo_url());
               // context.startActivity(intent);

              //  Toast.makeText(context,"HIIIII",Toast.LENGTH_SHORT);
            }
        });

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context,YoutubePlayer.class);
                intent.putExtra("id",video_list.get(position).getVideo_url());
                context.startActivity(intent);
            }
        });
        holder.avatarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ProfileViwer.class);
                intent.putExtra("id",video_list.get(position).getTalent_id());
                context.startActivity(intent);
            }
        });
        holder.talent_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ProfileViwer.class);
                intent.putExtra("id",video_list.get(position).getTalent_id());
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



