package ahmedt.minipos.Merk.TambahMerk;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseAndParsedRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.io.File;

import ahmedt.minipos.Helper.Config;
import ahmedt.minipos.Helper.Utils;
import ahmedt.minipos.Merk.DialogKategori.DialogKategoriActivity;
import ahmedt.minipos.Merk.TambahMerk.TambahMerkModel;
import ahmedt.minipos.R;
import okhttp3.Response;

public class TambahMerkActivity extends AppCompatActivity {
    public String id_kategori;
    private File fileName;
    private ImageView img;
    public static String ID_KAT = "id_kat";
    private static final String TAG = "TambahMerkActivity";
    private EditText txt_Merk, txt_Kategori ;
    private String id = "";
    private ProgressBar pb;
    private String URL = Config.URL_newMerk;
    private TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_merk);
        img             = findViewById(R.id.img_tambah_merk);
        txt_Merk        = findViewById(R.id.edt_tambah_merk);
        txt_Kategori    = findViewById(R.id.edt_kategori);
        title           = findViewById(R.id.textdaftar);
        pb              = findViewById(R.id.pb);

        if (getIntent().getBooleanExtra("edit", false)){
            title.setText("Edit Merk");
            URL = Config.URL_EDIT_Merk;
            id = getIntent().getStringExtra("id");
            txt_Merk.setText(getIntent().getStringExtra("nama"));
            try{
                Utils.loadGambar(TambahMerkActivity.this, Config.PATH_IMG_MERK+getIntent().getStringExtra("img"), pb, img);
            }catch (Exception e){
                Log.d(TAG, "onCreate: "+e.getMessage().toString());
            }
        }

        findViewById(R.id.img_tambah_merk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(TambahMerkActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(480, 480)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });
        findViewById(R.id.btn_tambah_merk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = txt_Merk.getText().toString();
                tambahMerk(nama,ID_KAT,fileName);
            }
        });

        findViewById(R.id.btn_pilih).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(TambahMerkActivity.this, DialogKategoriActivity.class), 444);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if(requestCode == 444){
                String nama_kategori    = data.getStringExtra(DialogKategoriActivity.NAMA_KATEGORI);
                ID_KAT             = data.getStringExtra(DialogKategoriActivity.ID_KATEGORI);
                txt_Kategori.setText(nama_kategori);
            }
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
        AndroidNetworking.cancel("tambah_Merk");
    }

    private void tambahMerk(String nama,String id, File fileImage){
        final KProgressHUD hud = new KProgressHUD(TambahMerkActivity.this);
        Utils.Loading(hud, null, null, false);
        AndroidNetworking.upload(URL)
                .setTag("tambah_Merk")
                .addMultipartFile("img",fileImage)
                .addMultipartParameter("nama_merk", nama)
                .addMultipartParameter("id_kategori", id)
                .build()
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {

                    }
                }).getAsOkHttpResponseAndObject(TambahMerkModel.class, new OkHttpResponseAndParsedRequestListener<TambahMerkModel>() {
            @Override
            public void onResponse(Response okHttpResponse, TambahMerkModel response) {
                if (okHttpResponse.isSuccessful()){
                    hud.dismiss();
                    Log.d(TAG, "onResponse: "+response.toString());
                    if (response.getMeta().getCode()==200){
                        Utils.ShowToast(TambahMerkActivity.this, response.getMeta().getMsg());
                        setResult(RESULT_OK);
                        finish();
                    }
                    Log.d(TAG, "onResponse: "+response.getMeta().getMsg());
                    Utils.ShowToast(TambahMerkActivity.this, response.getMeta().getMsg());
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
