/**
 * 
 */
package Pmud.object.equipment.weapon;

import Pmud.event.ItemEvent;
import Pmud.event.ItemListener;
import Pmud.object.equipment.Weapon;
import Pmud.spell.Spell;

/**
 * <br>Created: Sep 28, 2010
 * @author Kenneth Pierce
 */
public class Enchant implements ItemListener {
	private final Spell spell;
	private final int lvl;
	private final ItemEvent.Event trigger;
	public Enchant(Spell spell, Weapon weap, ItemEvent.Event trigger) {
		this.spell = spell;
		lvl = weap.Level();
		this.trigger = trigger;
	}
	/* (non-Javadoc)
	 * @see Pmud.event.ItemListener#itemEvent(Pmud.event.ItemEvent)
	 */
	public void itemEvent(ItemEvent e) {
		if (e.getEvent() == trigger) {
			
		}
	}
	
}
