/**
 * 
 */
package Pmud.event;

import Pmud.object.Obj;

/**
 * <br>Created: Sep 28, 2010
 * @author Kenneth Pierce
 */
public final class ItemEvent {
	public static enum Event {PICKUP, DROP, WEAR, REMOVE, BREAK, REPAIR, OPEN};
	private final Event event;
	private final Obj object;
	private final Pmud.character.Character ch;
	private final Pmud.character.Character tar;
	/**Item event.
	 * @param event What happened.
	 * @param source Where it originated.
	 * @param performer Who did it.
	 * @param target The target of any actions.
	 */
	public ItemEvent(Event event, Obj source, Pmud.character.Character performer, Pmud.character.Character target) {
		this.event = event;
		object = source;
		ch = performer;
		tar = target;
	}
	/**Same as {@link #ItemEvent(Event, Obj, Pmud.character.Character, Pmud.character.Character)}, but the performer is also the target.
	 * @param event
	 * @param source
	 * @param performer
	 */
	public ItemEvent(Event event, Obj source, Pmud.character.Character performer) {
		this(event, source, performer, performer);
	}
	/** What happened.
	 * @return the Event.
	 */
	public final Event getEvent() {
		return event;
	}
	/** Where it originated.
	 * @return the source object.
	 */
	public final Obj getSource() {
		return object;
	}
	/**The person who did the action.
	 * @return
	 */
	public final Pmud.character.Character getPerformer() {
		return ch;
	}
	/**Target of the triggered effect.
	 * @return
	 */
	public final Pmud.character.Character getTarget() {
		return tar;
	}
}
