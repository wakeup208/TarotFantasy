package com.wakeup.tarot.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.wakeup.tarot.R;

public class BuyTarotCustomDialog implements OnClickListener {

	private Activity activity;
	private Button btnCall;
	private TextView tvWebsite;
	private AlertDialog dialog;

	public BuyTarotCustomDialog(Activity a) {
		activity = a;
	}

	protected AlertDialog createDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		// Get the layout inflater
		LayoutInflater inflater = activity.getLayoutInflater();

		// Inflate and set the layout for the dialog
		builder.setView(inflater.inflate(R.layout.buy_tarot_card, null))
				.setPositiveButton("OK", null);

		return builder.create();
	}

	public void showDialog() {

		dialog = createDialog();
		dialog.setIcon(R.drawable.btn_shop);
		dialog.setTitle("MUA BÀI TAROT");
		dialog.show();

		btnCall = (Button) dialog.findViewById(R.id.btnCall);
		btnCall.setOnClickListener(this);

		tvWebsite = (TextView) dialog.findViewById(R.id.tvWebsite);
		tvWebsite.setOnClickListener(this);
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