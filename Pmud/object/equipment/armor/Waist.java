/* µCOMP:javac -classpath ".." %sµ
 *
 */

package Pmud.object.equipment.armor;

import Pmud.object.Obj;
import Pmud.object.equipment.Armor;
import Pmud.object.equipment.Weapon;

public class Waist extends Armor {
	/** 5 bag slots or maybe potion quick slots */
	private final Obj[] slots = new Obj[5];
	/** 2 weapon slots */
	private final Weapon[] sheath = new Weapon[2];

	public Waist(short str_mod,short con_mod,short int_mod,short wis_mod,short dex_mod,short cha_mod,short luc_mod,short ac_mod,short al_mod,int hp_mod,int mp_mod,int mv_mod){
		super(str_mod,con_mod,int_mod,wis_mod,dex_mod,cha_mod,luc_mod,ac_mod,al_mod,hp_mod,mp_mod,mv_mod);
	}

	public Waist(){
		// TODO Auto-generated constructor stub
	}

	public Obj getObj(String name) {
		for (int i = 0;i<slots.length;i++) {
			if (slots[i]!=null) {
				if (slots[i].equals(name)) {return slots[i];}
			} else {return null;}
		}
		return null;
	}
	public boolean addObj(Obj add)	{
		for (int i = 0;i<slots.length;i++) {
			if (slots[i]==null) {
				slots[i] = add;
				return true;
			}
		}
		return false;
	}
	public void remObj(Obj rem)	{
		for (int i = 0;i<slots.length;i++) {
			if (slots[i]!=null) {
				if (slots[i]==rem) {
					slots[i] = null;
					System.arraycopy(slots, i+1, slots, i, (slots.length-1)-i);
					return;
				}
			} else {return;}
		}
	}
}