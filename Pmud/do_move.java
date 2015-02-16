/* µCOMP:javac -classpath ".." %sµ
 *
 */

package Pmud;

import static Pmud.Mud.AREAS;
import static Pmud.Mud.LVL_IMM;
import static Pmud.Mud.MAX_AREA;
import static Pmud.constants.Affects.Char.AFF_FLY;
import static Pmud.constants.RoomExits.EXIT_DOWN;
import static Pmud.constants.RoomExits.EXIT_EAST;
import static Pmud.constants.RoomExits.EXIT_NE;
import static Pmud.constants.RoomExits.EXIT_NORTH;
import static Pmud.constants.RoomExits.EXIT_NW;
import static Pmud.constants.RoomExits.EXIT_SE;
import static Pmud.constants.RoomExits.EXIT_SOUTH;
import static Pmud.constants.RoomExits.EXIT_SW;
import static Pmud.constants.RoomExits.EXIT_UP;
import static Pmud.constants.RoomExits.EXIT_WEST;
import Pmud.area.Exit;
import Pmud.character.Mob;
import Pmud.character.Player;

/**Contains functions that move a player in the world
 * <br>Created: 2004
 * @author Kenneth Pierce
 */
public final class do_move {
	/** Attempts to move a character east.
	 * @param ch
	 */
	public static void east(Player ch) {
	Exit temp = ch.Room().getExit(EXIT_EAST, ch);
		if (temp!=null) {
			if (temp.Dest()!=null) {
				do_chan.send_room_around(ch.Name()+" leaves east.", ch);
				move_char(ch, temp.DestVnum());
				do_chan.send_room_around(ch.Name()+" arrives from the west.", ch);
			} else {
				do_chan.send_char("Sorry, this exit goes no where. Admins have been informed.",ch);
System.out.println("do_east: temp.Dest() is null!");
			}
		} else {
			do_chan.send_room(ch.Name()+" bumps into the east wall.", ch);
		}
	}
	/** Attempts to moves the character west.
	 * @param ch
	 */
	public static void west(Player ch) {
		Exit temp = ch.Room().getExit(EXIT_WEST, ch);
		if (temp!=null) {
			if (temp.Dest()!=null) {
				do_chan.send_room_around(ch.Name()+" leaves west.", ch);
				move_char(ch, temp.DestVnum());
				do_chan.send_room_around(ch.Name()+" arrives from the east.", ch);
			} else {
				do_chan.send_char("Sorry, this exit goes no where. Admins have been informed.",ch);
System.out.println("do_west: temp.Dest() is null!");
			}
		} else {
			do_chan.send_room(ch.Name()+" bumps into the west wall.", ch);
		}
	}
	/** Attempts to moves the character north.
	 * @param ch
	 */
	public static void north(Player ch) {
		Exit temp = ch.Room().getExit(EXIT_NORTH, ch);
		if (temp!=null) {
			if (temp.Dest()!=null) {
				do_chan.send_room_around(ch.Name()+" leaves north.", ch);
				move_char(ch, temp.DestVnum());
				do_chan.send_room_around(ch.Name()+" arrives from the south.", ch);
			} else {
				do_chan.send_char("Sorry, this exit goes no where. Admins have been informed.",ch);
System.out.println("do_north: temp.Dest() is null!");
			}
		} else {
			do_chan.send_room(ch.Name()+" bumps into the north wall.", ch);
		}
	}
	/** Attempts to moves the character south.
	 * @param ch
	 */
	public static void south(Player ch) {
		Exit temp = ch.Room().getExit(EXIT_SOUTH, ch);
		if (temp!=null) {
			if (temp.Dest()!=null) {
				do_chan.send_room_around(ch.Name()+" leaves south.", ch);
				move_char(ch, temp.DestVnum());
				do_chan.send_room_around(ch.Name()+" arrives from the north.", ch);
			} else {
				do_chan.send_char("Sorry, this exit goes no where. Admins have been informed.",ch);
System.out.println("do_south: temp.Dest() is null!");
			}
		} else {
			do_chan.send_room(ch.Name()+" bumps into the south wall.", ch);
		}
	}
	/** Attempts to moves the character southeast.
	 * @param ch
	 */
	public static void southeast(Player ch) {
		Exit temp = ch.Room().getExit(EXIT_SE, ch);
		if (temp!=null) {
			if (temp.Dest()!=null) {
				do_chan.send_room_around(ch.Name()+" leaves southeast.", ch);
				move_char(ch, temp.DestVnum());
				do_chan.send_room_around(ch.Name()+" arrives from the northwest.", ch);
			} else {
				do_chan.send_char("Sorry, this exit goes no where. Admins have been informed.",ch);
System.out.println("do_SE: temp.Dest() is null!");
			}
		} else {
			do_chan.send_room(ch.Name()+" bumps into the southeast wall.", ch);
		}
	}
	/** Attempts to moves the character southwest.
	 * @param ch
	 */
	public static void southwest(Player ch) {
		Exit temp = ch.Room().getExit(EXIT_SW, ch);
		if (temp!=null) {
			if (temp.Dest()!=null) {
				do_chan.send_room_around(ch.Name()+" leaves southwest.", ch);
				move_char(ch, temp.DestVnum());
				do_chan.send_room_around(ch.Name()+" arrives from the northeast.", ch);
			} else {
				do_chan.send_char("Sorry, this exit goes no where. Admins have been informed.",ch);
System.out.println("do_SW: temp.Dest() is null!");
			}
		} else {
				do_chan.send_room(ch.Name()+" bumps into the southwest wall.", ch);
			}
	}
	/** Attempts to moves the character northeast.
	 * @param ch
	 */
	public static void northeast(Player ch) {
		Exit temp = ch.Room().getExit(EXIT_NE, ch);
		if (temp!=null) {
			if (temp.Dest()!=null) {
				do_chan.send_room_around(ch.Name()+" leaves northeast.", ch);
				move_char(ch, temp.DestVnum());
				do_chan.send_room_around(ch.Name()+" arrives from the southwest.", ch);
			} else {
				do_chan.send_char("Sorry, this exit goes no where. Admins have been informed.",ch);
System.out.println("do_NE: temp.Dest() is null!");
			}
		} else {
			do_chan.send_room(ch.Name()+" bumps into the northeast wall.", ch);
		}
	}
	/** Attempts to moves the character northwest.
	 * @param ch
	 */
	public static void northwest(Player ch) {
		Exit temp = ch.Room().getExit(EXIT_NW, ch);
		if (temp!=null) {
			if (temp.Dest()!=null) {
				do_chan.send_room_around(ch.Name()+" leaves northwest.", ch);
				move_char(ch, temp.DestVnum());
				do_chan.send_room_around(ch.Name()+" arrives from the southeast.", ch);
			} else {
				do_chan.send_char("Sorry, this exit goes no where. Admins have been informed.",ch);
System.out.println("do_NW: temp.Dest() is null!");
			}
		} else {
			do_chan.send_room(ch.Name()+" bumps into the northwest wall.", ch);
		}
	}
	/** Attempts to moves the character up.
	 * <br>Fails if the character cannot climb
	 * or fly.
	 * @param ch
	 */
	public static void up(Player ch) {
		Exit temp = ch.Room().getExit(EXIT_UP, ch);
		if (temp!=null) {
			if (temp.Dest()!=null) {
				if (ch.isAff(AFF_FLY)) {
					do_chan.send_room_around(ch.Name()+" flies up.", ch);
					move_char(ch, temp.DestVnum());
					do_chan.send_room_around(ch.Name()+" flies from below.", ch);
				} else {
					do_chan.send_char("Whoops! You can't fly.", ch);
				}
			} else {
				do_chan.send_char("Sorry, this exit goes no where. Admins have been informed.",ch);
System.out.println("do_up: temp.Dest() is null!");
			}
		}
	}
 	/** Attempts to moves the character down.
 	 * <br>If the character cannot climb, fly, or float it
 	 * suffers fall damage.
 	 * @param ch
 	 */
 	public static void down(Player ch) {
 		Exit temp = ch.Room().getExit(EXIT_DOWN, ch);
		if (temp!=null) {
			if (temp.Dest()!=null) {
				do_chan.send_room_around(ch.Name()+" leaves down.", ch);
				move_char(ch, temp.DestVnum());
				do_chan.send_room_around(ch.Name()+" arrives from above.", ch);
			} else {
				do_chan.send_char("Sorry, this exit goes no where. Admins have been informed.",ch);
System.out.println("do_down: temp.Dest() is null!");
			}
		}
 	}
	/** Entrance message.
	 * @param ch
	 * @param what
	 */
	public static void enter(Player ch, String what) {
		if (do_func.trim(what).equals("")||what==null) {what = new String("\u0008");}
		do_chan.send_char("You you enter "+what+" and reappear in the void.", ch);
	}
	/** Leave message.
	 * @param ch
	 * @param what
	 */
	public static void leave(Player ch, String what) {
		if (do_func.trim(what).equals("")||what==null) {what = new String("\u0008");}else{what="via "+what;}
		do_chan.send_char("You you leave "+what+" and reappear in the void.", ch);
	}
	/**Moves the player to the specified spot in the world.
	 * <br>Can only be done by an immortal.
	 * @param ch
	 * @param where
	 */
	public static void do_goto(Player ch, String where) {
		if (ch.Level()<LVL_IMM) {do_chan.send_char("You lack the experience to do this.", ch);return;}
		if (do_func.trim(where).split(" ").length!=2||do_func.NaN(do_func.trim(where).split(" ")[0])||do_func.NaN(do_func.trim(where).split(" ")[1])) {
			do_chan.send_char("Illegal argument. Syntax: goto <areavnum> <roomvnum>\r\nSpiffyness will be added later.", ch);
		}
		int a = do_func.parseInt(do_func.trim(where).split(" ")[0]);
		if ((a>-1)&&(a<(MAX_AREA))) {
			if (AREAS[a]==null) {
				do_chan.send_char("Area does not exist.", ch);
			} else {
				int i = do_func.parseInt(do_func.trim(where).split(" ")[1]);
				if ((i<AREAS[a].getRooms().length)&&(i>-1)) {
					if (AREAS[a].getRoom(i) == null) {
						do_chan.send_char("Room does not exist.", ch);
					} else {
						move_char(ch, new int[]{a,i});
					}
				} else {
					do_chan.send_char("Area index out of range. Please enter a number in the range of 0-"+(AREAS[a].getRooms().length-1)+".<inclusive>", ch);
				}
			}
		} else {
			do_chan.send_char("Area index out of range. Please enter a number in the range of 0-"+(MAX_AREA-1)+".<inclusive>", ch);
		}
	}
	/**Moves a character to the new room
	 * @param ch
	 * @param where {area vnum, room vnum}
	 */
	public static void move_char(Player ch, int[] where) {
		ch.Room().remChar(ch);
		AREAS[where[0]].getRoom(where[1]).addChar(ch);
		ch.setRoom(where);
		do_char.look(ch, null);
	}
	/**Moves a mob to the new room
	 * @param ch
	 * @param where {area vnum, room vnum}
	 */
	public static void move_mob(Mob ch, int[] where) {
		ch.Room().remMob(ch);
		AREAS[where[0]].getRoom(where[1]).addMob(ch);
		ch.setRoom(where);
	}
}