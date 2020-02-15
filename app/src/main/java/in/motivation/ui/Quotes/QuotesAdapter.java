package in.motivation.ui.Quotes;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import in.motivation.R;
import in.motivation.model.Quote;
import in.motivation.ui.dashboard.YoutubePlayer;
import in.motivation.util.Constant;
import in.motivation.util.ErrorDialog;


public class QuotesAdapter extends RecyclerView.Adapter<QuotesAdapter.ViewHolder> {


    Context context;
    ArrayList<Quote> quotes_list=null;
    ProgressDialog progress=null;

    public QuotesAdapter(Context context, ArrayList<Quote> quotes_list) {
        this.context = context;
        this.quotes_list=quotes_list;
        progress = new ProgressDialog(context);
        progress.setMessage(Constant.MSG_LOADING);
        progress.setIndeterminate(false);
        progress.setCancelable(true);

    }


    public class  ViewHolder extends RecyclerView.ViewHolder
    {

         TextView cat_name;
         ImageView cat_imageView;
         LinearLayout layout;



        public ViewHolder(View itemview ){
            super(itemview);
            cat_name=(itemView.findViewById(R.id.name));
            cat_imageView=itemView.findViewById(R.id.imageView);
            layout=itemview.findViewById(R.id.quotes_parent_layout);
            System.out.println("....................+...................."+"Category\n");



        }


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quotes_view, parent, false);
        ViewHolder holder = new ViewHolder(view);
        context=parent.getContext();


        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        progress.show();
        Picasso.get().load(quotes_list.get(position).getUrl()).noPlaceholder().memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(holder.cat_imageView,new Callback()
        {
            @Override
            public void onError(Exception e) {
                progress.hide();
                ErrorDialog error=new ErrorDialog(Constant.API_ERROR_MSG,context);
                error.showLoader();
            }

            @Override
            public void onSuccess() {

                progress.hide();
            }});

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ImageViewer.class);
                intent.putExtra("url",quotes_list.get(position).getUrl());
                intent.putExtra("id",quotes_list.get(position).getId());
                context.startActivity(intent);


            }
        });
}

    @Override
    public int getItemCount() {

        return quotes_list.size();
    }
}



