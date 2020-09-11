package com.wakeup.tarot.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.SeekBar;

import com.wakeup.tarot.R;
import com.wakeup.tarot.view.ProfileActivity;

public class ChangeFontSizeCustomDialog implements OnCheckedChangeListener, OnSeekBarChangeListener{
	
	private ProfileActivity activity;
	private SeekBar sbPrecision;
	private TextView tvDemoFontSize;
	private AlertDialog dialog;
	
	// Determine precision of floating-point number -1 if don't care
	private float mFont_size;
	
	public ChangeFontSizeCustomDialog(ProfileActivity a, float font_size) {
		activity = a;
		mFont_size = font_size;
	}

	protected AlertDialog createDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
	    // Get the layout inflater
	    LayoutInflater inflater = activity.getLayoutInflater();

	    // Inflate and set the layout for the dialog
	    builder.setView(inflater.inflate(R.layout.change_font_size, null))
	           .setPositiveButton("OK", null);
	    
	    return builder.create();
	}
	
	public void showDialog() {
		
		dialog = createDialog();
		dialog.setIcon(R.drawable.icon_font);
		dialog.setTitle("Chọn kích cỡ font chữ");
		dialog.show();
		
		sbPrecision = (SeekBar)dialog.findViewById(R.id.sbPrecision);
		sbPrecision.setMax(100);
		sbPrecision.setProgress((int)mFont_size);
		sbPrecision.setOnSeekBarChangeListener(this);
		
		
		
		tvDemoFontSize = (TextView)dialog.findViewById(R.id.tvDemoFontSize);
		tvDemoFontSize.setTextSize(mFont_size);
		tvDemoFontSize.setText("Cỡ chữ: " + mFont_size);
		
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		// TODO Auto-generated method stub
		mFont_size = progress;
		tvDemoFontSize.setTextSize(mFont_size);
		tvDemoFontSize.setText("Cỡ chữ: " + mFont_size);
		activity.setFontSizeFromDialog(mFont_size);
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		this.sbPrecision.setEnabled(isChecked);
	}

}