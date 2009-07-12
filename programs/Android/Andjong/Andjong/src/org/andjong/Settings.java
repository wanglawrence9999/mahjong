package org.andjong;

import org.andjong.R.id;

import android.os.Bundle;
import android.app.Activity;
import android.widget.Spinner;
import android.widget.ArrayAdapter;

public class Settings extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.settings);

		final Spinner spin1 = (Spinner) findViewById(id.tanyao_setting);
		String[] arr = {"‚ ‚è","‚È‚µ"};
		
		ArrayAdapter adapter = new ArrayAdapter(this, R.layout.list, arr);
		spin1.setAdapter(adapter);
		spin1.setSelection(0);
	}
}