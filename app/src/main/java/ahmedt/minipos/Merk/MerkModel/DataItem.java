package ahmedt.minipos.Merk.MerkModel;


import com.google.gson.annotations.SerializedName;


public class DataItem{

	@SerializedName("img")
	private String img;

	@SerializedName("id_kategori")
	private String idKategori;

	@SerializedName("nama")
	private String nama;

	@SerializedName("id")
	private String id;

	@SerializedName("nama_merk")
	private String namaMerk;

	@SerializedName("timestamp")
	private String timestamp;

	public void setImg(String img){
		this.img = img;
	}

	public String getImg(){
		return img;
	}

	public void setIdKategori(String idKategori){
		this.idKategori = idKategori;
	}

	public String getIdKategori(){
		return idKategori;
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

	public void setNamaMerk(String namaMerk){
		this.namaMerk = namaMerk;
	}

	public String getNamaMerk(){
		return namaMerk;
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
			",id_kategori = '" + idKategori + '\'' + 
			",nama = '" + nama + '\'' + 
			",id = '" + id + '\'' + 
			",nama_merk = '" + namaMerk + '\'' + 
			",timestamp = '" + timestamp + '\'' + 
			"}";
		}
}