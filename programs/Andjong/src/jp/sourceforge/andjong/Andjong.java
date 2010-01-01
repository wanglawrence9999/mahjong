package jp.sourceforge.andjong;

import jp.sourceforge.andjong.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class Andjong extends Activity implements OnClickListener {
	private static final String TAG = "Andjong";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate");

		setContentView(R.layout.main);

		// すべてのボタンに、クリックリスナーを設定する。
		View newButton = this.findViewById(R.id.new_button);
		newButton.setOnClickListener(this);
		View exitButton = this.findViewById(R.id.exit_button);
		exitButton.setOnClickListener(this);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.new_button:
			startActivity(new Intent(Andjong.this, Game.class));
			break;
		case R.id.exit_button:
			finish();
			break;
		}
	}
}