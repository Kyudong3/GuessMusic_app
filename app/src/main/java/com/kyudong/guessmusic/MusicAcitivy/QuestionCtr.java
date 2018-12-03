package com.kyudong.guessmusic.MusicAcitivy;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.kyudong.guessmusic.UpdateVieww;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyudong on 2018. 11. 17..
 */

public class QuestionCtr {

    private QuestionModel model;
    private Context context;
    private int difficulty;

    public QuestionCtr(Context context, UpdateVieww vda) {
        model = new QuestionModel(context, vda);
        this.context = context;
    }

    public void SetQuestion() {
        model.getQuestion(context);
    }

    public void setDiff(int diff) {
        model.setDifficulty(diff);
    }

}
