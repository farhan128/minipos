package ahmedt.minipos.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseAndParsedRequestListener;
import com.google.android.material.textfield.TextInputEditText;
import com.kaopiz.kprogresshud.KProgressHUD;

import ahmedt.minipos.DashBoard.MainActivity;
import ahmedt.minipos.Helper.Config;
import ahmedt.minipos.Helper.Utils;
import ahmedt.minipos.Login.LoginModel.ModelLogin;
import ahmedt.minipos.R;
import ahmedt.minipos.Register.RegisterActivity;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText hp, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        hp          = findViewById(R.id.edt_hp);
        password    = findViewById(R.id.edt_password);
        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });

        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hpp     = hp.getText().toString().trim();
                String pass    = password.getText().toString().trim();
                Login(hpp,pass);
            }
        });
    }

    private void Login(String hp, String password){
        final KProgressHUD hud = new KProgressHUD(this);
        Utils.Loading(hud,null,null,false);
        AndroidNetworking.post(Config.URL_LOGIN)
                .addBodyParameter("hp",hp)
                .addBodyParameter("password",password)
                .build()
                .getAsOkHttpResponseAndObject(ModelLogin.class, new OkHttpResponseAndParsedRequestListener<ModelLogin>() {
                    @Override
                    public void onResponse(Response okHttpResponse, ModelLogin response) {
                        Log.d("Login", "onResponse: "+response.toString());
                        hud.dismiss();
                        if (okHttpResponse.isSuccessful()){
                            if (response.getMeta().getCode()==Utils.CodeSukses){
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                i.putExtra("id",response.getData().getId());
                                i.putExtra("nama",response.getData().getNama());
                                i.putExtra("email",response.getData().getEmail());
                                i.putExtra("hp",response.getData().getHp());
                                startActivity(i);
                                finish();
                                Utils.ShowToast(LoginActivity.this, response.getMeta().getMsg());
                            }else {
                                Utils.ShowToast(LoginActivity.this, response.getMeta().getMsg());
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        hud.dismiss();
                        Utils.ShowToast(LoginActivity.this, "Periksa Jaringan Anda");
                        Log.d("Login", "onError: "+anError.getErrorDetail());
                        Log.d("Login", "onError: "+anError.getErrorBody());
                        Log.d("Login", "onError: "+anError.getErrorCode());

                    }
                });
    }
}
