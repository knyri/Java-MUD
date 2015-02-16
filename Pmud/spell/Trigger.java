/**
 * 
 */
package Pmud.spell;

import Pmud.Mud;
import Pmud.character.Character;

/**
 * <br>Created: Sep 26, 2010
 * @author Kenneth Pierce
 */
public class Trigger {
	private final Spell spell;
	private final byte lvl;
	public Trigger(Spell spell, byte lvl) {
		this.spell = spell;
		this.lvl = lvl; 
	}
	public void fire(Character target) {
		spell.Cast(lvl, target, Mud.FLUFFY);
	}
}
