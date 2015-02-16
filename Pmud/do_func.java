/* µCOMP:javac -classpath ".." %sµ
 *
 */

package Pmud;
/*
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import simple.FileIO;*/
import static Pmud.Mud.AREAS;
import static Pmud.Mud.CHARS;
import static Pmud.Mud.DAY;
import static Pmud.Mud.LVL_IMM;
import static Pmud.Mud.LVL_REB;
import static Pmud.Mud.MAX_AREA;
import static Pmud.Mud.MAX_CHAR;
import static Pmud.Mud.POS_FIGHT;
import static Pmud.Mud.SEX;
import static Pmud.Mud.log;
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
import static Pmud.constants.ChatColors.clr_time;

import java.util.Date;
import java.util.TimerTask;

import Pmud.area.Area;
import Pmud.character.Character;
import Pmud.character.CharacterClass;
import Pmud.character.CharacterRace;
import Pmud.character.Player;
import Pmud.object.Obj;

/**Contains common miscellaneous MUD functions
 * @author Kenneth Pierce
 */
public final class do_func {
	static int MCHOUR = 1,//current mhour of the mday
	MCDAY = 0,//current mday of the mmonth(28 days)
	MCMONTH = 0,//current mmonth of the mmyear(6 months)
	MCYEAR = 0;//current myear(starts at 0 and continues on into infinity)

	/** Parses and executes a command sent by a character.
	 * <br>Unfortunately, all commands are hard coded at this time.
	 * @param ch
	 * @param what
	 */
	public static void do_cmd(Player ch, String what) {
		String cmd = gcmd(what);
		if (what.equals("")) {return;}
		switch(what.charAt(0)) {
			case '\'':
				do_chan.say(ch, what.substring(1,what.length()));
				break;
			case '.':
				do_chan.chat(ch, what.substring(1,what.length()));
				break;
			case 'a':
				if (str_comp(cmd,"affect")) {do_char.affects(ch, what);break;}
			case 'b':
			case 'c':
				if (str_comp(cmd,"chat")) {do_chan.chat(ch, trim(what.substring(cmd.length())));break;} else
				if (str_comp(cmd,"colorize")) {do_colorize(ch, trim(what.substring(cmd.length())));break;}
			case 'd':
				if (str_comp(cmd,"down")) {do_move.down(ch);break;} else
				if (str_comp(cmd,"drop")) {do_char.drop(ch, trim(what.substring(cmd.length())));break;}
			case 'e':
				if (str_comp(cmd,"east")) {do_move.east(ch);break;} else
				if (str_comp(cmd,"enter")) {do_move.enter(ch, arg1(what));break;} else
				if (str_comp(cmd,"equipment")) {do_char.equipment(ch);break;}
			case 'f':
				if (str_comp(cmd,"force")) {do_force(ch, what.substring(cmd.length()));break;}
			case 'g':
				if (str_comp(cmd,"get")) {do_char.get(ch, trim(what.substring(cmd.length())));break;} else
				if (str_comp(cmd,"glance")) {do_char.glance(ch, arg1(what));break;} else
				if (str_comp(cmd,"goto")) {do_move.do_goto(ch, what.substring(cmd.length()));break;}
			case 'h':
				if (str_comp(cmd,"help")) {do_char.help(ch, arg1(what));break;}
			case 'i':
				if (str_comp(cmd,"inventory")) {do_char.inv(ch);break;}
			case 'j':
			case 'k':
			case 'l':
				if (str_comp(cmd,"look")) {do_char.look(ch, arg1(what));break;} else
				if (str_comp(cmd,"leave")) {do_move.leave(ch, arg1(what));break;}
			case 'm':
				if (str_comp(cmd,"make")) {do_make(ch, arg1(what));break;}
			case 'n':
				if (str_comp(cmd,"north")) {do_move.north(ch);break;} else
				if (str_comp(cmd,"northwest")||cmd.equals("nw")) {do_move.northwest(ch);break;} else
				if (str_comp(cmd,"northeast")||cmd.equals("ne")) {do_move.northeast(ch);break;}
			case 'o':
			case 'p':
				if (str_comp(cmd,"prompt")) {do_prompt(ch, trim(what.substring(cmd.length())));break;} else
				if (str_comp(cmd, "pset")) {
					Command com = new Command(trim(what.substring(cmd.length())));
					do_pset(ch, com.Next(), com.Next(), com.Rest());
					break;
				}
			case 'q':
				if (cmd.equals("quit")) {do_char.quit(ch);break;}
			case 'r':
				if (str_comp(cmd, "remove")) {do_char.remove(ch, arg1(what));break;} else
				if (cmd.equals("reboot")) {do_mud_die(ch);break;}
			case 's':
				if (str_comp(cmd,"south")) {do_move.south(ch);break;} else
				if (str_comp(cmd,"southwest")||cmd.equals("sw")) {do_move.southwest(ch);break;} else
				if (str_comp(cmd,"southeast")||cmd.equals("se")) {do_move.southeast(ch);break;} else
				if (str_comp(cmd,"score")) {do_char.score(ch);break;} else
				if (str_comp(cmd,"say")) {do_chan.say(ch, trim(what.substring(cmd.length())));break;} else
				if (str_comp(cmd,"save")) {do_file.do_save(ch);break;}
			case 't':
				if (str_comp(cmd,"time")) {do_time(ch);break;}
			case 'u':
				if (str_comp(cmd,"up")) {do_move.up(ch);break;}
			case 'v':
			case 'w':
				if (str_comp(cmd,"west")) {do_move.west(ch);break;} else
				if (str_comp(cmd,"wear")) {do_char.wear(ch, arg1(what));break;} else
				if (str_comp(cmd,"who")) {do_who(ch, arg1(what));break;} else
				if (str_comp(cmd,"whois")) {do_whois(ch, arg1(what));break;}
			case 'x':
			case 'y':
			case 'z':
			default:
				do_chan.send_char("You think for a minute and decide not to "+what,ch);
		}
		//do_prompt(ch);
//System.out.println(ch.Name() + " " + what);
	}
	/**Sends the current time to the player
	 * @param ch
	 */
	public static void do_time(Player ch) {
		StringBuffer buf = new StringBuffer(255);
		buf.append(clr_time+"It is now ");
		if (MCHOUR>12) {
			buf.append((MCHOUR-12)+"pm on ");
		} else {
			buf.append(MCHOUR+"am on ");
		}
		buf.append(DAY[((MCDAY>6)?((int)(((MCDAY/7.0)-1.0)*7)):MCDAY)]);
		buf.append(" the "+(MCDAY+1)+", "+MCYEAR+".");
		do_chan.send_char(new String(buf), ch);
	}

	/**Changes a player's command prompt.
	 * @param ch
	 * @param what
	 */
	public static void do_prompt(Player ch, String what) {
		if (trim(what).equals("")) {
			do_chan.send_char(ch.Prompt(), ch);
		} else {
			ch.setPrompt(what);
		}
		do_chan.send_char("Done.", ch);
	}
	/**Displays a player's command prompt format
	 * @param ch
	 */
	public static void do_prompt(Player ch) {
		/* lowercase = current preceded by %
		 * h = hp		m = mn/bp		v = mv
		 * f = fvr	g = gld			b = bank
		 * a = al		r = rm V#		x = exp
		 * X = tnl	i = winvlvl	F = room flags
		 * O = opponent hp%
		 * colours preceded by &
		 * lowercase = light
		 * x = black	g = green	b = blue
		 * X = grey		G = green	B = blue
		 * c = cyan		r = red		p = purple
		 * C = 	"		R =  "		P = 	"
		 * y = yellow	w = white
		 * Y =	"		W = 	"
		 */
		String prompt = ch.Prompt();
		prompt = prompt.replaceAll("%h", String.valueOf(ch.HP()));
		prompt = prompt.replaceAll("%H", String.valueOf(ch.MHP()));
		prompt = prompt.replaceAll("%m", String.valueOf(ch.MP()));
		prompt = prompt.replaceAll("%M", String.valueOf(ch.MMP()));
		prompt = prompt.replaceAll("%v", String.valueOf(ch.MV()));
		prompt = prompt.replaceAll("%V", String.valueOf(ch.MMV()));
		prompt = prompt.replaceAll("%f", String.valueOf(ch.Favor()));
		prompt = prompt.replaceAll("%g", String.valueOf(ch.Gold()));
		prompt = prompt.replaceAll("%b", String.valueOf(ch.Bank()));
		prompt = prompt.replaceAll("%a", String.valueOf(ch.Align()));
		prompt = prompt.replaceAll("%x", String.valueOf(ch.Exp()));
		//prompt = prompt.replaceAll("%X", ch.Tnl());
		//prompt = prompt.replaceAll("%i", ch.WizInvis());
		if (ch.Pos()==POS_FIGHT) {
			prompt = prompt.replaceAll("%O", String.valueOf(ch.Vict().HitPer()));
		} else {
			prompt = prompt.replaceAll("%O", "");
		}
		prompt = do_chan.colorize(prompt);
		do_chan.send_char(prompt+CLR_DWHITE, ch);
	}

	/*public static void add_room(Room room) {
		rooms[room.Vnum()] = room;
	}*/
	/**Adds an area to the MUD world
	 * @param area
	 */
	public static void add_area(Area area) {
			AREAS[area.Vnum()] = area;
System.out.println("Adding area: "+area.Vnum());
	}
	//public static void add_obj(Obj obj) {objects[obj.Vnum()] = obj;}
/*	public static void add_mob(Mob ch) {
		for (int i = 0; i < mob.length;i++) {
			if (mob[i] == null) {
				mob[i] = ch;
				return;
			}
		}
System.out.println("Max NPC reached!");
	}
	public static void rem_mob(Mob ch) {
		for (int i = 0; i < mob.length;i++) {
			if (mob[i] == ch) {
				mob[i] = null;
				ch.Room().remMob(ch);
				return;
			}
		}
System.out.println("NPC "+ch.Name()+" not found!");
	}*/
	/**Adds a player to the MUD
	 * @param ch
	 */
	public static void add_char(Player ch) {
		for (int i = 0; i < CHARS.length;i++) {
			if (CHARS[i] == null) {
				CHARS[i] = ch;
				return;
			}
		}
System.out.println("Max PC reached!");
	}
	/**Removes a player from the game. i.e. logout
	 * @param ch player to remove
	 */
	public static void rem_char(Player ch) {
		for (int i = 0; i < CHARS.length;i++) {
			if (CHARS[i] == ch) {
				CHARS[i] = null;
				ch.Room().remChar(ch);
				System.arraycopy(CHARS, i+1, CHARS, i, (CHARS.length-1)-i);
				return;
			}
		}
System.out.println("PC "+ch.Name()+" not found!");
	}

//command funtions
	/**Shutdown procedure
	 * @param ch Player calling the command
	 */
	public static void do_mud_die(Player ch) {if (ch.Level()>=LVL_REB){do_save_all();do_chan.send_all("Reboot by "+ch.Name());System.exit(0);} else {do_chan.send_char("You shall do no such thing!", ch);}}
	/**
	 * Saves all the characters in the world
	 */
	public static void do_save_all() {
		for (int i = 0;i<CHARS.length;i++) {
			if (CHARS[i]!=null) {
					do_file.do_save(CHARS[i]);
			} else {return;}
		}
	}
	/**Forces the victim to do a command.
	 * @param ch Player issuing the command
	 * @param what
	 */
	public static void do_force(Player ch, String what) {
		what = trim(what);
		Player vict = get_char_world(gcmd(what));
		String cmd = what.substring(gcmd(what).length()).trim();
		if (vict==null) {
			do_chan.send_char(argn(what,1)+" doesn't exist.", ch);
		} else {
			if (vict.Level()>ch.Level()) {
				do_chan.send_char(CLR_YELLOW+"Respect your elders!",ch);
			} else {
				do_chan.send_char(CLR_YELLOW+ch.Name()+" forces you to "+cmd+".", vict);
				do_chan.send_room_around(CLR_YELLOW+ch.Name()+" forces "+vict.Name()+" to "+cmd+".", vict);
				do_cmd(vict, cmd);
				//do_prompt(vict);
				do_chan.send_char("Done.", ch);
			}
		}
	}
	/**Sets the values on the character
	 * @param ch Player giving command
	 * @param who Player we are editing
	 * @param what Attribute we are modifying
	 * @param value Value to be assigned
	 */
	public static void do_pset(Player ch, String who, String what, String value) {
		if (ch.Level()<LVL_IMM) {do_chan.send_char("You are not expirienced enough yet.", ch);return;}
		if (who==null||who.equals("")||
				what==null||what.equals("")||
				value==null||value.equals("")) {
			do_chan.send_char("Syntax: pset <player> <attr> <value>", ch);
			do_chan.send_char("attr values: align, aff, bio, bank, class, cha, con,\r\n"+
					" desc, dex, exp, favor, gold, hp, int, level, luc, mp,\r\n"+
					" mv, mkills, mdeaths, name, pass, pkills, pdeaths,\r\n"+
					" position, race, str, sex, title, wis", ch);
		} else {
			Player vict = get_char_world(who);
			if (vict==null) {do_chan.send_char(who+" does not exist.", ch);return;}
			switch(what.charAt(0)) {
				case 'a':
					if (what.equals("align")) {vict.setAlign(parseShort(value));} else
					if (what.equals("aff")) {vict.setAff(value);} else {
						do_chan.send_char("attr a values: align, aff", ch);
					}
					break;
				case 'b':
					if (what.equals("bio")) {vict.setBio(value);} else
					if (what.equals("bank")) {vict.setBank(parseInt(value));} else {
						do_chan.send_char("attr b values: bio, bank", ch);
					}
					break;
				case 'c':
					if (what.equals("class")) {vict.setClass(CharacterClass.valueOf(value));} else
					if (what.equals("cha")) {vict.setCha(parseByte(value));} else
					if (what.equals("con")) {vict.setCon(parseByte(value));} else {
						do_chan.send_char("attr c values: class, cha, con", ch);
					}
					break;
				case 'd':
					if (what.equals("desc")) {vict.setDesc(value);} else
					if (what.equals("dex")) {vict.setDex(parseByte(value));} else {
						do_chan.send_char("attr d values: desc, dex", ch);
					}
					break;
				case 'e':
					if (what.equals("exp")) {vict.setExp(parseInt(value));} else {
						do_chan.send_char("attr e values: exp", ch);
					}
					break;
				case 'f':
					if (what.equals("favor")) {vict.setFavor(parseShort(value));} else {
						do_chan.send_char("attr f values: favor", ch);
					}
					break;
				case 'g':
					if (what.equals("gold")) {vict.setGold(parseInt(value));} else {
						do_chan.send_char("attr g values: gold", ch);
					}
					break;
				case 'h':
					if (what.equals("hp")) {
						vict.setHP(parseInt(value));
						vict.setMHP(parseInt(value));
					} else {
						do_chan.send_char("attr h values: hp", ch);
					}
					break;
				case 'i':
					if (what.equals("int")) {vict.setInt(parseByte(value));} else {
						do_chan.send_char("attr i values: int", ch);
					}
					break;
				case 'l':
					if (what.equals("level")) {vict.setLevel(parseByte(value));} else
					if (what.equals("luc")) {vict.setLuc(parseByte(value));} else {
						do_chan.send_char("attr l values: level, luc", ch);
					}
					break;
				case 'm':
					if (what.equals("mp")) {
						vict.setMP(parseInt(value));
						vict.setMMP(parseInt(value));
					} else
					if (what.equals("mv")) {
						vict.setMV(parseInt(value));
						vict.setMMV(parseInt(value));
					} else
					if (what.equals("mkills")) {} else
					if (what.equals("mdeaths")) {} else {
						do_chan.send_char("attr m values: mp, mv, mkills, mdeaths", ch);
					}
					break;
				case 'n':
					if (what.equals("name")) {vict.setName(value);} else {
						do_chan.send_char("attr n values: name", ch);
					}
					break;
				case 'p':
						if (what.equals("pass")) {vict.setPass(value);} else
						if (what.equals("pkills")) {} else
						if (what.equals("pdeaths")) {} else
						if (what.equals("position")) {vict.setPos(parseByte(value));} else {
						do_chan.send_char("attr p values: pass, pkills, pdeaths, position", ch);
					}
					break;
				case 'r':
					if (what.equals("race")) {vict.setRace(CharacterRace.valueOf(value));} else {
						do_chan.send_char("attr r values: race", ch);
					}
					break;
				case 's':
					if (what.equals("str")) {vict.setStr(parseByte(value));} else
					if (what.equals("sex")) {vict.setSex(parseByte(value));} else {
						do_chan.send_char("attr s values: str, sex", ch);
					}
					break;
				case 't':
					if (what.equals("title")) {vict.setTitle(value);} else {
						do_chan.send_char("attr t values: title", ch);
					}
					break;
				case 'w':
					if (what.equals("wis")) {vict.setWis(parseByte(value));} else {
						do_chan.send_char("attr w values: wis", ch);
					}
					break;
				default:
					do_chan.send_char("attr values: align, aff, bio, bank, class, cha, con,\r\n"+
										" desc, dex, exp, favor, gold, hp, int, level, luc, mp,\r\n"+
										" mv, mkills, mdeaths, name, pass, pkills, pdeaths,\r\n"+
										" position, race, str, sex, title, wis", ch);
			}
			do_chan.send_char("Done.", ch);
		}
	}
	/**Creates an object and puts it in the player's inventory
	 * @param ch
	 * @param what
	 */
	public static void do_make(Player ch, String what) {
		what = trim(what);
		if (!NaN(what.split(" "))) {do_make(ch, parseInt(what.split(" ")));return;}
		for (int i = 0;i<MAX_AREA;i++) {
			if (AREAS[i]!=null) {
				for (int j = 0;j<AREAS[i].Objs().length;j++) {
					if (AREAS[i].Objs()[j].equals(what)) {
						do_chan.send_room_around(ch.Name()+" utters some arcane words and weaves into existance "+AREAS[i].Objs()[j].sDesc()+".", ch);
						do_chan.send_char("You create "+AREAS[i].Objs()[j].sDesc(), ch);
						ch.addObjInv((Obj) AREAS[i].Objs()[j].clone());
					}
				}
			} else {do_chan.send_char("Object does not exist.", ch);return;}
		}
	}
	/**Creates an object and puts it in the player's inventory
	 * @param ch
	 * @param vnum Virtual number of the object
	 */
	public static void do_make(Player ch, int[] vnum) {
		if (AREAS[vnum[0]].Objs()[vnum[1]]!=null) {
			ch.addObjInv((Obj) AREAS[vnum[0]].Objs()[vnum[1]].clone());
		} else {do_chan.send_char("Object does not exist.", ch);return;}
	}
	/**Displays detailed information about a player
	 * @param ch
	 * @param who
	 */
	public static void do_whois(Player ch, String who) {
		if (who.equals("")) {do_chan.send_char("Syntax: whois <character>", ch);return;}
		Player temp = (who.equals("self"))?ch:get_char_world(who);
		if (temp!=null) {
			do_chan.send_char(temp.Name()+" "+temp.Title()+" is a "+SEX[temp.Sex()]+" level "+temp.Level()+" "+temp.Race()+" "+temp.Class()+".", ch);
			do_chan.send_char(temp.Name()+" is in room "+temp.Room().Title(), ch);
			do_chan.send_char("-----------------------------------------\r\n"+temp.Bio(), ch);
		} else {do_chan.send_char(who+" is not in this world.", ch);}
	}
//------------------------------------------------------------------------------------------------------
	/**Shows who is online
	 * @param ch
	 * @param arg
	 */
	public static void do_who(Player ch, String arg) {
		Player[] nimm = new Player[(MAX_CHAR*3)/4];
		Player[] imm = new Player[MAX_CHAR/4];
		int cnimm = -1;
		int cimm = -1;
		for (int i = 0;i<MAX_CHAR;i++) {
			if (CHARS[i]!=null) {
				if (CHARS[i].Level()>=LVL_IMM) {
					imm[++cimm]=CHARS[i];
				} else {
					nimm[++cnimm]=CHARS[i];
				}
			} else {break;}
		}
		//[lvl race class] name+title
		do_chan.send_char(CLR_BLUE+"-"+CLR_CYAN+"lvl"+CLR_BLUE+"-"+CLR_CYAN+"race"+CLR_BLUE+"-"+CLR_CYAN+"class"+CLR_BLUE+"-----------------------------------------------------------------", ch);
		for (;cnimm>=0;cnimm--) {
			StringBuffer temp = new StringBuffer("[              ] ");
			temp.replace(1,String.valueOf(nimm[cnimm].Level()).length(),String.valueOf(nimm[cnimm].Level()));
			temp.replace(5,9,nimm[cnimm].Race().toString().substring(0,4));
			temp.replace(10,15,nimm[cnimm].Class().toString().substring(0,5));
			temp.insert(1,CLR_GREEN);
			temp.insert(15,CLR_GREEN);
			do_chan.send_char(temp+CLR_DGREEN+nimm[cnimm].Name()+" "+nimm[cnimm].Title(), ch);
		}
		do_chan.send_char(CLR_BLUE+"----------------------------["+CLR_WHITE+"IMMORTALS"+CLR_BLUE+"]-----------------------------------------", ch);
		for (;cimm>=0;cimm--) {
			StringBuffer temp = new StringBuffer("[              ] ");
			temp.replace(1,4,String.valueOf(imm[cimm].Level()));
			temp.replace(5,9,imm[cimm].Race().toString().substring(0,4));
			temp.replace(10,15,imm[cimm].Class().toString().substring(0,5));
			temp.insert(1,CLR_GREEN);
			temp.insert(15,CLR_GREEN);
			do_chan.send_char(temp+CLR_DGREEN+imm[cimm].Name()+" "+imm[cimm].Title(), ch);
		}
		do_chan.send_char("", ch);
	}
//------------------------------------------------------------------------------------------------------
	/**Opens a container
	 * @param ch
	 * @param what
	 */
	public static void do_open(Character ch, String what) {do_chan.send_char("You imagine opening "+what+".", ch);}
	/**Closes a contianer
	 * @param ch
	 * @param what
	 */
	public static void do_close(Character ch, String what) {do_chan.send_char("You imagine closing "+what+".", ch);}
	/**Unlocks a container
	 * @param ch
	 * @param what
	 */
	public static void do_unlock(Character ch, String what) {do_chan.send_char("You imagine unlocking "+what+".", ch);}
	/**Locks a container
	 * @param ch
	 * @param what
	 */
	public static void do_lock(Character ch, String what) {do_chan.send_char("You imagine locking "+what+".", ch);}

	/**Prints the colors in glorious color for preview
	 * @param ch
	 * @param args
	 */
	public static void do_colorize(Character ch, String args) {
		do_chan.send_char(CLR_BLACK+"black", ch);
		do_chan.send_char(CLR_RED+"red", ch);
		do_chan.send_char(CLR_GREEN+"green", ch);
		do_chan.send_char(CLR_YELLOW+"yellow", ch);
		do_chan.send_char(CLR_BLUE+"blue", ch);
		do_chan.send_char(CLR_PURPLE+"purple", ch);
		do_chan.send_char(CLR_CYAN+"cyan", ch);
		do_chan.send_char(CLR_WHITE+"white", ch);
		do_chan.send_char(CLR_DBLACK+"dark black", ch);
		do_chan.send_char(CLR_DRED+"dark red", ch);
		do_chan.send_char(CLR_DGREEN+"dark green", ch);
		do_chan.send_char(CLR_DYELLOW+"dark yellow", ch);
		do_chan.send_char(CLR_DBLUE+"dark blue", ch);
		do_chan.send_char(CLR_DPURPLE+"dark purple", ch);
		do_chan.send_char(CLR_DCYAN+"dark cyan", ch);
		do_chan.send_char(CLR_DWHITE+"dark white", ch);
	}

	/**Finds a character in the world by its name
	 * @param name
	 * @return
	 */
	public static Player get_char_world(String name) {
		name = trim(name);
		for (int i = 0;i<CHARS.length;i++) {
			if (CHARS[i]!=null) {
				if (str_comp(name,CHARS[i].Name())) {
					return CHARS[i];
				}
			} else {return null;}
		}
		return null;
	}

	public static String trim(String x) {return x.trim();}
	public static int len(String x) {return x.length()-1;}
//self defined parser thingies
	/**Parses an integer and casts to a byte.
	 * @param x
	 * @return the value or -1.
	 */
	public static byte parseByte(String x) {
		return (byte)parseInt(x.trim());
	}
	/**Parses an integer.
	 * @param x
	 * @return The value or -1.
	 */
	public static int parseInt(String x) {return ((NaN(x))?-1:Integer.parseInt(x));}
	public static int[] parseInt(String[] x) {
		int[] temp = new int[x.length];
		for (int i = 0;i<x.length;i++) {
			temp[i] = Integer.parseInt(x[i]);
		}
		return temp;
	}
	/**Parses an integer and casts to a short.
	 * @param x
	 * @return The value or -1.
	 */
	public static short parseShort(String x) {return (short)Integer.parseInt(x);}
	/** Attempts to parse an integer.
	 * @param num
	 * @return true on success, false otherwise
	 */
	public static boolean NaN(String num) {
		try {
			Integer.parseInt(num);
			return false;
		} catch (NumberFormatException ex) {return true;}
	}
	public static boolean NaN(String[] num) {
		try {
			for (int i = 0;i<num.length;i++) {
				Integer.parseInt(num[i]);
			}
			return false;
		} catch (NumberFormatException ex) {return true;}
	}
//cmd getters and cmd arg getters
	/**returns the command
	 * @param input
	 * @return
	 */
	public static String gcmd(String input) {
		return input.substring(0,((input.indexOf(" ")==-1) ? input.length():input.indexOf(" ")));
	}
	/**Returns the first argument of a command
	 * @param input
	 * @return
	 */
	public static String arg1(String input) {
		if (input.indexOf(" ")==-1) {
			return input;
		} else {
			return input.substring((input.indexOf(" ")+1));
		}
	}
	/**returns the nth argument of a command.
	 * <br>Space separated.
	 * @param input
	 * @param n
	 * @return
	 */
	public static String argn(String input, int n) {
		String z[] = input.split(" ");
		if (n>(z.length-1)) {return null;}
		return z[n];
	}

//String comparer for partial words
	/**Returns if the strings are equal or partially equal
	 * @param input
	 * @param cmd
	 * @return true as long as input.length() &lt;= cmd.length() and
	 * cmd.substring(input.length()).equalsIgnoreCase(input) is true
	 */
	public static boolean str_comp(String input, String cmd) {
		input = input.toLowerCase();
		cmd = cmd.toLowerCase();
		if (input.length()>cmd.length()) {return false;}
		for (int i = 0;i<input.length();i++) {
			if (cmd.charAt(i)!=input.charAt(i)) {return false;}
		}
		return true;
	}

	/**Timer that displays a message based on the time of day, day of month,
	 * and month
	 * @author Kenneth Pierce
	 */
	public static class update extends TimerTask {
		public update() {}
		@Override
		public void run() {
			if ((++MCHOUR)>24) {
				MCHOUR = 1;
				if ((++MCDAY)>27) {
					MCDAY = 0;
					if ((++MCMONTH)>6) {
						MCMONTH = 0;
						MCYEAR++;
					}
				}
			}
			String msg;
			switch(MCHOUR) {
				case 7:
					msg = CLR_DYELLOW+"The sun rises in the east.";
					break;
				case 12:
					msg = CLR_YELLOW+"It is noon.";
					break;
				case 20:
					msg = CLR_DRED+"The sun sinks below the horizon.";
					break;
				default:
					msg = "";
			}
			for (int i = 0;i < MAX_CHAR;i++) {
				if (CHARS[i]==null) {return;}
				CHARS[i].setHP(CHARS[i].HP()+((CHARS[i].Con()/2)-CHARS[i].Pos()));
				if (CHARS[i].HP()>CHARS[i].MHP()) {
					CHARS[i].setHP(CHARS[i].MHP());
				}
				do_chan.send_char(msg, CHARS[i]);
				do_prompt(CHARS[i]);
			}
		}
	}

//Misc. functions
	/**Converts a byte to a hex string.
	 * @param b
	 * @return
	 */
	public static String Hex(byte b) {
		StringBuffer x = new StringBuffer();
		switch(((b&240)>>>4)) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
				x.append((b&240)>>>4);
			break;
			case 10:
				x.append("A");
			break;
			case 11:
				x.append("B");
			break;
			case 12:
				x.append("C");
			break;
			case 13:
				x.append("D");
			break;
			case 14:
				x.append("E");
			break;
			case 15:
				x.append("F");
			break;
		}
		switch((b&15)) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
				x.append(b&15);
			break;
			case 10:
				x.append("A");
			break;
			case 11:
				x.append("B");
			break;
			case 12:
				x.append("C");
			break;
			case 13:
				x.append("D");
			break;
			case 14:
				x.append("E");
			break;
			case 15:
				x.append("F");
			break;
		}
		return new String(x);
	}
	/**unsigned byte subtraction
	 * @param x
	 * @param y
	 * @return
	 */
	public static byte bSub(byte x,byte y) {//unsigned byte subtraction
		return (byte)((-x)^(-y));
	}
	/**Creates an entry in the log file
	 * @param x
	 */
	public static void log(String x) {log.log("["+(new Date())+"] "+x+"\n");}
}