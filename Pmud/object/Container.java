/* µCOMP:javac -classpath ".." %sµ
 *
 */

package Pmud.object;


/**Base class for containers in the world. Chests, bags, cabinets, ect.
 * <br>Created: 2004
 * @author Kenneth Pierce
 */
public class Container extends Obj {
	private Obj[] slots = new Obj[50];
	private static final byte max_weight = 5;

	public Container() {}

	public Obj getObj(String name) {
		for (int i = 0;i<slots.length;i++) {
			if (slots[i]!=null) {
				if (slots[i].equals(name)) {return slots[i];}
			} else {return null;}
		}
		return null;
	}
	public boolean addObj(Obj add)	{
		for (int i = 0;i<slots.length;i++) {
			if (slots[i]==null) {
				if (getV(max_weight)>(add.Weight()+Weight())) {
					slots[i] = add;
					setV(3, Weight()+add.Weight());
					return true;
				} else {return false;}
			}
		}
		return false;
	}
	public void remObj(Obj rem)	{
		for (int i = 0;i<slots.length;i++) {
			if (slots[i]!=null) {
				if (slots[i]==rem) {
					slots[i] = null;
					System.arraycopy(slots, i+1, slots, i, (slots.length-1)-i);
					return;
				}
			} else {return;}
		}
	}
}