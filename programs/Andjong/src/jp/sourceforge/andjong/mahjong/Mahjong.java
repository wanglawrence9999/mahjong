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
	private AndjongView mView;

	/** 山 */
	private Yama mYama;

	/** 東一局 */
	public final static int KYOKU_TON_1 = 0;
	/** 東二局 */
	public final static int KYOKU_TON_2 = 1;
	/** 東三局 */
	public final static int KYOKU_TON_3 = 2;
	/** 東四局 */
	public final static int KYOKU_TON_4 = 3;

	/** 局 */
	private int mKyoku;

	/** 局の最大値 */
	private int mKyokuEnd;

	/** ツモ牌 */
	private Hai mTsumoHai;

	/** 捨牌 */
	private Hai mSuteHai;

	/** リーチ棒の数 */
	private int mReachbou;

	/** 本場 */
	private int mHonba;

	/** プレイヤーの人数 */
	private int mPlayerNum;

	/** プレイヤーに提供する情報 */
	private Info mInfo;

	/** プレイヤーの配列 */
	private Player[] mPlayers;

	/** 風をプレイヤーインデックスに変換する配列 */
	private int[] mKazeToPlayerIdx = new int[4];

	/** UIに提供する情報 */
	private InfoUI mInfoUi;

	/** サイコロの配列 */
	private Sai[] mSais = new Sai[] { new Sai(), new Sai() };

	/** 親のプレイヤーインデックス */
	private int mOyaIdx;

	/** 起家のプレイヤーインデックス */
	private int mChiichaIdx;

	/** 連荘 */
	private boolean mRenchan;

	/** イベントを発行した風 */
	private int mFromKaze;

	/** イベントの対象となった風 */
	private int mToKaze;

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
		this.mView = view;
	}

	/**
	 * 山を取得する。
	 *
	 * @return 山
	 */
	Yama getYama() {
		return mYama;
	}

	/**
	 * 局を取得する。
	 *
	 * @return 局
	 */
	int getkyoku() {
		return mKyoku;
	}

	/**
	 * ツモ牌を取得する。
	 *
	 * @return ツモ牌
	 */
	Hai getTsumoHai() {
		return mTsumoHai;
	}

	/**
	 * 捨牌を取得する。
	 *
	 * @return 捨牌
	 */
	Hai getSuteHai() {
		return mSuteHai;
	}

	public int getReachbou() {
		return mReachbou;
	}

	public void setReachbou(int reachbou) {
		mReachbou = reachbou;
	}

	/**
	 * 起家のプレイヤーインデックスを取得する。
	 *
	 * @return 起家のプレイヤーインデックス
	 */
	public int getChiichaIdx() {
		return mChiichaIdx;
	}

	/**
	 * サイコロの配列を取得する。
	 *
	 * @return サイコロの配列
	 */
	Sai[] getSais() {
		return mSais;
	}

	public final static int KAZE_TON = 0;
	public final static int KAZE_NAN = 1;
	public final static int KAZE_SHA = 2;
	public final static int KAZE_PE = 3;
	public final static int KAZE_COUNT_MAX = KAZE_PE + 1;
	public final static int KAZE_NONE = 4;

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

	private PlayerAction mPlayerAction = new PlayerAction();

	public int getManKaze() {
		return mPlayers[0].getJikaze();
	}

	/**
	 * ゲームを開始する。
	 */
	public void play() {
		// 初期化する。
		init();

		// 場所を決める。
		// TODO 未実装。固定とする。

		// イベント（場所決め）を発行する。
		mView.event(EID.BASHOGIME, KAZE_NONE, KAZE_NONE);

		// 親を決める。
		// TODO プレイヤーにサイコロを振らせる機能は未実装。
		mSais[0].saifuri();
		mSais[1].saifuri();
		mOyaIdx = (mSais[0].getNo() + mSais[1].getNo() - 1) % 4;
		mChiichaIdx = mOyaIdx;

		// イベント（親決め）を発行する。
		mView.event(EID.OYAGIME, KAZE_NONE, KAZE_NONE);

		// 局を繰り返して、ゲームを進行する。
		while (mKyoku <= mKyokuEnd) {
			// 局を開始する。
			startKyoku();

			// 連荘の場合、局を進めない。
			if (mRenchan) {
				// イベント（連荘）を発行する。
				mView.event(EID.RENCHAN, KAZE_NONE, KAZE_NONE);
				continue;
			}

			// 局を進める。
			mKyoku++;

			// 本場を初期化する。
			// TODO 上がりの位置に移動しないと。
			mHonba = 0;
		}

		// イベント（ゲームの終了）を発行する。
		mView.event(EID.END, KAZE_NONE, KAZE_NONE);
	}

	/**
	 * 初期化する。
	 */
	private void init() {
		// 山を作成する。
		mYama = new Yama();

		// 局を初期化する。
		mKyoku = KYOKU_TON_1;

		// 局の終了を設定する。
		mKyokuEnd = KYOKU_TON_4;

		// ツモ牌を作成する。
		mTsumoHai = new Hai();

		// 捨牌を作成する。
		mSuteHai = new Hai();

		// リーチ棒の数を初期化する。
		mReachbou = 0;

		// 本場を初期化する。
		mHonba = 0;

		// プレイヤーの人数を設定する。
		mPlayerNum = 4;

		// プレイヤーに提供する情報を作成する。
		mInfo = new Info(this);

		// プレイヤーの配列を初期化する。
		mPlayers = new Player[mPlayerNum];
		mPlayers[0] = new Player((EventIF) new Man(mInfo, "A", mPlayerAction));
		mPlayers[1] = new Player((EventIF) new AI(mInfo, "B"));
		mPlayers[2] = new Player((EventIF) new AI(mInfo, "C"));
		mPlayers[3] = new Player((EventIF) new AI(mInfo, "D"));

		for (int i = 0; i < mPlayerNum; i++) {
			mPlayers[i].setTenbou(TENBOU_INIT);
		}

		// 風をプレイヤーインデックスに変換する配列を初期化する。
		mKazeToPlayerIdx = new int[mPlayers.length];

		// UIに提供する情報を作成する。
		mInfoUi = new InfoUI(this, mPlayerAction);

		// UIを初期化する。
		mView.initUi(mInfoUi, "AndjongView");
	}

	/**
	 * 局を開始する。
	 */
	private void startKyoku() {
		// リーチ棒の数を初期化する。
		// TODO 上がりの位置に移動しないと。
		mReachbou = 0;

		// 連荘を初期化する。
		mRenchan = false;

		// イベントを発行した風を初期化する。
		mFromKaze = mPlayers[mOyaIdx].getJikaze();

		// イベントの対象となった風を初期化する。
		mToKaze = mPlayers[mOyaIdx].getJikaze();

		// プレイヤーの自風を設定する。
		setJikaze();

		// プレイヤー配列を初期化する。
		for (int i = 0; i < mPlayers.length; i++) {
			mPlayers[i].init();
		}

		// 洗牌する。
		mYama.xipai();

		// UIイベント（洗牌）を発行する。
		mView.event(EID.SENPAI, mFromKaze, mToKaze);

		// サイ振りをする。
		mSais[0].saifuri();
		mSais[1].saifuri();

		// UIイベント（サイ振り）を発行する。
		mView.event(EID.SAIFURI, mFromKaze, mToKaze);

		// 山に割れ目を設定する。
		setWareme(mSais);

		// 配牌する。
		haipai();

		if (false) {
			for (int i = 0; i < 13; i++)
				mPlayers[1].getTehai().rmJyunTehai(i);

			mPlayers[1].getTehai().addJyunTehai(new Hai(0));
			mPlayers[1].getTehai().addJyunTehai(new Hai(1));
			mPlayers[1].getTehai().addJyunTehai(new Hai(2));
			mPlayers[1].getTehai().addJyunTehai(new Hai(3));
			mPlayers[1].getTehai().addJyunTehai(new Hai(4));
			mPlayers[1].getTehai().addJyunTehai(new Hai(5));
			mPlayers[1].getTehai().addJyunTehai(new Hai(6));
			mPlayers[1].getTehai().addJyunTehai(new Hai(7));
			mPlayers[1].getTehai().addJyunTehai(new Hai(8));
			mPlayers[1].getTehai().addJyunTehai(new Hai(9));
			mPlayers[1].getTehai().addJyunTehai(new Hai(10));
			mPlayers[1].getTehai().addJyunTehai(new Hai(11));
			mPlayers[1].getTehai().addJyunTehai(new Hai(11));
			/*
			mPlayers[1].getTehai().addJyunTehai(new Hai(0));
			mPlayers[1].getTehai().addJyunTehai(new Hai(0));
			mPlayers[1].getTehai().addJyunTehai(new Hai(0));
			mPlayers[1].getTehai().addJyunTehai(new Hai(2));
			mPlayers[1].getTehai().addJyunTehai(new Hai(2));
			mPlayers[1].getTehai().addJyunTehai(new Hai(2));
			mPlayers[1].getTehai().addJyunTehai(new Hai(4));
			mPlayers[1].getTehai().addJyunTehai(new Hai(4));
			mPlayers[1].getTehai().addJyunTehai(new Hai(4));
			mPlayers[1].getTehai().addJyunTehai(new Hai(6));
			mPlayers[1].getTehai().addJyunTehai(new Hai(6));
			mPlayers[1].getTehai().addJyunTehai(new Hai(6));
			mPlayers[1].getTehai().addJyunTehai(new Hai(8));
			*/
		}

		// UIイベント（配牌）を発行する。
		mView.event(EID.HAIPAI, mFromKaze, mToKaze);

		EID retEid;

		// 局を進行する。
		KYOKU_MAIN: while (true) {
			// UIイベント（進行待ち）を発行する。
			mView.event(EID.PROGRESS_WAIT, KAZE_NONE, KAZE_NONE);

			// ツモ牌を取得する。
			mTsumoHai = mYama.tsumo();

			// ツモ牌がない場合、流局する。
			if (mTsumoHai == null) {
				// UIイベント（流局）を発行する。
				mView.event(EID.RYUUKYOKU, KAZE_NONE, KAZE_NONE);

				// 親を更新する。上がり連荘とする。
				mOyaIdx++;
				if (mOyaIdx >= mPlayers.length) {
					mOyaIdx = 0;
				}

				// 本場を増やす。
				mHonba++;

				break KYOKU_MAIN;
			}

			// イベント（ツモ）を発行する。
			retEid = tsumoEvent();

			// イベントを処理する。
			switch (retEid) {
			case TSUMOAGARI:// ツモあがり
				// UIイベント（ツモあがり）を発行する。
				mView.event(retEid, mFromKaze, mToKaze);

				// TODO 点数を清算する。
				activePlayer.increaseTenbou(mReachbou * 1000);

				// 親を更新する。
				if (mOyaIdx != mKazeToPlayerIdx[mFromKaze]) {
					mOyaIdx++;
					if (mOyaIdx >= mPlayers.length) {
						mOyaIdx = 0;
					}
				} else {
					mRenchan = true;
					mHonba++;
				}

				break KYOKU_MAIN;
			case RON:// ロン
				// UIイベント（ロン）を発行する。
				mView.event(retEid, mFromKaze, mToKaze);

				// TODO 点数を清算する。
				activePlayer.increaseTenbou(mReachbou * 1000);

				// 親を更新する。
				if (mOyaIdx != mKazeToPlayerIdx[mFromKaze]) {
					mOyaIdx++;
					if (mOyaIdx >= mPlayers.length) {
						mOyaIdx = 0;
					}
				} else {
					mRenchan = true;
					mHonba++;
				}

				break KYOKU_MAIN;
			case REACH:// リーチ
				int tenbou = activePlayer.getTenbou();
				if (tenbou >= 1000) {
					activePlayer.reduceTenbou(1000);
					activePlayer.setReach(true);
					mReachbou++;
				}
				break;
			default:
				break;
			}

			// イベントを発行した風を更新する。
			mFromKaze++;
			if (mFromKaze >= mPlayers.length) {
				mFromKaze = 0;
			}
		}
	}

	/**
	 * プレイヤーの自風を設定する。
	 */
	private void setJikaze() {
		for (int i = 0, j = mOyaIdx; i < mPlayers.length; i++, j++) {
			if (j >= mPlayers.length) {
				j = 0;
			}

			// プレイヤーの自風を設定する。
			mPlayers[j].setJikaze(i);

			// 風をプレイヤーインデックスに変換する配列を設定する。
			mKazeToPlayerIdx[i] = j;
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

		mYama.setTsumoHaisStartIdx(startHaisIdx);
	}

	/**
	 * 配牌する。
	 */
	private void haipai() {
		for (int i = 0, j = mOyaIdx, max = mPlayers.length * 13; i < max; i++, j++) {
			if (j >= mPlayers.length) {
				j = 0;
			}

			mPlayers[j].getTehai().addJyunTehai(mYama.tsumo());
		}
	}

	/**
	 * イベント（ツモ）を発行する。
	 *
	 * @return イベントID
	 */
	private EID tsumoEvent() {
		// アクティブプレイヤーを設定する。
		activePlayer = mPlayers[mKazeToPlayerIdx[mFromKaze]];

		// UIイベント（ツモ）を発行する。
		mView.event(EID.TSUMO, mFromKaze, mFromKaze);

		// イベント（ツモ）を発行する。
		EID retEid = activePlayer.getEventIf().event(EID.TSUMO, mFromKaze, mFromKaze);

		// UIイベント（進行待ち）を発行する。
		mView.event(EID.PROGRESS_WAIT, mFromKaze, mFromKaze);

		int sutehaiIdx;

		// イベントを処理する。
		switch (retEid) {
		case TSUMOAGARI:// ツモあがり
			break;
		case SUTEHAI:// 捨牌
			// 捨牌のインデックスを取得する。
			sutehaiIdx = activePlayer.getEventIf().getSutehaiIdx();

			// 理牌の間をとる。
			mInfoUi.setSutehaiIdx(sutehaiIdx);
			mView.event(EID.RIHAI_WAIT, mFromKaze, mFromKaze);

			if (sutehaiIdx == 13) {// ツモ切り
				Hai.copy(mSuteHai, mTsumoHai);
				activePlayer.getKawa().add(mSuteHai);
			} else {// 手出し
				activePlayer.getTehai().copyJyunTehaiIdx(mSuteHai, sutehaiIdx);
				activePlayer.getTehai().rmJyunTehai(sutehaiIdx);
				activePlayer.getTehai().addJyunTehai(mTsumoHai);
				activePlayer.getKawa().add(mSuteHai);
				activePlayer.getKawa().setTedashi(true);
			}

			// イベントを通知する。
			retEid = notifyEvent(EID.SUTEHAI, mFromKaze, mFromKaze);
			break;
		case REACH:
			// 捨牌のインデックスを取得する。
			sutehaiIdx = activePlayer.getEventIf().getSutehaiIdx();
			activePlayer.setReach(true);
			mView.event(EID.RIHAI_WAIT, mFromKaze, mFromKaze);

			if (sutehaiIdx == 13) {// ツモ切り
				Hai.copy(mSuteHai, mTsumoHai);
				activePlayer.getKawa().add(mSuteHai);
				activePlayer.getKawa().setReach(true);
			} else {// 手出し
				activePlayer.getTehai().copyJyunTehaiIdx(mSuteHai, sutehaiIdx);
				activePlayer.getTehai().rmJyunTehai(sutehaiIdx);
				activePlayer.getTehai().addJyunTehai(mTsumoHai);
				activePlayer.getKawa().add(mSuteHai);
				activePlayer.getKawa().setTedashi(true);
				activePlayer.getKawa().setReach(true);
			}

			// イベントを通知する。
			retEid = notifyEvent(EID.REACH, mFromKaze, mFromKaze);
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
	private EID notifyEvent(EID eid, int fromKaze, int toKaze) {
		EID retEid = EID.NAGASHI;

		// UIイベントを発行する。
		mView.event(eid, fromKaze, toKaze);

		// 各プレイヤーにイベントを通知する。
		NOTIFYLOOP: for (int i = 0, j = fromKaze; i < mPlayers.length; i++, j++) {
			if (j >= mPlayers.length) {
				j = 0;
			}

			// アクティブプレイヤーを設定する。
			activePlayer = mPlayers[mKazeToPlayerIdx[j]];

			// UIイベントを発行する。
			//mView.event(eid, fromKaze, toKaze);

			// イベントを発行する。
			retEid = activePlayer.getEventIf().event(eid, fromKaze, toKaze);

			// イベントを処理する。
			switch (retEid) {
			case TSUMOAGARI:// ツモあがり
				// アクティブプレイヤーを設定する。
				this.mFromKaze = j;
				this.mToKaze = toKaze;
				activePlayer = mPlayers[mKazeToPlayerIdx[this.mFromKaze]];
				break NOTIFYLOOP;
			case RON:// ロン
				// アクティブプレイヤーを設定する。
				this.mFromKaze = j;
				this.mToKaze = toKaze;
				activePlayer = mPlayers[mKazeToPlayerIdx[this.mFromKaze]];
				break NOTIFYLOOP;
			case PON:
				// アクティブプレイヤーを設定する。
				this.mFromKaze = j;
				this.mToKaze = fromKaze;
				activePlayer = mPlayers[mKazeToPlayerIdx[this.mFromKaze]];
				activePlayer.getTehai().setPon(mSuteHai, getRelation(this.mFromKaze, this.mToKaze));

				notifyEvent(EID.SUTEHAISELECT, this.mFromKaze, this.mToKaze);

				// 捨牌のインデックスを取得する。
				int sutehaiIdx = activePlayer.getEventIf().getSutehaiIdx();
				activePlayer.getTehai().copyJyunTehaiIdx(mSuteHai, sutehaiIdx);
				activePlayer.getTehai().rmJyunTehai(sutehaiIdx);
				activePlayer.getKawa().add(mSuteHai);
				activePlayer.getKawa().setNaki(true);
				activePlayer.getKawa().setTedashi(true);

				// イベントを通知する。
				retEid = notifyEvent(EID.PON, this.mFromKaze, this.mToKaze);
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
		return mHonba;
	}

	/**
	 * リーチを取得する。
	 *
	 * @param kaze
	 *            風
	 * @return リーチ
	 */
	boolean isReach(int kaze) {
		return mPlayers[mKazeToPlayerIdx[kaze]].isReach();
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
			Tehai.copy(tehai, mPlayers[mKazeToPlayerIdx[kaze]].getTehai(), false);
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
		Tehai.copy(tehai, mPlayers[mKazeToPlayerIdx[kaze]].getTehai(), true);
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
		Kawa.copy(kawa, mPlayers[mKazeToPlayerIdx[kaze]].getKawa());
	}

	/**
	 * ツモの残り数を取得する。
	 *
	 * @return ツモの残り数
	 */
	int getTsumoRemain() {
		return mYama.getTsumoNokori();
	}

	String getName(int kaze) {
		return mPlayers[mKazeToPlayerIdx[kaze]].getEventIf().getName();
	}

	int getTenbou(int kaze) {
		return mPlayers[mKazeToPlayerIdx[kaze]].getTenbou();
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
		// ゲームを開始する。
		play();
	}

	public void setSutehaiIdx(int sutehaiIdx) {
		mInfo.setSutehaiIdx(sutehaiIdx);
	}
}
