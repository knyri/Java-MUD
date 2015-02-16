/* µCOMP:javac -classpath ".." %sµ
 *
 */

package Pmud;

import static Pmud.Mud.AREAS;
import static Pmud.Mud.FL_AREA;
import static Pmud.Mud.FL_PLAYR;
import static Pmud.constants.RoomExits.EXIT_NAMES;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
//import java.io.FileNotFoundException;
import java.io.IOException;

import Pmud.area.Area;
import Pmud.area.Exit;
import Pmud.area.Room;
import Pmud.character.CharacterClass;
import Pmud.character.CharacterRace;
import Pmud.character.Mob;
import Pmud.character.Player;
import Pmud.object.Obj;
import Pmud.object.equipment.armor.Arms;
import Pmud.object.equipment.armor.Body;
import Pmud.object.equipment.armor.Ear;
import Pmud.object.equipment.armor.Finger;
import Pmud.object.equipment.armor.Hands;
import Pmud.object.equipment.armor.Head;
import Pmud.object.equipment.armor.Light;
import Pmud.object.equipment.armor.Neck;
import Pmud.object.equipment.armor.Waist;
import Pmud.object.equipment.weapon.Bow;
import Pmud.object.equipment.weapon.Dagger;
import Pmud.object.equipment.weapon.Gun;
import Pmud.object.equipment.weapon.Lance;
import Pmud.object.equipment.weapon.Sword;

/**Handles file functions.
 * @author Kenneth Pierce
 */
public final class do_file {

	/**Reads <code>file</code> and sends its contents to <code>ch</code>.
	 * @param file The file to read
	 * @param ch The player to send it to
	 */
	public static void do_show_file(String file, Player ch) {
		try {
			FileInputStream x = new FileInputStream(file);
			byte[] b = new byte[x.available()];
			x.read(b);
			do_chan.send_char(new String(b), ch);
		} catch (IOException ex) {}
	}

	/**
	 * Loads areas to memory
	 */
	public static void load_areas() {
		try {
			FileInputStream x = new FileInputStream(FL_AREA+"area.lst");
			do_func.log("Reading Area List...");
			String temp;
			{
				byte[] b = new byte[x.available()];
				x.read(b);
				temp = new String(b);
			}
			int pos = 0;
			int pos2= 0;
			int i = -1;
			while (true) {
				pos2 = temp.indexOf("\n");
				switch(temp.charAt(pos)) {
					case '#':
					return;
					default:
						i++;
						do_func.log("Reading area: "+temp.substring(pos,pos2)+":"+i);
						load_area(temp.substring(pos,pos2), i);
						do_func.log("Reading Objects");
						load_objs(temp.substring(pos,pos2), i);
						do_func.log("Reading Mobs");
						load_mobs(temp.substring(pos,pos2), i);
						do_func.log("Reading Resets");
						load_reset(temp.substring(pos,pos2), i);
				}
				pos = pos2+1;
			}
		} catch (IOException ex) {
			do_func.log("Error in load_areas():"+ex.toString());
		}
	}

	/**Loads a specific area to area to area slot <code>i</code>
	 * @param arean
	 * @param i
	 */
	public static void load_area(String arean, int i) {
System.out.println("Reading area: "+arean);
		try {
			FileInputStream x = new FileInputStream(FL_AREA+arean+".are");
			if (!new File(FL_AREA+arean+".are").exists()) {
				do_func.log("Area file does not exist.");
System.out.println("Area does not exist.");
				return;
			}
			String temp;
			String z;
			{
				byte[] b = new byte[x.available()];
				x.read(b);
				z = new String(b);
			}
			int pos = 0;
			int pos2= 0;
			Area area = new Area();
			Room room = new Room();
			while (true) {
				pos2 = z.indexOf("\n", pos);
				temp = z.substring(pos, pos2).trim();
//System.out.println("SubsString: "+temp);
//System.out.println(pos+" "+pos2);
				switch(temp.charAt(0)) {
					case '#':
						if (do_func.gcmd(temp).equals("#Exit")) {
							Exit e = new Exit();
							e.setDir(do_func.parseByte(do_func.argn(temp,1)));
							e.setDest(new int[]{area.Vnum(),do_func.parseInt(do_func.argn(temp,2))});
							e.setAff(do_func.argn(temp,3));
							e.setKey(do_func.parseInt(do_func.argn(temp,4)));
							pos = pos2+1;
							pos2= z.indexOf("~", pos);
							e.setDesc(z.substring(pos, pos2));
							pos = pos2+2;
							pos2= z.indexOf("~", pos);
							e.setKeyw(z.substring(pos, pos2));
							pos2++;
							room.addExit(e, null);
							do_func.log("Adding exit "+EXIT_NAMES[e.Dir()]+" from room "+room.sVnum()+" to ["+e.DestVnum()[0]+"]["+e.DestVnum()[1]+"]");
//System.out.println("Adding exit from room "+room.Vnum()+" to "+e.DestVnum());
						} else//dir dest aff key desc~ keyw~
						if (do_func.gcmd(temp).equals("#Ex")) {
							pos = pos2+1;
							pos2= z.indexOf("~", pos);
							String name = z.substring(pos, pos2);
							pos = pos2+2;
							pos2= z.indexOf("~", pos);
							String desc = z.substring(pos, pos2);
							pos2++;
							room.addExDesc(name, desc, null);
//System.out.println("Adding ExtraDesc: "+name+" "+desc);
						} else//keyw~ desc~
						if (do_func.gcmd(temp).equals("#RESET")) {area.setReset(temp.substring(6).trim());} else
						if (do_func.gcmd(temp).equals("#AREA")) {
							do_func.log("Creating Area Object...");
System.out.println("Creating Area Object...");
							area = new Area(do_func.parseInt(do_func.argn(temp,1)), do_func.parseInt(do_func.argn(temp,2)), do_func.argn(temp,3), do_func.argn(temp,4));//vnum, size, auth, name
						} else
						if (do_func.gcmd(temp).equals("#$")) {
							do_func.log("Adding Area to List...");
System.out.println("Adding Area to List...");
							do_func.add_area(area);
							return;
						} else
						{
							room.setVnum(new int[]{area.Vnum(), do_func.parseInt(do_func.trim(temp.substring(1)))});
//System.out.println("Setting room vnum to "+temp.substring(1));
						}
					break;
					case 't':
						if (do_func.gcmd(temp).equals("title")) {
							room.setTitle(temp.substring(6,temp.indexOf("~")));
//System.out.println("Setting room title to "+room.Title());
						}
					break;
					case 'd':
					if (do_func.gcmd(temp).equals("desc")) {
						room.setDesc(z.substring(5+pos,z.indexOf("~",pos)));
						pos2 = z.indexOf("\n",z.indexOf("~",pos));
//System.out.println("Setting room desc to "+room.Desc());
					}
					break;
					case 'a':
						if (do_func.gcmd(temp).equals("aff")) {room.setAff(do_func.arg1(temp));}
					break;
					case 's':
						//if (do_func.gcmd(temp).equals("sec")) {room.setSec(do_func.parseByte(do_func.arg1(temp)));}
					break;
					case 'k':
					break;
					case 'S':
						do_func.log("Adding Room "+room.sVnum());
System.out.println("Adding room: "+room.Vnum()[0]+","+room.Vnum()[1]);
						//add_room(room);
						area.addRoom(room);
						room = new Room();
					break;
					case 'M':
					break;
					case 'O':
					break;
					case 'P':
					break;
					case 'G':
					break;
					case 'E':
					break;
					case 'D':
					break;
					case 'R':
					break;
				}
				pos = pos2+1;
			}
		} catch (IOException ex) {
			do_func.log("Error in load_area(*):"+ex.toString());
		}
	}
	public static void load_objs(String area, int i) {
System.out.println("Reading area objects: "+area);
		try {
			FileInputStream x = new FileInputStream(FL_AREA+area+".obj");
			if (!new File(FL_AREA+area+".obj").exists()) {
				do_func.log("Area Objects do not Exist");
System.out.println("Area objects do not exist.");
				return;
			}
			String temp;
			String z;
			{
				byte[] b = new byte[x.available()];
				x.read(b);
				z = new String(b);
			}
			int pos = 0;
			int pos2= 0;
			Obj obj = new Obj();
			while (true) {
				//TODO: update this for new Equipment class compatibility
				pos2 = z.indexOf("\n", pos);
				temp = z.substring(pos, pos2).trim();
//System.out.println("SubString: "+temp);
//System.out.println(pos+" "+pos2);
				switch(temp.charAt(0)) {
					case '#':
						if (do_func.gcmd(temp).equals("#$")) {return;} else
						if (do_func.gcmd(temp).equals("#OBJ")) {AREAS[i].setObj(do_func.parseInt(do_func.arg1(temp)));} else
						if (!do_func.NaN(temp.substring(1))) {
								obj.setVnum(new int[]{i,do_func.parseInt(temp.substring(1))});
//System.out.println("Setting object vnum to "+temp.substring(1));
							}
					break;
					case 't':
						if (do_func.gcmd(temp).equals("type")) {
							if (do_func.arg1(temp).equals("light")) {obj=new Light();} else
							if (do_func.arg1(temp).equals("body")) {obj=new Body();} else
							if (do_func.arg1(temp).equals("arms")) {obj=new Arms();} else
							if (do_func.arg1(temp).equals("neck")) {obj=new Neck();} else
							if (do_func.arg1(temp).equals("waist")) {obj=new Waist();} else
							if (do_func.arg1(temp).equals("ear")) {obj=new Ear();} else
							if (do_func.arg1(temp).equals("hands")) {obj=new Hands();} else
							if (do_func.arg1(temp).equals("head")) {obj=new Head();} else
							if (do_func.arg1(temp).equals("finger")) {obj=new Finger();} else
							if (do_func.arg1(temp).equals("gun")) {obj=new Gun();} else
							if (do_func.arg1(temp).equals("bow")) {obj=new Bow();} else
							if (do_func.arg1(temp).equals("dagger")) {obj=new Dagger();} else
							if (do_func.arg1(temp).equals("lance")) {obj=new Lance();} else
							if (do_func.arg1(temp).equals("sword")) {obj=new Sword();}
						}
					break;
					case 'd':
					if (do_func.gcmd(temp).equals("desc")) {
						obj.setDesc(z.substring(5+pos,z.indexOf("~",pos)));
						pos2 = z.indexOf("\n",z.indexOf("~",pos));
//System.out.println("Setting obj desc to "+obj.Desc());
					}
					break;
					case 'a':
						if (do_func.gcmd(temp).equals("aff")) {obj.setAff(do_func.arg1(temp));}
					break;
					case 's':
						if (do_func.gcmd(temp).equals("sdesc")) {
							obj.setsDesc(temp.substring(6,temp.indexOf("~")));
						}
					break;
					case 'v':
						obj.setV(do_func.parseInt(do_func.argn(temp,1)),do_func.parseShort(do_func.argn(temp,2)));
					break;
					case 'S':
						do_func.log("Adding Object: "+obj.Vnum()[0]+","+obj.Vnum()[1]);
System.out.println("Adding object: "+obj.Vnum()[0]+","+obj.Vnum()[1]);
						AREAS[i].addObj(obj);
						//add_obj(obj);
						obj = new Obj();
					break;
					case 'n':
						if (do_func.gcmd(temp).equals("name")) {
							obj.setName(temp.substring(5,temp.indexOf("~")));
						}
					break;
					case 'l':
						if (do_func.gcmd(temp).equals("ldesc")) {
							obj.setlDesc(temp.substring(6,temp.indexOf("~")));
						}
					break;
				}
				pos = pos2+1;
			}
		} catch (IOException ex) {
			do_func.log("Error in load_objs(*):"+ex.toString());
		}
	}
	/**Loads an area's resets.
	 * @param area
	 * @param i
	 */
	public static void load_reset(String area, int i) {
System.out.println("Reading area resets: "+area);
		try {
			FileInputStream x = new FileInputStream(FL_AREA+area+".res");
			if (!new File(FL_AREA+area+".res").exists()) {
System.out.println("Area resets do not exist.");
				return;
			}
			String temp;
			String z;
			{
				byte[] b = new byte[x.available()];
				x.read(b);
				z = new String(b);
			}
			int pos = 0;
			int pos2= 0;
			while (true) {
				pos2 = z.indexOf("\n", pos);
				temp = z.substring(pos, pos2).trim();
//System.out.println("SubsString: "+temp);
//System.out.println(pos+" "+pos2);
				switch(temp.charAt(0)) {
					case '#':
						if (do_func.gcmd(temp).equals("#$")) {AREAS[i].run();return;}
					break;
					case 'S':
					break;
					case 'O':
						//areas[do_func.parseInt(do_func.argn(temp,2))].getRoom(do_func.parseInt(do_func.argn(temp,3))).addObj(objects[do_func.parseInt(do_func.argn(temp,1))]);
						AREAS[i].addReset('O', do_func.parseInt(do_func.argn(temp,1)), do_func.parseInt(do_func.argn(temp,2)), do_func.parseInt(do_func.argn(temp,3)));
					break;
					case 'M':
						//areas[do_func.parseInt(do_func.argn(temp,2))].getRoom(do_func.parseInt(do_func.argn(temp,3))).addMob(mob[do_func.parseInt(do_func.argn(temp,1))]);
						AREAS[i].addReset('M', do_func.parseInt(do_func.argn(temp,1)), do_func.parseInt(do_func.argn(temp,2)), do_func.parseInt(do_func.argn(temp,3)));
					break;
					case 'E':
					break;
				}//code rvnm vnm lim
				pos = pos2+1;
			}
		} catch (IOException ex) {
			do_func.log("Error in load_reset(*):"+ex.toString());
		}
	}

	/**Loads an area's monsters.
	 * @param area
	 * @param i
	 */
	public static void load_mobs(String area, int i) {
		File x = new File(FL_AREA+area+".mob");
		if (x.exists()) {
			String z;
			try {
				FileInputStream in = new FileInputStream(x);
				byte[] b = new byte[in.available()];
				in.read(b);
				z = new String(b);
			} catch (Exception e) {return;}
			int pos = 0;
			int pos2= 0;
			String temp;
			Mob ch = new Mob();
			while (true) {
				pos2 = z.indexOf("\n",pos);
				temp = z.substring(pos,pos2).trim();
				switch(z.charAt(pos)) {
					case '#':
						if (do_func.gcmd(temp).equals("#MOB")) {AREAS[i].setMob(do_func.parseInt(do_func.arg1(temp)));} else
						if (temp.trim().equals("#END")) {return;} else
						if (!do_func.NaN(temp.substring(1))) {
							ch.setVnum(new int[]{i,do_func.parseInt(temp.substring(1))});
System.out.println("Setting MOB vnum to "+temp.substring(1));
						}
						break;
					case 'a':
						if (do_func.gcmd(temp).equals("attr")) {
							ch.setStr(do_func.parseByte(do_func.argn(temp,1)));
							ch.setInt(do_func.parseByte(do_func.argn(temp,2)));
							ch.setWis(do_func.parseByte(do_func.argn(temp,3)));
							ch.setDex(do_func.parseByte(do_func.argn(temp,4)));
							ch.setCha(do_func.parseByte(do_func.argn(temp,5)));
							ch.setLuc(do_func.parseByte(do_func.argn(temp,6)));
							ch.setCon(do_func.parseByte(do_func.argn(temp,7)));
						} else
						if (do_func.gcmd(temp).equals("align")) {ch.setAlign(do_func.parseShort(do_func.arg1(temp)));} else
						if (do_func.gcmd(temp).equals("aff")) {ch.setAff(do_func.arg1(temp));}
						break;
					case 'c':
						if (do_func.gcmd(temp).equals("class")) {
							ch.setClass(CharacterClass.valueOf(do_func.arg1(temp)));
						}
						break;
					case 'e':
						if (do_func.gcmd(temp).equals("exp")) {ch.setExp(do_func.parseInt(do_func.arg1(temp)));}
						break;
					case 'f':
						break;
					case 'g':
						break;
					case 'h':
						if (do_func.gcmd(temp).equals("hp")) {
							ch.setHP(do_func.parseInt(do_func.argn(temp,1)));
							ch.setMHP(do_func.parseInt(do_func.argn(temp,2)));
						}
							break;
					case 'i':
						break;
					case 'j':
						break;
					case 'k':
						break;
					case 'l':
						if (do_func.gcmd(temp).equals("level")) {ch.setLevel(do_func.parseByte(do_func.arg1(temp)));}
						if (do_func.gcmd(temp).equals("ldesc")) {ch.setDesc(z.substring(6+pos,z.indexOf("~",pos)));}
						break;
					case 'm':
						if (do_func.gcmd(temp).equals("mp")) {
							ch.setMP(do_func.parseInt(do_func.argn(temp,1)));
							ch.setMMP(do_func.parseInt(do_func.argn(temp,2)));
						} else
						if (do_func.gcmd(temp).equals("mv")) {
							ch.setMV(do_func.parseInt(do_func.argn(temp,1)));
							ch.setMMV(do_func.parseInt(do_func.argn(temp,2)));
						} else
						if (do_func.gcmd(temp).equals("mkills")) {} else
						if (do_func.gcmd(temp).equals("mdeaths")) {}
						break;
					case 'n':
						if (do_func.gcmd(temp).equals("name")) {ch.setName(do_func.arg1(temp));}
						break;
					case 'o':
						break;
					case 'p':
							if (do_func.gcmd(temp).equals("pkills")) {} else
							if (do_func.gcmd(temp).equals("pdeaths")) {} else
							if (do_func.gcmd(temp).equals("position")) {ch.setPos(do_func.parseByte(do_func.argn(temp,1)));}
						break;
					case 'q':
						break;
					case 'r':
						if (do_func.gcmd(temp).equals("race")) {
							ch.setRace(CharacterRace.valueOf(do_func.argn(temp,1)));
						}
						break;
					case 's':
						if (do_func.gcmd(temp).equals("sex")) {ch.setSex(do_func.parseByte(do_func.argn(temp,1)));} else
						if (do_func.gcmd(temp).equals("sdesc")) {ch.setTitle(temp.substring(6,temp.indexOf("~")));}
						break;
					case 'S':
						//add_mob(ch);
						do_func.log("Adding Mob "+ch.Vnum()[0]+","+ch.Vnum()[1]);
						AREAS[i].addMob(ch);
						ch = new Mob();
						break;
					case 't':
						break;
					case 'u':
						break;
					case 'v':
						break;
					case 'w':
						break;
					case 'x':
						break;
					case 'y':
						break;
					case 'z':
						break;
				}
				pos = pos2+1;
			}
		} else {do_func.log("Error in load_mobs(*): File not found");}
	}

	/**Loads a character.
	 * @param ch
	 * @return
	 */
	public static Player read_char(Player ch) {
		File x = new File(FL_PLAYR+ch.Name()+".plr");

		if (x.exists()) {
			String z;
			try {
				FileInputStream in = new FileInputStream(x);
				byte[] b = new byte[in.available()];
				in.read(b);
				z = new String(b);
			} catch (Exception e) {return ch;}
			int pos = 0;
			int pos2= 0;
			String temp;

			while (true) {
				pos2 = z.indexOf("\n",pos);
				temp = z.substring(pos,pos2).trim();
				switch(z.charAt(pos)) {
					case 'a':
						if (do_func.gcmd(temp).equals("attr")) {
							ch.setStr(do_func.parseByte(do_func.argn(temp,1)));
							ch.setInt(do_func.parseByte(do_func.argn(temp,2)));
							ch.setWis(do_func.parseByte(do_func.argn(temp,3)));
							ch.setDex(do_func.parseByte(do_func.argn(temp,4)));
							ch.setCha(do_func.parseByte(do_func.argn(temp,5)));
							ch.setLuc(do_func.parseByte(do_func.argn(temp,6)));
							ch.setCon(do_func.parseByte(do_func.argn(temp,7)));
						} else
						if (do_func.gcmd(temp).equals("align")) {ch.setAlign(do_func.parseShort(do_func.arg1(temp)));} else
						if (do_func.gcmd(temp).equals("aff")) {ch.setAff(do_func.arg1(temp));}
						break;
					case 'b':
						if (do_func.gcmd(temp).equals("bio")) {ch.setBio(z.substring(4+pos,z.indexOf("~",pos)));} else
						if (do_func.gcmd(temp).equals("bank")) {ch.setBank(do_func.parseInt(do_func.arg1(temp)));}
						break;
					case 'c':
						if (do_func.gcmd(temp).equals("class")) {
							try{
								ch.setClass(CharacterClass.valueOf(do_func.argn(temp,1)));
							}catch(IllegalArgumentException e){
								do_func.log("Class not found: "+do_func.argn(temp,1));
								ch.setClass(CharacterClass.Peasant);
							}
						}
						break;
					case 'd':
						if (do_func.gcmd(temp).equals("desc")) {ch.setDesc(z.substring(5+pos,z.indexOf("~",pos)));}
						break;
					case 'e':
						if (do_func.gcmd(temp).equals("exp")) {ch.setExp(do_func.parseInt(do_func.arg1(temp)));}
						break;
					case 'f':
						if (do_func.gcmd(temp).equals("favor")) {ch.setFavor(do_func.parseShort(do_func.arg1(temp)));}
						break;
					case 'g':
						if (do_func.gcmd(temp).equals("gold")) {ch.setGold(do_func.parseInt(do_func.arg1(temp)));}
						break;
					case 'h':
						if (do_func.gcmd(temp).equals("hp")) {
							ch.setHP(do_func.parseInt(do_func.argn(temp,1)));
							ch.setMHP(do_func.parseInt(do_func.argn(temp,2)));
						}
							break;
					case 'i':
						break;
					case 'j':
						break;
					case 'k':
						break;
					case 'l':
						if (do_func.gcmd(temp).equals("level")) {ch.setLevel(do_func.parseByte(do_func.arg1(temp)));}
						break;
					case 'm':
						if (do_func.gcmd(temp).equals("mp")) {
							ch.setMP(do_func.parseInt(do_func.argn(temp,1)));
							ch.setMMP(do_func.parseInt(do_func.argn(temp,2)));
						} else
						if (do_func.gcmd(temp).equals("mv")) {
							ch.setMV(do_func.parseInt(do_func.argn(temp,1)));
							ch.setMMV(do_func.parseInt(do_func.argn(temp,2)));
						} else
						if (do_func.gcmd(temp).equals("mkills")) {} else
						if (do_func.gcmd(temp).equals("mdeaths")) {}
						break;
					case 'n':
						if (do_func.gcmd(temp).equals("name")) {ch.setName(do_func.arg1(temp));}
						break;
					case 'o':
						break;
					case 'p':
							if (do_func.gcmd(temp).equals("pass")) {ch.setPass(do_func.arg1(temp));} else
							if (do_func.gcmd(temp).equals("pkills")) {} else
							if (do_func.gcmd(temp).equals("pdeaths")) {} else
							if (do_func.gcmd(temp).equals("position")) {ch.setPos(do_func.parseByte(do_func.argn(temp,1)));}
						break;
					case 'q':
						break;
					case 'r':
						if (do_func.gcmd(temp).equals("race")) {
							try{
								ch.setRace(CharacterRace.valueOf(do_func.argn(temp,1)));
							}catch(IllegalArgumentException e){
								do_func.log("Race not found: "+do_func.argn(temp,1));
								ch.setRace(CharacterRace.Human);
							}
						}
						break;
					case 's':
						if (do_func.gcmd(temp).equals("sex")) {ch.setSex(do_func.parseByte(do_func.argn(temp,1)));}
						break;
					case 't':
						if (do_func.gcmd(temp).equals("title")) {ch.setTitle(temp.substring(6,temp.indexOf("~")));}
						break;
					case 'u':
						break;
					case 'v':
						break;
					case 'w':
						break;
					case 'x':
						break;
					case 'y':
						break;
					case 'z':
						break;
					default:
						if (temp.trim().equals("#END")) {return ch;}
				}
				pos = pos2+1;
			}
		} else {
			do_func.log("Error in read_char(*): "+ch.Name()+" not found");
System.out.println("Player "+ch.Name()+" doesn\'t exist.");
			do_chan.send_char("Player does not exist.", ch);
		}
		return ch;
	}

	/**Saves a character.
	 * @param ch
	 */
	public static void do_save(Player ch) {
		File a = new File(FL_PLAYR+ch.Name()+".plr");
		try {
			FileOutputStream out = new FileOutputStream(a);
			out.write(("#PLAYER\n").getBytes());
			out.write(("name "+ch.Name()+"\n").getBytes());
			out.write(("pass "+ch.Pass()+"\n").getBytes());
			out.write(("attr "+ch.bStr()
								+" "+ch.bInt()
								+" "+ch.bWis()
								+" "+ch.bDex()
								+" "+ch.bCha()
								+" "+ch.bLuc()
								+" "+ch.bCon()
								+"\n").getBytes());
			out.write(("exp "+ch.Exp()+"\n").getBytes());
			out.write(("level "+ch.Level()+"\n").getBytes());
			out.write(("imm "+((ch.Level()>114)?1:0)+"\n").getBytes());
			out.write(("align "+ch.Align()+"\n").getBytes());
			out.write(("favor "+ch.Favor()+"\n").getBytes());
			out.write(("room "+ch.Room().Vnum()[0]+" "+ch.Room().Vnum()[1]+"\n").getBytes());
			out.write(("class "+ch.Class()+"\n").getBytes());
			out.write(("race "+ch.Race()+"\n").getBytes());
			out.write(("sex "+ch.Sex()+"\n").getBytes());
			out.write(("aff "+ch.getAff()+"\n").getBytes());
			out.write(("hp "+ch.HP()+" "+ch.MHP()+"\n").getBytes());
			out.write(("mp "+ch.MP()+" "+ch.MMP()+"\n").getBytes());
			out.write(("mv "+ch.MV()+" "+ch.MMV()+"\n").getBytes());
			out.write(("mkills "+ch.Mkills()+"\n").getBytes());
			out.write(("mdeaths "+ch.Mdeaths()+"\n").getBytes());
			out.write(("pkills "+ch.Pkills()+"\n").getBytes());
			out.write(("pdeaths "+ch.Pdeaths()+"\n").getBytes());
			out.write(("position "+ch.Pos()+"\n").getBytes());
			out.write(("title "+ch.Title()+"~\n").getBytes());
			out.write(("bank "+ch.Bank()+"\n").getBytes());
			out.write(("gold "+ch.Gold()+"\n").getBytes());
			out.write(("bio "+ch.Bio()+"~\n").getBytes());
			out.write(("desc "+ch.Desc()+"~\n").getBytes());
			out.write(("#END\n").getBytes());
			out.close();
		} catch (Exception e) {
			do_func.log("Error in do_save(*):"+e.toString());
		}
		do_chan.send_char("saved.",ch);
	}
}