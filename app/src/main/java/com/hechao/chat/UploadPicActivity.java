package com.hechao.chat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

/**
 * 上传用户图片
 */
public class UploadPicActivity extends Activity {


    @InjectView(R.id.camera)
    Button btnCamera;
    @InjectView(R.id.pics)
    Button btnPics;

    @InjectView(R.id.imageview)
    ImageView imageView;

    private int camera_code = 1;
    private int galley_code = 2;
    private int crop_code = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uploadpic);

        ButterKnife.inject(this);


    }

    @OnClick(R.id.pics)
    void pics() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, galley_code);
    }

    @OnClick(R.id.camera)
    public void camera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, camera_code);
    }


    private void send(Bitmap bm) {

        Log.e("hechao","sending...");
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 60, stream);
        byte[] bytes = stream.toByteArray();

        String img = new String(Base64.encodeToString(bytes, Base64.DEFAULT));


        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("img", img);
        params.add("username",App.username);


        client.post("http://"+App.ip+"/chat/upload.php", params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Toast.makeText(UploadPicActivity.this, "success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(UploadPicActivity.this, "no success", Toast.LENGTH_SHORT).show();
            }

        });


        finish();

    }


    private Uri convertUri(Uri uri) {
        InputStream inputStream = null;

        try {
            inputStream = getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();

            return saveBitmap(bitmap);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }


    }


    private Uri saveBitmap(Bitmap bm) {
        File tempDir = new File("/mnt/sdcard/aaa/hhh");
        if (!tempDir.exists()) {
            tempDir.mkdir();
        }
        File imag = new File(tempDir, App.username+"temp.png");
        try {

            FileOutputStream fileOutputStream = new FileOutputStream(imag);
            bm.compress(Bitmap.CompressFormat.PNG, 85, fileOutputStream);
            fileOutputStream.flush();

            fileOutputStream.close();

            return Uri.fromFile(imag);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }


    }




    public static byte[] getBytes(InputStream is) throws Exception{
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while((len = is.read(buffer))!=-1){
            bos.write(buffer, 0, len);
        }
        is.close();
        bos.flush();
        byte[] result = bos.toByteArray();
//        System.out.println(new String(result));
        return  result;
    }


    public static Bitmap getImage(String address) throws Exception{
        //通过代码 模拟器浏览器访问图片的流程
        URL url = new URL(address);
        HttpURLConnection conn =  (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5000);
        //获取服务器返回回来的流
        InputStream is = conn.getInputStream();
        byte[] imagebytes = getBytes(is);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imagebytes, 0, imagebytes.length);
        return bitmap;
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == camera_code) {
            if (data == null) {
                return;
            } else {
                Bundle extra = data.getExtras();
                if (extra != null) {
                    Bitmap bitmap = extra.getParcelable("data");
                    Uri uri = saveBitmap(bitmap);
                    startImageZoom(uri);

//                    imageView.setImageBitmap(bitmap);
                }
            }


        } else if (requestCode == galley_code) {
            if (data == null) {
                return;
            } else {

                Uri uri = data.getData();
                uri = convertUri(uri);
                startImageZoom(uri);
//                imageView.setImageURI(uri);


            }

        } else if (requestCode == crop_code) {
            if (data == null) {
                return;
            }
            Bundle extra = data.getExtras();
            if (extra != null) {
                Bitmap bitmap = extra.getParcelable("data");

                imageView.setImageBitmap(bitmap);
                send(bitmap);

            }
        }


        super.onActivityResult(requestCode, resultCode, data);
    }


    private void startImageZoom(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", "1");
        intent.putExtra("aspectY", "1");
        intent.putExtra("outputX", "150");
        intent.putExtra("outputY", "150");
        intent.putExtra("return-data", true);

        startActivityForResult(intent, crop_code);

    }

}
