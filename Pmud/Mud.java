/* µCOMP:javac -classpath ".." %sµ
 *
 */

package Pmud;

import java.io.File;
import java.util.Timer;

import Pmud.area.Area;
import Pmud.character.Mob;
import Pmud.character.Player;
import Pmud.io.IO;
import Pmud.io.NullConnection;

/**Huge interface with defined constants.
 * @author Kenneth Pierce
 */
public final class Mud {
	private Mud(){}
	/** Used by Mob */
	public final static IO NULL_CON = new NullConnection();
	/**A special Mob that does special things. */
	public final static Mob FLUFFY = new Mob();
	/**
	 * Max number of player characters
	 */
	public final static byte MAX_CHAR = (byte)127; //max PC
	//final static short MAX_ROOM = 10000; //max rooms(room VNUMs, if you will)
	/**
	 * Max number of areas
	 */
	public final static short MAX_AREA = 20;
	//final static int MAX_MOB = 60000; //max mobs(mob VNUMs) ARG! would fit in an unsigned short if we had them!
	//final static int MAX_OBJ = 63000; //max objs(obj VNUMs) ARG! would fit in an unsigned short if we had them!
	/**
	 * Max length for the extra description
	 */
	public final static byte MAX_EXD = 30; //max extra desc

	/**
	 * Update timer for scripts and what-not
	 */
	public final static Timer UPDATE = new Timer();
	/**
	 * Fight timer
	 */
	public final static Timer FIGHT = new Timer();
	/**
	 * Area update timer
	 */
	public final static Timer AREAUP = new Timer();

	public final static debug log = new debug(true, "log.txt");

	public static final Player CHARS[] = new Player[MAX_CHAR];
	public static final Area AREAS[] = new Area[MAX_AREA];
	//static Room rooms[] = new Room[MAX_ROOM];
	//static Obj[] objects = new Obj[MAX_OBJ];
	//static Mob[] mob = new Mob[MAX_MOB];
	public final static short[] POW = new short[]{1, 10, 100, 1000, 10000};
	public static final ThreadGroup CONS = new ThreadGroup("connections");

//timing
	public final static int ROUND = 2000,//miliseconds
				UPDATEINT = 15*60*1000,//15min
				MDAY = 60*60*1000,//1hour = 1mday
				MHOUR = (int)(2.5*(60*1000));//2.5min = 1mhour
	//hours and such are in do_func
//days/month
	public final static String[] DAY = new String[] {"Monday", "Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
	public final static String[] MONTH = new String[] {"M1","M2","M3","M4","M5","M6"};

//levels
	/**
	 * important levels
	 */
	public final static byte MAX_LVL = 115,
				LVL_HERO = (byte)(MAX_LVL - 15),
				LVL_IMM = (byte)(MAX_LVL - 14),
				LVL_REB = MAX_LVL;//lvl needed to reboot

//files
	/**
	 * system files
	 */
	public final static String FL_LOGIN = "login.screen",
				FL_PLAYR = "player"+File.separator,
				FL_SYSTM = "sys"+File.separator,
				FL_AREA	= "area"+File.separator;
//sex
	/** player genders
	 */
	public final static byte SEX_NEU = 0,
				SEX_MAL = 1,
				SEX_FEM = 2;

//positions
	/** player positions
	 */
	public final static byte POS_SLEEP	= 0,
				POS_REST		= 1,
				POS_SIT			= 2,
				POS_STAND		= 3,
				POS_FIGHT		= 4;

//align
	/** player alignment thresholds
	 */
	public final static short EVIL = (short)-665,
				GOOD = (short)665;

//rooms
	/**predefined room locations
	 */
	public static final int[] ROOM_START = {0,0};

//login states
	/** login stages
	 */
	public final static byte LOG_NAME	= 0,
				LOG_PASS = 1,
				LOG_NEWC = 2,
				LOG_PLAY = 3;

	//misc things
	/** gender displays
	 */
	public final static String[] SEX  = new String[] {"neuter", "male", "female"};
}