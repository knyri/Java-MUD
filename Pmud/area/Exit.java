/* µCOMP:javac -classpath ".." %sµ
 *
 */

package Pmud.area;

import static Pmud.constants.RoomExits.EXIT_NORTH;
import Pmud.Mud;
import Pmud.constants.Bits;

/**
 * Defines an exit for a room.
 * @author Kenneth Pierce
 */
public class Exit {
	private int room[] = Mud.ROOM_START;
	/**
	 * Look description
	 */
	private String desc = "";
	private String keyw = "";
	private int aff = 0; //0-normal 1-door 2-closed 4-locked
	private byte dir = EXIT_NORTH;
	private int key = 0; //vnum of key

	public Exit() {}

	public void setDir(byte ndir)	{dir = ndir;}
	public boolean isAff(int affect)			{return Bits.isSet(aff,affect);}
	public void setDesc(String ndesc)			{desc = ndesc;}
	public void setDest(int[] nroom)			{room = nroom;}
	public void setKeyw(String nkeyw)			{keyw = nkeyw;}
	public void setKey(int nkey)					{key = nkey;}

	public Room Dest() {return Mud.AREAS[room[0]].getRoom(room[1]);}
	public int[] DestVnum()	{return room;}
	public String Desc() {return desc;}
	public byte Dir() {return dir;}
	public int Key()	{return key;}
	public void setAff(int affect)				{aff= Bits.set(aff,affect);}
	public void remAff(int affect)				{aff= Bits.unset(aff,affect);}
	public void setAff(String affect)			{aff = (int)Bits.parse(affect);}
	public boolean equals(String input)		{return (keyw.indexOf(input)>-1);}
}