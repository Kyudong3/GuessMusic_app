package com.kyudong.guessmusic;

import com.kyudong.guessmusic.Question.QuestionItem;

import java.util.ArrayList;

/**
 * Created by Kyudong on 2018. 11. 19..
 */

public interface OnTaskCompleted {
    void onTaskCompleted(ArrayList<QuestionItem> arr);
}
