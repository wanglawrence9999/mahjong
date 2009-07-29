package jp.sourceforge.andjong;

/**
 * Consoleを実装するクラスです。
 * 
 * @author Yuji Urushibara
 * 
 */
public class Console implements EventIF {
	/** InfoUIオブジェクト */
	private InfoUI infoUi;

	/** 手牌 */
	private Tehai tehai = new Tehai();

	/** 河 */
	private Kawa kawa = new Kawa();

	/** 捨牌のインデックス */
	private int sutehaiIdx = 0;

	private String name;

	public String getName() {
		return name;
	}

	/**
	 * UIを初期化します。
	 * 
	 * @param infoUi
	 *            InfoUIオブジェクト
	 */
	public Console(InfoUI infoUi, String name) {
		this.infoUi = infoUi;
		this.name = name;
	}

	private void dispInfo() {
		System.out.println("-------- INFO --------");
		int kyoku = infoUi.getkyoku();
		switch (kyoku) {
		case Game.KYOKU_TON_1:
			System.out.print("[東一局]");
			break;
		case Game.KYOKU_TON_2:
			System.out.print("[東二局]");
			break;
		case Game.KYOKU_TON_3:
			System.out.print("[東三局]");
			break;
		case Game.KYOKU_TON_4:
			System.out.print("[東四局]");
			break;
		}
		System.out.print("[" + infoUi.getHonba() + "本場]");
		System.out.println("[残り:" + infoUi.getTsumoRemain() + "]");
		// ドラ表示牌を表示します。
		Hai[] doras = infoUi.getDoras();
		System.out.print("[ドラ表示牌]");
		for (Hai hai : doras) {
			System.out.print("[" + idToString(hai.getOldId()) + "]");
		}
		System.out.println();

		// 名前などを表示してみる。
		System.out.println("[" + jikazeToString(0) + "][" + infoUi.getName(0)
				+ "][" + infoUi.getTenbou(0) + "]");
		System.out.println("[" + jikazeToString(1) + "][" + infoUi.getName(1)
				+ "][" + infoUi.getTenbou(1) + "]");
		System.out.println("[" + jikazeToString(2) + "][" + infoUi.getName(2)
				+ "][" + infoUi.getTenbou(2) + "]");
		System.out.println("[" + jikazeToString(3) + "][" + infoUi.getName(3)
				+ "][" + infoUi.getTenbou(3) + "]");

		System.out.println("----------------------");
	}

	/**
	 * イベントを処理します。
	 * 
	 * @param fromKaze
	 *            イベントを発行した家
	 * @param toKaze
	 *            イベントの対象の家
	 * @param eid
	 *            イベントID
	 */
	public EID event(EID eid, int fromKaze, int toKaze) {
		switch (eid) {
		case BASHOGIME:// 場所決め
			// 表示することはない。
			break;
		case OYAGIME:// 親決め
			// サイ振りを表示します。
			// Sai[] sai = infoUi.getSais();
			// System.out.println("[親決め][" + sai[0].getNo() + "]["
			// + sai[1].getNo() + "]");
			break;
		case SENPAI:// 洗牌
			break;
		case SAIFURI:// サイ振り
			dispInfo();
			break;
		case RYUUKYOKU:// 流局
			System.out.println("[流局]");
			break;
		case NAGASHI:// 流し
			// 表示することはない。
			break;
		case TSUMO:// ツモ
			System.out
					.print("[" + jikazeToString(infoUi.getJikaze()) + "][ツモ]");

			// 手牌を表示します。
			printJyunTehai(tehai);

			// ツモ牌を表示します。
			System.out.println(":"
					+ idToString((infoUi.getTsumoHai()).getOldId()));
			break;
		case TSUMOAGARI:// ツモあがり
			System.out.print("[" + jikazeToString(infoUi.getJikaze())
					+ "][ツモあがり]");

			// 手牌を表示します。
			printJyunTehai(tehai);

			// ツモ牌を表示します。
			System.out.println(":"
					+ idToString((infoUi.getTsumoHai()).getOldId()));
			break;
		case SUTEHAI:// 捨牌
			// 自分の捨牌のみを表示します。
			if (fromKaze == infoUi.getJikaze()) {
				System.out.print("[" + jikazeToString(infoUi.getJikaze())
						+ "][捨牌]");

				// 河を表示します。
				printKawa(kawa);

				// 捨牌を表示します。
				// System.out.println(":"
				// + idToString((infoUi.getSuteHai()).getId()));
				System.out.println();
			}
			break;
		case PON:// ポン
			// 自分の捨牌のみを表示します。
			if (fromKaze == infoUi.getJikaze()) {
				System.out.print("[" + jikazeToString(infoUi.getJikaze())
						+ "][ポン]");

				// 手牌を表示します。
				printJyunTehai(tehai);

				System.out.println();

				System.out.print("[" + jikazeToString(infoUi.getJikaze())
						+ "][捨牌]");

				// 河を表示します。
				printKawa(kawa);

				// 捨牌を表示します。
				// System.out.println(":"
				// + idToString((infoUi.getSuteHai()).getId()));
				System.out.println();
			}
			break;
		case REACH:
			// 自分の捨牌のみを表示します。
			if (fromKaze == infoUi.getJikaze()) {
				System.out.print("[" + jikazeToString(infoUi.getJikaze())
						+ "][リーチ]");

				// 河を表示します。
				printKawa(kawa);

				// 捨牌を表示します。
				// System.out.println(":"
				// + idToString((infoUi.getSuteHai()).getId()));
				System.out.println();
			}
			break;
		case RON:// ロン
			System.out
					.print("[" + jikazeToString(infoUi.getJikaze()) + "][ロン]");

			// 手牌を表示します。
			printJyunTehai(tehai);

			// 当たり牌を表示します。
			System.out.println(":"
					+ idToString((infoUi.getSuteHai()).getOldId()));
			break;
		default:
			break;
		}

		return EID.NAGASHI;
	}

	public int getSutehaiIdx() {
		return sutehaiIdx;
	}

	/**
	 * 手牌を表示します。
	 * <p>
	 * TODO 鳴き牌を表示すること。
	 * </p>
	 * 
	 * @param tehai
	 *            手牌
	 */
	private void printJyunTehai(Tehai tehai) {
		infoUi.copyTehai(tehai, infoUi.getJikaze());
		Hai[] jyunTehai = tehai.getJyunTehai();
		int jyunTehaiLength = tehai.getJyunTehaiLength();
		for (int i = 0; i < jyunTehaiLength; i++)
			System.out.print(idToString(jyunTehai[i].getOldId()));

		int minkousLength = tehai.getMinkousLength();
		Hai[][] minkous = tehai.getMinkous();
		for (int i = 0; i < minkousLength; i++) {
			System.out.print("[");
			System.out.print(idToString(minkous[i][0].getOldId()));
			System.out.print(idToString(minkous[i][1].getOldId()));
			System.out.print(idToString(minkous[i][2].getOldId()));
			System.out.print("]");
		}
	}

	/**
	 * 河を表示します。
	 * 
	 * @param kawa
	 *            河
	 */
	private void printKawa(Kawa kawa) {
		infoUi.copyKawa(kawa, infoUi.getJikaze());
		SuteHai[] SuteHai = kawa.getSuteHais();
		int kawaLength = kawa.getSuteHaiLength();
		for (int i = 0; i < kawaLength; i++)
			System.out.print(idToString(SuteHai[i].getOldId()));
	}

	/**
	 * 牌番号を文字列に変換します
	 * 
	 * @param id
	 *            牌番号
	 * @return 文字列
	 */
	static public String idToString(int id) {
		int kind = id & (Hai.OLD_KIND_SHUU | Hai.OLD_KIND_TSUU);
		id &= ~(Hai.OLD_KIND_SHUU | Hai.OLD_KIND_TSUU);

		switch (kind) {
		case Hai.OLD_KIND_WAN:
			switch (id) {
			case 1:
				return "一";
			case 2:
				return "二";
			case 3:
				return "三";
			case 4:
				return "四";
			case 5:
				return "五";
			case 6:
				return "六";
			case 7:
				return "七";
			case 8:
				return "八";
			case 9:
				return "九";
			}
			break;
		case Hai.OLD_KIND_PIN:
			switch (id) {
			case 1:
				return "①";
			case 2:
				return "②";
			case 3:
				return "③";
			case 4:
				return "④";
			case 5:
				return "⑤";
			case 6:
				return "⑥";
			case 7:
				return "⑦";
			case 8:
				return "⑧";
			case 9:
				return "⑨";
			}
			break;
		case Hai.OLD_KIND_SOU:
			switch (id) {
			case 1:
				return "１";
			case 2:
				return "２";
			case 3:
				return "３";
			case 4:
				return "４";
			case 5:
				return "５";
			case 6:
				return "６";
			case 7:
				return "７";
			case 8:
				return "８";
			case 9:
				return "９";
			}
			break;
		case Hai.OLD_KIND_FON:
			switch (id) {
			case 1:
				return "東";
			case 2:
				return "南";
			case 3:
				return "西";
			case 4:
				return "北";
			}
			break;
		case Hai.OLD_KIND_SANGEN:
			switch (id) {
			case 1:
				return "白";
			case 2:
				return "發";
			case 3:
				return "中";
			}
			break;
		}

		return null;
	}

	/**
	 * 自風を文字列に変換します。
	 * 
	 * @param jikaze
	 *            自風
	 * @return　文字列
	 */
	static public String jikazeToString(int jikaze) {
		switch (jikaze) {
		case 0:
			return "東";
		case 1:
			return "南";
		case 2:
			return "西";
		case 3:
			return "北";
		}

		return null;
	}
}
