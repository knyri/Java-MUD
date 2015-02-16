/* µCOMP:javac -classpath ".." %sµ
 *
 */

package Pmud.character;

import static Pmud.constants.ChatColors.CLR_BLUE;
import static Pmud.constants.ChatColors.CLR_CYAN;
import static Pmud.constants.ChatColors.CLR_DCYAN;
import Pmud.do_chan;
import Pmud.io.Connection;

/** A player character.
 * <br>Created: 2004
 * @author Kenneth Pierce
 */
public class Player extends Character {
	private String bio   = "";
	private String pass  = "";
	//private IO con;
	/**
	 * Favor with chosen deity
	 */
	private short favor = 0;
	/**
	 * Monsters killed
	 */
	private int mkill  = 0;
	/**
	 * Deaths by monsters
	 */
	private short mdeath = 0;
	/**
	 * Player kills
	 */
	private short pkill  = 0;
	/**
	 * Deaths by players
	 */
	private short pdeath = 0;

	public Player(Connection con) {super(con);}

	public void setBio(String nbio)			{bio = nbio;}
	public void setPass(String npass)		{pass = npass;}
	public void setFavor(short nfavor)	{favor = nfavor;}
	public void advance()	{
		setLevel((byte)(Level()+1));
		setHP(MHP());
		setMP(MMP());
		setMV(MMV());
		do_chan.send_char("A rush of power elevates you to a higher plane.\r\n", this);
	}

	public String Pass()	{return pass;}
	public int Mkills()	{return mkill;}
	public short Mdeaths(){return mdeath;}
	public short Pkills()	{return pkill;}
	public short Pdeaths(){return pdeath;}
	public short Favor()	{return favor;}
	public String Bio()		{return bio;}
	@Override
	public String toString() {
		/*
		str:      class       race
		int:      hr dr
		wis:      hp/mhp
		dex:      mp/mmp
		cha:      mv/mmv
		luc
		con
		*/
		StringBuffer x = new StringBuffer(1000);
		x.append(CLR_CYAN+Name()+" "+Title());
		x.append(CLR_BLUE+"\r\n--------------------------------------------------------------------------------\r\n");
		x.append(CLR_DCYAN+"Str: "+CLR_CYAN+Str()+CLR_DCYAN+"     class: "+CLR_CYAN+"Peasant     "+CLR_DCYAN+"Race: "+CLR_CYAN+"Human\r\n");
		x.append(CLR_DCYAN+"Int: "+CLR_CYAN+Int()+CLR_DCYAN+"     Hr: "+CLR_CYAN+HR()+CLR_DCYAN+"     Dr: "+CLR_CYAN+DR()+"\r\n");
		x.append(CLR_DCYAN+"Wis: "+CLR_CYAN+Wis()+CLR_DCYAN+"     Hp: "+CLR_CYAN+HP()+CLR_BLUE+"/"+CLR_CYAN+MHP()+"\r\n");
		x.append(CLR_DCYAN+"Dex: "+CLR_CYAN+Dex()+CLR_DCYAN+"     Mp: "+CLR_CYAN+MP()+CLR_BLUE+"/"+CLR_CYAN+MMP()+"\r\n");
		x.append(CLR_DCYAN+"Cha: "+CLR_CYAN+Cha()+CLR_DCYAN+"     Mv: "+CLR_CYAN+MV()+CLR_BLUE+"/"+CLR_CYAN+MMV()+"\r\n");
		x.append(CLR_DCYAN+"Luc: "+CLR_CYAN+Luc()+"\r\n");
		x.append(CLR_DCYAN+"Con: "+CLR_CYAN+Con()+"\r\n");
		x.append(CLR_BLUE+"--------------------------------------------------------------------------------");
		return do_chan.colorize(x);
	}
}