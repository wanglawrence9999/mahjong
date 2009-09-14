package jp.sourceforge.andjong;

public class AgariSetting {
	/** 特殊系役*/
	public enum YakuflgName{
		/** 立直 */
		REACH,
		/** 一発 */
		IPPATU,
		/** ツモ */
		TUMO  ,
		/** 海底 */
		HAITEI,
		/** 河底 */
		HOUTEI,
		/** 嶺上開放 */
		RINSYAN,
		/** 搶槓 */
		CHANKAN,
		/** ダブル立直 */
		DOUBLEREACH,
		/** 天和 */
		TENHOU,
		/** 地和 */
		TIHOU,
		/** 人和	*/
		RENHOU,
		/** 十三不塔 */
		TIISANPUTOU,
		/** 流し満貫 */
		NAGASHIMANGAN,
		/** 八連荘 */
		PARENCHAN,
		/** 役の数 */
		YAKUFLGNUM
	}

	/** 役成立フラグの配列 */
	boolean yakuflg[] = new boolean [YakuflgName.YAKUFLGNUM.ordinal()];
	/** 自風 */
	private int jikaze = Hai.NO_TON;
	/** 場風の設定 */
	private int bakaze = Hai.NO_TON;
	/** 表ドラ表示牌 */
	Hai doraHais[] = new Hai[4];
	/** 裏ドラ */
	Hai uraDoraHais[] = new Hai[4];
	
	AgariSetting(){
		for(int i = 0 ; i < yakuflg.length ; i++){
			yakuflg[i] = false;
		}
	}
	
	/** コンストラクタ */
	AgariSetting(Game game){
		for(int i = 0 ; i < yakuflg.length ; i++){
			yakuflg[i] = false;
		}
		this.doraHais = game.getDoras();
		this.uraDoraHais = game.getUraDoras();
		this.jikaze = game.getJikaze();
	}
	
	/** 特殊役成立の設定 */
	void setYakuflg(int yakuNum ,boolean yakuflg){
		this.yakuflg[yakuNum] = yakuflg;
	}
	/** 特殊役成立の取得 */
	boolean getYakuflg(int yakuNum){
		return this.yakuflg[yakuNum];
	}
	
	/** 自風の設定 */
	void setJikaze(int jikaze){
		this.jikaze = jikaze;
	}
	/** 自風の取得 */
	int getJikaze(){
		return this.jikaze;
	}
	
	/** 場風の設定 */
	void setBakaze(int bakaze){
		this.bakaze = bakaze;
	}
	/** 場風の取得 */
	int getBakaze(){
		return this.bakaze;
	}
	
	
	/** ドラ表示牌の設定 */
	void setDoraHais(Hai[] doraHais){
		this.doraHais = doraHais;
	}
	/** ドラ表示牌の取得 */
	Hai[] getDoraHais(){
		return this.doraHais;
	}
	
	/** 裏ドラ表示牌の設定 */
	void getUraDoraHais(Hai[] uraDoraHais){
		this.doraHais = uraDoraHais;
	}
	/** 裏ドラ表示牌の取得 */
	Hai[] setUraDoraHais(){
		return this.uraDoraHais;
	}
}
