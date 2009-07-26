package jp.sourceforge.andjong;

/**
 * ”v‚πΗ—‚·‚ιƒNƒ‰ƒX
 * 
 * @author Yuji Urushibara
 * 
 */
public class Hai {
	/** ID */
	private int id;

	/** κδέ */
	public static final int ID_WAN_1 = 0;
	/** “ρδέ */
	public static final int ID_WAN_2 = 1;
	/** Oδέ */
	public static final int ID_WAN_3 = 2;
	/** lδέ */
	public static final int ID_WAN_4 = 3;
	/** άδέ */
	public static final int ID_WAN_5 = 4;
	/** Zδέ */
	public static final int ID_WAN_6 = 5;
	/** µδέ */
	public static final int ID_WAN_7 = 6;
	/** ”ªδέ */
	public static final int ID_WAN_8 = 7;
	/** ‹γδέ */
	public static final int ID_WAN_9 = 8;

	/** κ“› */
	public static final int ID_PIN_1 = 9;
	/** “ρ“› */
	public static final int ID_PIN_2 = 10;
	/** O“› */
	public static final int ID_PIN_3 = 11;
	/** l“› */
	public static final int ID_PIN_4 = 12;
	/** ά“› */
	public static final int ID_PIN_5 = 13;
	/** Z“› */
	public static final int ID_PIN_6 = 14;
	/** µ“› */
	public static final int ID_PIN_7 = 15;
	/** ”ª“› */
	public static final int ID_PIN_8 = 16;
	/** ‹γ“› */
	public static final int ID_PIN_9 = 17;

	/** κυ */
	public static final int ID_SOU_1 = 18;
	/** “ρυ */
	public static final int ID_SOU_2 = 19;
	/** Oυ */
	public static final int ID_SOU_3 = 20;
	/** lυ */
	public static final int ID_SOU_4 = 21;
	/** άυ */
	public static final int ID_SOU_5 = 22;
	/** Zυ */
	public static final int ID_SOU_6 = 23;
	/** µυ */
	public static final int ID_SOU_7 = 24;
	/** ”ªυ */
	public static final int ID_SOU_8 = 25;
	/** ‹γυ */
	public static final int ID_SOU_9 = 26;

	/** “ */
	public static final int ID_TON = 27;
	/** “μ */
	public static final int ID_NAN = 28;
	/** Ό */
	public static final int ID_SHA = 29;
	/** –k */
	public static final int ID_PE = 30;

	/** ”’ */
	public static final int ID_HAKU = 31;
	/** αΆ */
	public static final int ID_HATSU = 32;
	/** ’† */
	public static final int ID_CYUN = 33;

	/** ”Τ† */
	private int no;

	/** κ */
	public static final int NO_1 = 1;
	/** “ρ */
	public static final int NO_2 = 2;
	/** O */
	public static final int NO_3 = 3;
	/** l */
	public static final int NO_4 = 4;
	/** ά */
	public static final int NO_5 = 5;
	/** Z */
	public static final int NO_6 = 6;
	/** µ */
	public static final int NO_7 = 7;
	/** ”ª */
	public static final int NO_8 = 8;
	/** ‹γ */
	public static final int NO_9 = 9;

	/** κδέ */
	public static final int NO_WAN_1 = 1;
	/** “ρδέ */
	public static final int NO_WAN_2 = 2;
	/** Oδέ */
	public static final int NO_WAN_3 = 3;
	/** lδέ */
	public static final int NO_WAN_4 = 4;
	/** άδέ */
	public static final int NO_WAN_5 = 5;
	/** Zδέ */
	public static final int NO_WAN_6 = 6;
	/** µδέ */
	public static final int NO_WAN_7 = 7;
	/** ”ªδέ */
	public static final int NO_WAN_8 = 8;
	/** ‹γδέ */
	public static final int NO_WAN_9 = 9;

	/** κ“› */
	public static final int NO_PIN_1 = 1;
	/** “ρ“› */
	public static final int NO_PIN_2 = 2;
	/** O“› */
	public static final int NO_PIN_3 = 3;
	/** l“› */
	public static final int NO_PIN_4 = 4;
	/** ά“› */
	public static final int NO_PIN_5 = 5;
	/** Z“› */
	public static final int NO_PIN_6 = 6;
	/** µ“› */
	public static final int NO_PIN_7 = 7;
	/** ”ª“› */
	public static final int NO_PIN_8 = 8;
	/** ‹γ“› */
	public static final int NO_PIN_9 = 9;

	/** κυ */
	public static final int NO_SOU_1 = 1;
	/** “ρυ */
	public static final int NO_SOU_2 = 2;
	/** Oυ */
	public static final int NO_SOU_3 = 3;
	/** lυ */
	public static final int NO_SOU_4 = 4;
	/** άυ */
	public static final int NO_SOU_5 = 5;
	/** Zυ */
	public static final int NO_SOU_6 = 6;
	/** µυ */
	public static final int NO_SOU_7 = 7;
	/** ”ªυ */
	public static final int NO_SOU_8 = 8;
	/** ‹γυ */
	public static final int NO_SOU_9 = 9;

	/** “ */
	public static final int NO_TON = 1;
	/** “μ */
	public static final int NO_NAN = 2;
	/** Ό */
	public static final int NO_SHA = 3;
	/** –k */
	public static final int NO_PE = 4;

	/** ”’ */
	public static final int NO_HAKU = 1;
	/** αΆ */
	public static final int NO_HATSU = 2;
	/** ’† */
	public static final int NO_CYUN = 3;

	/** ν—ή */
	private int kind;

	/** δέq */
	public static final int KIND_WAN = 0;
	/** “›q */
	public static final int KIND_PIN = 1;
	/** υq */
	public static final int KIND_SOU = 2;
	/** •—”v */
	public static final int KIND_FON = 3;
	/** O³”v */
	public static final int KIND_SANGEN = 4;

	/** ”vƒtƒ‰ƒO */
	private boolean tsuu;

	/**
	 * ‹σ‚Μ”v‚πμ¬‚·‚ιB
	 */
	public Hai() {

	}

	/**
	 * ”Τ†‚©‚η”v‚πμ¬‚·‚ιB
	 * 
	 * @param id
	 *            ”Τ†
	 */
	public Hai(int id) {
		this.id = id;

		if (id > ID_PE) {
			this.no = id - ID_PE;
			this.kind = KIND_SANGEN;
			this.tsuu = true;

			this.oldId = this.no | OLD_KIND_SANGEN;
		} else if (id > ID_SOU_9) {
			this.no = id - ID_SOU_9;
			this.kind = KIND_FON;
			this.tsuu = true;

			this.oldId = this.no | OLD_KIND_FON;
		} else if (id > ID_PIN_9) {
			this.no = id - ID_PIN_9;
			this.kind = KIND_SOU;
			this.tsuu = false;

			this.oldId = this.no | OLD_KIND_SOU;
		} else if (id > ID_WAN_9) {
			this.no = id - ID_WAN_9;
			this.kind = KIND_PIN;
			this.tsuu = false;

			this.oldId = this.no | OLD_KIND_PIN;
		} else {
			this.no = id + 1;
			this.kind = KIND_WAN;
			this.tsuu = false;

			this.oldId = this.no | OLD_KIND_WAN;
		}
	}

	/**
	 * ”v‚©‚η”v‚πμ¬‚·‚ιB
	 * 
	 * @param hai
	 *            ”v
	 */
	public Hai(Hai hai) {
		this.id = hai.id;
		this.no = hai.no;
		this.kind = hai.kind;
		this.tsuu = hai.tsuu;

		this.oldId = hai.oldId;
	}

	/**
	 * ”v‚πƒRƒs[‚·‚ιB
	 * 
	 * @param hai
	 *            ”v
	 */
	public void copy(Hai hai) {
		this.id = hai.id;
		this.no = hai.no;
		this.kind = hai.kind;
		this.tsuu = hai.tsuu;

		this.oldId = hai.oldId;
	}

	/**
	 * ID‚πζ“Ύ‚·‚ιB
	 * 
	 * @return ID
	 */
	public int getId() {
		return id;
	}

	/**
	 * ”Τ†‚πζ“Ύ‚·‚ιB
	 * 
	 * @return ”Τ†
	 */
	public int getNo() {
		return no;
	}

	/**
	 * ν—ή‚πζ“Ύ‚·‚ιB
	 * 
	 * @return ν—ή
	 */
	public int getKind() {
		return kind;
	}

	/**
	 * ”vƒtƒ‰ƒO‚πζ“Ύ‚·‚ιB
	 * 
	 * @return ”vƒtƒ‰ƒO
	 */
	public boolean isTsuu() {
		return tsuu;
	}

	/** δέq */
	public final static int OLD_KIND_WAN = 0x00000010;
	/** “›q */
	public final static int OLD_KIND_PIN = 0x00000020;
	/** υq */
	public final static int OLD_KIND_SOU = 0x00000040;
	/** ””v */
	public final static int OLD_KIND_SHUU = 0x00000070;

	/** •—”v */
	public final static int OLD_KIND_FON = 0x00000100;

	/** “ */
	public final static int OLD_KIND_TON = 0x00000101;
	/** “μ */
	public final static int OLD_KIND_NAN = 0x00000102;
	/** Ό */
	public final static int OLD_KIND_SYA = 0x00000103;
	/** –k */
	public final static int OLD_KIND_PEE = 0x00000104;

	/** O³”v */
	public final static int OLD_KIND_SANGEN = 0x00000200;

	/** ”’ */
	public final static int OLD_KIND_HAKU = 0x00000201;
	/** ”­ */
	public final static int OLD_KIND_HATU = 0x00000202;
	/** ’† */
	public final static int OLD_KIND_CYUN = 0x00000203;

	/** ”v */
	public final static int OLD_KIND_TSUU = 0x00000300;

	/** ”v‚Μν—ή‚πƒ}ƒXƒN‚·‚ι */
	public final static int OLD_KIND_MASK = 0x0000000F;

	/**
	 * ”v”Τ†
	 * <p>
	 * <dl>
	 * <dt>δέq</dt>
	 * <dd>KIND_WAN | 1-9</dd>
	 * <dt>“›q</dt>
	 * <dd>KIND_PIN | 1-9</dd>
	 * <dt>υq</dt>
	 * <dd>KIND_SOU | 1-9</dd>
	 * <dt>•—”v</dt>
	 * <dd>KIND_FON | 1-4</dd>
	 * <dt>O³”v</dt>
	 * <dd>KIND_SANGEN | 1-3</dd>
	 * </dl>
	 * </p>
	 */
	private int oldId;

	/**
	 * ”v”Τ†‚πζ“Ύ‚·‚ιB
	 * 
	 * @return ”v”Τ†
	 */
	public int getOldId() {
		return oldId;
	}
}
