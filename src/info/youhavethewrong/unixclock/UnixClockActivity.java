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
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Need to add onTouchListener to notice when a user touches the TextView.
 * When TextView is touched, rotate through display modes.
 * Display modes: decimal, hex.
 * Add custom font.
 * Add menu for colors: green on black, amber on gray.
 * * Save theme choice.
 * Add menu for about. -> check yamba baseActivity() for guide
 * Add desktop Widget.
 */

public class UnixClockActivity extends Activity implements Runnable {
	// debug/logcat tag for this class
	static final String TAG = "UnixClockActivity";
	
	// the handler controls the thread
	final Handler timeHandler = new Handler();
	
	// the thread will do what it's told by the handler
	Thread runner;
	
	// handles for parts of our ui
	TextView et;
	TextView ex;
	LinearLayout ll;
	
	// this is what the thread does
	final Runnable update_ui = new Runnable() {
		public void run() {
			// grab the current unix time
			String utime = getTime();
			
	        // write out our new time to the TextView
	        et.setText(utime); 
		};
	};
	
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
    	Log.d(TAG, "onCreate()");
        setContentView(R.layout.main);
        
        // grab a handle for the LinearLayout
        ll = (LinearLayout)findViewById(R.id.clockLayout);
        // grab handles for the clock TextView widgets
        et = (TextView)findViewById(R.id.unixTime);
        ex = (TextView)findViewById(R.id.explainId);
        
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Glass_TTY_VT220.ttf");
        et.setTypeface(tf);
        ex.setTypeface(tf);
        
        // if our thread doesn't exist yet, create it and get it spinning
        if (runner == null) {
        	runner = new Thread(this);
        	runner.start();
        }
    }
    
	// called only once first time menu is clicked on
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}
	
	// called every time user clicks on menu
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
			case R.id.colorScheme:
				this.cycleColorScheme();
				Log.d(TAG, "colorScheme menu item");
				break;
			case R.id.aboutUnixClock:
				// display about splash
				Log.d(TAG, "aboutUnixClock menu item");
				break;
		}
		return true;
	}
	
	// no longer visible to the user.  will be restarted or destroyed.
	protected void onStop() {
		super.onStop();
		// stop running our thread?
	}
	
	public void run() {
		while (runner != null) {
			try {
				/**
				 * want about 4 refreshes / second to get a fairly accurate time,
				 * while keeping the amount of work reasonable
				 */
				Thread.sleep(250);
				Log.i(TAG+".run()", "Tick");
			}
			catch (InterruptedException e) { 
				Log.e(TAG+".run()", "Something interrupted our sleep"); 
			}
			timeHandler.post(update_ui);
		}
	}
	
    public String getTime() {
    	// just want the seconds, not the millis
        return ""+(System.currentTimeMillis() / 1000l);
    }
    
    public void cycleColorScheme() {
 	   	// set text & background color
    	int curText = et.getCurrentTextColor();
    	int amber = ll.getResources().getColor(R.color.amber);
    	int green = ll.getResources().getColor(R.color.green);
    	
    	/* seems a little weird to declare colors separately, but this does save
    	 * a whole 4 bytes of memory + a function call
    	 */
    	if(curText == amber) {
        	int black = ll.getResources().getColor(R.color.black);
    		et.setTextColor(green);
    		ex.setTextColor(green);
    		ll.setBackgroundColor(black);
    	}
    	else {
        	int grey = ll.getResources().getColor(R.color.grey);
    		et.setTextColor(amber);
    		ex.setTextColor(amber);
    		ll.setBackgroundColor(grey);
    	}
    }
}
