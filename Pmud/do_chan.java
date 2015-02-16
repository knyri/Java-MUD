/* µCOMP:javac -classpath ".." %sµ
 *
 */

package Pmud;

import static Pmud.Mud.AREAS;
import static Pmud.Mud.CHARS;
import static Pmud.constants.ChatColors.CLR_BLACK;
import static Pmud.constants.ChatColors.CLR_BLUE;
import static Pmud.constants.ChatColors.CLR_CYAN;
import static Pmud.constants.ChatColors.CLR_DBLACK;
import static Pmud.constants.ChatColors.CLR_DBLUE;
import static Pmud.constants.ChatColors.CLR_DCYAN;
import static Pmud.constants.ChatColors.CLR_DGREEN;
import static Pmud.constants.ChatColors.CLR_DPURPLE;
import static Pmud.constants.ChatColors.CLR_DRED;
import static Pmud.constants.ChatColors.CLR_DWHITE;
import static Pmud.constants.ChatColors.CLR_DYELLOW;
import static Pmud.constants.ChatColors.CLR_GREEN;
import static Pmud.constants.ChatColors.CLR_PURPLE;
import static Pmud.constants.ChatColors.CLR_RED;
import static Pmud.constants.ChatColors.CLR_WHITE;
import static Pmud.constants.ChatColors.CLR_YELLOW;
import static Pmud.constants.ChatColors.clr_chat;
import static Pmud.constants.ChatColors.clr_say;
import Pmud.area.Room;
import Pmud.character.Character;

/**Handles channel functions. Say, chat, tell, yell, ect.
 * @author Kenneth Pierce
 */
public final class do_chan {
	/**Sends a message to a character
	 * @param what
	 * @param ch
	 */
	public static void send_char(String what, Character ch) {
		ch.getCon().write(("\r\n"+what+CLR_DWHITE).getBytes());
	}
	/**Sends a message to all characters
	 * @param what
	 */
	public static void send_all(String what) {
		for (int i = 0;i<CHARS.length;i++) {
			if (CHARS[i]!=null) {
				send_char(what, CHARS[i]);
				do_func.do_prompt(CHARS[i]);
			} else {return;}
		}
	}
	/**Sends a message to all players except for the target.
	 * @param what
	 * @param ch
	 */
	public static void send_all_around(String what, Character ch) {
		for (int i = 0;i<CHARS.length;i++) {
			if (CHARS[i]!=null) {
				if (CHARS[i]!=ch) {
					send_char(what, CHARS[i]);
					do_func.do_prompt(CHARS[i]);
				}
			} else {return;}
		}
	}
	/**Sends a message to everyone in the target's room
	 * @param what
	 * @param ch
	 */
	public static void send_room(String what, Character ch) {
		Character[] chs = ch.Room().Chars();
		for (int i = 0; i<chs.length;i++) {
			if (chs[i]!=null) {
				send_char(what, chs[i]);
				if (chs[i]!=ch) {do_func.do_prompt(CHARS[i]);}
			} else {return;}
		}
	}
	/**Send's a message to everyone in the target's room except for the target
	 * @param what
	 * @param ch
	 */
	public static void send_room_around(String what, Character ch) {
		Character[] chs = ch.Room().Chars();
		for (int i = 0; i<chs.length;i++) {
			if (chs[i]!=null) {
				if (chs[i]!=ch) {
					send_char(what, chs[i]);
					do_func.do_prompt(CHARS[i]);
				}
			} else {return;}
		}
	}
	public static void chat(Character ch, String what) {
		what = do_func.trim(what);
		//act_char("%cYou chat \'%s\'",new String[]{what},new String[]{clr_chat});
		send_char(clr_chat+"You chat \'"+what+"\'", ch);
		send_all_around(clr_chat+ch.Name()+" chats \'"+what+"\'", ch);
	}
	public static void say(Character ch, String what) {
		what = do_func.trim(what);
		send_char(clr_say+"You say \'"+what+"\'", ch);
		send_room_around(clr_say+ch.Name()+" says \'"+what+"\'", ch);
	}
	/** Sends a server-wide information message.
	 * @param what
	 */
	public static void info(String what) {
		send_all(CLR_GREEN+"Info: "+what);
	}

	public static void act_char(String what,String[] strings,String[] color, Character ch) {
		for (int i = 0;i<strings.length;i++) {
			what = what.replaceFirst("%s",strings[i]);
		}
		for (int i = 0;i<color.length;i++) {
			what = what.replaceFirst("%c",color[i]);
		}
		if (ch.Sex()==0) {
			what = what.replaceAll("%p","its");
			what = what.replaceAll("%o","it");
			what = what.replaceAll("%a","it");
		} else if (ch.Sex()==1) {
			what = what.replaceAll("%p","his");
			what = what.replaceAll("%o","he");
			what = what.replaceAll("%a","him");
		} else if (ch.Sex()==2) {
			what = what.replaceAll("%p","hers");
			what = what.replaceAll("%o","she");
			what = what.replaceAll("%a","her");
		}
		send_char(what, ch);
	}
	public static void act_char_around(String what,String[] strings,String[] color, Character ch) {
		for (int i = 0;i<strings.length;i++) {
			what = what.replaceFirst("%s",strings[i]);
		}
		for (int i = 0;i<color.length;i++) {
			what = what.replaceFirst("%c",color[i]);
		}
		if (ch.Sex()==0) {
			what = what.replaceAll("%p","its");
			what = what.replaceAll("%o","it");
			what = what.replaceAll("%a","it");
		} else if (ch.Sex()==1) {
			what = what.replaceAll("%p","his");
			what = what.replaceAll("%o","he");
			what = what.replaceAll("%a","him");
		} else if (ch.Sex()==2) {
			what = what.replaceAll("%p","hers");
			what = what.replaceAll("%o","she");
			what = what.replaceAll("%a","her");
		}
		send_room_around(what, ch);
	}
	public static void act_char_around(String what, String[] color, Character ch) {
			if (color!=null) {
				for (int i = 0;i<color.length;i++) {
					what = what.replaceFirst("%c",color[i]);
				}
			}
			if (ch.Sex()==0) {
				what = what.replaceAll("%p","its");
				what = what.replaceAll("%o","it");
				what = what.replaceAll("%a","it");
			} else if (ch.Sex()==1) {
				what = what.replaceAll("%p","his");
				what = what.replaceAll("%o","he");
				what = what.replaceAll("%a","him");
			} else if (ch.Sex()==2) {
				what = what.replaceAll("%p","hers");
				what = what.replaceAll("%o","she");
				what = what.replaceAll("%a","her");
			}
			send_room_around(what, ch);
	}
	public static void send_area(String what, int vnum) {
		Room[] temp = AREAS[vnum].getRooms();
		for (int i = 0; i<temp.length; i++) {
			if (AREAS[vnum].getRoom(i)!=null) {
				Character[] chs = AREAS[vnum].getRoom(i).Chars();
				for (int j = 0; j<chs.length;j++) {
					if (chs[j]!=null) {
						send_char(what, chs[j]);
					}else{
						return;
//System.out.println("NULL PC in "+areas[vnum].Name()+"("+vnum+") in room "+i);
					}
				}
			}
		}
	}
	public static String colorize(String string) {
		string = string.replaceAll("&x", CLR_DBLACK);
		string = string.replaceAll("&g", CLR_GREEN);
		string = string.replaceAll("&b", CLR_BLUE);
		string = string.replaceAll("&X", CLR_BLACK);
		string = string.replaceAll("&G", CLR_DGREEN);
		string = string.replaceAll("&B", CLR_DBLUE);
		string = string.replaceAll("&c", CLR_CYAN);
		string = string.replaceAll("&r", CLR_RED);
		string = string.replaceAll("&p", CLR_PURPLE);
		string = string.replaceAll("&C", CLR_DCYAN);
		string = string.replaceAll("&R", CLR_DRED);
		string = string.replaceAll("&P", CLR_DPURPLE);
		string = string.replaceAll("&y", CLR_YELLOW);
		string = string.replaceAll("&w", CLR_WHITE);
		string = string.replaceAll("&Y", CLR_DYELLOW);
		string = string.replaceAll("&W", CLR_DWHITE);
		string = string.replaceAll("&/", "&");
		return string;
	}
	public static String colorize(StringBuffer x) {
		return colorize(new String(x));
	}
}