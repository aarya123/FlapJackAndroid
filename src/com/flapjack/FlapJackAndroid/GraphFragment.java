package com.flapjack.FlapJackAndroid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

/**
 * User: AnubhawArya
 * Date: 9/14/13
 * Time: 2:53 PM
 */
public class GraphFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        GraphView.GraphViewData[] data = new GraphView.GraphViewData[Result.session.getCumProfit().length];
        for (int i = 0; i < Result.session.getCumProfit().length; i++)
            data[i] = new GraphView.GraphViewData(i + 1, Result.session.getCumProfit()[i]);
        LineGraphView temp = new LineGraphView(getActivity(), "Total Profit per Game");
        temp.addSeries(new GraphViewSeries(data));
        return temp;
    }
}
