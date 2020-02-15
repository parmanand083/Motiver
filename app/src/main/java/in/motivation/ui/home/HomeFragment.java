package in.motivation.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import in.motivation.R;
import in.motivation.ui.Quotes.ImageViewer;
import in.motivation.ui.dashboard.YoutubePlayer;
import in.motivation.util.DataHolder;

import static in.motivation.util.DataHolder.latestvideo_list;
import static in.motivation.util.DataHolder.quote_list;

public class HomeFragment extends Fragment {

    CarouselView carouselViewQuotes;
    CarouselView carouselViewVideos;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_home, container, false);
        TextView textTagline= root.findViewById(R.id.textTagline);
        carouselViewQuotes = (CarouselView) root.findViewById(R.id.carouselView);
        carouselViewQuotes.setPageCount(DataHolder.quote_list.size());

        // Click listner for quotes
        carouselViewQuotes.setImageListener(imageListenerQuotes);
        System.out.println(latestvideo_list.get(0).getThum_url());
        carouselViewVideos = (CarouselView) root.findViewById(R.id.carouselView1);
        carouselViewVideos.setPageCount(latestvideo_list.size());

        // Click listner for Videos
        carouselViewVideos.setImageListener(imageListenerVideos);

        textTagline.setText(DataHolder.tagLine);
        carouselViewVideos.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getContext(), YoutubePlayer.class);
                intent.putExtra("id",latestvideo_list.get(position).getVideo_url());
                getContext().startActivity(intent);
            }
        });

        carouselViewQuotes.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getContext(), ImageViewer.class);
                intent.putExtra("url",quote_list.get(position).getUrl());
                getContext().startActivity(intent);
            }
        });
        return root;
    }



    ImageListener imageListenerQuotes = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {


            Picasso.get().load(  DataHolder.quote_list.get(position).getUrl()).noPlaceholder().memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(imageView);

        }
    };
    ImageListener imageListenerVideos = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {


            Picasso.get().load(  latestvideo_list.get(position).getThum_url()).noPlaceholder().memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(imageView);

        }
    };



}



