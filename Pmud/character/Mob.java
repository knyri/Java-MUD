/* µCOMP:javac -classpath ".." %sµ
 *
 */

package Pmud.character;

import Pmud.Mud;

/**A computer controlled player
 * @author Kenneth Pierce
 */
public class Mob extends Character {
	private int[] vnum = {0,0};
	public Mob() {super(Mud.NULL_CON);}

	public void setLDesc(String ndesc)	{setDesc(ndesc);}
	public void setSDesc(String ndesc)	{setTitle(ndesc);}
	public void setVnum(int[] nvnum) {vnum = nvnum;}
	public void setVnum(int narea, int nvnum) {vnum[0] = narea; vnum[1] = nvnum;}

	public String sDesc()	{return Title();}
	public String lDesc()	{return Desc();}
	public int[] Vnum() {return vnum;}
}