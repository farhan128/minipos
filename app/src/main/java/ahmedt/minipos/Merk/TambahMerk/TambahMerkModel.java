package ahmedt.minipos.Merk.TambahMerk;


import com.google.gson.annotations.SerializedName;


public class TambahMerkModel{

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
			"TambahMerkModel{" + 
			"meta = '" + meta + '\'' + 
			"}";
		}
}