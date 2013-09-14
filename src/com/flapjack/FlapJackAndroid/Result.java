package com.flapjack.FlapJackAndroid;

import Engine.Casino;
import Engine.Session;
import Engine.Strategy;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

/**
 * User: AnubhawArya
 * Date: 9/13/13
 * Time: 10:59 PM
 */
public class Result extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        new Compute(this).execute(Input.selectedCasino);
    }

    public class Compute extends AsyncTask<Casino, String, Session> {
        Activity activity;

        public Compute(Activity activity) {
            this.activity = activity;
        }

        protected Session doInBackground(Casino... params) {
            Session session = new Session(params[0], new Strategy());
            session.playGames();
            return session;
        }

        protected void onPostExecute(Session s) {
            activity.findViewById(R.id.loading).setVisibility(View.GONE);
            activity.findViewById(R.id.statistics).setVisibility(View.VISIBLE);
            ((TextView) activity.findViewById(R.id.profit)).setText(s.getTotalProfit() + "");
            ((TextView) activity.findViewById(R.id.totWage)).setText(s.getTotalWage() + "");
            ((TextView) activity.findViewById(R.id.percentWon)).setText(s.getGameWonPercentage() + "");
            ((TextView) activity.findViewById(R.id.gamesPlayed)).setText(s.getCasino().getNumberOfGames() + "");
            GraphView.GraphViewData[] data = new GraphView.GraphViewData[s.getCumProfit().length];
            for (int i = 0; i < s.getCumProfit().length; i++)
                data[i] = new GraphView.GraphViewData(i + 1, s.getCumProfit()[i]);
            LineGraphView temp = new LineGraphView(Result.this, "Total Profit per Game");
            temp.addSeries(new GraphViewSeries(data));
            ((LinearLayout) activity.findViewById(R.id.lineGraphView)).addView(temp);
        }
    }
}