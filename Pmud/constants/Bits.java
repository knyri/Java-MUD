package Pmud.constants;

public final class Bits{

	private Bits(){}
	public final static byte B1 = 1,
				B2 = 2,
				B3 = 4,
				B4 = 8,
				B5 = 16,
				B6 = 32,
				B7 = 64;
	public final static short B8 = 128,
				B9 = 1<<9,
				B10 = 1<<10,
				B11 = 1<<11,
				B12 = 1<<12,
				B13 = 1<<13,
				B14 = 1<<14;
	public final static int B15 = 1<<15,
				B16 = 1<<16,
				B17 = 1<<17,
				B18 = 1<<18,
				B19 = 1<<19,
				B20 = 1<<20,
				B21 = 1<<21,
				B22 = 1<<22,
				B23 = 1<<23,
				B24 = 1<<24,
				B25 = 1<<25,
				B26 = 1<<26,
				B27 = 1<<27,
				B28 = 1<<28,
				B29 = 1<<29,
				B30 = 1<<30,
				B31 = 1<<31,
				B32 = 1<<32;
	public static final byte set(byte options,byte mask){	return (byte)(options|mask);}
	public static final short set(short options,short mask){	return (short)(options|mask);}
	public static final int set(int options,int mask){	return (options|mask);}
	public static final long set(long options,long mask){	return (options|mask);}

	public static final byte unset(byte options,byte mask){	return (byte)(options&~mask);}
	public static final short unset(short options,short mask){	return (short)(options&~mask);}
	public static final long unset(long options,long mask){	return (options&~mask);}
	public static final int unset(int options,int mask){	return (options&~mask);}

	public static final byte toggle(byte options,byte mask){	return (byte)(options^mask);}
	public static final short toggle(short options,short mask){	return (short)(options^mask);}
	public static final int toggle(int options,int mask){	return (options^mask);}
	public static final long toggle(long options,long mask){	return (options^mask);}

	public static final boolean isSet(long options,long mask){	return (options&mask)==mask;}

	public static final long parse(String bits){
		long ret=0;
		for(int i=0; i<bits.length(); i++){
			if(bits.charAt(i)=='1')
				ret|= 0x8000000000000000L >>> i;
		}
		return ret;
	}
	public static final String toString(byte b){
		char c[] = new char[8];
		for(byte i=0;i<8;i++)
			c[7-i] = (((b >> i)& 0x1) == 1)?'1':'0';
		return new String(c);
	}
	public static final String toString(int b){
		char c[] = new char[32];
		for(byte i=0;i<32;i++)
			c[31-i] = (((b >> i)& 0x1) == 1)?'1':'0';
		return new String(c);
	}
	public static final String toString(short b){
		char c[] = new char[16];
		for(byte i=0;i<16;i++)
			c[15-i] = (((b >> i)& 0x1) == 1)?'1':'0';
		return new String(c);
	}
	public static final String toString(long b){
		char c[] = new char[64];
		for(byte i=0;i<64;i++)
			c[63-i] = (((b >> i)& 0x1) == 1)?'1':'0';
		return new String(c);
	}
}
