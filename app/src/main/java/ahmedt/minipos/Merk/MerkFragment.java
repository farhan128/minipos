package ahmedt.minipos.Merk;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;

import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.util.ArrayList;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import ahmedt.minipos.Helper.Config;
import ahmedt.minipos.Helper.Utils;
import ahmedt.minipos.Merk.MerkModel.MerkModel;
import ahmedt.minipos.Merk.MerkModel.DataItem;
import ahmedt.minipos.Merk.TambahMerk.TambahMerkActivity;
import ahmedt.minipos.R;
import okhttp3.Response;

import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;

import android.view.Menu;

import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;

import android.app.SearchManager;
import android.widget.EditText;
import android.graphics.Color;
import android.text.InputFilter;
import android.text.Spanned;


import android.view.ViewGroup;
import android.view.MenuInflater;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseAndParsedRequestListener;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */


public class MerkFragment extends Fragment {


    private RecyclerView recyclerView;

    // @BindView(R.id.recycler_view)
    // RecyclerView recyclerView;


    // @BindView(R.id.swipe_refresh_recycler_list)
    // SwipeRefreshLayout swipeRefreshRecyclerList;

    private SwipeRefreshLayout swipeRefreshRecyclerList;
    private MerkAdapter mAdapter;
    private ProgressBar progressBar;
    private ImageView imgNote;
    private TextView txtNote;

    private ArrayList<DataItem> modelList = new ArrayList<>();


    public MerkFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_all_item, container, false);


        findViews(view);

        return view;

    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setAdapter();

        swipeRefreshRecyclerList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                // Do your stuff on refresh
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setAdapter();
                        if (swipeRefreshRecyclerList.isRefreshing())
                            swipeRefreshRecyclerList.setRefreshing(false);
                    }
                }, 5000);

            }
        });


    }


    private void findViews(View view) {

        progressBar = (ProgressBar) view.findViewById(R.id.progres_bar);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        swipeRefreshRecyclerList = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_recycler_list);
        mAdapter = new MerkAdapter(getActivity(), modelList);
        recyclerView.setHasFixedSize(true);
        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        txtNote = (TextView) view.findViewById(R.id.txt_notice);
        imgNote = (ImageView) view.findViewById(R.id.img_notice);
        view.findViewById(R.id.floating).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), TambahMerkActivity.class), 333);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            setAdapter();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_search, menu);

        // Retrieve the SearchView and plug it into SearchManager
        final SearchView searchView = (SearchView) MenuItemCompat
                .getActionView(menu.findItem(R.id.action_search));

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(getActivity().SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        //changing edittext color
        EditText searchEdit = ((EditText) searchView.findViewById(androidx.appcompat.R.id.search_src_text));
        searchEdit.setTextColor(Color.WHITE);
        searchEdit.setHintTextColor(Color.WHITE);
        searchEdit.setBackgroundColor(Color.TRANSPARENT);
        searchEdit.setHint("Search");

        InputFilter[] fArray = new InputFilter[2];
        fArray[0] = new InputFilter.LengthFilter(40);
        fArray[1] = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

                for (int i = start; i < end; i++) {

                    if (!Character.isLetterOrDigit(source.charAt(i)))
                        return "";
                }


                return null;


            }
        };
        searchEdit.setFilters(fArray);
        View v = searchView.findViewById(androidx.appcompat.R.id.search_plate);
        v.setBackgroundColor(Color.TRANSPARENT);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ArrayList<ahmedt.minipos.Merk.MerkModel.DataItem> filterList = new ArrayList<ahmedt.minipos.Merk.MerkModel.DataItem>();
                if (s.length() > 0) {
                    for (int i = 0; i < modelList.size(); i++) {
                        if (modelList.get(i).getNama().toLowerCase().contains(s.toString().toLowerCase())) {
                            filterList.add(modelList.get(i));
                            mAdapter.updateList(filterList);
                        }
                    }

                } else {
                    mAdapter.updateList(modelList);
                }
                return false;
            }
        });

    }


    private void setAdapter() {
        AndroidNetworking.post(Config.URL_getMerk)
                .build()
                .getAsOkHttpResponseAndObject(MerkModel.class, new OkHttpResponseAndParsedRequestListener<MerkModel>() {
                    @Override
                    public void onResponse(Response okHttpResponse, final MerkModel response) {
                        progressBar.setVisibility(View.GONE);
                        if(okHttpResponse.isSuccessful()){
                            modelList.clear();
                            if (response.getMeta().getCode()==200){
                                imgNote.setVisibility(View.GONE);
                                txtNote.setVisibility(View.GONE);
                                for (int i = 0;i<response.getData().size();i++){
                                    final DataItem items = new DataItem();
                                    items.setId(response.getData().get(i).getId());
                                    items.setNama(response.getData().get(i).getNama());
                                    items.setNamaMerk(response.getData().get(i).getNamaMerk());
                                    items.setImg(response.getData().get(i).getImg());
                                    modelList.add(items);
                                }
                                mAdapter.updateList(modelList);
                                mAdapter.SetOnItemClickListener(new MerkAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position, final DataItem model) {
                                        //handle item click events here
//                                Toast.makeText(getActivity(), "Hey " + model.getNama(), Toast.LENGTH_SHORT).show();
                                        showDialog(model.getId(),model.getNama(),model.getImg());
                                    }
                                });
                            }else{
                                imgNote.setVisibility(View.VISIBLE);
                                txtNote.setVisibility(View.VISIBLE);
                                imgNote.setImageResource(R.drawable.box);
                                txtNote.setText("Item not Found");
                            }
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        progressBar.setVisibility(View.GONE);
                        Log.d("eror", "onError: "+anError.getErrorDetail());
                        if (modelList.size()>0){
                            Toast.makeText(getActivity(), "No Connection",Toast.LENGTH_LONG).show();
                        }else {
                            imgNote.setVisibility(View.VISIBLE);
                            txtNote.setVisibility(View.VISIBLE);
                            imgNote.setImageResource(R.drawable.no_wifi);
                            txtNote.setText("No Connection");
                        }

                    }
                });
    }

    private void showDialog(final String id, final String nama, final String img){
        final AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
        dialog.setTitle("Konfirmasi");
        dialog.setMessage("Pilih Tindakan");
        dialog.setCancelable(false);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(getActivity(), TambahMerkActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("nama", nama);
                intent.putExtra("img", img);
                intent.putExtra("edit", true);
                startActivityForResult(intent, 333);
            }
        });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Hapus", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                hapusMerk(id);
                dialog.dismiss();
            }
        });
        dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void hapusMerk(String id){
        AndroidNetworking.post(Config.URL_HAPUS_MERK)
                .addBodyParameter("id", id)
                .build()
                .getAsOkHttpResponseAndObject(MerkModel.class, new OkHttpResponseAndParsedRequestListener<MerkModel>() {
                    @Override
                    public void onResponse(Response okHttpResponse, MerkModel response) {
                        if (okHttpResponse.isSuccessful()){
                            if (response.getMeta().getCode()==200){
                                Utils.ShowToast(getActivity(), response.getMeta().getMsg());
                                setAdapter();
                            }
                            Utils.ShowToast(getActivity(), response.getMeta().getMsg());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("frgTmbh", "onError: "+anError.getErrorDetail());
                    }
                });
    }

}
