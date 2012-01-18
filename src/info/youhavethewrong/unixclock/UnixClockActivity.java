package info.youhavethewrong.unixclock;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.lang.System;

public class UnixClockActivity extends Activity {
	static final String TAG = "UnixClock";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Log.d(TAG, "~~~~~~~~~~~~~~~~~"+System.currentTimeMillis()+"||||||||||");
    }
}