package in.motivation.ui.dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import in.motivation.R;



public class YoutubePlayer extends AppCompatActivity {



    String youtubelink="";
    ProgressBar progressBar;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
      //  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
      */


        setContentView(R.layout.activity_youtube_player);

        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String newString;
                if (savedInstanceState == null) {
                    Bundle extras = getIntent().getExtras();
                    if(extras == null) {
                        newString= null;
                    } else {
                        newString= extras.getString("id");
                    }
                } else {
                    newString= (String) savedInstanceState.getSerializable("id");
                }

                youTubePlayer.loadVideo(newString, 0f);
            }
        });
/*
YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
getLifecycle().addObserver(youTubePlayerView);

youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
  @Override
  public void onReady(@NonNull YouTubePlayer youTubePlayer) {
    String videoId = "S0Q4gqBUs7c";
    youTubePlayer.loadVideo(videoId, 0f);
  }
});
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        String width = "100%";
        int height =size.y/5 ;
        System.out.println(".........."+height+"....."+width+"//////////////////////");
        progressBar = (ProgressBar) findViewById(R.id.progressBar);


        String VIDEO_URL="https://www.youtube.com/watch?v=iGcuqvFfiHY";
        String data_html = "<!DOCTYPE html><html><body bgcolor=\"black\"> <iframe + width=\""+ width +
                "\"height=\""+height+"\" src=\"https://www.youtube.com/embed/McFkNxQPd7w\" " +
                "frameborder=\"0\" allow=\"autoplay; encrypted-media;" +
                " gyroscope; picture-in-picture\" allowfullscreen></iframe> </body></html> ";

        System.out.println(data_html);
        progressBar.setVisibility(View.VISIBLE);
        WebView web1 = (WebView) findViewById(R.id.webview);
        web1.setWebChromeClient(new WebChromeClient());
        web1.setWebViewClient(new Browser_home());
        WebSettings webSettings = web1.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);
        web1.loadData(data_html,"text/html; charset=utf-8",null);


    }

    class Browser_home extends WebViewClient {

        Browser_home() {
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            //  setTitle(view.getTitle());
            // progressBar.setVisibility(View.GONE);

            progressBar.setVisibility(View.GONE);
            super.onPageFinished(view, url);


        }
        */

    }



}




