/* µCOMP:javac -classpath ".." %sµ
 *
 */

package Pmud.spell;

import java.util.TimerTask;

import Pmud.Mud;
import Pmud.character.Character;

/**Spell that affects a player.
 * <br>Created: 2004
 * @author Kenneth Pierce
 */
public abstract class Affect extends TimerTask {
	/** resistance to being forcibly removed(0-100) */
	protected final int dispellResist;
	/** The name of the spell */
	protected final String what;
	/** How long the spell affects the player in rounds */
	private int duration;//in 'rounds'
	/** Player who casted the affect */
	protected final Character src;
	/** Who the spell affects */
	protected final Character ch;
	/**
	 * @param nwhat
	 * @param nduration
	 * @param ndispellResist
	 * @param nsrc
	 * @param nch
	 */
	public Affect(String nwhat, int nduration, int ndispellResist, Character nsrc, Character nch) {
		dispellResist = ndispellResist;
		what = nwhat;
		duration = nduration;
		src = nsrc;
		ch = nch;
		applied();
		Mud.UPDATE.schedule(this, Mud.ROUND);
	}
	@Override
	public final void run() {
		if ((--duration)!=0) {
			tick();
			Mud.UPDATE.schedule(this, Mud.ROUND);
		} else {
			finalTick();
		}
	}
	public final int getDispellResist() {
		return dispellResist;
	}
	/** Called when the spell is applied. */
	protected abstract void applied();
	/** What the spell does each round. */
	protected abstract void tick();
	/** Called just before the spell is removed. */
	protected abstract void finalTick();
	/** Called when the spell is removed by another spell or item. */
	public abstract void dispelled();
}
