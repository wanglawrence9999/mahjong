package jp.sourceforge.andjong.mahjong;

public class PlayerAction {
	public static final int STATE_NONE = 0;
	public static final int STATE_ACTION_WAIT = 1;

	private int mState = STATE_NONE;

	public void setState(int state) {
		this.mState = state;
	}

	public int getState() {
		return mState;
	}

	public synchronized void actionWait() {
		try {
			wait();
		} catch (InterruptedException e) {
			// TODO é©ìÆê∂ê¨Ç≥ÇÍÇΩ catch ÉuÉçÉbÉN
			e.printStackTrace();
		}
	}

	public synchronized void actionNotifyAll() {
		notifyAll();
	}
}
