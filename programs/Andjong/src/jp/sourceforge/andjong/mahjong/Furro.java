package jp.sourceforge.andjong.mahjong;

/**
 * •›˜I‚ğŠÇ—‚·‚éB
 *
 * @author Yuji Urushibara
 *
 */
public class Furro {
	/** í•Ê –¾‡ */
	public static int TYPE_MINSHUN = 0;
	/** í•Ê –¾ */
	public static int TYPE_MINKOU = 1;
	/** í•Ê ‘å–¾È */
	public static int TYPE_DAIMINKAN = 2;
	/** í•Ê ‰ÁÈ */
	public static int TYPE_KAKAN = 3;
	/** í•Ê ˆÃÈ */
	public static int TYPE_ANKAN = 4;
	/** í•Ê */
	private int type;

	/**
	 * í•Ê‚ğİ’è‚·‚éB
	 *
	 * @param type
	 *            í•Ê
	 */
	public void setType(
			int type) {
		this.type = type;
	}

	/**
	 * í•Ê‚ğæ“¾‚·‚éB
	 *
	 * @return í•Ê
	 */
	public int getType() {
		return type;
	}

	/** \¬”v */
	private Hai hai[] = new Hai[Mahjong.MENTSU_HAI_MEMBERS_4];

	/**
	 * \¬”v‚ğİ’è‚·‚éB
	 *
	 * @param hai
	 *            \¬”v
	 */
	public void setHai(
			Hai hai[]) {
		this.hai = hai;
	}

	/**
	 * \¬”v‚ğæ“¾‚·‚éB
	 *
	 * @return \¬”v
	 */
	public Hai[] getHai() {
		return hai;
	}

	/** ‘¼‰Æ‚Æ‚ÌŠÖŒW */
	private int relation;

	/**
	 * ‘¼‰Æ‚Æ‚ÌŠÖŒW‚ğİ’è‚·‚éB
	 *
	 * @param relation
	 *            ‘¼‰Æ‚Æ‚ÌŠÖŒW
	 */
	public void setRelation(
			int relation) {
		this.relation = relation;
	}

	/**
	 * ‘¼‰Æ‚Æ‚ÌŠÖŒW‚ğæ“¾‚·‚éB
	 *
	 * @return ‘¼‰Æ‚Æ‚ÌŠÖŒW
	 */
	public int getRelation() {
		return relation;
	}
}
