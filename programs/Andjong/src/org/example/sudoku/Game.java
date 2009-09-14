/***
 * Excerpted from "Hello, Android!",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/eband for more book information.
***/
package org.example.sudoku;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

public class Game extends Activity {
   private static final String TAG = "Sudoku";

   public static final String KEY_DIFFICULTY = "difficulty";
   
   private static final String PREF_PUZZLE = "puzzle" ;
   
   public static final int DIFFICULTY_EASY = 0;
   public static final int DIFFICULTY_MEDIUM = 1;
   public static final int DIFFICULTY_HARD = 2;
   
   protected static final int DIFFICULTY_CONTINUE = -1;
   

   private int puzzle[] = new int[9 * 9];

   private final String easyPuzzle =
      "360000000004230800000004200" +
      "070460003820000014500013020" +
      "001900000007048300000000045";
   private final String mediumPuzzle =
      "650000070000506000014000005" +
      "007009000002314700000700800" +
      "500000630000201000030000097";
   private final String hardPuzzle =
      "009000000080605020501078000" +
      "000000700706040102004000000" +
      "000720903090301080000000600";

   private PuzzleView puzzleView;

   
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      
      super.onCreate(savedInstanceState);
      Log.d(TAG, "onCreate");

      int diff = getIntent().getIntExtra(KEY_DIFFICULTY,
            DIFFICULTY_EASY);
      puzzle = getPuzzle(diff);
      calculateUsedTiles();

      puzzleView = new PuzzleView(this);
      setContentView(puzzleView);
      puzzleView.requestFocus();

      
      // ...
      // アクティビティが再起動されたときには、ゲームを続行する
      getIntent().putExtra(KEY_DIFFICULTY, DIFFICULTY_CONTINUE);
   }
   

   @Override
   protected void onResume() {
      super.onResume();
      Music.play(this, R.raw.game);
   }

   
   @Override
   protected void onPause() {
      super.onPause();
      Log.d(TAG, "onPause");
      Music.stop(this);

      // 現在のパズルを保存する
      getPreferences(MODE_PRIVATE).edit().putString(PREF_PUZZLE,
            toPuzzleString(puzzle)).commit();
   }
   
   
   
   /** 与えられた難易度レベルに合った新しいパズルを返す */
   private int[] getPuzzle(int diff) {
      String puz;
      switch (diff) {
      case DIFFICULTY_CONTINUE:
         puz = getPreferences(MODE_PRIVATE).getString(PREF_PUZZLE,
               easyPuzzle);
         break;
         // ...
         
      case DIFFICULTY_HARD:
         puz = hardPuzzle;
         break;
      case DIFFICULTY_MEDIUM:
         puz = mediumPuzzle;
         break;
      case DIFFICULTY_EASY:
      default:
         puz = easyPuzzle;
         break;
         
      }
      return fromPuzzleString(puz);
   }
   

   /** 整数配列をパズルの文字列に変換する */
   static private String toPuzzleString(int[] puz) {
      StringBuilder buf = new StringBuilder();
      for (int element : puz) {
         buf.append(element);
      }
      return buf.toString();
   }

   /** パズルの文字列を整数配列に変換する */
   static protected int[] fromPuzzleString(String string) {
      int[] puz = new int[string.length()];
      for (int i = 0; i < puz.length; i++) {
         puz[i] = string.charAt(i) - '0';
      }
      return puz;
   }

   /** 指定された座標のマスに書かれている数を返す */
   private int getTile(int x, int y) {
      return puzzle[y * 9 + x];
   }

   /** 指定された座標のマスの数を書き換える */
   private void setTile(int x, int y, int value) {
      puzzle[y * 9 + x] = value;
   }

   /** 指定された座標のマスに書かれている数を文字列にして返す */
   protected String getTileString(int x, int y) {
      int v = getTile(x, y);
      if (v == 0)
         return "";
      else
         return String.valueOf(v);
   }

   /** 有効な手になっているときに限りマスを変更する */
   protected boolean setTileIfValid(int x, int y, int value) {
      int tiles[] = getUsedTiles(x, y);
      if (value != 0) {
         for (int tile : tiles) {
            if (tile == value)
               return false;
         }
      }
      setTile(x, y, value);
      calculateUsedTiles();
      return true;
   }

   /** 有効な手があればキーパッドをオープンする */
   protected void showKeypadOrError(int x, int y) {
      int tiles[] = getUsedTiles(x, y);
      if (tiles.length == 9) {
         Toast toast = Toast.makeText(this,
               R.string.no_moves_label, Toast.LENGTH_SHORT);
         toast.setGravity(Gravity.CENTER, 0, 0);
         toast.show();
      } else {
         Log.d(TAG, "showKeypad: used=" + toPuzzleString(tiles));
         Dialog v = new Keypad(this, tiles, puzzleView);
         v.show();
      }
   }

   /** 使用済みの数のキャッシュ */
   private final int used[][][] = new int[9][9][];

   /** 現在のマスが属する行、列、ブロックの使用済みの数のリストを返す */
   protected int[] getUsedTiles(int x, int y) {
      return used[x][y];
   }

   /** それぞれのマスについて、使えない数のリストを計算する */
   private void calculateUsedTiles() {
      for (int x = 0; x < 9; x++) {
         for (int y = 0; y < 9; y++) {
            used[x][y] = calculateUsedTiles(x, y);
            // Log.d(TAG, "used[" + x + "][" + y + "] = "
            // + toPuzzleString(used[x][y]));
         }
      }
   }

   /** 指定されたマスが属する行、列、ブロックですでに使われている数を計算 */
   private int[] calculateUsedTiles(int x, int y) {
      int c[] = new int[9];
      // 横
      for (int i = 0; i < 9; i++) {
         if (i == y)
            continue;
         int t = getTile(x, i);
         if (t != 0)
            c[t - 1] = t;
      }
      // 縦
      for (int i = 0; i < 9; i++) {
         if (i == x)
            continue;
         int t = getTile(i, y);
         if (t != 0)
            c[t - 1] = t;
      }
      // ブロック
      int startx = (x / 3) * 3;
      int starty = (y / 3) * 3;
      for (int i = startx; i < startx + 3; i++) {
         for (int j = starty; j < starty + 3; j++) {
            if (i == x && j == y)
               continue;
            int t = getTile(i, j);
            if (t != 0)
               c[t - 1] = t;
         }
      }
      // 圧縮
      int nused = 0;
      for (int t : c) {
         if (t != 0)
            nused++;
      }
      int c1[] = new int[nused];
      nused = 0;
      for (int t : c) {
         if (t != 0)
            c1[nused++] = t;
      }
      return c1;
   }
   
}
