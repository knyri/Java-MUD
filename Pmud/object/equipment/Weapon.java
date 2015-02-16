/* µCOMP:javac -classpath ".." %sµ
 *
 */

package Pmud.object.equipment;


/**Base class for all weapons
 * <br>Created: 2004
 * @author Kenneth Pierce
 */
public class Weapon extends Equipment {
	private final byte DMGMN	= 5;
	private final byte DMGMX	= 6;

	public Weapon(){}
	public Weapon(short str_mod,short con_mod,short int_mod,short wis_mod,short dex_mod,short cha_mod,short luc_mod,short ac_mod,short al_mod,int hp_mod,int mp_mod,int mv_mod){
		super(str_mod,con_mod,int_mod,wis_mod,dex_mod,cha_mod,luc_mod,ac_mod,al_mod,hp_mod,mp_mod,mv_mod);
	}

	public void setDamMin(int dmg) {setV(DMGMN, dmg);}
	public void setDamMax(int dmg) {setV(DMGMX, dmg);}

	public int DamMin() {return getV(DMGMN);}
	public int DamMax() {return getV(DMGMX);}
}