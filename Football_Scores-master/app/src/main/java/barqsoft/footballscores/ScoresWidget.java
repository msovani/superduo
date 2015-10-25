package barqsoft.footballscores;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

import barqsoft.footballscores.service.ScoresWidgetService;

public class ScoresWidget extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        context.startService(new Intent(context,ScoresWidgetService.class));

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

}
