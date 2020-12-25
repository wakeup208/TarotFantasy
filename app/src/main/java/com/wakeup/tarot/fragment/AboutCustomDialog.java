//package com.wakeup.tarot.fragment;
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.net.Uri;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.CompoundButton;
//import android.widget.TextView;
//import android.widget.Toast;
//import android.widget.CompoundButton.OnCheckedChangeListener;
//import android.widget.SeekBar.OnSeekBarChangeListener;
//import android.widget.SeekBar;
//
//import com.wakeup.tarot.R;
//import com.wakeup.tarot.view.ProfileActivity;
//
//public class AboutCustomDialog implements OnClickListener {
//
//	private ProfileActivity activity;
//	private Button btnCall;
//	private TextView tvWebsite;
//	private AlertDialog dialog;
//
//	public AboutCustomDialog(ProfileActivity a) {
//		activity = a;
//	}
//
//	protected AlertDialog createDialog() {
//		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
//		// Get the layout inflater
//		LayoutInflater inflater = activity.getLayoutInflater();
//
//		// Inflate and set the layout for the dialog
//		builder.setView(inflater.inflate(R.layout.about, null))
//				.setPositiveButton("OK", null);
//
//		return builder.create();
//	}
//
//	public void showDialog() {
//
//		dialog = createDialog();
//		dialog.setIcon(R.drawable.icon_about);
//		dialog.setTitle("NHỮNG NGƯỜI THỰC HIỆN");
//		dialog.show();
//
//		tvWebsite = (TextView) dialog.findViewById(R.id.tvWebsite);
//		tvWebsite.setText("NHỮNG NGƯỜI THỰC HIỆN\n\nThiết kế:\n- Lê Ngọc Anh – Dev Android App\n(Skype: leanhuit1992 / Email: greendream.ait@gmail.com)\n\nBiên tập:\n- Văn Thế Anh (Bé Béo)\n- Nguyễn Hữu Nhiên (Magic Knight)\n- Bùi Quốc Bảo Lộc (Paulie)\n\nĐơn vị bảo trợ:\n- Saigon Mystic House (http://mystichouse.vn)\n- Vietnam Tarot Reading (http://boitarot.vn)\n- Biddytarot (www.biddytarot.com)\n- Trải Bài Tarot (http://pupuneko.net)\n- Dịch Thuật Bảo Hữu (http://idichthuat.com)\n\n\nArtwork from Rider Waite Tarot Deck (1909 Edition) by Pamela Coleman Smith & Arthur Edward Waite - Publisher : US Games Systems - Layout illustrated from Galaxy Tarot (Galaxy Tone Software).");
//		tvWebsite.setOnClickListener(this);
//	}
//
//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.btnCall:
////			Intent callIntent = new Intent(Intent.ACTION_CALL);
////			callIntent.setData(Uri.parse("tel:01238059792"));
////			activity.startActivity(callIntent);
//			break;
//
//		case R.id.tvWebsite:
//			break;
//		}
//
//	}
//
//}