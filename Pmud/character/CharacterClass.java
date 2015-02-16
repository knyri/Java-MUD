package Pmud.character;

public enum CharacterClass{
	Peasant(0,0,0,0,0,0,0);
	CharacterClass(int str_mod,int con_mod,int int_mod,int wis_mod, int dex_mod,int cha_mod,int luc_mod){
		this.str_mod=str_mod;
		this.int_mod=str_mod;
		this.wis_mod=str_mod;
		this.dex_mod=str_mod;
		this.cha_mod=str_mod;
		this.luc_mod=str_mod;
		this.con_mod=str_mod;
	}
	public final int
	str_mod,
	int_mod,
	dex_mod,
	wis_mod,
	cha_mod,
	luc_mod,
	con_mod;

}
