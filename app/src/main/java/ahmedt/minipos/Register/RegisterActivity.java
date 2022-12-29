package ahmedt.minipos.Register;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseAndParsedRequestListener;
import com.google.android.material.textfield.TextInputEditText;
import com.kaopiz.kprogresshud.KProgressHUD;

import ahmedt.minipos.Helper.Config;
import ahmedt.minipos.Helper.Utils;
import ahmedt.minipos.R;
import ahmedt.minipos.Register.RegisterModel.RegisterModel;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {
    private TextInputEditText txt_nama, txt_hp, txt_email, txt_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        txt_nama    = findViewById(R.id.edt_nama);
        txt_hp      = findViewById(R.id.edt_hp_regis);
        txt_email   = findViewById(R.id.edt_email);
        txt_password= findViewById(R.id.edt_password_regis);

        findViewById(R.id.btn_login_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              finish();
            }
        });

        findViewById(R.id.btn_register_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namaa,hpp,emaill,passwordd;
                namaa = txt_nama.getText().toString().trim();
                hpp = txt_hp.getText().toString().trim();
                emaill = txt_email.getText().toString().trim();
                passwordd = txt_password.getText().toString().trim();
                    DaftarAkun(namaa,hpp,emaill,passwordd);
            }
        });
    }
    private void DaftarAkun(String nama, String hp, String email, String password){
        final KProgressHUD hud = new KProgressHUD(this);
        Utils.Loading(hud,null,null,false);
        AndroidNetworking.post(Config.URL_REGIS)
                .addBodyParameter("nama", nama)
                .addBodyParameter("hp",hp)
                .addBodyParameter("email",email)
                .addBodyParameter("password",password)
                .build()
                .getAsOkHttpResponseAndObject(RegisterModel.class, new OkHttpResponseAndParsedRequestListener<RegisterModel>() {
                    @Override
                    public void onResponse(Response okHttpResponse, RegisterModel response) {
                        Log.d("regis", "onResponse: "+response.toString());
                        hud.dismiss();
                        if (okHttpResponse.isSuccessful()){
                            if (response.getMeta().getCode()==Utils.CodeSukses){
                               Utils.ShowToast(RegisterActivity.this,response.getMeta().getMsg());
                                finish();
                            }else{
                                Utils.ShowToast(RegisterActivity.this,response.getMeta().getMsg());
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        hud.dismiss();
                        Utils.ShowToast(RegisterActivity.this, "Periksa Jaringan Anda");
                        Log.d("Regist", "onError: "+anError.getErrorDetail());
                        Log.d("Regist", "onError: "+anError.getErrorBody());
                        Log.d("Regist", "onError: "+anError.getErrorCode());
                    }
                });

    }
}
