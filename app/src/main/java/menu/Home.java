package menu;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.gms.nearby.Nearby;
import com.zbar.lib.CaptureActivity;
import com.zbar.lib.HistoryActivity;
import com.zbar.lib.MylocActivity;
import com.zbar.lib.NearbyMenu;
import com.zbar.lib.R;

public class Home extends Activity {

	private Button button_scan, button_savedHistory, button_whereami;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
		Window myWindow = this.getWindow();
		myWindow.setFlags(flag, flag);

		setContentView(R.layout.activity_home);

		button_scan = (Button) findViewById(R.id.scanmap);
		button_savedHistory = (Button) findViewById(R.id.savedhistory);
		button_whereami = (Button) findViewById(R.id.whereami);

		button_scan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(Home.this, CaptureActivity.class);
				startActivity(intent);
			}

		});

        button_savedHistory.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Home.this, NearbyMenu.class);
                startActivity(intent);
            }
        });

        button_whereami.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Home.this, MylocActivity.class);
                startActivity(intent);
            }
        });

	}


}
