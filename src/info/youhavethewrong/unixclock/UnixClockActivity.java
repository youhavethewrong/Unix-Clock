package info.youhavethewrong.unixclock;

/**
 * I chose to write this simple app to get the hang of UIs and threads.
 * 
 * This Activity was inspired by the following URLs:
 * http://stackoverflow.com/questions/2830699/simple-android-binary-text-clock
 * http://stackoverflow.com/questions/732034/getting-unixtime-in-java
 * http://developer.android.com/guide/topics/fundamentals/processes-and-threads.html
 * 
 * Began 2012.01.17
 */

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

/**
 * Need to add onTouchListener to notice when a user touches the TextView.
 * When TextView is touched, rotate through display modes.
 * Display modes: decimal, hex.
 * Add custom font.
 * Add menu for colors: green on black, amber on gray, black on white.
 * Add menu for about. -> check yamba baseActivity() for guide
 * Add menu for font choice.
 * Add desktop Widget.
 */

public class UnixClockActivity extends Activity implements Runnable {
	
	static final String TAG = "UnixClockActivity";
	
	// the handler controls the thread
	final Handler timeHandler = new Handler();
	
	// the thread will do what it's told by the handler
	Thread runner;
	
	// this is what the thread does
	final Runnable update_ui = new Runnable() {
		public void run() {
			updateTime();
		};
	};
	
	// Our little clock widget
	TextView et;
	
	// no longer visible to the user.  will be restarted or destroyed.
	protected void onStop() {
		super.onStop();
		// stop running our thread?
	}

    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
    	Log.d(TAG, "onCreate()");
        setContentView(R.layout.main);
        
        // grab a handle on the EditText widget
        et = (TextView)findViewById(R.id.unixTime);
        
        // if our thread doesn't exist yet, create it and get it spinning
        if (runner == null) {
        	runner = new Thread(this);
        	runner.start();
        }
    }
    
    public void updateTime() {
    	// just want the seconds, not the millis
        String utime = ""+(System.currentTimeMillis() / 1000l);
        
        // write out our new time to the EditText
        et.setText(utime);    	
    }

	public void run() {
		while (runner != null) {
			try {
				Thread.sleep(1000);
				Log.i(TAG+".run()", "Tick");
			}
			catch (InterruptedException e) { 
				Log.e(TAG+".run()", "Something interrupted our sleep"); 
			}
			timeHandler.post(update_ui);
		}
		
	}
}
