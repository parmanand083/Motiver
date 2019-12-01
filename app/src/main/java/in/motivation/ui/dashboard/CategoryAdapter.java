package in.motivation.ui.dashboard;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import in.motivation.R;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {


      Context context;
      ArrayList<String> names;
     int progresscout=0;
    ArrayList< AsyncTaskExample>  obj=new ArrayList();
    URL ImageUrl = null;
    InputStream is = null;

    Bitmap bmImg = null;


    ProgressDialog progress=null;

    public CategoryAdapter(Context context, ArrayList<String> name) {
        this.context = context;
        this.names = name;

        progress = new ProgressDialog(context);
        progress.setMessage("Please wait....");
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
            imageView=(itemView.findViewById(R.id.imageView));
            image_name=itemview.findViewById(R.id.name);
            layout=itemview.findViewById(R.id.parent_layout);



        }


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_view, parent, false);
        ViewHolder holder = new ViewHolder(view);
        context=parent.getContext();


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
                System.out.println("......................P.....................................   "+names.size());
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

/*
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment calendarFragment = ((AppCompatActivity)context).getSupportFragmentManager().findFragmentById(R.id.navigation_dashboard);
                if (calendarFragment != null)
                {
                    ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction().remove(calendarFragment).commit();
                }
                Toast.makeText(context, names.get(position), Toast.LENGTH_SHORT).show();
                Fragment newFragment = new VideoFragment();

                FragmentTransaction ft = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();

                ft.replace(R.id.nav_host_fragment,newFragment);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.addToBackStack(null);

                ft.commit();

            }
        });
*/}

    @Override
    public int getItemCount() {

        return names.size();
    }


    public class AsyncTaskExample extends AsyncTask<Void, String, Bitmap> {

        private Listinfo listinfo;

        // a constructor so that you can pass the object and use
        AsyncTaskExample(Listinfo listinfo){
            this.listinfo = listinfo;
        }
        @Override
        protected void onPreExecute() {

            if(progress==null) {
                super.onPreExecute();
                progress = new ProgressDialog(context);
                progress.setMessage("Please wait...It is downloading");
                progress.setIndeterminate(false);
               // progress.setCancelable(false);
             //   progress.show();
            }
        }
        @Override
        protected Bitmap doInBackground(Void... info) {

            try {
                ImageUrl = new URL(listinfo.url);
                HttpURLConnection conn = (HttpURLConnection) ImageUrl.openConnection();
                conn.setDoInput(true);
                conn.connect();
                is = conn.getInputStream();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                bmImg = BitmapFactory.decodeStream(is, null, options);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bmImg;
        }
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            System.out.println("......................P.....................................   "+listinfo.pos+" : "+listinfo.url);

            /*
             if(listinfo.pos==4)
             {
                progress.hide();
             }
                */

            if(listinfo.view.imageView!=null) {

                listinfo.view.imageView.setImageBitmap(bitmap);
            }


        }
    }


    class Listinfo{


        public  ViewHolder view;
        public  int pos;
        public  String url;
        public  int size;

    }
}



