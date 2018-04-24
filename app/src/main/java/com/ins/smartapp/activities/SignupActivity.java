package com.ins.smartapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ins.smartapp.R;
import com.ins.smartapp.rest.ApiClient;
import com.ins.smartapp.rest.ApiInterface;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    @BindView(R.id.etusername)
    EditText etUsername;

    @BindView(R.id.etpassword)
    EditText etPassword;

    @BindView(R.id.etnama)
    EditText etNama;

    @BindView(R.id.etemail)
    EditText etEmail;

    @BindView(R.id.ethp)
    EditText etHp;

    @BindView(R.id.btnsave)
    Button btnSave;

    ApiInterface apiService;
    ProgressDialog pd;
    private static final String TAG = SignupActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registeruser);
        ButterKnife.bind(this);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
            }
        });

    }
    public void signup(){
        Log.d(TAG,"Menjalankan method Signup");
        pd = ProgressDialog.show(this,null,"Sedang mendaftarkan akun",true,false);

        String stUsername = etUsername.getText().toString();
        String stPassword = etPassword.getText().toString();
        String stNamaLengkap = etNama.getText().toString();
        String stEmail = etEmail.getText().toString();
        String stNoHp = etHp.getText().toString();

        Log.d(TAG,"Mendapatkan data dari edit text");

       apiService.signup(stUsername,stPassword,stNamaLengkap,stEmail,stNoHp).enqueue(new Callback<ResponseBody>() {
           @Override
           public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
               Log.d(TAG, "Berhasil memanggil API");
               if (response.isSuccessful()){
                   pd.dismiss();

                   Log.d(TAG,"Success mendapatkan API");
                   Toast.makeText(SignupActivity.this,"Berhasil mendaftarkan akun",Toast.LENGTH_SHORT).show();
                   Intent i = new Intent(SignupActivity.this, MainActivity.class);
                   i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                   startActivity(i);
                   finish();
               }

           }

           @Override
           public void onFailure(Call<ResponseBody> call, Throwable t) {
                pd.dismiss();
               Log.e(TAG,t.getLocalizedMessage());
               Toast.makeText(SignupActivity.this, "Gagal Mendaftarkan Akun", Toast.LENGTH_SHORT).show();
           }
       });



    }


}
