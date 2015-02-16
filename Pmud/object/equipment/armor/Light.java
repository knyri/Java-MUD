/* µCOMP:javac -classpath ".." %sµ
 *
 */

package Pmud.object.equipment.armor;

import Pmud.object.equipment.Armor;


/**Light for dark rooms
 * @author Kenneth Pierce
 */
public class Light extends Armor {
	private final byte HOURS	= 5;//hours left before lights out

	public Light(short str_mod,short con_mod,short int_mod,short wis_mod,short dex_mod,short cha_mod,short luc_mod,short ac_mod,short al_mod,int hp_mod,int mp_mod,int mv_mod){
		super(str_mod,con_mod,int_mod,wis_mod,dex_mod,cha_mod,luc_mod,ac_mod,al_mod,hp_mod,mp_mod,mv_mod);
	}

	public Light(){
		// TODO Auto-generated constructor stub
	}

	public int Hours() {return getV(HOURS);}
}