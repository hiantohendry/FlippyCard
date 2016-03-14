package com.hh.flippycard;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static int RESULT_LOAD_IMG1 = 1;
    private static int RESULT_LOAD_IMG2 = 2;

    SharedPreferences sharedpreferences;
    ImageView iv1,iv2;
    FloatingActionButton fab;
    Integer[] list_card = new Integer[5];
    CardAdapter.callbackEvent listener;
    int img1=0,img2=0;
    int counter=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Choose Your Card !");


        iv1= (ImageView)findViewById(R.id.imageView1);
        iv2= (ImageView)findViewById(R.id.imageView2);

        iv1.setOnClickListener(this);
        iv2.setOnClickListener(this);

        list_card[0] = new Integer(R.drawable.front);
        list_card[1] = new Integer(R.drawable.back);
        list_card[2] = new Integer(R.drawable.two);
        list_card[3] = new Integer(R.drawable.as);
        list_card[4] = new Integer(R.drawable.jack);


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(counter==2) {
                    Intent a = new Intent(MainActivity.this, FlipActivity.class);
                    a.putExtra("img1", img1);
                    a.putExtra("img2", img2);
                    startActivity(a);
                }
                else {
                    Log.e("sasdaa","asdada "+counter);
                    Snackbar.make(view, "Please Complete Your Cards", Snackbar.LENGTH_LONG)
                           .show();
                }
            }
        });

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder() .build();
        mAdView.loadAd(adRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void openGallery(final int ResultCode){
        // custom dialog

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set title
        if(ResultCode==1)
            alertDialogBuilder.setTitle("Select your front card");
        else if (ResultCode == 2)
            alertDialogBuilder.setTitle("Select your back card");


        View dialog = getLayoutInflater().inflate(R.layout.dialog_cards, null);


        // set dialog message
        alertDialogBuilder
                .setView(dialog);

        // create alert dialog
       final AlertDialog alertDialog = alertDialogBuilder.create();

        listener = new CardAdapter.callbackEvent() {
            @Override
            public void onItemSelected(int img) {
                if(ResultCode ==1 ) {
                    img1=img;
                    iv1.setImageResource(img);
                    counter++;
                }
                else if(ResultCode==2){
                    img2 = img;
                    iv2.setImageResource(img);
                    counter++;
                }

                if(counter>2)counter=2;
                alertDialog.dismiss();
            }
        }; // set the custom dialog components - text, image and button
        ListView listcard = (ListView)dialog.findViewById(R.id.list_cards);
        CardAdapter adapter = new CardAdapter(getApplicationContext(),R.layout.adapter_cards,list_card);


        adapter.setListener(listener);

        listcard.setAdapter(adapter);


        // show it
        alertDialog.show();
    }
    @Override
    public void onClick(View v) {
        if(v == iv1)
        {
            openGallery(RESULT_LOAD_IMG1);
        }
        else if(v == iv2)
        {
            openGallery(RESULT_LOAD_IMG2);
        }
    }
}
