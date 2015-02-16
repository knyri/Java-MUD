/**
 * 
 */
package Pmud.io;

/**
 * <br>Created: Sep 26, 2010
 * @author Kenneth Pierce
 */
public class NullConnection implements IO {

	/* (non-Javadoc)
	 * @see Pmud.IO#close()
	 */
	public void close() {}

	/* (non-Javadoc)
	 * @see Pmud.IO#read(byte[])
	 */
	public boolean read(byte[] b) {return false;}

	/* (non-Javadoc)
	 * @see Pmud.IO#write(byte[])
	 */
	public void write(byte[] b) {}
}
