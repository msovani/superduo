package barqsoft.footballscores.service;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;



/**
 * Created by msovani on 10/25/15.
 */
public class ScoreWidgetFactory implements RemoteViewsService.RemoteViewsFactory{
    private Context mContext;
    private int mAppWidgetId;
    public ScoreWidgetFactory(Context context, Intent intent){
        mContext = mContext;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
        String contentUrl = "content://barqsoft.footballscores/date";
        Uri uri = Uri.parse(contentUrl);
        Cursor dataCursor = mContext.getContentResolver().query(uri, null, null, null, "date");
        Log.d("WidgetFactory", "There are : " + dataCursor.getCount());
    }
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }
}
