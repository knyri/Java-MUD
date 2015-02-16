/* µCOMP:javac -classpath ".." %sµ
 *
 */

package Pmud.area;

import static Pmud.constants.ChatColors.CLR_WHITE;
import static Pmud.constants.ChatColors.clr_NPC;
import static Pmud.constants.ChatColors.clr_exit;
import static Pmud.constants.ChatColors.clr_room;
import static Pmud.constants.RoomExits.EXIT_DOWN;
import static Pmud.constants.RoomExits.EXIT_EAST;
import static Pmud.constants.RoomExits.EXIT_MAX;
import static Pmud.constants.RoomExits.EXIT_NE;
import static Pmud.constants.RoomExits.EXIT_NORTH;
import static Pmud.constants.RoomExits.EXIT_NW;
import static Pmud.constants.RoomExits.EXIT_SE;
import static Pmud.constants.RoomExits.EXIT_SOUTH;
import static Pmud.constants.RoomExits.EXIT_SW;
import static Pmud.constants.RoomExits.EXIT_UP;
import static Pmud.constants.RoomExits.EXIT_WEST;
import Pmud.Mud;
import Pmud.do_chan;
import Pmud.do_func;
import Pmud.character.Mob;
import Pmud.character.Player;
import Pmud.constants.Bits;
import Pmud.object.Obj;

/**A room in the world
 * <br>Created: 2004
 * @author Kenneth Pierce
 */
public class Room {
	private int[] vnum = new int[2];
	private String title = "";
	private String desc = "";
	private String[][] exdesc = new String[Mud.MAX_EXD][2];
	private Exit[] exits = new Exit[EXIT_MAX];
	private Player[] chs = new Player[Mud.MAX_CHAR];
	private Obj[] objs = new Obj[Mud.MAX_AREA*100];
	private Mob[] mobs = new Mob[Mud.MAX_AREA];
	private byte aff = 0;//default of 8
	public Room() {}

	public Player[] Chars() {return chs;}

	/** Tests to see if the character is in the room
	 * @param ch
	 * @return
	 */
	public boolean inRoom(Player ch) {
		for (int i = 0;i<chs.length;i++) {
			if (ch == chs[i]) {return true;}
		}
		return false;
	}
	/** Tests to see if the object is in the room
	 * @param obj
	 * @return
	 */
	public boolean inRoom(Obj obj) {
		for (int i = 0;i<objs.length;i++) {
			if (obj == objs[i]) {return true;}
		}
		return false;
	}
	/** Tests to see if the mob is in the room
	 * @param mob
	 * @return
	 */
	public boolean inRoom(Mob mob) {
		for (int i = 0;i<mobs.length;i++) {
			if (mob == mobs[i]) {return true;}
		}
		return false;
	}
	/** Adds an exit to the room. Sends an error message to the
	 * immortal adding the exit if the max number of exits have been reached.
	 * @param exit
	 * @param ch
	 */
	public void addExit(Exit exit, Player ch) {
		for (int i = 0;i<exits.length;i++) {
			if (exits[i]==null) {
				exits[i] = exit;
				return;
			}
		}
		do_chan.send_char("Max Exits reached",ch);
	}
	/** Removes an exit. Send an error message to the character
	 * if the exit does not exist.
	 * @param exit
	 * @param ch
	 */
	public void remExit(Exit exit, Player ch) {
		for (int i = 0;i<exits.length;i++) {
			if (exits[i]==exit) {
				exits[i] = null;
				System.arraycopy(exits, i+1, exits, i, (exits.length-1)-i);
				return;
			}
		}
		do_chan.send_char("Exit not found.",ch);
	}
	/** Attempts to get an exit.
	 * @param exit
	 * @param ch
	 * @return the exit or null if the exit doesn't exist.
	 */
	public Exit getExit(byte exit, Player ch) {
		for (int i = 0;i<exits.length;i++) {
			if (exits[i]!=null) {
				if (exits[i].Dir()==exit) {
					return exits[i];
				}
			}
		}
		//do_func.send_char("You run into a wall.",ch);
		return null;
	}

	public boolean addObj(Obj obj) {
		for(int i = 0;i<objs.length;i++) {
			if (objs[i]==null) {
				objs[i]=obj;
				return true;
			}
		}
		return false;
	}
	public void remObj(Obj obj) {
		for (int i = 0;i<objs.length;i++) {
			if (objs[i]==obj) {
				objs[i] = null;
				System.arraycopy(objs, i+1, objs, i, (objs.length-1)-i);
				return;
			}
		}
	}

	public void addChar(Player ch) {
		for(int i = 0;i<chs.length;i++) {
			if (chs[i]==null) {
				chs[i]=ch;
				return;
			}
		}
	}
	public void remChar(Player ch) {
		for (int i = 0;i<chs.length;i++) {
			if (chs[i]==ch) {
				chs[i] = null;
				System.arraycopy(chs, i+1, chs, i, (chs.length-1)-i);
				return;
			}
		}
	}

	public void addMob(Mob ch) {
		for(int i = 0;i<mobs.length;i++) {
			if (mobs[i]==null) {
				mobs[i]=ch;
				return;
			}
		}
	}
	public void remMob(Mob ch) {
		for (int i = 0;i<mobs.length;i++) {
			if (mobs[i]==ch) {
				mobs[i] = null;
				System.arraycopy(mobs, i+1, mobs, i, (mobs.length-1)-i);
				return;
			}
		}
	}

	public void setTitle(String ntitle) {title = ntitle;}
	/**
	 * @param nvnum
	 * @see #setVnum(int, int)
	 */
	public void setVnum(int[] nvnum)	{vnum = nvnum;}
	/**sets the area and room number
	 * @param area
	 * @param room
	 */
	public void setVnum(int area, int room)	{vnum[0] = area;vnum[1] = room;}
	public boolean isAff(int affect)	{return Bits.isSet(aff,affect);}
	public String getAff() {return Pmud.constants.Bits.toString(aff);}
	public void setDesc(String ndesc)	{desc = ndesc;}
	public void setExDesc(String[][] ndesc)	{exdesc = ndesc;}
	public void addExDesc(String name, String desc, Player ch)		{
		for (int i = 0;i<exdesc.length;i++) {
			if (exdesc[i][0]==null) {
				exdesc[i][0] = name.replaceAll(" ","");
				exdesc[i][1] = desc;
				return;
			}
		}
		ch.send("Max Extra Desc reached");
	}
	public void remExDesc(String name, Player ch)		{
		for (int i = 0;i<exdesc.length;i++) {
			if (exdesc[i][0]!=null) {
				if (exdesc[i][0].indexOf(name)>-1) {
					exdesc[i][0] = null;
					exdesc[i][1] = null;
					System.arraycopy(exdesc, i+1, exdesc, i, (exdesc.length-1)-i);
					return;
				}
			} else {break;}
		}
		ch.send(name+" not found.");
	}
	public void setAff(byte affect)				{aff= Bits.set(aff,affect);}
	public void remAff(byte affect)				{aff= Bits.unset(aff,affect);}
	public void setAff(String affect)			{aff = (byte)Bits.parse(affect);}
	/**Gets the array that represents this rooms area vnum and room vnum.
	 * @return
	 */
	public int[] Vnum() {return vnum;}
	/**Gets the vnum of the room's area.
	 * @return
	 */
	public int aVnum() {return vnum[1];}
	/**Gets a string representation of the area and room vnum.
	 * @return
	 */
	public String sVnum() {return "["+vnum[0]+"]["+vnum[1]+"]";}
	/**The title of the room.
	 * @return
	 */
	public String Title() {return title;}
	/**The room's description.
	 * @return
	 */
	public String Desc()	{return desc;}
	/**String representation of the room's exits for display.
	 * @return
	 */
	public String Exits() {
		StringBuffer x = new StringBuffer(50);
		x.append("Exits: ");
		for (int i = 0;i<EXIT_MAX;i++) {
			if (exits[i]!=null) {
				switch(exits[i].Dir()) {
					case EXIT_NORTH:
						x.append(" north");
					break;
					case EXIT_SOUTH:
						x.append(" south");
					break;
					case EXIT_EAST:
						x.append(" east");
					break;
					case EXIT_WEST:
						x.append(" west");
					break;
					case EXIT_NW:
						x.append(" northwest");
					break;
					case EXIT_NE:
						x.append(" northeast");
					break;
					case EXIT_SW:
						x.append(" southwest");
					break;
					case EXIT_SE:
						x.append(" southeast");
					break;
					case EXIT_UP:
						x.append(" up");
					break;
					case EXIT_DOWN:
						x.append(" down");
					break;
				}
			}
		}
		return new String(x);
	}

	/**Attempts to find a character in this room.
	 * @param name The name of the character.
	 * @return the character or null.
	 */
	public Player getChar(String name) {
		for (int i = 0;i<chs.length;i++) {
			if (chs[i]!=null) {
				if (name.equals(chs[i].Name())) {return chs[i];}
			} else {return null;}
		}
		return null;
	}
	/** Attempts to find the monster in this room.
	 * <br>If multiple monsters with the same name exist you can distinguish them
	 * by adding a dot(.) and a number at the end of the name.
	 * @param name The name of the monster.
	 * @return The monster or null.
	 */
	public Mob getMob(String name) {
		int dot = name.lastIndexOf('.');
		if (dot > 0) {
			dot = do_func.parseInt(name.substring(dot));
		}
		for (int i = 0;i<mobs.length;i++) {
			if (mobs[i]!=null) {
				if (mobs[i].equals(name)) {
					if (dot<1) return mobs[i];
					dot--;
				}
			} else {return null;}
		}
		return null;
	}
	/** Attempts to find the object in this room.
	 * <br>If multiple objects with the same name exist you can distinguish them
	 * by adding a dot(.) and a number at the end of the name.
	 * @param name The name of the object.
	 * @return The object or null.
	 */
	public Obj getObj(String name) {
		int dot = name.lastIndexOf('.');
		if (dot > 0) {
			dot = do_func.parseInt(name.substring(dot));
		}
		for (int i = 0;i<objs.length;i++) {
			if (objs[i]!=null) {
				if (objs[i].equals(name)) {
					if (dot<1) return objs[i];
					dot--;
				}
			} else {return null;}
		}
		return null;
	}
	@Override
	public String toString() {
		StringBuffer x = new StringBuffer(1024);
		x.append(CLR_WHITE+Title()+"\n");
		x.append(clr_room+Desc()+"\n");
		x.append(clr_exit+Exits());

		for (int i = 0;i<objs.length;i++) {
			if (objs[i]!=null) {
				x.append("\n"+objs[i].DescAff()+objs[i].lDesc());
			} else {break;}
		}
		for (int i = 0;i<mobs.length;i++) {
			if (mobs[i]!=null) {
				x.append("\n"+clr_NPC+mobs[i].lDesc());
			} else {break;}
		}
		return new String(x).replaceAll("\n","\r\n");
	}
}