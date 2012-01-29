package info.youhavethewrong.unixclock;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * The main point of this AppWidgetProvider is to accept broadcasts about time
 * updates and change the TextView to reflect the current Unix Time.
 * @author negated
 *
 */
public class UnixClockWidgetProvider extends AppWidgetProvider {
	static final String TAG = "UnixClockWidgetProvider";

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onDeleted(context, appWidgetIds);
	}

	@Override
	public void onDisabled(Context context) {
		// TODO Auto-generated method stub
		super.onDisabled(context);
	}

	@Override
	public void onEnabled(Context context) {
		// TODO Auto-generated method stub
		super.onEnabled(context);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
		// Only need to implement this if I want to provide back compat to
		// android 1.5 due to onDelete bugs
	}

	/**
	 * This is called every update period, as well as the first time a user adds
	 * this widget.  It isn't called the first time a user adds the widget IF we ever
	 * implement a configuration activity.
	 */
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		final int N = appWidgetIds.length;

		// Ask UnixClockActivity for an update?
        // do this for every App Widget under this provider that the user has added
        for (int i=0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];
            
            // Create an Intent to launch UnixClockActivity
            Intent intent = new Intent(context, UnixClockActivity.class);
            
            // need to somehow get UnixClockActivity.updateTime() to update my textview
            /*
             * Maybe create a custom intent UPDATE_TIME
             * - onReceive, call UnixClockActivity.getTime() or something?
             * - set our TextView's text = the results from getTime
             */
            
            // get the layout for this App Widget
            RemoteViews views = new RemoteViews(context.getPackageName(), 
            		R.layout.widget_layout);
            
            // tell the appWidgetManager to perform an update on the current App Widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
	}
}
