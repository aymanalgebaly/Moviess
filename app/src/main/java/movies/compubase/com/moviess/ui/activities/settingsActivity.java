package movies.compubase.com.moviess.ui.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import movies.compubase.com.moviess.R;
import movies.compubase.com.moviess.data.API;
import movies.compubase.com.moviess.helper.RetrofitClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class settingsActivity extends AppCompatActivity {

    @BindView(R.id.user_img)
    ImageView userImg;
    @BindView(R.id.name_value)
    TextView nameValue;
    @BindView(R.id.lin_settings)
    LinearLayout linSettings;
    @BindView(R.id.username_edit)
    EditText usernameEdit;
    @BindView(R.id.first_name_edit)
    EditText firstNameEdit;
    @BindView(R.id.last_name_edit)
    EditText lastNameEdit;
    @BindView(R.id.phone_edit)
    EditText phoneEdit;
    @BindView(R.id.pass_edit)
    EditText passEdit;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.mail_edit)
    EditText mailEdit;

    private int GALLERY_REQUEST_CODE = 1;

    private SharedPreferences preferences;
    private String id, fname, lname, email, phone, pass, username, image;

    StorageReference storageReference;
    FirebaseStorage storage;

    Uri filePath;

    String imageURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        FirebaseApp.initializeApp(settingsActivity.this);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        preferences = getSharedPreferences("user", MODE_PRIVATE);
        id = preferences.getString("id", "");
        fname = preferences.getString("fname", "");
        lname = preferences.getString("lname", "");
        email = preferences.getString("email", "");
        phone = preferences.getString("phone", "");
        image = preferences.getString("image", "");
        pass = preferences.getString("pass", "");
        username = preferences.getString("username", "");

        usernameEdit.setText(username);
        firstNameEdit.setText(fname);
        lastNameEdit.setText(lname);
        mailEdit.setText(email);
        phoneEdit.setText(phone);
        passEdit.setText(pass);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }

    }

    @OnClick({R.id.user_img, R.id.btn_save, R.id.btn_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.user_img:
                pickFromGallery();
                break;
            case R.id.btn_save:
                updateData();
                break;
            case R.id.btn_back:
                onBackPressed();
                break;
        }
    }

    private void updateData() {

        Retrofit retrofit = RetrofitClient.getInstant();
        API api = retrofit.create(API.class);
        Call<ResponseBody> responseBodyCall = api.UpdateData(username, fname, lname, email, pass, phone, image, id);
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()){
                    try {
                        String string = response.body().string();

                        if (string.equals("True")){
                            Toast.makeText(settingsActivity.this, "Updated", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(settingsActivity.this,HomeActivity.class));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void pickFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_REQUEST_CODE);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            filePath = data.getData();

            Bitmap bitmap;

            try {

                bitmap = MediaStore.Images.Media.getBitmap(settingsActivity.this.getContentResolver(), filePath);

                userImg.setImageBitmap(bitmap);

                uploadImage(filePath);



            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    private void uploadImage(Uri customfilepath) {

        if(customfilepath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);

            final StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
            ref.putFile(customfilepath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

                                @Override
                                public void onSuccess(Uri uri) {

                                    progressDialog.dismiss();

                                    Toast.makeText(settingsActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();

                                    image = uri.toString();

                                    SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();

                                    preferences = getSharedPreferences("user", MODE_PRIVATE);

                                    editor.putBoolean("login", true);

                                    editor.putString("image", image);
//                                    editor.putString("name",username);
//                                    editor.putString("email",email);
//                                    editor.putString("phone",phone);
//                                    editor.putString("pass",pass);

                                    editor.apply();

                                    Glide.with(settingsActivity.this).load(image).placeholder(R.drawable.anti_man).into(userImg);

                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(settingsActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }
}
