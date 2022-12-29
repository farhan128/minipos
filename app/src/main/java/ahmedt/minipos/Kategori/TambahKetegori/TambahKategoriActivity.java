package ahmedt.minipos.Kategori.TambahKetegori;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseAndParsedRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.io.File;

import ahmedt.minipos.Helper.Config;
import ahmedt.minipos.Helper.Utils;
import ahmedt.minipos.R;
import okhttp3.Response;

public class TambahKategoriActivity extends AppCompatActivity {

    private File fileName;
    private ImageView img;
    private static final String TAG = "TambahKategoriActivity";
    private EditText txt_kategori ;
    private String id = "";
    private ProgressBar pb;
    private String URL = Config.URL_newKategori;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_kategori);
        img = findViewById(R.id.img_tambah_kategori);
        txt_kategori = findViewById(R.id.edt_tambah_kategori);
        title = findViewById(R.id.textdaftar);

        if (getIntent().getBooleanExtra("edit", false)){
            title.setText("Edit Kategori");
            URL = Config.URL_EDIT_KATEGORI;
            id = getIntent().getStringExtra("id");
            txt_kategori.setText(getIntent().getStringExtra("nama"));
            try{
                Utils.loadGambar(TambahKategoriActivity.this, Config.PATH_IMG_KATEGORI+getIntent().getStringExtra("img"), pb, img);
            }catch (Exception e){
                Log.d(TAG, "onCreate: "+e.getMessage().toString());
            }
        }

        findViewById(R.id.img_tambah_kategori).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(TambahKategoriActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(480, 480)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });
        findViewById(R.id.btn_tambah_kategori).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = txt_kategori.getText().toString();
                tambahKategori(nama,fileName);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == ImagePicker.REQUEST_CODE){
                /**
                 * Keterangan :
                 * untuk mengambil image dari image picker
                 * AMD
                 **/
                fileName = ImagePicker.Companion.getFile(data);
                /**
                 * Keterangan :
                 * untuk meset image yang td di set
                 * AMD
                 **/
                img.setImageURI(Uri.fromFile(fileName));

            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AndroidNetworking.cancel("tambah_kategori");
    }

    private void tambahKategori(String nama, File fileImage){
        final KProgressHUD hud = new KProgressHUD(TambahKategoriActivity.this);
        Utils.Loading(hud, null, null, false);
        AndroidNetworking.upload(URL)
                .setTag("tambah_kategori")
                .addMultipartFile("img",fileImage)
                .addMultipartParameter("nama", nama)
                .addMultipartParameter("id", id)
                .build()
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {

                    }
                }).getAsOkHttpResponseAndObject(TambahKategoriModel.class, new OkHttpResponseAndParsedRequestListener<TambahKategoriModel>() {
            @Override
            public void onResponse(Response okHttpResponse, TambahKategoriModel response) {
                if (okHttpResponse.isSuccessful()){
                    hud.dismiss();
                    Log.d(TAG, "onResponse: "+response.toString());
                    if (response.getMeta().getCode()==200){
                        Utils.ShowToast(TambahKategoriActivity.this, response.getMeta().getMsg());
                        setResult(RESULT_OK);
                        finish();
                    }
                    Log.d(TAG, "onResponse: "+response.getMeta().getMsg());
                    Utils.ShowToast(TambahKategoriActivity.this, response.getMeta().getMsg());
                }
            }

            @Override
            public void onError(ANError anError) {
                hud.dismiss();
                Log.d(TAG, "onError: "+anError.getErrorDetail().toString());
            }
        });
    }
}
