/* µCOMP:javac -classpath ".." %sµ
 * µEXEC:cmd /K java -classpath .. Pmud.testµ
 */

package Pmud;

public class test {
	public static String[] t = new String[1];
	//public test() {System.out.println(rooms[0].toString());}
	public static void main(String[] arg) {
		/*for (int i = 0;i<100;i++) {
			int x = (int)Math.round(Math.random()*100);
			System.out.print(x+((x<10)?"   ":((x<100)?"  ":" ")));
			if ((i%20)==0) {System.out.println();}
		}
		System.out.println();
		System.out.println(do_func.NaN("n3"));
		System.out.println((int)((byte)255));
		byte x = 1;
		byte y = 3;
		short res = 0;
		res = (byte)((x)^(y));//-1-15
		System.out.println("Res1: "+res);
		res = (byte)((-x)^(y));//15+(-1)
		System.out.println("Res2: "+res);
		res = (byte)((-x)^(-y));//-1-15
		System.out.println("Res3: "+res);
		res = (byte)((x)^(-y));//15+(-1)
		System.out.println("Res4: "+res);
		res = (byte)((x)&(-y));//15(-1)
		System.out.println("Res5: "+res);
		res = (byte)((x)&(y));//15(1)
		System.out.println("Res6: "+res);
		res = (byte)((-x)&(-y));//1
		System.out.println("Res7: "+res);
		res = (byte)((x)|(-y));//-1
		System.out.println("Res8: "+res);
		res = (byte)((x)|(y));//-1
		System.out.println("Res9: "+res);
		String[] te = new String[1];
		System.out.println(t[0]);
		System.out.println(te[0]);*/
		Bits x = new Bits(13);
		x.setBits("10000");
		System.out.println(x.toString(' '));
		System.out.println(NaN("0 0".split(" ")));
		System.out.println(NaN("0 a".split(" ")));
		System.out.println(NaN("a 0".split(" ")));
		System.out.println((float)(2.5*(60*1000)));
		System.out.println((2.5*(60*1000)));
	}
	public static boolean NaN(String[] num) {
			try {
				for (int i = 0;i<num.length;i++) {
					Integer.parseInt(num[i]);
				}
				return false;
			} catch (NumberFormatException ex) {return true;}
	}
}
