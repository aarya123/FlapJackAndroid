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
            /*((TextView)activity.findViewById(R.id.ace)).setText(s.getStrategy().hotnessMap.get("A")+"");
            ((TextView)activity.findViewById(R.id.two)).setText(s.getStrategy().hotnessMap.get("2")+"");
            ((TextView)activity.findViewById(R.id.three)).setText(s.getStrategy().hotnessMap.get("3")+"");
            ((TextView)activity.findViewById(R.id.four)).setText(s.getStrategy().hotnessMap.get("4")+"");
            ((TextView)activity.findViewById(R.id.five)).setText(s.getStrategy().hotnessMap.get("5")+"");
            ((TextView)activity.findViewById(R.id.six)).setText(s.getStrategy().hotnessMap.get("6")+"");
            ((TextView)activity.findViewById(R.id.seven)).setText(s.getStrategy().hotnessMap.get("7")+"");
            ((TextView)activity.findViewById(R.id.eight)).setText(s.getStrategy().hotnessMap.get("8")+"");
            ((TextView)activity.findViewById(R.id.nine)).setText(s.getStrategy().hotnessMap.get("9")+"");
            ((TextView)activity.findViewById(R.id.ten)).setText(s.getStrategy().hotnessMap.get("10")+"");
            ((TextView)activity.findViewById(R.id.jack)).setText(s.getStrategy().hotnessMap.get("J")+"");
            ((TextView)activity.findViewById(R.id.queen)).setText(s.getStrategy().hotnessMap.get("Q")+"");
            ((TextView)activity.findViewById(R.id.king)).setText(s.getStrategy().hotnessMap.get("K")+"");*/
        }
    }
}