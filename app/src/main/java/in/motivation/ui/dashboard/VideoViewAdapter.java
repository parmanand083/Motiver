package in.motivation.ui.dashboard;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import in.motivation.R;
import in.motivation.model.Video;
import in.motivation.util.Constant;
import in.motivation.util.ErrorDialog;

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
        progress.setMessage(Constant.MSG_LOADING);
        progress.setIndeterminate(false);
        progress.setIndeterminate(false);
        progress.setCancelable(true);


    }



    public class  ViewHolder extends RecyclerView.ViewHolder
    {

        TextView desc,profession,views,timestamp;
        ImageView imageView;
        LinearLayout layout;
        AvatarView avatarView;
        TextView talent_name;
        ImageView share;


        public ViewHolder(View itemview ){
            super(itemview);
            imageView=(itemView.findViewById(R.id.video_imageView));
            desc=itemview.findViewById(R.id.textview);
            profession=itemview.findViewById(R.id.profession);
            share=itemview.findViewById(R.id.share);
            talent_name=itemview.findViewById(R.id.talent_name);
            views=itemview.findViewById(R.id.views);
            timestamp=itemview.findViewById(R.id.timestamp);
            avatarView =itemview.findViewById(R.id.avatar);
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

        progress.show();
        holder.desc.setText(video_list.get(position).getTitle());
        holder.talent_name.setText(video_list.get(position).getTalent_name());
        holder.profession.setText(video_list.get(position).getProfession());
        String totalViews = "Views- " + video_list.get(position).getViewscount();
        holder.views.setText(Html.fromHtml(totalViews));
        String uplaodTime = ", Uploaded " + video_list.get(position).getTimestamp();
        holder.timestamp.setText(Html.fromHtml(uplaodTime));
        Picasso.get().load(video_list.get(position).getTalent_pic()).noPlaceholder().memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(holder.avatarView);
        Picasso.get().load(video_list.get(position).getThum_url()).noPlaceholder().into(holder.imageView,new Callback()
        {
            @Override
            public void onError(Exception e) {
                progress.dismiss();
                ErrorDialog errorDialog=new ErrorDialog(Constant.API_ERROR_MSG,context);
                errorDialog.showLoader();

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
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Motiver");
                    String shareMessage= "\nWatch motivational videos using this app.\nClick here to install:\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=in.motivation";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                   context.startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch(Exception e) {
                   System.out.println(e);
                }
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
}



