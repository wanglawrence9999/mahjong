/***
 * Excerpted from "Hello, Android!",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material,
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose.
 * Visit http://www.pragmaticprogrammer.com/titles/eband for more book information.
 ***/

package jp.sourceforge.andjong;

import static jp.sourceforge.andjong.mahjong.Hai.*;
import jp.sourceforge.andjong.R;
import jp.sourceforge.andjong.mahjong.EventIF;
import jp.sourceforge.andjong.mahjong.Fuuro;
import jp.sourceforge.andjong.mahjong.Hai;
import jp.sourceforge.andjong.mahjong.InfoUI;
import jp.sourceforge.andjong.mahjong.Kawa;
import jp.sourceforge.andjong.mahjong.Mahjong;
import jp.sourceforge.andjong.mahjong.Sai;
import jp.sourceforge.andjong.mahjong.SuteHai;
import jp.sourceforge.andjong.mahjong.Tehai;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;

public class AndjongView extends View implements EventIF {
	private static final String TAG = "Andjong";

	private static final String SELX = "selX";
	private static final String SELY = "selY";
	private static final String VIEW_STATE = "viewState";
	private static final int ID = 42;

	private float width; // マスの横の長さ
	private float height; // マスの縦の長さ
	private int selX; // 選択されたマスのX軸の添字
	private int selY; // 選択されたマスのY軸の添字
	private final Rect selRect = new Rect();

	private final Game game;

	private HaiBitmap haiBitmap;

	class HaiBitmap {
		public HaiBitmap(
				Resources res) {
			top = new Bitmap[Hai.ID_MAX + 1];

			top[0] = BitmapFactory.decodeResource(res, R.drawable.hai_00_wan_1_top);
			top[1] = BitmapFactory.decodeResource(res, R.drawable.hai_01_wan_2_top);
			top[2] = BitmapFactory.decodeResource(res, R.drawable.hai_02_wan_3_top);
			top[3] = BitmapFactory.decodeResource(res, R.drawable.hai_03_wan_4_top);
			top[4] = BitmapFactory.decodeResource(res, R.drawable.hai_04_wan_5_top);
			top[5] = BitmapFactory.decodeResource(res, R.drawable.hai_05_wan_6_top);
			top[6] = BitmapFactory.decodeResource(res, R.drawable.hai_06_wan_7_top);
			top[7] = BitmapFactory.decodeResource(res, R.drawable.hai_07_wan_8_top);
			top[8] = BitmapFactory.decodeResource(res, R.drawable.hai_08_wan_9_top);

			top[9] = BitmapFactory.decodeResource(res, R.drawable.hai_09_pin_1_top);
			top[10] = BitmapFactory.decodeResource(res, R.drawable.hai_10_pin_2_top);
			top[11] = BitmapFactory.decodeResource(res, R.drawable.hai_11_pin_3_top);
			top[12] = BitmapFactory.decodeResource(res, R.drawable.hai_12_pin_4_top);
			top[13] = BitmapFactory.decodeResource(res, R.drawable.hai_13_pin_5_top);
			top[14] = BitmapFactory.decodeResource(res, R.drawable.hai_14_pin_6_top);
			top[15] = BitmapFactory.decodeResource(res, R.drawable.hai_15_pin_7_top);
			top[16] = BitmapFactory.decodeResource(res, R.drawable.hai_16_pin_8_top);
			top[17] = BitmapFactory.decodeResource(res, R.drawable.hai_17_pin_9_top);

			top[18] = BitmapFactory.decodeResource(res, R.drawable.hai_18_sou_1_top);
			top[19] = BitmapFactory.decodeResource(res, R.drawable.hai_19_sou_2_top);
			top[20] = BitmapFactory.decodeResource(res, R.drawable.hai_20_sou_3_top);
			top[21] = BitmapFactory.decodeResource(res, R.drawable.hai_21_sou_4_top);
			top[22] = BitmapFactory.decodeResource(res, R.drawable.hai_22_sou_5_top);
			top[23] = BitmapFactory.decodeResource(res, R.drawable.hai_23_sou_6_top);
			top[24] = BitmapFactory.decodeResource(res, R.drawable.hai_24_sou_7_top);
			top[25] = BitmapFactory.decodeResource(res, R.drawable.hai_25_sou_8_top);
			top[26] = BitmapFactory.decodeResource(res, R.drawable.hai_26_sou_9_top);

			top[27] = BitmapFactory.decodeResource(res, R.drawable.hai_27_ton_top);
			top[28] = BitmapFactory.decodeResource(res, R.drawable.hai_28_nan_top);
			top[29] = BitmapFactory.decodeResource(res, R.drawable.hai_29_sha_top);
			top[30] = BitmapFactory.decodeResource(res, R.drawable.hai_30_pei_top);

			top[31] = BitmapFactory.decodeResource(res, R.drawable.hai_31_haku_top);
			top[32] = BitmapFactory.decodeResource(res, R.drawable.hai_32_hatsu_top);
			top[33] = BitmapFactory.decodeResource(res, R.drawable.hai_33_chun_top);

			topUra = BitmapFactory.decodeResource(res, R.drawable.hai_ura_top);

			topSide = new Bitmap[Hai.ID_MAX + 1];

			topSide[0] = makeRotateBitmap(BitmapFactory.decodeResource(res, R.drawable.hai_00_wan_1_top));
			topSide[1] = makeRotateBitmap(BitmapFactory.decodeResource(res, R.drawable.hai_01_wan_2_top));
			topSide[2] = makeRotateBitmap(BitmapFactory.decodeResource(res, R.drawable.hai_02_wan_3_top));
			topSide[3] = makeRotateBitmap(BitmapFactory.decodeResource(res, R.drawable.hai_03_wan_4_top));
			topSide[4] = makeRotateBitmap(BitmapFactory.decodeResource(res, R.drawable.hai_04_wan_5_top));
			topSide[5] = makeRotateBitmap(BitmapFactory.decodeResource(res, R.drawable.hai_05_wan_6_top));
			topSide[6] = makeRotateBitmap(BitmapFactory.decodeResource(res, R.drawable.hai_06_wan_7_top));
			topSide[7] = makeRotateBitmap(BitmapFactory.decodeResource(res, R.drawable.hai_07_wan_8_top));
			topSide[8] = makeRotateBitmap(BitmapFactory.decodeResource(res, R.drawable.hai_08_wan_9_top));

			topSide[9] = makeRotateBitmap(BitmapFactory.decodeResource(res, R.drawable.hai_09_pin_1_top));
			topSide[10] = makeRotateBitmap(BitmapFactory.decodeResource(res, R.drawable.hai_10_pin_2_top));
			topSide[11] = makeRotateBitmap(BitmapFactory.decodeResource(res, R.drawable.hai_11_pin_3_top));
			topSide[12] = makeRotateBitmap(BitmapFactory.decodeResource(res, R.drawable.hai_12_pin_4_top));
			topSide[13] = makeRotateBitmap(BitmapFactory.decodeResource(res, R.drawable.hai_13_pin_5_top));
			topSide[14] = makeRotateBitmap(BitmapFactory.decodeResource(res, R.drawable.hai_14_pin_6_top));
			topSide[15] = makeRotateBitmap(BitmapFactory.decodeResource(res, R.drawable.hai_15_pin_7_top));
			topSide[16] = makeRotateBitmap(BitmapFactory.decodeResource(res, R.drawable.hai_16_pin_8_top));
			topSide[17] = makeRotateBitmap(BitmapFactory.decodeResource(res, R.drawable.hai_17_pin_9_top));

			topSide[18] = makeRotateBitmap(BitmapFactory.decodeResource(res, R.drawable.hai_18_sou_1_top));
			topSide[19] = makeRotateBitmap(BitmapFactory.decodeResource(res, R.drawable.hai_19_sou_2_top));
			topSide[20] = makeRotateBitmap(BitmapFactory.decodeResource(res, R.drawable.hai_20_sou_3_top));
			topSide[21] = makeRotateBitmap(BitmapFactory.decodeResource(res, R.drawable.hai_21_sou_4_top));
			topSide[22] = makeRotateBitmap(BitmapFactory.decodeResource(res, R.drawable.hai_22_sou_5_top));
			topSide[23] = makeRotateBitmap(BitmapFactory.decodeResource(res, R.drawable.hai_23_sou_6_top));
			topSide[24] = makeRotateBitmap(BitmapFactory.decodeResource(res, R.drawable.hai_24_sou_7_top));
			topSide[25] = makeRotateBitmap(BitmapFactory.decodeResource(res, R.drawable.hai_25_sou_8_top));
			topSide[26] = makeRotateBitmap(BitmapFactory.decodeResource(res, R.drawable.hai_26_sou_9_top));

			topSide[27] = makeRotateBitmap(BitmapFactory.decodeResource(res, R.drawable.hai_27_ton_top));
			topSide[28] = makeRotateBitmap(BitmapFactory.decodeResource(res, R.drawable.hai_28_nan_top));
			topSide[29] = makeRotateBitmap(BitmapFactory.decodeResource(res, R.drawable.hai_29_sha_top));
			topSide[30] = makeRotateBitmap(BitmapFactory.decodeResource(res, R.drawable.hai_30_pei_top));

			topSide[31] = makeRotateBitmap(BitmapFactory.decodeResource(res, R.drawable.hai_31_haku_top));
			topSide[32] = makeRotateBitmap(BitmapFactory.decodeResource(res, R.drawable.hai_32_hatsu_top));
			topSide[33] = makeRotateBitmap(BitmapFactory.decodeResource(res, R.drawable.hai_33_chun_top));

			bottom = new Bitmap[Hai.ID_MAX + 1];

			bottom[0] = BitmapFactory.decodeResource(res, R.drawable.hai_00_wan_1_bottom);
			bottom[1] = BitmapFactory.decodeResource(res, R.drawable.hai_01_wan_2_bottom);
			bottom[2] = BitmapFactory.decodeResource(res, R.drawable.hai_02_wan_3_bottom);
			bottom[3] = BitmapFactory.decodeResource(res, R.drawable.hai_03_wan_4_bottom);
			bottom[4] = BitmapFactory.decodeResource(res, R.drawable.hai_04_wan_5_bottom);
			bottom[5] = BitmapFactory.decodeResource(res, R.drawable.hai_05_wan_6_bottom);
			bottom[6] = BitmapFactory.decodeResource(res, R.drawable.hai_06_wan_7_bottom);
			bottom[7] = BitmapFactory.decodeResource(res, R.drawable.hai_07_wan_8_bottom);
			bottom[8] = BitmapFactory.decodeResource(res, R.drawable.hai_08_wan_9_bottom);

			bottom[9] = BitmapFactory.decodeResource(res, R.drawable.hai_09_pin_1_bottom);
			bottom[10] = BitmapFactory.decodeResource(res, R.drawable.hai_10_pin_2_bottom);
			bottom[11] = BitmapFactory.decodeResource(res, R.drawable.hai_11_pin_3_bottom);
			bottom[12] = BitmapFactory.decodeResource(res, R.drawable.hai_12_pin_4_bottom);
			bottom[13] = BitmapFactory.decodeResource(res, R.drawable.hai_13_pin_5_bottom);
			bottom[14] = BitmapFactory.decodeResource(res, R.drawable.hai_14_pin_6_bottom);
			bottom[15] = BitmapFactory.decodeResource(res, R.drawable.hai_15_pin_7_bottom);
			bottom[16] = BitmapFactory.decodeResource(res, R.drawable.hai_16_pin_8_bottom);
			bottom[17] = BitmapFactory.decodeResource(res, R.drawable.hai_17_pin_9_bottom);

			bottom[18] = BitmapFactory.decodeResource(res, R.drawable.hai_18_sou_1_bottom);
			bottom[19] = BitmapFactory.decodeResource(res, R.drawable.hai_19_sou_2_bottom);
			bottom[20] = BitmapFactory.decodeResource(res, R.drawable.hai_20_sou_3_bottom);
			bottom[21] = BitmapFactory.decodeResource(res, R.drawable.hai_21_sou_4_bottom);
			bottom[22] = BitmapFactory.decodeResource(res, R.drawable.hai_22_sou_5_bottom);
			bottom[23] = BitmapFactory.decodeResource(res, R.drawable.hai_23_sou_6_bottom);
			bottom[24] = BitmapFactory.decodeResource(res, R.drawable.hai_24_sou_7_bottom);
			bottom[25] = BitmapFactory.decodeResource(res, R.drawable.hai_25_sou_8_bottom);
			bottom[26] = BitmapFactory.decodeResource(res, R.drawable.hai_26_sou_9_bottom);

			bottom[27] = BitmapFactory.decodeResource(res, R.drawable.hai_27_ton_bottom);
			bottom[28] = BitmapFactory.decodeResource(res, R.drawable.hai_28_nan_bottom);
			bottom[29] = BitmapFactory.decodeResource(res, R.drawable.hai_29_sha_bottom);
			bottom[30] = BitmapFactory.decodeResource(res, R.drawable.hai_30_pei_bottom);

			bottom[31] = BitmapFactory.decodeResource(res, R.drawable.hai_31_haku_bottom);
			bottom[32] = BitmapFactory.decodeResource(res, R.drawable.hai_32_hatsu_bottom);
			bottom[33] = BitmapFactory.decodeResource(res, R.drawable.hai_33_chun_bottom);

			bottomUra = BitmapFactory.decodeResource(res, R.drawable.hai_ura_bottom);
		}

		public Bitmap[] top;
		public Bitmap topUra;
		public Bitmap[] topSide;

		public Bitmap[] bottom;
		public Bitmap bottomUra;

		public Bitmap hide;

		private Bitmap makeRotateBitmap(
				Bitmap bitmap) {
			// 高さと幅を入れ替えたビットマップを作成する。
			int height = bitmap.getWidth();
			int width = bitmap.getHeight();
			Bitmap rotateBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(rotateBitmap);
			canvas.rotate(270, 0, 0);
			canvas.drawBitmap(bitmap, -height, 0, null);
			return rotateBitmap;
		}
	}

	public AndjongView(Context context) {

		super(context);
		this.game = (Game) context;
		setFocusable(true);
		setFocusableInTouchMode(true);

		// ...
		setId(ID);

		haiBitmap = new HaiBitmap(getResources());
		initSaiImage(getResources());

		initBaImage();

		initTenbouImage(getResources());
	}

	private class DrawItem {
		/** onDrawフラグ */
		boolean isOnDraw = false;

		/** イベントID */
		EID eid;

		/** 手牌 */
		Tehai tehais[] = new Tehai[4];

		/** 河 */
		Kawa kawas[] = new Kawa[4];

		{
			for (int i = 0; i < 4; i++) {
				tehais[i] = new Tehai();
				kawas[i] = new Kawa();
			}
		}

		/** ツモ牌 */
		Hai tsumoHai = new Hai();
		int tsumoKaze = 5;
	}

	private DrawItem drawItem = new DrawItem();

	@Override
	protected Parcelable onSaveInstanceState() {
		Parcelable p = super.onSaveInstanceState();
		Log.d(TAG, "onSaveInstanceState");
		Bundle bundle = new Bundle();
		bundle.putInt(SELX, selX);
		bundle.putInt(SELY, selY);
		bundle.putParcelable(VIEW_STATE, p);
		return bundle;
	}

	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		Log.d(TAG, "onRestoreInstanceState");
		Bundle bundle = (Bundle) state;
		select(bundle.getInt(SELX), bundle.getInt(SELY));
		super.onRestoreInstanceState(bundle.getParcelable(VIEW_STATE));
		return;
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		width = w / 9f;
		height = h / 9f;
		getRect(selX, selY, selRect);
		Log.d(TAG, "onSizeChanged: width " + width + ", height " + height);
		super.onSizeChanged(w, h, oldw, oldh);
	}

	private static final int SAI_IMAGE_LEFT = 1;
	private static final int SAI_IMAGE_TOP = 1;

	private int mSaiImageLeft[];
	private int mSaiImageTop[];

	private Bitmap mSaiImage[] = new Bitmap[6];

	private void initSaiImage(
			Resources res) {
		mSaiImage[0] = BitmapFactory.decodeResource(res, R.drawable.sai_1);
		mSaiImage[1] = BitmapFactory.decodeResource(res, R.drawable.sai_2);
		mSaiImage[2] = BitmapFactory.decodeResource(res, R.drawable.sai_3);
		mSaiImage[3] = BitmapFactory.decodeResource(res, R.drawable.sai_4);
		mSaiImage[4] = BitmapFactory.decodeResource(res, R.drawable.sai_5);
		mSaiImage[5] = BitmapFactory.decodeResource(res, R.drawable.sai_6);
	}

	private static final int DORA_IMAGE_LEFT = 1;
	private static final int DORA_IMAGE_TOP = 39;

	private int mDoraImageLeft;
	private int mDoraImageTop;
	private int mUraDoraImageLeft;
	private int mUraDoraImageTop;

	private Bitmap tenbou01000MinImage;
	private Bitmap tenbou00100MinImage;

	private static final int TENBOU_01000_MIN_IMAGE_LEFT = 1;
	private static final int TENBOU_01000_MIN_IMAGE_TOP = 27;

	private static final int TENBOU_00100_MIN_IMAGE_LEFT = 51;
	private static final int TENBOU_00100_MIN_IMAGE_TOP = 27;

	private void initTenbouImage(
			Resources res) {
		tenbou00100MinImage = BitmapFactory.decodeResource(res, R.drawable.tenbou_00100_min);
		tenbou01000MinImage = BitmapFactory.decodeResource(res, R.drawable.tenbou_01000_min);
	}

	private static final int BA_IMAGE_WIDTH = 110;
	private static final int BA_IMAGE_HEIGHT = 213;
	private static final int BA_IMAGE_LEFT = 105;
	private static final int BA_IMAGE_TOP = 107;

	private Bitmap mBaImage;

	private Canvas mBaCanvas;

	private void initBaImage() {
		mBaImage = Bitmap.createBitmap(BA_IMAGE_WIDTH, BA_IMAGE_HEIGHT, Bitmap.Config.ARGB_8888);
		mBaCanvas = new Canvas(mBaImage);

		mSaiImageLeft = new int[2];
		mSaiImageTop = new int[2];

		mSaiImageLeft[0] = SAI_IMAGE_LEFT;
		mSaiImageTop[0] = SAI_IMAGE_TOP;

		mSaiImageLeft[1] = SAI_IMAGE_LEFT + mSaiImage[0].getWidth();
		mSaiImageTop[1] = SAI_IMAGE_TOP;

		mDoraImageLeft = DORA_IMAGE_LEFT;
		mDoraImageTop = DORA_IMAGE_TOP;
		mUraDoraImageLeft = DORA_IMAGE_LEFT;
		mUraDoraImageTop = DORA_IMAGE_TOP + haiBitmap.bottom[0].getHeight();
	}

	private void setBaImage() {
		Sai sais[] = mInfoUi.getSais();
		for (int i = 0; i < 2; i++) {
			mBaCanvas.drawBitmap(mSaiImage[sais[i].getNo() - 1], mSaiImageLeft[i], mSaiImageTop[i], null);
		}

		Hai doras[] = mInfoUi.getDoras();
		for (int i = 0; i < 5; i++) {
			if (i < doras.length) {
				mBaCanvas.drawBitmap(haiBitmap.bottom[doras[i].getId()], mDoraImageLeft + (i * haiBitmap.bottom[0].getWidth()), mDoraImageTop, null);
			} else {
				mBaCanvas.drawBitmap(haiBitmap.bottomUra, mDoraImageLeft + (i * haiBitmap.bottomUra.getWidth()), mDoraImageTop, null);
			}
			mBaCanvas.drawBitmap(haiBitmap.bottomUra, mUraDoraImageLeft + (i * haiBitmap.bottomUra.getWidth()), mUraDoraImageTop, null);
		}

		mBaCanvas.drawBitmap(tenbou01000MinImage, TENBOU_01000_MIN_IMAGE_LEFT, TENBOU_01000_MIN_IMAGE_TOP, null);
		mBaCanvas.drawBitmap(tenbou00100MinImage, TENBOU_00100_MIN_IMAGE_LEFT, TENBOU_00100_MIN_IMAGE_TOP, null);

		/*
		Paint kazuPaint = new Paint();
		//Paint kazuPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		kazuPaint.setColor(getResources().getColor(R.color.puzzle_foreground));
		kazuPaint.setStyle(Style.FILL);
		kazuPaint.setTextSize(16);
		kazuPaint.setTypeface(Typeface.MONOSPACE);
		//kazuPaint.setTextSize(height * 0.75f);
		//kazuPaint.setTextScaleX(width / height);
		kazuPaint.setTextAlign(Paint.Align.CENTER);

		mBaCanvas.drawText("0", TENBOU_01000_MIN_IMAGE_LEFT + 40, TENBOU_01000_MIN_IMAGE_TOP + 10, kazuPaint);
		*/
	}

	/** プレイヤー情報領域の高さ */
	private static final int PLAYER_INFO_AREA_HEIGHT = 105;
	/** プレイヤー情報領域の高さ */
	private static final int PLAYER_INFO_AREA_WIDTH = 316;

	private static final int PLAYER_INFO_AREA_LEFT = 4;
	private static final int PLAYER_INFO_AREA_TOP = 319;
	//private static final int PLAYER_INFO_AREA_TOP = TITLE_OFFSET + 319;

	/** 河の表示位置のオフセット(X座標) */
	private static final int KAWA_OFFSET_X = 12;
	/** 河の表示位置のオフセット(Y座標) */
	private static final int KAWA_OFFSET_Y = 42;

	private static final int M_HAI_HEIGHT = 26;
	private static final int M_HAI_WIDTH = 19;

	private static final int KAWA_HEIGHT = M_HAI_HEIGHT * 3;
	private static final int KAWA_WIDTH = M_HAI_WIDTH * 12;

	private static final int DEGREES_0 = 0;
	private static final int DEGREES_90 = 90;
	private static final int DEGREES_180 = 180;
	private static final int DEGREES_270 = 270;

	private Bitmap getKawaBitmap(Tehai tehai, Kawa kawa, int degrees, int kaze) {
		int width;
		int height;

		if ((degrees == DEGREES_0) || (degrees == DEGREES_180)) {
			width = PLAYER_INFO_AREA_WIDTH;
			height = PLAYER_INFO_AREA_HEIGHT;
		} else {
			width = PLAYER_INFO_AREA_HEIGHT;
			height = PLAYER_INFO_AREA_WIDTH;
		}

		Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

		Canvas canvas = new Canvas(bitmap);
		//canvas.rotate(-degrees, width / 2, height / 2);
		canvas.rotate(degrees, width / 2, height / 2);

		if ((degrees == DEGREES_0) || (degrees == DEGREES_180)) {
		} else {
			canvas.translate((width - height) / 2, -(width - height) / 2);
			//canvas.translate((width - height) / 2, (width - height) / 2);
		}

		SuteHai[] suteHai = kawa.getSuteHais();
		int kawaLength = kawa.getSuteHaisLength();
		int j = 0;
		int k = 0;
		int offset = 0;
		for (int i = 0; i < kawaLength; i++, k++) {
			if (i == 12) {
				j++;
				k = 0;
				offset = 0;
			}

			if (suteHai[i].isReach()) {
				canvas.drawBitmap(haiBitmap.topSide[suteHai[i].getId()], KAWA_OFFSET_Y + (M_HAI_WIDTH * k), KAWA_OFFSET_X + (M_HAI_HEIGHT * j) + (M_HAI_HEIGHT - M_HAI_WIDTH), null);
				offset = M_HAI_HEIGHT - M_HAI_WIDTH;
			} else {
				canvas.drawBitmap(haiBitmap.top[suteHai[i].getId()], KAWA_OFFSET_Y + (M_HAI_WIDTH * k) + offset, KAWA_OFFSET_X + (M_HAI_HEIGHT * j), null);
			}
		}

		if ((mInfoUi.getManKaze() == kaze) && (drawItem.tsumoKaze == kaze)) {
			printTehai(0, 79, canvas, tehai, kaze, selectSutehaiIdx);
		} else {
			printTehai(0, 79, canvas, tehai, kaze, 15);
		}

		return bitmap;
	}

	private static final int FUURO_LEFT = 296;

	private void printTehai(float left, float top, Canvas canvas, Tehai tehai, int kaze, int select) {
		Hai[] jyunTehai = tehai.getJyunTehai();
		int jyunTehaiLength = tehai.getJyunTehaiLength();
		int width = haiBitmap.top[0].getWidth();
		int height = haiBitmap.top[0].getHeight();
		for (int i = 0; i < jyunTehaiLength; i++) {
			if (i == select) {
				canvas.drawBitmap(haiBitmap.top[jyunTehai[i].getId()], left + (width * i), top - 10, null);
			} else {
				canvas.drawBitmap(haiBitmap.top[jyunTehai[i].getId()], left + (width * i), top, null);
			}
		}
		Log.d(this.getClass().getName(), "print, tsumoKaze = " + drawItem.tsumoKaze + ", id = " + drawItem.tsumoHai);
		if ((drawItem.tsumoHai != null) && (drawItem.tsumoKaze == kaze)) {
			if (select >= jyunTehaiLength) {
				canvas.drawBitmap(haiBitmap.top[drawItem.tsumoHai.getId()], left + ((width * jyunTehaiLength) + 5), top - 10, null);
			} else {
				canvas.drawBitmap(haiBitmap.top[drawItem.tsumoHai.getId()], left + ((width * jyunTehaiLength) + 5), top, null);
			}
		}

		int fuuroLeft = FUURO_LEFT;
		int fuuroNums = tehai.getFuuroNums();
		if (fuuroNums > 0) {
			Fuuro[] fuuros = tehai.getFuuros();
			for (int i = 0; i < fuuroNums; i++) {
				Hai hais[] = fuuros[i].getHai();
				int type = fuuros[i].getType();
				int relation = fuuros[i].getRelation();

				if (relation == Mahjong.RELATION_KAMICHA) {
					fuuroLeft -= M_HAI_HEIGHT;
					canvas.drawBitmap(haiBitmap.topSide[hais[2].getId()], fuuroLeft, 86, null);
					fuuroLeft -= M_HAI_WIDTH;
					canvas.drawBitmap(haiBitmap.top[hais[1].getId()], fuuroLeft, 79, null);
					fuuroLeft -= M_HAI_WIDTH;
					canvas.drawBitmap(haiBitmap.top[hais[0].getId()], fuuroLeft, 79, null);
				} else if (relation == Mahjong.RELATION_TOIMEN) {
					fuuroLeft -= M_HAI_WIDTH;
					canvas.drawBitmap(haiBitmap.top[hais[2].getId()], fuuroLeft, 79, null);
					fuuroLeft -= M_HAI_HEIGHT;
					canvas.drawBitmap(haiBitmap.topSide[hais[1].getId()], fuuroLeft, 86, null);
					fuuroLeft -= M_HAI_WIDTH;
					canvas.drawBitmap(haiBitmap.top[hais[0].getId()], fuuroLeft, 79, null);
				} else {
					fuuroLeft -= M_HAI_WIDTH;
					canvas.drawBitmap(haiBitmap.top[hais[2].getId()], fuuroLeft, 79, null);
					fuuroLeft -= M_HAI_WIDTH;
					canvas.drawBitmap(haiBitmap.top[hais[1].getId()], fuuroLeft, 79, null);
					fuuroLeft -= M_HAI_HEIGHT;
					canvas.drawBitmap(haiBitmap.topSide[hais[0].getId()], fuuroLeft, 86, null);
				}
			}
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		drawItem.isOnDraw = true;

		// 背景を描画する
		Paint background = new Paint();
		background.setColor(getResources().getColor(R.color.puzzle_background));
		canvas.drawRect(0, 0, getWidth(), getHeight(), background);

		int manKaze = mInfoUi.getManKaze();
		int dispKaze[] = {0, 1, 2, 3};
		for (int i = 0; i < 4; i++) {
			dispKaze[i] = manKaze;
			manKaze++;
			manKaze %= 4;
		}

		Bitmap test2 = getKawaBitmap(drawItem.tehais[dispKaze[0]], drawItem.kawas[dispKaze[0]], 0, dispKaze[0]);
		canvas.drawBitmap(test2, PLAYER_INFO_AREA_LEFT, PLAYER_INFO_AREA_TOP, null);

		Bitmap test3 = getKawaBitmap(drawItem.tehais[dispKaze[1]], drawItem.kawas[dispKaze[1]], 270, dispKaze[1]);
		canvas.drawBitmap(test3, 215, 56, null);

		Bitmap test = getKawaBitmap(drawItem.tehais[dispKaze[2]], drawItem.kawas[dispKaze[2]], 180, dispKaze[2]);
		canvas.drawBitmap(test, 0, 1, null);

		Bitmap test4 = getKawaBitmap(drawItem.tehais[dispKaze[3]], drawItem.kawas[dispKaze[3]], 90, dispKaze[3]);
		canvas.drawBitmap(test4, 0, 53, null);

		setBaImage();
		canvas.drawBitmap(mBaImage, BA_IMAGE_LEFT, BA_IMAGE_TOP, null);

		drawItem.isOnDraw = false;
		/*
		 * // 盤面を描画する...
		 *
		 * // 枠線の色を定義する Paint dark = new Paint();
		 * dark.setColor(getResources().getColor(R.color.puzzle_dark));
		 *
		 * Paint hilite = new Paint();
		 * hilite.setColor(getResources().getColor(R.color.puzzle_hilite));
		 *
		 * Paint light = new Paint();
		 * light.setColor(getResources().getColor(R.color.puzzle_light));
		 *
		 * // マス目を区切る線 for (int i = 0; i < 9; i++) { canvas.drawLine(0, i *
		 * height, getWidth(), i * height, light); canvas.drawLine(0, i * height
		 * + 1, getWidth(), i * height + 1, hilite); canvas.drawLine(i * width,
		 * 0, i * width, getHeight(), light); canvas.drawLine(i * width + 1, 0,
		 * i * width + 1, getHeight(), hilite); }
		 *
		 * // 3 x 3 のブロックを区切る線 for (int i = 0; i < 9; i++) { if (i % 3 != 0)
		 * continue; canvas.drawLine(0, i * height, getWidth(), i * height,
		 * dark); canvas.drawLine(0, i * height + 1, getWidth(), i * height + 1,
		 * hilite); canvas.drawLine(i * width, 0, i * width, getHeight(), dark);
		 * canvas.drawLine(i * width + 1, 0, i * width + 1, getHeight(),
		 * hilite); }
		 *
		 * // 数値を描画する... // 数値の色とスタイルを定義する Paint foreground = new
		 * Paint(Paint.ANTI_ALIAS_FLAG);
		 * foreground.setColor(getResources().getColor
		 * (R.color.puzzle_foreground)); foreground.setStyle(Style.FILL);
		 * foreground.setTextSize(height * 0.75f);
		 * foreground.setTextScaleX(width / height);
		 * foreground.setTextAlign(Paint.Align.CENTER);
		 *
		 * // マス目の中央に数字を描く FontMetrics fm = foreground.getFontMetrics(); //
		 * X軸方向でセンタリングする。アラインメントを使う float x = width / 2; // Y軸方向でセンタリングする。 //
		 * まずアセント/ディセント（上半分と下半分）を調べる。 float y = height / 2 - (fm.ascent +
		 * fm.descent) / 2; for (int i = 0; i < 9; i++) { for (int j = 0; j < 9;
		 * j++) { canvas.drawText(this.game.getTileString(i, j), i * width + x,
		 * j height + y, foreground); } }
		 *
		 * if (Settings.getHints(getContext())) { // ヒントを描画する...
		 *
		 * // 残された手の数に基づいてヒントの色を塗る Paint hint = new Paint(); int c[] = {
		 * getResources().getColor(R.color.puzzle_hint_0),
		 * getResources().getColor(R.color.puzzle_hint_1),
		 * getResources().getColor(R.color.puzzle_hint_2), }; Rect r = new
		 * Rect(); for (int i = 0; i < 9; i++) { for (int j = 0; j < 9; j++) {
		 * int movesleft = 9 - game.getUsedTiles(i, j).length; if (movesleft <
		 * c.length) { getRect(i, j, r); hint.setColor(c[movesleft]);
		 * canvas.drawRect(r, hint); } } }
		 *
		 * }
		 *
		 * // 選択されたマスを描画する... Log.d(TAG, "selRect=" + selRect); Paint selected =
		 * new Paint();
		 * selected.setColor(getResources().getColor(R.color.puzzle_selected));
		 * canvas.drawRect(selRect, selected);
		 */
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() != MotionEvent.ACTION_DOWN)
			return super.onTouchEvent(event);

		select((int) (event.getX() / width), (int) (event.getY() / height));
		game.showKeypadOrError(selX, selY);
		Log.d(TAG, "onTouchEvent: x " + selX + ", y " + selY);
		return true;
	}

	private int selectSutehaiIdx = 0;
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.d(TAG, "onKeyDown: keycode=" + keyCode + ", event=" + event);
		switch (keyCode) {
		case KeyEvent.KEYCODE_DPAD_UP:
			this.post(new Runnable() {
				public void run() {
					Dialog a = new Dialog(game);
					a.show();
				}
			});
			break;
		case KeyEvent.KEYCODE_DPAD_DOWN:
			selectSutehaiIdx = 100;
			break;
		case KeyEvent.KEYCODE_DPAD_LEFT:
			selectSutehaiIdx--;
			if (selectSutehaiIdx < 0) {
				selectSutehaiIdx = 13;
			}
			break;
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			selectSutehaiIdx++;
			if (selectSutehaiIdx > 13) {
				selectSutehaiIdx = 0;
			}
			break;
		case KeyEvent.KEYCODE_0:
		case KeyEvent.KEYCODE_SPACE:
			selectSutehaiIdx *= 10;
			selectSutehaiIdx += 0;
			//setSelectedTile(0);
			break;
		case KeyEvent.KEYCODE_1:
			selectSutehaiIdx *= 10;
			selectSutehaiIdx += 1;
			//setSelectedTile(1);
			break;
		case KeyEvent.KEYCODE_2:
			selectSutehaiIdx *= 10;
			selectSutehaiIdx += 2;
			//setSelectedTile(2);
			break;
		case KeyEvent.KEYCODE_3:
			selectSutehaiIdx *= 10;
			selectSutehaiIdx += 3;
			//setSelectedTile(3);
			break;
		case KeyEvent.KEYCODE_4:
			//setSelectedTile(4);
			selectSutehaiIdx *= 10;
			selectSutehaiIdx += 4;
			break;
		case KeyEvent.KEYCODE_5:
			selectSutehaiIdx *= 10;
			selectSutehaiIdx += 5;
			//setSelectedTile(5);
			break;
		case KeyEvent.KEYCODE_6:
			selectSutehaiIdx *= 10;
			selectSutehaiIdx += 6;
			//setSelectedTile(6);
			break;
		case KeyEvent.KEYCODE_7:
			selectSutehaiIdx *= 10;
			selectSutehaiIdx += 7;
			//setSelectedTile(7);
			break;
		case KeyEvent.KEYCODE_8:
			selectSutehaiIdx *= 10;
			selectSutehaiIdx += 8;
			//setSelectedTile(8);
			break;
		case KeyEvent.KEYCODE_9:
			selectSutehaiIdx *= 10;
			selectSutehaiIdx += 9;
			//setSelectedTile(9);
			break;
		case KeyEvent.KEYCODE_ENTER:
		case KeyEvent.KEYCODE_DPAD_CENTER:
			game.mahjong.setSutehaiIdx(selectSutehaiIdx);
			selectSutehaiIdx = 13;
			//game.showKeypadOrError(selX, selY);
			break;
		default:
			return super.onKeyDown(keyCode, event);
		}
		invalidate();
		return true;
	}

	public void setSelectedTile(int tile) {
		if (game.setTileIfValid(selX, selY, tile)) {
			invalidate();// ヒントは変わる可能性あり
		} else {
			// このマスの数値は選べない値
			Log.d(TAG, "setSelectedTile: invalid: " + tile);
			startAnimation(AnimationUtils.loadAnimation(game, R.anim.shake));
		}
	}

	private void select(int x, int y) {
		invalidate(selRect);
		selX = Math.min(Math.max(x, 0), 8);
		selY = Math.min(Math.max(y, 0), 8);
		getRect(selX, selY, selRect);
		invalidate(selRect);
	}

	private void getRect(int x, int y, Rect rect) {
		rect.set((int) (x * width), (int) (y * height),
				(int) (x * width + width), (int) (y * height + height));
	}

	/** InfoUIオブジェクト */
	private InfoUI mInfoUi;

	/** 手牌 */
	private Tehai tehai = new Tehai();

	/** 河 */
	private Kawa kawa = new Kawa();

	/** 捨牌のインデックス */
	private int sutehaiIdx = 0;

	private String name;

	public String getName() {
		return name;
	}

	/**
	 * UIを初期化します。
	 *
	 * @param infoUi
	 *            InfoUIオブジェクト
	 */
	public void Console(InfoUI infoUi, String name) {
		this.mInfoUi = infoUi;
		this.name = name;
	}

	private void dispInfo() {
		System.out.println("-------- INFO --------");
		int kyoku = mInfoUi.getkyoku();
		switch (kyoku) {
		case Mahjong.KYOKU_TON_1:
			System.out.print("[東一局]");
			break;
		case Mahjong.KYOKU_TON_2:
			System.out.print("[東二局]");
			break;
		case Mahjong.KYOKU_TON_3:
			System.out.print("[東三局]");
			break;
		case Mahjong.KYOKU_TON_4:
			System.out.print("[東四局]");
			break;
		}
		System.out.print("[" + mInfoUi.getHonba() + "本場]");
		System.out.println("[残り:" + mInfoUi.getTsumoRemain() + "]");
		// ドラ表示牌を表示します。
		Hai[] doras = mInfoUi.getDoras();
		System.out.print("[ドラ表示牌]");
		for (Hai hai : doras) {
			System.out.print("[" + idToString(hai.getId()) + "]");
		}
		System.out.println();

		// 名前などを表示してみる。
		System.out.println("[" + jikazeToString(0) + "][" + mInfoUi.getName(0)
				+ "][" + mInfoUi.getTenbou(0) + "]");
		System.out.println("[" + jikazeToString(1) + "][" + mInfoUi.getName(1)
				+ "][" + mInfoUi.getTenbou(1) + "]");
		System.out.println("[" + jikazeToString(2) + "][" + mInfoUi.getName(2)
				+ "][" + mInfoUi.getTenbou(2) + "]");
		System.out.println("[" + jikazeToString(3) + "][" + mInfoUi.getName(3)
				+ "][" + mInfoUi.getTenbou(3) + "]");

		System.out.println("----------------------");
	}

	/**
	 * イベントを処理します。
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
		case BASHOGIME:// 場所決め
			// 表示することはない。
			break;
		case OYAGIME:// 親決め
			// サイ振りを表示します。
			// Sai[] sai = infoUi.getSais();
			// System.out.println("[親決め][" + sai[0].getNo() + "]["
			// + sai[1].getNo() + "]");
			break;
		case SENPAI:// 洗牌
			break;
		case SAIFURI:// サイ振り
			dispInfo();
			break;
		case RYUUKYOKU:// 流局
			System.out.println("[流局]");
			break;
		case NAGASHI:// 流し
			// 表示することはない。
			break;
		case TSUMO:// ツモ
			// 手牌を表示します。
			printJyunTehai(tehai);

//			if (drawItem.isOnDraw == false) {
				mInfoUi.copyTehai(drawItem.tehais[0], 0);
				mInfoUi.copyTehai(drawItem.tehais[1], 1);
				mInfoUi.copyTehai(drawItem.tehais[2], 2);
				mInfoUi.copyTehai(drawItem.tehais[3], 3);
				drawItem.tsumoKaze = mInfoUi.getJikaze();
				Log.d(TAG, "tsumoKaze = " + drawItem.tsumoKaze);
				drawItem.tsumoHai = mInfoUi.getTsumoHai();
				isPrintTehai = true;
				this.postInvalidate(0, 0, getWidth(), getHeight());
				/*
				 * this.post(new Runnable() { public void run() { selRect.set(0,
				 * 0, getWidth(), getHeight()); invalidate(selRect); } });
				 */
//			}
			Log.d(this.getClass().getName(), "tsumo, tsumoKaze = " + drawItem.tsumoKaze + ", id = " + drawItem.tsumoHai.getId());
			/*
			 * if (isDraw == false) { infoUi.copyTehai(printTehai,
			 * infoUi.getJikaze()); isPrintTehai = true; this.post(new
			 * Runnable() { public void run() { selRect.set(0, 0, getWidth(),
			 * getHeight()); invalidate(selRect); } }); }
			 */

			// ツモ牌を表示します。
			System.out
					.println(":" + idToString((mInfoUi.getTsumoHai()).getId()));
			break;
		case TSUMOAGARI:// ツモあがり
			System.out.print("[" + jikazeToString(mInfoUi.getJikaze())
					+ "][ツモあがり]");

			// 手牌を表示します。
			printJyunTehai(tehai);

			// ツモ牌を表示します。
			System.out
					.println(":" + idToString((mInfoUi.getTsumoHai()).getId()));
			break;
		case SUTEHAI:// 捨牌
			// 自分の捨牌のみを表示します。
			if (fromKaze == mInfoUi.getJikaze()) {
				System.out.print("[" + jikazeToString(mInfoUi.getJikaze())
						+ "][捨牌]");

				// 河を表示します。
				printKawa(kawa);

				Log.d(this.getClass().getName(), "sutehai");
				{
					mInfoUi.copyTehai(drawItem.tehais[0], 0);
					mInfoUi.copyTehai(drawItem.tehais[1], 1);
					mInfoUi.copyTehai(drawItem.tehais[2], 2);
					mInfoUi.copyTehai(drawItem.tehais[3], 3);
					mInfoUi.copyKawa(drawItem.kawas[0], 0);
					mInfoUi.copyKawa(drawItem.kawas[1], 1);
					mInfoUi.copyKawa(drawItem.kawas[2], 2);
					mInfoUi.copyKawa(drawItem.kawas[3], 3);
					this.postInvalidate(0, 0, getWidth(), getHeight());
					drawItem.tsumoKaze = 5;
					drawItem.tsumoHai = null;
				}
				// 捨牌を表示します。
				// System.out.println(":"
				// + idToString((infoUi.getSuteHai()).getId()));
				System.out.println();
			}
			break;
		case PON:// ポン
			// 自分の捨牌のみを表示します。
			if (fromKaze == mInfoUi.getJikaze()) {
				this.post(new Runnable() {
					public void run() {
						Dialog a = new Dialog(game);
						a.show();
					}
				});
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
			}
			break;
		case REACH:
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
			break;
		case RON:// ロン
			this.post(new Runnable() {
				public void run() {
					Dialog a = new Dialog(game);
					a.show();
				}
			});
			System.out
					.print("[" + jikazeToString(mInfoUi.getJikaze()) + "][ロン]");

			// 手牌を表示します。
			printJyunTehai(tehai);

			// 当たり牌を表示します。
			System.out.println(":" + idToString((mInfoUi.getSuteHai()).getId()));
			break;
		default:
			break;
		}

		return EID.NAGASHI;
	}

	public int getSutehaiIdx() {
		return sutehaiIdx;
	}

	private boolean isPrintTehai = false;

	// private Tehai printTehai = new Tehai();

	/**
	 * 手牌を表示します。
	 * <p>
	 * TODO 鳴き牌を表示すること。
	 * </p>
	 *
	 * @param tehai
	 *            手牌
	 */
	private void printJyunTehai(Tehai tehai) {
		mInfoUi.copyTehai(tehai, mInfoUi.getJikaze());
		Hai[] jyunTehai = tehai.getJyunTehai();
		int jyunTehaiLength = tehai.getJyunTehaiLength();
		for (int i = 0; i < jyunTehaiLength; i++)
			System.out.print(idToString(jyunTehai[i].getId()));

		int minkousLength = tehai.getMinKousLength();
		Hai[][] minkous = tehai.getMinKous();
		for (int i = 0; i < minkousLength; i++) {
			System.out.print("[");
			System.out.print(idToString(minkous[i][0].getId()));
			System.out.print(idToString(minkous[i][1].getId()));
			System.out.print(idToString(minkous[i][2].getId()));
			System.out.print("]");
		}
	}

	/**
	 * 河を表示します。
	 *
	 * @param kawa
	 *            河
	 */
	private void printKawa(Kawa kawa) {
		mInfoUi.copyKawa(kawa, mInfoUi.getJikaze());
		SuteHai[] SuteHai = kawa.getSuteHais();
		int kawaLength = kawa.getSuteHaisLength();
		for (int i = 0; i < kawaLength; i++)
			System.out.print(idToString(SuteHai[i].getId()));
	}

	/**
	 * 牌番号を文字列に変換します
	 *
	 * @param id
	 *            牌番号
	 * @return 文字列
	 */
	static public String idToString(int id) {
		switch (id) {
		case ID_WAN_1:
			return "一";
		case ID_WAN_2:
			return "二";
		case ID_WAN_3:
			return "三";
		case ID_WAN_4:
			return "四";
		case ID_WAN_5:
			return "五";
		case ID_WAN_6:
			return "六";
		case ID_WAN_7:
			return "七";
		case ID_WAN_8:
			return "八";
		case ID_WAN_9:
			return "九";
		case ID_PIN_1:
			return "①";
		case ID_PIN_2:
			return "②";
		case ID_PIN_3:
			return "③";
		case ID_PIN_4:
			return "④";
		case ID_PIN_5:
			return "⑤";
		case ID_PIN_6:
			return "⑥";
		case ID_PIN_7:
			return "⑦";
		case ID_PIN_8:
			return "⑧";
		case ID_PIN_9:
			return "⑨";
		case ID_SOU_1:
			return "１";
		case ID_SOU_2:
			return "２";
		case ID_SOU_3:
			return "３";
		case ID_SOU_4:
			return "４";
		case ID_SOU_5:
			return "５";
		case ID_SOU_6:
			return "６";
		case ID_SOU_7:
			return "７";
		case ID_SOU_8:
			return "８";
		case ID_SOU_9:
			return "９";
		case ID_TON:
			return "東";
		case ID_NAN:
			return "南";
		case ID_SHA:
			return "西";
		case ID_PE:
			return "北";
		case ID_HAKU:
			return "白";
		case ID_HATSU:
			return "發";
		case ID_CHUN:
			return "中";
		}

		return null;
	}

	/**
	 * 自風を文字列に変換します。
	 *
	 * @param jikaze
	 *            自風
	 * @return　文字列
	 */
	static public String jikazeToString(int jikaze) {
		switch (jikaze) {
		case 0:
			return "東";
		case 1:
			return "南";
		case 2:
			return "西";
		case 3:
			return "北";
		}

		return null;
	}

	/**
	 * 成立している役を表示します。
	 *
	 * @param jikaze
	 *            自風
	 * @return　
	 */
	public void jikazeToString(Hai addHai) {
		String[] yakuNames = this.mInfoUi.getYakuName(tehai, addHai);
		for (int i = 0; i < yakuNames.length; i++) {
			System.out.println(yakuNames[i]);
		}
	}
}