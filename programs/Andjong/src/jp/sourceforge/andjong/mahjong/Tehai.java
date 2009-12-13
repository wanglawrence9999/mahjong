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

	/** 明槓の配列 */
	private Hai[][] m_minKans = new Hai[FUURO_MAX][MENTSU_LENGTH_4];

	/** 明槓の配列の長さ */
	private int m_minKansLength;

	/** 暗槓の配列 */
	private Hai[][] m_anKans = new Hai[FUURO_MAX][MENTSU_LENGTH_4];

	/** 暗槓の配列の長さ */
	private int m_anKansLength;

	/** 副露の配列 */
	private Fuuro[] m_fuuros = new Fuuro[FUURO_MAX];

	/** 副露の個数 */
	private int m_fuuroNums;

	{
		for (int i = 0; i < JYUN_TEHAI_LENGTH_MAX; i++) {
			m_jyunTehai[i] = new Hai();
		}

		for (int i = 0; i < FUURO_MAX; i++) {
			for (int j = 0; j < MENTSU_LENGTH_4; j++) {
				m_minKans[i][j] = new Hai();
				m_anKans[i][j] = new Hai();
			}
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
		m_minKansLength = 0;
		m_anKansLength = 0;
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

		a_dest.m_minKansLength = a_src.m_minKansLength;
		Tehai.copyMinKans(a_dest.m_minKans, a_src.m_minKans, a_dest.m_minKansLength);

		a_dest.m_anKansLength = a_src.m_anKansLength;
		Tehai.copyMinKans(a_dest.m_anKans, a_src.m_anKans, a_dest.m_anKansLength);

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
	public boolean copyJyunTehaiIdx(Hai a_hai, int a_index) {
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
	public boolean validChiiLeft(Hai a_suteHai) {
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

		if (m_jyunTehaiLength <= MENTSU_LENGTH_3) {
			return false;
		}

		int noKindLeft = a_suteHai.getNoKind();
		int noKindCenter = noKindLeft + 1;
		int noKindRight = noKindLeft + 2;
		for (int i = 0; i < m_jyunTehaiLength; i++) {
			if (m_jyunTehai[i].getNoKind() == noKindCenter) {
				for (int j = i + 1; j < m_jyunTehaiLength; j++) {
					if (m_jyunTehai[j].getNoKind() == noKindRight) {
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
		if (!validChiiLeft(a_suteHai)) {
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
	public boolean validChiiCenter(Hai a_suteHai) {
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

		if (m_jyunTehaiLength <= MENTSU_LENGTH_3) {
			return false;
		}

		int noKindCenter = a_suteHai.getNoKind();
		int noKindLeft = noKindCenter - 1;
		int noKindRight = noKindCenter + 1;
		for (int i = 0; i < m_jyunTehaiLength; i++) {
			if (m_jyunTehai[i].getNoKind() == noKindLeft) {
				for (int j = i + 1; j < m_jyunTehaiLength; j++) {
					if (m_jyunTehai[j].getNoKind() == noKindRight) {
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
	 * @return 結果
	 */
	public boolean setChiiCenter(Hai a_suteHai, int a_relation) {
		if (!validChiiCenter(a_suteHai)) {
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
	public boolean validChiiRight(Hai a_suteHai) {
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

		if (m_jyunTehaiLength <= MENTSU_LENGTH_3) {
			return false;
		}

		int noKindRight = a_suteHai.getNoKind();
		int noKindLeft = noKindRight - 2;
		int noKindCenter = noKindRight - 1;
		for (int i = 0; i < m_jyunTehaiLength; i++) {
			if (m_jyunTehai[i].getNoKind() == noKindLeft) {
				for (int j = i + 1; j < m_jyunTehaiLength; j++) {
					if (m_jyunTehai[j].getNoKind() == noKindCenter) {
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
	 * @return 結果
	 */
	public boolean setChiiRight(Hai a_suteHai, int a_relation) {
		if (!validChiiRight(a_suteHai)) {
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

		int iMinKou = 0;
		hais[iMinKou] = new Hai(a_suteHai);
		iMinKou++;

		int id = a_suteHai.getId();
		for (int i = 0; i < m_jyunTehaiLength; i++) {
			if (id == m_jyunTehai[i].getId()) {
				hais[iMinKou] = new Hai(m_jyunTehai[i]);
				iMinKou++;

				rmJyunTehai(i);
				i--;

				if (iMinKou >= MENTSU_LENGTH_3) {
					break;
				}
			}
		}

		hais[iMinKou] = new Hai();

		m_fuuros[m_fuuroNums].setType(Fuuro.TYPE_MINKOU);
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
	public int getFuuroNums() {
		return m_fuuroNums;
	}

	/**
	 * 明槓を追加する。
	 *
	 * @param minKan
	 *            明槓
	 * @return 結果
	 */
	public boolean addMinKan(Hai[] minKan) {
		if (m_minKansLength >= FUURO_MAX) {
			return false;
		}

		for (int i = 0; i < MENTSU_LENGTH_4; i++) {
			Hai.copy(m_minKans[m_minKansLength][i], minKan[i]);
		}
		m_minKansLength++;

		return true;
	}

	/**
	 * 明槓の配列を取得する。
	 *
	 * @return 明槓の配列
	 */
	public Hai[][] getMinKans() {
		return m_minKans;
	}

	/**
	 * 明槓の配列の長さを取得する。
	 *
	 * @return 明槓の配列の長さ
	 */
	public int getMinKansLength() {
		return m_minKansLength;
	}

	/**
	 * 明槓の配列をコピーする。
	 *
	 * @param destMinKans
	 *            コピー先の明槓の配列
	 * @param srcMinKans
	 *            コピー元の明槓の配列
	 * @param length
	 *            コピーする長さ
	 * @return 結果
	 */
	public static boolean copyMinKans(Hai[][] destMinKans, Hai[][] srcMinKans, int length) {
		if (length >= FUURO_MAX) {
			return false;
		}

		for (int i = 0; i < length; i++) {
			for (int j = 0; j < MENTSU_LENGTH_4; j++) {
				Hai.copy(destMinKans[i][j], srcMinKans[i][j]);
			}
		}

		return true;
	}

	/**
	 * 明槓の可否をチェックする。
	 *
	 * @param addHai
	 *            追加する牌
	 * @return 明槓の可否
	 */
	public boolean validMinKan(Hai addHai) {
		if (m_jyunTehaiLength <= MENTSU_LENGTH_4) {
			return false;
		}

		if (m_minKansLength >= FUURO_MAX) {
			return false;
		}

		int addHaiId = addHai.getId();
		for (int i = 0, minKanIdx = 1; i < m_jyunTehaiLength; i++) {
			if (addHaiId == m_jyunTehai[i].getId()) {
				minKanIdx++;
				if (minKanIdx >= MENTSU_LENGTH_4) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * 明槓を設定する。
	 *
	 * @param suteHai
	 *            捨牌
	 * @return 結果
	 */
	public boolean setMinKan(Hai suteHai) {
		if (!validMinKan(suteHai)) {
			return false;
		}

		int minKanIdx = 0;

		Hai.copy(m_minKans[m_minKansLength][minKanIdx], suteHai);
		minKanIdx++;

		int suteHaiId = suteHai.getId();

		for (int i = 0; i < m_jyunTehaiLength; i++) {
			if (suteHaiId == m_jyunTehai[i].getId()) {
				Hai.copy(m_minKans[m_minKansLength][minKanIdx], m_jyunTehai[i]);
				minKanIdx++;

				rmJyunTehai(i--);

				if (minKanIdx >= MENTSU_LENGTH_4) {
					break;
				}
			}
		}

		m_minKansLength++;

		return true;
	}

	/**
	 * 暗槓を追加する。
	 *
	 * @param anKan
	 *            暗槓
	 * @return 結果
	 */
	public boolean addAnKan(Hai[] anKan) {
		if (m_anKansLength >= FUURO_MAX) {
			return false;
		}

		for (int i = 0; i < MENTSU_LENGTH_4; i++) {
			Hai.copy(m_anKans[m_anKansLength][i], anKan[i]);
		}
		m_anKansLength++;

		return true;
	}

	/**
	 * 暗槓の配列を取得する。
	 *
	 * @return 暗槓の配列
	 */
	public Hai[][] getAnKans() {
		return m_anKans;
	}

	/**
	 * 暗槓の配列の長さを取得する。
	 *
	 * @return 暗槓の配列の長さ
	 */
	public int getAnKansLength() {
		return m_anKansLength;
	}

	/**
	 * 暗槓の配列をコピーする。
	 *
	 * @param destAnKans
	 *            コピー先の暗槓の配列
	 * @param srcAnKans
	 *            コピー元の暗槓の配列
	 * @param length
	 *            コピーする長さ
	 * @return 結果
	 */
	public static boolean copyAnKans(Hai[][] destAnKans, Hai[][] srcAnKans, int length) {
		if (length >= FUURO_MAX) {
			return false;
		}

		for (int i = 0; i < length; i++) {
			for (int j = 0; j < MENTSU_LENGTH_4; j++) {
				Hai.copy(destAnKans[i][j], srcAnKans[i][j]);
			}
		}

		return true;
	}

	/**
	 * 暗槓の可否をチェックする。
	 *
	 * @param addHai
	 *            追加する牌
	 * @return 暗槓の可否
	 */
	public boolean validAnKan(Hai addHai) {
		if (m_jyunTehaiLength <= MENTSU_LENGTH_4) {
			return false;
		}

		if (m_anKansLength >= FUURO_MAX) {
			return false;
		}

		int addHaiId = addHai.getId();
		for (int i = 0, anKanIdx = 1; i < m_jyunTehaiLength; i++) {
			if (addHaiId == m_jyunTehai[i].getId()) {
				anKanIdx++;
				if (anKanIdx >= MENTSU_LENGTH_4) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * 暗槓を設定する。
	 *
	 * @param tsumoHai
	 *            ツモ牌
	 * @return 結果
	 */
	public boolean setAnKan(Hai tsumoHai) {
		if (!validAnKan(tsumoHai)) {
			return false;
		}

		int anKanIdx = 0;

		Hai.copy(m_anKans[m_anKansLength][anKanIdx], tsumoHai);
		anKanIdx++;

		int tsumoHaiId = tsumoHai.getId();

		for (int i = 0; i < m_jyunTehaiLength; i++) {
			if (tsumoHaiId == m_jyunTehai[i].getId()) {
				Hai.copy(m_anKans[m_anKansLength][anKanIdx], m_jyunTehai[i]);
				anKanIdx++;

				rmJyunTehai(i--);

				if (anKanIdx >= MENTSU_LENGTH_4) {
					break;
				}
			}
		}

		m_anKansLength++;

		return true;
	}

	public static boolean copyFuuros(Fuuro[] destFuuros, Fuuro[] srcFuuros, int length) {
		if (length >= FUURO_MAX) {
			return false;
		}

		for (int i = 0; i < length; i++) {
			Fuuro.copy(destFuuros[i], srcFuuros[i]);
		}

		return true;
	}
}
