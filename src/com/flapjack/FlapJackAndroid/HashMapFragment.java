package com.flapjack.FlapJackAndroid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * User: AnubhawArya
 * Date: 9/14/13
 * Time: 2:45 PM
 */
public class HashMapFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.map, null);
        if (Result.session == null)
            Log.d("FlapJack", "Session null");
        if (Result.session.getStrategy() == null)
            Log.d("FlapJack", "Strategy null");
        if (Result.session.getStrategy().hotnessMap == null)
            Log.d("FlapJack", "hotnessMap null");
        ((TextView) v.findViewById(R.id.ace)).setText(Result.session.getStrategy().hotnessMap.get("A") + "");
        ((TextView) v.findViewById(R.id.two)).setText(Result.session.getStrategy().hotnessMap.get("2") + "");
        ((TextView) v.findViewById(R.id.three)).setText(Result.session.getStrategy().hotnessMap.get("3") + "");
        ((TextView) v.findViewById(R.id.four)).setText(Result.session.getStrategy().hotnessMap.get("4") + "");
        ((TextView) v.findViewById(R.id.five)).setText(Result.session.getStrategy().hotnessMap.get("5") + "");
        ((TextView) v.findViewById(R.id.six)).setText(Result.session.getStrategy().hotnessMap.get("6") + "");
        ((TextView) v.findViewById(R.id.seven)).setText(Result.session.getStrategy().hotnessMap.get("7") + "");
        ((TextView) v.findViewById(R.id.eight)).setText(Result.session.getStrategy().hotnessMap.get("8") + "");
        ((TextView) v.findViewById(R.id.nine)).setText(Result.session.getStrategy().hotnessMap.get("9") + "");
        ((TextView) v.findViewById(R.id.ten)).setText(Result.session.getStrategy().hotnessMap.get("10") + "");
        ((TextView) v.findViewById(R.id.jack)).setText(Result.session.getStrategy().hotnessMap.get("J") + "");
        ((TextView) v.findViewById(R.id.queen)).setText(Result.session.getStrategy().hotnessMap.get("Q") + "");
        ((TextView) v.findViewById(R.id.king)).setText(Result.session.getStrategy().hotnessMap.get("K") + "");
        return v;
    }
}
