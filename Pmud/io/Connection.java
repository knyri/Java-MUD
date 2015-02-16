/* µCOMP:javac -classpath ".." %sµ
 * µEXEC:cmd /K java -classpath .. Pmud.Connectionµ
 */

package Pmud.io;

import static Pmud.Mud.FL_LOGIN;
import static Pmud.Mud.FL_PLAYR;
import static Pmud.Mud.LOG_NAME;
import static Pmud.Mud.LOG_NEWC;
import static Pmud.Mud.LOG_PASS;
import static Pmud.Mud.LOG_PLAY;
import static Pmud.Mud.ROOM_START;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
//import java.net.ServerSocket;
import java.net.Socket;

import Pmud.do_chan;
import Pmud.do_file;
import Pmud.do_func;
import Pmud.do_move;
import Pmud.character.Player;
/** Basic connection to the server. Each Character has one.
 * <br>Created: 2004
 * @author Kenneth Pierce
 * @version 1.0
 */
public final class Connection extends Thread implements IO {
  private final Socket sock;
  private final InputStream in;
  private final OutputStream out;
  private final Player ch;
  public Connection(Socket socket,String name, ThreadGroup group) throws IOException {
    super(group,name);
    sock = socket;
    in = sock.getInputStream();
    out = sock.getOutputStream();
    ch = new Player(this);
  }
  @Override
public void run() {
    try {
      byte b[] = new byte[256];
      {
      do_file.do_show_file(FL_LOGIN,ch);
      String x="";
      byte state = LOG_NAME;
      while(state<LOG_PLAY) {
      	switch(state) {
      		case LOG_NAME:
      				do_chan.send_char("Name: ",ch);
      				if (read(b)) {
      					ch.setName(new String(b).trim());
      					if (ch.Name().equals("new")) {state=LOG_NEWC;break;}
      					File f = new File(FL_PLAYR+ch.Name()+".plr");
      					if (f.exists()) {
      						do_file.read_char(ch);
      						state++;
      					} else {do_chan.send_char("That player doesn't exist.", ch);}
      				} else {state=LOG_PLAY;}
      			break;
      		case LOG_PASS:
      			do_chan.send_char("Password for "+ch.Name()+": ",ch);
      			if (read(b)) {if(new String(b).trim().equals(ch.Pass())){state=LOG_PLAY;}}else{state=LOG_PLAY;}
      			break;
      		case LOG_NEWC:
      			do_chan.send_char("Pick a name: ", ch);
      			if (read(b)) {
      				x = new String(b).trim();
      				if (x.equals("")) {break;}
      				File f = new File(x+".plr");
      				if (f.exists()) {do_chan.send_char("Player already exists.", ch);break;}
      				ch.setName(x);
      				do_chan.send_char("Pick a password: ", ch);
      				if (read(b)) {ch.setPass(new String(b).trim());}
      			}
      			//do_func.send_char("Not yet implemented", ch);
      			state = LOG_PLAY;
      			break;
      	}
      }
      }
      do_chan.info(ch.Name()+" has blessed us with their pressence!");
      do_func.add_char(ch);
      do_chan.send_char("Welcome "+ch.Name()+"!! ^_^\r\n", ch);
      do_move.move_char(ch, ROOM_START);
      do_func.do_prompt(ch);
      for (short i = (short)(b.length-1);i>-1;i--) {b[i]=0;}
      while(read(b)) {
      	do_func.do_cmd(ch, new String(b).trim());
      	for (short i = (short)(b.length-1);i>-1;i--) {b[i]=0;}
      	do_func.do_prompt(ch);
      }
    }
    catch (IOException ex) {
    }
    finally {
    	close();
    }
System.out.println(getName()+" lost");
  }
  @Override
public synchronized boolean read(byte[] b) throws IOException {
  	byte[] x = new byte[1];
  	short i = 0;
  	while (true) {
      if (in.read(x)==-1) {close();return false;}
      if ((char)x[0]=='\b') {i=(short)((i>0)?(i-1):(0));}
      b[i]=x[0];
      i++;
      if (x[0]==10) {break;}//reached a newline char
      if (i==b.length) {do_chan.send_char("Line greater than "+b.length+". Trimed.",ch);break;}
			//write(b);
			//write(new byte[]{(byte)'\n'});
			//temp.setCharAt(i+1,toChar(b[i]));
			//write(temp.toString().getBytes());
    }
    return true;
  }
  @Override
public synchronized void write(byte[] b) {
    try {
      out.write(b);
    }
    catch (IOException ex) {
      if (!sock.isClosed()) {close();}
    }
  }
  @Override
public void close() {
System.out.println("closing "+getName());
  	do_chan.send_char("Goodbye. :(",ch);
  	do_func.rem_char(ch);
  	do_chan.info(ch.Name()+" has left.");
  	try {
  		in.close();
  	}
    catch (IOException ex) {}
  }/* / *
  private static char toChar(byte b) {
    return (char)(((b>>>8&1)*128)+(b&((byte)127)));
  }//*/
}