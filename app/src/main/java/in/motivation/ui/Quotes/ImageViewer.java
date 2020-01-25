package in.motivation.ui.Quotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;

import in.motivation.R;

public class ImageViewer extends Activity {
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 54654;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        Button downloadbtn= (findViewById(R.id.downloadbtn));

      final    String newString;
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

    }

}
