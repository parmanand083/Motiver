package in.motivation.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import in.motivation.R;
import in.motivation.ui.util.ErrorDialog;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    CarouselView carouselView;

    int[] sampleImages = {R.drawable.quotes,R.drawable.quotes2};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        carouselView = (CarouselView) root.findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(imageListener);
       // ImageView imageView;
        //imageView = root.findViewById(R.id.home_imageView);
        //imageView.setImageDrawable(getResources().getDrawable(R.drawable.quotes));
    //    Picasso.get().load(getResources().getDrawable("")).noPlaceholder().memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(imageView);

        return root;
    }
    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {

            imageView.setImageResource(sampleImages[position]);
        }
    };

}


