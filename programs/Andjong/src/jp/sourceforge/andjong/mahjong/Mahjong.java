package jp.sourceforge.andjong.mahjong;

import android.util.Log;
import jp.sourceforge.andjong.AndjongView;
import jp.sourceforge.andjong.mahjong.AgariScore;
import jp.sourceforge.andjong.mahjong.AgariScore.AgariInfo;
import jp.sourceforge.andjong.mahjong.AgariSetting.YakuflgName;
import jp.sourceforge.andjong.mahjong.CountFormat.Combi;
import static jp.sourceforge.andjong.mahjong.EventIf.*;
import static jp.sourceforge.andjong.mahjong.Hai.*;

/**
 * ゲームを管理するクラスです。
 *
 * @author Yuji Urushibara
 *
 */
public class Mahjong implements Runnable {
	/** AndjongView */
	private AndjongView m_view;

	/** 山 */
	private Yama m_yama;

	/** 東一局 */
	public final static int KYOKU_TON_1 = 0;
	/** 東二局 */
	public final static int KYOKU_TON_2 = 1;
	/** 東三局 */
	public final static int KYOKU_TON_3 = 2;
	/** 東四局 */
	public final static int KYOKU_TON_4 = 3;

	/** 局 */
	private int m_kyoku;

	/** 局の最大値 */
	private int m_kyokuEnd;

	/** ツモ牌 */
	private Hai m_tsumoHai;

	/** 捨牌 */
	private Hai m_suteHai;

	/** リーチ棒の数 */
	private int m_reachbou;

	/** 本場 */
	private int m_honba;

	/** プレイヤーの人数 */
	private int m_playerNum;

	/** プレイヤーに提供する情報 */
	private Info m_info;

	/** プレイヤーの配列 */
	private Player[] m_players;

	/** 風をプレイヤーインデックスに変換する配列 */
	private int[] m_kazeToPlayerIdx = new int[4];

	/** UIに提供する情報 */
	private InfoUi m_infoUi;

	/** サイコロの配列 */
	private Sai[] m_sais = new Sai[] { new Sai(), new Sai() };

	/** 親のプレイヤーインデックス */
	private int m_iOya;

	/** 起家のプレイヤーインデックス */
	private int m_iChiicha;

	/** 連荘 */
	private boolean m_renchan;

	/** イベントを発行した風 */
	private int m_kazeFrom;

	/** イベントの対象となった風 */
	private int m_kazeTo;

	/** 持ち点の初期値 */
	private static final int TENBOU_INIT = 20000;

	/**
	 * コンストラクタ
	 *
	 * @param view
	 *            View
	 */
	public Mahjong(AndjongView view) {
		super();
		this.m_view = view;
	}

	/**
	 * 山を取得する。
	 *
	 * @return 山
	 */
	Yama getYama() {
		return m_yama;
	}

	/**
	 * 局を取得する。
	 *
	 * @return 局
	 */
	int getkyoku() {
		return m_kyoku;
	}

	/**
	 * ツモ牌を取得する。
	 *
	 * @return ツモ牌
	 */
	Hai getTsumoHai() {
		return m_tsumoHai;
	}

	/**
	 * 捨牌を取得する。
	 *
	 * @return 捨牌
	 */
	Hai getSuteHai() {
		return m_suteHai;
	}

	public int getReachbou() {
		return m_reachbou;
	}

	public void setReachbou(int reachbou) {
		m_reachbou = reachbou;
	}

	/**
	 * 起家のプレイヤーインデックスを取得する。
	 *
	 * @return 起家のプレイヤーインデックス
	 */
	public int getChiichaIdx() {
		return m_iChiicha;
	}

	/**
	 * サイコロの配列を取得する。
	 *
	 * @return サイコロの配列
	 */
	Sai[] getSais() {
		return m_sais;
	}

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
	private int mWareme;

	/** アクティブプレイヤー */
	private Player activePlayer;

	private PlayerAction m_playerAction = new PlayerAction();

	public int getManKaze() {
		return m_players[0].getJikaze();
	}

	/**
	 * ゲームを開始する。
	 */
	public void play() {
		// 初期化する。
		initialize();

		// 親を決める。
		m_sais[0].saifuri();
		m_sais[1].saifuri();
		m_iOya = (m_sais[0].getNo() + m_sais[1].getNo() - 1) % 4;
		m_iChiicha = m_iOya;

		// イベント（親決め）を発行する。
		//m_view.event(EventId.OYAGIME, KAZE_NONE, KAZE_NONE);
		m_view.event(EventId.START_GAME, KAZE_NONE, KAZE_NONE);

		// 局を繰り返して、ゲームを進行する。
		while (m_kyoku <= m_kyokuEnd) {
			Log.e("Mahjong", "oya = " + m_iOya);
			// 局を開始する。
			startKyoku();

			// 連荘の場合、局を進めない。
			if (m_renchan) {
				// イベント（連荘）を発行する。
				//m_view.event(EventId.RENCHAN, KAZE_NONE, KAZE_NONE);
				continue;
			}

			// 局を進める。
			m_kyoku++;

			// 本場を初期化する。
			// TODO 上がりの位置に移動しないと。
			m_honba = 0;
		}

		// イベント（ゲームの終了）を発行する。
		m_view.event(EventId.END_GAME, KAZE_NONE, KAZE_NONE);
	}

	/**
	 * 初期化する。
	 */
	private void initialize() {
		// 山を作成する。
		m_yama = new Yama();

		// 局を初期化する。
		m_kyoku = KYOKU_TON_1;

		// 局の終了を設定する。
		m_kyokuEnd = KYOKU_TON_4;

		// ツモ牌を作成する。
		m_tsumoHai = new Hai();

		// 捨牌を作成する。
		m_suteHai = new Hai();

		// リーチ棒の数を初期化する。
		m_reachbou = 0;

		// 本場を初期化する。
		m_honba = 0;

		// プレイヤーの人数を設定する。
		m_playerNum = 4;

		// プレイヤーに提供する情報を作成する。
		m_info = new Info(this);

		// プレイヤーの配列を初期化する。
		m_players = new Player[m_playerNum];
		m_players[0] = new Player((EventIf) new Man(m_info, "A", m_playerAction));
		m_players[1] = new Player((EventIf) new AI(m_info, "B"));
		m_players[2] = new Player((EventIf) new AI(m_info, "C"));
		m_players[3] = new Player((EventIf) new AI(m_info, "D"));

		for (int i = 0; i < m_playerNum; i++) {
			m_players[i].setTenbou(TENBOU_INIT);
		}

		// 風をプレイヤーインデックスに変換する配列を初期化する。
		m_kazeToPlayerIdx = new int[m_players.length];

		// UIに提供する情報を作成する。
		m_infoUi = new InfoUi(this, m_playerAction);

		// UIを初期化する。
		m_view.initUi(m_infoUi, "AndjongView");
	}

	boolean m_tenpai[] = new boolean[4];

	public boolean[] getTenpai() {
		return m_tenpai;
	}

	/**
	 * 局を開始する。
	 */
	private void startKyoku() {
		// リーチ棒の数を初期化する。
		// TODO 上がりの位置に移動しないと。
		m_reachbou = 0;

		// 連荘を初期化する。
		m_renchan = false;

		// プレイヤーの自風を設定する。
		setJikaze();

		// イベントを発行した風を初期化する。
		m_kazeFrom = m_players[m_iOya].getJikaze();

		// イベントの対象となった風を初期化する。
		m_kazeTo = m_players[m_iOya].getJikaze();

		// プレイヤー配列を初期化する。
		for (int i = 0; i < m_players.length; i++) {
			m_players[i].init();
		}

		// 洗牌する。
		m_yama.xipai();

		// サイ振りをする。
		m_sais[0].saifuri();
		m_sais[1].saifuri();

		// UIイベント（サイ振り）を発行する。
		//m_view.event(EventId.SAIFURI, mFromKaze, mToKaze);

		// 山に割れ目を設定する。
		setWareme(m_sais);

		// 配牌する。
		haipai();

		// UIイベント（配牌）を発行する。
		//m_view.event(EventId.HAIPAI, mFromKaze, mToKaze);
		m_view.event(EventId.START_KYOKU, m_kazeFrom, m_kazeTo);

		EventId retEid;

		// 局を進行する。
		KYOKU_MAIN: while (true) {
			// UIイベント（進行待ち）を発行する。
			m_view.event(EventId.UI_WAIT_PROGRESS, KAZE_NONE, KAZE_NONE);

			// ツモ牌を取得する。
			m_tsumoHai = m_yama.tsumo();

			// ツモ牌がない場合、流局する。
			if (m_tsumoHai == null) {
				// テンパイの確認をする。
				int tenpaiCount = 0;
				int iPlayer;
				for (int i = 0; i < m_tenpai.length; i++) {
					iPlayer = m_kazeToPlayerIdx[i];
					m_tenpai[iPlayer] = m_players[iPlayer].isTenpai();
					if (m_tenpai[iPlayer]) {
						tenpaiCount++;
					}
				}
				int incScore = 0;
				int redScore = 0;
				switch (tenpaiCount) {
				case 0:
					break;
				case 1:
					incScore = 3000;
					redScore = 1000;
					break;
				case 2:
					incScore = 1500;
					redScore = 1500;
					break;
				case 3:
					incScore = 1000;
					redScore = 3000;
					break;
				}
				for (int i = 0; i < m_tenpai.length; i++) {
					if (m_tenpai[i]) {
						m_players[i].increaseTenbou(incScore);
					} else {
						m_players[i].reduceTenbou(redScore);
					}
				}

				// UIイベント（流局）を発行する。
				m_view.event(EventId.RYUUKYOKU, KAZE_NONE, KAZE_NONE);

				// フラグを落としておく。
				for (int i = 0; i < m_tenpai.length; i++) {
					m_tenpai[i] = false;
				}

				// 親を更新する。上がり連荘とする。
				m_iOya++;
				if (m_iOya >= m_players.length) {
					m_iOya = 0;
				}

				// 本場を増やす。
				m_honba++;

				break KYOKU_MAIN;
			}

			// イベント（ツモ）を発行する。
			retEid = tsumoEvent();

			Log.e("Mahjong", "kazeFrom = " + m_kazeFrom + " kazeTo = " + m_kazeTo);
			Log.e("Mahjong", "iFrom = " + m_kazeToPlayerIdx[m_kazeFrom] + " iTo = " + m_kazeToPlayerIdx[m_kazeTo]);
			int score;
			int iPlayer;
			// イベントを処理する。
			switch (retEid) {
			case TSUMO_AGARI:// ツモあがり
				iPlayer = m_kazeToPlayerIdx[m_kazeTo];
				if (m_iOya == iPlayer) {
					score = m_agariInfo.m_score.m_oyaRon + (m_honba * 300);
					for (int i = 0; i < 3; i++) {
						iPlayer = (iPlayer + 1) % 4;
						m_players[iPlayer].reduceTenbou(m_agariInfo.m_score.m_oyaTsumo + (m_honba * 100));
					}
				} else {
					score = m_agariInfo.m_score.m_koRon + (m_honba * 300);
					for (int i = 0; i < 3; i++) {
						iPlayer = (iPlayer + 1) % 4;
						if (m_iOya == iPlayer) {
							m_players[iPlayer].reduceTenbou(m_agariInfo.m_score.m_oyaTsumo + (m_honba * 100));
						} else {
							m_players[iPlayer].reduceTenbou(m_agariInfo.m_score.m_koTsumo + (m_honba * 100));
						}
					}
				}

				activePlayer.increaseTenbou(score);
				m_agariInfo.m_agariScore = score - (m_honba * 300);

				// TODO 点数を清算する。
				activePlayer.increaseTenbou(m_reachbou * 1000);

				// UIイベント（ツモあがり）を発行する。
				m_view.event(retEid, m_kazeFrom, m_kazeTo);

				// 親を更新する。
				if (m_iOya != m_kazeToPlayerIdx[m_kazeFrom]) {
					m_iOya++;
					if (m_iOya >= m_players.length) {
						m_iOya = 0;
					}
				} else {
					m_renchan = true;
					m_honba++;
				}

				break KYOKU_MAIN;
			case RON_AGARI:// ロン
				if (m_iOya == m_kazeToPlayerIdx[m_kazeTo]) {
					score = m_agariInfo.m_score.m_oyaRon + (m_honba * 300);
				} else {
					score = m_agariInfo.m_score.m_koRon + (m_honba * 300);
				}

				m_players[m_kazeToPlayerIdx[m_kazeFrom]].increaseTenbou(score);
				m_players[m_kazeToPlayerIdx[m_kazeTo]].reduceTenbou(score);

				m_agariInfo.m_agariScore = score - (m_honba * 300);

				// TODO 点数を清算する。
				activePlayer.increaseTenbou(m_reachbou * 1000);

				// UIイベント（ロン）を発行する。
				m_view.event(retEid, m_kazeFrom, m_kazeTo);

				// 親を更新する。
				if (m_iOya != m_kazeToPlayerIdx[m_kazeFrom]) {
					m_iOya++;
					if (m_iOya >= m_players.length) {
						m_iOya = 0;
					}
				} else {
					m_renchan = true;
					m_honba++;
				}

				break KYOKU_MAIN;
			case REACH:// リーチ
				int tenbou = activePlayer.getTenbou();
				if (tenbou >= 1000) {
					activePlayer.reduceTenbou(1000);
					activePlayer.setReach(true);
					m_reachbou++;
				}
				break;
			default:
				break;
			}

			// イベントを発行した風を更新する。
			m_kazeFrom++;
			if (m_kazeFrom >= m_players.length) {
				m_kazeFrom = 0;
			}
		}
	}

	/**
	 * プレイヤーの自風を設定する。
	 */
	private void setJikaze() {
		for (int i = 0, j = m_iOya; i < m_players.length; i++, j++) {
			if (j >= m_players.length) {
				j = 0;
			}

			// プレイヤーの自風を設定する。
			m_players[j].setJikaze(i);

			// 風をプレイヤーインデックスに変換する配列を設定する。
			m_kazeToPlayerIdx[i] = j;
		}
	}

	/**
	 * 山に割れ目を設定する。
	 *
	 * @param sais
	 *            サイコロの配列
	 */
	void setWareme(Sai[] sais) {
		int sum = sais[0].getNo() + sais[1].getNo() - 1;

		mWareme = sum % 4;

		int startHaisIdx = ((sum % 4) * 36) + sum;

		m_yama.setTsumoHaisStartIndex(startHaisIdx);
	}

	/**
	 * 配牌する。
	 */
	private void haipai() {
		for (int i = 0, j = m_iOya, max = m_players.length * 13; i < max; i++, j++) {
			if (j >= m_players.length) {
				j = 0;
			}

			m_players[j].getTehai().addJyunTehai(m_yama.tsumo());
		}

		if (true) {
		//if (false) {
			while (m_players[0].getTehai().getJyunTehaiLength() > 0) {
				m_players[0].getTehai().rmJyunTehai(0);
			}
			int haiIds[] = {1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4}; // リーチタンピンイーペーコーTehai tehai = new Tehai();
			//int haiIds[] = {1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7}; // リーチタンピンイーペーコーTehai tehai = new Tehai();
			//int haiIds[] = {1, 1, 2, 2, 3, 3, 4, 5, 6, 10, 10, 10, 11, 12}; // リーチタンピンイーペーコーTehai tehai = new Tehai();
			for (int i = 0; i < haiIds.length - 1; i++) {
				m_players[0].getTehai().addJyunTehai(new Hai(haiIds[i]));
			}
		}
	}

	boolean m_isTsumo = false;

	/**
	 * イベント（ツモ）を発行する。
	 *
	 * @return イベントID
	 */
	private EventId tsumoEvent() {
		// アクティブプレイヤーを設定する。
		activePlayer = m_players[m_kazeToPlayerIdx[m_kazeFrom]];

		boolean isTest = false;
		if (isTest) {
			//activePlayer.setReach(true);
			int haiIds[] = {1, 1, 2, 2, 3, 3, 4, 5, 6, 10, 10, 10, 11, 9}; // ピンフイーペーコー
			//int haiIds[] = {ID_NAN, ID_NAN, ID_NAN, 2, 3, 4, 5, 6, 7, 10, 10, 10, 11, 12}; // 東
			//int haiIds[] = {ID_TON, ID_TON, ID_TON, 2, 3, 4, 5, 6, 7, 10, 10, 10, 11, 12}; // 東
			//int haiIds[] = {1, 2, 2, 3, 3, 4, 5, 6, 10, 10, 10, 11, 12}; // リーチタンピンイーペーコー
			//int haiIds[] = {1, 1, 2, 3, 4, 5, 6, 7, 10, 10, 10, 11, 12}; // タンヤオ
			//int haiIds[] = {ID_HAKU, ID_HAKU, ID_HAKU, ID_CHUN, ID_CHUN, ID_CHUN, 5, 6, 7, ID_HATSU, ID_HATSU, 10, 11, 12}; // 役牌
			//int haiIds[] = {ID_HAKU, ID_HAKU, ID_HAKU, ID_CHUN, ID_CHUN, ID_CHUN, 5, 6, 7, 10, 10, 10, 11, 12}; // 役牌
			//int haiIds[] = {ID_HAKU, ID_HAKU, ID_HAKU, ID_HATSU, ID_HATSU, ID_HATSU, ID_CHUN, ID_CHUN, ID_CHUN, 10, 10, 10, 11, 12}; // 役牌
			//int haiIds[] = {ID_HAKU, ID_HAKU, ID_HAKU, ID_HATSU, ID_HATSU, ID_HATSU, 5, 6, 7, 10, 10, 10, 11, 12}; // 役牌
			//int haiIds[] = {ID_HAKU, ID_HAKU, ID_HAKU, 2, 3, 4, 5, 6, 7, 10, 10, 10, 11, 12}; // 役牌
			//int haiIds[] = {1, 1, 2, 2, 3, 3, 4, 5, 6, 10, 10, 10, 11, 12}; // リーチタンピンイーペーコー
			//int haiIds[] = {1, 1, 1, 2, 3, 4, 5, 6, 7, 10, 10, 10, 11, 12}; // タンヤオ
			//int haiIds[] = {0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 10, 11, 12}; // 平和
			//int haiIds[] = {0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
			//int haiIds[] = {0, 0, 1, 2, 3, 4, 5, 6, 9, 10, 11, 12, 13, 14};
			Tehai tehai = new Tehai();
			for (int i = 0; i < haiIds.length - 1; i++) {
				tehai.addJyunTehai(new Hai(haiIds[i]));
			}
			//tehai.setChiiLeft(new Hai(1), 0);
			//tehai.setPon(new Hai(1), 0);
			Hai addHai = new Hai(haiIds[haiIds.length - 1]);

			int agariScore = getAgariScore(tehai, addHai);

			Log.e("TEST", "agariScore = " + agariScore);
		}
		m_isTsumo = true;

		// UIイベント（ツモ）を発行する。
		m_view.event(EventId.TSUMO, m_kazeFrom, m_kazeFrom);

		// イベント（ツモ）を発行する。
		EventId retEid = activePlayer.getEventIf().event(EventId.TSUMO, m_kazeFrom, m_kazeFrom);

		m_isTsumo = false;

		// UIイベント（進行待ち）を発行する。
		m_view.event(EventId.UI_WAIT_PROGRESS, m_kazeFrom, m_kazeFrom);

		int sutehaiIdx;
		Hai[] kanHais;

		// イベントを処理する。
		switch (retEid) {
		case ANKAN:
			activePlayer.getTehai().addJyunTehai(m_tsumoHai);
			sutehaiIdx = activePlayer.getEventIf().getISutehai();
			kanHais = m_playerAction.getKanHais();
			activePlayer.getTehai().setAnKan(kanHais[sutehaiIdx], getRelation(this.m_kazeFrom, this.m_kazeTo));

			// イベントを通知する。
			retEid = notifyEvent(EventId.ANKAN, m_kazeFrom, m_kazeFrom);

			// UIイベント（進行待ち）を発行する。
			m_view.event(EventId.UI_WAIT_PROGRESS, KAZE_NONE, KAZE_NONE);

			// ツモ牌を取得する。
			m_tsumoHai = m_yama.rinshanTsumo();

			// イベント（ツモ）を発行する。
			retEid = tsumoEvent();
			break;
		case TSUMO_AGARI:// ツモあがり
			break;
		case SUTEHAI:// 捨牌
			// 捨牌のインデックスを取得する。
			sutehaiIdx = activePlayer.getEventIf().getISutehai();

			// 理牌の間をとる。
			m_infoUi.setSutehaiIdx(sutehaiIdx);
			m_view.event(EventId.UI_WAIT_RIHAI, m_kazeFrom, m_kazeFrom);

			if (sutehaiIdx == 13) {// ツモ切り
				Hai.copy(m_suteHai, m_tsumoHai);
				activePlayer.getKawa().add(m_suteHai);
			} else {// 手出し
				activePlayer.getTehai().copyJyunTehaiIndex(m_suteHai, sutehaiIdx);
				activePlayer.getTehai().rmJyunTehai(sutehaiIdx);
				activePlayer.getTehai().addJyunTehai(m_tsumoHai);
				activePlayer.getKawa().add(m_suteHai);
				activePlayer.getKawa().setTedashi(true);
			}

			// イベントを通知する。
			retEid = notifyEvent(EventId.SUTEHAI, m_kazeFrom, m_kazeFrom);
			break;
		case REACH:
			// 捨牌のインデックスを取得する。
			sutehaiIdx = activePlayer.getEventIf().getISutehai();
			activePlayer.setReach(true);
			m_view.event(EventId.UI_WAIT_RIHAI, m_kazeFrom, m_kazeFrom);

			if (sutehaiIdx == 13) {// ツモ切り
				Hai.copy(m_suteHai, m_tsumoHai);
				activePlayer.getKawa().add(m_suteHai);
				activePlayer.getKawa().setReach(true);
			} else {// 手出し
				activePlayer.getTehai().copyJyunTehaiIndex(m_suteHai, sutehaiIdx);
				activePlayer.getTehai().rmJyunTehai(sutehaiIdx);
				activePlayer.getTehai().addJyunTehai(m_tsumoHai);
				activePlayer.getKawa().add(m_suteHai);
				activePlayer.getKawa().setTedashi(true);
				activePlayer.getKawa().setReach(true);
			}

			activePlayer.reduceTenbou(1000);
			activePlayer.setReach(true);
			m_reachbou++;

			// イベントを通知する。
			retEid = notifyEvent(EventId.REACH, m_kazeFrom, m_kazeFrom);
			break;
		default:
			break;
		}

		return retEid;
	}

	/**
	 * イベントを通知する。
	 *
	 * @param eid
	 *            イベントID
	 * @param fromKaze
	 *            イベントを発行した風
	 * @param toKaze
	 *            イベントの対象となった風
	 * @return イベントID
	 */
	private EventId notifyEvent(EventId eid, int fromKaze, int toKaze) {
		EventId retEid = EventId.NAGASHI;
		int iSuteHai;

		// UIイベントを発行する。
		m_view.event(eid, fromKaze, toKaze);

		// 各プレイヤーにイベントを通知する。
		NOTIFYLOOP: for (int i = 0, j = fromKaze; i < m_players.length; i++, j++) {
			if (j >= m_players.length) {
				j = 0;
			}

			// アクティブプレイヤーを設定する。
			activePlayer = m_players[m_kazeToPlayerIdx[j]];

			// UIイベントを発行する。
			//mView.event(eid, fromKaze, toKaze);

			// イベントを発行する。
			toKaze = j;
			retEid = activePlayer.getEventIf().event(eid, fromKaze, toKaze);

			// イベントを処理する。
			switch (retEid) {
			case TSUMO_AGARI:// ツモあがり
				// アクティブプレイヤーを設定する。
				this.m_kazeFrom = j;
				this.m_kazeTo = toKaze;
				activePlayer = m_players[m_kazeToPlayerIdx[this.m_kazeFrom]];
				break NOTIFYLOOP;
			case RON_AGARI:// ロン
				// アクティブプレイヤーを設定する。
				this.m_kazeFrom = toKaze;
				this.m_kazeTo = fromKaze;
//				this.m_kazeFrom = j;
//				this.m_kazeTo = toKaze;
				activePlayer = m_players[m_kazeToPlayerIdx[this.m_kazeFrom]];
				break NOTIFYLOOP;
			case PON:
				// アクティブプレイヤーを設定する。
				this.m_kazeFrom = j;
				this.m_kazeTo = fromKaze;
				activePlayer = m_players[m_kazeToPlayerIdx[this.m_kazeFrom]];
				activePlayer.getTehai().setPon(m_suteHai, getRelation(this.m_kazeFrom, this.m_kazeTo));
				m_players[m_kazeToPlayerIdx[this.m_kazeTo]].getKawa().setNaki(true);

				notifyEvent(EventId.SELECT_SUTEHAI, this.m_kazeFrom, this.m_kazeTo);

				// 捨牌のインデックスを取得する。
				iSuteHai = activePlayer.getEventIf().getISutehai();
				activePlayer.getTehai().copyJyunTehaiIndex(m_suteHai, iSuteHai);
				activePlayer.getTehai().rmJyunTehai(iSuteHai);
				activePlayer.getKawa().add(m_suteHai);
				//activePlayer.getKawa().setNaki(true);
				activePlayer.getKawa().setTedashi(true);

				// イベントを通知する。
				retEid = notifyEvent(EventId.PON, this.m_kazeFrom, this.m_kazeTo);
				break NOTIFYLOOP;
			case CHII_LEFT:
				// アクティブプレイヤーを設定する。
				this.m_kazeFrom = j;
				this.m_kazeTo = fromKaze;
				activePlayer = m_players[m_kazeToPlayerIdx[this.m_kazeFrom]];
				activePlayer.getTehai().setChiiLeft(m_suteHai, getRelation(this.m_kazeFrom, this.m_kazeTo));
				m_players[m_kazeToPlayerIdx[this.m_kazeTo]].getKawa().setNaki(true);

				notifyEvent(EventId.SELECT_SUTEHAI, this.m_kazeFrom, this.m_kazeTo);

				// 捨牌のインデックスを取得する。
				iSuteHai = activePlayer.getEventIf().getISutehai();
				activePlayer.getTehai().copyJyunTehaiIndex(m_suteHai, iSuteHai);
				activePlayer.getTehai().rmJyunTehai(iSuteHai);
				activePlayer.getKawa().add(m_suteHai);
				//activePlayer.getKawa().setNaki(true);
				activePlayer.getKawa().setTedashi(true);

				// イベントを通知する。
				retEid = notifyEvent(EventId.CHII_LEFT, this.m_kazeFrom, this.m_kazeTo);
				break;
			case CHII_CENTER:
				// アクティブプレイヤーを設定する。
				this.m_kazeFrom = j;
				this.m_kazeTo = fromKaze;
				activePlayer = m_players[m_kazeToPlayerIdx[this.m_kazeFrom]];
				activePlayer.getTehai().setChiiCenter(m_suteHai, getRelation(this.m_kazeFrom, this.m_kazeTo));
				m_players[m_kazeToPlayerIdx[this.m_kazeTo]].getKawa().setNaki(true);

				notifyEvent(EventId.SELECT_SUTEHAI, this.m_kazeFrom, this.m_kazeTo);

				// 捨牌のインデックスを取得する。
				iSuteHai = activePlayer.getEventIf().getISutehai();
				activePlayer.getTehai().copyJyunTehaiIndex(m_suteHai, iSuteHai);
				activePlayer.getTehai().rmJyunTehai(iSuteHai);
				activePlayer.getKawa().add(m_suteHai);
				//activePlayer.getKawa().setNaki(true);
				activePlayer.getKawa().setTedashi(true);

				// イベントを通知する。
				retEid = notifyEvent(EventId.CHII_CENTER, this.m_kazeFrom, this.m_kazeTo);
				break;
			case CHII_RIGHT:
				// アクティブプレイヤーを設定する。
				this.m_kazeFrom = j;
				this.m_kazeTo = fromKaze;
				activePlayer = m_players[m_kazeToPlayerIdx[this.m_kazeFrom]];
				activePlayer.getTehai().setChiiRight(m_suteHai, getRelation(this.m_kazeFrom, this.m_kazeTo));
				m_players[m_kazeToPlayerIdx[this.m_kazeTo]].getKawa().setNaki(true);

				notifyEvent(EventId.SELECT_SUTEHAI, this.m_kazeFrom, this.m_kazeTo);

				// 捨牌のインデックスを取得する。
				iSuteHai = activePlayer.getEventIf().getISutehai();
				activePlayer.getTehai().copyJyunTehaiIndex(m_suteHai, iSuteHai);
				activePlayer.getTehai().rmJyunTehai(iSuteHai);
				activePlayer.getKawa().add(m_suteHai);
				//activePlayer.getKawa().setNaki(true);
				activePlayer.getKawa().setTedashi(true);

				// イベントを通知する。
				retEid = notifyEvent(EventId.CHII_RIGHT, this.m_kazeFrom, this.m_kazeTo);
				break;
			case DAIMINKAN:
				// アクティブプレイヤーを設定する。
				this.m_kazeFrom = j;
				this.m_kazeTo = fromKaze;
				activePlayer = m_players[m_kazeToPlayerIdx[this.m_kazeFrom]];
				activePlayer.getTehai().setDaiMinKan(m_suteHai, getRelation(this.m_kazeFrom, this.m_kazeTo));
				m_players[m_kazeToPlayerIdx[this.m_kazeTo]].getKawa().setNaki(true);

				// イベントを通知する。
				retEid = notifyEvent(EventId.DAIMINKAN, this.m_kazeFrom, this.m_kazeTo);

				// UIイベント（進行待ち）を発行する。
				m_view.event(EventId.UI_WAIT_PROGRESS, KAZE_NONE, KAZE_NONE);

				// ツモ牌を取得する。
				m_tsumoHai = m_yama.rinshanTsumo();

				// イベント（ツモ）を発行する。
				retEid = tsumoEvent();
				break;
			default:
				break;
			}

			if (eid == EventId.SELECT_SUTEHAI) {
				return retEid;
			}
		}

		return retEid;
	}

	/*
	 * Info, InfoUIに提供するAPIを定義する。
	 */

	/**
	 * 表ドラ、槓ドラの配列を取得する。
	 *
	 * @return 表ドラ、槓ドラの配列
	 */
	Hai[] getDoras() {
		return getYama().getOmoteDoraHais();
	}

	/**
	 * 表ドラ、槓ドラの配列を取得する。
	 *
	 * @return 表ドラ、槓ドラの配列
	 */
	Hai[] getUraDoras() {
		return getYama().getUraDoraHais();
	}

	/**
	 * 自風を取得する。
	 */
	int getJikaze() {
		return activePlayer.getJikaze();
	}

	/**
	 * 本場を取得する。
	 *
	 * @return 本場
	 */
	int getHonba() {
		return m_honba;
	}

	/**
	 * リーチを取得する。
	 *
	 * @param kaze
	 *            風
	 * @return リーチ
	 */
	boolean isReach(int kaze) {
		return m_players[m_kazeToPlayerIdx[kaze]].isReach();
	}

	/**
	 * 手牌をコピーする。
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
			Tehai.copy(tehai, m_players[m_kazeToPlayerIdx[kaze]].getTehai(), false);
		}
	}

	/**
	 * 手牌をコピーする。
	 *
	 * @param tehai
	 *            手牌
	 * @param kaze
	 *            風
	 */
	void copyTehaiUi(Tehai tehai, int kaze) {
		Tehai.copy(tehai, m_players[m_kazeToPlayerIdx[kaze]].getTehai(), true);
	}

	/**
	 * 河をコピーする。
	 *
	 * @param kawa
	 *            河
	 * @param kaze
	 *            風
	 */
	void copyKawa(Kawa kawa, int kaze) {
		Kawa.copy(kawa, m_players[m_kazeToPlayerIdx[kaze]].getKawa());
	}

	/**
	 * ツモの残り数を取得する。
	 *
	 * @return ツモの残り数
	 */
	int getTsumoRemain() {
		return m_yama.getTsumoNokori();
	}

	String getName(int kaze) {
		return m_players[m_kazeToPlayerIdx[kaze]].getEventIf().getName();
	}

	int getTenbou(int kaze) {
		return m_players[m_kazeToPlayerIdx[kaze]].getTenbou();
	}

	private Combi[] combis = new Combi[10];
	{
		for (int i = 0; i < combis.length; i++)
			combis[i] = new Combi();
	}

	AgariScore m_score;
	AgariInfo m_agariInfo = new AgariInfo();

	public AgariInfo getAgariInfo() {
		return m_agariInfo;
	}

	public int getAgariScore(Tehai tehai, Hai addHai) {
		AgariSetting setting = new AgariSetting(this);
		if (activePlayer.isReach()) {
			setting.setYakuflg(YakuflgName.REACH.ordinal(), true);
		}
		if (m_isTsumo) {
			setting.setYakuflg(YakuflgName.TUMO.ordinal(), true);
		}
		m_score = new AgariScore();
		int score = m_score.getAgariScore(tehai, addHai, combis, setting, m_agariInfo);
//		if (m_iOya == m_kazeToPlayerIdx[m_kazeTo]) {
//			score *= 1.5;
//			score = (score / 100) * 100;
//		}
//		m_agariInfo.m_score = score;
		return score;
	}

	public String[] getYakuName(Tehai tehai, Hai addHai){
		AgariSetting setting = new AgariSetting(this);
		AgariScore score = new AgariScore();
		return score.getYakuName(tehai, addHai, combis, setting);
	}

	@Override
	public void run() {
		// ゲームを開始する。
		play();
	}

	public void setSutehaiIdx(int sutehaiIdx) {
		m_info.setSutehaiIdx(sutehaiIdx);
	}

	public void postUiEvent(EventId a_eventId, int a_kazeFrom, int a_kazeTo) {
		m_view.event(a_eventId, a_kazeFrom, a_kazeTo);
	}
}
