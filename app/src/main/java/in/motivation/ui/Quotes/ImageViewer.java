package in.motivation.ui.Quotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import in.motivation.R;
import in.motivation.util.Util;

public class ImageViewer extends Activity {
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 54654;



    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);
        imageView= findViewById(R.id.imageView);
        Button downloadbtn= (findViewById(R.id.downloadbtn));
        Button sharebtn= (findViewById(R.id.sharebtn));

      final  String newString;
      final String quotes_id;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                newString = null;

            } else {
                newString = extras.getString("url");

            }
        } else {
            newString = (String) savedInstanceState.getSerializable("url");

        }

        Picasso.get().load(newString).noPlaceholder().memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).fit().into(imageView);
        downloadbtn.setOnClickListener(new View.OnClickListener(){
File file=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            @Override
            public void onClick(View v) {


                if (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ImageViewer.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            WRITE_EXTERNAL_STORAGE_REQUEST_CODE);

                }
                else
                {
                    startService(DownloadService.getDownloadService(getApplicationContext(), newString,file
                            .getAbsolutePath()));
                }

            }
        });



        sharebtn.setOnClickListener(new View.OnClickListener(){
            File file=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            @Override
            public void onClick(View v) {

                // Get access to the URI for the bitmap
                Uri bmpUri = Util.getLocalBitmapUri(imageView,ImageViewer.this);
                if (bmpUri != null) {
                    // Construct a ShareIntent with link to image
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
                    String shareMessage= "\nDownload more motivational quotes using this app.\nClick here to install:\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=in.motivation";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    shareIntent.setType("image/*");
                    // Launch sharing dialog for image
                    startActivity(Intent.createChooser(shareIntent, "Share Image"));
                } else {

                    Toast.makeText(getApplicationContext(),"Error, Please try again",Toast.LENGTH_SHORT).show();

                }


            }
        });

    }

}
