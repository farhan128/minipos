package ahmedt.minipos.Merk.DialogKategori;


import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseAndParsedRequestListener;

import java.util.ArrayList;

import ahmedt.minipos.Helper.Config;
import ahmedt.minipos.Helper.Utils;
import ahmedt.minipos.Kategori.KategoriAdapter;
import ahmedt.minipos.Kategori.KategoriModel.DataItem;
import ahmedt.minipos.Kategori.KategoriModel.KategoriModel;
import ahmedt.minipos.Kategori.TambahKetegori.TambahKategoriActivity;
import ahmedt.minipos.R;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */


public class DialogKategoriActivity extends AppCompatActivity {

    public static final String NAMA_KATEGORI    = "nama_kategori";
    public static final String ID_KATEGORI      = "id_kategori";


    private RecyclerView recyclerView;

    // @BindView(R.id.recycler_view)
    // RecyclerView recyclerView;


    // @BindView(R.id.swipe_refresh_recycler_list)
    // SwipeRefreshLayout swipeRefreshRecyclerList;

    private KategoriAdapter mAdapter;
    private ArrayList<DataItem> modelList = new ArrayList<>();
    private ProgressBar progressBar;
    private Context ctx;


    public DialogKategoriActivity() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_kategori);
        findViews();
        View v = getWindow().getDecorView();
        v.setBackgroundColor(Color.TRANSPARENT);
        WindowManager.LayoutParams wmlp = getWindow().getAttributes();
        wmlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wmlp.gravity = Gravity.CENTER;
        wmlp.windowAnimations = android.R.style.Animation_Toast;
        setFinishOnTouchOutside(false);

    }
    //
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_all_item, container, false);
//        findViews(view);
//        return view;
//    }
//
//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        setAdapter();
//
//        swipeRefreshRecyclerList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//
//                // Do your stuff on refresh
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                     setAdapter();
//                        if (swipeRefreshRecyclerList.isRefreshing())
//                            swipeRefreshRecyclerList.setRefreshing(false);
//                    }
//                }, 5000);
//
//            }
//        });
//
//
//    }


    private void findViews() {
        ctx = this;
        progressBar = (ProgressBar) findViewById(R.id.progres_bar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new KategoriAdapter(ctx, modelList);
        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(ctx);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        findViewById(R.id.btn_tambah_dialog_kategori).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        setAdapter();
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode==RESULT_OK){
//            setAdapter();
//        }
//    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.menu_search, menu);
//
//        // Retrieve the SearchView and plug it into SearchManager
//        final SearchView searchView = (SearchView) MenuItemCompat
//                .getActionView(menu.findItem(R.id.action_search));
//
//        SearchManager searchManager = (SearchManager) getActivity().getSystemService(getActivity().SEARCH_SERVICE);
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
//        //changing edittext color
//        EditText searchEdit = ((EditText) searchView.findViewById(androidx.appcompat.R.id.search_src_text));
//        searchEdit.setTextColor(Color.WHITE);
//        searchEdit.setHintTextColor(Color.WHITE);
//        searchEdit.setBackgroundColor(Color.TRANSPARENT);
//        searchEdit.setHint("Search");
//
//        InputFilter[] fArray = new InputFilter[2];
//        fArray[0] = new InputFilter.LengthFilter(40);
//        fArray[1] = new InputFilter() {
//            @Override
//            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
//
//                for (int i = start; i < end; i++) {
//
//                    if (!Character.isLetterOrDigit(source.charAt(i)))
//                        return "";
//                }
//
//
//                return null;
//
//
//            }
//        };
//        searchEdit.setFilters(fArray);
//        View v = searchView.findViewById(androidx.appcompat.R.id.search_plate);
//        v.setBackgroundColor(Color.TRANSPARENT);
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                ArrayList<DataItem> filterList = new ArrayList<DataItem>();
//                if (s.length() > 0) {
//                    for (int i = 0; i < modelList.size(); i++) {
//                        if (modelList.get(i).getNama().toLowerCase().contains(s.toString().toLowerCase())) {
//                            filterList.add(modelList.get(i));
//                            mAdapter.updateList(filterList);
//                        }
//                    }
//
//                } else {
//                    mAdapter.updateList(modelList);
//                }
//                return false;
//            }
//        });
//
//    }


    private void setAdapter() {
        AndroidNetworking.post(Config.URL_getKategori)
        .build()
        .getAsOkHttpResponseAndObject(KategoriModel.class, new OkHttpResponseAndParsedRequestListener<KategoriModel>() {
            @Override
            public void onResponse(Response okHttpResponse, final KategoriModel response) {
                progressBar.setVisibility(View.GONE);
                if(okHttpResponse.isSuccessful()){
                    modelList.clear();
                    if (response.getMeta().getCode()==200){

                        for (int i = 0;i<response.getData().size();i++){
                            final DataItem items = new DataItem();
                            items.setId(response.getData().get(i).getId());
                            items.setNama(response.getData().get(i).getNama());
                            items.setImg(response.getData().get(i).getImg());
                            modelList.add(items);
                        }
                        mAdapter.updateList(modelList);
                        mAdapter.SetOnItemClickListener(new KategoriAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position, final DataItem model) {
                                //handle item click events here
                                String namaK   = model.getNama();
                                String idK     = model.getId();
                                getKategoriName(namaK, idK);
//                                Toast.makeText(getActivity(), "Hey " + model.getNama(), Toast.LENGTH_SHORT).show();
//                               showDialog(model.getId(),model.getNama(),model.getImg());
                            }
                        });
                    }else{

                    }
                }

            }

            @Override
            public void onError(ANError anError) {
                progressBar.setVisibility(View.GONE);
                Log.d("eror", "onError: "+anError.getErrorDetail());
                if (modelList.size()>0){
                    Toast.makeText(ctx, "No Connection",Toast.LENGTH_LONG).show();
                }else {

                }

            }
        });
    }

    private void getKategoriName(String nama, String id){
        Intent resultIntent = new Intent();
        resultIntent.putExtra(NAMA_KATEGORI, nama);
        resultIntent.putExtra(ID_KATEGORI, id);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

//    private void showDialog(final String id, final String nama, final String img){
//        final AlertDialog dialog = new AlertDialog.Builder(ctx).create();
//        dialog.setTitle("Konfirmasi");
//        dialog.setMessage("Pilih Tindakan");
//        dialog.setCancelable(false);
//        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Edit", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//                Intent intent = new Intent(ctx, TambahKategoriActivity.class);
//                intent.putExtra("id", id);
//                intent.putExtra("nama", nama);
//                intent.putExtra("img", img);
//                intent.putExtra("edit", true);
//                startActivityForResult(intent, 222);
//            }
//        });
//        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Hapus", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                hapusKategori(id);
//                dialog.dismiss();
//            }
//        });
//        dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//        dialog.show();
//    }
//
//    private void hapusKategori(String id){
//        AndroidNetworking.post(Config.URL_HAPUS_KATEGORI)
//                .addBodyParameter("id", id)
//                .build()
//                .getAsOkHttpResponseAndObject(KategoriModel.class, new OkHttpResponseAndParsedRequestListener<KategoriModel>() {
//                    @Override
//                    public void onResponse(Response okHttpResponse, KategoriModel response) {
//                        if (okHttpResponse.isSuccessful()){
//                            if (response.getMeta().getCode()==200){
//                                Utils.ShowToast(ctx, response.getMeta().getMsg());
//                                setAdapter();
//                            }
//                            Utils.ShowToast(ctx, response.getMeta().getMsg());
//                        }
//                    }
//
//                    @Override
//                    public void onError(ANError anError) {
//                        Log.d("frgTmbh", "onError: "+anError.getErrorDetail());
//                    }
//                });
//    }

}
