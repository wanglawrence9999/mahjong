package jp.sourceforge.andjong;
import static jp.sourceforge.andjong.Hai.*;
import static jp.sourceforge.andjong.Tehai.JYUNTEHAI_MAX;
import jp.sourceforge.andjong.Tehai.Combi;

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
	
	Tehai tehai;
	Hai addHai;
	Combi combi;
	Info info;
	YakuHantei yakuhantei[];
	boolean nakiflg = false;

	/**
	 * Yakuクラスのコンストラクタ。
	 * 引数を保存し、YakuHanteiクラスの配列を作成する。
	 * @param tehai 手牌　addHai 上がった牌  combi 手牌 の組み合わせ info 情報
	 */
	Yaku(Tehai tehai, Hai addHai, Combi combi,Info info){
		this.tehai = tehai;
		this.addHai = addHai;
		this.combi  = combi;
		this.info   = info;
		//鳴きがある場合
		if((tehai.getMinkansLength() != 0)
		|| (tehai.getMinkousLength() != 0)
		|| (tehai.getMinshunsLength() != 0)
		){
			nakiflg = true;
		}
		
		YakuHantei buffer[] = {new CheckTanyao(),
							   new CheckPinfu(),
							   new CheckIpeikou(),
							   new CheckReach(),
							   new CheckIppatu(),
							   new CheckTon(),
							   new CheckNan(),
							   new CheckSya(),
							   new CheckPei(),
							   new CheckHaku(),
							   new CheckHatu(),
							   new CheckCyun(),
							   new CheckHaitei(),
							   new CheckRinsyan(),
							   new CheckCyankan(),
							   new CheckDoubleReach(),
							   new CheckTeetoitu(),
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
							   new CheckKokushi()
		};
		
		yakuhantei = buffer;

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

		return hanSuu;
	}
	
	/**
	 * 成立している役の名前を取得します。
	 * 
	 * @return 成立している役の名前の配列
	 */
	String[] getYakuName(){
		int count = 0;
		
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
				yakuName[count] = yakuhantei[i].getYakuName() + " " + yakuhantei[i].getHanSuu() + "翻";
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

	private class CheckTon extends YakuHantei{
		CheckTon(){
			hantei = checkTon();
			if(info.getJikaze() == JIKAZE_TON){
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

	
	boolean checkTanyao() {
		int id;
		Hai[] jyunTehai = tehai.getJyunTehai();
		Hai checkHai[][]; 

		//純手牌をチェック
		int jyunTehaiLength = tehai.getJyunTehaiLength();
		for (int i = 0; i < jyunTehaiLength; i++) {
			id = jyunTehai[i].getOldId();
			//数牌でなければ不成立
			if ((id & OLD_KIND_SHUU) == 0)
				return false;
			id &= OLD_KIND_MASK;
			//数が1か9ならば不成立
			if ((id == 1) || (id == 9))
				return false;
		}
		
		//明順の牌をチェック
		for(int i = 0; i < tehai.getMinshunsLength(); i++){
			checkHai = tehai.getMinshuns();
			id = checkHai[i][0].getOldId();
			id &= OLD_KIND_MASK;
			//123 と　789 の順子があれば不成立
			if ((id == 1) || (id == 7))
				return false;
		}
		
		//明刻の牌をチェック
		for(int i = 0; i < tehai.getMinkousLength(); i++){
			checkHai = tehai.getMinkous();
			id = checkHai[i][0].getOldId();
			if ((id & OLD_KIND_SHUU) == 0)
				return false;
			id &= OLD_KIND_MASK;
			if ((id == 1) || (id == 9))
				return false;
		}
		
		//明槓の牌をチェック
		for(int i = 0; i < tehai.getMinkansLength(); i++){
			checkHai = tehai.getMinkans();
			id = checkHai[i][0].getOldId();
			if ((id & OLD_KIND_SHUU) == 0)
				return false;
			id &= OLD_KIND_MASK;
			if ((id == 1) || (id == 9))
				return false;
		}
		
		//暗槓の牌をチェック
		for(int i = 0; i < tehai.getAnkansLength(); i++){
			checkHai = tehai.getAnkans();
			id = checkHai[i][0].getOldId();
			if ((id & OLD_KIND_SHUU) == 0)
				return false;
			id &= OLD_KIND_MASK;
			if ((id == 1) || (id == 9))
				return false;
		}
		
		return true;
	}

	boolean checkPinfu() {
		int id;
		//鳴きが入っている場合は成立しない
		if(nakiflg == true){
			return false;
		}
		
		//面子が順子だけではない
		if(combi.shunCount != 4){
			return false;
		}
		
		//頭が三元牌 
		id = combi.atamaId;
		if( (id & OLD_KIND_SANGEN) != 0){
			return false;
		}
		
		//頭が場風
		if( (id & OLD_KIND_TON) != 0){
			return false;
		}
		
		//頭が自風
		if(((id & OLD_KIND_FON) != 0) && 
				(id & OLD_KIND_MASK) == (info.getJikaze()+1)){
			return false;
		}		

		int addHaiid = addHai.getOldId();
		//字牌の頭待ちの場合は不成立
		if((addHaiid & OLD_KIND_TSUU) != 0){
			return false;
		}

		//待ちが両面待ちか判定
		boolean ryanmenflg = false;
		//上がり牌の数をチェックして場合分け
		switch(addHaiid & OLD_KIND_MASK){
			//上がり牌が1,2,3の場合は123,234,345の順子ができているかどうかチェック
			case 1:
			case 2:
			case 3:
				for(int i = 0 ; i < combi.shunCount ; i++){
					if(addHaiid == combi.shunIds[i]){
						ryanmenflg = true;
					}
				}
				break;
			//上がり牌が4,5,6の場合は456か234,567か345,678か456の順子ができているかどうかチェック
			case 4:
			case 5:
			case 6:
				for(int i = 0 ; i < combi.shunCount ; i++){
					if((addHaiid == combi.shunIds[i])
					 ||(addHaiid-2 == combi.shunIds[i])){
						ryanmenflg = true;
					}
				}
				break;
			//上がり牌が7,8,9の場合は567,678,789の順子ができているかどうかチェック
			case 7:
			case 8:
			case 9:
				for(int i = 0 ; i < combi.shunCount ; i++){
					if(addHaiid-2 == (combi.shunIds[i])){
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
		for (int i = 0; i < combi.shunCount -1; i++) {
			if(combi.shunIds[i] == combi.shunIds[i+1]){
				return true;
			}
		}
		return false;
	}
	
	
	boolean checkReach() {
		return info.isReach(info.getJikaze());
	}
	
	boolean checkIppatu() {
		//TODO 一発の判定
		return false;
	}

	boolean checkTumo() {
		//鳴きが入っている場合は成立しない
		if(nakiflg == true){
			return false;
		}
		//TODO ツモ上がりかどうかの判定
		return true;
	}


	//役牌ができているかどうかの判定に使う補助メソッド
	private boolean checkYakuHai(Tehai tehai, Combi combi , int yakuHaiId) {
		int id;
		Hai checkHai[][]; 

		//純手牌をチェック
		for(int i = 0; i < combi.kouCount ; i++){
			//IDと役牌のIDをチェック
			if( combi.kouIds[i] == yakuHaiId ){
				return true;
			}
		}
		
		//明刻の牌をチェック
		for(int i = 0; i < tehai.getMinkousLength(); i++){
			checkHai = tehai.getMinkous();
			id = checkHai[i][0].getOldId();
			//IDと役牌のIDをチェック
			if( id == yakuHaiId ){
				return true;
			}
		}
		
		//明槓の牌をチェック
		for(int i = 0; i < tehai.getMinkansLength(); i++){
			checkHai = tehai.getMinkans();
			id = checkHai[i][0].getOldId();
			//IDと役牌のIDをチェック
			if( id == yakuHaiId ){
				return true;
			}
		}
		//暗槓の牌をチェック
		for(int i = 0; i < tehai.getAnkansLength(); i++){
			checkHai = tehai.getAnkans();
			id = checkHai[i][0].getOldId();
			//IDと役牌のIDをチェック
			if( id == yakuHaiId ){
				return true;
			}
		}
		return false;
	}
	
	boolean checkTon() {
		return checkYakuHai(tehai,combi,OLD_KIND_TON);
	}

	boolean checkNan() {
		if(info.getJikaze() == JIKAZE_NAN){
			return checkYakuHai(tehai,combi,OLD_KIND_NAN);
		}else{
			return false;
		}
	}

	boolean checkSya() {
		if(info.getJikaze() == JIKAZE_SYA){
			return checkYakuHai(tehai,combi,OLD_KIND_NAN);
		}else{
			return false;
		}
	}

	boolean checkPei() {
		if(info.getJikaze() == JIKAZE_PEI){
			return checkYakuHai(tehai,combi,OLD_KIND_NAN);
		}else{
			return false;
		}
	}

	boolean checkHaku() {
		return checkYakuHai(tehai,combi,OLD_KIND_HAKU);
	}

	boolean checkHatu() {
		return checkYakuHai(tehai,combi,OLD_KIND_HATU);
	}

	boolean checkCyun() {
		return checkYakuHai(tehai,combi,OLD_KIND_CYUN);
	}
	
	//TODO 特殊な役は後回し

	boolean checkHaitei() {
		//TODO　海底摸月
		return false;
	}
	
	boolean checkRinsyan() {
		//TODO 嶺上開花
		return false;
	}
	boolean checkCyankan() {
		//TODO 槍槓
		return false;
	}
	
	boolean checkDoubleReach() {
		//TODO ダブルリーチ
		return false;
	}
	
	boolean checkTeetoitu() {
		//鳴きが入っている場合は成立しない
		if(nakiflg == true){
			return false;
		}
		//TODO 七対子
		return false;
	}
	
	boolean checkCyanta() {
		int id;
		Hai checkHai[][]; 

		//純手牌の刻子をチェック
		for(int i = 0; i < combi.kouCount ; i++){
			id = combi.kouIds[i];
			//数牌の場合は数字をチェック
			if ((id & OLD_KIND_SHUU) != 0){
				id &= OLD_KIND_MASK;
				if ((id > 1) && (id < 9))
					return false;
			}
		}
		
		//純手牌の順子をチェック
		for(int i = 0; i < combi.shunCount ; i++){
			id = combi.shunIds[i];
			//数牌の場合は数字をチェック
			if ((id & OLD_KIND_SHUU) != 0){
				id &= OLD_KIND_MASK;
				if ((id > 1) && (id < 7))
					return false;
			}			
		}
		
		//純手牌の頭をチェック
		id = combi.atamaId;
		if ((id & OLD_KIND_SHUU) != 0){
			id &= OLD_KIND_MASK;
			if ((id > 1) && (id < 9))
				return false;
		}
		
		//明順の牌をチェック
		for(int i = 0; i < tehai.getMinshunsLength(); i++){
			checkHai = tehai.getMinshuns();
			id = checkHai[i][0].getOldId();
			id &= OLD_KIND_MASK;
			//123 と　789 以外の順子があれば不成立
			if ((id > 1) && (id < 7))
				return false;
		}
		
		//明刻の牌をチェック
		for(int i = 0; i < tehai.getMinkousLength(); i++){
			checkHai = tehai.getMinkous();
			id = checkHai[i][0].getOldId();
			//数牌の場合は数字をチェック
			if ((id & OLD_KIND_SHUU) != 0){
				id &= OLD_KIND_MASK;
				if ((id > 1) && (id < 9))
					return false;
			}
		}
		
		//明槓の牌をチェック
		for(int i = 0; i < tehai.getMinkansLength(); i++){
			checkHai = tehai.getMinkans();
			id = checkHai[i][0].getOldId();
			//数牌の場合は数字をチェック
			if ((id & OLD_KIND_SHUU) != 0){
				id &= OLD_KIND_MASK;
				if ((id > 1) && (id < 9))
					return false;
			}
		}
		
		//暗槓の牌をチェック
		for(int i = 0; i < tehai.getAnkansLength(); i++){
			checkHai = tehai.getAnkans();
			id = checkHai[i][0].getOldId();
			//数牌の場合は数字をチェック
			if ((id & OLD_KIND_SHUU) != 0){
				id &= OLD_KIND_MASK;
				if ((id > 1) && (id < 9))
					return false;
			}
		}
		
		return true;
	}

	boolean checkIkkituukan() {
		int id;
		Hai checkHai[][]; 
		boolean ikkituukanflg[]= {false,false,false,false,false,false,false,false,false};
		//萬子、筒子、索子の1,4,7をチェック
		int checkId[] = {OLD_KIND_WAN+1,OLD_KIND_WAN+4,OLD_KIND_WAN+7,OLD_KIND_PIN+1,OLD_KIND_PIN+4,OLD_KIND_PIN+7,OLD_KIND_SOU+1,OLD_KIND_SOU+4,OLD_KIND_SOU+7};
		
		//手牌の順子をチェック
		for(int i = 0 ; i < combi.shunCount ; i++){
			id = combi.shunIds[i];
			for(int j =0 ; j < checkId.length ; j++){
				if(id == checkId[j]){
					ikkituukanflg[j] = true;
				}
			}
		}
		
		//鳴いた牌をチェック
		for(int i = 0 ; i < tehai.getMinshunsLength() ; i++){
			checkHai = tehai.getMinshuns();
			id = checkHai[i][0].getOldId();
			for(int j =0 ; j < checkId.length ; j++){
				if(id == checkId[j]){
					ikkituukanflg[j] = true;
				}
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
		int checkId[] = {OLD_KIND_WAN,OLD_KIND_PIN,OLD_KIND_SOU};
		for(int i =0 ; i < sansyokuflg.length ; i++){
			for(int j = 0; j < sansyokuflg[i].length ; j++){
				if(id == (checkId[i] + j+1) ){
					sansyokuflg[i][j] = true;
				}
			}
		}		
	}
	
	boolean checkSansyokuDoujun() {
		int id;
		Hai checkHai[][]; 
		boolean sansyokuflg[][]= new boolean[3][9];
		
		
		//フラグの初期化
		for(int i = 0 ; i<sansyokuflg.length; i++){
			for (int k = 0; k <sansyokuflg[i].length ; k++){
				sansyokuflg[i][k] = false;
			}
		}
		
		//手牌の順子をチェック
		for(int i = 0 ; i < combi.shunCount ; i++){
			id = combi.shunIds[i];
			checkSansyoku(id,sansyokuflg);
		}
		
		//鳴いた牌をチェック
		for(int i = 0 ; i < tehai.getMinshunsLength() ; i++){
			checkHai = tehai.getMinshuns();
			id = checkHai[i][0].getOldId();
			checkSansyoku(id,sansyokuflg);
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
		Hai checkHai[][]; 
		boolean sansyokuflg[][]= new boolean[3][9];
		
		
		//フラグの初期化
		for(int i = 0 ; i<sansyokuflg.length; i++){
			for (int k = 0; k <sansyokuflg[i].length ; k++){
				sansyokuflg[i][k] = false;
			}
		}
		
		//手牌の刻子をチェック
		for(int i = 0 ; i < combi.kouCount ; i++){
			id = combi.kouIds[i];
			checkSansyoku(id,sansyokuflg);
		}
		
		//鳴いた牌の明刻をチェック
		for(int i = 0 ; i < tehai.getMinkousLength() ; i++){
			checkHai = tehai.getMinkous();
			id = checkHai[i][0].getOldId();
			checkSansyoku(id,sansyokuflg);
		}
		
		//鳴いた牌の明槓をチェック
		for(int i = 0 ; i < tehai.getMinkansLength() ; i++){
			checkHai = tehai.getMinkans();
			id = checkHai[i][0].getOldId();
			checkSansyoku(id,sansyokuflg);
		}

		//鳴いた牌の暗槓をチェック
		for(int i = 0 ; i < tehai.getAnkansLength() ; i++){
			checkHai = tehai.getAnkans();
			id = checkHai[i][0].getOldId();
			checkSansyoku(id,sansyokuflg);
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
	
	boolean checkToitoi() {
		//手牌に順子がある
		if((combi.shunCount != 0) || (tehai.getMinshunsLength() != 0) ){
			return false;
		}else{
			return true;
		}
	}
	
	boolean checkSanankou() {
		//手牌の暗刻が３つ
		//TODO 上がった際が出上がりかどうかの判定も必要？
		if(combi.kouCount == 3){
			return true;
		}else{
			return false;
		}
	}
	
	boolean checkSankantu() {
		int kansnumber = 0;
		kansnumber += tehai.getAnkansLength();
		kansnumber += tehai.getMinkansLength();
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
		if(combi.shunCount < 4){
			return false;
		}
		
		//順子の組み合わせを確認する
		if(combi.shunIds[0] == combi.shunIds[1]
		&& combi.shunIds[2] == combi.shunIds[3]){
			return true;
		}else{
			return false;
		}
	}
	
	boolean checkHonitu() {
		int id;
		Hai[] jyunTehai = tehai.getJyunTehai();
		Hai checkHai[][]; 

		//萬子、筒子、索子をチェック
		int checkId[] = {OLD_KIND_WAN,OLD_KIND_PIN,OLD_KIND_SOU};

		for (int i = 0 ; i < checkId.length ; i++){
			boolean honituflg = true;
			//純手牌をチェック
			int jyunTehaiLength = tehai.getJyunTehaiLength();
			for (int j = 0; j < jyunTehaiLength; j++) {
				id = jyunTehai[j].getOldId();
				//牌が(萬子、筒子、索子)以外もしくは字牌以外
				if (((id & checkId[i]) == 0) && ((id & OLD_KIND_TSUU) == 0) ){
					honituflg = false;
				}
			}
			
			//明順の牌をチェック
			for(int j = 0; j < tehai.getMinshunsLength(); j++){
				checkHai = tehai.getMinshuns();
				id = checkHai[j][0].getOldId();
				//牌が(萬子、筒子、索子)以外もしくは字牌以外
				if (((id & checkId[i]) == 0) && ((id & OLD_KIND_TSUU) == 0) ){
					honituflg = false;
				}
			}
			
			//明刻の牌をチェック
			for(int j = 0; j < tehai.getMinkousLength(); j++){
				checkHai = tehai.getMinkous();
				id = checkHai[j][0].getOldId();
				//牌が(萬子、筒子、索子)以外もしくは字牌以外
				if (((id & checkId[i]) == 0) && ((id & OLD_KIND_TSUU) == 0) ){
					honituflg = false;
				}
			}
			
			//明槓の牌をチェック
			for(int j = 0; j < tehai.getMinkansLength(); j++){
				checkHai = tehai.getMinkans();
				id = checkHai[j][0].getOldId();
				//牌が(萬子、筒子、索子)以外もしくは字牌以外
				if (((id & checkId[i]) == 0) && ((id & OLD_KIND_TSUU) == 0) ){
					honituflg = false;
				}
			}
			
			//暗槓の牌をチェック
			for(int j = 0; j < tehai.getAnkansLength(); j++){
				checkHai = tehai.getAnkans();
				id = checkHai[j][0].getOldId();
				//牌が(萬子、筒子、索子)以外もしくは字牌以外
				if (((id & checkId[i]) == 0) && ((id & OLD_KIND_TSUU) == 0) ){
					honituflg = false;
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
		
		int id;
		Hai checkHai[][]; 
		
		//純手牌の刻子をチェック
		for(int i = 0; i < combi.kouCount ; i++){
			id = combi.kouIds[i];
			//字牌があれば不成立
			if( (id & OLD_KIND_TSUU) != 0){
				return false;
			}

			//数牌の場合は数字をチェック
			if ((id & OLD_KIND_SHUU) != 0){
				id &= OLD_KIND_MASK;
				if ((id > 1) && (id < 9))
					return false;
			}
		}
		
		//純手牌の順子をチェック
		for(int i = 0; i < combi.shunCount ; i++){
			id = combi.shunIds[i];
			//字牌があれば不成立
			if( (id & OLD_KIND_TSUU) != 0){
				return false;
			}
			//数牌の場合は数字をチェック
			if ((id & OLD_KIND_SHUU) != 0){
				id &= OLD_KIND_MASK;
				if ((id > 1) && (id < 7))
					return false;
			}			
		}
		
		//純手牌の頭をチェック
		id = combi.atamaId;
		//字牌があれば不成立
		if( (id & OLD_KIND_TSUU) != 0){
			return false;
		}
		if ((id & OLD_KIND_SHUU) != 0){
			id &= OLD_KIND_MASK;
			if ((id > 1) && (id < 9))
				return false;
		}
		
		//明順の牌をチェック
		for(int i = 0; i < tehai.getMinshunsLength(); i++){
			checkHai = tehai.getMinshuns();
			id = checkHai[i][0].getOldId();
			id &= OLD_KIND_MASK;
			//123 と　789 以外の順子があれば不成立
			if ((id > 1) && (id < 7))
				return false;
		}
		
		//明刻の牌をチェック
		for(int i = 0; i < tehai.getMinkousLength(); i++){
			checkHai = tehai.getMinkous();
			id = checkHai[i][0].getOldId();
			//字牌があれば不成立
			if( (id & OLD_KIND_TSUU) != 0){
				return false;
			}
			//数牌の場合は数字をチェック
			if ((id & OLD_KIND_SHUU) != 0){
				id &= OLD_KIND_MASK;
				if ((id > 1) && (id < 9))
					return false;
			}
		}
		
		//明槓の牌をチェック
		for(int i = 0; i < tehai.getMinkansLength(); i++){
			checkHai = tehai.getMinkans();
			id = checkHai[i][0].getOldId();
			//字牌があれば不成立
			if( (id & OLD_KIND_TSUU) != 0){
				return false;
			}
			//数牌の場合は数字をチェック
			if ((id & OLD_KIND_SHUU) != 0){
				id &= OLD_KIND_MASK;
				if ((id > 1) && (id < 9))
					return false;
			}
		}
		
		//暗槓の牌をチェック
		for(int i = 0; i < tehai.getAnkansLength(); i++){
			checkHai = tehai.getAnkans();
			id = checkHai[i][0].getOldId();
			//字牌があれば不成立
			if( (id & OLD_KIND_TSUU) != 0){
				return false;
			}
			//数牌の場合は数字をチェック
			if ((id & OLD_KIND_SHUU) != 0){
				id &= OLD_KIND_MASK;
				if ((id > 1) && (id < 9))
					return false;
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
		if(((combi.atamaId & OLD_KIND_SANGEN) != 0) && (countSangen == 2)){
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
/*	
	boolean checkRenhou() {
		return true;
	}
*/
	boolean checkTinitu() {
		int id;
		Hai[] jyunTehai = tehai.getJyunTehai();
		Hai checkHai[][]; 

		//萬子、筒子、索子をチェック
		int checkId[] = {OLD_KIND_WAN,OLD_KIND_PIN,OLD_KIND_SOU};

		for (int i = 0 ; i < checkId.length ; i++){
			boolean honituflg = true;
			//純手牌をチェック
			int jyunTehaiLength = tehai.getJyunTehaiLength();
			for (int j = 0; j < jyunTehaiLength; j++) {
				id = jyunTehai[j].getOldId();
				//牌が(萬子、筒子、索子)以外
				if (((id & checkId[i]) == 0)){
					honituflg = false;
				}
			}
			
			//明順の牌をチェック
			for(int j = 0; j < tehai.getMinshunsLength(); j++){
				checkHai = tehai.getMinshuns();
				id = checkHai[j][0].getOldId();
				//牌が(萬子、筒子、索子)以外
				if (((id & checkId[i]) == 0)){
					honituflg = false;
				}
			}
			
			//明刻の牌をチェック
			for(int j = 0; j < tehai.getMinkousLength(); j++){
				checkHai = tehai.getMinkous();
				id = checkHai[j][0].getOldId();
				//牌が(萬子、筒子、索子)以外
				if (((id & checkId[i]) == 0)){
					honituflg = false;
				}
			}
			
			//明槓の牌をチェック
			for(int j = 0; j < tehai.getMinkansLength(); j++){
				checkHai = tehai.getMinkans();
				id = checkHai[j][0].getOldId();
				//牌が(萬子、筒子、索子)以外
				if (((id & checkId[i]) == 0)){
					honituflg = false;
				}
			}
			
			//暗槓の牌をチェック
			for(int j = 0; j < tehai.getAnkansLength(); j++){
				checkHai = tehai.getAnkans();
				id = checkHai[j][0].getOldId();
				//牌が(萬子、筒子、索子)以外
				if (((id & checkId[i]) == 0)){
					honituflg = false;
				}
			}
			
			//清一が成立している
			if(honituflg == true){
				return true;
			}
			
		}
		//成立していなければ不成立
		return false;

	}

	boolean checkSuuankou() {
		//手牌の暗刻が4つ
		//TODO 上がった際が出上がり、ツモ上がりかどうかの判定も必要？
		if(combi.kouCount == 4){
			return true;
		}else{
			return false;
		}
	}

	boolean checkSuukantu() {
		int kansnumber = 0;
		kansnumber += tehai.getAnkansLength();
		kansnumber += tehai.getMinkansLength();
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
	//TODO 天和、地和は後で考える。
	boolean checkTenhou() {
		return false;
	}

	boolean checkTihou() {
		return false;
	}
	
	boolean checkSyousuushi() {
		//風牌役が成立している個数を調べる
		int countFon = 0;
		//東が刻子
		if(checkTon() == true){
			countFon++;
		}
		//南が刻子
		if(checkNan() == true){
			countFon++;
		}
		//西が刻子
		if(checkSya() == true){
			countFon++;
		}
		//北が刻子
		if(checkPei() == true){
			countFon++;
		}

		//頭が風牌 かつ、風牌役が3つ成立
		if(((combi.atamaId & OLD_KIND_FON) != 0) && (countFon == 3)){
			return true;
		}else{
			return false;
		}
	}

	boolean checkDaisuushi() {
		//風牌役が成立している個数を調べる
		int countFon = 0;
		//東が刻子
		if(checkTon() == true){
			countFon++;
		}
		//南が刻子
		if(checkNan() == true){
			countFon++;
		}
		//西が刻子
		if(checkSya() == true){
			countFon++;
		}
		//北が刻子
		if(checkPei() == true){
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
		int id;
		Hai[] jyunTehai = tehai.getJyunTehai();
		Hai checkHai[][]; 
		
		//順子があるかどうか確認
		if(checkToitoi() == false){
			return false;
		}
		
		//純手牌をチェック
		int jyunTehaiLength = tehai.getJyunTehaiLength();
		for (int j = 0; j < jyunTehaiLength; j++) {
			id = jyunTehai[j].getOldId();
			//牌が数牌
			if ((id & OLD_KIND_SHUU) != 0){
				return false;
			}
		}
		
		//明刻の牌をチェック
		for(int j = 0; j < tehai.getMinkousLength(); j++){
			checkHai = tehai.getMinkous();
			id = checkHai[j][0].getOldId();
			//牌が数牌
			if ((id & OLD_KIND_SHUU) != 0){
				return false;
			}
		}
		
		//明槓の牌をチェック
		for(int j = 0; j < tehai.getMinkansLength(); j++){
			checkHai = tehai.getMinkans();
			id = checkHai[j][0].getOldId();
			//牌が数牌
			if ((id & OLD_KIND_SHUU) != 0){
				return false;
			}
		}
		
		//暗槓の牌をチェック
		for(int j = 0; j < tehai.getAnkansLength(); j++){
			checkHai = tehai.getAnkans();
			id = checkHai[j][0].getOldId();
			//牌が数牌
			if ((id & OLD_KIND_SHUU) != 0){
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
		int checkId[] = {OLD_KIND_SOU+2,OLD_KIND_SOU+3,OLD_KIND_SOU+4,OLD_KIND_SOU+6,OLD_KIND_SOU+8,OLD_KIND_HATU};
		int id;
		boolean ryuuisouflg = false;
		Hai[] jyunTehai = tehai.getJyunTehai();
		Hai checkHai[][]; 

		//純手牌をチェック
		int jyunTehaiLength = tehai.getJyunTehaiLength();
		for (int i = 0; i < jyunTehaiLength; i++) {
			id = jyunTehai[i].getOldId();
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
		
		//明順の牌をチェック
		for(int i = 0; i < tehai.getMinshunsLength(); i++){
			checkHai = tehai.getMinshuns();
			id = checkHai[i][0].getOldId();
			//索子の2,3,4以外の順子があった場合不成立
			if (id != (OLD_KIND_SOU + 2)){
				return false;
			}
		}
		
		//明刻の牌をチェック
		for(int i = 0; i < tehai.getMinkousLength(); i++){
			checkHai = tehai.getMinkous();
			id = checkHai[i][0].getOldId();
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
		
		//明槓の牌をチェック
		for(int i = 0; i < tehai.getMinkansLength(); i++){
			checkHai = tehai.getMinkans();
			id = checkHai[i][0].getOldId();
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
		
		//暗槓の牌をチェック
		for(int i = 0; i < tehai.getAnkansLength(); i++){
			checkHai = tehai.getAnkans();
			id = checkHai[i][0].getOldId();
			if ((id & OLD_KIND_SHUU) == 0)
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
		
		//条件に該当した
		return true;
	}

	boolean checkCyuurennpoutou() {
		int id;
		
		//牌の数を調べるための配列 (0番地は使用しない）
		int countNumber[] = {0,0,0,0,0,0,0,0,0,0};
		Hai checkHai[] = new Hai[JYUNTEHAI_MAX];
		
		//鳴きがある場合は成立しない
		if(nakiflg == true){
			return false;
		}
		//手牌が清一になっていない場合も成立しない
		if(checkTinitu() == false){
			return false;
		}
		
		//手牌をコピーする
		checkHai = tehai.getJyunTehai();
		
		//手牌にある牌の番号を調べる
		for(int i = 0 ; i < tehai.getJyunTehaiLength() ; i++){
			id = checkHai[i].getOldId();
			//数字をチェックする
			id &= OLD_KIND_MASK;
			//数字の番号をインクリメントする
			countNumber[id]++;
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
		return false;
	}
}
