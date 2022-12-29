package ahmedt.minipos.Login.LoginModel;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("password")
	private String password;

	@SerializedName("img")
	private String img;

	@SerializedName("nama")
	private String nama;

	@SerializedName("hp")
	private String hp;

	@SerializedName("id")
	private String id;

	@SerializedName("email")
	private String email;

	@SerializedName("timestamp")
	private String timestamp;

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

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

	public void setHp(String hp){
		this.hp = hp;
	}

	public String getHp(){
		return hp;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
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
			"Data{" + 
			"password = '" + password + '\'' + 
			",img = '" + img + '\'' + 
			",nama = '" + nama + '\'' + 
			",hp = '" + hp + '\'' + 
			",id = '" + id + '\'' + 
			",email = '" + email + '\'' + 
			",timestamp = '" + timestamp + '\'' + 
			"}";
		}
}