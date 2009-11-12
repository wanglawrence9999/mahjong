package jp.sourceforge.andjong.mahjong;

import jp.sourceforge.andjong.AndjongView;
import jp.sourceforge.andjong.mahjong.AgariScore;
import jp.sourceforge.andjong.mahjong.CountFormat.Combi;
import jp.sourceforge.andjong.mahjong.EventIF.EID;

/**
 * ゲームを管理するクラスです。
 *
 * @author Yuji Urushibara
 *
 */
public class Mahjong implements Runnable {
	/** AndjongView */
	private AndjongView ui;

	public void setAndjongView(AndjongView andjongView) {
		this.ui = andjongView;
	}

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

	/** 東一局 */
	public final static int KYOKU_TON_1 = 1;
	/** 東二局 */
	public final static int KYOKU_TON_2 = 2;
	/** 東三局 */
	public final static int KYOKU_TON_3 = 3;
	/** 東四局 */
	public final static int KYOKU_TON_4 = 4;

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
	//private Console ui;

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

	public final static int KAZE_TON = 0;
	public final static int KAZE_NAN = 1;
	public final static int KAZE_SHA = 2;
	public final static int KAZE_PE = 3;

	public int getRelation(
			int fromKaze,
			int toKaze) {
		int relation;
		if (fromKaze == toKaze) {
			relation = RELATION_JIBUN;
		} else if ((fromKaze + 1) % 4 == toKaze) {
			relation = RELATION_SHIMOCHA;
		} else if ((fromKaze + 2) % 4 == toKaze) {
			relation = RELATION_TOIMEN;
		} else {
			relation = RELATION_KAMICHA;
		}
		return relation;
	}

	/*
	 * 共通定義
	 */

	/** 面子の構成牌の数(3個) */
	public static final int MENTSU_HAI_MEMBERS_3 = 3;
	/** 面子の構成牌の数(4個) */
	public static final int MENTSU_HAI_MEMBERS_4 = 4;

	/** 他家との関係 自分 */
	public static final int RELATION_JIBUN = 0;
	/** 他家との関係 上家 */
	public static final int RELATION_KAMICHA = 1;
	/** 他家との関係 対面 */
	public static final int RELATION_TOIMEN = 2;
	/** 他家との関係 下家 */
	public static final int RELATION_SHIMOCHA = 3;

	/** 割れ目 */
	private int wareme;

	/** アクティブプレイヤー */
	private Player activePlayer;

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
		while (kyoku <= kyokuMax) {
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
		// for (int i = 0; i < players.length; i++) {
		// players[i] = new Player((EventIF) new AI(info));
		// }
		//players[0] = new Player((EventIF) new AI(info, "一郎"));
		players[1] = new Player((EventIF) new AI(info, "二郎"));
		players[2] = new Player((EventIF) new AI(info, "三郎"));
		players[3] = new Player((EventIF) new AI(info, "四郎"));
		players[0] = new Player((EventIF) new Man(info, "プレイヤー"));

		// 風をプレイヤーインデックスに変換する配列を初期化します。
		kazeToPlayerIdx = new int[players.length];

		// UIに提供する情報を初期化します。
		infoUi = new InfoUI(this);

		// UIを初期化します。
		//ui = new Console(infoUi, "コンソール");

		// UIを初期化します。
		ui.Console(infoUi, "AndjongView");
	}

	public int getManKaze() {
		return players[0].getJikaze();
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

		// 山に割れ目を設定します。
		setWareme(sais);

		// プレイヤー配列を初期化します。
		for (int i = 0; i < players.length; i++) {
			players[i].init();
		}

		// UIイベント（サイ振り）を発行します。
		ui.event(EID.SAIFURI, fromKaze, toKaze);

		// 配牌をします。
		haipai();

		// 局のメインループ
		EID retEid;
		MAINLOOP: while (true) {
			try {
				Thread.sleep(50, 0);
			} catch (InterruptedException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
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
	 * 山に割れ目を設定します。
	 *
	 * @param sais
	 *            サイコロの配列
	 */
	void setWareme(Sai[] sais) {
		int sum = sais[0].getNo() + sais[1].getNo() - 1;

		wareme = sum % 4;

		int startHaisIdx = ((sum % 4) * 36) + sum;

		yama.setTsumoHaisStartIdx(startHaisIdx);
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
		try {
			Thread.sleep(50, 0);
		} catch (InterruptedException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		int sutehaiIdx;

		// イベントを処理します。
		switch (retEid) {
		case TSUMOAGARI:// ツモあがり
			break;
		case SUTEHAI:// 捨牌
			// 捨牌のインデックスを取得します。
			sutehaiIdx = activePlayer.getEventIf().getSutehaiIdx();
			if (sutehaiIdx == 13) {// ツモ切り
				Hai.copy(suteHai, tsumoHai);
				activePlayer.getKawa().add(suteHai);
			} else {// 手出し
				activePlayer.getTehai().copyJyunTehaiIdx(suteHai, sutehaiIdx);
				activePlayer.getTehai().rmJyunTehai(sutehaiIdx);
				activePlayer.getTehai().addJyunTehai(tsumoHai);
				activePlayer.getKawa().add(suteHai);
				activePlayer.getKawa().setTedashi(true);
			}

			// イベントを通知します。
			retEid = notifyEvent(EID.SUTEHAI, fromKaze, fromKaze);
			break;
		case REACH:
			// 捨牌のインデックスを取得します。
			sutehaiIdx = activePlayer.getEventIf().getSutehaiIdx();
			if (sutehaiIdx == 13) {// ツモ切り
				Hai.copy(suteHai, tsumoHai);
				activePlayer.getKawa().add(suteHai);
				activePlayer.getKawa().setReach(true);
			} else {// 手出し
				activePlayer.getTehai().copyJyunTehaiIdx(suteHai, sutehaiIdx);
				activePlayer.getTehai().rmJyunTehai(sutehaiIdx);
				activePlayer.getTehai().addJyunTehai(tsumoHai);
				activePlayer.getKawa().add(suteHai);
				activePlayer.getKawa().setTedashi(true);
				activePlayer.getKawa().setReach(true);
			}

			// イベントを通知します。
			retEid = notifyEvent(EID.REACH, fromKaze, fromKaze);
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
			case PON:
				// アクティブプレイヤーを設定します。
				this.fromKaze = j;
				this.toKaze = fromKaze;
				activePlayer = players[kazeToPlayerIdx[this.fromKaze]];
				activePlayer.getTehai().setPon(suteHai, getRelation(this.fromKaze, this.toKaze));

				notifyEvent(EID.SUTEHAISELECT, this.fromKaze, this.toKaze);

				// 捨牌のインデックスを取得します。
				int sutehaiIdx = activePlayer.getEventIf().getSutehaiIdx();
				activePlayer.getTehai().copyJyunTehaiIdx(suteHai, sutehaiIdx);
				activePlayer.getTehai().rmJyunTehai(sutehaiIdx);
				activePlayer.getKawa().add(suteHai);
				activePlayer.getKawa().setNaki(true);
				activePlayer.getKawa().setTedashi(true);

				// イベントを通知します。
				retEid = notifyEvent(EID.PON, this.fromKaze, this.toKaze);
				break NOTIFYLOOP;
			default:
				break;
			}

			if (eid == EID.SUTEHAISELECT) {
				return retEid;
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
		return getYama().getOmoteDoraHais();
	}

	/**
	 * 表ドラ、槓ドラの配列を取得します。
	 *
	 * @return 表ドラ、槓ドラの配列
	 */
	Hai[] getUraDoras() {
		return getYama().getUraDoraHais();
	}

	/**
	 * 自風を取得します。
	 */
	int getJikaze() {
		return activePlayer.getJikaze();
	}

	/**
	 * 本場を取得します。
	 *
	 * @return 本場
	 */
	int getHonba() {
		return honba;
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
			Tehai.copy(tehai, activePlayer.getTehai(), true);
		} else {
			Tehai.copy(tehai, players[kazeToPlayerIdx[kaze]].getTehai(), false);
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
		Kawa.copy(kawa, players[kazeToPlayerIdx[kaze]].getKawa());
	}

	/**
	 * ツモの残り数を取得します。
	 *
	 * @return ツモの残り数
	 */
	int getTsumoRemain() {
		return yama.getTsumoNokori();
	}

	String getName(int kaze) {
		return players[kazeToPlayerIdx[kaze]].getEventIf().getName();
	}

	int getTenbou(int kaze) {
		return players[kazeToPlayerIdx[kaze]].getTenbou();
	}

	int getWareme() {
		return wareme;
	}

	private Combi[] combis = new Combi[10];
	{
		for (int i = 0; i < combis.length; i++)
			combis[i] = new Combi();
	}

	public int getAgariScore(Tehai tehai, Hai addHai) {
		AgariSetting setting = new AgariSetting(this);
		AgariScore score = new AgariScore();
		return score.getAgariScore(tehai, addHai, combis, setting);
	}

	public String[] getYakuName(Tehai tehai, Hai addHai){
		AgariSetting setting = new AgariSetting(this);
		AgariScore score = new AgariScore();
		return score.getYakuName(tehai, addHai, combis, setting);
	}

	@Override
	public void run() {
		// ゲームを開始します。
		play();
	}

	public void setSutehaiIdx(int sutehaiIdx) {
		info.setSutehaiIdx(sutehaiIdx);
	}
}
