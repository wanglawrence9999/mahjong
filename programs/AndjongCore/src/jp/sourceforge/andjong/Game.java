package jp.sourceforge.andjong;

import static jp.sourceforge.andjong.Hai.*;

import jp.sourceforge.andjong.EventIF.EID;
import jp.sourceforge.andjong.Tehai.Combi;
import jp.sourceforge.andjong.Tehai.CountFormat;
import jp.sourceforge.andjong.Yaku;

/**
 * ゲームを管理するクラスです。
 * 
 * @author Yuji Urushibara
 * 
 */
public class Game {
	/** 山 */
	private Yama yama;

	/**
	 * 山を取得します。
	 * 
	 * @return 山
	 */
	Yama getYama() {
		return yama;
	}

	/** 局 */
	private int kyoku;

	/**
	 * 局を取得します。
	 * 
	 * @return 局
	 */
	int getkyoku() {
		return kyoku;
	}

	/** 局の最大値 */
	private int kyokuMax;

	/** ツモ牌 */
	private Hai tsumoHai;

	/**
	 * ツモ牌を取得します。
	 * 
	 * @return ツモ牌
	 */
	Hai getTsumoHai() {
		return tsumoHai;
	}

	/** 捨牌 */
	private Hai suteHai;

	/**
	 * 捨牌を取得します。
	 * 
	 * @return 捨牌
	 */
	Hai getSuteHai() {
		return suteHai;
	}

	/** プレイヤーに提供する情報 */
	private Info info;

	/** プレイヤーの人数 */
	private int playerLength;

	/** プレイヤーの配列 */
	private Player[] players;

	/** 風をプレイヤーインデックスに変換する配列 */
	private int[] kazeToPlayerIdx = new int[4];

	/** UIに提供する情報 */
	private InfoUI infoUi;

	/** UI */
	private Console ui;

	/** リーチ棒の数 */
	private int reachbou;

	/** 親のプレイヤーインデックス */
	private int oyaIdx;

	/** 連荘 */
	private boolean renchan;

	/** 本場 */
	private int honba;

	/** イベントを発行した風 */
	private int fromKaze;

	/** イベントの対象となった風 */
	private int toKaze;

	/** サイコロの配列 */
	private Sai[] sais = new Sai[] { new Sai(), new Sai() };

	/**
	 * サイコロの配列を取得します。
	 * 
	 * @return サイコロの配列
	 */
	Sai[] getSais() {
		return sais;
	}

	/** 割れ目 */
	private int wareme;

	/** アクティブプレイヤー */
	private Player activePlayer;

	/**
	 * メイン処理を開始します。
	 * 
	 * @param args
	 *            コマンドライン引数
	 */
	public static void main(String[] args) {
		// Gameインスタンスを作成します。
		Game game = new Game();

		// ゲームを開始します。
		game.play();
	}

	/**
	 * ゲームを開始します。
	 */
	public void play() {
		// Gameインスタンスを初期化します。
		init();

		// 場所を決めます。
		// TODO 未実装です。

		// イベント（場所決め）を発行します。
		ui.event(EID.BASHOGIME, 0, 0);

		// プレイヤーが親を決めます。
		sais[0].saifuri();
		sais[1].saifuri();
		oyaIdx = (sais[0].getNo() + sais[1].getNo() - 1) % 4;

		// イベント（親決め）を発行します。
		ui.event(EID.OYAGIME, 0, 0);

		// 局を開始します。
		while (kyoku < kyokuMax) {
			startKyoku();
			if (!renchan) {
				kyoku++;
				honba = 0;
			} else {
				System.out.println("連荘です。");
			}
		}
	}

	/**
	 * 初期化します。
	 * <p>
	 * 設定によって動的に初期化します。
	 * </p>
	 */
	private void init() {
		// 山を初期化します。
		yama = new Yama();

		// 局を初期化します。
		kyoku = 1;

		// 局の最大値を設定します。
		kyokuMax = 4;

		// ツモ牌を初期化します。
		tsumoHai = new Hai();

		// 捨牌を初期化します。
		suteHai = new Hai();

		// プレイヤーに提供する情報を初期化します。
		info = new Info(this);

		// プレイヤーの人数を設定します。
		playerLength = 4;

		// プレイヤー配列を初期化します。
		players = new Player[playerLength];
		for (int i = 0; i < players.length; i++) {
			players[i] = new Player((EventIF) new AI(info));
		}
		// players[0] = new Player((EventIF) new Man(info));

		// 風をプレイヤーインデックスに変換する配列を初期化します。
		kazeToPlayerIdx = new int[players.length];

		// UIに提供する情報を初期化します。
		infoUi = new InfoUI(this);

		// UIを初期化します。
		ui = new Console(infoUi);
	}

	/**
	 * 局を開始します。
	 */
	private void startKyoku() {
		// リーチ棒の数を初期化します。
		reachbou = 0;

		// 連荘を初期化します。
		renchan = false;

		// イベントを発行した風を初期化します。
		fromKaze = oyaIdx;

		// イベントの対象となった風を初期化します。
		toKaze = oyaIdx;

		// プレイヤーの自風を設定します。
		setJikaze();

		// 洗牌をします。
		yama.xipai();

		// UIイベント（洗牌）を発行します。
		ui.event(EID.SENPAI, fromKaze, toKaze);

		// サイ振りをします。
		sais[0].saifuri();
		sais[1].saifuri();
		wareme = (sais[0].getNo() + sais[1].getNo() - 1) % 4;

		// UIイベント（サイ振り）を発行します。
		ui.event(EID.SAIFURI, fromKaze, toKaze);

		// プレイヤー配列を初期化します。
		for (int i = 0; i < players.length; i++) {
			players[i].init();
		}

		// 配牌をします。
		haipai();

		// 局のメインループ
		EID retEid;
		MAINLOOP: while (true) {
			// ツモします。
			tsumoHai = yama.tsumo();

			// ツモ牌がない場合、流局します。
			if (tsumoHai == null) {
				// UIイベント（流局）を発行します。
				ui.event(EID.RYUUKYOKU, 0, 0);

				// 親を更新します。
				oyaIdx++;
				if (oyaIdx >= players.length) {
					oyaIdx = 0;
				}

				break MAINLOOP;
			}

			// イベント（ツモ）を発行します。
			retEid = tsumoEvent();

			// イベントを処理します。
			switch (retEid) {
			case TSUMOAGARI:// ツモあがり
				// UIイベント（ツモあがり）を発行します。
				ui.event(retEid, fromKaze, toKaze);

				// TODO 点数を清算します。
				activePlayer.increaseTenbou(reachbou * 1000);

				// 親を更新します。
				if (oyaIdx != kazeToPlayerIdx[fromKaze]) {
					oyaIdx++;
					if (oyaIdx >= players.length) {
						oyaIdx = 0;
					}
				} else {
					renchan = true;
					honba++;
				}

				break MAINLOOP;
			case RON:// ロン
				// UIイベント（ロン）を発行します。
				ui.event(retEid, fromKaze, toKaze);

				// TODO 点数を清算します。
				activePlayer.increaseTenbou(reachbou * 1000);

				// 親を更新します。
				if (oyaIdx != kazeToPlayerIdx[fromKaze]) {
					oyaIdx++;
					if (oyaIdx >= players.length) {
						oyaIdx = 0;
					}
				} else {
					renchan = true;
					honba++;
				}

				break MAINLOOP;
			case REACH:// リーチ
				int tenbou = activePlayer.getTenbou();
				if (tenbou >= 1000) {
					activePlayer.reduceTenbou(1000);
					activePlayer.setReach(true);
					reachbou++;
				}
				break;
			default:
				break;
			}

			// イベントを発行した風を更新します。
			fromKaze++;
			if (fromKaze >= players.length) {
				fromKaze = 0;
			}
		}
	}

	/**
	 * プレイヤーの自風を設定します。
	 */
	private void setJikaze() {
		for (int i = 0, j = oyaIdx; i < players.length; i++, j++) {
			if (j >= players.length) {
				j = 0;
			}

			// プレイヤーの自風を設定します。
			players[j].setJikaze(i);

			// 風をプレイヤーインデックスに変換する配列を設定します。
			kazeToPlayerIdx[i] = j;
		}
	}

	/**
	 * 配牌します。
	 */
	private void haipai() {
		// TODO 山に割れ目を設定する必要があります。

		for (int i = 0, j = oyaIdx, max = players.length * 13; i < max; i++, j++) {
			if (j >= players.length) {
				j = 0;
			}

			players[j].getTehai().addJyunTehai(yama.tsumo());
		}
	}

	/**
	 * イベント（ツモ）を発行します。
	 * 
	 * @return イベントID
	 */
	private EID tsumoEvent() {
		// アクティブプレイヤーを設定します。
		activePlayer = players[kazeToPlayerIdx[fromKaze]];

		// UIイベント（ツモ）を発行します。
		ui.event(EID.TSUMO, fromKaze, fromKaze);

		// イベント（ツモ）を発行します。
		EID retEid = activePlayer.getEventIf().event(EID.TSUMO, fromKaze,
				fromKaze);

		int sutehaiIdx;

		// イベントを処理します。
		switch (retEid) {
		case TSUMOAGARI:// ツモあがり
			break;
		case SUTEHAI:// 捨牌
			// 捨牌のインデックスを取得します。
			sutehaiIdx = activePlayer.getEventIf().getSutehaiIdx();
			if (sutehaiIdx == 13) {// ツモ切り
				suteHai.copy(tsumoHai);
				activePlayer.getKawa().add(suteHai);

				// イベントを通知します。
				retEid = notifyEvent(EID.SUTEHAI, fromKaze, fromKaze);
			} else {// 手出し
				activePlayer.getTehai().copyJyunTehaiIdx(suteHai, sutehaiIdx);
				activePlayer.getTehai().removeJyunTehai(sutehaiIdx);
				activePlayer.getTehai().addJyunTehai(tsumoHai);
				activePlayer.getKawa().add(suteHai, Kawa.PROPERTY_TEDASHI);

				// イベントを通知します。
				retEid = notifyEvent(EID.SUTEHAI, fromKaze, fromKaze);
			}
			break;
		default:
			break;
		}

		return retEid;
	}

	/**
	 * イベントを通知します。
	 * 
	 * @param eid
	 *            イベントID
	 * @param fromKaze
	 *            イベントを発行した風
	 * @param toKaze
	 *            イベントの対象となった風
	 * @return イベントID
	 */
	private EID notifyEvent(EID eid, int fromKaze, int toKaze) {
		EID retEid = EID.NAGASHI;

		// 各プレイヤーにイベントを通知する。
		NOTIFYLOOP: for (int i = 0, j = fromKaze; i < players.length; i++, j++) {
			if (j >= players.length) {
				j = 0;
			}

			// アクティブプレイヤーを設定します。
			activePlayer = players[kazeToPlayerIdx[j]];

			// UIイベントを発行します。
			ui.event(eid, fromKaze, toKaze);

			// イベントを発行します。
			retEid = activePlayer.getEventIf().event(eid, fromKaze, toKaze);

			// イベントを処理します。
			switch (retEid) {
			case TSUMOAGARI:// ツモあがり
				// アクティブプレイヤーを設定します。
				this.fromKaze = j;
				this.toKaze = toKaze;
				activePlayer = players[kazeToPlayerIdx[this.fromKaze]];
				break NOTIFYLOOP;
			case RON:// ロン
				// アクティブプレイヤーを設定します。
				this.fromKaze = j;
				this.toKaze = toKaze;
				activePlayer = players[kazeToPlayerIdx[this.fromKaze]];
				break NOTIFYLOOP;
			default:
				break;
			}
		}

		return retEid;
	}

	/*
	 * Info, InfoUIに提供するAPIを定義します。
	 */

	/**
	 * 表ドラ、槓ドラの配列を取得します。
	 * 
	 * @return 表ドラ、槓ドラの配列
	 */
	Hai[] getDoras() {
		return getYama().getDoras();
	}

	/**
	 * 自風を取得します。
	 */
	int getJikaze() {
		return activePlayer.getJikaze();
	}

	/**
	 * リーチを取得します。
	 * 
	 * @param kaze
	 *            風
	 * @return リーチ
	 */
	boolean isReach(int kaze) {
		return players[kazeToPlayerIdx[kaze]].isReach();
	}

	/**
	 * 手牌をコピーします。
	 * 
	 * @param tehai
	 *            手牌
	 * @param kaze
	 *            風
	 */
	void copyTehai(Tehai tehai, int kaze) {
		if (activePlayer.getJikaze() == kaze) {
			tehai.copy(activePlayer.getTehai(), true);
		} else {
			tehai.copy(players[kazeToPlayerIdx[kaze]].getTehai(), false);
		}
	}

	/**
	 * 河をコピーします。
	 * 
	 * @param kawa
	 *            河
	 * @param kaze
	 *            風
	 */
	void copyKawa(Kawa kawa, int kaze) {
		kawa.copy(players[kazeToPlayerIdx[kaze]].getKawa());
	}

	/** カウントフォーマット */
	private CountFormat countFormat = new CountFormat();

	private Combi[] combis = new Combi[10];
	{
		for (int i = 0; i < combis.length; i++)
			combis[i] = new Combi();
	}

	private int countHu(Tehai tehai, Hai addHai, int combisCount, Combi combi) {
		int countHu = 20;
		int id;
		Hai checkHai[][];

		id = combi.atamaId;
		/*
		 * // TODO 役牌の識別が必要
		 * 
		 * //頭が役牌 if((combi.atamaId & KIND_TSUU) != 0 ){ //２ 符追加 countHan += 2;
		 * }
		 */
		// TODO 単騎、カンチャン、ペンチャンならば
		// countHu += 2;
		// 刻子による追加
		// 暗刻による加点
		for (int i = 0; i < combi.kouCount; i++) {
			id = combi.kouIds[i];
			// 牌が字牌もしくは1,9
			if (((id & KIND_TSUU) != 0) || ((id & KIND_MASK) == 1)
					|| ((id & KIND_MASK) == 9)) {
				countHu += 8;
			} else {
				countHu += 4;
			}
		}

		// 明刻による加点
		for (int i = 0; i < tehai.getMinkousLength(); i++) {
			checkHai = tehai.getMinkous();
			id = checkHai[i][0].getId();
			// 牌が字牌もしくは1,9
			if (((id & KIND_TSUU) != 0) || ((id & KIND_MASK) == 1)
					|| ((id & KIND_MASK) == 9)) {
				countHu += 4;
			} else {
				countHu += 2;
			}
		}

		// 明槓による加点
		for (int i = 0; i < tehai.getMinkansLength(); i++) {
			checkHai = tehai.getMinkans();
			id = checkHai[i][0].getId();
			// 牌が字牌もしくは1,9
			if (((id & KIND_TSUU) != 0) || ((id & KIND_MASK) == 1)
					|| ((id & KIND_MASK) == 9)) {
				countHu += 16;
			} else {
				countHu += 8;
			}
		}

		// 暗槓による加点
		for (int i = 0; i < tehai.getMinkansLength(); i++) {
			checkHai = tehai.getMinkans();
			id = checkHai[i][0].getId();
			// 牌が字牌もしくは1,9
			if (((id & KIND_TSUU) != 0) || ((id & KIND_MASK) == 1)
					|| ((id & KIND_MASK) == 9)) {
				countHu += 32;
			} else {
				countHu += 16;
			}
		}

		// TODO ツモ上がりによる追加
		// countHu += 2;

		// TODO 面前ロン上がりによる追加
		if (tehai.getJyunTehaiLength() < Tehai.JYUNTEHAI_MAX) {
			// TODO ロン上がりの場合
			// countHu += 10;
		}

		return countHu;
	}

	public int getScore(int hanSuu, int huSuu) {
		int score;
		// 符　× ２の　（役数　+　場ゾロの2役)
		score = huSuu * (int) Math.pow(2, hanSuu + 2);
		// 子は上の4倍が基本点(親は6倍)
		score *= 4;

		if (hanSuu >= 13) { // 13翻以上は役満
			score = 32000;
		} else if (hanSuu >= 11) { // 11翻以上は3倍満
			score = 24000;
		} else if (hanSuu >= 8) { // 8翻以上は倍満
			score = 16000;
		} else if (hanSuu >= 6) { // 6翻以上は跳満
			score = 12000;
		} else if (hanSuu >= 5) { // 5翻以上は満貫
			score = 8000;
		}

		// 7700は8000とする
		if (score > 7600) {
			score = 8000;
		}

		// 100で割り切れない数がある場合100点繰上げ
		if (score % 100 != 0) {
			score = score - (score % 100) + 100;
		}

		return score;
	}

	public int getAgariScore(Tehai tehai, Hai addHai) {
		// カウントフォーマットを取得します。
		tehai.getCountFormat(countFormat, addHai);

		// あがりの組み合わせを取得します。
		int combisCount = tehai.getCombi(combis, countFormat);

		// あがりの組み合わせがない場合は0点
		if (combisCount == 0)
			return 0;

		// 役
		int hanSuu[] = new int[combisCount];
		// 符
		int huSuu[] = new int[combisCount];
		// 点数（子のロン上がり）
		int agariScore[] = new int[combisCount];
		// 最大の点数
		int maxagariScore = 0;

		for (int i = 0; i < combisCount; i++) {
			Yaku yaku = new Yaku(tehai, addHai, combis[i]);
			hanSuu[i] = yaku.getHanSuu();
			huSuu[i] = countHu(tehai, addHai, combisCount, combis[i]);
			// TODO ドラの計算
			agariScore[i] = getScore(hanSuu[i], huSuu[i]);
		}

		// 最大値を探す
		maxagariScore = agariScore[0];
		for (int i = 0; i < combisCount; i++) {
			maxagariScore = Math.max(maxagariScore, agariScore[i]);
		}
		return maxagariScore;
	}
}
