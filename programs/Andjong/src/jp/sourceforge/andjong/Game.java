package jp.sourceforge.andjong;

//import static jp.sourceforge.andjong.Hai.*;

import java.util.Random;

//import jp.sourceforge.andjong.Tehai.CountFormat;

/**
 * ゲームのハンドリングを行うクラスです。
 * 
 * @author Yuji Urushibara
 * 
 */
public class Game {
	/** 山 */
	private Yama yama = new Yama();

	/** プレイヤーの人数 */
	private int playerNum;

	/** プレイヤーの配列 */
	private Player[] players;

	private int kyoku;

	private boolean renchan;

	private int oya;

	private int honba;

	private int reachbou;

	private boolean last_tsumogiri;

	private int wareme;

	private int activePlayerIdx;

	private int action;

	public void play() {
		// Gameオブジェクトを初期化します。
		init();

		// 場所を決めます。
		// TODO あとで実装します。

		// 親を決めます。
		// TODO ランダムで決める必要があります。
		oya = 0;

		// プレイヤーを初期化します。
		playerNum = 4;
		players = new Player[playerNum];
		for (int i = 0; i < playerNum; i++)
			players[i] = new Player(new AI());

		// 局を開始します。
		// TODO 最初は東風戦にしておきます。
		while (kyoku < 4) {
			startKyoku();
			if (!renchan) {
				kyoku++;
				honba = 0;
			} else {
				System.out.println("連荘です。");
			}
		}

		// // 山を洗牌します。
		// yama.xipai();
		//
		// // ドラを表示してみます。
		// {
		// Hai[] dora;
		// dora = yama.getDora();
		// System.out.println("dora.length = " + dora.length);
		// for (int i = 0; i < dora.length; i++)
		// System.out.println("dora = " + dora[i].id);
		// }
		//
		// // ツモします。
		// for (int i = 0; i < playerNum; i++)
		// for (int j = 0; j < 13; j++)
		// players[i].tehai.addJyunTehai(yama.tsumo());
		//
		// // 純手牌を表示します。
		// for (int i = 0; i < playerNum; i++) {
		// System.out.println("players[" + i + "]");
		// for (int j = 0; j < players[i].tehai.jyunTehaiLength; j++)
		// System.out.print(players[i].tehai.jyunTehai[j].id + ",");
		// System.out.println();
		// }
		//
		// // CountFormatに変換してみます。
		// CountFormat countFormat;
		// countFormat = players[0].tehai.getCountFormat(yama.tsumo());
		//
		// {
		// System.out.println("CountFormat");
		// for (int i = 0; i < countFormat.length; i++) {
		// System.out.println("id = " + countFormat.counts[i].id
		// + ", length = " + countFormat.counts[i].length);
		// }
		// System.out.println(countFormat.getTotalCountLength());
		// }
		//
		// {
		// Tehai tehai = new Tehai();
		// tehai.addJyunTehai(new Hai(KIND_WAN | 1));
		// tehai.addJyunTehai(new Hai(KIND_WAN | 1));
		// tehai.addJyunTehai(new Hai(KIND_WAN | 1));
		// tehai.addJyunTehai(new Hai(KIND_WAN | 2));
		// tehai.addJyunTehai(new Hai(KIND_WAN | 3));
		// tehai.addJyunTehai(new Hai(KIND_WAN | 4));
		// tehai.addJyunTehai(new Hai(KIND_WAN | 5));
		// tehai.addJyunTehai(new Hai(KIND_WAN | 6));
		// tehai.addJyunTehai(new Hai(KIND_WAN | 7));
		// tehai.addJyunTehai(new Hai(KIND_WAN | 8));
		// tehai.addJyunTehai(new Hai(KIND_WAN | 9));
		// tehai.addJyunTehai(new Hai(KIND_WAN | 9));
		// tehai.addJyunTehai(new Hai(KIND_WAN | 9));
		//
		// CountFormat countFormatTest;
		// countFormatTest = tehai.getCountFormat(new Hai(KIND_WAN | 2));
		//
		// tehai.combiManage.init(countFormatTest.getTotalCountLength());
		// tehai.searchCombi(0);
		// }
		//
		// // 河に捨ててみます。
		// for (int i = 0; i < 5; i++) {
		// players[0].kawa.add(players[0].tehai.jyunTehai[0]);
		// players[0].tehai.removeJyunTehai(0);
		// }
		//
		// // 河を表示してみます。
		// System.out.println("kawa");
		// for (int i = 0; i < players[0].kawa.kawaLength; i++)
		// System.out.print(players[0].kawa.hais[i].id + ",");
		// System.out.println();
		//
		// {
		// Tehai tehai = new Tehai();
		//
		// Hai[] minshun = new Hai[] { new Hai(Hai.KIND_WAN | 1),
		// new Hai(Hai.KIND_WAN | 2), new Hai(Hai.KIND_WAN | 3) };
		//
		// for (int i = 0; i < 5; i++)
		// tehai.addMinshun(minshun);
		//
		// Hai[] minkou = new Hai[] { new Hai(Hai.KIND_WAN | 1),
		// new Hai(Hai.KIND_WAN | 1), new Hai(Hai.KIND_WAN | 1) };
		//
		// for (int i = 0; i < 5; i++)
		// tehai.addMinkou(minkou);
		//
		// Hai[] minkan = new Hai[] { new Hai(Hai.KIND_WAN | 1),
		// new Hai(Hai.KIND_WAN | 1), new Hai(Hai.KIND_WAN | 1),
		// new Hai(Hai.KIND_WAN | 1) };
		//
		// for (int i = 0; i < 5; i++)
		// tehai.addMinkan(minkan);
		//
		// Hai[] ankan = new Hai[] { new Hai(Hai.KIND_WAN | 1),
		// new Hai(Hai.KIND_WAN | 1), new Hai(Hai.KIND_WAN | 1),
		// new Hai(Hai.KIND_WAN | 1) };
		//
		// for (int i = 0; i < 5; i++)
		// tehai.addankan(ankan);
		// }
		//
		// Hai tsumoHai;
		// while (true) {
		// tsumoHai = yama.tsumo();
		// if (tsumoHai == null)
		// break;
		// System.out.println("tsumoHai = " + tsumoHai.id);
		// }
		//
		// Hai rinshanHai;
		// while (true) {
		// rinshanHai = yama.rinshan();
		// if (rinshanHai == null)
		// break;
		// System.out.println("rinshanHai = " + rinshanHai.id);
		// }
		//
		// // ドラを表示してみます。
		// {
		// Hai[] dora;
		// dora = yama.getDoraAll();
		// System.out.println("dora.length = " + dora.length);
		// for (int i = 0; i < dora.length; i++)
		// System.out.println("dora = " + dora[i].id);
		// }
	}

	private void setCha() {
		for (int i = 0, j = oya; i < players.length; i++, j++) {
			if (j >= players.length)
				j = 0;

			players[j].jikaze = i;

			for (int k = 0, l = j; k < players.length; k++, l++) {
				if (l >= players.length)
					l = 0;
				players[j].ChaToPlayer[l] = k;
				players[j].PlayerToCha[k] = l;
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
		last_tsumogiri = false;
		reachbou = 0;
		renchan = false;

		// 家を設定
		setCha();

		// 洗牌
		yama.xipai();

		// サイ振り
		saifuri();

		// プレイヤーの初期化
		/*
		 * for (int i = 0; i < players.length; i++) { players[i].init(); }
		 */

		// 配牌
		haipai();

		Hai[] doras = yama.getDoraAll();
		for (Hai hai : doras) {
			System.out.println("ドラ:" + hai.id);
		}

		{
			// debug
			System.out.println("親:" + oya);
		}

		// 局のメインループ
		// loopKyoku();

		{
			// debug
			System.out.println("honba:" + honba);
		}
	}

	private Player activePlayer;

	private final static int ACTION_TSUMO = 0;
	private final static int ACTION_RON = 1;

	private void loopKyoku() {
		Hai tsumoHai;
		activePlayerIdx = oya;

		while (true) {
			// ツモ
			tsumoHai = yama.tsumo();
			// tsumo_hai = yama.rinshan();
			if (tsumoHai == null) {
				// 流局
				oya++;
				if (oya >= players.length) {
					oya = 0;
				}
				return;
			}

			action = 0;
			activePlayer = players[activePlayerIdx];
			// sutehai(tsumoHai);

			switch (action) {
			case ACTION_TSUMO:
				activePlayer.tenbou += reachbou * 1000;
				System.out.println("★ツモ");
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
				System.out.println("★ロン");
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
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.play();
	}
}
