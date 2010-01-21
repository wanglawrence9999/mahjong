package jp.sourceforge.andjong.mahjong;
import static jp.sourceforge.andjong.mahjong.AgariSetting.YakuflgName.*;
import static jp.sourceforge.andjong.mahjong.Hai.*;
import static jp.sourceforge.andjong.mahjong.Tehai.JYUN_TEHAI_LENGTH_MAX;
import jp.sourceforge.andjong.mahjong.CountFormat.Combi;

/**
 * 手牌全体の役を判定するクラスです。
 *
 * @author Hiroyuki Muromachi
 *
 */
public class Yaku {
	public static final int JIKAZE_TON = 0;
	public static final int JIKAZE_NAN = 1;
	public static final int JIKAZE_SYA = 2;
	public static final int JIKAZE_PEI = 3;

	Tehai m_tehai;
	Hai m_addHai;
	Combi m_combi;
	AgariSetting m_setting;
	YakuHantei yakuhantei[];
	boolean nakiflg = false;
	int m_doraCount;

	/**
	 * Yakuクラスのコンストラクタ。
	 * 引数を保存し、YakuHanteiクラスの配列を作成する。
	 * @param tehai 手牌　addHai 上がった牌  combi 手牌 の組み合わせ info 情報
	 */
	Yaku(Tehai tehai, Hai addHai, Combi combi,AgariSetting setting){
		this.m_tehai = tehai;
		this.m_addHai = addHai;
		this.m_combi  = combi;
		this.m_setting = setting;
		//鳴きがある場合
		nakiflg = tehai.isNaki();

		YakuHantei buffer[] = {new CheckTanyao(),
							   new CheckPinfu(),
							   new CheckIpeikou(),
							   new CheckReach(),
							   new CheckIppatu(),
							   new CheckTumo(),
							   new CheckTon(),
							   new CheckNan(),
							   new CheckSya(),
							   new CheckPei(),
							   new CheckHaku(),
							   new CheckHatu(),
							   new CheckCyun(),
							   new CheckHaitei(),
							   new CheckHoutei(),
							   new CheckRinsyan(),
							   new CheckCyankan(),
							   new CheckDoubleReach(),
//							   new CheckTeetoitu(),
							   new CheckCyanta(),
							   new CheckIkkituukan(),
							   new CheckSansyokuDoukou(),
							   new CheckSansyokuDoujun(),
							   new CheckToitoi(),
							   new CheckSanankou(),
							   new CheckSankantu(),
							   new CheckRyanpeikou(),
							   new CheckHonitu(),
							   new CheckJunCyan(),
							   new CheckSyousangen(),
							   new CheckHonroutou(),
							   new CheckTinitu(),
							   new CheckSuuankou(),
							   new CheckSuukantu(),
							   new CheckDaisangen(),
							   new CheckSyousuushi(),
							   new CheckDaisuushi(),
							   new CheckTuuisou(),
							   new CheckChinroutou(),
							   new CheckRyuuisou(),
							   new CheckCyuurennpoutou(),
							   new CheckKokushi(),
							   new CheckTenhou(),
							   new CheckTihou(),
							   new CheckDora()
		};

		yakuhantei = buffer;

		yakuhantei[yakuhantei.length - 1].hanSuu = m_doraCount;

		//役満成立時は他の一般役は切り捨てる
		for(int i = 0 ; i < yakuhantei.length ; i++){
			if((yakuhantei[i].getYakuman() == true) && (yakuhantei[i].getYakuHantei() == true)) {
				for(int j = 0 ; j < yakuhantei.length; j++){
					if(yakuhantei[j].getYakuman() == false){
						yakuhantei[j].setYakuHantei(false);
					}
				}
			}
		}
	}


	/**
	 * Yakuクラスのコンストラクタ。
	 * 引数を保存し、YakuHanteiクラスの配列を作成する。
	 * @param tehai 手牌　addHai 上がった牌  combi 手牌 の組み合わせ info 情報
	 */
	Yaku(Tehai tehai, Hai addHai, AgariSetting setting){
		this.m_tehai = tehai;
		this.m_addHai = addHai;
		this.m_setting = setting;

		YakuHantei buffer[] = {new CheckKokushi(),
							   new CheckTenhou(),
							   new CheckTihou()
		};

		yakuhantei = buffer;
	}

	Yaku(Tehai tehai, Hai addHai, Combi combi,AgariSetting setting, int a_status){
		this.m_tehai = tehai;
		this.m_addHai = addHai;
		this.m_combi  = combi;
		this.m_setting = setting;
		nakiflg = false;

		YakuHantei buffer[] = {
				new CheckTanyao(),
				new CheckReach(),
				new CheckTumo(),
				new CheckHaitei(),
				new CheckHoutei(),
				new CheckRinsyan(),
				new CheckTeetoitu(),
				new CheckHonroutouChiitoitsu(),
				new CheckHonitu(),
				new CheckTinitu(),
				new CheckTuuisou(),
				new CheckTenhou(),
				new CheckTihou(),
				new CheckDora()
		};

		yakuhantei = buffer;

		//yakuhantei[yakuhantei.length - 1].hanSuu = m_doraCount;
	}

	/**
	 * 手牌全体の翻数を取得します。
	 *
	 * @return 手牌全体の翻数
	 */
	int getHanSuu(){
		int hanSuu = 0;
		for(int i = 0 ; i < yakuhantei.length ; i++){
			if( yakuhantei[i].getYakuHantei() == true){
				hanSuu+= yakuhantei[i].getHanSuu();
			}
		}

		// ドラのみは無し
		if (hanSuu == yakuhantei[yakuhantei.length - 1].hanSuu) {
			return 0;
		}

		return hanSuu;
	}

	int getHan(){
		int hanSuu = 0;
		for(int i = 0 ; i < yakuhantei.length ; i++){
			if( yakuhantei[i].getYakuHantei() == true){
				hanSuu+= yakuhantei[i].getHanSuu();
			}
		}

		return hanSuu;
	}

	/**
	 * 成立している役の名前を取得します。
	 *
	 * @return 成立している役の名前の配列
	 */
	String[] getYakuName(){
		int count = 0;
		int hanSuu;

		//成立している役の数をカウント
		for(int i = 0 ; i < yakuhantei.length ; i++){
			if( yakuhantei[i].getYakuHantei() == true){
				count++;
			}
		}

		String yakuName[] = new String[count];
		count = 0;
		for(int i = 0 ; i < yakuhantei.length ; i++){
			if( yakuhantei[i].getYakuHantei() == true){
				hanSuu = yakuhantei[i].getHanSuu();
				if (hanSuu >= 13) {
					yakuName[count] = yakuhantei[i].getYakuName() + " " + "役満";
				} else {
					yakuName[count] = yakuhantei[i].getYakuName() + " " + hanSuu + "翻";
				}
				count++;
			}
		}
		return yakuName;
	}

	/**
	 * 役満が成立しているかを取得します。
	 *
	 * @return 役満成立フラグ
	 */
	boolean getYakumanflg(){
		for(int i = 0 ; yakuhantei[i] != null ; i++){
			if( yakuhantei[i].getYakuman() == true){
				return true;
			}
		}
		return false;
	}

	/**
	 * 個別の役を判定するクラスです。
	 *
	 * @author Hiroyuki Muromachi
	 *
	 */
	private class YakuHantei{
		/** 役の成立判定フラグ */
		boolean hantei = false;
		/** 役満の判定フラグ */
		boolean yakuman = false;
		/** 役の名前 */
		String  yakuName;
		/** 役の翻数 */
		int hanSuu;

		/**
		 * 役の成立判定フラグを取得します。
		 *
		 * @return 役の成立判定フラグ
		 */
		boolean getYakuHantei(){
			return hantei;
		}

		/**
		 * 役の成立判定フラグを設定します。
		 *
		 * @param hantei
		 */
		void setYakuHantei(boolean hantei){
			this.hantei = hantei;
		}

		/**
		 * 役の翻数を取得します。
		 *
		 * @return 役の翻数
		 */
		int getHanSuu(){
			return hanSuu;
		}

		/**
		 * 役の名前を取得します。
		 *
		 * @return 役の名前
		 */
		String getYakuName(){
			return yakuName;
		}

		/**
		 * 役満の判定フラグを取得します。
		 *
		 * @return 役満の判定フラグ
		 */
		boolean getYakuman(){
			return yakuman;
		}
	}

	private class CheckTanyao extends YakuHantei{
		CheckTanyao(){
			hantei = checkTanyao();
			yakuName = "断幺";
			hanSuu = 1;
		}
	}

	private class CheckPinfu extends YakuHantei{
		CheckPinfu(){
			hantei = checkPinfu();
			yakuName = "平和";
			hanSuu = 1;
		}
	}

	private class CheckIpeikou extends YakuHantei{
		CheckIpeikou(){
			hantei = checkIpeikou();
			if(checkRyanpeikou()){
				hantei = false;
			}
			yakuName = "一盃口";
			hanSuu = 1;
		}
	}

	private class CheckReach extends YakuHantei{
		CheckReach(){
			hantei = checkReach();
			if(checkDoubleReach() == true){
				hantei = false;
			}
			yakuName = "立直";
			hanSuu = 1;
		}
	}

	private class CheckIppatu extends YakuHantei{
		CheckIppatu(){
			hantei = checkIppatu();
			yakuName = "一発";
			hanSuu = 1;
		}
	}

	private class CheckTumo extends YakuHantei{
		CheckTumo(){
			hantei = checkTumo();
			yakuName = "門前清自摸和";
			hanSuu = 1;
		}
	}

	private class CheckTon extends YakuHantei{
		CheckTon(){
			hantei = checkTon();
			if(m_setting.getJikaze() == JIKAZE_TON){
				yakuName = "ダブ東";
				hanSuu = 2;
			}else{
				yakuName = "東";
				hanSuu = 1;
			}
		}
	}

	private class CheckNan extends YakuHantei{
		CheckNan(){
			hantei = checkNan();
			yakuName = "南";
			hanSuu = 1;
		}
	}

	private class CheckSya extends YakuHantei{
		CheckSya(){
			hantei = checkSya();
			yakuName = "西";
			hanSuu = 1;
		}
	}

	private class CheckPei extends YakuHantei{
		CheckPei(){
			hantei = checkPei();
			yakuName = "北";
			hanSuu = 1;
		}
	}

	private class CheckHaku extends YakuHantei{
		CheckHaku(){
			hantei = checkHaku();
			yakuName = "白";
			hanSuu = 1;
		}
	}

	private class CheckHatu extends YakuHantei{
		CheckHatu(){
			hantei = checkHatu();
			yakuName = "發";
			hanSuu = 1;
		}
	}

	private class CheckCyun extends YakuHantei{
		CheckCyun(){
			hantei = checkCyun();
			yakuName = "中";
			hanSuu = 1;
		}
	}

	private class CheckHaitei extends YakuHantei{
		CheckHaitei(){
			hantei = checkHaitei();
			yakuName = "海底摸月";
			hanSuu = 1;
		}
	}

	private class CheckHoutei extends YakuHantei{
		CheckHoutei(){
			hantei = checkHoutei();
			yakuName = "河底撈魚";
			hanSuu = 1;
		}
	}

	private class CheckRinsyan extends YakuHantei{
		CheckRinsyan(){
			hantei = checkRinsyan();
			yakuName = "嶺上開花";
			hanSuu = 1;
		}
	}

	private class CheckCyankan extends YakuHantei{
		CheckCyankan(){
			hantei = checkCyankan();
			yakuName = "槍槓";
			hanSuu = 1;
		}
	}

	private class CheckDoubleReach extends YakuHantei{
		CheckDoubleReach(){
			hantei = checkDoubleReach();
			yakuName = "ダブル立直";
			hanSuu = 2;
		}
	}

	private class CheckTeetoitu extends YakuHantei{
		CheckTeetoitu(){
			hantei = checkTeetoitu();
			yakuName = "七対子";
			hanSuu = 2;
		}
	}

	private class CheckCyanta extends YakuHantei{
		CheckCyanta(){
			hantei = checkCyanta();
			if(checkJunCyan()){
				hantei = false;
			}
			if(checkHonroutou()){
				hantei = false;
			}
			yakuName = "全帯";
			if (nakiflg == true) {
				hanSuu = 1;
			}else{
				hanSuu = 2;
			}
		}
	}

	private class CheckIkkituukan extends YakuHantei{
		CheckIkkituukan(){
			hantei = checkIkkituukan();
			yakuName = "一気通貫";
			if (nakiflg == true) {
				hanSuu = 1;
			}else{
				hanSuu = 2;
			}
		}
	}

	private class CheckSansyokuDoujun extends YakuHantei{
		CheckSansyokuDoujun(){
			hantei = checkSansyokuDoujun();
			yakuName = "三色同順";
			if (nakiflg == true) {
				hanSuu = 1;
			}else{
				hanSuu = 2;
			}
		}
	}

	private class CheckSansyokuDoukou extends YakuHantei{
		CheckSansyokuDoukou(){
			hantei = checkSansyokuDoukou();
			yakuName = "三色同刻";
			hanSuu = 2;
		}
	}

	private class CheckToitoi extends YakuHantei{
		CheckToitoi(){
			hantei = checkToitoi();
			yakuName = "対々和";
			hanSuu = 2;
		}
	}

	private class CheckSanankou extends YakuHantei{
		CheckSanankou(){
			hantei = checkSanankou();
			yakuName = "三暗刻";
			hanSuu = 2;
		}
	}

	private class CheckSankantu extends YakuHantei{
		CheckSankantu(){
			hantei = checkSankantu();
			yakuName = "三槓子";
			hanSuu = 2;
		}
	}

	private class CheckRyanpeikou extends YakuHantei{
		CheckRyanpeikou(){
			hantei = checkRyanpeikou();
			yakuName = "二盃口";
			hanSuu = 3;
		}
	}

	private class CheckHonitu extends YakuHantei{
		CheckHonitu(){
			hantei = checkHonitu();
			if(checkTinitu()){
				hantei = false;
			}
			yakuName = "混一色";
			if (nakiflg == true) {
				hanSuu = 2;
			}else{
				hanSuu = 3;
			}
		}
	}

	private class CheckJunCyan extends YakuHantei{
		CheckJunCyan(){
			hantei = checkJunCyan();
			yakuName = "純全帯";
			if (nakiflg == true) {
				hanSuu = 2;
			}else{
				hanSuu = 3;
			}
		}
	}

	private class CheckSyousangen extends YakuHantei{
		CheckSyousangen(){
			hantei = checkSyousangen();
			yakuName = "小三元";
			hanSuu = 2;
		}
	}

	private class CheckHonroutou extends YakuHantei{
		CheckHonroutou(){
			hantei = checkHonroutou();
			yakuName = "混老頭";
			hanSuu = 2;
		}
	}

	private class CheckHonroutouChiitoitsu extends YakuHantei{
		CheckHonroutouChiitoitsu(){
			hantei = checkHonroutouChiitoitsu();
			yakuName = "混老頭";
			hanSuu = 2;
		}
	}

	private class CheckTinitu extends YakuHantei{
		CheckTinitu(){
			hantei = checkTinitu();
			yakuName = "清一色";
			if (nakiflg == true) {
				hanSuu = 5;
			}else{
				hanSuu = 6;
			}
		}
	}

	private class CheckSuuankou extends YakuHantei{
		CheckSuuankou(){
			hantei = checkSuuankou();
			yakuName = "四暗刻";
			hanSuu = 13;
			yakuman = true;
		}
	}

	private class CheckSuukantu extends YakuHantei{
		CheckSuukantu(){
			hantei = checkSuukantu();
			yakuName = "四槓子";
			hanSuu = 13;
			yakuman = true;
		}
	}

	private class CheckDaisangen extends YakuHantei{
		CheckDaisangen(){
			hantei = checkDaisangen();
			yakuName = "大三元";
			hanSuu = 13;
			yakuman = true;
		}
	}

	private class CheckSyousuushi extends YakuHantei{
		CheckSyousuushi(){
			hantei = checkSyousuushi();
			yakuName = "小四喜";
			hanSuu = 13;
			yakuman = true;
		}
	}

	private class CheckDaisuushi extends YakuHantei{
		CheckDaisuushi(){
			hantei = checkDaisuushi();
			yakuName = "大四喜";
			hanSuu = 13;
			yakuman = true;
		}
	}

	private class CheckTuuisou extends YakuHantei{
		CheckTuuisou(){
			hantei = checkTuuisou();
			yakuName = "字一色";
			hanSuu = 13;
			yakuman = true;
		}
	}

	private class CheckChinroutou extends YakuHantei{
		CheckChinroutou(){
			hantei = checkChinroutou();
			yakuName = "清老頭";
			hanSuu = 13;
			yakuman = true;
		}
	}

	private class CheckRyuuisou extends YakuHantei{
		CheckRyuuisou(){
			hantei = checkRyuuisou();
			yakuName = "緑一色";
			hanSuu = 13;
			yakuman = true;
		}
	}
	private class CheckCyuurennpoutou extends YakuHantei{
		CheckCyuurennpoutou(){
			hantei = checkCyuurennpoutou();
			yakuName = "九蓮宝燈";
			hanSuu = 13;
			yakuman = true;
		}
	}
	private class CheckKokushi extends YakuHantei{
		CheckKokushi(){
			hantei = checkKokushi();
			yakuName = "国士無双";
			hanSuu = 13;
			yakuman = true;
		}
	}
	private class CheckTenhou extends YakuHantei{
		CheckTenhou(){
			hantei = checkTenhou();
			yakuName = "天和";
			hanSuu = 13;
			yakuman = true;
		}
	}
	private class CheckTihou extends YakuHantei{
		CheckTihou(){
			hantei = checkTihou();
			yakuName = "地和";
			hanSuu = 13;
			yakuman = true;
		}
	}
	private class CheckDora extends YakuHantei{
		CheckDora(){
			hantei = checkDora();
			yakuName = "ドラ";
			hanSuu = 1;
			yakuman = false;
		}
	}


	boolean checkTanyao() {
		int id;
		Hai[] jyunTehai = m_tehai.getJyunTehai();
		Hai checkHai[];

		//純手牌をチェック
		int jyunTehaiLength = m_tehai.getJyunTehaiLength();
		for (int i = 0; i < jyunTehaiLength; i++) {
			//１９字牌ならば不成立
			if (jyunTehai[i].isYaochuu() == true){
				return false;
			}
		}

		// 追加牌をチェック

		//１９字牌ならば不成立
		if (m_addHai.isYaochuu() == true){
			return false;
		}

		Fuuro[] fuuros;
		fuuros = m_tehai.getFuuros();
		int fuuroNum;
		fuuroNum = m_tehai.getFuuroNum();
		int type;
		for (int i = 0; i < fuuroNum; i++) {
			type = fuuros[i].getType();
			switch (type) {
			case Fuuro.TYPE_MINSHUN:
				//明順の牌をチェック
				checkHai = fuuros[i].getHais();
				id = checkHai[0].getId();
				//123 と　789 の順子があれば不成立
				if ((id == 1) || (id == 7)){
					return false;
				}
				break;
			case Fuuro.TYPE_MINKOU:
				//明刻の牌をチェック
				checkHai = fuuros[i].getHais();
				if (checkHai[0].isYaochuu() == true){
					return false;
				}
				break;
			case Fuuro.TYPE_DAIMINKAN:
			case Fuuro.TYPE_KAKAN:
				//明槓の牌をチェック
				checkHai = fuuros[i].getHais();
				if (checkHai[0].isYaochuu() == true){
					return false;
				}
				break;
			case Fuuro.TYPE_ANKAN:
				//暗槓の牌をチェック
				checkHai = fuuros[i].getHais();
				if (checkHai[0].isYaochuu() == true){
					return false;
				}
				break;
			default:
				break;
			}
		}

		return true;
	}

	boolean checkPinfu() {
		Hai atamaHai;
		//鳴きが入っている場合は成立しない
		if(nakiflg == true){
			return false;
		}

		//面子が順子だけではない
		if(m_combi.m_shunNum != 4){
			return false;
		}

		//頭が三元牌
		atamaHai = new Hai(Hai.noKindToId(m_combi.m_atamaNoKind));
		if( atamaHai.getKind() == KIND_SANGEN ){
			return false;
		}

		//頭が場風
		if( atamaHai.getKind() == KIND_FON
				&& atamaHai.getNo() == m_setting.getBakaze()){
			return false;
		}

		//頭が自風
		if( atamaHai.getKind() == KIND_FON
				&& atamaHai.getNo() == m_setting.getJikaze()){
			return false;
		}

		//字牌の頭待ちの場合は不成立
		if(m_addHai.isTsuu() == true){
			return false;
		}

		//待ちが両面待ちか判定
		boolean ryanmenflg = false;
		//int addHaiid = addHai.getId();
		int addHaiid = m_addHai.getNoKind();
		//上がり牌の数をチェックして場合分け
		switch(m_addHai.getNo()){
			//上がり牌が1,2,3の場合は123,234,345の順子ができているかどうかチェック
			case 1:
			case 2:
			case 3:
				for(int i = 0 ; i < m_combi.m_shunNum ; i++){
					if(addHaiid == m_combi.m_shunNoKinds[i]){
						ryanmenflg = true;
					}
				}
				break;
			//上がり牌が4,5,6の場合は456か234,567か345,678か456の順子ができているかどうかチェック
			case 4:
			case 5:
			case 6:
				for(int i = 0 ; i < m_combi.m_shunNum ; i++){
					if((addHaiid == m_combi.m_shunNoKinds[i])
					 ||(addHaiid-2 == m_combi.m_shunNoKinds[i])){
						ryanmenflg = true;
					}
				}
				break;
			//上がり牌が7,8,9の場合は567,678,789の順子ができているかどうかチェック
			case 7:
			case 8:
			case 9:
				for(int i = 0 ; i < m_combi.m_shunNum ; i++){
					if(addHaiid-2 == (m_combi.m_shunNoKinds[i])){
						ryanmenflg = true;
					}
				}
				break;
			default:
				break;
		}
		if(ryanmenflg == false){
			return false;
		}


		//条件を満たしていれば、約成立
		return true;
	}

	boolean checkIpeikou() {
		//鳴きが入っている場合は成立しない
		if(nakiflg == true){
			return false;
		}

		//順子の組み合わせを確認する
		for (int i = 0; i < m_combi.m_shunNum -1; i++) {
			if(m_combi.m_shunNoKinds[i] == m_combi.m_shunNoKinds[i+1]){
				return true;
			}
		}
		return false;
	}

	boolean checkReach() {
		return m_setting.getYakuflg(REACH.ordinal());
	}

	boolean checkIppatu() {
		return m_setting.getYakuflg(IPPATU.ordinal());
	}

	boolean checkTumo() {
		//鳴きが入っている場合は成立しない
		if(nakiflg == true){
			return false;
		}
		return m_setting.getYakuflg(TUMO.ordinal());
	}


	//役牌ができているかどうかの判定に使う補助メソッド
	private boolean checkYakuHai(Tehai tehai, Combi combi , int yakuHaiId) {
		int id;
		Hai checkHai[];

		//純手牌をチェック
		for(int i = 0; i < combi.m_kouNum ; i++){
			//IDと役牌のIDをチェック
			id = Hai.noKindToId(combi.m_kouNoKinds[i]);
			if( id == yakuHaiId ){
				return true;
			}
		}

		Fuuro[] fuuros;
		fuuros = tehai.getFuuros();
		int fuuroNum;
		fuuroNum = tehai.getFuuroNum();
		int type;
		for (int i = 0; i < fuuroNum; i++) {
			type = fuuros[i].getType();
			switch (type) {
			case Fuuro.TYPE_MINKOU:
				//明刻の牌をチェック
				checkHai = fuuros[i].getHais();
				id = checkHai[0].getId();
				//IDと役牌のIDをチェック
				if( id == yakuHaiId ){
					return true;
				}
				break;
			case Fuuro.TYPE_DAIMINKAN:
			case Fuuro.TYPE_KAKAN:
				//明槓の牌をチェック
				checkHai = fuuros[i].getHais();
				id = checkHai[0].getId();
				//IDと役牌のIDをチェック
				if( id == yakuHaiId ){
					return true;
				}
				break;
			case Fuuro.TYPE_ANKAN:
				//暗槓の牌をチェック
				checkHai = fuuros[i].getHais();
				id = checkHai[0].getId();
				//IDと役牌のIDをチェック
				if( id == yakuHaiId ){
					return true;
				}
				break;
			default:
				break;
			}
		}
		return false;
	}

	boolean checkTon() {
		return checkYakuHai(m_tehai,m_combi,ID_TON);
	}

	boolean checkNan() {
		if(m_setting.getJikaze() == JIKAZE_NAN){
			return checkYakuHai(m_tehai,m_combi,ID_NAN);
		}else{
			return false;
		}
	}

	boolean checkSya() {
		if(m_setting.getJikaze() == JIKAZE_SYA){
			return checkYakuHai(m_tehai,m_combi,ID_SHA);
		}else{
			return false;
		}
	}

	boolean checkPei() {
		if(m_setting.getJikaze() == JIKAZE_PEI){
			return checkYakuHai(m_tehai,m_combi,ID_PE);
		}else{
			return false;
		}
	}

	boolean checkHaku() {
		return checkYakuHai(m_tehai,m_combi,ID_HAKU);
	}

	boolean checkHatu() {
		return checkYakuHai(m_tehai,m_combi,ID_HATSU);
	}

	boolean checkCyun() {
		return checkYakuHai(m_tehai,m_combi,ID_CHUN);
	}

	boolean checkHaitei() {
		return m_setting.getYakuflg(HAITEI.ordinal());
	}

	boolean checkHoutei(){
		return m_setting.getYakuflg(HOUTEI.ordinal());
	}

	boolean checkRinsyan() {
		return m_setting.getYakuflg(RINSYAN.ordinal());
	}

	boolean checkCyankan() {
		return m_setting.getYakuflg(CHANKAN.ordinal());
	}

	boolean checkDoubleReach() {
		return m_setting.getYakuflg(DOUBLEREACH.ordinal());
	}

	boolean checkTeetoitu() {
		//鳴きが入っている場合は成立しない
		if(nakiflg == true){
			return false;
		}

		return true;
	}

	boolean checkCyanta() {
		Hai checkHais[];
		Hai checkHai;

		//純手牌の刻子をチェック
		for(int i = 0; i < m_combi.m_kouNum ; i++){
			checkHai = new Hai(Hai.noKindToId(m_combi.m_kouNoKinds[i]));
			//数牌の場合は数字をチェック
			if (checkHai.isYaochuu() == false){
				return false;
			}
		}

		//純手牌の順子をチェック
		for(int i = 0; i < m_combi.m_shunNum ; i++){
			checkHai = new Hai(Hai.noKindToId(m_combi.m_shunNoKinds[i]));
			//数牌の場合は数字をチェック
			if (checkHai.isTsuu() == false){
				if ((checkHai.getNo() > 1) && (checkHai.getNo() < 7))
					return false;
			}
		}

		//純手牌の頭をチェック
		checkHai = new Hai(Hai.noKindToId(m_combi.m_atamaNoKind));
		if (checkHai.isYaochuu() == false){
			return false;
		}
		Fuuro[] fuuros;
		fuuros = m_tehai.getFuuros();
		int fuuroNum;
		fuuroNum = m_tehai.getFuuroNum();
		int type;
		for (int i = 0; i < fuuroNum; i++) {
			type = fuuros[i].getType();
			switch (type) {
			case Fuuro.TYPE_MINSHUN:
				//明順の牌をチェック
				checkHais = fuuros[i].getHais();
				//123 と　789 以外の順子があれば不成立
				if ((checkHais[0].getNo() > 1) && (checkHais[0].getNo() < 7))
					return false;
				break;
			case Fuuro.TYPE_MINKOU:
				//明刻の牌をチェック
				checkHais = fuuros[i].getHais();
				//数牌の場合は数字をチェック
				if (checkHais[0].isYaochuu() == false){
					return false;
				}
				break;
			case Fuuro.TYPE_DAIMINKAN:
			case Fuuro.TYPE_KAKAN:
				//明槓の牌をチェック
				checkHais = fuuros[i].getHais();
				//数牌の場合は数字をチェック
				if (checkHais[0].isYaochuu() == false){
					return false;
				}
				break;
			case Fuuro.TYPE_ANKAN:
				//暗槓の牌をチェック
				checkHais = fuuros[i].getHais();
				//数牌の場合は数字をチェック
				if (checkHais[0].isYaochuu() == false){
					return false;
				}
				break;
			default:
				break;
			}
		}

		return true;
	}

	boolean checkIkkituukan() {
		int id;
		Hai checkHai[];
		boolean ikkituukanflg[]= {false,false,false,false,false,false,false,false,false};
		//萬子、筒子、索子の1,4,7をチェック
		int checkId[] = {ID_WAN_1,ID_WAN_4,ID_WAN_7,ID_PIN_1,ID_PIN_4,ID_PIN_7,ID_SOU_1,ID_SOU_4,ID_SOU_7};

		//手牌の順子をチェック
		for(int i = 0 ; i < m_combi.m_shunNum ; i++){
			id = Hai.noKindToId(m_combi.m_shunNoKinds[i]);
			for(int j =0 ; j < checkId.length ; j++){
				if(id == checkId[j]){
					ikkituukanflg[j] = true;
				}
			}
		}

		Fuuro[] fuuros;
		fuuros = m_tehai.getFuuros();
		int fuuroNum;
		fuuroNum = m_tehai.getFuuroNum();
		int type;
		for (int i = 0; i < fuuroNum; i++) {
			type = fuuros[i].getType();
			switch (type) {
			case Fuuro.TYPE_MINSHUN:
				//鳴いた牌をチェック
				checkHai = fuuros[i].getHais();
				id = checkHai[0].getId();
				for(int j =0 ; j < checkId.length ; j++){
					if(id == checkId[j]){
						ikkituukanflg[j] = true;
					}
				}
				break;
			default:
				break;
			}
		}

		//一気通貫が出来ているかどうかチェック
		if(   (ikkituukanflg[0] == true && ikkituukanflg[1] == true && ikkituukanflg[2] == true )
			||(ikkituukanflg[3] == true && ikkituukanflg[4] == true && ikkituukanflg[5] == true )
			||(ikkituukanflg[6] == true && ikkituukanflg[7] == true && ikkituukanflg[8] == true )){
			return true;
		}else{
			return false;
		}
	}

	//三色ができているかどうかの判定に使う補助メソッド
	private static void checkSansyoku(int id , boolean sansyokuflg[][]){
		//萬子、筒子、索子をチェック
		int checkId[] = {ID_WAN_1,ID_PIN_1,ID_SOU_1};
		for(int i =0 ; i < sansyokuflg.length ; i++){
			for(int j = 0; j < sansyokuflg[i].length ; j++){
				if(id == (checkId[i] + j) ){
					sansyokuflg[i][j] = true;
				}
			}
		}
	}

	boolean checkSansyokuDoujun() {
		int id;
		Hai checkHai[];
		boolean sansyokuflg[][]= new boolean[3][9];

		//フラグの初期化
		for(int i = 0 ; i<sansyokuflg.length; i++){
			for (int k = 0; k <sansyokuflg[i].length ; k++){
				sansyokuflg[i][k] = false;
			}
		}

		//手牌の順子をチェック
		for(int i = 0 ; i < m_combi.m_shunNum ; i++){
			id = Hai.noKindToId(m_combi.m_shunNoKinds[i]);
			checkSansyoku(id,sansyokuflg);
		}

		Fuuro[] fuuros;
		fuuros = m_tehai.getFuuros();
		int fuuroNum;
		fuuroNum = m_tehai.getFuuroNum();
		int type;
		for (int i = 0; i < fuuroNum; i++) {
			type = fuuros[i].getType();
			switch (type) {
			case Fuuro.TYPE_MINSHUN:
				//鳴いた牌をチェック
				checkHai = fuuros[i].getHais();
				id = checkHai[0].getId();
				checkSansyoku(id,sansyokuflg);
				break;
			default:
				break;
			}
		}

		//三色同順が出来ているかどうかチェック
		for(int i = 0 ; i < sansyokuflg[0].length ; i++){
			if( (sansyokuflg[0][i] == true) && (sansyokuflg[1][i] == true ) && (sansyokuflg[2][i] == true)){
				return true;
			}
		}
		//出来ていない場合 falseを返却
		return false;
	}

	boolean checkSansyokuDoukou() {
		int id;
		Hai checkHai[];
		boolean sansyokuflg[][]= new boolean[3][9];


		//フラグの初期化
		for(int i = 0 ; i<sansyokuflg.length; i++){
			for (int k = 0; k <sansyokuflg[i].length ; k++){
				sansyokuflg[i][k] = false;
			}
		}

		//手牌の刻子をチェック
		for(int i = 0 ; i < m_combi.m_kouNum ; i++){
			id = Hai.noKindToId(m_combi.m_kouNoKinds[i]);
			checkSansyoku(id,sansyokuflg);
		}
		Fuuro[] fuuros;
		fuuros = m_tehai.getFuuros();
		int fuuroNum;
		fuuroNum = m_tehai.getFuuroNum();
		int type;
		for (int i = 0; i < fuuroNum; i++) {
			type = fuuros[i].getType();
			switch (type) {
			case Fuuro.TYPE_MINKOU:
			case Fuuro.TYPE_DAIMINKAN:
			case Fuuro.TYPE_KAKAN:
			case Fuuro.TYPE_ANKAN:
				//鳴いた牌の明刻をチェック
				//鳴いた牌の明槓をチェック
				//鳴いた牌の暗槓をチェック
				checkHai = fuuros[i].getHais();
				id = checkHai[0].getId();
				checkSansyoku(id,sansyokuflg);
				break;
			default:
				break;
			}
		}

		//三色同刻が出来ているかどうかチェック
		for(int i = 0 ; i < sansyokuflg[0].length ; i++){
			if( (sansyokuflg[0][i] == true) && (sansyokuflg[1][i] == true ) && (sansyokuflg[2][i] == true)){
				return true;
			}
		}

		//出来ていない場合 falseを返却
		return false;
	}

	boolean checkToitoi() {
		Fuuro[] fuuros;
		fuuros = m_tehai.getFuuros();
		int fuuroNum;
		fuuroNum = m_tehai.getFuuroNum();
		int type;
		int minShunNum = 0;
		for (int i = 0; i < fuuroNum; i++) {
			type = fuuros[i].getType();
			switch (type) {
			case Fuuro.TYPE_MINSHUN:
				minShunNum++;
				break;
			default:
				break;
			}
		}
		//手牌に順子がある
		if((m_combi.m_shunNum != 0) || (minShunNum != 0) ){
			return false;
		}else{
			return true;
		}
	}

	boolean checkSanankou() {

		//対々形で鳴きがなければ成立している【ツモ和了りや単騎の場合、四暗刻が優先される）
		if((checkToitoi() == true)
		 &&(nakiflg == false)){
			return true;
		}

		Fuuro[] fuuros;
		fuuros = m_tehai.getFuuros();
		int fuuroNum;
		fuuroNum = m_tehai.getFuuroNum();
		int type;
		int anKanNum = 0;
		for (int i = 0; i < fuuroNum; i++) {
			type = fuuros[i].getType();
			switch (type) {
			case Fuuro.TYPE_ANKAN:
				anKanNum++;
				break;
			default:
				break;
			}
		}

		//暗刻と暗槓の合計が３つではない場合は不成立
		if((m_combi.m_kouNum + anKanNum) != 3){
			return false;
		}

		//ツモ上がりの場合は成立
		if(m_setting.getYakuflg(TUMO.ordinal()) == true){
			return true;
		}
		//ロン上がりの場合、和了った牌と
		else{
			int noKind = m_addHai.getNoKind();
			//ロン上がりで頭待ちの場合は成立
			if(noKind == m_combi.m_atamaNoKind){
				return true;
			}else{
				//和了った牌と刻子になっている牌が同じか確認
				boolean checkflg = false;
				for(int i = 0 ; i < m_combi.m_kouNum ; i++){
					if(noKind == m_combi.m_kouNoKinds[i]){
						checkflg = true;
					}
				}

				//刻子の牌で和了った場合
				if(checkflg == true){
					//字牌ならば不成立
					if(m_addHai.isTsuu() == true){
						return false;
					}else{
						//順子の待ちにもなっていないか確認する
						//　例:11123 で1で和了り  , 45556 の5で和了り
						boolean checkshun = false;
						for(int i = 0 ; i < m_combi.m_shunNum ; i++){
							switch(m_addHai.getNo()){
								case 1:
									if(noKind == m_combi.m_shunNoKinds[i]){
										checkshun = true;
									}
									break;
								case 2:
									if((noKind == m_combi.m_shunNoKinds[i])
									 ||(noKind-1 == m_combi.m_shunNoKinds[i])){
										checkshun = true;
									}
									break;
								case 3:
								case 4:
								case 5:
								case 6:
								case 7:
									if((noKind == m_combi.m_shunNoKinds[i])
										 ||(noKind-1 == m_combi.m_shunNoKinds[i])
										 ||(noKind-2 == m_combi.m_shunNoKinds[i])){
											checkshun = true;
									}
									break;
								case 8:
									if((noKind-1 == m_combi.m_shunNoKinds[i])
										 ||(noKind-2 == m_combi.m_shunNoKinds[i])){
											checkshun = true;
									}
									break;
								case 9:
									if(noKind-2 == m_combi.m_shunNoKinds[i]){
											checkshun = true;
									}
									break;
							}
						}
						//順子の牌でもあった場合は成立
						if(checkshun == true){
							return true;
						}
						//関係ある順子がなかった場合は不成立
						else{
							return false;
						}
					}
				}
				//刻子と関係ない牌で和了った場合は成立
				else{
					return true;
				}
			}
		}
	}

	boolean checkSankantu() {
		int kansnumber = 0;

		Fuuro[] fuuros;
		fuuros = m_tehai.getFuuros();
		int fuuroNum;
		fuuroNum = m_tehai.getFuuroNum();
		int type;
		for (int i = 0; i < fuuroNum; i++) {
			type = fuuros[i].getType();
			switch (type) {
			case Fuuro.TYPE_DAIMINKAN:
			case Fuuro.TYPE_KAKAN:
			case Fuuro.TYPE_ANKAN:
				kansnumber++;
				break;
			default:
				break;
			}
		}
		if(kansnumber == 3){
			return true;
		}else{
			return false;
		}
	}

	boolean checkRyanpeikou() {
		//鳴きが入っている場合は成立しない
		if(nakiflg == true){
			return false;
		}

		//順子が４つである
		if(m_combi.m_shunNum < 4){
			return false;
		}

		//順子の組み合わせを確認する
		if(m_combi.m_shunNoKinds[0] == m_combi.m_shunNoKinds[1]
		&& m_combi.m_shunNoKinds[2] == m_combi.m_shunNoKinds[3]){
			return true;
		}else{
			return false;
		}
	}

	boolean checkHonitu() {
		Hai[] jyunTehai = m_tehai.getJyunTehai();
		Hai checkHai[];

		//萬子、筒子、索子をチェック
		int checkId[] = {KIND_WAN,KIND_PIN,KIND_SOU};

		for (int i = 0 ; i < checkId.length ; i++){
			boolean honituflg = true;
			//純手牌をチェック
			int jyunTehaiLength = m_tehai.getJyunTehaiLength();
			for (int j = 0; j < jyunTehaiLength; j++) {
				//牌が(萬子、筒子、索子)以外もしくは字牌以外
				if ((jyunTehai[j].getKind() != checkId[i]) && (jyunTehai[j].isTsuu() == false)){
					honituflg = false;
				}
			}

			Fuuro[] fuuros;
			fuuros = m_tehai.getFuuros();
			int fuuroNum;
			fuuroNum = m_tehai.getFuuroNum();
			int type;
			for (int j = 0; j < fuuroNum; j++) {
				type = fuuros[j].getType();
				switch (type) {
				case Fuuro.TYPE_MINSHUN:
				case Fuuro.TYPE_MINKOU:
				case Fuuro.TYPE_DAIMINKAN:
				case Fuuro.TYPE_KAKAN:
				case Fuuro.TYPE_ANKAN:
					//明順の牌をチェック
					//明刻の牌をチェック
					//明槓の牌をチェック
					//暗槓の牌をチェック
					checkHai = fuuros[i].getHais();
					//牌が(萬子、筒子、索子)以外もしくは字牌以外
					if ((checkHai[0].getKind() != checkId[i]) && (checkHai[0].isTsuu() == false)){
						honituflg = false;
					}
					break;
				default:
					break;
				}
			}

			//混一が成立している
			if(honituflg == true){
				return true;
			}

		}
		//成立していなけらば不成立
		return false;

	}

	boolean checkJunCyan() {
		Hai checkHais[];
		Hai checkHai;

		//純手牌の刻子をチェック
		for(int i = 0; i < m_combi.m_kouNum ; i++){
			checkHai = new Hai(Hai.noKindToId(m_combi.m_kouNoKinds[i]));
			//字牌があれば不成立
			if( checkHai.isTsuu() == true){
				return false;
			}

			//中張牌ならば不成立
			if(checkHai.isYaochuu() == false ){
				return false;
			}
		}

		//純手牌の順子をチェック
		for(int i = 0; i < m_combi.m_shunNum ; i++){
			checkHai = new Hai(Hai.noKindToId(m_combi.m_shunNoKinds[i]));
			//字牌があれば不成立
			if( checkHai.isTsuu() == true){
				return false;
			}

			//数牌の場合は数字をチェック
			if ((checkHai.getNo() > NO_1) && (checkHai.getNo() < NO_7)){
				return false;
			}
		}

		//純手牌の頭をチェック
		checkHai = new Hai(Hai.noKindToId(m_combi.m_atamaNoKind));
		//字牌があれば不成立
		if( checkHai.isTsuu() == true){
			return false;
		}
		//中張牌ならば不成立
		if(checkHai.isYaochuu() == false ){
			return false;
		}

		Fuuro[] fuuros;
		fuuros = m_tehai.getFuuros();
		int fuuroNum;
		fuuroNum = m_tehai.getFuuroNum();
		int type;
		for (int i = 0; i < fuuroNum; i++) {
			type = fuuros[i].getType();
			switch (type) {
			case Fuuro.TYPE_MINSHUN:
				//明順の牌をチェック
				checkHais = fuuros[i].getHais();
				//123 と　789 以外の順子があれば不成立
				if ((checkHais[0].getNo() > NO_1) && (checkHais[0].getNo()< NO_7)){
					return false;
				}
				break;
			case Fuuro.TYPE_MINKOU:
				//明刻の牌をチェック
				checkHais = fuuros[i].getHais();
				//字牌があれば不成立
				if( checkHais[0].isTsuu() == true){
					return false;
				}
				//中張牌ならば不成立
				if(checkHais[0].isYaochuu() == false ){
					return false;
				}
				break;
			case Fuuro.TYPE_DAIMINKAN:
			case Fuuro.TYPE_KAKAN:
				//明槓の牌をチェック
				checkHais = fuuros[i].getHais();
				//字牌があれば不成立
				if( checkHais[0].isTsuu() == true){
					return false;
				}
				//中張牌ならば不成立
				if(checkHais[0].isYaochuu() == false ){
					return false;
				}
				break;
			case Fuuro.TYPE_ANKAN:
				//暗槓の牌をチェック
				checkHais = fuuros[i].getHais();
				//字牌があれば不成立
				if( checkHais[0].isTsuu() == true){
					return false;
				}
				//中張牌ならば不成立
				if(checkHais[0].isYaochuu() == false ){
					return false;
				}
				break;
			default:
				break;
			}
		}

		return true;
	}

	boolean checkSyousangen() {
		//三元牌役が成立している個数を調べる
		int countSangen = 0;
		//白が刻子
		if(checkHaku() == true){
			countSangen++;
		}
		//発が刻子
		if(checkHatu() == true){
			countSangen++;
		}
		//中が刻子
		if(checkCyun() == true){
			countSangen++;
		}
		//頭が三元牌 かつ、三元牌役が2つ成立
		//Hai atamaHai = new Hai(combi.m_atamaNoKind);
		//if((atamaHai.getKind() == KIND_SANGEN) && (countSangen == 2)){
		if(((m_combi.m_atamaNoKind & KIND_SANGEN) == KIND_SANGEN) && (countSangen == 2)){
			return true;
		}

		return false;
	}

	boolean checkHonroutou() {
		//トイトイが成立している
		if(checkToitoi() == false){
			return false;
		}

		//チャンタが成立している
		if(checkCyanta() == true){
			return true;
		}else{
			return false;
		}
	}

	boolean checkHonroutouChiitoitsu() {
		Hai[] jyunTehai = m_tehai.getJyunTehai();

		//純手牌をチェック
		int jyunTehaiLength = m_tehai.getJyunTehaiLength();
		for (int i = 0; i < jyunTehaiLength; i++) {
			//１９字牌ならば成立
			if (jyunTehai[i].isYaochuu() == false){
				return false;
			}
		}

		// 追加牌をチェック

		//１９字牌ならば成立
		if (m_addHai.isYaochuu() == false){
			return false;
		}

		return true;
	}

	boolean checkRenhou() {
		if(m_setting.getYakuflg(RENHOU.ordinal())){
			return true;
		}else{
			return false;
		}
	}

	boolean checkTinitu() {
		Hai[] jyunTehai = m_tehai.getJyunTehai();
		Hai checkHai[];

		//萬子、筒子、索子をチェック
		int checkId[] = {KIND_WAN,KIND_PIN,KIND_SOU};

		for (int i = 0 ; i < checkId.length ; i++){
			boolean Tinituflg = true;
			//純手牌をチェック
			int jyunTehaiLength = m_tehai.getJyunTehaiLength();
			for (int j = 0; j < jyunTehaiLength; j++) {
				//牌が(萬子、筒子、索子)以外
				if (jyunTehai[j].getKind() != checkId[i]){
					Tinituflg = false;
				}
			}
			Fuuro[] fuuros;
			fuuros = m_tehai.getFuuros();
			int fuuroNum;
			fuuroNum = m_tehai.getFuuroNum();
			for (int j = 0; j < fuuroNum; j++) {
				checkHai = fuuros[j].getHais();
				//牌が(萬子、筒子、索子)以外
				if (checkHai[0].getKind() != checkId[i]){
					Tinituflg = false;
					break;
				}
			}

			//清一が成立している
			if(Tinituflg == true){
				return true;
			}

		}
		//成立していなければ不成立
		return false;

	}

	boolean checkSuuankou() {
		Fuuro[] fuuros;
		fuuros = m_tehai.getFuuros();
		int fuuroNum;
		fuuroNum = m_tehai.getFuuroNum();
		int type;
		int anKanNum = 0;
		for (int i = 0; i < fuuroNum; i++) {
			type = fuuros[i].getType();
			switch (type) {
			case Fuuro.TYPE_ANKAN:
				anKanNum++;
				break;
			default:
				break;
			}
		}

		//手牌の暗刻が4つ
		if((m_combi.m_kouNum + anKanNum) != 4){
			return false;
		}else{
			//ツモ和了りの場合は成立
			if(m_setting.getYakuflg(TUMO.ordinal())){
				return true;
			}
			//ロン和了りの場合
			else{
				//頭待ちならば成立 (四暗刻単騎待ち)
				if(m_addHai.getNoKind() == m_combi.m_atamaNoKind){
					return true;
				}else{
					return false;
				}
			}
		}
	}

	boolean checkSuukantu() {
		int kansnumber = 0;
		Fuuro[] fuuros;
		fuuros = m_tehai.getFuuros();
		int fuuroNum;
		fuuroNum = m_tehai.getFuuroNum();
		int type;
		for (int i = 0; i < fuuroNum; i++) {
			type = fuuros[i].getType();
			switch (type) {
			case Fuuro.TYPE_DAIMINKAN:
			case Fuuro.TYPE_KAKAN:
			case Fuuro.TYPE_ANKAN:
				kansnumber++;
				break;
			default:
				break;
			}
		}
		if(kansnumber == 4){
			return true;
		}else{
			return false;
		}
	}

	boolean checkDaisangen() {
		//三元牌役が成立している個数を調べる
		int countSangen = 0;
		//白が刻子
		if(checkHaku() == true){
			countSangen++;
		}
		//発が刻子
		if(checkHatu() == true){
			countSangen++;
		}
		//中が刻子
		if(checkCyun() == true){
			countSangen++;
		}
		//３元牌が３つ揃っている
		if(countSangen == 3){
			return true;
		}else{
			return false;
		}
	}

	boolean checkTenhou() {
		return m_setting.getYakuflg(TENHOU.ordinal());
	}

	boolean checkTihou() {
		return m_setting.getYakuflg(TIHOU.ordinal());
	}

	boolean checkSyousuushi() {
		//風牌役が成立している個数を調べる
		int countFon = 0;
		//東が刻子
		if(checkYakuHai(m_tehai,m_combi,ID_TON) == true){
			countFon++;
		}
		//南が刻子
		if(checkYakuHai(m_tehai,m_combi,ID_NAN) == true){
			countFon++;
		}
		//西が刻子
		if(checkYakuHai(m_tehai,m_combi,ID_SHA) == true){
			countFon++;
		}
		//北が刻子
		if(checkYakuHai(m_tehai,m_combi,ID_PE) == true){
			countFon++;
		}

		//頭が風牌 かつ、風牌役が3つ成立
		//Hai atamaHai = new Hai(combi.m_atamaNoKind);
		//if((atamaHai.getKind() == KIND_FON) && (countFon == 3)){
		if(((m_combi.m_atamaNoKind & KIND_FON) == KIND_FON) && (countFon == 3)){
			return true;
		}else{
			return false;
		}
	}

	boolean checkDaisuushi() {
		//風牌役が成立している個数を調べる
		int countFon = 0;
		//東が刻子
		if(checkYakuHai(m_tehai,m_combi,ID_TON) == true){
			countFon++;
		}
		//南が刻子
		if(checkYakuHai(m_tehai,m_combi,ID_NAN) == true){
			countFon++;
		}
		//西が刻子
		if(checkYakuHai(m_tehai,m_combi,ID_SHA) == true){
			countFon++;
		}
		//北が刻子
		if(checkYakuHai(m_tehai,m_combi,ID_PE) == true){
			countFon++;
		}
			//風牌役が4つ成立
		if(countFon == 4){
			return true;
		}else{
			return false;
		}
	}

	boolean checkTuuisou() {
		Hai[] jyunTehai = m_tehai.getJyunTehai();
		Hai checkHai[];

		//順子があるかどうか確認
		if(checkToitoi() == false){
			return false;
		}

		//純手牌をチェック
		int jyunTehaiLength = m_tehai.getJyunTehaiLength();
		for (int j = 0; j < jyunTehaiLength; j++) {
			//牌が字牌ではない
			if (jyunTehai[j].isTsuu() == false){
				return false;
			}
		}

		Fuuro[] fuuros;
		fuuros = m_tehai.getFuuros();
		int fuuroNum;
		fuuroNum = m_tehai.getFuuroNum();
		for (int i = 0; i < fuuroNum; i++) {
			checkHai = fuuros[i].getHais();
			//牌が字牌ではない
			if (checkHai[0].isTsuu() == false){
				return false;
			}
		}

		return true;
	}

	boolean checkChinroutou() {
		//順子があるかどうか確認
		if(checkToitoi() == false){
			return false;
		}

		//順子なしでジュンチャンが成立しているか（1と9のみで作成）
		if(checkJunCyan() == false){
			return false;
		}

		return true;

	}

	boolean checkRyuuisou() {
		int checkId[] = {ID_SOU_2,ID_SOU_3,ID_SOU_4,ID_SOU_6,ID_SOU_8,ID_HATSU};
		int id;
		boolean ryuuisouflg = false;
		Hai[] jyunTehai = m_tehai.getJyunTehai();
		Hai checkHai[];

		//純手牌をチェック
		int jyunTehaiLength = m_tehai.getJyunTehaiLength();
		for (int i = 0; i < jyunTehaiLength; i++) {
			id = jyunTehai[i].getId();
			ryuuisouflg = false;
			for(int j = 0 ; j < checkId.length ; j++){
				//緑一色に使用できる牌だった
				if(id == checkId[j]){
					ryuuisouflg = true;
				}
			}
			//該当する牌ではなかった
			if(ryuuisouflg == false){
				return false;
			}
		}

		Fuuro[] fuuros;
		fuuros = m_tehai.getFuuros();
		int fuuroNum;
		fuuroNum = m_tehai.getFuuroNum();
		int type;
		for (int i = 0; i < fuuroNum; i++) {
			type = fuuros[i].getType();
			switch (type) {
			case Fuuro.TYPE_MINSHUN:
				//明順の牌をチェック
				checkHai = fuuros[i].getHais();
				id = checkHai[0].getId();
				//索子の2,3,4以外の順子があった場合不成立
				if (id != ID_SOU_2){
					return false;
				}
				break;
			case Fuuro.TYPE_MINKOU:
			case Fuuro.TYPE_DAIMINKAN:
			case Fuuro.TYPE_KAKAN:
			case Fuuro.TYPE_ANKAN:
				checkHai = fuuros[i].getHais();
				id = checkHai[0].getId();
				ryuuisouflg = false;
				for(int j = 0 ; j < checkId.length ; j++){
					//緑一色に使用できる牌だった
					if(id == checkId[j]){
						ryuuisouflg = true;
					}
				}
				//該当する牌ではなかった
				if(ryuuisouflg == false){
					return false;
				}
				break;
			default:
				break;
			}
		}

		//条件に該当した
		return true;
	}

	boolean checkCyuurennpoutou() {
		//牌の数を調べるための配列 (0番地は使用しない）
		int countNumber[] = {0,0,0,0,0,0,0,0,0,0};
		Hai checkHai[] = new Hai[JYUN_TEHAI_LENGTH_MAX];

		//鳴きがある場合は成立しない
		if(nakiflg == true){
			return false;
		}
		//手牌が清一になっていない場合も成立しない
		if(checkTinitu() == false){
			return false;
		}

		//手牌をコピーする
		checkHai = m_tehai.getJyunTehai();

		//手牌にある牌の番号を調べる
		for(int i = 0 ; i < m_tehai.getJyunTehaiLength() ; i++){
			//数字の番号をインクリメントする
			countNumber[checkHai[i].getNo()]++;
		}

		//九蓮宝燈になっているか調べる（1と9が３枚以上 2～8が１枚以上)
		if(( countNumber[1] >= 3)
		&& ( countNumber[2] >= 1)
		&& ( countNumber[3] >= 1)
		&& ( countNumber[4] >= 1)
		&& ( countNumber[5] >= 1)
		&& ( countNumber[6] >= 1)
		&& ( countNumber[7] >= 1)
		&& ( countNumber[8] >= 1)
		&& ( countNumber[9] >= 3)){
			return true;
		}
		return false;
	}

	boolean checkKokushi() {
		//牌の数を調べるための配列 (0番地は使用しない）
		int checkId[] = {ID_WAN_1,ID_WAN_9,ID_PIN_1,ID_PIN_9,ID_SOU_1,ID_SOU_9,
								ID_TON,ID_NAN,ID_SHA,ID_PE,ID_HAKU,ID_HATSU,ID_CHUN};
		int countHai[] = {0,0,0,0,0,0,0,0,0,0,0,0,0};
		Hai checkHai[] = new Hai[JYUN_TEHAI_LENGTH_MAX];

		//鳴きがある場合は成立しない
		if(nakiflg == true){
			return false;
		}

		//手牌をコピーする
		checkHai = m_tehai.getJyunTehai();

		//手牌のIDを検索する
		for(int i = 0 ; i < m_tehai.getJyunTehaiLength() ; i++){
			for(int j = 0 ; j < checkId.length ; j++){
				if(checkHai[i].getId() == checkId[j]){
					countHai[j]++;
				}
			}
		}

		for(int j = 0 ; j < checkId.length ; j++){
			if(m_addHai.getId() == checkId[j]){
				countHai[j]++;
			}
		}

		boolean atama = false;
		//国士無双が成立しているか調べる(手牌がすべて1.9字牌 すべての１,９字牌を持っている）
		for(int i = 0 ; i < countHai.length ; i++){
			//0枚の牌があれば不成立
			if(countHai[i] == 0){
				return false;
			}
			if(countHai[i] == 2){
				atama = true;
			}
		}
		//条件を満たしていれば成立
		if (atama) {
			return true;
		} else {
			return false;
		}
	}

	boolean checkDora() {
		int doraCount = 0;

		Hai[] doraHais = m_setting.getDoraHais();

		Hai[] jyunTehai = m_tehai.getJyunTehai();
		int jyunTehaiLength = m_tehai.getJyunTehaiLength();
		for (int i = 0; i < doraHais.length; i++) {
			for (int j = 0; j < jyunTehaiLength; j++) {
				if (doraHais[i].getNextHaiId() == jyunTehai[j].getId()) {
					doraCount++;
				}
			}
		}

		for (int i = 0; i < doraHais.length; i++) {
			if (doraHais[i].getNextHaiId() == m_addHai.getId()) {
				doraCount++;
				break;
			}
		}

		Fuuro[] fuuros = m_tehai.getFuuros();
		int fuuroNum = m_tehai.getFuuroNum();
		int type;
		for (int i = 0; i < fuuroNum; i++) {
			type = fuuros[i].getType();
			switch (type) {
			case Fuuro.TYPE_MINKOU:
				for (int j = 0; j < doraHais.length; j++) {
					if (doraHais[j].getNextHaiId() == fuuros[i].getHais()[0].getId()) {
						doraCount += 3;
						break;
					}
				}
				break;
			case Fuuro.TYPE_DAIMINKAN:
			case Fuuro.TYPE_KAKAN:
			case Fuuro.TYPE_ANKAN:
				for (int j = 0; j < doraHais.length; j++) {
					if (doraHais[j].getNextHaiId() == fuuros[i].getHais()[0].getId()) {
						doraCount += 4;
						break;
					}
				}
				break;
			case Fuuro.TYPE_MINSHUN:
				SEARCHLOOP: for (int j = 0; j < doraHais.length; j++) {
					for (int k = 0; k < 3; k++) {
						if (doraHais[j].getNextHaiId() == fuuros[i].getHais()[k].getId()) {
							doraCount += 1;
							break SEARCHLOOP;
						}
					}
				}
				break;
			default:
				break;
			}
		}

		if (doraCount > 0) {
			m_doraCount = doraCount;
			return true;
		}

		return false;
	}
}
