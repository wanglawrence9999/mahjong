package jp.sourceforge.andjong;

import jp.sourceforge.andjong.R;
import jp.sourceforge.andjong.DrawItem.PlayerInfo;
import static jp.sourceforge.andjong.DrawItem.*;
import jp.sourceforge.andjong.mahjong.EventIf;
import jp.sourceforge.andjong.mahjong.Fuuro;
import jp.sourceforge.andjong.mahjong.Hai;
import jp.sourceforge.andjong.mahjong.InfoUi;
import jp.sourceforge.andjong.mahjong.Kawa;
import jp.sourceforge.andjong.mahjong.Mahjong;
import jp.sourceforge.andjong.mahjong.PlayerAction;
import jp.sourceforge.andjong.mahjong.SuteHai;
import jp.sourceforge.andjong.mahjong.Tehai;
import jp.sourceforge.andjong.mahjong.Yama;
import jp.sourceforge.andjong.mahjong.AgariScore.AgariInfo;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Paint.Align;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class AndjongView extends View implements EventIf {
	private static final String TAG = "AndjongView";

	/** アクティビティ */
	private Game mGame;

	/** 牌のイメージ */
	private Bitmap[] m_haiImage;
	/** 牌のイメージの幅 */
	private int mHaiImageWidth;
	/** 牌のイメージの高さ */
	private int mHaiImageHeight;

	/** 牌の幅 */
	private static final int HAI_WIDTH = 19;
	/** 牌の高さ */
	private static final int HAI_HEIGHT = 23;

	/** 牌の裏のイメージ */
	private Bitmap mHaiUraImage;

	/** 横になった牌のイメージ */
	private Bitmap[] mHaiHorizontalImage;

	/** 隠している牌のイメージ */
	private Bitmap mHaiHideImage;

	/** 1000点棒のイメージ */
	private Bitmap mTenbou1000Image;
	/** 100点棒のイメージ */
	private Bitmap mTenbou100Image;

	/** 起家マークのイメージ */
	private Bitmap mChiichaImage;

	/** メニュー選択のイメージ */
	private Bitmap mMenuSelectImage;

	/** 背景のペイント */
	private Paint mBackgroundPaint;

	/** メッセージエリアの幅 */
	private static final int MESSAGE_AREA_WIDTH = 146;
	/** メッセージエリアの高さ */
	private static final int MESSAGE_AREA_HEIGHT = 143;

	/** メッセージエリアのLeft */
	private static final int MESSAGE_AREA_LEFT = 87;
	/** メッセージエリアのTop */
	private static final int MESSAGE_AREA_TOP = 176;
	/** メッセージエリアのRight */
	private static final int MESSAGE_AREA_RIGHT = MESSAGE_AREA_LEFT + MESSAGE_AREA_WIDTH;
	/** メッセージエリアのBottom */
	private static final int MESSAGE_AREA_BOTTOM = MESSAGE_AREA_TOP + MESSAGE_AREA_HEIGHT;

	/** メッセージのテキストサイズ */
	private static final int MESSAGE_TEXT_SIZE = 30;

	/** メッセージの枠の丸み */
	private static final int MESSAGE_ROUND = 5;

	/** メッセージのペイント */
	private Paint mMessagePaint;

	/** メッセージの枠 */
	private RectF mMessageRect;

	/*
	 * メニュー
	 */

	/** メニューの個数の最大値 */
	private static final int MENU_ITEM_MAX = 4;

	/** メニューの幅 */
	private static final int MENU_WIDTH = 78;
	/** メニューの高さ */
	private static final int MENU_HEIGHT = 72;

	/** メニューエリアのtop */
	private static final int MENU_AREA_TOP = 406;
	/** メニューエリアのleft */
	private static final int MENU_AREA_LEFT = 0;
	/** メニューエリアのtopのマージン */
	private static final int MENU_AREA_TOP_MARGIN = 1;
	/** メニューエリアのleftのマージン */
	private static final int MENU_AREA_LEFT_MARGIN = 1;

	/** メニューの枠 */
	private RectF m_menuRect[];

	/*
	 * 結果画面
	 */

	/** 結果画面のドラのtop */
	private static final int RESULT_DORAS_TOP = 2;
	/** 結果画面のドラのleft */
	private static final int RESULT_DORAS_LEFT = 2;

	/** 描画アイテム */
	private DrawItem m_drawItem = new DrawItem();

	/** InfoUI */
	private InfoUi m_infoUi;

	/** UIの名前 */
	private String mName;

	/** プレイヤーアクション */
	private PlayerAction m_playerAction;

	/** 局のLeft */
	private static final int KYOKU_LEFT = 160;
	/** 局のTop */
	private static final int KYOKU_TOP = 85 + 11;

	/** 局のテキストサイズ */
	private static final int KYOKU_TEXT_SIZE = 18;

	/** ドラのLeft */
	private static final int DORAS_LEFT = 112;
	/** ドラのTop */
	private static final int DORAS_TOP = 150;

	/** リーチ棒のイメージのLeft */
	private static final int TENBOU_01000_MIN_IMAGE_LEFT = 100;
	/** リーチ棒のイメージのTop */
	private static final int TENBOU_01000_MIN_IMAGE_TOP = 137;

	/** リーチ棒の数のLeft */
	private static final int REACHBOU_LEFT = TENBOU_01000_MIN_IMAGE_LEFT + 43;
	/** リーチ棒の数Top */
	private static final int REACHBOU_TOP = TENBOU_01000_MIN_IMAGE_TOP + 5;

	/** 小さいのテキストサイズ */
	private static final int MINI_TEXT_SIZE = 12;

	/** 点棒のLeft */
	private static final int[] TENBO_LEFT = { 160, 197, 160, 123 };
	/** 点棒のTop */
	private static final int[] TENBO_TOP = { 131, 121, 111, 121 };

	/** 本場のイメージのLeft */
	private static final int TENBOU_00100_MIN_IMAGE_LEFT = 170;
	/** 本場のイメージのTop */
	private static final int TENBOU_00100_MIN_IMAGE_TOP = TENBOU_01000_MIN_IMAGE_TOP;

	/** 本場の数のLeft */
	private static final int HONBA_LEFT = TENBOU_00100_MIN_IMAGE_LEFT + 43;
	/** 本場の数Top */
	private static final int HONBA_TOP = TENBOU_00100_MIN_IMAGE_TOP + 5;

	private boolean mHaiSelectStatus;

	/**
	 * コンストラクタ
	 *
	 * @param context
	 *            アクティビティ
	 */
	public AndjongView(Context context) {
		super(context);

		// アクティビティを保存する。
		this.mGame = (Game) context;

		// イメージを初期化する。
		initImage(getResources());

		// ペイントを初期化する。
		initPaint(getResources());

		// UIが初期化されるまで待つ。
		m_drawItem.setState(STATE_INIT_WAIT);

		// フォーカスを有効にする。
		setFocusable(true);
		setFocusableInTouchMode(true);
	}

	/**
	 * イメージを初期化する。
	 *
	 * @param res
	 *            リソース
	 */
	private void initImage(Resources res) {
		m_haiImage = new Bitmap[Hai.ID_MAX + 1];

		m_haiImage[0] = BitmapFactory.decodeResource(res, R.drawable.hai_00_wan_1);
		m_haiImage[1] = BitmapFactory.decodeResource(res, R.drawable.hai_01_wan_2);
		m_haiImage[2] = BitmapFactory.decodeResource(res, R.drawable.hai_02_wan_3);
		m_haiImage[3] = BitmapFactory.decodeResource(res, R.drawable.hai_03_wan_4);
		m_haiImage[4] = BitmapFactory.decodeResource(res, R.drawable.hai_04_wan_5);
		m_haiImage[5] = BitmapFactory.decodeResource(res, R.drawable.hai_05_wan_6);
		m_haiImage[6] = BitmapFactory.decodeResource(res, R.drawable.hai_06_wan_7);
		m_haiImage[7] = BitmapFactory.decodeResource(res, R.drawable.hai_07_wan_8);
		m_haiImage[8] = BitmapFactory.decodeResource(res, R.drawable.hai_08_wan_9);

		m_haiImage[9] = BitmapFactory.decodeResource(res, R.drawable.hai_09_pin_1);
		m_haiImage[10] = BitmapFactory.decodeResource(res, R.drawable.hai_10_pin_2);
		m_haiImage[11] = BitmapFactory.decodeResource(res, R.drawable.hai_11_pin_3);
		m_haiImage[12] = BitmapFactory.decodeResource(res, R.drawable.hai_12_pin_4);
		m_haiImage[13] = BitmapFactory.decodeResource(res, R.drawable.hai_13_pin_5);
		m_haiImage[14] = BitmapFactory.decodeResource(res, R.drawable.hai_14_pin_6);
		m_haiImage[15] = BitmapFactory.decodeResource(res, R.drawable.hai_15_pin_7);
		m_haiImage[16] = BitmapFactory.decodeResource(res, R.drawable.hai_16_pin_8);
		m_haiImage[17] = BitmapFactory.decodeResource(res, R.drawable.hai_17_pin_9);

		m_haiImage[18] = BitmapFactory.decodeResource(res, R.drawable.hai_18_sou_1);
		m_haiImage[19] = BitmapFactory.decodeResource(res, R.drawable.hai_19_sou_2);
		m_haiImage[20] = BitmapFactory.decodeResource(res, R.drawable.hai_20_sou_3);
		m_haiImage[21] = BitmapFactory.decodeResource(res, R.drawable.hai_21_sou_4);
		m_haiImage[22] = BitmapFactory.decodeResource(res, R.drawable.hai_22_sou_5);
		m_haiImage[23] = BitmapFactory.decodeResource(res, R.drawable.hai_23_sou_6);
		m_haiImage[24] = BitmapFactory.decodeResource(res, R.drawable.hai_24_sou_7);
		m_haiImage[25] = BitmapFactory.decodeResource(res, R.drawable.hai_25_sou_8);
		m_haiImage[26] = BitmapFactory.decodeResource(res, R.drawable.hai_26_sou_9);

		m_haiImage[27] = BitmapFactory.decodeResource(res, R.drawable.hai_27_ton);
		m_haiImage[28] = BitmapFactory.decodeResource(res, R.drawable.hai_28_nan);
		m_haiImage[29] = BitmapFactory.decodeResource(res, R.drawable.hai_29_sha);
		m_haiImage[30] = BitmapFactory.decodeResource(res, R.drawable.hai_30_pei);

		m_haiImage[31] = BitmapFactory.decodeResource(res, R.drawable.hai_31_haku);
		m_haiImage[32] = BitmapFactory.decodeResource(res, R.drawable.hai_32_hatsu);
		m_haiImage[33] = BitmapFactory.decodeResource(res, R.drawable.hai_33_chun);

		mHaiImageWidth = m_haiImage[0].getWidth();
		mHaiImageHeight = m_haiImage[0].getHeight();

		mHaiUraImage = BitmapFactory.decodeResource(res, R.drawable.hai_ura);

		mHaiHorizontalImage = new Bitmap[Hai.ID_MAX + 1];

		for (int i = 0; i < mHaiHorizontalImage.length; i++) {
			mHaiHorizontalImage[i] = createHorizontalBitmap(m_haiImage[i]);
		}

		mHaiHideImage = BitmapFactory.decodeResource(res, R.drawable.hai_hide);

		mTenbou1000Image = BitmapFactory.decodeResource(res, R.drawable.tenbou_1000);
		mTenbou100Image = BitmapFactory.decodeResource(res, R.drawable.tenbou_100);

		mChiichaImage = BitmapFactory.decodeResource(res, R.drawable.chiicha);

		mMenuSelectImage = BitmapFactory.decodeResource(res, R.drawable.menu_select);
	}

	/**
	 * ペイントを初期化する。
	 *
	 * @param res
	 *            リソース
	 */
	private void initPaint(Resources res) {
		mBackgroundPaint = new Paint();
		mBackgroundPaint.setColor(res.getColor(R.color.andjong_background));

		mMessagePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mMessagePaint.setColor(Color.DKGRAY);
		mMessagePaint.setAlpha(200);
		mMessageRect = new RectF(MESSAGE_AREA_LEFT, MESSAGE_AREA_TOP, MESSAGE_AREA_RIGHT, MESSAGE_AREA_BOTTOM);

		// メニューを初期化する。
		m_menuRect = new RectF[MENU_ITEM_MAX];
		int left;
		int top = MENU_AREA_TOP + MENU_AREA_TOP_MARGIN;
		int right;
		int bottom = top + MENU_HEIGHT;
		for (int i = 0; i < m_menuRect.length; i++) {
			left = (MENU_AREA_LEFT + MENU_AREA_LEFT_MARGIN) + ((MENU_WIDTH + (MENU_AREA_LEFT_MARGIN * 2)) * i);
			right = left + MENU_WIDTH;
			m_menuRect[i] = new RectF(left, top, right, bottom);
		}
	}

	/**
	 * 横になったイメージを作成する。
	 *
	 * @param verticalImage
	 *            縦のイメージ
	 * @return 横になったイメージ
	 */
	private Bitmap createHorizontalBitmap(Bitmap verticalImage) {
		int height = verticalImage.getWidth();
		int width = verticalImage.getHeight();
		Bitmap horizontalImage = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(horizontalImage);
		canvas.rotate(270, 0, 0);
		canvas.drawBitmap(verticalImage, -height, 0, null);
		return horizontalImage;
	}

	/**
	 * UIを初期化する。
	 *
	 * @param infoUi
	 *            InfoUi
	 * @param name
	 *            UIの名前
	 */
	public void initUi(InfoUi infoUi, String name) {
		this.m_infoUi = infoUi;
		this.mName = name;

		// プレイヤーアクションを取得する。
		m_playerAction = m_infoUi.getPlayerAction();

		// 状態なしにしておく。
		m_drawItem.setState(STATE_NONE);
	}

	@Override
	protected void onDraw(Canvas a_canvas) {
		// 背景を描画する。
		a_canvas.drawRect(0, 0, getWidth(), getHeight(), mBackgroundPaint);

		synchronized (m_drawItem) {
			switch (m_drawItem.m_state) {
			case STATE_INIT_WAIT:
			case STATE_NONE:
				// 何も描画しない。
				return;
			case STATE_KYOKU_START:
				// 局を表示する。
				drawMessage(a_canvas, m_drawItem.getKyokuString());
				return;
			case STATE_REACH:
				// リーチのメッセージを表示する。
				drawMessage(a_canvas, "リーチ");
				break;
			case STATE_RON:
				// ロンのメッセージを表示する。
				drawMessage(a_canvas, "ロン");
				break;
			case STATE_TSUMO:
				// ツモのメッセージを表示する。
				drawMessage(a_canvas, "ツモ");
				break;
			case STATE_RYUUKYOKU:
				// 流局のメッセージを表示する。
				drawMessage(a_canvas, getResources().getString(R.string.ryuukyoku));
				break;
			case STATE_RESULT:
				// ドラを表示する。
				drawDoraHais(RESULT_DORAS_LEFT, RESULT_DORAS_TOP, a_canvas, m_infoUi.getDoraHais());
				// 裏ドラを表示する。
				drawDoraHais(RESULT_DORAS_LEFT, RESULT_DORAS_TOP + HAI_HEIGHT, a_canvas, m_infoUi.getUraDoraHais());

				if (m_drawItem.m_eventId == EventId.TSUMO_AGARI) {
					drawTehai(2, 50, a_canvas, m_drawItem.m_playerInfos[m_drawItem.m_kazeFrom].m_tehai, m_drawItem.m_playerInfos[m_drawItem.m_kazeFrom].m_tsumoHai, 0, 15, true);
				} else {
					drawTehai(2, 50, a_canvas, m_drawItem.m_playerInfos[m_drawItem.m_kazeFrom].m_tehai, m_drawItem.m_suteHai, 0, 15, true);
				}

				AgariInfo agariInfo = m_infoUi.getAgariInfo();
				int left = 2;
				int top = 50 + 38 + 2 + 10;
				//int left = (MESSAGE_AREA_LEFT + MESSAGE_AREA_RIGHT) / 2;
				//int top = (MESSAGE_AREA_TOP + MESSAGE_AREA_BOTTOM) / 2;
				Log.e(TAG, "length = " + agariInfo.m_yakuNames.length);
				for (int i = 0; i < agariInfo.m_yakuNames.length; i++) {
					drawString(left, top, a_canvas, 20, Color.WHITE, agariInfo.m_yakuNames[i], Align.LEFT);
					top += 20;
				}
				String string = new String();
				string += agariInfo.m_han + "翻 " + agariInfo.m_fu + "符 " + agariInfo.m_score + "点";
				drawString(left, top + 20, a_canvas, 20, Color.WHITE, string, Align.LEFT);
				return;
			}

			// アクションボタンを表示する。
			boolean actionRequest = m_playerAction.isActionRequest();
			if (actionRequest) {
				//drawMessage(canvas, getResources().getString(R.string.action_button));
				int iMenu = 0;

				if (m_playerAction.isValidReach()) {
					drawMenuMessage(a_canvas, "立直", iMenu);
					iMenu++;
				}

				if (m_playerAction.isValidRon()) {
					drawMenuMessage(a_canvas, "ロン", iMenu);
					iMenu++;
				}

				if (m_playerAction.isValidTsumo()) {
					drawMenuMessage(a_canvas, "ツモ", iMenu);
					iMenu++;
				}

				if (m_playerAction.isValidPon()) {
					drawMenuMessage(a_canvas, "ポン", iMenu);
					iMenu++;
				}

				//drawMenuMessage(canvas, "流し", iMenu);

				if (m_playerAction.getMenuSelect() == 0) {
					//canvas.drawBitmap(mMenuSelectImage, MENU1_AREA_LEFT, MENU_AREA_TOP, null);
				} else {
					//canvas.drawBitmap(mMenuSelectImage, MENU2_AREA_LEFT, MENU_AREA_TOP, null);
				}
			}

			// 局を表示する。
			drawString(KYOKU_LEFT, KYOKU_TOP, a_canvas, KYOKU_TEXT_SIZE, Color.WHITE, m_drawItem.getKyokuString(), Align.CENTER);

			// リーチ棒の数を表示する。
			drawReachbou(a_canvas, m_drawItem.getReachbou());

			// 本場を表示する。
			drawHonba(a_canvas, m_drawItem.getHonba());

			// ドラを表示する。
			drawDoraHais(DORAS_LEFT, DORAS_TOP, a_canvas, m_infoUi.getDoraHais());

			int manKaze = m_infoUi.getManKaze();
			Log.e(TAG, "manKaze = " + manKaze);
			int dispKaze[] = { 0, 1, 2, 3 };
			for (int i = 0; i < 4; i++) {
				dispKaze[i] = manKaze;
				manKaze++;
				manKaze %= 4;
			}

			// 点棒を表示する。
			for (int i = 0; i < EventIf.KAZE_KIND_NUM; i++) {
				drawString(TENBO_LEFT[i], TENBO_TOP[i], a_canvas, MINI_TEXT_SIZE, Color.WHITE, new Integer(m_drawItem.m_playerInfos[dispKaze[i]].m_tenbo).toString(), Align.CENTER);
			}

			// 起家マークを表示する。
			drawChiicha(a_canvas, m_drawItem.getChiicha());

			Bitmap test2 = getKawaTehaiAreaImage(m_drawItem.m_playerInfos[dispKaze[0]].m_tehai, m_drawItem.m_playerInfos[dispKaze[0]].m_kawa, PLACE_PLAYER, dispKaze[0], true, m_drawItem.m_playerInfos[dispKaze[0]].m_tsumoHai);
			a_canvas.drawBitmap(test2, KAWA_TEHAI_AREA_PLAYER_LEFT, KAWA_TEHAI_AREA_PLAYER_TOP, null);

			Bitmap test3 = getKawaTehaiAreaImage(m_drawItem.m_playerInfos[dispKaze[1]].m_tehai, m_drawItem.m_playerInfos[dispKaze[1]].m_kawa, PLACE_KAMICHA, dispKaze[1], false, m_drawItem.m_playerInfos[dispKaze[1]].m_tsumoHai);
			a_canvas.drawBitmap(test3, KAWA_TEHAI_AREA_KAMICHA_LEFT, KAWA_TEHAI_AREA_KAMICHA_TOP, null);

			Bitmap test = getKawaTehaiAreaImage(m_drawItem.m_playerInfos[dispKaze[2]].m_tehai, m_drawItem.m_playerInfos[dispKaze[2]].m_kawa, PLACE_TOIMEN, dispKaze[2], false, m_drawItem.m_playerInfos[dispKaze[2]].m_tsumoHai);
			a_canvas.drawBitmap(test, KAWA_TEHAI_AREA_TOIMEN_LEFT, KAWA_TEHAI_AREA_TOIMEN_TOP, null);

			Bitmap test4 = getKawaTehaiAreaImage(m_drawItem.m_playerInfos[dispKaze[3]].m_tehai, m_drawItem.m_playerInfos[dispKaze[3]].m_kawa, PLACE_SHIMOCHA, dispKaze[3], false, m_drawItem.m_playerInfos[dispKaze[3]].m_tsumoHai);
			a_canvas.drawBitmap(test4, KAWA_TEHAI_AREA_SHIMOCHA_LEFT, KAWA_TEHAI_AREA_SHIMOCHA_TOP, null);
		}
	}

	/**
	 * メッセージを表示する。
	 *
	 * @param canvas
	 *            キャンバス
	 * @param string
	 *            文字列
	 */
	private void drawMessage(Canvas canvas, String string) {
		canvas.drawRoundRect(mMessageRect, MESSAGE_ROUND, MESSAGE_ROUND, mMessagePaint);
		drawString((MESSAGE_AREA_LEFT + MESSAGE_AREA_RIGHT) / 2, (MESSAGE_AREA_TOP + MESSAGE_AREA_BOTTOM) / 2, canvas, MESSAGE_TEXT_SIZE, Color.WHITE, string, Align.CENTER);
	}


	private void drawMenuMessage(Canvas canvas, String string, int no) {
		canvas.drawRoundRect(m_menuRect[no], MESSAGE_ROUND, MESSAGE_ROUND, mMessagePaint);
		drawString((int) (m_menuRect[no].left + (MENU_WIDTH / 2)), (int) (m_menuRect[no].top + (MENU_HEIGHT / 2)), canvas, MESSAGE_TEXT_SIZE, Color.WHITE, string, Align.CENTER);
	}

	/**
	 * 文字列を表示する。
	 *
	 * @param left
	 *            Left
	 * @param top
	 *            Top
	 * @param canvas
	 *            キャンバス
	 * @param textSize
	 *            テキストサイズ
	 * @param color
	 *            色
	 * @param string
	 *            文字列
	 */
	private void drawString(int left, int top, Canvas canvas, int textSize,
			int color, String string, Align a_align) {
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(color);
		paint.setTextSize(textSize);
		paint.setTextAlign(a_align);

		canvas.drawText(string, left, top - ((paint.ascent() + paint.descent()) / 2), paint);
	}

	/**
	 * ドラを表示する。
	 *
	 * @param a_left
	 *            left
	 * @param a_top
	 *            top
	 * @param a_canvas
	 *            キャンバス
	 * @param a_hais
	 *            牌の配列
	 */
	private void drawDoraHais(int a_left, int a_top, Canvas a_canvas, Hai a_hais[]) {
		int i = 0;
		for (; i < a_hais.length; i++) {
			a_canvas.drawBitmap(m_haiImage[a_hais[i].getId()], a_left + (i * HAI_WIDTH), a_top, null);
		}

		for (; i < Yama.DORA_HAIS_MAX; i++) {
			a_canvas.drawBitmap(mHaiUraImage, a_left + (i * HAI_WIDTH), a_top, null);
		}
	}

	/**
	 * リーチ棒の数を表示する。
	 *
	 * @param canvas
	 *            キャンバス
	 * @param reachbou
	 *            リーチ棒の数
	 */
	private void drawReachbou(Canvas canvas, int reachbou) {
		canvas.drawBitmap(mTenbou1000Image, TENBOU_01000_MIN_IMAGE_LEFT, TENBOU_01000_MIN_IMAGE_TOP, null);
		drawString(REACHBOU_LEFT, REACHBOU_TOP, canvas, MINI_TEXT_SIZE, Color.WHITE, "x " + new Integer(reachbou).toString(), Align.CENTER);
	}

	/**
	 * 本場を表示する。
	 *
	 * @param canvas
	 *            キャンバス
	 * @param honba
	 *            本場
	 */
	private void drawHonba(Canvas canvas, int honba) {
		canvas.drawBitmap(mTenbou100Image, TENBOU_00100_MIN_IMAGE_LEFT, TENBOU_00100_MIN_IMAGE_TOP, null);
		drawString(HONBA_LEFT, HONBA_TOP, canvas, MINI_TEXT_SIZE, Color.WHITE, "x " + new Integer(honba).toString(), Align.CENTER);
	}

	/**
	 * 起家マークを表示する。
	 *
	 * @param canvas
	 *            キャンバス
	 * @param chiicha
	 *            起家
	 */
	private void drawChiicha(Canvas canvas, int chiicha) {
		canvas.drawBitmap(mChiichaImage, TENBO_LEFT[chiicha] - 26, TENBO_TOP[chiicha] - 5, null);
	}

	private static final int PLACE_PLAYER = 0;
	private static final int PLACE_KAMICHA = 1;
	private static final int PLACE_TOIMEN = 2;
	private static final int PLACE_SHIMOCHA = 3;

	private static final int KAWA_TEHAI_AREA_WIDTH = 320;
	private static final int KAWA_TEHAI_AREA_HEIGHT = 85;

	private static final int TEHAI_LEFT = 2;
	private static final int TEHAI_TOP = 47;

	private static final int KAWA_LEFT = 49;
	private static final int KAWA_TOP = 0;

	private static final int KAWA_TEHAI_AREA_PLAYER_LEFT = 0;
	private static final int KAWA_TEHAI_AREA_PLAYER_TOP = 321;

	private static final int KAWA_TEHAI_AREA_TOIMEN_LEFT = 0;
	private static final int KAWA_TEHAI_AREA_TOIMEN_TOP = 0;

	private static final int KAWA_TEHAI_AREA_KAMICHA_LEFT = 235;
	private static final int KAWA_TEHAI_AREA_KAMICHA_TOP = 47;

	private static final int KAWA_TEHAI_AREA_SHIMOCHA_LEFT = 0;
	private static final int KAWA_TEHAI_AREA_SHIMOCHA_TOP = 38;

	private Bitmap getKawaTehaiAreaImage(Tehai tehai, Kawa kawa, int place, int kaze, boolean isPlayer, Hai tsumoHai) {
		int width;
		int height;
		Bitmap image;
		Canvas canvas;

		switch (place) {
		case PLACE_PLAYER:
			width = KAWA_TEHAI_AREA_WIDTH;
			height = KAWA_TEHAI_AREA_HEIGHT;
			image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
			canvas = new Canvas(image);
			break;
		case PLACE_KAMICHA:
			width = KAWA_TEHAI_AREA_HEIGHT;
			height = KAWA_TEHAI_AREA_WIDTH;
			image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
			canvas = new Canvas(image);
			canvas.rotate(270, 0, 0);
			canvas.translate(-height, 0);
			break;
		case PLACE_TOIMEN:
			width = KAWA_TEHAI_AREA_WIDTH;
			height = KAWA_TEHAI_AREA_HEIGHT;
			image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
			canvas = new Canvas(image);
			canvas.rotate(180, 0, 0);
			canvas.translate(-width, -height);
			break;
		case PLACE_SHIMOCHA:
			width = KAWA_TEHAI_AREA_HEIGHT;
			height = KAWA_TEHAI_AREA_WIDTH;
			image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
			canvas = new Canvas(image);
			canvas.rotate(90, 0, 0);
			canvas.translate(0, -width);
			break;
		default:
			return null;
		}

		drawKawa(KAWA_LEFT, KAWA_TOP, canvas, kawa, null);

		if ((m_infoUi.getManKaze() == kaze)) {
	//	if ((mInfoUi.getManKaze() == kaze) && (drawItem.tsumoKaze == kaze)) {
			drawTehai(TEHAI_LEFT, TEHAI_TOP, canvas, tehai, tsumoHai, kaze, mSelectSutehaiIdx, isPlayer);
		} else {
			drawTehai(TEHAI_LEFT, TEHAI_TOP, canvas, tehai, tsumoHai, kaze, 15, isPlayer);
		}

		return image;
	}

	private void drawKawa(int left, int top, Canvas canvas, Kawa kawa,
			Paint paint) {
		int leftTemp = left;
		SuteHai[] suteHais = kawa.getSuteHais();
		int kawaLength = kawa.getSuteHaisLength();
		boolean reachFlag = false;
		int nakiCount = 0;
		for (int i = 0; i < kawaLength; i++) {
			if (i - nakiCount == 12) {
				left = leftTemp;
				top += mHaiImageHeight;
				nakiCount = 0;
			}

			if (suteHais[i].isReach() || reachFlag) {
				if (suteHais[i].isNaki()) {
					reachFlag = true;
				} else {
					canvas.drawBitmap(mHaiHorizontalImage[suteHais[i].getId()], left, top + ((mHaiImageHeight - mHaiImageWidth) / 2), paint);
					left += mHaiImageHeight - mHaiImageWidth;
					reachFlag = false;
				}
			} else {
				if (!suteHais[i].isNaki()) {
					canvas.drawBitmap(m_haiImage[suteHais[i].getId()], left, top, paint);
				} else {
					left -= mHaiImageWidth;
					nakiCount++;
				}
			}

			left += mHaiImageWidth;
		}
	}

	private static final int FUURO_LEFT = 296;

	private void drawTehai(int left, int top, Canvas canvas, Tehai tehai, Hai a_addHai, int kaze, int select, boolean isPlayer) {
		top += 15;
		boolean isDisp = isPlayer || m_drawItem.m_isDebug;

		Hai[] jyunTehai = tehai.getJyunTehai();
		int jyunTehaiLength = tehai.getJyunTehaiLength();
		int width = m_haiImage[0].getWidth();
		for (int i = 0; i < jyunTehaiLength; i++) {
			if (a_addHai != null && m_drawItem.m_state == STATE_RIHAI_WAIT) {
				if (i == m_drawItem.getSkipIdx()) {
					continue;
				}
			}
			if ((i == select) && (m_playerAction.getState() == PlayerAction.STATE_SUTEHAI_SELECT)) {
				canvas.drawBitmap(m_haiImage[jyunTehai[i].getId()], left + (width * i), top - 10, null);
			} else {
				if (isDisp) {
					canvas.drawBitmap(m_haiImage[jyunTehai[i].getId()], left + (width * i), top, null);
				} else {
					canvas.drawBitmap(mHaiHideImage, left + (width * i), top, null);
				}
			}
		}

		//Log.d(this.getClass().getName(), "print, tsumoKaze = " + mDrawItem.tsumoKaze + ", id = " + mDrawItem.tsumoHai);
		if (a_addHai != null) {
			if ((select >= jyunTehaiLength) && (m_drawItem.m_state != STATE_RIHAI_WAIT) && (m_drawItem.m_state != STATE_RESULT)) {
				if (isDisp) {
					canvas.drawBitmap(m_haiImage[a_addHai.getId()], left + ((width * jyunTehaiLength) + 5), top - 10, null);
				} else {
					canvas.drawBitmap(mHaiHideImage, left + ((width * jyunTehaiLength) + 5), top, null);
				}
			} else {
				if (isDisp) {
					canvas.drawBitmap(m_haiImage[a_addHai.getId()], left + ((width * jyunTehaiLength) + 5), top, null);
				} else {
					canvas.drawBitmap(mHaiHideImage, left + ((width * jyunTehaiLength) + 5), top, null);
				}
			}
		}

		int fuuroLeft = FUURO_LEFT;
		int fuuroNums = tehai.getFuuroNum();
		if (fuuroNums > 0) {
			Fuuro[] fuuros = tehai.getFuuros();
			for (int i = 0; i < fuuroNums; i++) {
				Hai hais[] = fuuros[i].getHais();
				//int type = fuuros[i].getType();
				int relation = fuuros[i].getRelation();

				if (relation == Mahjong.RELATION_KAMICHA) {
					fuuroLeft -= mHaiImageHeight;
					canvas.drawBitmap(mHaiHorizontalImage[hais[2].getId()], fuuroLeft, top + 4, null);
					fuuroLeft -= mHaiImageWidth;
					canvas.drawBitmap(m_haiImage[hais[1].getId()], fuuroLeft, top, null);
					fuuroLeft -= mHaiImageWidth;
					canvas.drawBitmap(m_haiImage[hais[0].getId()], fuuroLeft, top, null);
				} else if (relation == Mahjong.RELATION_TOIMEN) {
					fuuroLeft -= mHaiImageWidth;
					canvas.drawBitmap(m_haiImage[hais[2].getId()], fuuroLeft, top, null);
					fuuroLeft -= mHaiImageHeight;
					canvas.drawBitmap(mHaiHorizontalImage[hais[1].getId()], fuuroLeft, top + 4, null);
					fuuroLeft -= mHaiImageWidth;
					canvas.drawBitmap(m_haiImage[hais[0].getId()], fuuroLeft, top, null);
				} else {
					fuuroLeft -= mHaiImageWidth;
					canvas.drawBitmap(m_haiImage[hais[2].getId()], fuuroLeft, top, null);
					fuuroLeft -= mHaiImageWidth;
					canvas.drawBitmap(m_haiImage[hais[1].getId()], fuuroLeft, top, null);
					fuuroLeft -= mHaiImageHeight;
					canvas.drawBitmap(mHaiHorizontalImage[hais[0].getId()], fuuroLeft, top + 4, null);
				}
			}
		}
	}

	private static final int TOUCH_TOP = 480 - 97;
	private static final int TOUCH_BOTTOM = 480;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int x = (int) event.getX();
		int y = (int) event.getY();
		int action = event.getAction();

		Log.d(TAG, "onTouchEvent: x = " + x + ", y = " + y);

		if (action == MotionEvent.ACTION_UP) {
			//return true;
		}

		boolean actionRequest = m_playerAction.isActionRequest();
		if (actionRequest) {
			if (action == MotionEvent.ACTION_DOWN) {
				int iMenu = 5;
				for (int i = 0; i < m_menuRect.length; i++) {
					if (x >= m_menuRect[i].left && x <= m_menuRect[i].right) {
						if (y >= m_menuRect[i].top && y <= m_menuRect[i].bottom) {
							iMenu = i;
							break;
						}
					}
				}
				Log.d(TAG, "actionRequest actionNotifyAll");
				m_playerAction.setMenuSelect(iMenu);
				m_playerAction.actionNotifyAll();
			}
			return true;
		}

		if (action == MotionEvent.ACTION_DOWN) {
			synchronized (m_drawItem) {
				switch (m_drawItem.m_state) {
				case STATE_PLAY:
					Log.d(TAG, "mDrawItem STATE_PLAY");
					break;
				default:
					Log.d(TAG, "mDrawItem actionNotifyAll");
					m_playerAction.actionNotifyAll();
					return true;
				}
			}
		}

		/* X,Y座標の取得 */
		int tx = (int) event.getX();
		int ty = (int) event.getY();
		int act_evt = event.getAction();

		/* Y座標の判定(牌の高さの間) */
		if ((TOUCH_TOP <= ty) && (ty <= TOUCH_BOTTOM)) {
			if (m_drawItem.m_isManReach) {
				mSelectSutehaiIdx = 13;
			} else {
				int iSelect = (tx - 2) / HAI_WIDTH;
				if (iSelect > 13) {
					iSelect = 13;
				}
				mSelectSutehaiIdx = iSelect;
			}
			mHaiSelectStatus = true;
		} else {
			if (act_evt == MotionEvent.ACTION_DOWN) {
				synchronized (m_drawItem) {
					switch (m_drawItem.m_state) {
					case STATE_PLAY:
						Log.d(TAG, "STATE_PLAY actionNotifyAll");
						m_playerAction.setSutehaiIdx(mSelectSutehaiIdx);
						m_playerAction.actionNotifyAll();
						break;
					default:
						Log.d(TAG, "default actionNotifyAll");
						m_playerAction.actionNotifyAll();
						break;
					}
				}
			}
		}
		/* 再描画の指示 */
		invalidate();
		return true;
	}


	private int mSelectSutehaiIdx = 0;
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.d(TAG, "onKeyDown: keycode=" + keyCode + ", event=" + event);
		boolean actionRequest = m_playerAction.isActionRequest();
		int menuSelect = m_playerAction.getMenuSelect();
		if (actionRequest) {
			switch (keyCode) {
			case KeyEvent.KEYCODE_DPAD_LEFT:
				menuSelect--;
				menuSelect %= 2;
				m_playerAction.setMenuSelect(menuSelect);
				break;
			case KeyEvent.KEYCODE_DPAD_RIGHT:
				menuSelect++;
				menuSelect %= 2;
				m_playerAction.setMenuSelect(menuSelect);
				break;
			case KeyEvent.KEYCODE_ENTER:
			case KeyEvent.KEYCODE_DPAD_CENTER:
				Log.d(TAG, "KEYCODE_DPAD_CENTER actionNotifyAll");
				m_playerAction.actionNotifyAll();
				break;
			default:
				return super.onKeyDown(keyCode, event);
			}
			invalidate();
			return true;
		}

		int state = m_playerAction.getState();
		switch (keyCode) {
		case KeyEvent.KEYCODE_DPAD_UP:
			if (state != PlayerAction.STATE_SUTEHAI_SELECT) {
				return true;
			}
			mSelectSutehaiIdx = 0;
			if(m_drawItem.m_isDebug){
				m_drawItem.m_isDebug = false;
			}else{
				m_drawItem.m_isDebug = true;
			}
			break;
		case KeyEvent.KEYCODE_DPAD_DOWN:
			if (state != PlayerAction.STATE_SUTEHAI_SELECT) {
				return true;
			}
			mSelectSutehaiIdx = 100;
			break;
		case KeyEvent.KEYCODE_DPAD_LEFT:
			if (state != PlayerAction.STATE_SUTEHAI_SELECT) {
				return true;
			}
			mSelectSutehaiIdx--;
			if (mSelectSutehaiIdx < 0) {
				mSelectSutehaiIdx = 13;
			}
			break;
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			if (state != PlayerAction.STATE_SUTEHAI_SELECT) {
				return true;
			}
			mSelectSutehaiIdx++;
			if (mSelectSutehaiIdx > 13) {
				mSelectSutehaiIdx = 0;
			}
			break;
		case KeyEvent.KEYCODE_ENTER:
		case KeyEvent.KEYCODE_DPAD_CENTER:
			synchronized (m_drawItem) {
				switch (m_drawItem.m_state) {
				case STATE_PLAY:
					Log.d(TAG, "STATE_PLAY actionNotifyAll");
					m_playerAction.setSutehaiIdx(mSelectSutehaiIdx);
					m_playerAction.actionNotifyAll();
					break;
				default:
					Log.d(TAG, "default actionNotifyAll");
					m_playerAction.actionNotifyAll();
					break;
				}
			}

			break;
		default:
			return super.onKeyDown(keyCode, event);
		}
		if (m_drawItem.m_isManReach) {
			mSelectSutehaiIdx = 13;
		}
		invalidate();
		return true;
	}

	/** 捨牌のインデックス */
	private int mSutehaiIdx = 0;

	public String getName() {
		return mName;
	}

    //private int mSkipIdx = 0;

    /** 進行の待ち時間 */
	private static int PROGRESS_WAIT_TIME = 100;

	/**
	 * イベントを処理する。
	 *
	 * @param a_kazeFrom
	 *            イベントを発行した家
	 * @param a_kazeTo
	 *            イベントの対象の家
	 * @param a_eventId
	 *            イベントID
	 */
	public EventId event(EventId a_eventId, int a_kazeFrom, int a_kazeTo) {
		m_drawItem.m_eventId = a_eventId;
		m_drawItem.m_kazeFrom = a_kazeFrom;
		m_drawItem.m_kazeTo = a_kazeTo;

		Log.d(TAG, "eid = " + a_eventId.toString());
		switch (a_eventId) {
		case UI_WAIT_PROGRESS:// 進行待ち
			try {
				Thread.sleep(PROGRESS_WAIT_TIME, 0);
			} catch (InterruptedException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			break;
		case START_GAME:// 親決め
			// この段階で初期化されている情報を設定する。
			m_drawItem.setState(STATE_NONE);
			synchronized (m_drawItem) {
				// 点棒を設定する。
				for (int i = 0; i < m_drawItem.m_playerInfos.length; i++) {
					m_drawItem.m_playerInfos[i].m_tenbo = m_infoUi.getTenbou(i);
				}

				// リーチ棒の数を設定する。
				m_drawItem.setReachbou(m_infoUi.getReachbou());

				// 本場を設定する。
				m_drawItem.setHonba(m_infoUi.getHonba());

				// 起家を設定する。
				m_drawItem.setChiicha(m_infoUi.getChiichaIdx());
			}
			break;
		case START_KYOKU:// サイ振り
			// サイ振りを局の開始と考える。
			m_drawItem.m_isManReach = false;

			// 局の文字列を設定する。
			m_drawItem.setKyokuString(getResources(), m_infoUi.getkyoku());

			m_drawItem.setState(STATE_KYOKU_START);

			// 描画する。
			this.postInvalidate(0, 0, getWidth(), getHeight());

			// アクションを待つ。
			m_playerAction.actionWait();
//			break;
//		case HAIPAI:
			// 手牌を設定して、プレイ状態にする。
			m_drawItem.setState(STATE_PLAY);
			synchronized (m_drawItem) {
				for (int i = 0; i < m_drawItem.m_playerInfos.length; i++) {
					m_infoUi.copyTehai(m_drawItem.m_playerInfos[i].m_tehai, i);
					m_infoUi.copyKawa(m_drawItem.m_playerInfos[i].m_kawa, i);
					m_drawItem.m_playerInfos[i].m_tsumoHai = null;
					m_drawItem.m_playerInfos[i].m_tenbo = m_infoUi.getTenbou(i);
				}
			}

			// 描画する。
			this.postInvalidate(0, 0, getWidth(), getHeight());
			break;
		case RYUUKYOKU:// 流局
			// サイ振りを局の開始と考える。
			m_drawItem.setState(STATE_RYUUKYOKU);

			// 描画する。
			this.postInvalidate(0, 0, getWidth(), getHeight());

			// アクションを待つ。
			m_playerAction.actionWait();
			break;
		case NAGASHI:// 流し
			// 何も表示しない。
			break;
		case TSUMO:// ツモ
			// ツモ牌を取得する。
			m_drawItem.m_playerInfos[m_infoUi.getJikaze()].m_tsumoHai = m_infoUi.getTsumoHai();

			// 描画する。
			this.postInvalidate(0, 0, getWidth(), getHeight());
			break;
		case TSUMO_AGARI:// ツモあがり
			m_drawItem.setState(STATE_TSUMO);
			m_infoUi.copyTehai(m_drawItem.m_playerInfos[a_kazeFrom].m_tehai, a_kazeFrom);
			m_drawItem.m_playerInfos[a_kazeFrom].m_tsumoHai = m_infoUi.getTsumoHai();

//			synchronized (m_drawItem) {
//				for (int i = 0; i < m_drawItem.m_playerInfos.length; i++) {
//					Log.d(TAG, "getTenbou = " + m_infoUi.getTenbou(i));
//					m_drawItem.m_playerInfos[i].m_tenbo = m_infoUi.getTenbou(i);
//				}
//			}

			// 描画する。
			this.postInvalidate(0, 0, getWidth(), getHeight());

			// アクションを待つ。
			m_playerAction.actionWait();

			// 結果画面を表示する。
			m_drawItem.m_state = STATE_RESULT;

			// アクションを待つ。
			m_playerAction.actionWait();
			break;
		case SUTEHAI:// 捨牌
			Log.e(TAG, "SUTEHAI fromKaze = " + a_kazeFrom);
			// 手牌をコピーする。
			m_infoUi.copyTehai(m_drawItem.m_playerInfos[a_kazeFrom].m_tehai, a_kazeFrom);

			// 河をコピーする。
			m_infoUi.copyKawa(m_drawItem.m_playerInfos[a_kazeFrom].m_kawa, a_kazeFrom);

			// ツモ牌をなくす。
			m_drawItem.m_playerInfos[a_kazeFrom].m_tsumoHai = null;

			// 描画する。
			this.postInvalidate(0, 0, getWidth(), getHeight());
			break;
		case SELECT_SUTEHAI:
			if (a_kazeFrom == m_infoUi.getJikaze()) {
				for (int i = 0; i < m_drawItem.m_playerInfos.length; i++) {
					m_infoUi.copyTehai(m_drawItem.m_playerInfos[i].m_tehai, i);
				}
					//mDrawItem.tsumoKaze = 5;
					//mDrawItem.tsumoHai = null;
					//mDrawItem.tsumoHais[mInfoUi.getJikaze()] = null;
					this.postInvalidate(0, 0, getWidth(), getHeight());
			}
			break;
		case UI_WAIT_RIHAI:
			m_drawItem.setSkipIdx(m_infoUi.getSutehaiIdx());
			m_drawItem.m_state = STATE_RIHAI_WAIT;
			this.postInvalidate(0, 0, getWidth(), getHeight());
			try {
				Thread.sleep(PROGRESS_WAIT_TIME, 0);
			} catch (InterruptedException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			mSelectSutehaiIdx = 13;
			m_drawItem.m_state = STATE_PLAY;
			break;
		case PON:// ポン
			// 自分の捨牌のみを表示します。
			if (a_kazeFrom == m_infoUi.getJikaze()) {
				{
					for (int i = 0; i < m_drawItem.m_playerInfos.length; i++) {
						m_infoUi.copyTehai(m_drawItem.m_playerInfos[i].m_tehai, i);
						m_infoUi.copyKawa(m_drawItem.m_playerInfos[i].m_kawa, i);
					}
					//mDrawItem.tsumoKaze = 5;
					//mDrawItem.tsumoHai = null;
					//mDrawItem.tsumoHais[mInfoUi.getJikaze()] = null;
					this.postInvalidate(0, 0, getWidth(), getHeight());
				}

				/*
				System.out.print("[" + jikazeToString(mInfoUi.getJikaze())
						+ "][ポン]");

				// 手牌を表示します。
				printJyunTehai(tehai);

				System.out.println();

				System.out.print("[" + jikazeToString(mInfoUi.getJikaze())
						+ "][捨牌]");

				// 河を表示します。
				printKawa(kawa);

				// 捨牌を表示します。
				// System.out.println(":"
				// + idToString((infoUi.getSuteHai()).getId()));
				System.out.println();
				*/
			}
			break;
		case REACH:
			Log.e(TAG, "REACH fromKaze = " + a_kazeFrom);
			// 手牌をコピーする。
			m_infoUi.copyTehai(m_drawItem.m_playerInfos[a_kazeFrom].m_tehai, a_kazeFrom);

			// 河をコピーする。
			m_infoUi.copyKawa(m_drawItem.m_playerInfos[a_kazeFrom].m_kawa, a_kazeFrom);

			// ツモ牌をなくす。
			m_drawItem.m_playerInfos[a_kazeFrom].m_tsumoHai = null;

			if (m_infoUi.getManKaze() == a_kazeFrom) {
				m_drawItem.m_isManReach = true;
				//mSelectSutehaiIdx = m_playerAction.m_indexs[0];
			}
			m_drawItem.m_state = STATE_REACH;
			this.postInvalidate(0, 0, getWidth(), getHeight());
			try {
				Thread.sleep(1000, 0);
			} catch (InterruptedException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			m_drawItem.m_state = STATE_PLAY;
			// 描画する。
			this.postInvalidate(0, 0, getWidth(), getHeight());
			break;
		case RON_AGARI:// ロン
			Log.d(TAG, "RON_AGARI");

			// 手牌をコピーする。
			m_infoUi.copyTehai(m_drawItem.m_playerInfos[a_kazeFrom].m_tehai, a_kazeFrom);
			m_drawItem.m_suteHai = m_infoUi.getSuteHai();

//			synchronized (m_drawItem) {
//				for (int i = 0; i < m_drawItem.m_playerInfos.length; i++) {
//					Log.d(TAG, "getTenbou = " + m_infoUi.getTenbou(i));
//					m_drawItem.m_playerInfos[i].m_tenbo = m_infoUi.getTenbou(i);
//				}
//			}

			m_drawItem.m_state = STATE_RON;
			this.postInvalidate(0, 0, getWidth(), getHeight());

			// アクションを待つ。
			m_playerAction.actionWait();

			// 結果画面を表示する。
			m_drawItem.m_state = STATE_RESULT;

			// アクションを待つ。
			m_playerAction.actionWait();
			break;
		default:
			break;
		}

		return EventId.NAGASHI;
	}

	public int getISutehai() {
		return mSutehaiIdx;
	}

	/**
	 * 成立している役を表示します。
	 *
	 * @param jikaze
	 *            自風
	 * @return　
	 */
	public void jikazeToString(Hai addHai) {
		/*
		String[] yakuNames = this.mInfoUi.getYakuName(tehai, addHai);
		for (int i = 0; i < yakuNames.length; i++) {
			System.out.println(yakuNames[i]);
		}
		*/
	}
}