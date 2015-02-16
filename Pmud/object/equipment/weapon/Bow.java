/* µCOMP:javac -classpath ".." %sµ
 *
 */

package Pmud.object.equipment.weapon;

import Pmud.object.equipment.Weapon;

/**A ranged weapon
 * <br>Created: 2004
 * @author Kenneth Pierce
 */
public class Bow extends Weapon {
	private final byte ARROW = 5;
	/*private final byte A_TYPE = 0;
	private final byte NORM = 0;
	private final byte FIRE = 1;
	private final byte ICE = 2;
	private final byte POISON = 3;*/ //will be added later on

	public Bow(short str_mod,short con_mod,short int_mod,short wis_mod,short dex_mod,short cha_mod,short luc_mod,short ac_mod,short al_mod,int hp_mod,int mp_mod,int mv_mod){
		super(str_mod,con_mod,int_mod,wis_mod,dex_mod,cha_mod,luc_mod,ac_mod,al_mod,hp_mod,mp_mod,mv_mod);
	}

	public Bow(){
		// TODO Auto-generated constructor stub
	}

	//public void setArrowType(byte type) {setV(A_TYPE,type);}
	//public void addArrows(short amount) {setV(ARROW,getV(ARROW)+amount);}

	//public void dmg() {setV(ARROW,getV(ARROW)-1);}
}