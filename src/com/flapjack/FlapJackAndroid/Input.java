package com.flapjack.FlapJackAndroid;

import Engine.Casino;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;

/**
 * User: AnubhawArya
 * Date: 9/13/13
 * Time: 10:59 PM
 */
public class Input extends Activity {
    Spinner casinoList;
    TextView numDecks, bjMultiplier;
    CheckBox soft17s, doubleSplitting, resplitAces;
    EditText numGames;
    Button goButton;
    ArrayList<Casino> casinos;
    static Casino selectedCasino;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input);
        casinos = new ArrayList<Casino>();
        casinos.add(new Casino("Bellagio", 1.5, 6, true, true, true));
        casinos.add(new Casino("Caesar's Palace", 1.5, 2, true, false, false));
        casinos.add(new Casino("MGM Grand", 1.5, 6, false, true, true));
        casinoList = (Spinner) findViewById(R.id.casinoList);
        numDecks = (TextView) findViewById(R.id.numDecks);
        bjMultiplier = (TextView) findViewById(R.id.bjMultiplier);
        soft17s = (CheckBox) findViewById(R.id.soft17s);
        doubleSplitting = (CheckBox) findViewById(R.id.doubleSplitting);
        resplitAces = (CheckBox) findViewById(R.id.resplitAces);
        numGames = (EditText) findViewById(R.id.numGames);
        goButton = (Button) findViewById(R.id.goButton);
        casinoList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (Casino casino : casinos)
                    if (casino.getName().equals(((TextView) view).getText())) {
                        numDecks.setText(casino.getNumberOfDecks() + "");
                        bjMultiplier.setText(casino.getBlackjackMultiplier() + "");
                        soft17s.setChecked(casino.isHitOnSoft17s());
                        doubleSplitting.setChecked(casino.isDoubleAfterSplit());
                        resplitAces.setChecked(casino.isResplitAfterAce());
                    }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        goButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                for (Casino casino : casinos)
                    if (casinoList.getSelectedItem().equals(casino.getName())) {
                        casino.setNumberOfGames(Integer.parseInt(numGames.getText().toString()));
                        selectedCasino = casino;
                        startActivity(new Intent(Input.this, Result.class));
                    }
            }
        });
    }
}