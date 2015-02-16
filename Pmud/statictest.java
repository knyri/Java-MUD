/* µCOMP:javac -classpath ".." %sµ
 * µEXEC:cmd /K java -classpath .. Pmud.testµ
 */

package Pmud;

import Pmud.area.Exit;
import Pmud.area.Room;
import Pmud.character.Player;

public class statictest {
	public static String[] t = new String[1];
	//public statictest() {System.out.println(rooms[0].toString());}
	public static void main(String[] arg) {
		System.out.println(do_func.NaN("n3"));
		System.out.println(((byte)255));
		byte x = -1;
		byte y = 15;
		short res = 0;
		res = (byte)((x)^(y));//-1-15
		res = (byte)((-x)^(y));//15+(-1)
		res = (byte)((-x)^(-y));//-1-15
		res = (byte)((x)^(-y));//15+(-1)
		res = (byte)((x)&(-y));//15(-1)
		res = (byte)((x)&(y));//15(1)
		res = (byte)((-x)&(-y));//1
		res = (byte)((x)|(-y));//-1
		res = (byte)((x)|(y));//-1
		System.out.println(res);
		String[] te = new String[1];
		System.out.println(t[0]);
		System.out.println(te[0]);
		Player ch = new Player(null);
		ch.setName("klomp");
		//ch = do_func.read_char(ch);
		System.out.println(ch.toString());
		{
			Room room = new Room();
			room.setTitle("Limbo");
			room.setDesc("You are in Limbo. You can see nothing for ages.\r\nYou emit a warm glow as you float through the nothingness.");
			Exit e = new Exit();
			e.setDir(Pmud.constants.RoomExits.EXIT_UP);
			//e.setDest(1);
			room.addExit(e, null);
			//mud.rooms[0] = room;
		}
		//new test();
	}
}