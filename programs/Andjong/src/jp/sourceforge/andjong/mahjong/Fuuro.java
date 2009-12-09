package jp.sourceforge.andjong.mahjong;

/**
 * 副露を管理する。
 * 
 * @author Yuji Urushibara
 * 
 */
public class Fuuro {
	/*
	 * 種別
	 */

	/** 種別 明順 */
	public static final int TYPE_MINSHUN = 0;
	/** 種別 明刻 */
	public static final int TYPE_MINKOU = 1;
	/** 種別 大明槓 */
	public static final int TYPE_DAIMINKAN = 2;
	/** 種別 加槓 */
	public static final int TYPE_KAKAN = 3;
	/** 種別 暗槓 */
	public static final int TYPE_ANKAN = 4;

	/** 種別 */
	private int m_type;

	/** 他家との関係 */
	private int m_relation;

	/** 構成牌 */
	private Hai m_hais[] = new Hai[Mahjong.MENTSU_HAI_MEMBERS_4];

	{
		for (int i = 0; i < m_hais.length; i++) {
			m_hais[i] = new Hai();
		}
	}

	/**
	 * 種別を設定する。
	 * 
	 * @param a_type
	 *            種別
	 */
	public void setType(int a_type) {
		this.m_type = a_type;
	}

	/**
	 * 種別を取得する。
	 * 
	 * @return 種別
	 */
	public int getType() {
		return m_type;
	}

	/**
	 * 他家との関係を設定する。
	 * 
	 * @param a_relation
	 *            他家との関係
	 */
	public void setRelation(int a_relation) {
		this.m_relation = a_relation;
	}

	/**
	 * 他家との関係を取得する。
	 * 
	 * @return 他家との関係
	 */
	public int getRelation() {
		return m_relation;
	}

	/**
	 * 構成牌を設定する。
	 * 
	 * @param m_hais
	 *            構成牌
	 */
	public void setHais(Hai m_hais[]) {
		this.m_hais = m_hais;
	}

	/**
	 * 構成牌を取得する。
	 * 
	 * @return 構成牌
	 */
	public Hai[] getHais() {
		return m_hais;
	}

	/**
	 * 副露をコピーする。
	 * 
	 * @param a_dest
	 *            コピー先の副露
	 * @param a_src
	 *            コピー元の副露
	 */
	public static void copy(Fuuro a_dest, Fuuro a_src) {
		a_dest.m_type = a_src.m_type;
		a_dest.m_relation = a_dest.m_relation;

		for (int i = 0; i < Mahjong.MENTSU_HAI_MEMBERS_4; i++) {
			Hai.copy(a_dest.m_hais[i], a_src.m_hais[i]);
		}
	}
}
