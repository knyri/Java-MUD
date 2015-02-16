package Pmud;

/**A command sent by a player
 * <br>Created: 2004
 * @author Kenneth Pierce
 */
public class Command {
	String[] cmd;
	int index = 0;
	public Command(String com) {
		cmd = com.split(" ");
	}
	public String Next() {
		if (index>=(cmd.length-1)) {
			return null;
		}
		return cmd[index++];
	}
	public String Rest() {
		String x = "";
		for (int i =index;i<cmd.length;i++) {
			x += " " + cmd[i];
		}
		return x.trim();
	}
}