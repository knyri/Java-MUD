/* µCOMP:javac -classpath ".." %sµ */
package Pmud;

import javax.swing.JOptionPane;
import java.io.*;

/**
 * <p>Title: Debugger</p>
 * <p>Description: Provides debuging functions</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Kenneth Pierce
 * @version 1.0
 */

public final class debug {
	private FileOutputStream out = null;
	public static final int mINF = JOptionPane.INFORMATION_MESSAGE;
	public static final int mGEN = JOptionPane.PLAIN_MESSAGE;
	public static final int mASK = JOptionPane.QUESTION_MESSAGE;
	public static final int mWARN = JOptionPane.WARNING_MESSAGE;
	public static final int moYN = JOptionPane.YES_NO_OPTION;
	public static final int moOC = JOptionPane.OK_CANCEL_OPTION;
	public static final int moYNC = JOptionPane.YES_NO_CANCEL_OPTION;
	public static final int moOK = JOptionPane.OK_OPTION;
	public static final int moNO = JOptionPane.NO_OPTION;
	public static final int moCA = JOptionPane.CANCEL_OPTION;
	private void mes(String mess, String title, int type) {
		JOptionPane.showMessageDialog(null, mess, title, type);
	}
//Pro Callers
	public debug(String caller, String s, boolean Release) {
		if (!Release) {
			new debug(caller,s);
		} else {
			mes(s,caller, mWARN);
		}
	}
	public debug(String caller, int s, boolean Release) {
		if (!Release) {
			new debug(caller,s);
		} else {
			mes(String.valueOf(s),caller, mWARN);
		}
	}
	public debug(String caller, double s, boolean Release) {
		if (!Release) {
			new debug(caller,s);
		} else {
			mes(String.valueOf(s),caller, mWARN);
		}
	}
	public debug(String caller, Object s, boolean Release) {
		if (!Release) {
			new debug(caller,s);
		} else {
			mes(String.valueOf(s),caller, mWARN);
		}
	}
	public debug(String caller, float s, boolean Release) {
		if (!Release) {
			new debug(caller,s);
		} else {
			mes(String.valueOf(s),caller, mWARN);
		}
	}
	public debug(String caller, char s, boolean Release) {
		if (!Release) {
			new debug(caller,s);
		} else {
			mes(String.valueOf(s),caller, mWARN);
		}
	}
	public debug(String caller, char[] s, boolean Release) {
		if (!Release) {
			new debug(caller,s);
		} else {
			mes(String.valueOf(s),caller, mWARN);
		}
	}
	public debug(String caller, boolean s, boolean Release) {
		if (!Release) {
			new debug(caller,s);
		} else {
			mes(String.valueOf(s),caller, mWARN);
		}
	}
	public debug(String caller, long s, boolean Release) {
		if (!Release) {
			new debug(caller,s);
		} else {
			mes(String.valueOf(s),caller, mWARN);
		}
	}
//Simple Callers
	public debug(String caller, String mes) {
		System.out.println("-----" + caller + "-----");
		System.out.println(mes);
	}
	public debug(String caller, int s) {
		System.out.println("-----" + caller + "-----");
		System.out.println(s);
	}
	public debug(String caller, double s) {
		System.out.println("-----" + caller + "-----");
		System.out.println(s);
	}
	public debug(String caller, Object s) {
		System.out.println("-----" + caller + "-----");
		System.out.println(s);
	}
	public debug(String caller, float s) {
		System.out.println("-----" + caller + "-----");
		System.out.println(s);
	}
	public debug(String caller, char s) {
		System.out.println("-----" + caller + "-----");
		System.out.println(s);
	}
	public debug(String caller, char[] s) {
			System.out.println("-----" + caller + "-----");
			System.out.println(s);
	}
	public debug(String caller, boolean s) {
		System.out.println("-----" + caller + "-----");
		System.out.println(s);
	}
	public debug(String caller, long s) {
		System.out.println("-----" + caller + "-----");
		System.out.println(s);
	}
//Debug Logging
	public debug(boolean append, File file) {
		try {
		out = new FileOutputStream(file, append);
		} catch (IOException e) {new debug("Debug","Log File Not Found",true);}
	}
	public debug(boolean append, String file) {
		this(append, new File(file));
	}
	public boolean log(String caller, String msg) {
		if (out==null) {
			mes("Log File Not Specified!!","ERROR", mWARN);
			return false;
		}
		try {
			msg = "-----"+caller+"-----\n"+msg+"\n";
			out.write(msg.getBytes());
		}
		catch (IOException ex) {return false;}
		return true;
	}
	public boolean log(String msg) {
		if (out==null) {
			mes("Log File Not Specified!!","ERROR", mWARN);
			return false;
		}
		try {
			out.write(msg.getBytes());
		}
		catch (IOException ex) {return false;}
		return true;
	}
	public void finalize() {
		try {
			out.close();
		} catch(IOException ex) {}
	}
}