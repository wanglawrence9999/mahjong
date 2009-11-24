package jp.sourceforge.andjong.mahjong;

public class InfoUI extends Info {
	private PlayerAction mPlayerAction;

	public InfoUI(Mahjong game, PlayerAction playerAction) {
		super(game);
		this.setPlayerAction(playerAction);
	}

	public Hai[] getDoraAll() {
		return game.getYama().getUraDoraHais();
	}

	public int getManKaze() {
		return game.getManKaze();
	}

	/**
	 * 手牌をコピーします。
	 *
	 * @param tehai
	 *            手牌
	 * @param kaze
	 *            風
	 */
	public void copyTehai(Tehai tehai, int kaze) {
		game.copyTehaiUi(tehai, kaze);
	}

	public void setPlayerAction(PlayerAction playerAction) {
		this.mPlayerAction = playerAction;
	}

	public PlayerAction getPlayerAction() {
		return mPlayerAction;
	}
}
