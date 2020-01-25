package in.motivation.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.motivation.R;
import in.motivation.ui.Quotes.ImageViewer;
import in.motivation.ui.dashboard.YoutubePlayer;
import in.motivation.ui.util.DataHolder;
import in.motivation.ui.util.ErrorDialog;
import static in.motivation.ui.util.DataHolder.latestvideo_list;
import static in.motivation.ui.util.DataHolder.quote_list;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    CarouselView carouselView;
    CarouselView carouselView1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        carouselView = (CarouselView) root.findViewById(R.id.carouselView);
        carouselView.setPageCount(DataHolder.quote_list.size());
        carouselView.setImageListener(imageListener);
        System.out.println(latestvideo_list.get(0).getThum_url());
        carouselView1 = (CarouselView) root.findViewById(R.id.carouselView1);
        carouselView1.setPageCount(latestvideo_list.size());
        carouselView1.setImageListener(imageListener1);

        carouselView1.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getContext(), YoutubePlayer.class);
                intent.putExtra("id",latestvideo_list.get(position).getVideo_url());
                getContext().startActivity(intent);
            }
        });

        carouselView.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getContext(), ImageViewer.class);
                intent.putExtra("url",quote_list.get(position).getUrl());
                getContext().startActivity(intent);
            }
        });
        return root;
    }



    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {


            Picasso.get().load(  DataHolder.quote_list.get(position).getUrl()).noPlaceholder().memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(imageView);

        }
    };
    ImageListener imageListener1 = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {


            Picasso.get().load(  latestvideo_list.get(position).getThum_url()).noPlaceholder().memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(imageView);

        }
    };



}



