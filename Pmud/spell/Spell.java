/* µCOMP:javac -classpath ".." %sµ
 *
 */

package Pmud.spell;

import Pmud.character.Character;

/**Base class of all spells.
 * <br>Created: 2004
 * @author Kenneth Pierce
 */
public abstract class Spell {
	/** display name of the spell */
	private String name = "";
	/** formatted display for casting. */
	private String cast = "";
	/** success message */
	private String succ = "";
	/** fail message */
	private String fail = "";
	private byte max = 100;
	private byte cur = 0;

	public Spell() {}

	public void setName(String nname) {name = nname;}
	public void setCast(String ncast) {cast = ncast;}
	public void setSucc(String nsucc) {succ = nsucc;}
	public void setFail(String nfail) {fail = nfail;}
	public void setMax(byte nmax)			{max = nmax;}
	public void setCur(byte ncur)			{cur = ncur;}

	public String Name()	{return name;}
	public String Cast()	{return cast;}
	public String Succ()	{return succ;}
	public String Fail()	{return fail;}
	public byte		Max()		{return max;}
	public byte		Cur()		{return cur;}

	public abstract void Cast(byte lvl, Character vic, Character ch);
}