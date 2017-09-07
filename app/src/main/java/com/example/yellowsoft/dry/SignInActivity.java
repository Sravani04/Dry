package com.example.yellowsoft.dry;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by yellowsoft on 11/8/17.
 */

public class SignInActivity extends Activity {
    EditText fname,lname,email,password,phone;
    ImageView back_btn;
    LinearLayout progress_holder;
    TextView signin_btn;
    Integer REQUEST_CAMERA=1,SELECT_FILE=0;
    ImageView profile_pic;
    TextView st_register;
    Typeface regular,regular_arabic;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_screen);
        Session.forceRTLIfSupported(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        fname = (EditText) findViewById(R.id.fname);
        lname = (EditText) findViewById(R.id.lname);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        phone = (EditText) findViewById(R.id.phone);
        signin_btn = (TextView) findViewById(R.id.signin_btn);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        profile_pic = (ImageView) findViewById(R.id.profile_pic);
        st_register = (TextView) findViewById(R.id.st_register);
        progress_holder = (LinearLayout) findViewById(R.id.progress_holder);
        progress_holder.setVisibility(View.GONE);
        signin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_member();
            }
        });
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_images();
            }
        });

        st_register.setText(Session.GetWord(this,"REGISTER"));
        fname.setHint(Session.GetWord(this,"FIRST NAME"));
        lname.setHint(Session.GetWord(this,"LAST NAME"));
        email.setHint(Session.GetWord(this,"Email"));
        password.setHint(Session.GetWord(this,"Password"));
        phone.setHint(Session.GetWord(this,"MOBILE"));
        signin_btn.setText(Session.GetWord(this,"CREATE ACCOUNT"));

//        regular = Typeface.createFromAsset(this.getAssets(), "fonts/libel-suit-rg.ttf");
//        regular_arabic = Typeface.createFromAsset(this.getAssets(), "fonts/Hacen Tunisia.ttf");
//
//
//        if (Session.GetLang(this).equals("en")) {
//            fname.setTypeface(regular);
//            lname.setTypeface(regular);
//            phone.setTypeface(regular);
//            email.setTypeface(regular);
//            password.setTypeface(regular);
//        }else {
//            fname.setTypeface(regular_arabic);
//            lname.setTypeface(regular_arabic);
//            phone.setTypeface(regular_arabic);
//            email.setTypeface(regular_arabic);
//            password.setTypeface(regular_arabic);
//        }




    }

    public void show_progress(){
        progress_holder.setVisibility(View.VISIBLE);
    }

    public void hide_progress(){
        progress_holder.setVisibility(View.GONE);
    }

    public void add_member(){
        String fname_string = fname.getText().toString();
        String lname_string = lname.getText().toString();
        String email_string = email.getText().toString();
        String password_string = password.getText().toString();
        String phone_string = phone.getText().toString();
        if (fname_string.equals("")){
            Toast.makeText(SignInActivity.this,"Please Enter First Name",Toast.LENGTH_SHORT).show();
            fname.requestFocus();
        }else if (lname_string.equals("")){
            Toast.makeText(SignInActivity.this,"Please Enter Last Name",Toast.LENGTH_SHORT).show();
            lname.requestFocus();
        }else if (email_string.equals("") || !email_string.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")){
            Toast.makeText(SignInActivity.this,"Please Enter Email",Toast.LENGTH_SHORT).show();
            email.requestFocus();
        }else if (password_string.equals("")){
            Toast.makeText(SignInActivity.this,"Please Enter Password",Toast.LENGTH_SHORT).show();
            password.requestFocus();
        }else if (phone_string.equals("") || !validCellPhone(phone_string) || phone_string.length() < 6 || phone_string.length() > 13){
            Toast.makeText(SignInActivity.this,"Please Enter Phone",Toast.LENGTH_SHORT).show();
            phone.requestFocus();
        }else {
            show_progress();
            Ion.with(this).load(Session.SERVER_URL + "add-member.php").setBodyParameter("fname", fname_string).setBodyParameter("lname", lname_string)
                    .setBodyParameter("email", email_string).setBodyParameter("password", password_string).setBodyParameter("phone", phone_string)
                    .asJsonObject().setCallback(new FutureCallback<JsonObject>() {
                @Override
                public void onCompleted(Exception e, JsonObject result) {
                    hide_progress();
                    try {
                        if (result.get("status").getAsString().equals("Success")){
                            if (selected_image_path.equals("")) {
                                add_success();
                            }else {
                                upload_image();
                            }
                        }else {
                            Toast.makeText(SignInActivity.this,result.get("message").getAsString(),Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            });
        }
    }

    public boolean validCellPhone(String number){
        return android.util.Patterns.PHONE.matcher(number).matches();
    }

    public void show_images(){
        final CharSequence[] items = {"camera","gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("select_image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if(items[item].equals("camera")){
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent,REQUEST_CAMERA);

                }else if(items[item].equals("gallery")){
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto,SELECT_FILE);
                }
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }




    String selected_image_path = "";
    public void onActivityResult(int requestCode,int resultCode, Intent imageReturnedIntent){
        super.onActivityResult(requestCode,resultCode,imageReturnedIntent);
        if (resultCode== Activity.RESULT_OK) {
            if (requestCode == REQUEST_CAMERA && imageReturnedIntent != null) {
                Bundle bundle = imageReturnedIntent.getExtras();
                final Bitmap bmp = (Bitmap) bundle.get("data");
                profile_pic.setImageBitmap(bmp);
                Uri selectedImage = getImageUri(SignInActivity.this, bmp);
                selected_image_path = getRealPathFromURI(selectedImage);
                Toast.makeText(SignInActivity.this, "Here " + getRealPathFromURI(selectedImage), Toast.LENGTH_LONG).show();
                upload_image();
            } else if (requestCode == SELECT_FILE) {
                Uri selectedImage = imageReturnedIntent.getData();
                profile_pic.setImageURI(selectedImage);
                File new_file = new File(selectedImage.getPath());
                selected_image_path = getRealPathFromURI(selectedImage);
                Log.e("selected_image_path", selected_image_path);
                upload_image();
            }
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
    private void upload_image(){
        final ProgressBar progressBar = new ProgressBar(this);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait image is loading..");
        progressDialog.setIndeterminate(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);
        progressDialog.show();
        Ion.with(this)
                .load(Session.SERVER_URL+"add-member-image.php")
                .uploadProgressBar(progressBar)
                .uploadProgressHandler(new ProgressCallback() {
                    @Override
                    public void onProgress(long downloaded, long total) {
                        Log.e(String.valueOf(downloaded),String.valueOf(total));
                        progressDialog.setMax((int)total);
                        progressDialog.setProgress((int) downloaded);
                    }
                })
                .setMultipartParameter("member_id", Session.GetUserId(this))
                .setMultipartFile("file", "image/png", new File(selected_image_path))
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (progressDialog!=null)
                            progressDialog.dismiss();
                        if(e!=null)
                            e.printStackTrace();
                        else {
                            try {
                                if(result.isJsonNull())
                                    Log.e("json_null", "null");
                                else {
                                    Log.e("image_upload_res", result.toString());
                                    add_success();
                                }

                            }catch (Exception e1){
                                e1.printStackTrace();
                            }

                        }

                    }
                });
    }

    public void add_success(){
        Toast.makeText(SignInActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(SignInActivity.this,LoginActivity.class);
        startActivity(intent);
        SignInActivity.this.onBackPressed();
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI,null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }
}
