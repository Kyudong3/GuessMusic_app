package com.kyudong.guessmusic.UserCharacter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.kyudong.guessmusic.Coordinate;
import com.kyudong.guessmusic.R;

import java.util.ArrayList;

/**
 * Created by Kyudong on 2018. 11. 17..
 */

public class CharacterCtr extends View {

    private Paint paint;
    private Bitmap bitmap;
    private Bitmap resized;
    private int diff;

    private GameWin gameWinDialog;

    private int firstLeft = 760;
    private int firstTop = 760;

    private Context context;

    private int totalCnt = 1;

    public CharacterCtr(Context context) {
        super(context);
    }

    public CharacterCtr(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.virtuoso);
        resized = bitmap.createScaledBitmap(bitmap, 100, 100, true);
        gameWinDialog = new GameWin(context, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        gameWinDialog.setCanceledOnTouchOutside(false);
        gameWinDialog.setCancelable(false);
    }

    public void setDiff(int diff) {
        this.diff = diff;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint = new Paint();
        canvas.drawBitmap(resized, firstLeft, firstTop, paint);
    }

    public void update(int diff, ArrayList<Coordinate> arr) {
        if(totalCnt==1) {
            if(diff == 1) {
                firstLeft = arr.get(1).getCoorX();
                firstTop = arr.get(1).getCoorY();
                totalCnt += 1;
            } else if(diff==2) {
                firstLeft = arr.get(2).getCoorX();
                firstTop = arr.get(2).getCoorY();
                totalCnt += 2;
            } else if(diff==3) {
                firstLeft = arr.get(3).getCoorX();
                firstTop = arr.get(3).getCoorY();
                totalCnt += 3;
            }
        } else if(totalCnt < 15) {
            if(diff == 1) {
                firstLeft = arr.get(totalCnt).getCoorX();
                firstTop = arr.get(totalCnt).getCoorY();
                totalCnt += 1;
            } else if(diff==2) {
                firstLeft = arr.get(totalCnt+1).getCoorX();
                firstTop = arr.get(totalCnt+1).getCoorY();
                totalCnt += 2;
            } else if(diff==3) {
                firstLeft = arr.get(totalCnt+2).getCoorX();
                firstTop = arr.get(totalCnt+2).getCoorY();
                totalCnt += 3;
            }
        } else if(totalCnt < 16) {
            if(diff==1) {
                firstLeft = arr.get(totalCnt).getCoorX();
                firstTop = arr.get(totalCnt).getCoorY();
                totalCnt += 1;
            } else if(diff==2) {
                firstLeft = arr.get(totalCnt+1).getCoorX();
                firstTop = arr.get(totalCnt+1).getCoorY();
                totalCnt += 2;
            } else if(diff==3) {
                gameWinDialog.show();
                Toast.makeText(getContext(), "게임 승리!!", Toast.LENGTH_SHORT).show();
            }
         } else if(totalCnt < 17) {
            if(diff==1) {
                firstLeft = arr.get(totalCnt).getCoorX();
                firstTop = arr.get(totalCnt).getCoorY();
                totalCnt += 1;
            } else if(diff==2) {
                gameWinDialog.show();
                Toast.makeText(getContext(), "게임 승리!!", Toast.LENGTH_SHORT).show();
            } else if(diff==3) {
                gameWinDialog.show();
                Toast.makeText(getContext(), "게임 승리!!", Toast.LENGTH_SHORT).show();
            }
        } else if(totalCnt < 18) {
            if(diff==1) {
                gameWinDialog.show();
                Toast.makeText(getContext(), "게임 승리!!", Toast.LENGTH_SHORT).show();
            } else if(diff==2) {
                gameWinDialog.show();
                Toast.makeText(getContext(), "게임 승리!!", Toast.LENGTH_SHORT).show();
            } else if(diff==3) {
                gameWinDialog.show();
                Toast.makeText(getContext(), "게임 승리!!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class GameWin extends Dialog {

        private Button gameWinBtn;

        public GameWin(Context context) {
            super(context);

            init();
        }

        public GameWin(Context context, int themeResId) {
            super(context, themeResId);

            init();
        }

        private void init() {
            setContentView(R.layout.game_win_dialog);

            gameWinBtn = (Button) findViewById(R.id.gameWinBtn);
            gameWinBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gameWinDialog.dismiss();
                    ((Activity)context).finish();
                }
            });
        }
    }

}
