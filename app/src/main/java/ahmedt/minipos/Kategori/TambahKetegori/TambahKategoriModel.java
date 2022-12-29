package ahmedt.minipos.Kategori.TambahKetegori;


import com.google.gson.annotations.SerializedName;


public class TambahKategoriModel{

	@SerializedName("meta")
	private Meta meta;

	public void setMeta(Meta meta){
		this.meta = meta;
	}

	public Meta getMeta(){
		return meta;
	}

	@Override
 	public String toString(){
		return 
			"TambahKategoriModel{" + 
			"meta = '" + meta + '\'' + 
			"}";
		}
}