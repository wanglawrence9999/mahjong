package jp.sourceforge.andjong;

import jp.sourceforge.andjong.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class Andjong extends Activity implements OnClickListener {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);

		this.findViewById(R.id.new_button).setOnClickListener(this);
		this.findViewById(R.id.about_button).setOnClickListener(this);
		this.findViewById(R.id.settings_button).setOnClickListener(this);
		this.findViewById(R.id.exit_button).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.new_button:
			startActivity(new Intent(Andjong.this, Game.class));
			break;
		case R.id.about_button:
			startActivity(new Intent(Andjong.this, About.class));
			break;
		case R.id.settings_button:
			startActivity(new Intent(Andjong.this, Settings.class));
			break;
		case R.id.exit_button:
			finish();
			break;
		default:
			break;
		}
	}
}