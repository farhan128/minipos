package ahmedt.minipos.DashBoard;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;


import ahmedt.minipos.Kategori.KategoriFragment;
import ahmedt.minipos.Merk.MerkFragment;
import ahmedt.minipos.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private  DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        View view = navigationView.getHeaderView(0);
        ShowFrag(new DashboardActivity());
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);
    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
//                || super.onSupportNavigateUp();
//    }
    private boolean ShowFrag(Fragment frag){
//        if (drawer.isDrawerOpen(GravityCompat.START)){
//            drawer.closeDrawer(GravityCompat.START);
//        }
        if (frag != null){
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, frag).commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
        super.onBackPressed();}
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        //tambah methode runnable, supaya navdraw ga kenceng

        final int id = menuItem.getItemId();
        drawer.postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (id){
                    case R.id.nav_home:
                        ShowFrag(new DashboardActivity());
                        break;
                    case R.id.nav_kategori:
                        ShowFrag(new KategoriFragment());
                        break;
                    case R.id.nav_slideshow:
                        ShowFrag(new MerkFragment());
                        break;

                    case R.id.nav_send:

                    case R.id.nav_share:
                }
            }
        }, 300);
       drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
