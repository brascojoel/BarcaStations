package com.example.barcastations;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class FormActivity extends AppCompatActivity {

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Button take_button, validation_button;
    private EditText title_ed;
    private ImageView image_ed;
    private PictureRepository pictureRepository;
    private StationRepository stationRepository;
    private Station bd_station = null;
    private byte[] image = null;
    private Uri imageUri = null;
    private String path = null;
    Station bus_station;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        getSupportActionBar().setTitle("FormActivity");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        title_ed = (EditText) findViewById(R.id.title_ed);
        validation_button = (Button) findViewById(R.id.validation_button);
        take_button = (Button) findViewById(R.id.take_button);
        image_ed = (ImageView)findViewById(R.id.image_ed);
        pictureRepository = new PictureRepository(getApplication());
        stationRepository = new StationRepository(getApplication());

        ShareFactory shareFactory = new ShareFactory(SharedViewModel.getInstance());
        SharedViewModel model = new ViewModelProvider(this,shareFactory).get(SharedViewModel.class);
        model.getStation().observe(this, new Observer<Station>() {
            @Override
            public void onChanged(Station station) {
                bus_station = station;


                Log.d("FormActivity",""+bus_station.getStreet_name());




            }
        });
        take_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                permission();
                Log.d("take_button","data "+bus_station.getStreet_name());



            }
        });

        validation_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String DATE_FORMAT = "dd-MMM-yyyy hh:mm:ss";

                String title = title_ed.getText().toString();
                String date_picture = Utils.getDateTimeFromTimeStamp(System.currentTimeMillis(),DATE_FORMAT);


                int x = insert(bus_station.getId(),image,title,date_picture);
                if(x==1){
                    Log.d("insert","good");
                }

            }
        });
    }

    private void permission(){
        if (checkPermissionREAD_EXTERNAL_STORAGE(this)) {
           dispatchTakePictureIntent();

        }
    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();


           // path = data.getData().getPath();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

               // path = getImageUri(imageBitmap).getPath();


            imageUri = getImageUri(imageBitmap);

            image_ed.setImageBitmap(imageBitmap);
            image = Utils.convertBitmapToByteArray(imageBitmap);


        }
    }

    private Uri getImageUri( Bitmap photo) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), photo, "Title", null);
        return Uri.parse(path);
    }

    public boolean checkPermissionREAD_EXTERNAL_STORAGE(
            final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        (Activity) context,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                    showDialog("External storage", context, Manifest.permission.WRITE_EXTERNAL_STORAGE);

                } else {
                    ActivityCompat.requestPermissions(
                                    (Activity) context,
                                    new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }
    }

    public void showDialog(final String msg, final Context context,
                           final String permission) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(msg + " permission is necessary");
        alertBuilder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[] { permission },
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }
    private int insert(String id,byte[] image, String title, String date_now){

        Picture picture = null;
        if(image != null && title!=null && !title.isEmpty() ){

           bd_station  = stationRepository.getStation(id);

            picture = new Picture(id,image,title,date_now);
            // vérifier que la station n'existe pas encore
            if(bd_station == null){
                stationRepository.insert(bus_station);

            }

            pictureRepository.insert(picture);

            return 1;
        }
        else {
            Utils.showDialog(this,"INSERTION","Sorry, title and image cannot be empty");
        }
        return 0;
    }

  /*  private void getStationBD(String id){
        stationRepository.getStation(id);
    } */

    private void share(){
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.setType("image/*");
        startActivity(Intent.createChooser(shareIntent, "Share"));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // do your stuff
                } else {
                    Toast.makeText(FormActivity.this, "GET_ACCOUNTS Denied",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions,
                        grantResults);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_form, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.share_button) {
            if(image != null){
                share();
            }else{
                Utils.showDialog(this,"IMAGE","Sorry , take a picture before ");
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
