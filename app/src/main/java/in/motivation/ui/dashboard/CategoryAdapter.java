package in.motivation.ui.dashboard;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import android.app.ProgressDialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import in.motivation.R;
import in.motivation.util.Constant;
import in.motivation.util.ErrorDialog;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    Context context;
    ArrayList<CategoryList> category_list=null;
    ProgressDialog progress=null;

    public CategoryAdapter(Context context, ArrayList<CategoryList> category_list) {
        this.context = context;
        this.category_list=category_list;
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
            layout=itemview.findViewById(R.id.parent_layout);
            System.out.println("....................+...................."+"Category\n");
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



        Picasso.get().load(category_list.get(position).url).noPlaceholder().memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(holder.cat_imageView,new Callback()
        {
            @Override
            public void onError(Exception e) {
                progress.hide();
                ErrorDialog errorDialog=new ErrorDialog(Constant.API_ERROR_MSG,context);
                errorDialog.showLoader();
            }

            @Override
            public void onSuccess() {

                progress.hide();
            }});



        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("...................onclick............CAT");
                Fragment newFragment = new VideoFragment(category_list.get(position).id);
                 FragmentTransaction ft = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment,newFragment);
                ft.addToBackStack("Cat");
                ft.commit();

            }
        });
}

    @Override
    public int getItemCount() {

        return category_list.size();
    }



}



