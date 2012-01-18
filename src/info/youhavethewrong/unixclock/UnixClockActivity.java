package info.youhavethewrong.unixclock;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.EditText;

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
	EditText et;
	
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

    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
    	Log.d(TAG, "onCreate()");
        setContentView(R.layout.main);
        
        // grab a handle on the EditText widget
        et = (EditText)findViewById(R.id.unixTime);
        
        // if our thread doesn't exist yet, create it and get it spinning
        if (runner == null) {
        	runner = new Thread(this);
        	runner.start();
        }
    }
    
    public void updateTime() {
        String utime = ""+(System.currentTimeMillis() / 1000l);
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