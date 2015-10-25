package barqsoft.footballscores;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RemoteViews;

import java.util.Random;

import barqsoft.footballscores.service.ScoreWidgetUpdateService;

public class ScoresWidget extends AppWidgetProvider {
    public static final int COL_HOME = 3;
    public static final int COL_AWAY = 4;
    public static final int COL_HOME_GOALS = 6;
    public static final int COL_AWAY_GOALS = 7;
    public static final int COL_DATE = 1;
    public static final int COL_LEAGUE = 5;
    public static final int COL_MATCHDAY = 9;
    public static final int COL_ID = 8;
    public static final int COL_MATCHTIME = 2;
    scoresAdapter mAdapter;
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    @Override
    public void onRestored(Context context, int[] oldWidgetIds, int[] newWidgetIds) {
        super.onRestored(context, oldWidgetIds, newWidgetIds);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        // Get all ids
        ComponentName thisWidget = new ComponentName(context,
                ScoresWidget.class);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.content_scores_widget);

        String contentUrl = "content://barqsoft.footballscores";
        Uri uri = Uri.parse(contentUrl);
        Cursor dataCursor = context.getContentResolver().query(uri, null, null, null, "date");

        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        for (int widgetId : allWidgetIds) {


            Log.d("WidgetFactory", "There are : " + dataCursor.getCount());

            if (dataCursor.getCount() > 0)
            {
                if (dataCursor.moveToLast())
                {
                    for (int i = 0; i < dataCursor.getColumnCount(); i++) {
                        Log.d("WidgetFactory", "There are : " + dataCursor.getColumnName(i));
                    }

                    remoteViews.setTextViewText(R.id.home_name, dataCursor.getString(COL_HOME));
                    remoteViews.setTextViewText(R.id.away_name, dataCursor.getString(COL_AWAY));
                    remoteViews.setTextViewText(R.id.data_textview, dataCursor.getString(COL_MATCHTIME));
                    remoteViews.setTextViewText(R.id.score_textview, Utilies.getScores(dataCursor.getInt(COL_HOME_GOALS),dataCursor.getInt(COL_AWAY_GOALS)));
//                    mHolder.match_id = dataCursor.getDouble(COL_ID);
                    remoteViews.setImageViewResource(R.id.home_crest, Utilies.getTeamCrestByTeamName(
                            dataCursor.getString(COL_HOME)));
                    remoteViews.setImageViewResource(R.id.away_crest, Utilies.getTeamCrestByTeamName(
                            dataCursor.getString(COL_AWAY)));
                    appWidgetManager.updateAppWidget(widgetId, remoteViews);

                }

            }


//            // Register an onClickListener
            Intent intent = new Intent(context, ScoresWidget.class);
//
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
//            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
//
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.away_name, pendingIntent);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
            dataCursor.close();
        }



        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);


        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

}
