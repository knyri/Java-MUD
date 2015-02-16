package Pmud;

import static Pmud.Mud.CONS;
import static Pmud.Mud.FLUFFY;
import static Pmud.Mud.MHOUR;
import static Pmud.Mud.UPDATE;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import Pmud.io.Connection;

/**Class that starts the mud
 * @author Kenneth Pierce
 */
public class Run {
	/*public Run() {
		try {
			ServerSocket s = new ServerSocket(4000);
			while (true) {
				Socket client = s.accept();
				new Connection(client, "connection "+cons.activeCount(), cons).start();
			}
		} catch (IOException ex) {}
	}*/
	public static void main(String arg[]) {
		do_file.load_areas();
		FLUFFY.setCha(Byte.MAX_VALUE);
		FLUFFY.setCon(Byte.MAX_VALUE);
		FLUFFY.setDex(Byte.MAX_VALUE);
		FLUFFY.setInt(Byte.MAX_VALUE);
		FLUFFY.setStr(Byte.MAX_VALUE);
		FLUFFY.setWis(Byte.MAX_VALUE);
		FLUFFY.setMHP(Integer.MAX_VALUE);
		FLUFFY.setMMP(Integer.MAX_VALUE);
		FLUFFY.setMMV(Integer.MAX_VALUE);
		FLUFFY.setHP(Integer.MAX_VALUE);
		FLUFFY.setMP(Integer.MAX_VALUE);
		FLUFFY.setMV(Integer.MAX_VALUE);
		FLUFFY.setHP(Integer.MAX_VALUE);

		UPDATE.scheduleAtFixedRate(new do_func.update(), MHOUR, MHOUR);
		try {
			ServerSocket s = new ServerSocket(4000);
			while (true) {
				Socket client = s.accept();
				new Connection(client, "connection "+CONS.activeCount(), CONS).start();
System.out.println("connection "+CONS.activeCount());
			}
		} catch (IOException ex) {}
		//new Run() save this only if we later need to
	}
}