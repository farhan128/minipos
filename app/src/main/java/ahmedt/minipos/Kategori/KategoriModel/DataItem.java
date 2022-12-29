package ahmedt.minipos.Kategori.KategoriModel;


import com.google.gson.annotations.SerializedName;


public class DataItem{

	@SerializedName("img")
	private String img;

	@SerializedName("nama")
	private String nama;

	@SerializedName("id")
	private String id;

	@SerializedName("timestamp")
	private String timestamp;

	public void setImg(String img){
		this.img = img;
	}

	public String getImg(){
		return img;
	}

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setTimestamp(String timestamp){
		this.timestamp = timestamp;
	}

	public String getTimestamp(){
		return timestamp;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"img = '" + img + '\'' + 
			",nama = '" + nama + '\'' + 
			",id = '" + id + '\'' + 
			",timestamp = '" + timestamp + '\'' + 
			"}";
		}
}