package com.example.vivekgandhi.playvideoapp;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    VideoView vidView;
    private static final String TAG = "MyMessage";
    String vidAddress;
    Uri vidUri;
    MediaController vidControl;
    private static final int SELECT_IMAGE = 1;
    Button openGalery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openGalery = (Button) findViewById(R.id.openGalery);
        openGalery.setOnClickListener(this);
      /*  vidView = (VideoView)findViewById(R.id.myVideo);
        Uri uri = Uri.parse(Environment.getExternalStorageDirectory()+"/documents/welcome.mp4");
        vidView.setVideoURI(uri);
        vidControl = new MediaController(this);
        vidControl.setAnchorView(vidView);
        vidView.setMediaController(vidControl);
        vidView.start();*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.openGalery:
              //  Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                Intent gallery = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallery, SELECT_IMAGE);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && requestCode==SELECT_IMAGE){
            Uri selectedImage=data.getData();
            Log.i(TAG," b4 calling getpath  ");
            String path=getPath(selectedImage);
            Log.i(TAG,"path == " +path);
            //Bitmap bitmapImage= BitmapFactory.decodeFile(path);
           // ImageView image=(ImageView)findViewById(R.id.image);
            vidView = (VideoView)findViewById(R.id.myVideo);

            Uri uri = Uri.parse(Environment.getExternalStorageDirectory()+path);
            vidView.setVideoURI(uri);
            vidControl = new MediaController(this);
            vidControl.setAnchorView(vidView);
            vidView.setMediaController(vidControl);
            vidView.start();
            //image.setImageBitmap(bitmapImage);
        }
    }
    public String getPath(Uri uri){
        Log.i(TAG,"inside getpath");
        String[] filePathColumn={MediaStore.Video.Media.DATA};
        Cursor cursor=getContentResolver().query(uri, filePathColumn, null, null, null);
        Log.i(TAG,"bloody cursor = "+cursor);


        if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            Log.i(TAG,"exit  if get path");
            return cursor.getString(column_index);
        } else{
            Log.i(TAG,"exit else get path");

            return null;}

    }
}
