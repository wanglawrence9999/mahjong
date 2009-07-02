package jp.sourceforge.andjong;

/**
 * ゲームのハンドリングを行うクラスです。
 * 
 * @author Yuji Urushibara
 * 
 */
public class Game {
	public void play() {
		Yama yama = new Yama();

		yama.xipai();

		Hai[] dora;
		dora = yama.getDora();
		System.out.println("dora.length = " + dora.length);
		for (int i = 0; i < dora.length; i++)
			System.out.println("dora = " + dora[i].id);

		Hai tsumoHai;
		while (true) {
			tsumoHai = yama.tsumo();
			if (tsumoHai == null)
				break;
			System.out.println("tsumoHai = " + tsumoHai.id);
		}

		Hai rinshanHai;
		while (true) {
			rinshanHai = yama.rinshan();
			if (rinshanHai == null)
				break;
			System.out.println("rinshanHai = " + rinshanHai.id);
		}

		dora = yama.getDoraAll();
		System.out.println("dora.length = " + dora.length);
		for (int i = 0; i < dora.length; i++)
			System.out.println("dora = " + dora[i].id);
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.play();
	}
}
