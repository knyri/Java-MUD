package Pmud.object.equipment;

import Pmud.character.Character;
import Pmud.object.Obj;

public class Equipment extends Obj{

	public Equipment(short str_mod, short con_mod, short int_mod, short wis_mod, short dex_mod, short cha_mod, short luc_mod,
			short ac_mod, short al_mod, int hp_mod, int mp_mod, int mv_mod){
		this.str_mod= str_mod;
		this.int_mod= str_mod;
		this.wis_mod= str_mod;
		this.dex_mod= str_mod;
		this.cha_mod= str_mod;
		this.luc_mod= str_mod;
		this.con_mod= str_mod;
		this.al_mod= al_mod;
		this.ac_mod= ac_mod;
		this.hp_mod= hp_mod;
		this.mp_mod= mp_mod;
		this.mv_mod= mv_mod;
	}
	public Equipment(){}
	public short
		str_mod= 0,
		int_mod= 0,
		dex_mod= 0,
		wis_mod= 0,
		cha_mod= 0,
		luc_mod= 0,
		con_mod= 0,
		al_mod= 0,
		ac_mod= 0;
	public int
		hp_mod= 0,
		mp_mod= 0,
		mv_mod= 0;

	public void applyMods(Character c){
		c.modAC(ac_mod);
		c.modAlign(al_mod);
		c.modMHP(hp_mod);
		c.modMMV(mv_mod);
		c.modMMP(mp_mod);

	}

}
