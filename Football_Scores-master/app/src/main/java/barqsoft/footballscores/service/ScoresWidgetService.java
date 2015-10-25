package barqsoft.footballscores.service;

import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.widget.RemoteViews;

import barqsoft.footballscores.MainActivity;
import barqsoft.footballscores.R;
import barqsoft.footballscores.ScoresWidget;

/**
 * Service to show one match at a time
 * Created by msovani on 10/25/15.
 */
public class ScoresWidgetService extends IntentService {

    private static final int COL_HOME = 3;
    private static final int COL_AWAY = 4;
    private static final int COL_HOME_GOALS = 6;
    private static final int COL_AWAY_GOALS = 7;
    public static final int COL_DATE = 1;
    public static final int COL_LEAGUE = 5;
    public static final int COL_MATCHDAY = 9;
    public static final int COL_ID = 8;
    private static final int COL_MATCHTIME = 2;

    public ScoresWidgetService() {
        super("ScoresWidgetService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, ScoresWidget.class));

        String contentUrl = "content://barqsoft.footballscores";
        Uri uri = Uri.parse(contentUrl);
        Cursor scoresData = getContentResolver().query(uri, null, null, null, "date");

        if (scoresData == null) {
            return;
        }
        scoresData.moveToLast();

        String homeTeam = scoresData.getString(COL_HOME);
        Integer homeGoals = scoresData.getInt(COL_HOME_GOALS);
        String awayTeam = scoresData.getString(COL_AWAY);
        Integer awayGoals = scoresData.getInt(COL_AWAY_GOALS);
        String matchTime = scoresData.getString(COL_MATCHTIME);
        scoresData.close();


        Intent clickingIntent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, clickingIntent, 0);
        RemoteViews views = new RemoteViews(getPackageName(), R.layout.content_scores_widget);
        views.setOnClickPendingIntent(R.id.widget_relative_layout, pendingIntent);

        views.setTextViewText(R.id.home_name, homeTeam);

        if ( (homeGoals > -1) && (awayGoals > -1)) {
            views.setTextViewText(R.id.score_textview, homeGoals.toString() + "-" + awayGoals.toString());
            views.setContentDescription(R.id.score_textview, homeGoals.toString() + "-" + awayGoals.toString());
        }else {
            views.setTextViewText(R.id.score_textview, getResources().getString(R.string.no_game_yet));
            views.setContentDescription(R.id.score_textview, getResources().getString(R.string.no_game_yet));
        }
        views.setTextViewText(R.id.data_textview, matchTime);
        views.setTextViewText(R.id.away_name, awayTeam);

        appWidgetManager.updateAppWidget(appWidgetIds, views);
    }

    }
