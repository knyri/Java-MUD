/**
 * 
 */
package Pmud.skills;

import Pmud.character.Player;

/**
 * <br>Created: Oct 12, 2010
 * @author Kenneth Pierce
 */
public abstract class Skill {
	private int tier = 0;
	private int level = 0;
	private final String name;
	public Skill(String name, int tier, int level) {
		this.name = name;
		this.tier = tier;
		this.level = level;
	}
	public Skill(String data) {
		if (data==null) throw new NullPointerException("Data is null!");
		if (!data.startsWith("Pmud.Skill")) throw new IllegalArgumentException("Data is not a Skill class.");
		int end = data.indexOf(';', 10);
		if (end<0) throw new IllegalArgumentException("Data is in the wrong format.");
		name = data.substring(12, end);
		int start = end+1;
		end = data.indexOf(';',start);
		tier = Integer.parseInt(data.substring(start, end));
		start = end+1;
		end = data.indexOf(';',start);
		level = Integer.parseInt(data.substring(start,end));
	}
	public final String toString() {
		return "Pmud.Skill{"+name+";"+tier+";"+level+"}";
	}
	public final int getTier() { return tier; }
	public final int getLevel() { return level; }
	public final String getName() { return name; }
	public abstract void incrementTier(Player pl);
	public abstract void incrementLevel(Player pl);
}
