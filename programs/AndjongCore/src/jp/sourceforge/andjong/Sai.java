package jp.sourceforge.andjong;

import java.util.Random;

/**
 * サイコロを管理する。
 *
 * @author Yuji Urushibara
 *
 */
public class Sai {
	/** 番号 */
	private int no = 1;

	/** 乱数ジェネレータ */
	private Random random = new Random();

	/**
	 * 番号を取得する。
	 *
	 * @return 番号
	 */
	public int getNo() {
		return no;
	}

	/**
	 * サイコロを振って番号を取得する。
	 *
	 * @return 番号
	 */
	public int saifuri() {
		return no = random.nextInt(6) + 1;
	}
}
