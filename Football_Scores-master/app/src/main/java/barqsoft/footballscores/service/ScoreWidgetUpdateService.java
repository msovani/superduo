package barqsoft.footballscores.service;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by msovani on 10/25/15.
 */
public class ScoreWidgetUpdateService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ScoreWidgetFactory(this.getApplicationContext(), intent);
    }
}
