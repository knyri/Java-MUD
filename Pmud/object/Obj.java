/* µCOMP:javac -classpath ".." %sµ
 *
 */

package Pmud.object;

import static Pmud.constants.Affects.Object.AFF_BLESS;
import static Pmud.constants.Affects.Object.AFF_EVIL;
import static Pmud.constants.Affects.Object.AFF_MAGIC;
import static Pmud.constants.ChatColors.CLR_DYELLOW;
import static Pmud.constants.ChatColors.CLR_RED;
import static Pmud.constants.ChatColors.CLR_YELLOW;
import static Pmud.constants.ChatColors.clr_obj;

import java.util.Vector;

import Pmud.Mud;
import Pmud.area.Room;
import Pmud.constants.Bits;
import Pmud.event.ItemListener;

/**Base class for all objects in the world
 * <br>Created: 2004
 * @author Kenneth Pierce
 */
public class Obj implements Cloneable {
	private final Vector<ItemListener> listeners = new Vector<ItemListener>();
	private int[] vnum = {0, 0};
	private String name = "";
	private String sdesc = "";
	private String ldesc = "";
	private String desc = "";
	/**
	 * TODO: get rid of this
	 * <dl>
	 * <dt>0</dt><dd>item level</dd>
	 * <dt>1</dt><dd>armor value</dd>
	 * <dt>2</dt><dd>armor value modification</dd>
	 * <dt>3</dt><dd>weight</dd>
	 * <dt>4</dt><dd>worth</dd>
	 * <dt>5</dt><dd>unused</dd>
	 * <dt>6</dt><dd>unused</dd>
	 * </dl>
	 */
	private final int[] values = new int[] {0,0,0,0,0,0,0};
	private final int[] room = Mud.ROOM_START;
	private int aff = 0;

	public Obj() {}

	public void addListener(ItemListener il) {
		listeners.add(il);
	}
	/**See {@link #values}
	 * @param v
	 * @return
	 */
	public int getV(int v) {return values[v];}
	public boolean equals(String keyword) {return (name.indexOf(keyword)>-1);}
	public String Name()			{return name;}
	public String sDesc()			{return sdesc;}
	public String lDesc()			{return ldesc;}
	public String Desc()			{return desc;}
	public int[] Vnum() {return vnum;}
	public Room Room() {return Mud.AREAS[room[0]].getRoom(room[1]);}
	public boolean inRoom(int[] in) {return ((in[0]==room[0])&&(in[1]==room[1]));}
	public boolean isAff(int affect)	{return Bits.isSet(aff,affect);}

	public void setVnum(int[] nvnum) {vnum=nvnum;}
	public void setAff(int affect)				{aff= Bits.set(aff,affect);}
	public void remAff(int affect)				{aff= Bits.unset(aff,affect);}
	public void setAff(String affect)			{aff=0;setAff((int)Bits.parse(affect));}
	public void setRoom(int[] nroom)	{room[0] = nroom[0];room[1] = nroom[1];}
	public void setName(String nname) {name = nname;}
	public void setsDesc(String nsdesc) {sdesc = nsdesc;}
	public void setlDesc(String nldesc) {ldesc = nldesc;}
	public void setDesc(String ndesc) {desc = ndesc;}
	public void setV(int index, int value) {values[index] = value;}
	public void setWorth(short worth) {setV(1, worth);}
	public void setLevel(short level) {setV(0, level);}
	@Override
	public Object clone() {
		Obj temp = new Obj();
		temp.setAff(Bits.toString(aff));
		temp.setName(Name());
		temp.setsDesc(sDesc());
		temp.setlDesc(lDesc());
		temp.setVnum(Vnum());
		for (byte i = 0;i<values.length;i++) {
			temp.setV(i, getV(i));
		}
		temp.setRoom(Room().Vnum());
		temp.listeners.addAll(listeners);
		return temp;
	}

	/** The item level of this object.
	 * @return
	 */
	public int Level() {return getV(0);}
	/** The armor value of this object.
	 * @return
	 */
	public int AC() {return getV(1);}
	/** Any armor value modification on this object.
	 * @return
	 */
	public int ACM() {return getV(2);}
	/** The weight of this object.
	 * @return
	 */
	public int Weight() {return getV(3);}
	/** The base worth of this object
	 * @return
	 */
	public int Worth() {return getV(4);}
	/** Compares the name of the two objects.
	 * @param object
	 * @return
	 */
	public boolean equals(Obj object) {
		return object.Name().equals(name);
	}
	/** Returns a String containing the colored effects on this object.<br>
	 * (bless, evil, magic, etc)
	 * @return
	 */
	public String DescAff() {
		StringBuilder buf = new StringBuilder(255);
		buf.append(clr_obj);
		if (isAff(Pmud.constants.Affects.AFF_ANY)) {
			if (isAff(AFF_BLESS)) {
				buf.append("("+CLR_YELLOW+"glowing"+clr_obj+") ");
			}
			if (isAff(AFF_EVIL)) {
				buf.append("("+CLR_RED+"glowing"+clr_obj+") ");
			}
			if (isAff(AFF_MAGIC)) {
				buf.append("("+CLR_DYELLOW+"floating"+clr_obj+") ");
			}
		}
		return buf.toString();
	}
}