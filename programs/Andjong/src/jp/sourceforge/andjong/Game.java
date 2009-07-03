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

		Tehai tehai = new Tehai();
		Hai tsumoHai;

		for (int i = 0; i < 13; i++) {
			tsumoHai = yama.tsumo();
			tehai.addJyunTehai(tsumoHai);
		}

		for (int i = 0; i < tehai.jyunTehaiLength; i++)
			System.out.println("jyunTehai = " + tehai.jyunTehai[i].id);

		{
			System.out.println("removeJyunTehai");
			for (int i = 0; i < 3; i++)
				tehai.removeJyunTehai(i);

			for (int i = 0; i < tehai.jyunTehaiLength; i++)
				System.out.println("jyunTehai = " + tehai.jyunTehai[i].id);
		}

		{
			Hai[] minshun = new Hai[] { new Hai(Hai.KIND_WAN | 1),
					new Hai(Hai.KIND_WAN | 2), new Hai(Hai.KIND_WAN | 3) };

			for (int i = 0; i < 5; i++)
				tehai.addMinshun(minshun);

			Hai[] minkou = new Hai[] { new Hai(Hai.KIND_WAN | 1),
					new Hai(Hai.KIND_WAN | 1), new Hai(Hai.KIND_WAN | 1) };

			for (int i = 0; i < 5; i++)
				tehai.addMinkou(minkou);

			Hai[] minkan = new Hai[] { new Hai(Hai.KIND_WAN | 1),
					new Hai(Hai.KIND_WAN | 1), new Hai(Hai.KIND_WAN | 1),
					new Hai(Hai.KIND_WAN | 1) };

			for (int i = 0; i < 5; i++)
				tehai.addMinkan(minkan);

			Hai[] ankan = new Hai[] { new Hai(Hai.KIND_WAN | 1),
					new Hai(Hai.KIND_WAN | 1), new Hai(Hai.KIND_WAN | 1),
					new Hai(Hai.KIND_WAN | 1) };

			for (int i = 0; i < 5; i++)
				tehai.addankan(ankan);
		}

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
