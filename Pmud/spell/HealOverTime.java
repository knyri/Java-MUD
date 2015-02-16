/**
 * 
 */
package Pmud.spell;

import Pmud.do_chan;
import Pmud.character.Character;
import Pmud.character.Player;

/**
 * <br>Created: Sep 24, 2010
 * @author Kenneth Pierce
 */
public class HealOverTime extends Affect {
	/** Maximum heal on the last tick. (amount == 100) */
	private final int endBloom;
	/** Maximum heal when applied. (amount == 100) */
	private final int startBloom;
	/**
	 * @param nwhat
	 * @param namount
	 * @param nduration
	 * @param ndispellResist
	 * @param nch
	 */
	public HealOverTime(String nwhat, int namount, int nduration,
			int ndispellResist, Character nsrc, Character nch) {
		super(nwhat, nduration, ndispellResist, nsrc, nch);
		endBloom = 0;
		startBloom = 0;
	}

	/* (non-Javadoc)
	 * @see Pmud.spell.Affect#applied()
	 */
	@Override
	protected void applied() {
		if (src == ch && src instanceof Player) {
			do_chan.send_char("", (Player)src);
		}
	}

	/* (non-Javadoc)
	 * @see Pmud.spell.Affect#dispelled()
	 */
	@Override
	public void dispelled() {
	}

	/* (non-Javadoc)
	 * @see Pmud.spell.Affect#finalTick()
	 */
	@Override
	protected void finalTick() {
	}

	/* (non-Javadoc)
	 * @see Pmud.spell.Affect#tick()
	 */
	@Override
	protected void tick() {
	}

}
