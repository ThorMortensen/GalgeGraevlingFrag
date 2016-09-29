package com.example.thor.galgraevling;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import galgeleg.Galgelogik;

public class Playscreen extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {

    Galgelogik galgelogik = new Galgelogik();
    EditText brugerInput;
    Playscreen parrent = this;
    TextView brugtBogstaver;
    TextView ordet;
    ImageView hangBilled;
    int billedID[];
    TextView dødtxt;
//    Button gætOrd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playscreen);
        Toast.makeText(this, galgelogik.getOrdet(), Toast.LENGTH_LONG).show();
        findViewById(R.id.buttonStartNy).setOnClickListener(this);
//        gætOrd = (Button) findViewById(R.id.buttonGæt);
//        gætOrd.setOnClickListener(this);
        brugerInput = (EditText) findViewById(R.id.editTextBrugerInput);
        brugtBogstaver = (TextView) findViewById(R.id.textViewBrugt);
        ordet = (TextView) findViewById(R.id.textViewGetteOrd);
        hangBilled = (ImageView) findViewById(R.id.imageView);
        dødtxt = (TextView) findViewById(R.id.textViewDød);
        brugerInput.setOnKeyListener(this);
        brugerInput.setOnClickListener(this);
//        gætOrd.setActivated(false);


        billedID = new int[R.drawable.class.getFields().length];
        billedID[0] = R.drawable.galge;
        billedID[1] = R.drawable.forkert1;
        billedID[2] = R.drawable.forkert2;
        billedID[3] = R.drawable.forkert3;
        billedID[4] = R.drawable.forkert4;
        billedID[5] = R.drawable.forkert5;
        billedID[6] = R.drawable.forkert6;


        brugerInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                parrent.brugerInput.setSelection(0, s.length());
            }
        });
        ordet.setAllCaps(true);
        ordet.setText(galgelogik.getSynligtOrd());


        new AsyncTask() {
            @Override
            protected Object doInBackground(Object... arg0) {
                try {
                    galgelogik.hentOrdFraDr();
                    return "Ordene blev korrekt hentet fra DR's server";
                } catch (Exception e) {
                    e.printStackTrace();
                    return "Ordene blev ikke hentet korrekt: "+e;
                }
            }

            @Override
            protected void onPostExecute(Object resultat) {
                System.out.println("resultat: \n" + resultat);
            }
        }.execute();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonStartNy:
                galgelogik.nulstil();
                dødtxt.setText("");
                brugtBogstaver.setText(galgelogik.getBrugteBogstaver().toString());
                ordet.setText(galgelogik.getSynligtOrd());
                hangBilled.setImageResource(billedID[galgelogik.getAntalForkerteBogstaver()]);

//                gætOrd.setActivated(true);
                break;

            case R.id.editTextBrugerInput:
                System.out.println("click");
                brugerInput.setSelection(0, brugerInput.getText().length());
                break;


            default:
                Toast.makeText(this, "SOMETHING BAD HAPPENED!!! Defaulted in: onClick", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_UP) {
            if (galgelogik.erSpilletSlut()) return false;
            galgelogik.gætBogstav(brugerInput.getText().toString());
            if (galgelogik.erSidsteBogstavKorrekt()) {
                ordet.setText(galgelogik.getSynligtOrd());
                if (galgelogik.erSpilletVundet()){
                    dødtxt.setText("TILLYKKE DU VANDT");
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
//                gætOrd.setActivated(true);

            } else {

                hangBilled.setImageResource(billedID[galgelogik.getAntalForkerteBogstaver()]);
                System.out.println(galgelogik.getAntalForkerteBogstaver());

                if (galgelogik.getAntalForkerteBogstaver() > 5) {
                    dødtxt.setText("DU DUM OG DØD");
                    ordet.setText(galgelogik.getOrdet());
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//                    gætOrd.setActivated(false);
                }
            }

//            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

            // Toast.makeText(this, brugerInput.getText(), Toast.LENGTH_SHORT).show();
        }
        brugtBogstaver.setText(galgelogik.getBrugteBogstaver().toString());

        return false;
    }


}
