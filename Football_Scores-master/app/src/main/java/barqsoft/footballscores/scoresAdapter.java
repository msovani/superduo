package barqsoft.footballscores;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * This adapter is used in main application to show the data obtained from api via local database
 * Created by yehya khaled on 2/26/2015.
 */
public class scoresAdapter extends CursorAdapter
{
    private static final int COL_HOME = 3;
    private static final int COL_AWAY = 4;
    private static final int COL_HOME_GOALS = 6;
    private static final int COL_AWAY_GOALS = 7;
    private static final int COL_LEAGUE = 5;
    private static final int COL_MATCHDAY = 9;
    private static final int COL_ID = 8;
    private static final int COL_MATCHTIME = 2;
    public double detail_match_id = 0;

    public scoresAdapter(Context context,Cursor cursor,int flags)
    {
        super(context, null,flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent)
    {
        View mItem = LayoutInflater.from(context).inflate(R.layout.scores_list_item, parent, false);
        ViewHolder mHolder = new ViewHolder(mItem);
        mItem.setTag(mHolder);
        //Log.v(FetchScoreTask.LOG_TAG,"new View inflated");
        return mItem;
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor)
    {
        final ViewHolder mHolder = (ViewHolder) view.getTag();
        mHolder.home_name.setText(cursor.getString(COL_HOME));
        mHolder.away_name.setText(cursor.getString(COL_AWAY));
        mHolder.date.setText(cursor.getString(COL_MATCHTIME));
        mHolder.score.setText(Utilies.getScores(cursor.getInt(COL_HOME_GOALS), cursor.getInt(COL_AWAY_GOALS)));
        mHolder.match_id = cursor.getDouble(COL_ID);
        mHolder.home_crest.setImageResource(Utilies.getTeamCrestByTeamName(
                cursor.getString(COL_HOME)));

        mHolder.away_crest.setImageResource(Utilies.getTeamCrestByTeamName(
                cursor.getString(COL_AWAY)
        ));

        //set content description
        mHolder.home_crest.setContentDescription(context.getResources().getString(R.string.logo_text) + cursor.getString(COL_HOME));
        mHolder.away_crest.setContentDescription(context.getResources().getString(R.string.logo_text) + cursor.getString(COL_AWAY));

        LayoutInflater vi = (LayoutInflater) context.getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = vi.inflate(R.layout.detail_fragment, null);
        ViewGroup container = (ViewGroup) view.findViewById(R.id.details_fragment_container);
        if(mHolder.match_id == detail_match_id)
        {

            container.addView(v, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                    , ViewGroup.LayoutParams.MATCH_PARENT));
            TextView match_day = (TextView) v.findViewById(R.id.matchday_textview);
            match_day.setText(Utilies.getMatchDay(cursor.getInt(COL_MATCHDAY),
                    cursor.getInt(COL_LEAGUE)));
            //For Accessibility
            match_day.setContentDescription(Utilies.getMatchDay(cursor.getInt(COL_MATCHDAY),
                    cursor.getInt(COL_LEAGUE)));

            TextView league = (TextView) v.findViewById(R.id.league_textview);
            league.setText(Utilies.getLeague(cursor.getInt(COL_LEAGUE)));

            //For Accessibility
            league.setContentDescription(Utilies.getLeague(cursor.getInt(COL_LEAGUE)));

            Button share_button = (Button) v.findViewById(R.id.share_button);
            share_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    //add Share Action
                    try {
                        context.startActivity(createShareForecastIntent(mHolder.home_name.getText() + " "
                                + mHolder.score.getText() + " " + mHolder.away_name.getText() + " "));
                    }catch (android.content.ActivityNotFoundException anfe){
                        Log.e("Share", "Unable to launch activity");
                    }
                }
            });

        }
        else
        {
            container.removeAllViews();
        }

    }
    private Intent createShareForecastIntent(String ShareText) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        String FOOTBALL_SCORES_HASHTAG = "#Football_Scores";
        shareIntent.putExtra(Intent.EXTRA_TEXT, ShareText + FOOTBALL_SCORES_HASHTAG);
        return shareIntent;
    }

}
