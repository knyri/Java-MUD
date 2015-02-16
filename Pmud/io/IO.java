/**
 * 
 */
package Pmud.io;

import java.io.IOException;

/**
 * <br>Created: Sep 26, 2010
 * @author Kenneth Pierce
 */
public interface IO {
	public boolean read(byte[] b) throws IOException;
	public void write(byte[] b);
	public void close();
}
