package com.example.mylaptop.tictactoe;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "MyPrefsFile";

    final static int CUSTOM_DIALOG_FOR_ONE = 1;
    final static int CUSTOM_DIALOG_FOR_TWO = 2;

    private static String player1Name = "Player 1";
    private static String player2Name = "CPU";

    private static int modePlay = 1;

    boolean isLike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.optionToolbar);
        setSupportActionBar(toolbar);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        isLike = settings.getBoolean("isLike", false);

        setIconForRateButton();
    }

    public void setIconForRateButton(){
        if(isLike){
            ImageButton imageButton = (ImageButton)findViewById(R.id.rateButton);
            imageButton.setImageDrawable(getResources().getDrawable(R.drawable.stariconlike));
        }
        else{
            ImageButton imageButton = (ImageButton)findViewById(R.id.rateButton);
            imageButton.setImageDrawable(getResources().getDrawable(R.drawable.staricon));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view){
        switch (view.getId()){
            case (R.id.rateButton):{
                isLike = !isLike;
                setIconForRateButton();
                break;
            }
            case (R.id.helpButton):{
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/Tictactoe-221958118151410/?ref=hl\""));
                startActivity(browserIntent);
                break;
            }
            case (R.id.aboutButton):{
                Intent intent = new Intent(MainActivity.this,AboutActivity.class);
                startActivity(intent);
                break;
            }
            case (R.id.btnOnePlayer):{
                showDialog(CUSTOM_DIALOG_FOR_ONE);
                break;
            }
            case (R.id.btnTwoPlayers):{
                showDialog(CUSTOM_DIALOG_FOR_TWO);
                break;
            }
        }
    }

    public Dialog onCreateDialog(int id){
        final Dialog dialog = new Dialog(this);
        dialog.setCanceledOnTouchOutside(false);
        switch (id){
            case CUSTOM_DIALOG_FOR_ONE:{
                dialog.setTitle("Player Name");
                dialog.setContentView(R.layout.dialog_for_one_layout);
                dialog.show();
                Button buttonCancel = (Button)dialog.findViewById(R.id.btnDialogOneCancel);
                buttonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText editText = (EditText)dialog.findViewById(R.id.edtTextForOnePlayer);
                        if(editText.getText().toString().length() != 0) {
                            editText.setText("");
                        }
                        TextView textView = (TextView)dialog.findViewById(R.id.warningTxtView1);
                        if(textView.getText().toString().length() != 0) {
                            textView.setText("");
                        }
                        dismissDialog(CUSTOM_DIALOG_FOR_ONE);
                    }
                });
                Button buttonPlay = (Button)dialog.findViewById(R.id.btnDialogOnePlay);
                buttonPlay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText editText = (EditText)dialog.findViewById(R.id.edtTextForOnePlayer);
                        String contentOfDialogEdittext = editText.getText().toString();
                        if(contentOfDialogEdittext.length() <= 8) {
                            if (contentOfDialogEdittext.length() != 0) {
                                setPlayer1Name(contentOfDialogEdittext);
                                editText.setText("");
                                TextView textView = (TextView)dialog.findViewById(R.id.warningTxtView1);
                                if(textView.getText().toString().length() > 0) {
                                    textView.setText("");
                                }
                            }
                            setModePlay(1);
                            Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                            startActivity(intent);
                            dismissDialog(CUSTOM_DIALOG_FOR_ONE);
                        }
                        else{
                            editText.setText("");
                            TextView textView = (TextView)dialog.findViewById(R.id.warningTxtView1);
                            textView.setText("Length Of Name Must Be Less Than Or Equal 8 !!!");
                        }
                    }
                });
                return dialog;
            }
            case CUSTOM_DIALOG_FOR_TWO: {
                dialog.setTitle("Player Name");
                dialog.setContentView(R.layout.dialog_for_two_layout);
                dialog.show();

                Button buttonCancel = (Button)dialog.findViewById(R.id.btnDialogTwoCancel);
                buttonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        EditText editText = (EditText)dialog.findViewById(R.id.edtTextForTwoPlayer1);
                        if(editText.getText().toString().length() != 0){
                            editText.setText("");
                        }
                        editText = (EditText)dialog.findViewById(R.id.edtTextForTwoPlayer2);
                        if(editText.getText().toString().length() != 0){
                            editText.setText("");
                        }
                        TextView textView = (TextView)dialog.findViewById(R.id.warningTxtView2);
                        if(textView.getText().toString().length() != 0){
                            textView.setText("");
                        }
                        dismissDialog(CUSTOM_DIALOG_FOR_TWO);
                    }
                });

                Button buttonPlay = (Button)dialog.findViewById(R.id.btnDialogTwoPlay);
                buttonPlay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText editText1 = (EditText)dialog.findViewById(R.id.edtTextForTwoPlayer1);
                        EditText editText2 = (EditText)dialog.findViewById(R.id.edtTextForTwoPlayer2);
                        String contentOfDialogEdittext1 = editText1.getText().toString();
                        String contentOfDialogEdittext2 = editText2.getText().toString();
                        if(contentOfDialogEdittext1.length() <= 8 && contentOfDialogEdittext2.length() <= 8) {
                            if (contentOfDialogEdittext1.length() != 0) {
                                setPlayer1Name(contentOfDialogEdittext1);
                                editText1.setText("");
                                TextView textview = (TextView)dialog.findViewById(R.id.warningTxtView2);
                                if(textview.getText().toString().length() > 0) {
                                    textview.setText("");
                                }
                            }
                            if (contentOfDialogEdittext2.length() != 0) {
                                setPlayer2Name(contentOfDialogEdittext2);
                                editText2.setText("");
                                TextView textView = (TextView)dialog.findViewById(R.id.warningTxtView2);
                                if(textView.getText().toString().length() > 0) {
                                    textView.setText("");
                                }
                            }
                            else{
                                setPlayer2Name("Player 2");
                            }
                            setModePlay(2);
                            Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                            startActivity(intent);
                            dismissDialog(CUSTOM_DIALOG_FOR_TWO);
                        }
                        else{
                            editText1.setText("");
                            editText2.setText("");
                            TextView textView = (TextView)dialog.findViewById(R.id.warningTxtView2);
                            textView.setText("Length Of Name Must Be Less Than Or Equal 8 !!!");
                        }
                    }
                });
                return dialog;
            }
        }
        return super.onCreateDialog(id);
    }

    public void setPlayer1Name(String content){
        player1Name = content;
    }

    public void setPlayer2Name(String content){
        player2Name = content;
    }

    public void setModePlay(int modeValue){
        modePlay = modeValue;
    }

    public static String getPlayer1Name(){
        return player1Name;
    }


    public static String getPlayer2Name(){
        return player2Name;
    }

    public static int getModePlay(){
        return  modePlay;
    }


    protected void onResume(){
        super.onResume();
        player1Name = "Player 1";
        player2Name = "CPU";
    }

    protected void onStop(){
        super.onStop();

        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("isLike", isLike);

        // Commit the edits!
        editor.commit();
    }
}
