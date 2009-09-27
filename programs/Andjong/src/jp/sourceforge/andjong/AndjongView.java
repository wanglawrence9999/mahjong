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
import jp.sourceforge.andjong.mahjong.Hai;
import jp.sourceforge.andjong.mahjong.InfoUI;
import jp.sourceforge.andjong.mahjong.Kawa;
import jp.sourceforge.andjong.mahjong.Mahjong;
import jp.sourceforge.andjong.mahjong.SuteHai;
import jp.sourceforge.andjong.mahjong.Tehai;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
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

	private Bitmap[] m_hai_bitmap;
	private Bitmap m_ura_bitmap;

	public AndjongView(Context context) {

		super(context);
		this.game = (Game) context;
		setFocusable(true);
		setFocusableInTouchMode(true);

		// ...
		setId(ID);

		initBitmap();
	}

	private class DrawItem {
		/** onDrawフラグ */
		boolean isOnDraw = false;

		/** イベントID */
		EID eid;

		/** 手牌 */
		Tehai tehai = new Tehai();

		/** ツモ牌 */
		Hai tsumoHai = new Hai();

		/** 河 */
		Kawa kawa = new Kawa();
	}

	private DrawItem drawItem = new DrawItem();

	private void initBitmap() {
		Resources res = this.getContext().getResources();
		m_hai_bitmap = new Bitmap[Hai.ID_MAX + 1];
		m_hai_bitmap[0] = BitmapFactory.decodeResource(res, R.drawable.m_hai_00_wan_1);
		m_hai_bitmap[1] = BitmapFactory.decodeResource(res, R.drawable.m_hai_01_wan_2);
		m_hai_bitmap[2] = BitmapFactory.decodeResource(res, R.drawable.m_hai_02_wan_3);
		m_hai_bitmap[3] = BitmapFactory.decodeResource(res, R.drawable.m_hai_03_wan_4);
		m_hai_bitmap[4] = BitmapFactory.decodeResource(res, R.drawable.m_hai_04_wan_5);
		m_hai_bitmap[5] = BitmapFactory.decodeResource(res, R.drawable.m_hai_05_wan_6);
		m_hai_bitmap[6] = BitmapFactory.decodeResource(res, R.drawable.m_hai_06_wan_7);
		m_hai_bitmap[7] = BitmapFactory.decodeResource(res, R.drawable.m_hai_07_wan_8);
		m_hai_bitmap[8] = BitmapFactory.decodeResource(res, R.drawable.m_hai_08_wan_9);

		m_hai_bitmap[9] = BitmapFactory.decodeResource(res, R.drawable.m_hai_09_pin_1);
		m_hai_bitmap[10] = BitmapFactory.decodeResource(res, R.drawable.m_hai_10_pin_2);
		m_hai_bitmap[11] = BitmapFactory.decodeResource(res, R.drawable.m_hai_11_pin_3);
		m_hai_bitmap[12] = BitmapFactory.decodeResource(res, R.drawable.m_hai_12_pin_4);
		m_hai_bitmap[13] = BitmapFactory.decodeResource(res, R.drawable.m_hai_13_pin_5);
		m_hai_bitmap[14] = BitmapFactory.decodeResource(res, R.drawable.m_hai_14_pin_6);
		m_hai_bitmap[15] = BitmapFactory.decodeResource(res, R.drawable.m_hai_15_pin_7);
		m_hai_bitmap[16] = BitmapFactory.decodeResource(res, R.drawable.m_hai_16_pin_8);
		m_hai_bitmap[17] = BitmapFactory.decodeResource(res, R.drawable.m_hai_17_pin_9);

		m_hai_bitmap[18] = BitmapFactory.decodeResource(res, R.drawable.m_hai_18_sou_1);
		m_hai_bitmap[19] = BitmapFactory.decodeResource(res, R.drawable.m_hai_19_sou_2);
		m_hai_bitmap[20] = BitmapFactory.decodeResource(res, R.drawable.m_hai_20_sou_3);
		m_hai_bitmap[21] = BitmapFactory.decodeResource(res, R.drawable.m_hai_21_sou_4);
		m_hai_bitmap[22] = BitmapFactory.decodeResource(res, R.drawable.m_hai_22_sou_5);
		m_hai_bitmap[23] = BitmapFactory.decodeResource(res, R.drawable.m_hai_23_sou_6);
		m_hai_bitmap[24] = BitmapFactory.decodeResource(res, R.drawable.m_hai_24_sou_7);
		m_hai_bitmap[25] = BitmapFactory.decodeResource(res, R.drawable.m_hai_25_sou_8);
		m_hai_bitmap[26] = BitmapFactory.decodeResource(res, R.drawable.m_hai_26_sou_9);

		m_hai_bitmap[27] = BitmapFactory.decodeResource(res, R.drawable.m_hai_27_ton);
		m_hai_bitmap[28] = BitmapFactory.decodeResource(res, R.drawable.m_hai_28_nan);
		m_hai_bitmap[29] = BitmapFactory.decodeResource(res, R.drawable.m_hai_29_sha);
		m_hai_bitmap[30] = BitmapFactory.decodeResource(res, R.drawable.m_hai_30_pe);

		m_hai_bitmap[31] = BitmapFactory.decodeResource(res, R.drawable.m_hai_31_haku);
		m_hai_bitmap[32] = BitmapFactory.decodeResource(res, R.drawable.m_hai_32_hatsu);
		m_hai_bitmap[33] = BitmapFactory.decodeResource(res, R.drawable.m_hai_33_chun);

		m_ura_bitmap = BitmapFactory.decodeResource(res, R.drawable.m_hai_ura);
	}

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

	private void printTehai(float left, float top, float right, float bottom, Paint paint, Canvas canvas) {
		Hai[] jyunTehai = drawItem.tehai.getJyunTehai();
		int jyunTehaiLength = drawItem.tehai.getJyunTehaiLength();
		int width = m_hai_bitmap[0].getWidth();
		int height = m_hai_bitmap[0].getHeight();
		for (int i = 0; i < jyunTehaiLength; i++) {
			canvas.drawBitmap(m_hai_bitmap[jyunTehai[i].getId()], left + (width * i), top + height, paint);
		}
		if (drawItem.tsumoHai != null) {
			canvas.drawBitmap(m_hai_bitmap[drawItem.tsumoHai.getId()], left + ((width * jyunTehaiLength) + 5), top + height, paint);
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		drawItem.isOnDraw = true;

		// 背景を描画する
		Paint background = new Paint();
		background.setColor(getResources().getColor(R.color.puzzle_background));
		canvas.drawRect(0, 0, getWidth(), getHeight(), background);

		Paint tehaiPaint = new Paint();
		printTehai(0, 100, getWidth(), getHeight(), tehaiPaint, canvas);

		SuteHai[] suteHai = kawa.getSuteHais();
		int kawaLength = kawa.getSuteHaisLength();
		for (int i = 0; i < kawaLength; i++) {
			canvas.drawBitmap(m_hai_bitmap[suteHai[i].getId()], 19 * i, 26, tehaiPaint);
		}

		drawItem.isOnDraw = false;
/*
		// 盤面を描画する...

		// 枠線の色を定義する
		Paint dark = new Paint();
		dark.setColor(getResources().getColor(R.color.puzzle_dark));

		Paint hilite = new Paint();
		hilite.setColor(getResources().getColor(R.color.puzzle_hilite));

		Paint light = new Paint();
		light.setColor(getResources().getColor(R.color.puzzle_light));

		// マス目を区切る線
		for (int i = 0; i < 9; i++) {
			canvas.drawLine(0, i * height, getWidth(), i * height, light);
			canvas.drawLine(0, i * height + 1, getWidth(), i * height + 1,
					hilite);
			canvas.drawLine(i * width, 0, i * width, getHeight(), light);
			canvas.drawLine(i * width + 1, 0, i * width + 1, getHeight(),
					hilite);
		}

		// 3 x 3 のブロックを区切る線
		for (int i = 0; i < 9; i++) {
			if (i % 3 != 0)
				continue;
			canvas.drawLine(0, i * height, getWidth(), i * height, dark);
			canvas.drawLine(0, i * height + 1, getWidth(), i * height + 1,
					hilite);
			canvas.drawLine(i * width, 0, i * width, getHeight(), dark);
			canvas.drawLine(i * width + 1, 0, i * width + 1, getHeight(),
					hilite);
		}

		// 数値を描画する...
		// 数値の色とスタイルを定義する
		Paint foreground = new Paint(Paint.ANTI_ALIAS_FLAG);
		foreground.setColor(getResources().getColor(R.color.puzzle_foreground));
		foreground.setStyle(Style.FILL);
		foreground.setTextSize(height * 0.75f);
		foreground.setTextScaleX(width / height);
		foreground.setTextAlign(Paint.Align.CENTER);

		// マス目の中央に数字を描く
		FontMetrics fm = foreground.getFontMetrics();
		// X軸方向でセンタリングする。アラインメントを使う
		float x = width / 2;
		// Y軸方向でセンタリングする。
		// まずアセント/ディセント（上半分と下半分）を調べる。
		float y = height / 2 - (fm.ascent + fm.descent) / 2;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				canvas.drawText(this.game.getTileString(i, j), i * width + x, j
						* height + y, foreground);
			}
		}

		if (Settings.getHints(getContext())) {
			// ヒントを描画する...

			// 残された手の数に基づいてヒントの色を塗る
			Paint hint = new Paint();
			int c[] = { getResources().getColor(R.color.puzzle_hint_0),
					getResources().getColor(R.color.puzzle_hint_1),
					getResources().getColor(R.color.puzzle_hint_2), };
			Rect r = new Rect();
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					int movesleft = 9 - game.getUsedTiles(i, j).length;
					if (movesleft < c.length) {
						getRect(i, j, r);
						hint.setColor(c[movesleft]);
						canvas.drawRect(r, hint);
					}
				}
			}

		}

		// 選択されたマスを描画する...
		Log.d(TAG, "selRect=" + selRect);
		Paint selected = new Paint();
		selected.setColor(getResources().getColor(R.color.puzzle_selected));
		canvas.drawRect(selRect, selected);
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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.d(TAG, "onKeyDown: keycode=" + keyCode + ", event=" + event);
		switch (keyCode) {
		case KeyEvent.KEYCODE_DPAD_UP:
			select(selX, selY - 1);
			break;
		case KeyEvent.KEYCODE_DPAD_DOWN:
			select(selX, selY + 1);
			break;
		case KeyEvent.KEYCODE_DPAD_LEFT:
			select(selX - 1, selY);
			break;
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			select(selX + 1, selY);
			break;
		case KeyEvent.KEYCODE_0:
		case KeyEvent.KEYCODE_SPACE:
			setSelectedTile(0);
			break;
		case KeyEvent.KEYCODE_1:
			setSelectedTile(1);
			break;
		case KeyEvent.KEYCODE_2:
			setSelectedTile(2);
			break;
		case KeyEvent.KEYCODE_3:
			setSelectedTile(3);
			break;
		case KeyEvent.KEYCODE_4:
			setSelectedTile(4);
			break;
		case KeyEvent.KEYCODE_5:
			setSelectedTile(5);
			break;
		case KeyEvent.KEYCODE_6:
			setSelectedTile(6);
			break;
		case KeyEvent.KEYCODE_7:
			setSelectedTile(7);
			break;
		case KeyEvent.KEYCODE_8:
			setSelectedTile(8);
			break;
		case KeyEvent.KEYCODE_9:
			setSelectedTile(9);
			break;
		case KeyEvent.KEYCODE_ENTER:
		case KeyEvent.KEYCODE_DPAD_CENTER:
			game.showKeypadOrError(selX, selY);
			break;
		default:
			return super.onKeyDown(keyCode, event);
		}
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
	private InfoUI infoUi;

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
		this.infoUi = infoUi;
		this.name = name;
	}

	private void dispInfo() {
		System.out.println("-------- INFO --------");
		int kyoku = infoUi.getkyoku();
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
		System.out.print("[" + infoUi.getHonba() + "本場]");
		System.out.println("[残り:" + infoUi.getTsumoRemain() + "]");
		// ドラ表示牌を表示します。
		Hai[] doras = infoUi.getDoras();
		System.out.print("[ドラ表示牌]");
		for (Hai hai : doras) {
			System.out.print("[" + idToString(hai.getId()) + "]");
		}
		System.out.println();

		// 名前などを表示してみる。
		System.out.println("[" + jikazeToString(0) + "][" + infoUi.getName(0)
				+ "][" + infoUi.getTenbou(0) + "]");
		System.out.println("[" + jikazeToString(1) + "][" + infoUi.getName(1)
				+ "][" + infoUi.getTenbou(1) + "]");
		System.out.println("[" + jikazeToString(2) + "][" + infoUi.getName(2)
				+ "][" + infoUi.getTenbou(2) + "]");
		System.out.println("[" + jikazeToString(3) + "][" + infoUi.getName(3)
				+ "][" + infoUi.getTenbou(3) + "]");

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
			System.out
					.print("[" + jikazeToString(infoUi.getJikaze()) + "][ツモ]");

			// 手牌を表示します。
			printJyunTehai(tehai);

			if (drawItem.isOnDraw == false) {
				infoUi.copyTehai(drawItem.tehai, infoUi.getJikaze());
				drawItem.tsumoHai = infoUi.getTsumoHai();
				isPrintTehai = true;
				this.postInvalidate(0, 0, getWidth(), getHeight());
				/*
				this.post(new Runnable() {
					public void run() {
						selRect.set(0, 0, getWidth(), getHeight());
						invalidate(selRect);
					}
				});
				*/
			}
			/*
			if (isDraw == false) {
				infoUi.copyTehai(printTehai, infoUi.getJikaze());
				isPrintTehai = true;
				this.post(new Runnable() {
					public void run() {
						selRect.set(0, 0, getWidth(), getHeight());
						invalidate(selRect);
					}
				});
			}
			*/

			// ツモ牌を表示します。
			System.out
					.println(":" + idToString((infoUi.getTsumoHai()).getId()));
			break;
		case TSUMOAGARI:// ツモあがり
			System.out.print("[" + jikazeToString(infoUi.getJikaze())
					+ "][ツモあがり]");

			// 手牌を表示します。
			printJyunTehai(tehai);

			// ツモ牌を表示します。
			System.out
					.println(":" + idToString((infoUi.getTsumoHai()).getId()));
			break;
		case SUTEHAI:// 捨牌
			// 自分の捨牌のみを表示します。
			if (fromKaze == infoUi.getJikaze()) {
				System.out.print("[" + jikazeToString(infoUi.getJikaze())
						+ "][捨牌]");

				// 河を表示します。
				printKawa(kawa);

				infoUi.copyKawa(drawItem.kawa, infoUi.getJikaze());
				this.postInvalidate(0, 0, getWidth(), getHeight());
				// 捨牌を表示します。
				// System.out.println(":"
				// + idToString((infoUi.getSuteHai()).getId()));
				System.out.println();
			}
			break;
		case PON:// ポン
			// 自分の捨牌のみを表示します。
			if (fromKaze == infoUi.getJikaze()) {
				System.out.print("[" + jikazeToString(infoUi.getJikaze())
						+ "][ポン]");

				// 手牌を表示します。
				printJyunTehai(tehai);

				System.out.println();

				System.out.print("[" + jikazeToString(infoUi.getJikaze())
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
			if (fromKaze == infoUi.getJikaze()) {
				System.out.print("[" + jikazeToString(infoUi.getJikaze())
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
			System.out
					.print("[" + jikazeToString(infoUi.getJikaze()) + "][ロン]");

			// 手牌を表示します。
			printJyunTehai(tehai);

			// 当たり牌を表示します。
			System.out.println(":" + idToString((infoUi.getSuteHai()).getId()));
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
	//private Tehai printTehai = new Tehai();

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
		infoUi.copyTehai(tehai, infoUi.getJikaze());
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
		infoUi.copyKawa(kawa, infoUi.getJikaze());
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
		String[] yakuNames = this.infoUi.getYakuName(tehai, addHai);
		for(int i = 0 ; i < yakuNames.length ; i++){
			System.out.println(yakuNames[i]);
		}
	}
}