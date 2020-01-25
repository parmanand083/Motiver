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
import in.motivation.ui.dashboard.YoutubePlayer;


public class QuotesAdapter extends RecyclerView.Adapter<QuotesAdapter.ViewHolder> {


    Context context;
    ArrayList<QuotesList> quotes_list=null;
    ProgressDialog progress=null;

    public QuotesAdapter(Context context, ArrayList<QuotesList> quotes_list) {
        this.context = context;
        this.quotes_list=quotes_list;
        progress = new ProgressDialog(context);
        progress.setMessage("Please wait....");
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

       // holder.image_name.setText(names.get(position));
        //imageView.setImageResource(R.drawable.exam)

        Listinfo info =new Listinfo();
        info.url=quotes_list.get(position).url;
        info.view=holder;
        info.pos=position;
        info.size=quotes_list.size()-1;
        progress.show();
        //System.

        Picasso.get().load(quotes_list.get(position).url).noPlaceholder().memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(holder.cat_imageView,new Callback()
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
             //   System.out.println("......................P.....................................   "+names.size());
                progress.hide();
              //  progress.dismiss();
              //  progressbar.setVisibility(View.GONE);
            }});
       //  new AsyncTaskExample(info).execute();


     //   obj[0]=asyncTask;
     //   obj.add(asyncTask);
       // obj.get(0).execute(names.get(position));
       // AsyncTaskExample asyncTask=new AsyncTaskExample();
      //  asyncTask.execute(names.get(position));


        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ImageViewer.class);
                intent.putExtra("url",quotes_list.get(position).url);
                context.startActivity(intent);


            }
        });
}

    @Override
    public int getItemCount() {

        return quotes_list.size();
    }




    class Listinfo{


        public  ViewHolder view;
        public  int pos;
        public  String url;
        public  int size;

    }
}



