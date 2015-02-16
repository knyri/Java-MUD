package Pmud.constants;

public final class RoomExits{
	private RoomExits(){}
	public final static byte EXIT_NORTH	= 0,
			EXIT_SOUTH	= 1,
			EXIT_EAST	= 2,
			EXIT_WEST	= 3,
			EXIT_NE		= 4,
			EXIT_SE		= 5,
			EXIT_SW		= 6,
			EXIT_NW		= 7,
			EXIT_UP		= 8,
			EXIT_DOWN	= 9,
			EXIT_MAX		= 15;
	/** exit displays
	 */
	public final static String[] EXIT_NAMES = new String[] {"north", "south", "east", "west", "northeast", "southeast", "southwest", "northwest", "up", "down",//10
										"somewhere", "somewhere", "somewhere", "somewhere", "somewhere", "somewhere"};//6
}
