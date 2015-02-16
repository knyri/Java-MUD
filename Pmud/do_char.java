/* µCOMP:javac -classpath ".." %sµ
 *
 */

package Pmud;

import static Pmud.constants.Affects.Char.AFF_BLIND;
import static Pmud.constants.Affects.Char.AFF_CURSE;
import static Pmud.constants.Affects.Char.AFF_DET_H;
import static Pmud.constants.Affects.Char.AFF_DET_I;
import static Pmud.constants.Affects.Char.AFF_DET_T;
import static Pmud.constants.Affects.Char.AFF_FLOAT;
import static Pmud.constants.Affects.Char.AFF_FLY;
import static Pmud.constants.Affects.Char.AFF_HIDE;
import static Pmud.constants.Affects.Char.AFF_INVIS;
import static Pmud.constants.Affects.Char.AFF_PROT;
import static Pmud.constants.Affects.Char.AFF_SANC;
import static Pmud.constants.Affects.Char.AFF_SNEAK;
import static Pmud.constants.Affects.Char.AFF_TRUE_SIGHT;
import static Pmud.constants.ChatColors.CLR_BLUE;
import static Pmud.constants.ChatColors.CLR_CYAN;
import static Pmud.constants.ChatColors.CLR_GREEN;
import static Pmud.constants.ChatColors.CLR_RED;
import static Pmud.constants.ChatColors.CLR_WHITE;
import static Pmud.constants.ChatColors.clr_PC;
import static Pmud.constants.ChatColors.clr_exit;
import Pmud.character.Character;
import Pmud.character.Player;
import Pmud.object.Obj;
import Pmud.object.equipment.Weapon;
import Pmud.object.equipment.armor.Arms;
import Pmud.object.equipment.armor.Body;
import Pmud.object.equipment.armor.Ear;
import Pmud.object.equipment.armor.Feet;
import Pmud.object.equipment.armor.Finger;
import Pmud.object.equipment.armor.Hands;
import Pmud.object.equipment.armor.Head;
import Pmud.object.equipment.armor.Legs;
import Pmud.object.equipment.armor.Light;
import Pmud.object.equipment.armor.Neck;
import Pmud.object.equipment.armor.Waist;

/**Handles functions related to players.
 * @author Kenneth Pierce
 */
public final class do_char {
	/**Returns if a player can see another
	 * @param ch
	 * @param vict
	 * @return
	 */
	public static boolean can_see(Character ch, Character vict) {
		if (ch.isAff(AFF_TRUE_SIGHT)) {return true;} else {
			if (ch.isAff(AFF_BLIND)) {return false;} else
			if (vict.isAff(AFF_HIDE)&&!ch.isAff(AFF_DET_H)) {
				return false;
			} else
			if (vict.isAff(AFF_INVIS)&&!ch.isAff(AFF_DET_I)) {
				return false;
			}
			return true;
		}
	}
	public static void look(Player ch, String what) {
		do_chan.send_char(ch.Room().toString(), ch);
		Player[] chs = ch.Room().Chars();
		for (int i = 0;i<chs.length;i++) {
			if (chs[i]!=null) {
				if (chs[i]!=ch) {
					if (can_see(ch, chs[i])) {do_chan.send_char(clr_PC+chs[i].Name() + " " + chs[i].Title() + " is here.", ch);}
				}
			} else {
				return;
			}
		}
	}
	public static void glance(Player ch, String what) {do_chan.send_char(CLR_WHITE+ch.Room().Title()+"\n"+clr_exit+ch.Room().Exits(), ch);}

	public static void affects(Player ch, String com) {
		do_chan.send_char(CLR_BLUE+"Affects:", ch);
		StringBuffer buff = new StringBuffer(225);
		buff.append(CLR_CYAN);
		if (ch.isAff(AFF_BLIND)) {buff.append(" blind");}
		if (ch.isAff(AFF_CURSE)) {buff.append(" curse");}
		if (ch.isAff(AFF_HIDE)) {buff.append(" hide");}
		if (ch.isAff(AFF_INVIS)) {buff.append(" invis");}
		if (ch.isAff(AFF_SNEAK)) {buff.append(" sneak");}
		if (ch.isAff(AFF_TRUE_SIGHT)) {buff.append(" truesight");}
		if (ch.isAff(AFF_DET_H)) {buff.append(" detect hidden");}
		if (ch.isAff(AFF_DET_I)) {buff.append(" detect invis");}
		if (ch.isAff(AFF_DET_T)) {buff.append(" detect traps");}
		if (ch.isAff(AFF_FLY)) {buff.append(" fly");}
		if (ch.isAff(AFF_FLOAT)) {buff.append(" float");}
		if (ch.isAff(AFF_SANC)) {buff.append(" sanctuary");}
		if (ch.isAff(AFF_PROT)) {buff.append(" protect");}
		if (buff.equals(CLR_CYAN)) {buff.append(" nothing");}
		do_chan.send_char(new String(buff), ch);
	}
	public static void help(Player ch, String what) {do_chan.send_char("There is no help for you. JK. Help isn\'t implemented yet.", ch);}
	public static void remove(Player ch, String what) {
		if (ch.Light().equals(what)) {
			do_chan.send_char("You stop using "+ch.Light().sDesc(), ch);
			do_chan.act_char_around(ch.Name()+" stops using "+ch.Light().sDesc()+" as %p light.",null, ch);
			ch.addObjInv(ch.Light());
			ch.setLight(null);
		} else if (ch.Head().equals(what)) {
			do_chan.send_char("You stop using "+ch.Head().sDesc(), ch);
			do_chan.act_char_around(ch.Name()+" removes "+ch.Head().sDesc()+" from %p head.",null, ch);
			ch.addObjInv(ch.Head());
			ch.setHead(null);
		} else if (ch.Neck1().equals(what)) {
			do_chan.send_char("You stop using "+ch.Neck1().sDesc(), ch);
			do_chan.act_char_around(ch.Name()+" removes "+ch.Neck1().sDesc()+" from %p neck.",null, ch);
			ch.addObjInv(ch.Neck1());
			ch.setNeck1(null);
		} else if (ch.Neck2().equals(what)) {
			do_chan.send_char("You stop using "+ch.Neck2().sDesc(), ch);
			do_chan.act_char_around(ch.Name()+" removes "+ch.Neck2().sDesc()+" from %p neck.",null, ch);
			ch.addObjInv(ch.Neck2());
			ch.setNeck2(null);
		} else if (ch.Ears().equals(what)) {
			do_chan.send_char("You stop using "+ch.Ears().sDesc(), ch);
			do_chan.act_char_around(ch.Name()+" unclasps "+ch.Ears().sDesc()+" from %p ear.",null, ch);
			ch.addObjInv(ch.Ears());
			ch.setEars(null);
		} else if (ch.Body().equals(what)) {
			do_chan.send_char("You stop using "+ch.Body().sDesc(), ch);
			do_chan.act_char_around(ch.Name()+" removes "+ch.Body().sDesc()+" from %p body.",null, ch);
			ch.addObjInv(ch.Body());
			ch.setBody(null);
		} else if (ch.Arms().equals(what)) {
			do_chan.send_char("You stop using "+ch.Arms().sDesc(), ch);
			do_chan.act_char_around(ch.Name()+" removes "+ch.Arms().sDesc()+" from %p arms.",null, ch);
			ch.addObjInv(ch.Arms());
			ch.setArms(null);
		} else if (ch.Fingerl().equals(what)) {
			do_chan.send_char("You stop using "+ch.Fingerl().sDesc(), ch);
			do_chan.act_char_around(ch.Name()+" removes "+ch.Fingerl().sDesc()+" from %p left finger.",null, ch);
			ch.addObjInv(ch.Fingerl());
			ch.setFingerl(null);
		} else if (ch.Fingerr().equals(what)) {
			do_chan.send_char("You stop using "+ch.Fingerr().sDesc(), ch);
			do_chan.act_char_around(ch.Name()+" removes "+ch.Fingerr().sDesc()+" from %p right finger.",null, ch);
			ch.addObjInv(ch.Fingerr());
			ch.setFingerr(null);
		} else if (ch.Hands().equals(what)) {
			do_chan.send_char("You stop using "+ch.Hands().sDesc(), ch);
			do_chan.act_char_around(ch.Name()+" slips "+ch.Hands().sDesc()+" off %p hands.",null, ch);
			ch.addObjInv(ch.Hands());
			ch.setHands(null);
		} else if (ch.Weapon().equals(what)) {
			do_chan.send_char("You stop using "+ch.Weapon().sDesc(), ch);
			do_chan.act_char_around(ch.Name()+" stops weilding "+ch.Weapon().sDesc()+".",null, ch);
			ch.addObjInv(ch.Weapon());
			ch.setWeapon(null);
		} else if (ch.Waist().equals(what)) {
			do_chan.send_char("You stop using "+ch.Waist().sDesc(), ch);
			do_chan.act_char_around(ch.Name()+" removes "+ch.Waist().sDesc()+" from %p waist.",null, ch);
			ch.addObjInv(ch.Waist());
			ch.setWaist(null);
		} else if (ch.Legs().equals(what)) {
			do_chan.send_char("You stop using "+ch.Legs().sDesc(), ch);
			do_chan.act_char_around(ch.Name()+" removes "+ch.Legs().sDesc()+" from %p legs.",null, ch);
			ch.addObjInv(ch.Legs());
			ch.setLegs(null);
		} else if (ch.Feet().equals(what)) {
			do_chan.send_char("You stop using "+ch.Feet().sDesc(), ch);
			do_chan.act_char_around(ch.Name()+" removes "+ch.Feet().sDesc()+" from %p feet.",null, ch);
			ch.addObjInv(ch.Feet());
			ch.setFeet(null);
		} else {
			do_chan.send_char("You are not wearing "+what+".", ch);
		}
	}
	public static void wear(Player ch, String what) {
		for (int i = 0;i<ch.Inv().length;i++) {
			if (ch.Inv()[i]!=null) {
//System.out.println("Non null Obj");
				if (ch.Inv()[i].equals(what)) {
//System.out.println("matching Obj");
					if (ch.Inv()[i] instanceof Weapon) {
						if (ch.Weapon()==null) {
							ch.setWeapon((Weapon)ch.Inv()[i]);
							do_chan.send_char("You weild "+ch.Inv()[i].sDesc(), ch);
							do_chan.send_room_around(ch.Name()+" weilds "+ch.Inv()[i].sDesc(), ch);
							ch.remObjInv(ch.Inv()[i]);
						}else{
							do_chan.send_char("You are already weilding something.", ch);
						}
					} else if (ch.Inv()[i] instanceof Arms) {
						if (ch.Arms()==null) {
							ch.setArms((Arms)ch.Inv()[i]);
							do_chan.send_char("You wear "+ch.Inv()[i].sDesc()+" on your arms.", ch);
							do_chan.send_room_around(ch.Name()+" wears "+ch.Inv()[i].sDesc(), ch);
							ch.remObjInv(ch.Inv()[i]);
						}else{
							do_chan.send_char("You already have something on your arms.", ch);
						}
					} else if (ch.Inv()[i] instanceof Ear) {
						if (ch.Ears()==null) {
							ch.setEars((Ear)ch.Inv()[i]);
							do_chan.send_char("You affix "+ch.Inv()[i].sDesc()+" to your ears.", ch);
							do_chan.send_room_around(ch.Name()+" affixes "+ch.Inv()[i].sDesc()+" to their ears.", ch);
							ch.remObjInv(ch.Inv()[i]);
						}else{
							do_chan.send_char("You already have something on your ears.", ch);
						}
					} else if (ch.Inv()[i] instanceof Hands) {
						if (ch.Hands()==null) {
							ch.setHands((Hands)ch.Inv()[i]);
							do_chan.send_char("You wear "+ch.Inv()[i].sDesc()+" on your hands.", ch);
							do_chan.send_room_around(ch.Name()+" wears "+ch.Inv()[i].sDesc(), ch);
							ch.remObjInv(ch.Inv()[i]);
						}else{
							do_chan.send_char("You already have something on your hands.", ch);
						}
					} else if (ch.Inv()[i] instanceof Head) {
						if (ch.Head()==null) {
							ch.setHead((Head)ch.Inv()[i]);
							do_chan.send_char("You don "+ch.Inv()[i].sDesc()+" on your head.", ch);
							do_chan.send_room_around(ch.Name()+" wears "+ch.Inv()[i].sDesc(), ch);
							ch.remObjInv(ch.Inv()[i]);
						}else{
							do_chan.send_char("You already have something on your head.", ch);
						}
					} else if (ch.Inv()[i] instanceof Legs) {
						if (ch.Legs()==null) {
							ch.setLegs((Legs)ch.Inv()[i]);
							do_chan.send_char("You wear "+ch.Inv()[i].sDesc()+" on your legs.", ch);
							do_chan.send_room_around(ch.Name()+" wears "+ch.Inv()[i].sDesc(), ch);
							ch.remObjInv(ch.Inv()[i]);
						}else{
							do_chan.send_char("You already have something on your legs.", ch);
						}
					} else if (ch.Inv()[i] instanceof Light) {
						if (ch.Light()==null) {
							ch.setLight((Light)ch.Inv()[i]);
							do_chan.send_char("You hold "+ch.Inv()[i].sDesc()+" as your light.", ch);
							do_chan.send_room_around(ch.Name()+" wears "+ch.Inv()[i].sDesc(), ch);
							ch.remObjInv(ch.Inv()[i]);
						}else{
							do_chan.send_char("You are already holding something as your light.", ch);
						}
					} else if (ch.Inv()[i] instanceof Finger) {
						if (ch.Fingerl()==null) {
							ch.setFingerl((Finger)ch.Inv()[i]);
							do_chan.send_char("You wear "+ch.Inv()[i].sDesc()+" on your left finger.", ch);
							do_chan.send_room_around(ch.Name()+" wears "+ch.Inv()[i].sDesc(), ch);
							ch.remObjInv(ch.Inv()[i]);
						} else if (ch.Fingerr()==null){
							ch.setFingerr((Finger)ch.Inv()[i]);
							do_chan.send_char("You wear "+ch.Inv()[i].sDesc()+" on your right finger.", ch);
							do_chan.send_room_around(ch.Name()+" wears "+ch.Inv()[i].sDesc(), ch);
							ch.remObjInv(ch.Inv()[i]);
						}else{
							do_chan.send_char("You already have something on your fingers.", ch);
						}
					} else if (ch.Inv()[i] instanceof Feet) {
						if (ch.Feet()==null) {
							ch.setFeet((Feet)ch.Inv()[i]);
							do_chan.send_char("You put "+ch.Inv()[i].sDesc()+" on your feet.", ch);
							do_chan.send_room_around(ch.Name()+" wears "+ch.Inv()[i].sDesc(), ch);
							ch.remObjInv(ch.Inv()[i]);
						}else{
							do_chan.send_char("You are already wearing something on your feet.", ch);
						}
					} else if (ch.Inv()[i] instanceof Body) {
						if (ch.Body()==null) {
							ch.setBody((Body)ch.Inv()[i]);
							do_chan.send_char("You wear "+ch.Inv()[i].sDesc()+" on your body.", ch);
							do_chan.send_room_around(ch.Name()+" wears "+ch.Inv()[i].sDesc(), ch);
							ch.remObjInv(ch.Inv()[i]);
						}else{
							do_chan.send_char("You are already wearing something on your body.", ch);
						}
					} else if (ch.Inv()[i] instanceof Waist) {
						if (ch.Waist()==null) {
							ch.setWaist((Waist)ch.Inv()[i]);
							do_chan.send_char("You put "+ch.Inv()[i].sDesc()+" about your waist.", ch);
							do_chan.send_room_around(ch.Name()+" wears "+ch.Inv()[i].sDesc(), ch);
							ch.remObjInv(ch.Inv()[i]);
						}else{
							do_chan.send_char("You are already wearing something on your waist.", ch);
						}
					} else if (ch.Inv()[i] instanceof Neck) {
						if (ch.Neck1()==null) {
							ch.setNeck1((Neck)ch.Inv()[i]);
							do_chan.send_char("You wear "+ch.Inv()[i].sDesc()+" on your upper neck.", ch);
							do_chan.send_room_around(ch.Name()+" wears "+ch.Inv()[i].sDesc(), ch);
							ch.remObjInv(ch.Inv()[i]);
						} else if (ch.Neck2()==null){
							ch.setNeck2((Neck)ch.Inv()[i]);
							do_chan.send_char("You wear "+ch.Inv()[i].sDesc()+" on your lower neck.", ch);
							do_chan.send_room_around(ch.Name()+" wears "+ch.Inv()[i].sDesc(), ch);
							ch.remObjInv(ch.Inv()[i]);
						}else{
							do_chan.send_char("You already have something on your neck.", ch);
						}
					}//end instanceof
//System.out.println(ch.Inv()[i].getClass().getName());
					return;
				}//end equals
			} else {System.out.println("null Obj");break;}//end null check
		}
		do_chan.send_char("You do not have that.", ch);
	}

	public static void get(Player ch, String what) {
		String obj = do_func.argn(what,0);
		String con = do_func.argn(what,1);
		if (con==null) {
			Obj temp = ch.Room().getObj(obj);
			if (temp==null) {do_chan.send_char("You do not see that here.", ch);return;}
			ch.Room().remObj(temp);
			ch.addObjInv(temp);
			do_chan.send_char("You get "+temp.sDesc()+".", ch);
			do_chan.send_room_around(ch.Name()+" gets "+temp.sDesc()+".", ch);
		} else {
			Obj cont = ch.getObjInv(con);
			if (cont==null) {cont = ch.Room().getObj(con);}
			if (cont==null) {do_chan.send_char("You search vainly for "+con+" to get "+obj+" from.", ch);return;}
			//remove object from container and put it into inventory
		}
	}
	public static void drop(Player ch, String what) {
		Obj temp = ch.getObjInv(what);
		if (temp==null) {do_chan.send_char("You do not have that.", ch);return;}
		ch.remObjInv(temp);
		ch.Room().addObj(temp);
		do_chan.send_char("You drop "+temp.sDesc()+".", ch);
		do_chan.send_room_around(ch.Name()+" drops "+temp.sDesc()+".", ch);
	}
	public static void inv(Player ch) {
		Obj[] inv = ch.Inv();
		do_chan.send_char(CLR_GREEN+"Inventory:", ch);
		for (int i = 0; i<inv.length;i++) {
			if (inv[i]!=null) {
				do_chan.send_char(" "+inv[i].sDesc()+".", ch);
			}else{
				if (i==0) {do_chan.send_char(CLR_RED+" Nothing", ch);}
				return;
			}
		}
	}
	public static void equipment(Player ch) {
		do_chan.send_char("Light:        "+((ch.Light()==null)? "nothing":ch.Light().sDesc()), ch);
		do_chan.send_char("Head:         "+((ch.Head()==null)? "nothing":ch.Head().sDesc()), ch);
		do_chan.send_char("Neck:         "+((ch.Neck1()==null)? "nothing":ch.Neck1().sDesc()), ch);
		do_chan.send_char("Neck:         "+((ch.Neck2()==null)? "nothing":ch.Neck2().sDesc()), ch);
		do_chan.send_char("Body:         "+((ch.Body()==null)? "nothing":ch.Body().sDesc()), ch);
		do_chan.send_char("Arms:         "+((ch.Arms()==null)? "nothing":ch.Arms().sDesc()), ch);
		do_chan.send_char("Hands:        "+((ch.Hands()==null)? "nothing":ch.Hands().sDesc()), ch);
		do_chan.send_char("Left Hand:    "+((ch.Weapon()==null)? "nothing":ch.Weapon().sDesc()), ch);
		//do_chan.send_char("Right Hand: "+((ch.Light()==null)? "nothing":ch.Light().sDesc()), ch);
		do_chan.send_char("Left Finger:  "+((ch.Fingerl()==null)? "nothing":ch.Fingerl().sDesc()), ch);
		do_chan.send_char("Right Finger: "+((ch.Fingerr()==null)? "nothing":ch.Fingerr().sDesc()), ch);
		do_chan.send_char("Waist:        "+((ch.Waist()==null)? "nothing":ch.Waist().sDesc()), ch);
		do_chan.send_char("Legs:         "+((ch.Legs()==null)? "nothing":ch.Legs().sDesc()), ch);
		do_chan.send_char("Feet:         "+((ch.Feet()==null)? "nothing":ch.Feet().sDesc()), ch);
	}

	public static void score(Player ch) {do_chan.send_char(ch.toString(),ch);}
	public static void quit(Player ch) {ch.getCon().close();}
}