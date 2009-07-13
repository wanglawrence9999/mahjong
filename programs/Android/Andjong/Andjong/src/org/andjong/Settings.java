package org.andjong;

import org.andjong.R.id;

import android.os.Bundle;
import android.app.Activity;
import android.widget.Spinner;
import android.widget.ArrayAdapter;

public class Settings extends Activity{
	
	private ArrayAdapter adapter_tanyao_setting;
	private ArrayAdapter adapter_tunsuu_setting;
	private Spinner spin_tanyao_setting;
	private Spinner spin_tensuu_setting;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.settings);

		spin_tanyao_setting = (Spinner) findViewById(id.tanyao_setting);
		spin_tensuu_setting = (Spinner) findViewById(id.tensuu_setting);
		
		String[] arr_tanyao_setting = {"‚ ‚è","‚È‚µ"};
		String[] arr_tensuu_setting = {"25000","35000"};
		
		adapter_tanyao_setting = new ArrayAdapter(this, R.layout.list, arr_tanyao_setting);
		adapter_tunsuu_setting = new ArrayAdapter(this, R.layout.list, arr_tensuu_setting);

		spin_tanyao_setting.setAdapter(adapter_tanyao_setting);
		spin_tanyao_setting.setSelection(0);
		spin_tensuu_setting.setAdapter(adapter_tunsuu_setting);
		spin_tensuu_setting.setSelection(0);
	}
}