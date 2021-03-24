package org.techtown.graduation;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;





public class MainActivity extends AppCompatActivity implements AutoPermissionsListener{
    ProgressDialog progressDialog;

    Bitmap selected_bitmap;
    ImageView imageView;
    File file;
    Uri uri;
    static int FRAG = 0;

    Button takePicture;
    Button selectImg;
    Button upload_img;

    static RequestQueue requestQueue;
    static String myUrl = "http://192.168.140.23:8080/api";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.img_selected);

        initTakeButton();
        initSelectButton();
        initUploadButton();


        AutoPermissions.Companion.loadAllPermissions(this, 101);
    }

    public void initTakeButton(){
        takePicture = findViewById(R.id.take_picture);
        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture();
                FRAG = 1;
            }
        });
    }
    public void takePicture() {
        try {
            file = createFile();
            if (file.exists()) {
                file.delete();
            }

            file.createNewFile();
        } catch(IOException e) {
            e.printStackTrace();
        }

        if(Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(this, "org.techtown.graduation.fileprovider", file);
        } else {
            uri = Uri.fromFile(file);
        }

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 101);
        }
    }


    public void initSelectButton(){
        selectImg = findViewById(R.id.select_img);
        selectImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
                FRAG = 2;
            }
        });
    }

    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(intent, 101);
    }



    private File createFile() {
        String filename = "capture.jpg";
        File outFile = new File(getExternalCacheDir(), filename);

        return outFile;
    }


    public void initUploadButton(){
        if(requestQueue == null)    requestQueue = Volley.newRequestQueue(getApplicationContext());

        upload_img = findViewById(R.id.upload_img);

        upload_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doUpload();
            }
        });
    }
    public void doUpload(){
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Uploading, please wait...");
        progressDialog.show();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        selected_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        //sending image to server
        StringRequest request = new StringRequest(Request.Method.GET, myUrl, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                progressDialog.dismiss();
//                if(s.equals("babo")){
//                    Toast.makeText(MainActivity.this, "Uploaded Successful", Toast.LENGTH_LONG).show();
//
//
//                }
//                else{
//                    Toast.makeText(MainActivity.this, "Some error occurred!", Toast.LENGTH_LONG).show();
//                }
                Glide.with(getApplicationContext()).load(s).into(imageView);
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity.this, "Some error occurred -> "+volleyError, Toast.LENGTH_LONG).show();;
            }
        }) {
            //adding parameters to send
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("image", imageString);
                return parameters;
            }
        };

        RequestQueue rQueue = Volley.newRequestQueue(MainActivity.this);
        rQueue.add(request);

//        waitResponse();
    }



//    public void waitResponse(){
//        String url = myUrl;
//
//        ImageRequest request = new ImageRequest(url, new Response.Listener<Bitmap>() {
//            @Override
//            public void onResponse(Bitmap response) {
//
//            }
//        },0,0, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//
//
//
//        request.setShouldCache(false);
//        requestQueue.add(request);
//
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101 && resultCode == RESULT_OK && FRAG == 1) {
            try {
                selected_bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                imageView.setImageBitmap(selected_bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        if(requestCode == 101 && FRAG == 2) {
            if(resultCode == RESULT_OK) {
                Uri fileUri = data.getData();

                ContentResolver resolver = getContentResolver();

                try {
                    InputStream instream = resolver.openInputStream(fileUri);
                    selected_bitmap = BitmapFactory.decodeStream(instream);
                    imageView.setImageBitmap(selected_bitmap);

                    instream.close();
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AutoPermissions.Companion.parsePermissions(this, requestCode, permissions, this);
    }

    @Override
    public void onDenied(int requestCode, String[] permissions) {
        Toast.makeText(this, "permissions denied : " + permissions.length,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onGranted(int requestCode, String[] permissions) {
        Toast.makeText(this, "permissions granted : " + permissions.length, Toast.LENGTH_LONG).show();
    }


}