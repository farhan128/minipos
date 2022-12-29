package ahmedt.minipos.Register.RegisterModel;


import com.google.gson.annotations.SerializedName;


public class RegisterModel{

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
			"RegisterModel{" + 
			"meta = '" + meta + '\'' + 
			"}";
		}
}