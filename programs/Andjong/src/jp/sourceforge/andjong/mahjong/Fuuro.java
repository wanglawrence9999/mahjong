package jp.sourceforge.andjong.mahjong;

/**
 * 副露を管理する。
 *
 * @author Yuji Urushibara
 *
 */
public class Fuuro {
	/** 種別 明順 */
	public static int TYPE_MINSHUN = 0;
	/** 種別 明刻 */
	public static int TYPE_MINKOU = 1;
	/** 種別 大明槓 */
	public static int TYPE_DAIMINKAN = 2;
	/** 種別 加槓 */
	public static int TYPE_KAKAN = 3;
	/** 種別 暗槓 */
	public static int TYPE_ANKAN = 4;
	/** 種別 */
	private int type;

	/**
	 * 種別を設定する。
	 *
	 * @param type
	 *            種別
	 */
	public void setType(
			int type) {
		this.type = type;
	}

	/**
	 * 種別を取得する。
	 *
	 * @return 種別
	 */
	public int getType() {
		return type;
	}

	/** 構成牌 */
	private Hai hais[] = new Hai[Mahjong.MENTSU_HAI_MEMBERS_4];

	{
		for (int i = 0; i < hais.length; i++) {
			hais[i] = new Hai();
		}
	}

	/**
	 * 構成牌を設定する。
	 *
	 * @param hais
	 *            構成牌
	 */
	public void setHai(
			Hai hais[]) {
		this.hais = hais;
	}

	/**
	 * 構成牌を取得する。
	 *
	 * @return 構成牌
	 */
	public Hai[] getHai() {
		return hais;
	}

	/** 他家との関係 */
	private int relation;

	/**
	 * 他家との関係を設定する。
	 *
	 * @param relation
	 *            他家との関係
	 */
	public void setRelation(
			int relation) {
		this.relation = relation;
	}

	/**
	 * 他家との関係を取得する。
	 *
	 * @return 他家との関係
	 */
	public int getRelation() {
		return relation;
	}

	/**
	 * 副露をコピーする。
	 *
	 * @param destFuuro
	 *            コピー先の副露
	 * @param srcFuuro
	 *            コピー元の副露
	 */
	public static void copy(
			Fuuro destFuuro,
			Fuuro srcFuuro) {
		destFuuro.type = srcFuuro.type;

		for (int i = 0; i < Mahjong.MENTSU_HAI_MEMBERS_4; i++) {
			Hai.copy(destFuuro.hais[i], srcFuuro.hais[i]);
		}

		destFuuro.relation = destFuuro.relation;
	}
}
