/* µCOMP:javac -classpath ".." %sµ
 *
 */

package Pmud.area;

import Pmud.Mud;

/**Class for handling area respawns of mobs and objects
 * <br>Created: 2004
 * @author Kenneth Pierce
 */
public class Reset {
	char code;
	int area;
	int room;
	int vnum;
	int amt;
	/**
	 * @param ncode Reset type S for ??, O for object, M for mob, E for ??
	 * @param narea area number
	 * @param nroom room number
	 * @param nvnum vnum for mob or object
	 * @param namt amount to spawn
	 */
	public Reset(char ncode, int narea, int nroom, int nvnum, int namt) {
		code = ncode;
		area = narea;
		room = nroom;
		vnum = nvnum;
		amt = namt;
	}
	public void go() {
		switch(code) {
			case 'S':
				break;
			case 'O':
				Mud.AREAS[area].getRoom(room).addObj(Mud.AREAS[area].getObj(vnum));
				break;
			case 'M':
				Mud.AREAS[area].getRoom(room).addMob(Mud.AREAS[area].getMob(vnum));
				break;
			case 'E':
				break;
		}
	}
}
