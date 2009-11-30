package jp.sourceforge.andjong;

import jp.sourceforge.andjong.R;
import jp.sourceforge.andjong.DrawItem.PlayerInfo;
import jp.sourceforge.andjong.mahjong.EventIF;
import jp.sourceforge.andjong.mahjong.Fuuro;
import jp.sourceforge.andjong.mahjong.Hai;
import jp.sourceforge.andjong.mahjong.InfoUI;
import jp.sourceforge.andjong.mahjong.Kawa;
import jp.sourceforge.andjong.mahjong.Mahjong;
import jp.sourceforge.andjong.mahjong.PlayerAction;
import jp.sourceforge.andjong.mahjong.SuteHai;
import jp.sourceforge.andjong.mahjong.Tehai;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class AndjongView extends View implements EventIF {
	private static final String TAG = "AndjongView";

	/** アクティビティ */
	private Game mGame;

	/** 牌のイメージ */
	private Bitmap[] mHaiImage;
	/** 牌のイメージの幅 */
	private int mHaiImageWidth;
	/** 牌のイメージの高さ */
	private int mHaiImageHeight;

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

	/** 描画アイテム */
	private DrawItem mDrawItem = new DrawItem();

	/** InfoUI */
	private InfoUI mInfoUi;

	/** UIの名前 */
	private String mName;

	/** プレイヤーアクション */
	private PlayerAction mPlayerAction;

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
		mDrawItem.setState(DrawItem.STATE_INIT_WAIT);

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
		mHaiImage = new Bitmap[Hai.ID_MAX + 1];

		mHaiImage[0] = BitmapFactory.decodeResource(res, R.drawable.hai_00_wan_1);
		mHaiImage[1] = BitmapFactory.decodeResource(res, R.drawable.hai_01_wan_2);
		mHaiImage[2] = BitmapFactory.decodeResource(res, R.drawable.hai_02_wan_3);
		mHaiImage[3] = BitmapFactory.decodeResource(res, R.drawable.hai_03_wan_4);
		mHaiImage[4] = BitmapFactory.decodeResource(res, R.drawable.hai_04_wan_5);
		mHaiImage[5] = BitmapFactory.decodeResource(res, R.drawable.hai_05_wan_6);
		mHaiImage[6] = BitmapFactory.decodeResource(res, R.drawable.hai_06_wan_7);
		mHaiImage[7] = BitmapFactory.decodeResource(res, R.drawable.hai_07_wan_8);
		mHaiImage[8] = BitmapFactory.decodeResource(res, R.drawable.hai_08_wan_9);

		mHaiImage[9] = BitmapFactory.decodeResource(res, R.drawable.hai_09_pin_1);
		mHaiImage[10] = BitmapFactory.decodeResource(res, R.drawable.hai_10_pin_2);
		mHaiImage[11] = BitmapFactory.decodeResource(res, R.drawable.hai_11_pin_3);
		mHaiImage[12] = BitmapFactory.decodeResource(res, R.drawable.hai_12_pin_4);
		mHaiImage[13] = BitmapFactory.decodeResource(res, R.drawable.hai_13_pin_5);
		mHaiImage[14] = BitmapFactory.decodeResource(res, R.drawable.hai_14_pin_6);
		mHaiImage[15] = BitmapFactory.decodeResource(res, R.drawable.hai_15_pin_7);
		mHaiImage[16] = BitmapFactory.decodeResource(res, R.drawable.hai_16_pin_8);
		mHaiImage[17] = BitmapFactory.decodeResource(res, R.drawable.hai_17_pin_9);

		mHaiImage[18] = BitmapFactory.decodeResource(res, R.drawable.hai_18_sou_1);
		mHaiImage[19] = BitmapFactory.decodeResource(res, R.drawable.hai_19_sou_2);
		mHaiImage[20] = BitmapFactory.decodeResource(res, R.drawable.hai_20_sou_3);
		mHaiImage[21] = BitmapFactory.decodeResource(res, R.drawable.hai_21_sou_4);
		mHaiImage[22] = BitmapFactory.decodeResource(res, R.drawable.hai_22_sou_5);
		mHaiImage[23] = BitmapFactory.decodeResource(res, R.drawable.hai_23_sou_6);
		mHaiImage[24] = BitmapFactory.decodeResource(res, R.drawable.hai_24_sou_7);
		mHaiImage[25] = BitmapFactory.decodeResource(res, R.drawable.hai_25_sou_8);
		mHaiImage[26] = BitmapFactory.decodeResource(res, R.drawable.hai_26_sou_9);

		mHaiImage[27] = BitmapFactory.decodeResource(res, R.drawable.hai_27_ton);
		mHaiImage[28] = BitmapFactory.decodeResource(res, R.drawable.hai_28_nan);
		mHaiImage[29] = BitmapFactory.decodeResource(res, R.drawable.hai_29_sha);
		mHaiImage[30] = BitmapFactory.decodeResource(res, R.drawable.hai_30_pei);

		mHaiImage[31] = BitmapFactory.decodeResource(res, R.drawable.hai_31_haku);
		mHaiImage[32] = BitmapFactory.decodeResource(res, R.drawable.hai_32_hatsu);
		mHaiImage[33] = BitmapFactory.decodeResource(res, R.drawable.hai_33_chun);

		mHaiImageWidth = mHaiImage[0].getWidth();
		mHaiImageHeight = mHaiImage[0].getHeight();

		mHaiUraImage = BitmapFactory.decodeResource(res, R.drawable.hai_ura);

		mHaiHorizontalImage = new Bitmap[Hai.ID_MAX + 1];

		for (int i = 0; i < mHaiHorizontalImage.length; i++) {
			mHaiHorizontalImage[i] = createHorizontalBitmap(mHaiImage[i]);
		}

		mHaiHideImage = BitmapFactory.decodeResource(res, R.drawable.hai_hide);

		mTenbou1000Image = BitmapFactory.decodeResource(res, R.drawable.tenbou_1000);
		mTenbou100Image = BitmapFactory.decodeResource(res, R.drawable.tenbou_100);

		mChiichaImage = BitmapFactory.decodeResource(res, R.drawable.chiicha);
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

		mMessageRect = new RectF(MESSAGE_AREA_LEFT, MESSAGE_AREA_TOP, MESSAGE_AREA_RIGHT, MESSAGE_AREA_BOTTOM);
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
	public void initUi(InfoUI infoUi, String name) {
		this.mInfoUi = infoUi;
		this.mName = name;

		// プレイヤーアクションを取得する。
		mPlayerAction = mInfoUi.getPlayerAction();

		// 状態なしにしておく。
		mDrawItem.setState(DrawItem.STATE_NONE);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// 背景を描画する。
		canvas.drawRect(0, 0, getWidth(), getHeight(), mBackgroundPaint);

		synchronized (mDrawItem) {
			switch (mDrawItem.mState) {
			case DrawItem.STATE_INIT_WAIT:
			case DrawItem.STATE_NONE:
				// 何も描画しない。
				return;
			case DrawItem.STATE_KYOKU_START:
				// 局を表示する。
				drawMessage(canvas, mDrawItem.getKyokuString());
				return;
			case DrawItem.STATE_RYUUKYOKU:
				// 流局のメッセージを表示する。
				drawMessage(canvas, getResources().getString(R.string.ryuukyoku));
				break;
			}

			// アクションボタンを表示する。
			boolean actionRequest = mPlayerAction.isActionRequest();
			if (actionRequest) {
				drawMessage(canvas, getResources().getString(R.string.action_button));
			}

			// 局を表示する。
			drawString(KYOKU_LEFT, KYOKU_TOP, canvas, KYOKU_TEXT_SIZE, Color.WHITE, mDrawItem.getKyokuString());

			// リーチ棒の数を表示する。
			drawReachbou(canvas, mDrawItem.getReachbou());

			// 本場を表示する。
			drawHonba(canvas, mDrawItem.getHonba());

			// ドラを表示する。
			drawDoras(canvas);

			int manKaze = mInfoUi.getManKaze();
			int dispKaze[] = { 0, 1, 2, 3 };
			for (int i = 0; i < 4; i++) {
				dispKaze[i] = manKaze;
				manKaze++;
				manKaze %= 4;
			}

			// 点棒を表示する。
			for (int i = 0; i < Mahjong.KAZE_COUNT_MAX; i++) {
				drawString(TENBO_LEFT[dispKaze[i]], TENBO_TOP[dispKaze[i]], canvas, MINI_TEXT_SIZE, Color.WHITE, new Integer(mDrawItem.mPlayerInfos[dispKaze[0]].mTenbo).toString());
			}

			// 起家マークを表示する。
			drawChiicha(canvas, mDrawItem.getChiicha());

			Bitmap test2 = getKawaTehaiAreaImage(mDrawItem.mPlayerInfos[dispKaze[0]].mTehai, mDrawItem.mPlayerInfos[dispKaze[0]].mKawa, PLACE_PLAYER, dispKaze[0], true, mDrawItem.mPlayerInfos[dispKaze[0]].mTsumoHai);
			canvas.drawBitmap(test2, KAWA_TEHAI_AREA_PLAYER_LEFT, KAWA_TEHAI_AREA_PLAYER_TOP, null);

			Bitmap test3 = getKawaTehaiAreaImage(mDrawItem.mPlayerInfos[dispKaze[1]].mTehai, mDrawItem.mPlayerInfos[dispKaze[1]].mKawa, PLACE_KAMICHA, dispKaze[1], false, mDrawItem.mPlayerInfos[dispKaze[1]].mTsumoHai);
			canvas.drawBitmap(test3, KAWA_TEHAI_AREA_KAMICHA_LEFT, KAWA_TEHAI_AREA_KAMICHA_TOP, null);

			Bitmap test = getKawaTehaiAreaImage(mDrawItem.mPlayerInfos[dispKaze[2]].mTehai, mDrawItem.mPlayerInfos[dispKaze[2]].mKawa, PLACE_TOIMEN, dispKaze[2], false, mDrawItem.mPlayerInfos[dispKaze[2]].mTsumoHai);
			canvas.drawBitmap(test, KAWA_TEHAI_AREA_TOIMEN_LEFT, KAWA_TEHAI_AREA_TOIMEN_TOP, null);

			Bitmap test4 = getKawaTehaiAreaImage(mDrawItem.mPlayerInfos[dispKaze[3]].mTehai, mDrawItem.mPlayerInfos[dispKaze[3]].mKawa, PLACE_SHIMOCHA, dispKaze[3], false, mDrawItem.mPlayerInfos[dispKaze[3]].mTsumoHai);
			canvas.drawBitmap(test4, KAWA_TEHAI_AREA_SHIMOCHA_LEFT, KAWA_TEHAI_AREA_SHIMOCHA_TOP, null);
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
		drawString((MESSAGE_AREA_LEFT + MESSAGE_AREA_RIGHT) / 2, (MESSAGE_AREA_TOP + MESSAGE_AREA_BOTTOM) / 2, canvas, MESSAGE_TEXT_SIZE, Color.WHITE, string);
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
			int color, String string) {
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(color);
		paint.setTextSize(textSize);
		paint.setTextAlign(Paint.Align.CENTER);

		canvas.drawText(string, left, top - ((paint.ascent() + paint.descent()) / 2), paint);
	}

	/**
	 * ドラを表示する。
	 *
	 * @param canvas
	 *            キャンバス
	 */
	private void drawDoras(Canvas canvas) {
		Hai doras[] = mInfoUi.getDoras();
		for (int i = 0; i < 5; i++) {
			if (i < doras.length) {
				canvas.drawBitmap(mHaiImage[doras[i].getId()], DORAS_LEFT + (i * mHaiImageWidth), DORAS_TOP, null);
			} else {
				canvas.drawBitmap(mHaiUraImage, DORAS_LEFT + (i * mHaiImageWidth), DORAS_TOP, null);
			}
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
		drawString(REACHBOU_LEFT, REACHBOU_TOP, canvas, MINI_TEXT_SIZE, Color.WHITE, "x " + new Integer(reachbou).toString());
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
		drawString(HONBA_LEFT, HONBA_TOP, canvas, MINI_TEXT_SIZE, Color.WHITE, "x " + new Integer(honba).toString());
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

		if ((mInfoUi.getManKaze() == kaze)) {
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
		for (int i = 0; i < kawaLength; i++) {
			if (i == 12) {
				left = leftTemp;
				top += mHaiImageHeight;
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
					canvas.drawBitmap(mHaiImage[suteHais[i].getId()], left, top, paint);
				}
			}

			left += mHaiImageWidth;
		}
	}

	private static final int FUURO_LEFT = 296;

	private void drawTehai(int left, int top, Canvas canvas, Tehai tehai, Hai tsumoHai, int kaze, int select, boolean isPlayer) {
		top += 15;
		boolean isDisp = isPlayer || mDrawItem.mIsDebug;

		Hai[] jyunTehai = tehai.getJyunTehai();
		int jyunTehaiLength = tehai.getJyunTehaiLength();
		int width = mHaiImage[0].getWidth();
		for (int i = 0; i < jyunTehaiLength; i++) {
			if (tsumoHai != null && mDrawItem.mState == DrawItem.STATE_RIHAI_WAIT) {
				if (i == mDrawItem.getSkipIdx()) {
					continue;
				}
			}
			if (i == select) {
				canvas.drawBitmap(mHaiImage[jyunTehai[i].getId()], left + (width * i), top - 10, null);
			} else {
				if (isDisp) {
					canvas.drawBitmap(mHaiImage[jyunTehai[i].getId()], left + (width * i), top, null);
				} else {
					canvas.drawBitmap(mHaiHideImage, left + (width * i), top, null);
				}
			}
		}

		//Log.d(this.getClass().getName(), "print, tsumoKaze = " + mDrawItem.tsumoKaze + ", id = " + mDrawItem.tsumoHai);
		if (tsumoHai != null) {
			if ((select >= jyunTehaiLength) && (mDrawItem.mState != DrawItem.STATE_RIHAI_WAIT)) {
				if (isDisp) {
					canvas.drawBitmap(mHaiImage[tsumoHai.getId()], left + ((width * jyunTehaiLength) + 5), top - 10, null);
				} else {
					canvas.drawBitmap(mHaiHideImage, left + ((width * jyunTehaiLength) + 5), top, null);
				}
			} else {
				if (isDisp) {
					canvas.drawBitmap(mHaiImage[tsumoHai.getId()], left + ((width * jyunTehaiLength) + 5), top, null);
				} else {
					canvas.drawBitmap(mHaiHideImage, left + ((width * jyunTehaiLength) + 5), top, null);
				}
			}
		}

		int fuuroLeft = FUURO_LEFT;
		int fuuroNums = tehai.getFuuroNums();
		if (fuuroNums > 0) {
			Fuuro[] fuuros = tehai.getFuuros();
			for (int i = 0; i < fuuroNums; i++) {
				Hai hais[] = fuuros[i].getHai();
				//int type = fuuros[i].getType();
				int relation = fuuros[i].getRelation();

				if (relation == Mahjong.RELATION_KAMICHA) {
					fuuroLeft -= mHaiImageHeight;
					canvas.drawBitmap(mHaiHorizontalImage[hais[2].getId()], fuuroLeft, top + 4, null);
					fuuroLeft -= mHaiImageWidth;
					canvas.drawBitmap(mHaiImage[hais[1].getId()], fuuroLeft, top, null);
					fuuroLeft -= mHaiImageWidth;
					canvas.drawBitmap(mHaiImage[hais[0].getId()], fuuroLeft, top, null);
				} else if (relation == Mahjong.RELATION_TOIMEN) {
					fuuroLeft -= mHaiImageWidth;
					canvas.drawBitmap(mHaiImage[hais[2].getId()], fuuroLeft, top, null);
					fuuroLeft -= mHaiImageHeight;
					canvas.drawBitmap(mHaiHorizontalImage[hais[1].getId()], fuuroLeft, top + 4, null);
					fuuroLeft -= mHaiImageWidth;
					canvas.drawBitmap(mHaiImage[hais[0].getId()], fuuroLeft, top, null);
				} else {
					fuuroLeft -= mHaiImageWidth;
					canvas.drawBitmap(mHaiImage[hais[2].getId()], fuuroLeft, top, null);
					fuuroLeft -= mHaiImageWidth;
					canvas.drawBitmap(mHaiImage[hais[1].getId()], fuuroLeft, top, null);
					fuuroLeft -= mHaiImageHeight;
					canvas.drawBitmap(mHaiHorizontalImage[hais[0].getId()], fuuroLeft, top + 4, null);
				}
			}
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		synchronized (mDrawItem) {
			switch (mDrawItem.mState) {
			case DrawItem.STATE_PLAY:
				break;
			default:
				mPlayerAction.actionNotifyAll();
				return true;
			}
		}

		boolean actionRequest = mPlayerAction.isActionRequest();
		if (actionRequest) {
			int tx = (int)event.getX();
			int ty = (int)event.getY();
			int act_evt = event.getAction();
			if (act_evt == MotionEvent.ACTION_DOWN) {
				if (tx >= MESSAGE_AREA_LEFT && tx <= MESSAGE_AREA_RIGHT) {
					if (ty >= MESSAGE_AREA_TOP && ty <= MESSAGE_AREA_BOTTOM) {
						//showAlertDialog("MENU");
					}
				}
			}
			return true;
		}

/*
 		if (event.getAction() != MotionEvent.ACTION_DOWN)
			return super.onTouchEvent(event);

*/
		/* X,Y座標の取得 */
		int tx = (int)event.getX();
		int ty = (int)event.getY();
		int act_evt = event.getAction();

		/* Y座標の判定(牌の高さの間) */
		if ((397 <= ty) && (ty <= 426) )
		{
			/* X座標の判定(牌1の横の範囲) */
			if ((3 <= tx) && (tx <= 21) )
			{
				/* 選択牌の番号を保持 */
				mSelectSutehaiIdx = 0;
				mHaiSelectStatus = true;
			}
			/* X座標の判定(牌2の横の範囲) */
			else if ((22 <= tx) && (tx <= 40) )
			{
				/* 選択牌の番号を保持 */
				mSelectSutehaiIdx = 1;

				/* 牌を選択状態 */
				mHaiSelectStatus = true;
			}
			/* X座標の判定(牌3の横の範囲) */
			else if ((41 <= tx) && (tx <= 59) )
			{
				/* 選択牌の番号を保持 */
				mSelectSutehaiIdx = 2;

				/* 牌を選択状態 */
				mHaiSelectStatus = true;
			}
			/* X座標の判定(牌4の横の範囲) */
			else if ((60 <= tx) && (tx <= 78) )
			{
				/* 選択牌の番号を保持 */
				mSelectSutehaiIdx = 3;

				/* 牌を選択状態 */
				mHaiSelectStatus = true;
			}
			/* X座標の判定(牌5の横の範囲) */
			else if ((79 <= tx) && (tx <= 97) )
			{
				/* 選択牌の番号を保持 */
				mSelectSutehaiIdx = 4;

				/* 牌を選択状態 */
				mHaiSelectStatus = true;
			}
			/* X座標の判定(牌6の横の範囲) */
			else if ((98 <= tx) && (tx <= 116) )
			{
				/* 選択牌の番号を保持 */
				mSelectSutehaiIdx = 5;

				/* 牌を選択状態 */
				mHaiSelectStatus = true;
			}
			/* X座標の判定(牌7の横の範囲) */
			else if ((117 <= tx) && (tx <= 136) )
			{
				/* 選択牌の番号を保持 */
				mSelectSutehaiIdx = 6;

				/* 牌を選択状態 */
				mHaiSelectStatus = true;
			}
			/* X座標の判定(牌8の横の範囲) */
			else if ((137 <= tx) && (tx <= 155) )
			{
				/* 選択牌の番号を保持 */
				mSelectSutehaiIdx = 7;

				/* 牌を選択状態 */
				mHaiSelectStatus = true;
			}
			/* X座標の判定(牌9の横の範囲) */
			else if ((156 <= tx) && (tx <= 174) )
			{
				/* 選択牌の番号を保持 */
				mSelectSutehaiIdx = 8;

				/* 牌を選択状態 */
				mHaiSelectStatus = true;
			}
			/* X座標の判定(牌10の横の範囲) */
			else if ((175 <= tx) && (tx <= 191) )
			{
				/* 選択牌の番号を保持 */
				mSelectSutehaiIdx = 9;

				/* 牌を選択状態 */
				mHaiSelectStatus = true;
			}
			/* X座標の判定(牌11の横の範囲) */
			else if ((192 <= tx) && (tx <= 211) )
			{
				/* 選択牌の番号を保持 */
				mSelectSutehaiIdx = 10;

				/* 牌を選択状態 */
				mHaiSelectStatus = true;
			}
			/* X座標の判定(牌12の横の範囲) */
			else if ((212 <= tx) && (tx <= 230) )
			{
				/* 選択牌の番号を保持 */
				mSelectSutehaiIdx = 11;

				/* 牌を選択状態 */
				mHaiSelectStatus = true;
			}
			/* X座標の判定(牌13の横の範囲) */
			else if ((231 <= tx) && (tx <= 249) )
			{
				/* 選択牌の番号を保持 */
				mSelectSutehaiIdx = 12;

				/* 牌を選択状態 */
				mHaiSelectStatus = true;
			}
			/* X座標の判定(牌14の横の範囲) */
			else if ((256 <= tx) && (tx <= 274) )
			{
				/* 選択牌の番号を保持 */
				mSelectSutehaiIdx = 13;

				/* 牌を選択状態 */
				mHaiSelectStatus = true;
			}
			else
			{
				// do nothing
			}
		}
		else
		{
			/* 牌が選択状態・イベントACTION_UP・Y座標が385以下の時、牌が捨てられたとする */
			if ((mHaiSelectStatus == true) && (act_evt == MotionEvent.ACTION_UP) && (ty <= 385))
			{
				mInfoUi.getPlayerAction().setSutehaiIdx(mSelectSutehaiIdx);
				/* 牌を非選択状態にする */
				mHaiSelectStatus = false;
			}
			else if (act_evt == MotionEvent.ACTION_MOVE)
			{
				// ACTION_MOVEイベントの場合は、牌の状態を変更しない。(処理を何もしない)
			}
			else
			{
				/* 牌を非選択状態にする */
				mHaiSelectStatus = false;
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
		switch (keyCode) {
		case KeyEvent.KEYCODE_DPAD_UP:
			mSelectSutehaiIdx = 0;
			if(mDrawItem.mIsDebug){
				mDrawItem.mIsDebug = false;
			}else{
				mDrawItem.mIsDebug = true;
			}
			break;
		case KeyEvent.KEYCODE_DPAD_DOWN:
			mSelectSutehaiIdx = 100;
			break;
		case KeyEvent.KEYCODE_DPAD_LEFT:
			mSelectSutehaiIdx--;
			if (mSelectSutehaiIdx < 0) {
				mSelectSutehaiIdx = 13;
			}
			break;
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			mSelectSutehaiIdx++;
			if (mSelectSutehaiIdx > 13) {
				mSelectSutehaiIdx = 0;
			}
			break;
		case KeyEvent.KEYCODE_ENTER:
		case KeyEvent.KEYCODE_DPAD_CENTER:
			synchronized (mDrawItem) {
				switch (mDrawItem.mState) {
				case DrawItem.STATE_PLAY:
					mPlayerAction.setSutehaiIdx(mSelectSutehaiIdx);
					mPlayerAction.actionNotifyAll();
					break;
				default:
					mPlayerAction.actionNotifyAll();
					break;
				}
			}

			break;
		default:
			return super.onKeyDown(keyCode, event);
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
	private static int PROGRESS_WAIT_TIME = 300;

	/**
	 * イベントを処理する。
	 *
	 * @param fromKaze
	 *            イベントを発行した家
	 * @param toKaze
	 *            イベントの対象の家
	 * @param eid
	 *            イベントID
	 */
	public EID event(EID eid, int fromKaze, int toKaze) {
		switch (eid) {
		case PROGRESS_WAIT:// 進行待ち
			try {
				Thread.sleep(PROGRESS_WAIT_TIME, 0);
			} catch (InterruptedException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			break;
		case BASHOGIME:// 場所決め
			// 何も表示しない。
			break;
		case OYAGIME:// 親決め
			// この段階で初期化されている情報を設定する。
			mDrawItem.setState(DrawItem.STATE_NONE);
			synchronized (mDrawItem) {
				// 点棒を設定する。
				for (int i = 0; i < mDrawItem.mPlayerInfos.length; i++) {
					mDrawItem.mPlayerInfos[i].mTenbo = mInfoUi.getTenbou(i);
				}

				// リーチ棒の数を設定する。
				mDrawItem.setReachbou(mInfoUi.getReachbou());

				// 本場を設定する。
				mDrawItem.setHonba(mInfoUi.getHonba());

				// 起家を設定する。
				mDrawItem.setChiicha(mInfoUi.getChiichaIdx());
			}
			break;
		case SENPAI:// 洗牌
			// 何も表示しない。
			break;
		case SAIFURI:// サイ振り
			// サイ振りを局の開始と考える。

			// 局の文字列を設定する。
			mDrawItem.setKyokuString(getResources(), mInfoUi.getkyoku());

			mDrawItem.setState(DrawItem.STATE_KYOKU_START);

			// 描画する。
			this.postInvalidate(0, 0, getWidth(), getHeight());

			// アクションを待つ。
			mPlayerAction.actionWait();
			break;
		case HAIPAI:
			// 手牌を設定して、プレイ状態にする。
			mDrawItem.setState(DrawItem.STATE_PLAY);
			synchronized (mDrawItem) {
				for (int i = 0; i < mDrawItem.mPlayerInfos.length; i++) {
					mInfoUi.copyTehai(mDrawItem.mPlayerInfos[i].mTehai, i);
					mDrawItem.mPlayerInfos[i].mTsumoHai = null;
				}
			}

			// 描画する。
			this.postInvalidate(0, 0, getWidth(), getHeight());
			break;
		case RYUUKYOKU:// 流局
			// サイ振りを局の開始と考える。
			mDrawItem.setState(DrawItem.STATE_RYUUKYOKU);

			// 描画する。
			this.postInvalidate(0, 0, getWidth(), getHeight());

			// アクションを待つ。
			mPlayerAction.actionWait();
			break;
		case NAGASHI:// 流し
			// 何も表示しない。
			break;
		case TSUMO:// ツモ
			// ツモ牌を取得する。
			mDrawItem.mPlayerInfos[mInfoUi.getJikaze()].mTsumoHai = mInfoUi.getTsumoHai();

			// 描画する。
			this.postInvalidate(0, 0, getWidth(), getHeight());
			break;
		case TSUMOAGARI:// ツモあがり
			/*
			System.out.print("[" + jikazeToString(mInfoUi.getJikaze())
					+ "][ツモあがり]");

			// 手牌を表示します。
			printJyunTehai(tehai);

			// ツモ牌を表示します。
			System.out
					.println(":" + idToString((mInfoUi.getTsumoHai()).getId()));
					*/
			break;
		case SUTEHAI:// 捨牌
			// 手牌をコピーする。
			mInfoUi.copyTehai(mDrawItem.mPlayerInfos[fromKaze].mTehai, fromKaze);

			// 河をコピーする。
			mInfoUi.copyKawa(mDrawItem.mPlayerInfos[fromKaze].mKawa, fromKaze);

			// ツモ牌をなくす。
			mDrawItem.mPlayerInfos[fromKaze].mTsumoHai = null;

			// 描画する。
			this.postInvalidate(0, 0, getWidth(), getHeight());
			break;
		case SUTEHAISELECT:
			if (fromKaze == mInfoUi.getJikaze()) {
				for (int i = 0; i < mDrawItem.mPlayerInfos.length; i++) {
					mInfoUi.copyTehai(mDrawItem.mPlayerInfos[i].mTehai, i);
				}
					//mDrawItem.tsumoKaze = 5;
					//mDrawItem.tsumoHai = null;
					//mDrawItem.tsumoHais[mInfoUi.getJikaze()] = null;
					this.postInvalidate(0, 0, getWidth(), getHeight());
			}
			break;
		case RIHAI_WAIT:
			mDrawItem.setSkipIdx(mInfoUi.getSutehaiIdx());
			mDrawItem.mState = DrawItem.STATE_RIHAI_WAIT;
			this.postInvalidate(0, 0, getWidth(), getHeight());
			try {
				Thread.sleep(200, 0);
			} catch (InterruptedException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			mSelectSutehaiIdx = 13;
			mDrawItem.mState = DrawItem.STATE_PLAY;
			break;
		case PON:// ポン
			// 自分の捨牌のみを表示します。
			if (fromKaze == mInfoUi.getJikaze()) {
				{
					for (int i = 0; i < mDrawItem.mPlayerInfos.length; i++) {
						mInfoUi.copyTehai(mDrawItem.mPlayerInfos[i].mTehai, i);
						mInfoUi.copyKawa(mDrawItem.mPlayerInfos[i].mKawa, i);
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
			/*
			// 自分の捨牌のみを表示します。
			if (fromKaze == mInfoUi.getJikaze()) {
				System.out.print("[" + jikazeToString(mInfoUi.getJikaze())
						+ "][リーチ]");

				// 河を表示します。
				printKawa(kawa);

				// 捨牌を表示します。
				// System.out.println(":"
				// + idToString((infoUi.getSuteHai()).getId()));
				System.out.println();
			}
			*/
			break;
		case RON:// ロン
			/*
			this.post(new Runnable() {
				public void run() {
					String[] yaku;
					yaku = mInfoUi.getYakuName(tehai, mInfoUi.getSuteHai());
					showAlertDialog("ロン" + yaku[0]);
				}
			});

			System.out
					.print("[" + jikazeToString(mInfoUi.getJikaze()) + "][ロン]");

			// 手牌を表示します。
			printJyunTehai(tehai);

			// 当たり牌を表示します。
			System.out.println(":" + idToString((mInfoUi.getSuteHai()).getId()));
			*/
			break;
		default:
			break;
		}

		return EID.NAGASHI;
	}

	public int getSutehaiIdx() {
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