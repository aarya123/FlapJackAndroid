package com.flapjack.FlapJackAndroid;

import Engine.Casino;
import Engine.Session;
import Engine.Strategy;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

/**
 * User: AnubhawArya
 * Date: 9/13/13
 * Time: 10:59 PM
 */
public class Result extends FragmentActivity {
    public static Session session;

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
            session = new Session(params[0], new Strategy());
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
            ((ViewPager) activity.findViewById(R.id.pager)).setAdapter(new StatPagerAdapter(Result.this.getSupportFragmentManager()));
            /**/
        }
    }
}