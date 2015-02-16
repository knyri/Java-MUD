package Pmud.constants;

public final class Affects{
	public final static int AFF_ANY = ~0;

	private Affects(){}
	public static final class Char{
		private Char(){}
		/**
		 * Character effects
		 * <dl>
		 * <dt>blind</dt><dd>can't see anything(room desc, content, exits, nothing)</dd>
		 * <dt>curse</dt><dd>cannot summon or use anything granted by their deity.</dd>
		 * <dt>hide</dt><dd>need detect hidden to be seen</dd>
		 * <dt>sneak</dt><dd>no enter/exit announcements</dd>
		 * <dt>invis</dt><dd>need detect invis to be seen</dd>
		 * <dt>detect hidden</dt><dd>can see hidden players</dd>
		 * <dt>detect invis</dt><dd>can see invis players</dd>
		 * <dt>true sight</dt><dd>can see everything, no matter what</dd>
		 * <dt>fly</dt><dd>travel across water and air. no falling damage. can travel up and down without climbing. MV usage dropped to 1</dd>
		 * <dt>float</dt><dd>travel across water. no falling damage. can travel down without climbing. MV usage dropped to 1.</dd>
		 * <dt>sanctuary</dt><dd>incoming physical damage halved</dd>
		 * <dt>protection</dt><dd>incoming physical damage halved if source is evil(player or weapon)</dd>
		 * <dt>detect traps</dt><dd>can see traps</dd>
		 * </dl>
		 */
		public final static byte AFF_BLIND	= Bits.B1,//just that, can only see if aff_true
					AFF_CURSE		= Bits.B2,//no supp recall/corpse/avatar
					AFF_HIDE		= Bits.B3,//need det_h to be seen
					AFF_SNEAK		= Bits.B4,//can't be heard entering/leaving room
					AFF_INVIS		= Bits.B5,//need det_i to be seen
					AFF_DET_H		= Bits.B6,//can see hide
					AFF_DET_I		= Bits.B7;//can see invis
		/**
		 * Player effects.<br>
		 * See {@link #AFF_BLIND} for a complete list.
		 */
		public final static short AFF_TRUE_SIGHT	= Bits.B8,//can see traps(traps not added yet)
				AFF_FLY			= Bits.B9,
				AFF_FLOAT		= Bits.B10,
				AFF_SANC		= Bits.B11,
				AFF_PROT		= Bits.B12,
				AFF_DET_T		= Bits.B13;

	}
	public static final class Room{

		private Room(){}

		/**
		 * room effects
		 * <dl>
		 * <dt>death</dt><dd>instant death</dd>
		 * <dt>dark</dt><dd>always dark</dd>
		 * <dt>light</dt><dd>always light</dd>
		 * <dt>silent</dt><dd>no chat</dd>
		 * <dt>safe</dt><dd>no aggressive acts</dd>
		 * <dt>nopk</dt><dd>no player vs player</dd>
		 * <dt>allpk</dt><dd>all players can fight other players</dd>
		 * <dt>clan</dt><dd>no non-player killers can enter</dd>
		 * <dt>guild</dt><dd>no player killers can enter</dd>
		 * <dt>no floor</dt><dd>anyone not flying will fall through(floaters suffer no falling damage)</dd>
		 * </dl>
		 */
		public final static byte AFF_DEATH	= Bits.B1,//instant death
					AFF_DARK		= Bits.B2,//always need a light to see
					AFF_LIGHT		= Bits.B3,//can always see
					AFF_SILENT		= Bits.B4,//no say
					AFF_SAFE		= Bits.B5,//no fighting at all
					AFF_NOPK		= Bits.B6,//no player vs player
					AFF_ALLPK		= Bits.B7;//arena
		/**
		 * room effects.<br>
		 * See {@link #AFF_DEATH} for complete list.
		 */
		public final static short AFF_CLAN	= Bits.B8,//no NPK can enter
					AFF_GUILD		= Bits.B9,//no PK can enter
					AFF_NOFLOOR		= Bits.B10;
		/*item and non-fly char/mob fall through a down exit.
		 *if no down exit then to limbo
		 */
	}
	public static class Object {
		private Object(){}
		/**Object effects.
		 * <dl>
		 * <dt>evil</dt><dd>dmg bonus for evil wielder</dd>
		 * <dt>bless</dt><dd>dmg bonus for good wielder</dd>
		 * <dt>magic</dt><dd>20% less dmg to obj, doesn't fall in no floor rooms</dd>
		 * <dt>metal</dt><dd>10% less dmg to obj</dd>
		 * <dt>organic</dt><dd>10% more dmg to obj</dd>
		 * <dt>synthetic</dt><dd>50% less dmg to obj</dd>
		 * <dt>loyal</dt><dd>disarms into sheath</dd>
		 * </dl>
		 */
		public final static byte AFF_EVIL	= Bits.B1,//dmg bonus for evil wielder
					AFF_BLESS		= Bits.B2,//dmg bonus for good wielder
					AFF_MAGIC		= Bits.B3,//20% less dmg to obj, doesn't fall in no floor rooms
					AFF_METAL		= Bits.B4,//10% less dmg to obj
					AFF_ORGANIC		= Bits.B5,//10% more dmg to obj
					AFF_SYNTH		= Bits.B6,//50% less dmg to obj
					AFF_LOYAL		= Bits.B7;//disarms into sheath
	}

}
