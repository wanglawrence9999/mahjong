package jp.sourceforge.andjong;

import static jp.sourceforge.andjong.Info.*;
import static jp.sourceforge.andjong.Hai.*;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

import jp.sourceforge.andjong.Tehai.Combi;
import jp.sourceforge.andjong.Tehai.CountFormat;

/**
 * ゲームのハンドリングを行うクラスです。
 * 
 * @author Yuji Urushibara
 * 
 */
public class Game {
	/** 山 */
	private Yama yama;

	/** プレイヤーの人数 */
	private int playerNum;

	/** プレイヤーの配列 */
	Player[] players;

	private int kyoku;

	private boolean renchan;

	private int oya;

	private int honba;

	private int reachbou;

	private int wareme;

	private int activePlayerIdx;

	private int action;

	private Info info;
	private InfoUI infoUi;

	private UI ui;
	
	public Yama getYama() {
		return yama;
	}

	private boolean checkTanyao(Tehai tehai, Hai addHai, Combi combi) {
		int id;
		Hai[] jyunTehai = tehai.getJyunTehai();
		int jyunTehaiLength = tehai.getJyunTehaiLength();

		for (int i = 0; i < jyunTehaiLength; i++) {
			id = jyunTehai[i].getId();
			if ((id & KIND_SHUU) == 0)
				return false;
			id &= KIND_MASK;
			if ((id == 1) || (id == 9))
				return false;
		}

		// TODO 鳴き牌の確認も

		return true;
	}

	public int getAgariScore(Tehai tehai, Hai addHai, int combisCount,
			Combi[] combis) {
		/*
		 * ここで役と得点を計算する。
		 */

		boolean tanyao;
		for (int i = 0; i < combisCount; i++) {
			tanyao = checkTanyao(tehai, addHai, combis[i]);
			if (tanyao) {
				System.out.println("タンヤオ！！！");
				/*
				 * TODO 役をカウントしていく。
				 */
			}
		}
		return 0;
	}

	class SAI {
		private int no;

		public int getNo() {
			return no;
		}

		public int saifuri() {
			return no = new Random().nextInt(6) + 1;
		}
	}

	private SAI[] sai = new SAI[] { new SAI(), new SAI() };
	
	SAI[] getSai() {
		return sai;
	}

	public void play() {
		// Gameオブジェクトを初期化します。
		init();

		// 場所を決めます。
		// TODO あとで実装します。

		// UIイベント（場所決め）
		ui.event(EID.UI_BASHOGIME, 0, 0);

		// プレイヤーを初期化します。
		playerNum = 4;
		players = new Player[playerNum];
		for (int i = 0; i < playerNum; i++)
			players[i] = new Player(new AI(info));

		// 親を決めます。
		// TODO ランダムで決める必要があります。
		sai[0].saifuri();
		sai[1].saifuri();
		oya = 0;

		// UIイベント（親決め）
		ui.event(EID.UI_OYAGIME, 0, 0);

		// 局を開始します。
		// TODO 最初は東風戦にしておきます。
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

	private void setCha() {
		for (int i = 0, j = oya; i < players.length; i++, j++) {
			if (j >= players.length)
				j = 0;

			players[j].setJikaze(i);

			for (int k = 0, l = j; k < players.length; k++, l++) {
				if (l >= players.length)
					l = 0;
				players[j].ChaToPlayer[l] = k;
				players[j].PlayerToCha[k] = l;
				players[j].players[k] = players[l];
			}
		}
	}

	public void saifuri() {
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

	private final static int HAIPAI_END = 52;

	private void haipai() {
		for (int i = 0, j = oya; i < HAIPAI_END; i++) {
			players[j].tehai.addJyunTehai(yama.tsumo());

			j++;
			if (j >= players.length) {
				j = 0;
			}
		}
	}

	private void startKyoku() {
		reachbou = 0;
		renchan = false;

		// 家を設定
		setCha();

		// 洗牌
		yama.xipai();

		// UIイベント（洗牌）
		ui.event(EID.UI_SENPAI, 0, 0);
		
		// サイ振り
		saifuri();
		
		// UIイベント（サイ振り）
		ui.event(EID.UI_SAIFURI, 0, 0);

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

	private Player activePlayer;

	public Player getActivePlayer() {
		return activePlayer;
	}

	private final static int ACTION_TSUMOAGARI = 0;
	private final static int ACTION_RON = 1;

	// int eventCallPlayerIdx;
	int eventTargetPlayerIdx;

	private void callEvent(int eventCallPlayerIdx, int eventTargetPlayerIdx,
			EID eid) {
		EID returnEid;
		int j = eventCallPlayerIdx;

		for (int i = 0; i < players.length; i++) {
			// アクションを通知

			// ツモ
			ui.event(eid, activePlayer.ChaToPlayer[eventCallPlayerIdx],
					activePlayer.ChaToPlayer[eventTargetPlayerIdx]);
			returnEid = activePlayer.ai.event(eid,
					activePlayer.ChaToPlayer[eventCallPlayerIdx],
					activePlayer.ChaToPlayer[eventTargetPlayerIdx]);

			switch (returnEid) {
			case RON:
				activePlayer = players[j];
				activePlayerIdx = j;
				action = ACTION_RON;
				ui.event(EID.RON, activePlayerIdx,
						activePlayer.ChaToPlayer[eventCallPlayerIdx]);
				return;
			case TSUMOAGARI:
				activePlayer = players[j];
				activePlayerIdx = j;
				action = ACTION_TSUMOAGARI;
				return;
			default:
				j++;
				if (j >= players.length) {
					j = 0;
				}
				activePlayer = players[j];
				break;
			}
		}
	}

	Hai tsumoHai;

	Hai suteHai;

	private void sutehai() {
		EID returnEid;
		int sutehaiIdx;

		// イベント（ツモ）
		ui.event(EID.TSUMO, 0, 0);
		returnEid = activePlayer.ai.event(EID.TSUMO, 0, 0);

		switch (returnEid) {
		case SUTEHAI:
			// イベント（捨牌）
			sutehaiIdx = info.getSutehaiIdx();
			if (sutehaiIdx == 13) {
				// ツモ切り
				suteHai.copy(tsumoHai);
				activePlayer.kawa.add(suteHai);
				eventTargetPlayerIdx = activePlayerIdx;
				callEvent(activePlayerIdx, eventTargetPlayerIdx, EID.SUTEHAI);
			} else {
				// 手出し
				activePlayer.tehai.copyJyunTehaiIdx(suteHai, sutehaiIdx);
				activePlayer.tehai.removeJyunTehai(sutehaiIdx);
				activePlayer.kawa.add(suteHai, Kawa.PROPERTY_TEDASHI);
				if (tsumoHai != null) {
					activePlayer.tehai.addJyunTehai(tsumoHai);
				}
				eventTargetPlayerIdx = activePlayerIdx;
				callEvent(activePlayerIdx, eventTargetPlayerIdx, EID.SUTEHAI);
			}
			break;
		case TSUMOAGARI:
			ui.event(EID.TSUMOAGARI, 0, 0);
			action = ACTION_TSUMOAGARI;
			break;
		default:
			break;
		}
	}

	private void loopKyoku() {
		activePlayerIdx = oya;
		eventTargetPlayerIdx = oya;
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

			activePlayer = players[activePlayerIdx];
			sutehai();

			switch (action) {
			case ACTION_TSUMOAGARI:
				activePlayer.tenbou += reachbou * 1000;
				action = 0;
				if (oya != activePlayerIdx) {
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
			case ACTION_RON:
				activePlayer.tenbou += reachbou * 1000;
				action = 0;
				if (oya != activePlayerIdx) {
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
				activePlayerIdx++;
				if (activePlayerIdx >= players.length) {
					activePlayerIdx = 0;
				}
				break;
			}
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
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.play();
	}
}
