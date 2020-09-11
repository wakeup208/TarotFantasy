package com.wakeup.tarot.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.SeekBar;

import com.wakeup.tarot.R;
import com.wakeup.tarot.view.MainActivity;

public class RequestActivatedApplicationCustomDialog implements OnClickListener {

	private MainActivity activity;
	private Button btnCall;
	private TextView tvWebsite;
	private AlertDialog dialog;

	public RequestActivatedApplicationCustomDialog(MainActivity a) {
		activity = a;
	}

	protected AlertDialog createDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		// Get the layout inflater
		LayoutInflater inflater = activity.getLayoutInflater();

		// Inflate and set the layout for the dialog
		builder.setView(inflater.inflate(R.layout.buy_tarot_card, null))
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						activity.finish();
					}
				});

		return builder.create();
	}

	public void showDialog() {

		dialog = createDialog();
		dialog.setIcon(R.drawable.icon_active);
		dialog.setTitle("BẢN DEMO CẦN KÍCH HOẠT");
		dialog.show();

		btnCall = (Button) dialog.findViewById(R.id.btnCall);
		btnCall.setOnClickListener(this);

		tvWebsite = (TextView) dialog.findViewById(R.id.tvWebsite);
		tvWebsite.setText("Vui lòng gọi cho Lê Ngọc Anh để nhận được tin nhắn kích hoạt ứng dụng miễn phí");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnCall:
//			Intent callIntent = new Intent(Intent.ACTION_CALL);
//			callIntent.setData(Uri.parse("tel:01696016830"));
//			activity.startActivity(callIntent);
			break;
			
		case R.id.tvWebsite:
			break;
		}

	}

}