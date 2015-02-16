/* µCOMP:javac -classpath ".." %sµ
 *
 */

package Pmud.character;

import static Pmud.constants.Affects.Char.AFF_HIDE;
import static Pmud.constants.Affects.Char.AFF_INVIS;
import static Pmud.constants.Affects.Char.AFF_SANC;
import static Pmud.constants.ChatColors.CLR_BLACK;
import static Pmud.constants.ChatColors.CLR_DWHITE;
import static Pmud.constants.ChatColors.CLR_WHITE;
import static Pmud.constants.ChatColors.clr_PC;
import Pmud.Mud;
import Pmud.do_func;
import Pmud.area.Area;
import Pmud.area.Room;
import Pmud.constants.Bits;
import Pmud.io.IO;
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
/**Base class for all players.<br>
 * Affects MUST use the add and sub methods for modifying stats!
 * <br>Created: 2004
 * @author Kenneth Pierce
 */
public abstract class Character {

	private final IO con;
	private String name  = "guest";
	private String title = "the quest master.";
	/** Shown when 'look'ed at. */
	private String desc  = "A queer little person stands before you.";
	/**Prompt for commands.
	 * @see do_func#do_prompt(Player)
	 */
	private String prompt = "<&y%h/%Hhp &c%m/%Mmn &p%v/%Vmv&W>&r%O ";
	private CharacterClass Class = CharacterClass.Peasant;
	private CharacterRace race  = CharacterRace.Human;
	private byte pos   = Mud.POS_STAND;
	private byte sex	 = Mud.SEX_NEU;
	private int[] room  = Mud.ROOM_START;
	private byte level = 1;
	private byte base_str = 10;
	private byte base_dex = 10;
	private byte base_int = 10;
	private byte base_wis = 10;
	private byte base_cha = 10;
	private byte base_luc = 10;
	private byte base_con = 10;
	private byte mod_str = 0;
	private byte mod_dex = 0;
	private byte mod_int = 0;
	private byte mod_wis = 0;
	private byte mod_cha = 0;
	private byte mod_luc = 0;
	private byte mod_con = 0;
	private int aff = 0;

	/** current health */
	private int HP  = 20;
	/** max health */
	private int MHP = 20;
	private int mod_MHP= 0;
	/** current mana */
	private int MP  = 10;
	/** max mana */
	private int MMP = 10;
	private int mod_MMP= 0;
	/** current moves */
	private int MV  = 150;
	/** max moves */
	private int MMV = 150;
	private int mod_MMV= 0;
	/** damage roll when unarmed */
	private byte DR = 0;
	private byte mod_DR= 0;
	/** chance to hit */
	private byte HR = 0;
	private byte mod_HR= 0;
	/** armor class. physical damage reduction. */
	private short AC = 0;
	private short mod_AC= 0;
	/** TODO: figure out what this did. */
	private short AL = 0;
	/**
	 * Used for setting min/max alignment
	 * caused by wearing evil/holy items
	 */
	private short mod_AL= 0;
	/** total experience */
	private int exp = 0;
	/** total money in bank */
	private int bank = 0;
	/** total money on person */
	private int gold = 0;
	/** inventory slots */
	private Obj[] inv = new Obj[20];
	private Light light;
	private Head head;
	private Ear ears;
	private Neck neck1;
	private Neck neck2;
	private Body body;
	private Arms arms;
	private Finger fingerl;
	private Finger fingerr;
	private Hands hands;
	private Weapon weap;
	private Waist waist;
	private Legs legs;
	private Feet feet;
	/** default target for auto-attack and offensive spells */
	private Character vict;
	/**Sync objects */
	private final Object moneyLock = new Object(), statLock = new Object(), equipLock = new Object(), invLock= new Object();

	public Character(IO con) {
		this.con = con;
	}
	public IO getCon() {return con;}
	/** adds an affect to the player
	 * @param affect
	 */
	public synchronized void setAff(int affect)				{aff= Bits.set(aff,affect);}
	/** removes an affect from the player
	 * @param affect
	 */
	public synchronized void remAff(int affect)				{aff= Bits.unset(aff,affect);}
	/** Sets the affects for this player
	 * @param affect
	 */
	public synchronized void setAff(String affect)			{aff= Bits.set(aff,(int)Bits.parse(affect));}
	/** tests to see if the player is effected by an affect
	 * @param affect
	 * @return
	 */
	public boolean isAff(int affect)	{return Bits.isSet(aff,affect);}
	/** gets a string representation of the affects.
	 * @return
	 * @see Pmud.Bits#toString()
	 */
	public String getAff() {return Bits.toString(aff);}
	public void setBank(int nbank)		{synchronized(moneyLock) {bank = nbank;}}
	/** removes or adds gold to bank and adds or removes gold to the player.
	 * @param amount
	 */
	public void addBank(int amount)		{synchronized(moneyLock) {bank += amount; gold -= amount;}}
	public void setGold(int ngold)		{synchronized(moneyLock) {gold = ngold;}}
	public void addGold(int amount)		{synchronized(moneyLock) {gold += amount;}}
	public void setPrompt(String nprompt) {prompt = nprompt;}
	public void setName(String nname)		{name = nname;}
	public void setClass(CharacterClass nClass)		{
		if(nClass==null)nClass= CharacterClass.Peasant;
		Class = nClass;
		updateStatMod();
	}
	public void setRace(CharacterRace nrace)			{
		if(nrace==null) nrace= CharacterRace.Human;
		race = nrace;
		updateStatMod();
	}
	public void setPos(byte npos)				{pos = npos;}
	public void setRoom(int[] nroom)			{room = nroom;}
	public void setTitle(String ntitle)	{title = ntitle;}
	public void setDesc(String ndesc)		{desc = ndesc;}
	public void setHP(int nHP)					{synchronized(statLock) {HP = nHP;}}
	public void setMP(int nMP)					{synchronized(statLock) {MP = nMP;}}
	public void setMV(int nMV)					{synchronized(statLock) {MV = nMV;}}
	public void setMMV(int nMMV)				{synchronized(statLock) {MMV = nMMV;}}
	public void setMMP(int nMMP)				{synchronized(statLock) {MMP = nMMP;}}
	public void setMHP(int nMHP)				{synchronized(statLock) {MHP = nMHP;}}
	public void setAC(short nAC)				{synchronized(statLock) {AC = nAC;}}
	public void setAL(short nAL)				{synchronized(statLock) {AL = nAL;}}
	public void setExp(int nexp)				{synchronized(statLock) {exp = nexp;}}
	public void setAlign(short nalign)	{synchronized(statLock) {AL = nalign;}}

	public void modHP(int nHP) {
		synchronized(statLock) {
			HP+= nHP;
			if(HP>MHP) HP= MHP;
		}
	}
	public void modMP(int nMP)	 {
		synchronized(statLock) {
			MP+= nMP;
			if(MP>MMP) MP= MMP;
		}
	}
	public void modMV(int nMV) {
		synchronized(statLock) {
			MV+= nMV;
			if(MV > MMV) MV=MMV;
		}
	}
	public void modMMV(int amt){synchronized(statLock) {mod_MMV+= amt;}}
	public void modMMP(int amt){synchronized(statLock) {mod_MMP+= amt;}}
	public void modMHP(int amt){synchronized(statLock) {mod_MHP+= amt;}}
	public void modAC(short amt){synchronized(statLock) {mod_AC+= amt;}}
	public void modAL(short amt){synchronized(statLock) {mod_AL+= amt;}}
	public void modExp(int amt){synchronized(statLock) {exp+= amt;}}
	public void modAlign(short amt){synchronized(statLock) {AL+= amt;}}

	public void setLevel(byte lvl)			{synchronized(statLock) {level = lvl;}}
	public void setSex(byte nsex)			{synchronized(statLock) {sex = nsex;}}
	public void setLight(Light nlight)		{synchronized(equipLock) {light = nlight;}}
	public void setHead(Head nhead)			{synchronized(equipLock) {head = nhead;}}
	public void setEars(Ear nears)			{synchronized(equipLock) {ears = nears;}}
	public void setNeck1(Neck nneck)		{synchronized(equipLock) {neck1 = nneck;}}
	public void setNeck2(Neck nneck)		{synchronized(equipLock) {neck2 = nneck;}}
	public void setBody(Body nbody)			{synchronized(equipLock) {body = nbody;}}
	public void setArms(Arms narms)			{synchronized(equipLock) {arms = narms;}}
	public void setFingerl(Finger nfingerl){synchronized(equipLock) {fingerl = nfingerl;}}
	public void setFingerr(Finger nfingerr){synchronized(equipLock) {fingerr = nfingerr;}}
	public void setHands(Hands nhands)		{synchronized(equipLock) {hands = nhands;}}
	public void setWeapon(Weapon nweap)		{synchronized(equipLock) {weap = nweap;}}
	public void setWaist(Waist nwaist)		{synchronized(equipLock) {waist = nwaist;}}
	public void setLegs(Legs nlegs)			{synchronized(equipLock) {legs = nlegs;}}
	public void setFeet(Feet nfeet)			{synchronized(equipLock) {feet = nfeet;}}
	public void setVict(Character nvict)			{vict = nvict;}
	public boolean addObjInv(Obj add)	{
		synchronized(invLock){
			for (int i = 0;i<inv.length;i++) {
				if (inv[i]==null) {
					inv[i] = add;
					return true;
				}
			}
		}
		return false;
	}
	public void remObjInv(Obj rem)	{
		synchronized(invLock){
			for (int i = 0;i<inv.length;i++) {
				if (inv[i]!=null) {
					if (inv[i]==rem) {
						inv[i] = null;
						System.arraycopy(inv, i+1, inv, i, (inv.length-1)-i);
						return;
					}
				} else {return;}
			}
		}
	}
	public Obj getObjInv(String obj)	{
		synchronized(invLock){
			for (int i = 0;i<inv.length;i++) {
				if (inv[i]!=null) {
					if (inv[i].equals(obj)) {
						return inv[i];
					}
				} else {return null;}
			}
		}
		return null;
	}
	public String 	Name()		{return name;}
	public String	Title()		{return title;}
	public String	Desc()		{return desc;}
	public String	Prompt()	{return prompt;}
	public CharacterClass 	Class()		{return Class;}
	public CharacterRace 	Race()		{return race;}
	public byte 	Pos()		{return pos;}
	public Room 	Room()		{return Mud.AREAS[room[0]].getRoom(room[1]);}
	public Area 	Area()		{return Mud.AREAS[room[0]];}
	public byte 	HR()		{return (byte)((mod_dex*2/3)+HR);}
	public byte 	DR()		{return (byte)((mod_str*4/5)+DR);}
	public int  	HP()		{return HP;}
	public int  	MP()		{return MP;}
	public int  	MV()		{return MV;}
	public int  	MMV()		{return MMV+mod_MMV;}
	public int  	MMP()		{return MMP+mod_MMP;}
	public int  	MHP()		{return MHP+mod_MHP;}
	public int	AC()		{return AC+mod_AC;}
	public int	Align()		{return AL+mod_AL;}
	public byte		Level()		{return level;}
	public int		Exp()		{return exp;}
	public byte		Sex()		{return sex;}
	public Light	Light()		{return light;}
	public Head		Head()		{return head;}
	public Ear		Ears()		{return ears;}
	public Neck		Neck1()		{return neck1;}
	public Neck		Neck2()		{return neck2;}
	public Body		Body()		{return body;}
	public Arms		Arms()		{return arms;}
	public Finger	Fingerl()	{return fingerl;}
	public Finger	Fingerr()	{return fingerr;}
	public Hands	Hands()		{return hands;}
	public Weapon	Weapon()	{return weap;}
	public Waist	Waist()		{return waist;}
	public Legs		Legs()		{return legs;}
	public Feet		Feet()		{return feet;}
	public Obj[]	Inv()		{return inv;}
	public Character	Vict()		{return vict;}
	public int		HitPer()	{return ((HP*100)/MHP);}
	public int		Bank()		{return bank;}
	public int		Gold()		{return gold;}

	public void updateStatMod(){
		//TODO: this
	}
	protected void resetStatMods(){
		mod_AC=mod_AL=mod_cha=mod_con=mod_dex=mod_DR=mod_HR=mod_int=mod_luc=mod_str=mod_wis=0;
		mod_MHP=mod_MMP=mod_MMV=0;
	}


	/** The players actual strength
	 * @return
	 */
	public byte Str() {return (byte)(base_str+mod_str);}
	/** The players actual intelligence
	 * @return
	 */
	public byte Int() {return (byte)(base_int+mod_int);}
	/** The players actual wisdom
	 * @return
	 */
	public byte Wis() {return (byte)(base_wis+mod_wis);}
	/** The players actual dexterity
	 * @return
	 */
	public byte Dex() {return (byte)(base_dex+mod_dex);}
	/** The players actual charisma
	 * @return
	 */
	public byte Cha() {return (byte)(base_cha+mod_cha);}
	/** The players actual luck
	 * @return
	 */
	public byte Luc() {return (byte)(base_luc+mod_luc);}
	/** The players actual constitution
	 * @return
	 */
	public byte Con() {return (byte)(base_con+mod_con);}
	/** The players base strength
	 * @return
	 */
	public byte bStr() {return base_str;}
	/** The players base intelligence
	 * @return
	 */
	public byte bInt() {return base_int;}
	/** The players base wisdom
	 * @return
	 */
	public byte bWis() {return base_wis;}
	/** The players base dexterity
	 * @return
	 */
	public byte bDex() {return base_dex;}
	/** The players base charisma
	 * @return
	 */
	public byte bCha() {return base_cha;}
	/** The players base luck
	 * @return
	 */
	public byte bLuc() {return base_luc;}
	/** The players base constitution
	 * @return
	 */
	public byte bCon() {return base_con;}
	/** sets the base strength. Should only be called by
	 * immortal commands.
	 * @param v
	 */
	public void setStr(byte v) {synchronized(statLock) {base_str=v;}}
	/** sets the base intelligence. Should only be called by
	 * immortal commands.
	 * @param v
	 */
	public void setInt(byte v) {synchronized(statLock) {base_int=v;}}
	/** sets the base wisdom. Should only be called by
	 * immortal commands.
	 * @param v
	 */
	public void setWis(byte v) {synchronized(statLock) {base_wis=v;}}
	/** sets the base dexterity. Should only be called by
	 * immortal commands.
	 * @param v
	 */
	public void setDex(byte v) {synchronized(statLock) {base_dex=v;}}
	/** sets the base charisma. Should only be called by
	 * immortal commands.
	 * @param v
	 */
	public void setCha(byte v) {synchronized(statLock) {base_cha=v;}}
	/** sets the base luck. Should only be called by
	 * immortal commands.
	 * @param v
	 */
	public void setLuc(byte v) {synchronized(statLock) {base_luc=v;}}
	/** sets the base constitution. Should only be called by
	 * immortal commands.
	 * @param v
	 */
	public void setCon(byte v) {synchronized(statLock) {base_con=v;}}
	/** adds to the modifier
	 * @param mod
	 */
	public void addStr(byte mod) {synchronized(statLock) {mod_str+=mod;}}
	/** subtracts from the modifier
	 * @param mod
	 */
	public void subStr(byte mod) {synchronized(statLock) {mod_str-=mod;}}
	/** adds to the modifier
	 * @param mod
	 */
	public void addDex(byte mod) {synchronized(statLock) {mod_dex+=mod;}}
	/** subtracts from the modifier
	 * @param mod
	 */
	public void subDex(byte mod) {synchronized(statLock) {mod_dex-=mod;}}
	/** adds to the modifier
	 * @param mod
	 */
	public void addInt(byte mod) {synchronized(statLock) {mod_int+=mod;}}
	/** subtracts from the modifier
	 * @param mod
	 */
	public void subInt(byte mod) {synchronized(statLock) {mod_int-=mod;}}
	/** adds to the modifier
	 * @param mod
	 */
	public void addWis(byte mod) {synchronized(statLock) {mod_wis+=mod;}}
	/** subtracts from the modifier
	 * @param mod
	 */
	public void subWis(byte mod) {synchronized(statLock) {mod_wis-=mod;}}
	/** adds to the modifier
	 * @param mod
	 */
	public void addCha(byte mod) {synchronized(statLock) {mod_cha+=mod;}}
	/** subtracts from the modifier
	 * @param mod
	 */
	public void subCha(byte mod) {synchronized(statLock) {mod_cha-=mod;}}
	/** adds to the modifier
	 * @param mod
	 */
	public void addLuc(byte mod) {synchronized(statLock) {mod_luc+=mod;}}
	/** subtracts from the modifier
	 * @param mod
	 */
	public void subLuc(byte mod) {synchronized(statLock) {mod_luc-=mod;}}
	/** adds to the modifier
	 * @param mod
	 */
	public void addCon(byte mod) {synchronized(statLock) {mod_con+=mod;}}
	/** subtracts from the modifier
	 * @param mod
	 */
	public void subCon(byte mod) {synchronized(statLock) {mod_con-=mod;}}
	/**Formatted String with any visible affects.
	 * @return Formatted String of visible affects.
	 */
	public String Aff(){
		StringBuffer buf = new StringBuffer(255);
		buf.append(clr_PC);
		if (isAff(Pmud.constants.Affects.AFF_ANY)) {
			if (isAff(AFF_HIDE)) {
				buf.append("("+CLR_WHITE+"hidden"+clr_PC+") ");
			}
			if (isAff(AFF_INVIS)) {
				buf.append("("+CLR_WHITE+"invisible"+clr_PC+") ");
			}
			if (isAff(AFF_SANC)) {
				if (Align()<Mud.EVIL) {
					buf.append("("+CLR_BLACK+"luminous aura"+clr_PC+") ");
				} else
				if (Align()>Mud.GOOD) {
					buf.append("("+CLR_WHITE+"luminous aura"+clr_PC+") ");
				} else {
					buf.append("("+CLR_DWHITE+"luminous aura"+clr_PC+") ");
				}
			}
		}
		return new String(buf);
	}
	@Override
	public String toString() {
		StringBuffer x = new StringBuffer(1000);
		x.append(Name()+" "+Title());
		x.append("\r\n--------------------------------------------------------------------------------\r\n");
		x.append("Str: "+Str()+"\tInt: "+Int()+"\tWis: "+Wis()+"\tDex: "+Dex()+"\tCha: "+Cha()+"\tLuc: "+Luc()+"Con: "+Con()+"\r\n");
		x.append("Hp: "+HP()+"/"+MHP()+"  Mp: "+MP()+"/"+MMP()+"  Mv: "+MV()+"/"+MMV()+"\r\n");
		x.append("Class: Peasant\tRace: Human\tHR: "+HR()+"\tDR:"+DR()+"\r\n");
		x.append("--------------------------------------------------------------------------------");
		return new String(x);
	}
	public synchronized void send(String msg) {
		con.write(("\r\n"+msg+CLR_DWHITE).getBytes());
	}
	/**Send message to everyone in the room except this person
	 * @param msg
	 */
	public void send_around_room(String msg) {
		Player[] chs = Room().Chars();
		for (int i = 0; i<chs.length;i++) {
			if (chs[i]!=null) {
				if (chs[i]!=this) {
					chs[i].send(msg);
					do_func.do_prompt(chs[i]);
				}
			} else {return;}
		}
	}
	/**Send message to everyone in the world except this person
	 * @param msg
	 */
	public void send_around_world(String msg) {
		for (int i = 0;i<Mud.CHARS.length;i++) {
			if (Mud.CHARS[i]!=null) {
				if (Mud.CHARS[i]!=this) {
					Mud.CHARS[i].send(msg);
					do_func.do_prompt(Mud.CHARS[i]);
				}
			} else {return;}
		}
	}
}
