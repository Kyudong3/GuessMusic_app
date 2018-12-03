package com.kyudong.guessmusic.MusicAcitivy;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kyudong.guessmusic.Coordinate;
import com.kyudong.guessmusic.OnTaskCompleted;
import com.kyudong.guessmusic.Question.QuestionItem;
import com.kyudong.guessmusic.R;
import com.kyudong.guessmusic.UpdateVieww;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Kyudong on 2018. 11. 17..
 */

public class QuestionModel {

    private int difficulty;
    private Context context;

    public static String url = "http://172.30.1.7:8080/question/name/";

    private MediaPlayer mediaPlayer;

    private ArrayList<QuestionItem> arrayList = new ArrayList<>();
    private ArrayList<QuestionItem> arrayList2 = new ArrayList<>();
    private ArrayList<QuestionItem> arrayList3 = new ArrayList<>();
    private Handler handler = new Handler();

    private QShownDialog dia;
    private int cnt1 = 0;
    private int cnt2 = 0;
    private int cnt3 = 0;

    private UpdateVieww upd;

    private ArrayList<Coordinate> coorList = new ArrayList<>();

    public QuestionModel(Context context, UpdateVieww upd) {
        this.context = context;
        this.upd = upd;

        setListCoordinate();
    }

    public void setListCoordinate() {
        for(int i = 0; i < 5; i++) {
            Coordinate coorXY = new Coordinate();
            coorXY.setCoorX(760);
            coorXY.setCoorY(760 - 180*i);
            coorList.add(coorXY);
        }

        for(int i = 0; i < 2; i++) {
            Coordinate coorXY = new Coordinate();
            coorXY.setCoorX(760 - 180*(i+1));
            coorXY.setCoorY(40);
            coorList.add(coorXY);
        }

        for(int i = 0; i < 4; i++) {
            Coordinate coorXY = new Coordinate();
            coorXY.setCoorX(400);
            coorXY.setCoorY(40 + 180*(i+1));
            coorList.add(coorXY);
        }

        for(int i = 0; i < 2; i++) {
            Coordinate coorXY = new Coordinate();
            coorXY.setCoorX(400 - 180*(i+1));
            coorXY.setCoorY(760);
            coorList.add(coorXY);
        }

        for(int i = 0; i < 4; i++) {
            Coordinate coorXY = new Coordinate();
            coorXY.setCoorX(40);
            coorXY.setCoorY(760 - 180*(i+1));
            coorList.add(coorXY);
        }
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    // 노래 모드에서 노래를 재생하는 함수 //
    public void PlayMusic() {
        Resources res = context.getResources();
        if(difficulty==1) {
            int musicId = res.getIdentifier(arrayList.get(cnt1).tag, "raw", context.getPackageName());

            mediaPlayer = MediaPlayer.create(context, musicId);
            mediaPlayer.start();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    showDialog();
                }
            }, 2000);

        } else if(difficulty==2) {
            int musicId = res.getIdentifier(arrayList2.get(cnt2).tag, "raw", context.getPackageName());
            mediaPlayer = MediaPlayer.create(context, musicId);
            mediaPlayer.start();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    showDialog();
                }
            }, 1500);

        } else if(difficulty==3) {
            int musicId = res.getIdentifier(arrayList3.get(cnt3).tag, "raw", context.getPackageName());
            mediaPlayer = MediaPlayer.create(context, musicId);
            mediaPlayer.start();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    showDialog();
                }
            }, 1500);

        }
    }

    public void showDialog() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //dia = new QShownDialog(context, android.R.style.Theme_Translucent_NoTitleBar);
                dia = new QShownDialog(context);
                dia.setCanceledOnTouchOutside(false);
                dia.setCancelable(false);
                dia.show();
            }
        }, 1000);
    }

    // Database 에서 문제를 가져오는 함수 //
    public void getQuestion(Context context) {
        OnTaskCompleted onTaskCompleted = new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(ArrayList<QuestionItem> arr) {
                if(difficulty==1) {
                    arrayList = arr;
                    Log.e("list", "diff is 1 & arr is inserted into arrayList1");
                } else if(difficulty==2) {
                    arrayList2 = arr;
                    Log.e("list", "diff is 2 & arr is inserted into arrayList2");
                } else if(difficulty==3) {
                    arrayList3 = arr;
                    Log.e("list", "diff is 3 & arr is inserted into arrayList3");
                }
                PlayMusic();
            }
        };

        if(difficulty==1) {
            if(arrayList.isEmpty()) {
                AAsyncTask asyncTask = new AAsyncTask(onTaskCompleted);
                asyncTask.execute();
                Log.e("post", "First Arraylist1 initialize Http");
            } else {

                PlayMusic();
            }
        } else if(difficulty==2) {
            if(arrayList2.isEmpty()) {
                AAsyncTask asyncTask = new AAsyncTask(onTaskCompleted);
                asyncTask.execute();
                Log.e("post", "First Arraylist2 initialize Http");
            } else {
                PlayMusic();
            }
        } else if(difficulty==3) {
            if(arrayList3.isEmpty()) {
                AAsyncTask asyncTask = new AAsyncTask(onTaskCompleted);
                asyncTask.execute();
                Log.e("post", "First Arraylist3 initialize Http");
            } else {
                PlayMusic();
            }
        }
    }

    // 문제가 맞았는지 체크 하는 함수 //
    public void isAnswer(int tempIndex, int choice) {
        if(difficulty==1) {
            if(arrayList.get(tempIndex).answer == choice) {
                Toast.makeText(context.getApplicationContext(), "정답입니다!!", Toast.LENGTH_SHORT).show();
                upd.updateView(1, coorList);
            } else {
                Toast.makeText(context.getApplicationContext(), "틀렸습니다!!", Toast.LENGTH_SHORT).show();
            }
        } else if(difficulty==2) {
            if(arrayList2.get(tempIndex).answer == choice) {
                Toast.makeText(context.getApplicationContext(), "정답입니다!!", Toast.LENGTH_SHORT).show();
                upd.updateView(2, coorList);
            } else {
                Toast.makeText(context.getApplicationContext(), "틀렸습니다!!", Toast.LENGTH_SHORT).show();
            }
        } else if(difficulty==3) {
            if(arrayList3.get(tempIndex).answer == choice) {
                Toast.makeText(context.getApplicationContext(), "정답입니다!!", Toast.LENGTH_SHORT).show();
                upd.updateView(3, coorList);
            } else {
                Toast.makeText(context.getApplicationContext(), "틀렸습니다!!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class AAsyncTask extends AsyncTask<String, String, String> {

        OkHttpClient client = new OkHttpClient();
        ArrayList<QuestionItem> arr = new ArrayList<>();

        private OnTaskCompleted onTaskCompleted;

        public AAsyncTask(OnTaskCompleted onTaskCompleted) {
            this.onTaskCompleted = onTaskCompleted;
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                Request request = new Request.Builder()
                        .header("Content-Type", "application/json")
                        .url(url+difficulty)
                        .build();

                Response response = client.newCall(request).execute();
                if (!response.isSuccessful()) {
                    Log.e("Http", "HttpConnection bad!!");
                    return null;
                }

                Log.e("http", "httpConnection success!!");

                String aa = response.body().string();

                return aa;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONArray array = new JSONArray(s);
                if(array.length()==0) {
                    Log.e("CALLED", "no data  : " + array.length());
                } else {
                    for(int i = 0; i < array.length(); i++) {
                        QuestionItem item = new QuestionItem();

                        JSONObject child = array.getJSONObject(i);

                        item.tag = child.getString("tag");
                        item.option1 = child.getString("option1");
                        item.option2 = child.getString("option2");
                        item.option3 = child.getString("option3");
                        item.answer = child.getInt("answer");

                        arr.add(item);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            onTaskCompleted.onTaskCompleted(arr);
        }
    }

    private class QShownDialog extends Dialog {

        private Button choice1, choice2, choice3;
        private TextView quesTxv;
        // 임시 index 저장 변수 //
        private int tempIndex;

        public QShownDialog(Context context) {
            super(context);
            init();
        }
        public QShownDialog(Context context, int themeResId) {
            super(context, themeResId);
            init();
        }

        private void init() {
            setContentView(R.layout.qshown_dialog_activity);

            choice1 = (Button) findViewById(R.id.choice1);
            choice2 = (Button) findViewById(R.id.choice2);
            choice3 = (Button) findViewById(R.id.choice3);
            quesTxv = (TextView) findViewById(R.id.quesTxv);

            quesTxv.setText("문제를 맞춰보세요!");

            tempIndex = 0;

            if(difficulty==1) {
                choice1.setText("1 . " + arrayList.get(cnt1).option1);
                choice2.setText("2 . " + arrayList.get(cnt1).option2);
                choice3.setText("3 . " + arrayList.get(cnt1).option3);

                if (cnt1 <= 10) {
                    tempIndex = cnt1;
                    cnt1 += 1;
                }

            } else if(difficulty==2) {
                choice1.setText("1 . " + arrayList2.get(cnt2).option1);
                choice2.setText("2 . " + arrayList2.get(cnt2).option2);
                choice3.setText("3 . " + arrayList2.get(cnt2).option3);

                if (cnt2 <= 10) {
                    tempIndex = cnt2;
                    cnt2 += 1;
                }

            } else if(difficulty==3) {
                choice1.setText("1 . " + arrayList3.get(cnt3).option1);
                choice2.setText("2 . " + arrayList3.get(cnt3).option2);
                choice3.setText("3 . " + arrayList3.get(cnt3).option3);

                if (cnt3 <= 10) {
                    tempIndex = cnt3;
                    cnt3 += 1;
                }
            }

            choice1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isAnswer(tempIndex, 1);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dia.dismiss();
                        }
                    }, 1500);
                }
            });

            choice2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(context.getApplicationContext(), "2번이 눌렸습니다!", Toast.LENGTH_LONG).show();
                    isAnswer(tempIndex, 2);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dia.dismiss();
                        }
                    }, 1500);
                }
            });

            choice3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(context.getApplicationContext(), "3번이 눌렸습니다!", Toast.LENGTH_LONG).show();
                    isAnswer(tempIndex, 3);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dia.dismiss();
                        }
                    }, 1500);
                }
            });

        }
    }
}
