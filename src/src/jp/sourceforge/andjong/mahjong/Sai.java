package jp.sourceforge.andjong.mahjong;

import java.util.Random;

/**
 * サイコロを管理する。
 *
 * @author Yuji Urushibara
 *
 */
public class Sai {
	/** シード */
	private static long m_seed;

	/** 番号 */
	private int m_no = 1;

	/** 乱数ジェネレータ */
	private Random m_random = new Random(m_seed++);

	static {
		m_seed = System.currentTimeMillis();
	}

	/**
	 * 番号を取得する。
	 *
	 * @return 番号
	 */
	public int getNo() {
		return m_no;
	}

	/**
	 * サイコロを振って番号を取得する。
	 *
	 * @return 番号
	 */
	public int saifuri() {
		return m_no = m_random.nextInt(6) + 1;
	}
}
