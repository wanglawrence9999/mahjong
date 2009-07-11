package jp.sourceforge.andjong;

import static jp.sourceforge.andjong.Hai.*;

import java.util.Random;

import jp.sourceforge.andjong.EventIF.EID;
import jp.sourceforge.andjong.Tehai.Combi;
import jp.sourceforge.andjong.Yaku;

/**
 * ゲームのハンドリングを行うクラスです。
 * 
 * @author Yuji Urushibara
 * 
 */
public class Game {
	/** 山 */
	private Yama yama;

	public Yama getYama() {
		return yama;
	}

	/** プレイヤーの人数 */
	private int playerNum = 4;

	/** プレイヤーの配列 */
	Player[] players;

	private int kyoku;

	private boolean renchan;

	private int oya;

	private int honba;

	private int reachbou;

	private int wareme;

	private int fromKaze;

	private int action;

	private Info info;
	private InfoUI infoUi;

	private UI ui;

	class SAI {
		private int no;

		public int getNo() {
			return no;
		}

		public int saifuri() {
			return no = new Random().nextInt(6) + 1;
		}
	}

	private SAI[] sais = new SAI[] { new SAI(), new SAI() };

	SAI[] getSais() {
		return sais;
	}

	private Player activePlayer;

	private final static int ACTION_TSUMOAGARI = 0;
	private final static int ACTION_RON = 1;

	private int toKaze;

	Hai tsumoHai;

	Hai suteHai;

	private final static int HAIPAI_END = 52;

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

		// イベント（場所決め）
		ui.event(EID.BASHOGIME, 0, 0);

		// プレイヤーが親を決めます。
		sais[0].saifuri();
		sais[1].saifuri();
		oya = (sais[0].getNo() + sais[1].getNo() - 1) % 4;

		// イベント（親決め）
		ui.event(EID.OYAGIME, 0, 0);

		// 局を開始します。
		// TODO 暫定的に東風戦で実装します。
		while (kyoku < 4) {
			startKyoku();
			break;
			// if (!renchan) {
			// kyoku++;
			// honba = 0;
			// } else {
			// System.out.println("連荘です。");
			// }
		}
	}

	private void init() {
		yama = new Yama();
		kyoku = 0;
		renchan = false;
		info = new Info(this);
		infoUi = new InfoUI(this);
		ui = new UI(infoUi);
		tsumoHai = new Hai();
		suteHai = new Hai();

		// プレイヤーを初期化します。
		players = new Player[playerNum];
		for (int i = 0; i < playerNum; i++)
			players[i] = new Player(new AI(info));
		// players[0] = new Player((EventIF) new Man(info));
	}

	private void startKyoku() {
		reachbou = 0;
		renchan = false;

		// 家を設定
		setCha();

		// 洗牌
		yama.xipai();

		// UIイベント（洗牌）
		ui.event(EID.SENPAI, fromKaze, toKaze);

		// サイ振り
		saifuri();

		// UIイベント（サイ振り）
		ui.event(EID.SAIFURI, fromKaze, toKaze);

		// プレイヤーの初期化
		for (int i = 0; i < players.length; i++)
			players[i].init();

		// 配牌
		haipai();

		// 局のメインループ
		loopKyoku();

		{
			// debug
			System.out.println("honba:" + honba);
		}
	}

	private int[] kazeToPlayerIdx = new int[4];

	private void setCha() {
		for (int i = 0, j = oya; i < players.length; i++, j++) {
			if (j >= players.length)
				j = 0;

			players[j].setJikaze(i);
			kazeToPlayerIdx[i] = j;
		}
		fromKaze = oya;
		toKaze = oya;
	}

	private void saifuri() {
		Random random = new Random();
		int sainome = random.nextInt(10) + 2;

		switch (sainome) {
		case 5:
		case 9:
			wareme = oya;
			break;
		case 2:
		case 6:
		case 10:
			wareme = (oya + 1) % players.length;
			break;
		case 3:
		case 7:
		case 11:
			wareme = (oya + 2) % players.length;
			break;
		case 4:
		case 8:
		case 12:
			wareme = (oya + 3) % players.length;
			break;
		}
	}

	private void haipai() {
		for (int i = 0, j = oya; i < HAIPAI_END; i++) {
			players[j].tehai.addJyunTehai(yama.tsumo());

			j++;
			if (j >= players.length) {
				j = 0;
			}
		}
	}

	private void loopKyoku() {
		action = 2;

		while (true) {
			// ツモ
			tsumoHai = yama.tsumo();
			if (tsumoHai == null) {
				// 流局
				oya++;
				if (oya >= players.length) {
					oya = 0;
				}
				return;
			}

			activePlayer = players[kazeToPlayerIdx[fromKaze]];
			toKaze = fromKaze;
			sutehai();

			switch (action) {
			case ACTION_TSUMOAGARI:
				ui.event(EID.TSUMOAGARI, fromKaze, toKaze);
				activePlayer.tenbou += reachbou * 1000;
				action = 0;
				if (oya != fromKaze) {
					oya++;
					if (oya >= players.length) {
						oya = 0;
					}
				} else {
					renchan = true;
					honba++;
				}
				return;
			case ACTION_RON:
				ui.event(EID.RON, fromKaze, toKaze);
				activePlayer.tenbou += reachbou * 1000;
				action = 0;
				if (oya != fromKaze) {
					// System.out.println("oya++");
					oya++;
					if (oya >= players.length) {
						oya = 0;
					}
				} else {
					renchan = true;
					honba++;
				}
				return;
			default:
				fromKaze++;
				if (fromKaze >= players.length) {
					fromKaze = 0;
				}
				break;
			}
		}
	}

	private void sutehai() {
		EID returnEid;
		int sutehaiIdx;

		// イベント（ツモ）
		ui.event(EID.TSUMO, fromKaze, toKaze);
		returnEid = activePlayer.eventIf.event(EID.TSUMO, fromKaze, toKaze);

		switch (returnEid) {
		case SUTEHAI:
			// イベント（捨牌）
			sutehaiIdx = info.getSutehaiIdx();
			if (sutehaiIdx == 13) {
				// ツモ切り
				suteHai.copy(tsumoHai);
				activePlayer.kawa.add(suteHai);
				// toKaze = fromKaze;
				callEvent(fromKaze, toKaze, EID.SUTEHAI);
			} else {
				// 手出し
				activePlayer.tehai.copyJyunTehaiIdx(suteHai, sutehaiIdx);
				activePlayer.tehai.removeJyunTehai(sutehaiIdx);
				activePlayer.kawa.add(suteHai, Kawa.PROPERTY_TEDASHI);
				if (tsumoHai != null) {
					activePlayer.tehai.addJyunTehai(tsumoHai);
				}
				// toKaze = fromKaze;
				callEvent(fromKaze, toKaze, EID.SUTEHAI);
			}
			break;
		case TSUMOAGARI:
			action = ACTION_TSUMOAGARI;
			break;
		default:
			break;
		}
	}

	private void callEvent(int fromKaze, int toKaze, EID eid) {
		EID returnEid;
		int j = fromKaze;

		for (int i = 0; i < players.length; i++) {
			// アクションを通知

			// ツモ
			ui.event(eid, fromKaze, toKaze);
			returnEid = activePlayer.eventIf.event(eid, fromKaze, toKaze);

			switch (returnEid) {
			case RON:
				activePlayer = players[j];
				this.fromKaze = j;
				this.toKaze = toKaze;
				action = ACTION_RON;
				return;
			case TSUMOAGARI:
				activePlayer = players[j];
				this.fromKaze = j;
				this.toKaze = toKaze;
				action = ACTION_TSUMOAGARI;
				return;
			default:
				j++;
				if (j >= players.length) {
					j = 0;
				}
				activePlayer = players[kazeToPlayerIdx[j]];
				break;
			}
		}
	}

	public void copyTehai(Tehai tehai, int kaze) {
		if (activePlayer.getJikaze() == kaze) {
			tehai.copy(activePlayer.tehai, true);
		} else {
			tehai.copy(players[kazeToPlayerIdx[kaze]].tehai, false);
		}
	}

	public void copyKawa(Kawa kawa, int kaze) {
		kawa.copy(players[kazeToPlayerIdx[kaze]].kawa);
	}

	public int getJikaze() {
		return activePlayer.getJikaze();
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

	public int getAgariScore(Tehai tehai, Hai addHai, int combisCount,
			Combi[] combis) {
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
