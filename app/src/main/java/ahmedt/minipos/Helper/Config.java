package ahmedt.minipos.Helper;

public class Config {
    public static String URL = "http://192.168.1.15/bikinApi/public";
    public static String URL_REGIS = URL+"/user/daftar";
    public static String URL_LOGIN = URL+"/user/login";
    public static String URL_getKategori = URL + "/getKategori";
    public static String URL_getMerk = URL + "/getMerk";
    public static String URL_newMerk = URL + "/setMerk";
    public static String URL_newKategori = URL + "/setKategori";
    public static final String PATH_IMG_KATEGORI = URL +"/img_kategori/";
    public static final String PATH_IMG_MERK = URL +"/img_merk/";
    public static final String URL_HAPUS_KATEGORI = URL +"/setKategori/hapus";
    public static final String URL_HAPUS_MERK = URL +"/setMerk/hapus";
    public static final String URL_EDIT_KATEGORI = URL + "/setKategori/edit";
    public static final String URL_EDIT_Merk = URL + "/setMerk/edit";
}
