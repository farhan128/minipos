package ahmedt.minipos.Helper;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;

public class MiniPOS extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AndroidNetworking.initialize(this);
    }
}
