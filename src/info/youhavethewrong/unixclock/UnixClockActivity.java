package info.youhavethewrong.unixclock;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

public class UnixClockActivity extends Activity {
	
	protected void onPause() {
		super.onPause();
		updateTime();
	}

	protected void onRestart() {
		super.onRestart();
		updateTime();
	}

	protected void onResume() {
		super.onResume();
		updateTime();
	}

	protected void onStart() {
		super.onStart();
		updateTime();
	}

	protected void onStop() {
		super.onStop();
		updateTime();
	}

	static final String TAG = "UnixClockActivity";
	
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	Log.d(TAG, "onCreate()");
        setContentView(R.layout.main);
        updateTime();
    }
    
    public void updateTime() {
        String utime = ""+System.currentTimeMillis();
        EditText et = (EditText)findViewById(R.id.unixTime);
        et.setText(utime);    	
    }
}