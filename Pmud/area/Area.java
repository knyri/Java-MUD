/* µCOMP:javac -classpath ".." %sµ
 *
 */

package Pmud.area;

import static Pmud.constants.ChatColors.clr_reset;

import java.util.TimerTask;

import Pmud.Mud;
import Pmud.do_chan;
import Pmud.character.Mob;
import Pmud.object.Obj;

/**An area in the world. Could be a city, cave, etc.
 * <br>Created: 2004
 * @author Kenneth Pierce
 */
public class Area extends TimerTask {
	private Room[] rooms;
	private Obj[] objs;
	private Mob[] mobs;
	private String name;
	private String auth;
	private String reset = "A wave of magical energy surges by.";
	private int vnum;
	private Reset[] resets = new Reset[0];
	public Area() {
		this(0, 1, "", "");
	}
	/**
	 * @param avnum area's vnum
	 * @param roomn total number of rooms
	 * @param aauth Area's author
	 * @param aname Area's name
	 */
	public Area(int avnum, int roomn, String aauth, String aname) {
		this(avnum, new Room[roomn], aauth, aname);
	}
	public Area(int avnum, Room[] rooms, String aauth, String aname) {
		this.rooms = rooms;
		name = aname;
		auth = aauth;
		vnum = avnum;
		Mud.AREAUP.scheduleAtFixedRate(this, Mud.UPDATEINT, Mud.UPDATEINT);
	}
	@Override
	public void run() {
		do_chan.send_area(clr_reset+reset, vnum);
		if (resets!=null) {
			for (int i = 0;i < resets.length;i++) {
				if (resets[i]!=null) {
					resets[i].go();
				} else {
System.out.println("Reset["+i+"] of area "+name+" does not exist.");
				}
			}
System.out.println("Resetting area "+ name);
		}
	}
	public void setReset(String msg) {reset = msg;}
	public void setAuth(String aauth) {auth = aauth;}
	public void setName(String aname) {name = aname;}
	public void setVnum(int nvnum) {vnum = nvnum;}
	public void setRoom(int num) {
		if (rooms != null) {
			Room[] temp = rooms;
			rooms = new Room[num];
			System.arraycopy(temp, 0, rooms, 0, temp.length-1);
		}else{
			rooms = new Room[num];
		}
	}
	public void setObj(int num) {
		if (objs != null) {
			Obj[] temp = objs;
			objs = new Obj[num];
			System.arraycopy(temp, 0, objs, 0, temp.length-1);
		}else{
			objs = new Obj[num];
		}
	}
	public void setMob(int num) {
		if (mobs != null) {
			Mob[] temp = mobs;
			mobs = new Mob[num];
			System.arraycopy(temp, 0, mobs, 0, temp.length-1);
		}else{
			mobs = new Mob[num];
		}
	}
	public void addReset(char code, int room, int vnum, int amt) {
		Reset[] temp = resets;
		resets = new Reset[resets.length+1];
		System.arraycopy(temp, 0, resets, 0, temp.length);
		resets[resets.length-1] = new Reset(code, room, vnum, amt);
	}
	public void addRoom(Room room) {
		for (int i = 0;i<rooms.length;i++) {
			if (rooms[i]==null) {
				rooms[i] = room;
				rooms[i].setVnum(vnum, i);
				return;
			}
		}
		Room[] temp = rooms;
		rooms = new Room[rooms.length+1];
		System.arraycopy(temp,0,rooms,0,temp.length-1);
		rooms[rooms.length-1] = room;
System.out.println("Expanded area "+name+"'s rooms.");
	}
	public void addObj(Obj obj) {
		for (int i = 0;i<objs.length;i++) {
			if (objs[i]==null) {
				objs[i] = obj;
				objs[i].setVnum(new int[]{vnum, i});
				return;
			}
		}
		Obj[] temp = objs;
		objs = new Obj[objs.length+1];
		System.arraycopy(temp,0,objs,0,temp.length-1);
		objs[objs.length-1] = obj;
System.out.println("Expanded area "+name+"'s objects.");
	}
	public void addMob(Mob mob) {
			for (int i = 0;i<mobs.length;i++) {
				if (mobs[i]==null) {
					mobs[i] = mob;
					mobs[i].setVnum(new int[]{vnum, i});
					return;
				}
			}
			Mob[] temp = mobs;
			mobs = new Mob[mobs.length+1];
			System.arraycopy(temp,0,mobs,0,temp.length-1);
			mobs[mobs.length-1] = mob;
System.out.println("Expanded area "+name+"'s mobs.");
	}
	public String Name() {return name;}
	public String Auth() {return auth;}
	public String Reset() {return reset;}
	public int Vnum() {return vnum;}
	public Room getRoom(int i) {return rooms[i];}
	public Room[] getRooms() {return rooms;}
	public Obj getObj(int i) {return objs[i];}
	public Obj[] Objs() {return objs;}
	public Mob getMob(int i) {return mobs[i];}
	public Mob[] Mobs() {return mobs;}

	protected int count(Obj obj) {
		int j = 0;
		for (int i = 0;i<rooms.length;i++) {
			if (rooms[i].inRoom(obj)) {
				j++;
			}
		}
		return j;
	}
	protected int count(Mob mob) {
		int j = 0;
		for (int i = 0;i<rooms.length;i++) {
			if (rooms[i].inRoom(mob)) {
				j++;
			}
		}
		return j;
	}
	public class Reset {
		char code;
		int room;
		int rvnum;
		int amt;
		public Reset(char ncode, int nroom, int nvnum, int namt) {
			code = ncode;
			//area = narea;
			room = nroom;
			rvnum = nvnum;
			amt = namt;
		}
		public void go() {
			switch(code) {
				case 'S':
					break;
				case 'O':
					if (count(Mud.AREAS[vnum].Objs()[rvnum])<amt){
						Mud.AREAS[vnum].getRoom(room).addObj(Mud.AREAS[vnum].Objs()[rvnum]);
					}
					break;
				case 'M':
					if (count(Mud.AREAS[vnum].Mobs()[rvnum])<amt){Mud.AREAS[vnum].getRoom(room).addMob(Mud.AREAS[vnum].Mobs()[rvnum]);}
					break;
				case 'E':
					break;
			}
		}
	}
}
