package jp.sourceforge.andjong.mahjong;

/**
 * 手牌を管理する。
 *
 * @author Yuji Urushibara
 *
 */
public class Tehai {
	/** 純手牌の長さの最大値 */
	public final static int JYUN_TEHAI_LENGTH_MAX = 14;

	/** 副露の最大値 */
	public final static int FUURO_MAX = 4;

	/** 面子の長さ(3) */
	public final static int MENTSU_LENGTH_3 = 3;

	/** 面子の長さ(4) */
	public final static int MENTSU_LENGTH_4 = 4;

	/** 純手牌 */
	private Hai[] m_jyunTehai = new Hai[JYUN_TEHAI_LENGTH_MAX];

	/** 純手牌の長さ */
	private int m_jyunTehaiLength;

	/** 副露の配列 */
	private Fuuro[] m_fuuros = new Fuuro[FUURO_MAX];

	/** 副露の個数 */
	private int m_fuuroNums;

	{
		for (int i = 0; i < JYUN_TEHAI_LENGTH_MAX; i++) {
			m_jyunTehai[i] = new Hai();
		}

		for (int i = 0; i < FUURO_MAX; i++) {
			m_fuuros[i] = new Fuuro();
		}
	}

	/**
	 * 手牌を作成する。
	 */
	public Tehai() {
		initialize();
	}

	/**
	 * 手牌を初期化する。
	 */
	public void initialize() {
		m_jyunTehaiLength = 0;
		m_fuuroNums = 0;
	}

	/**
	 * 手牌をコピーする。
	 *
	 * @param a_dest
	 *            コピー先の手牌
	 * @param a_src
	 *            コピー元の手牌
	 * @param jyunTehaiCopy
	 *            純手牌のコピー許可
	 */
	public static void copy(Tehai a_dest, Tehai a_src, boolean jyunTehaiCopy) {
		if (jyunTehaiCopy == true) {
			a_dest.m_jyunTehaiLength = a_src.m_jyunTehaiLength;
			Tehai.copyJyunTehai(a_dest.m_jyunTehai, a_src.m_jyunTehai, a_dest.m_jyunTehaiLength);
		}

		a_dest.m_fuuroNums = a_src.m_fuuroNums;
		copyFuuros(a_dest.m_fuuros, a_src.m_fuuros, a_dest.m_fuuroNums);
	}

	/**
	 * 純手牌を取得する。
	 *
	 * @return 純手牌
	 */
	public Hai[] getJyunTehai() {
		return m_jyunTehai;
	}

	/**
	 * 純手牌の長さを取得する。
	 *
	 * @return 純手牌の長さ
	 */
	public int getJyunTehaiLength() {
		return m_jyunTehaiLength;
	}

	/**
	 * 純手牌に牌を追加する。
	 *
	 * @param a_hai
	 *            追加する牌
	 * @return 結果
	 */
	public boolean addJyunTehai(Hai a_hai) {
		if (m_jyunTehaiLength >= JYUN_TEHAI_LENGTH_MAX) {
			return false;
		}

		int i;
		for (i = m_jyunTehaiLength; i > 0; i--) {
			if (m_jyunTehai[i - 1].getId() <= a_hai.getId()) {
				break;
			}

			Hai.copy(m_jyunTehai[i], m_jyunTehai[i - 1]);
		}

		Hai.copy(m_jyunTehai[i], a_hai);
		m_jyunTehaiLength++;

		return true;
	}

	/**
	 * 純手牌から指定位置の牌を削除する。
	 *
	 * @param a_index
	 *            指定位置
	 * @return 結果
	 */
	public boolean rmJyunTehai(int a_index) {
		if (a_index >= JYUN_TEHAI_LENGTH_MAX) {
			return false;
		}

		for (int i = a_index; i < m_jyunTehaiLength - 1; i++) {
			Hai.copy(m_jyunTehai[i], m_jyunTehai[i + 1]);
		}

		m_jyunTehaiLength--;

		return true;
	}

	/**
	 * 純手牌から指定の牌を削除する。
	 *
	 * @param a_hai
	 *            指定の牌
	 * @return 結果
	 */
	public boolean rmJyunTehai(Hai a_hai) {
		int id = a_hai.getId();

		for (int i = 0; i < m_jyunTehaiLength; i++) {
			if (id == m_jyunTehai[i].getId()) {
				return rmJyunTehai(i);
			}
		}

		return false;
	}

	/**
	 * 純手牌をコピーする。
	 *
	 * @param a_dest
	 *            コピー先の純手牌
	 * @param a_src
	 *            コピー元の純手牌
	 * @param a_length
	 *            コピーする長さ
	 * @return 結果
	 */
	public static boolean copyJyunTehai(Hai[] a_dest, Hai[] a_src, int a_length) {
		if (a_length >= JYUN_TEHAI_LENGTH_MAX) {
			return false;
		}

		for (int i = 0; i < a_length; i++) {
			Hai.copy(a_dest[i], a_src[i]);
		}

		return true;
	}

	/**
	 * 純手牌の指定位置の牌をコピーする。
	 *
	 * @param a_hai
	 *            牌
	 * @param a_index
	 *            指定位置
	 */
	public boolean copyJyunTehaiIndex(Hai a_hai, int a_index) {
		if (a_index >= m_jyunTehaiLength) {
			return false;
		}

		Hai.copy(a_hai, m_jyunTehai[a_index]);

		return true;
	}

	/**
	 * チー(左)の可否をチェックする。
	 *
	 * @param a_suteHai
	 *            捨牌
	 * @return チー(左)の可否
	 */
	public boolean validChiiLeft(Hai a_suteHai, Hai[] a_sarashiHais) {
		if (a_suteHai.isTsuu()) {
			return false;
		}

		if (a_suteHai.getNo() == Hai.NO_8) {
			return false;
		}

		if (a_suteHai.getNo() == Hai.NO_9) {
			return false;
		}

		if (m_fuuroNums >= FUURO_MAX) {
			return false;
		}

		int noKindLeft = a_suteHai.getNoKind();
		int noKindCenter = noKindLeft + 1;
		int noKindRight = noKindLeft + 2;
		for (int i = 0; i < m_jyunTehaiLength; i++) {
			if (m_jyunTehai[i].getNoKind() == noKindCenter) {
				for (int j = i + 1; j < m_jyunTehaiLength; j++) {
					if (m_jyunTehai[j].getNoKind() == noKindRight) {
						a_sarashiHais[0] = new Hai(m_jyunTehai[i]);
						a_sarashiHais[1] = new Hai(m_jyunTehai[j]);
						return true;
					}
				}
			}
		}

		return false;
	}

	/**
	 * チー(左)を設定する。
	 *
	 * @param a_suteHai
	 *            捨牌
	 * @param a_relation
	 *            関係
	 * @return 結果
	 */
	public boolean setChiiLeft(Hai a_suteHai, int a_relation) {
		Hai sarashiHais[] = new Hai[2];
		if (!validChiiLeft(a_suteHai, sarashiHais)) {
			return false;
		}

		Hai hais[] = new Hai[Mahjong.MENTSU_HAI_MEMBERS_4];

		hais[0] = new Hai(a_suteHai);

		int noKindLeft = a_suteHai.getNoKind();
		int noKindCenter = noKindLeft + 1;
		int noKindRight = noKindLeft + 2;
		for (int i = 0; i < m_jyunTehaiLength; i++) {
			if (m_jyunTehai[i].getNoKind() == noKindCenter) {
				hais[1] = new Hai(m_jyunTehai[i]);

				rmJyunTehai(i);

				for (int j = i; j < m_jyunTehaiLength; j++) {
					if (m_jyunTehai[j].getNoKind() == noKindRight) {
						hais[2] = new Hai(m_jyunTehai[j]);

						rmJyunTehai(j);

						hais[3] = new Hai();

						m_fuuros[m_fuuroNums].setType(Fuuro.TYPE_MINSHUN);
						m_fuuros[m_fuuroNums].setRelation(a_relation);
						m_fuuros[m_fuuroNums].setHais(hais);
						m_fuuroNums++;
						return true;
					}
				}
			}
		}

		return false;
	}

	/**
	 * チー(中央)の可否をチェックする。
	 *
	 * @param a_suteHai
	 *            捨牌
	 * @return チー(中央)の可否
	 */
	public boolean validChiiCenter(Hai a_suteHai, Hai[] a_sarashiHais) {
		if (a_suteHai.isTsuu()) {
			return false;
		}

		if (a_suteHai.getNo() == Hai.NO_1) {
			return false;
		}

		if (a_suteHai.getNo() == Hai.NO_9) {
			return false;
		}

		if (m_fuuroNums >= FUURO_MAX) {
			return false;
		}

		int noKindCenter = a_suteHai.getNoKind();
		int noKindLeft = noKindCenter - 1;
		int noKindRight = noKindCenter + 1;
		for (int i = 0; i < m_jyunTehaiLength; i++) {
			if (m_jyunTehai[i].getNoKind() == noKindLeft) {
				for (int j = i + 1; j < m_jyunTehaiLength; j++) {
					if (m_jyunTehai[j].getNoKind() == noKindRight) {
						a_sarashiHais[0] = new Hai(m_jyunTehai[i]);
						a_sarashiHais[1] = new Hai(m_jyunTehai[j]);
						return true;
					}
				}
			}
		}

		return false;
	}

	/**
	 * チー(中央)を設定する。
	 *
	 * @param a_suteHai
	 *            捨牌
	 * @param a_relation
	 *            関係
	 * @return 結果
	 */
	public boolean setChiiCenter(Hai a_suteHai, int a_relation) {
		Hai sarashiHais[] = new Hai[2];
		if (!validChiiCenter(a_suteHai, sarashiHais)) {
			return false;
		}

		Hai hais[] = new Hai[Mahjong.MENTSU_HAI_MEMBERS_4];

		hais[1] = new Hai(a_suteHai);

		int noKindCenter = a_suteHai.getNoKind();
		int noKindLeft = noKindCenter - 1;
		int noKindRight = noKindCenter + 1;
		for (int i = 0; i < m_jyunTehaiLength; i++) {
			if (m_jyunTehai[i].getNoKind() == noKindLeft) {
				hais[0] = new Hai(m_jyunTehai[i]);

				rmJyunTehai(i);

				for (int j = i; j < m_jyunTehaiLength; j++) {
					if (m_jyunTehai[j].getNoKind() == noKindRight) {
						hais[2] = new Hai(m_jyunTehai[j]);

						rmJyunTehai(j);

						hais[3] = new Hai();

						m_fuuros[m_fuuroNums].setType(Fuuro.TYPE_MINSHUN);
						m_fuuros[m_fuuroNums].setRelation(a_relation);
						m_fuuros[m_fuuroNums].setHais(hais);
						m_fuuroNums++;
						return true;
					}
				}
			}
		}

		return false;
	}

	/**
	 * チー(右)の可否をチェックする。
	 *
	 * @param a_suteHai
	 *            捨牌
	 * @return チー(右)の可否
	 */
	public boolean validChiiRight(Hai a_suteHai, Hai[] a_sarashiHais) {
		if (a_suteHai.isTsuu()) {
			return false;
		}

		if (a_suteHai.getNo() == Hai.NO_1) {
			return false;
		}

		if (a_suteHai.getNo() == Hai.NO_2) {
			return false;
		}

		if (m_fuuroNums >= FUURO_MAX) {
			return false;
		}

		int noKindRight = a_suteHai.getNoKind();
		int noKindLeft = noKindRight - 2;
		int noKindCenter = noKindRight - 1;
		for (int i = 0; i < m_jyunTehaiLength; i++) {
			if (m_jyunTehai[i].getNoKind() == noKindLeft) {
				for (int j = i + 1; j < m_jyunTehaiLength; j++) {
					if (m_jyunTehai[j].getNoKind() == noKindCenter) {
						a_sarashiHais[0] = new Hai(m_jyunTehai[i]);
						a_sarashiHais[1] = new Hai(m_jyunTehai[j]);
						return true;
					}
				}
			}
		}

		return false;
	}

	/**
	 * チー(右)を設定する。
	 *
	 * @param a_suteHai
	 *            捨牌
	 * @param a_relation
	 *            関係
	 * @return 結果
	 */
	public boolean setChiiRight(Hai a_suteHai, int a_relation) {
		Hai sarashiHais[] = new Hai[2];
		if (!validChiiRight(a_suteHai, sarashiHais)) {
			return false;
		}

		Hai hais[] = new Hai[Mahjong.MENTSU_HAI_MEMBERS_4];

		hais[2] = new Hai(a_suteHai);

		int noKindRight = a_suteHai.getNoKind();
		int noKindLeft = noKindRight - 2;
		int noKindCenter = noKindRight - 1;
		for (int i = 0; i < m_jyunTehaiLength; i++) {
			if (m_jyunTehai[i].getNoKind() == noKindLeft) {
				hais[0] = new Hai(m_jyunTehai[i]);

				rmJyunTehai(i);

				for (int j = i; j < m_jyunTehaiLength; j++) {
					if (m_jyunTehai[j].getNoKind() == noKindCenter) {
						hais[1] = new Hai(m_jyunTehai[j]);

						rmJyunTehai(j);

						hais[3] = new Hai();

						m_fuuros[m_fuuroNums].setType(Fuuro.TYPE_MINSHUN);
						m_fuuros[m_fuuroNums].setRelation(a_relation);
						m_fuuros[m_fuuroNums].setHais(hais);
						m_fuuroNums++;
						return true;
					}
				}
			}
		}

		return false;
	}

	/**
	 * ポンの可否をチェックする。
	 *
	 * @param a_suteHai
	 *            捨牌
	 * @return ポンの可否
	 */
	public boolean validPon(Hai a_suteHai) {
		if (m_fuuroNums >= FUURO_MAX) {
			return false;
		}

		int id = a_suteHai.getId();
		for (int i = 0, count = 1; i < m_jyunTehaiLength; i++) {
			if (id == m_jyunTehai[i].getId()) {
				count++;
				if (count >= MENTSU_LENGTH_3) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * ポンを設定する。
	 *
	 * @param a_suteHai
	 *            捨牌
	 * @param a_relation
	 *            関係
	 * @return 結果
	 */
	public boolean setPon(Hai a_suteHai, int a_relation) {
		if (!validPon(a_suteHai)) {
			return false;
		}

		Hai hais[] = new Hai[Mahjong.MENTSU_HAI_MEMBERS_4];

		int iHais = 0;
		hais[iHais] = new Hai(a_suteHai);
		iHais++;

		int id = a_suteHai.getId();
		for (int i = 0; i < m_jyunTehaiLength; i++) {
			if (id == m_jyunTehai[i].getId()) {
				hais[iHais] = new Hai(m_jyunTehai[i]);
				iHais++;

				rmJyunTehai(i);
				i--;

				if (iHais >= MENTSU_LENGTH_3) {
					break;
				}
			}
		}

		hais[iHais] = new Hai();

		m_fuuros[m_fuuroNums].setType(Fuuro.TYPE_MINKOU);
		m_fuuros[m_fuuroNums].setRelation(a_relation);
		m_fuuros[m_fuuroNums].setHais(hais);
		m_fuuroNums++;

		return true;
	}

	/**
	 * 槓の可否をチェックする。
	 *
	 * @param a_addHai
	 *            追加する牌
	 * @return 暗槓の可否
	 */
	public int validKan(Hai a_addHai, Hai[] a_kanHais) {
		if (m_fuuroNums >= FUURO_MAX) {
			return 0;
		}

		int kanCount = 0;

		addJyunTehai(a_addHai);
		int id = m_jyunTehai[0].getId();
		for (int i = 1, count = 1; i < m_jyunTehaiLength; i++) {
			if (id == m_jyunTehai[i].getId()) {
				count++;
				if (count >= MENTSU_LENGTH_4) {
					a_kanHais[kanCount] = new Hai(m_jyunTehai[i]);
					kanCount++;
				}
			} else {
				id = m_jyunTehai[i].getId();
				count = 1;
			}
		}
		rmJyunTehai(a_addHai);

		return kanCount;
	}

	/**
	 * 大明槓を設定する。
	 *
	 * @param a_suteHai
	 *            捨牌
	 * @param a_relation
	 *            関係
	 * @return 結果
	 */
	public boolean setDaiMinKan(Hai a_suteHai, int a_relation) {
		Hai[] kanHais = new Hai[3];
		if (validKan(a_suteHai, kanHais) == 0) {
			return false;
		}

		Hai hais[] = new Hai[Mahjong.MENTSU_HAI_MEMBERS_4];

		int iHais = 0;
		hais[iHais] = new Hai(a_suteHai);
		iHais++;

		int suteHaiId = a_suteHai.getId();
		for (int i = 0; i < m_jyunTehaiLength; i++) {
			if (suteHaiId == m_jyunTehai[i].getId()) {
				hais[iHais] = new Hai(m_jyunTehai[i]);
				iHais++;

				rmJyunTehai(i);
				i--;

				if (iHais >= MENTSU_LENGTH_4) {
					break;
				}
			}
		}

		hais[iHais] = new Hai();

		m_fuuros[m_fuuroNums].setType(Fuuro.TYPE_MINKOU);
		m_fuuros[m_fuuroNums].setRelation(a_relation);
		m_fuuros[m_fuuroNums].setHais(hais);
		m_fuuroNums++;

		return true;
	}

	/**
	 * 加槓の可否をチェックする。
	 *
	 * @param a_tsumoHai
	 *            ツモ牌
	 * @return 加槓の可否
	 */
	public boolean validKaKan(Hai a_tsumoHai) {
		if (m_fuuroNums >= FUURO_MAX) {
			return false;
		}

		int id = a_tsumoHai.getId();
		for (int i = 0; i < m_fuuroNums; i++) {
			if (m_fuuros[i].getType() == Fuuro.TYPE_MINKOU) {
				if (id == (m_fuuros[i].getHais())[0].getId()) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * 加槓を設定する。
	 *
	 * @param a_tsumoHai
	 *            ツモ牌
	 * @return 結果
	 */
	public boolean setKaKan(Hai a_tsumoHai) {
		if (!validKaKan(a_tsumoHai)) {
			return false;
		}

		Hai hais[];

		int id = a_tsumoHai.getId();
		for (int i = 0; i < m_fuuroNums; i++) {
			if (m_fuuros[i].getType() == Fuuro.TYPE_MINKOU) {
				hais = m_fuuros[i].getHais();
				if (id == hais[0].getId()) {
					Hai.copy(hais[3], a_tsumoHai);
					m_fuuros[i].setType(Fuuro.TYPE_KAKAN);
					m_fuuros[i].setHais(hais);
				}
			}
		}

		return true;
	}

	/**
	 * 暗槓を設定する。
	 *
	 * @param a_tsumoHai
	 *            ツモ牌
	 * @param a_relation
	 *            関係
	 * @return 結果
	 */
	public boolean setAnKan(Hai a_tsumoHai, int a_relation) {
		Hai[] kanHais = new Hai[3];
		//if (validKan(a_tsumoHai, kanHais) == 0) {
		//	return false;
		//}

		Hai hais[] = new Hai[Mahjong.MENTSU_HAI_MEMBERS_4];

		int iHais = 0;
		//hais[iHais] = new Hai(a_tsumoHai);
		//iHais++;

		int id = a_tsumoHai.getId();
		for (int i = 0; i < m_jyunTehaiLength; i++) {
			if (id == m_jyunTehai[i].getId()) {
				hais[iHais] = new Hai(m_jyunTehai[i]);
				iHais++;

				rmJyunTehai(i);
				i--;

				if (iHais >= MENTSU_LENGTH_4) {
					break;
				}
			}
		}

		//hais[iHais] = new Hai();

		m_fuuros[m_fuuroNums].setType(Fuuro.TYPE_ANKAN);
		m_fuuros[m_fuuroNums].setRelation(a_relation);
		m_fuuros[m_fuuroNums].setHais(hais);
		m_fuuroNums++;

		return true;
	}

	/**
	 * 副露の配列を取得する。
	 *
	 * @return 副露の配列
	 */
	public Fuuro[] getFuuros() {
		return m_fuuros;
	}

	/**
	 * 副露の個数を取得する。
	 *
	 * @return 副露の個数
	 */
	public int getFuuroNum() {
		return m_fuuroNums;
	}

	/**
	 * 副露の配列をコピーする。
	 *
	 * @param a_dest
	 *            コピー先の副露の配列
	 * @param a_src
	 *            コピー元の副露の配列
	 * @param a_length
	 *            コピーする長さ
	 * @return 結果
	 */
	public static boolean copyFuuros(Fuuro[] a_dest, Fuuro[] a_src, int a_length) {
		if (a_length >= FUURO_MAX) {
			return false;
		}

		for (int i = 0; i < a_length; i++) {
			Fuuro.copy(a_dest[i], a_src[i]);
		}

		return true;
	}
}
