package in.motivation.ui.dashboard;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;


import java.util.ArrayList;

import in.motivation.R;

public class YoutubeFragment extends Fragment {



    String youtubelink="";


    YoutubeFragment(String youtubelink){
        this.youtubelink=youtubelink;
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {


        View root = inflater.inflate(R.layout.youtube_fragment, container, false);



        return root;
    }



}




