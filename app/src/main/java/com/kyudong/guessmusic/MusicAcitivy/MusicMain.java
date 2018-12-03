package com.kyudong.guessmusic.MusicAcitivy;

import android.app.Dialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.kyudong.guessmusic.Coordinate;
import com.kyudong.guessmusic.Question.QuestionItem;
import com.kyudong.guessmusic.R;
import com.kyudong.guessmusic.UpdateVieww;
import com.kyudong.guessmusic.UserCharacter.CharacterCtr;

import java.util.ArrayList;

import okhttp3.OkHttpClient;

public class MusicMain extends AppCompatActivity implements View.OnClickListener, UpdateVieww{

    private StartDialog dia;
    private GameEnd endDialog;
    private RelativeLayout ll;
    private CharacterCtr cC;
    private Button diff1, diff2, diff3;
    private QuestionCtr questionCtr;

    private MediaPlayer mediaPlayer;

    private OkHttpClient client = new OkHttpClient();

    private ArrayList<QuestionItem> arrayList = new ArrayList<>();

    //private int initial1, initial2, initial3;

    private int chance = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_main);

//        initial1 = 0;
//        initial2 = 0;
//        initial3 = 0;

        dia = new StartDialog(MusicMain.this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dia.setCanceledOnTouchOutside(false);
        dia.show();

        findId();
        setClickListener();

        questionCtr = new QuestionCtr(MusicMain.this, this);

        cC.bringToFront();

        endDialog = new GameEnd(MusicMain.this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        endDialog.setCanceledOnTouchOutside(false);
        endDialog.setCancelable(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.diff1Btn:
                if(chance < 9) {
                    questionCtr.setDiff(1);
                    questionCtr.SetQuestion();
                    chance += 1;
//                    if (initial1 == 0) {
//                        Log.e("initial", "this is initial1  " + initial1);
//                        questionCtr.setDiff(1);
//                        questionCtr.SetQuestion();
//                        initial1 += 1;
//                        chance += 1;
//                    } else {
//                        questionCtr.setDiff(1);
//                        questionCtr.SetQuestion();
//                        chance += 1;
//                    }
                } else {
                    endDialog.show();
                }
                break;
            case R.id.diff2Btn:
                if(chance < 9) {
                    questionCtr.setDiff(2);
                    questionCtr.SetQuestion();
                    chance += 1;
//                    if (initial2 == 0) {
//                        Log.e("initial", "this is initial2  " + initial2);
//                        questionCtr.setDiff(2);
//                        questionCtr.SetQuestion();
//                        initial2 += 1;
//                        chance += 1;
//                    } else {
//                        //Log.e("initial", "this is no initial2  " + initial2);
//                        questionCtr.setDiff(2);
//                        questionCtr.SetQuestion();
//                        chance += 1;
//                    }
                } else {
                    endDialog.show();
                }
                break;
            case R.id.diff3Btn:
                if(chance < 9) {
                    questionCtr.setDiff(3);
                    questionCtr.SetQuestion();
                    chance += 1;
//                    if (initial3 == 0) {
//                        Log.e("initial", "this is initial3  " + initial3);
//                        questionCtr.setDiff(3);
//                        questionCtr.SetQuestion();
//                        initial3 += 1;
//                        chance += 1;
//                    } else {
//                        //Log.e("initial", "this is initial3  " + initial3);
//                        questionCtr.setDiff(3);
//                        questionCtr.SetQuestion();
//                        chance += 1;
//                    }
                } else {
                    endDialog.show();
                }
                break;
        }
    }

    // ** ID 찾는 함수 ** //
    private void findId() {
        ll = (RelativeLayout) findViewById(R.id.squareLayout);
        cC = (CharacterCtr) findViewById(R.id.CharacterView);
        diff1 = (Button) findViewById(R.id.diff1Btn);
        diff2 = (Button) findViewById(R.id.diff2Btn);
        diff3 = (Button) findViewById(R.id.diff3Btn);
    }

    // ** Click Listener 함수 ** //
    private void setClickListener() {
        diff1.setOnClickListener(this);
        diff2.setOnClickListener(this);
        diff3.setOnClickListener(this);
    }

    @Override
    public void updateView(int diff, ArrayList<Coordinate> arr) {
        if(diff == 1) {
            cC.update(1, arr);
        } else if(diff==2) {
            cC.update(2, arr);
        } else if(diff==3) {
            cC.update(3, arr);
        }

        cC.invalidate();
    }
    
    private class StartDialog extends Dialog {

        private Button startDialogBtn;

        public StartDialog(Context context) {
            super(context);

            init();
        }

        public StartDialog(Context context, int themeResId) {
            super(context, themeResId);

            init();
        }

        private void init() {
            setContentView(R.layout.start_dialog_activity);

            startDialogBtn = (Button) findViewById(R.id.dialogStartBtn);
            startDialogBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dia.dismiss();
                }
            });
        }
    }

    private class GameEnd extends Dialog {

        private Button gameEndBtn;

        public GameEnd(Context context) {
            super(context);

            init();
        }

        public GameEnd(Context context, int themeResId) {
            super(context, themeResId);

            init();
        }

        private void init() {
            setContentView(R.layout.game_end_dialog);

            gameEndBtn = (Button) findViewById(R.id.gameEndBtn);
            gameEndBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    endDialog.dismiss();
                    finish();
                }
            });
        }
    }
}
