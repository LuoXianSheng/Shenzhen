package com.xiaofan.shenzhen;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private FloatingActionButton btnFloat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        btnFloat = (FloatingActionButton) findViewById(R.id.btnFloat);
        setSupportActionBar(mToolbar);

        Log.e("aa", btnFloat.getTranslationY() + "--" + btnFloat.getRight() + "---" + btnFloat.getTop());
        ListView mListView = (ListView) findViewById(R.id.listview);
        String[] data = new String[21];
        for (int i = 0; i < data.length; i++) {
                data[i] = "test" + i;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        View headView = new View(this);
        headView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, 80));
        mListView.addHeaderView(headView);
        mListView.setAdapter(adapter);

        minTouchSlop = ViewConfiguration.get(this).getScaledTouchSlop();

        mListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        firstY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        currY = event.getY();
                        if (currY - firstY > minTouchSlop) {
                            Log.e("action", "下");
//                            toolBarAnim(1);
//                            floatBtnAnim(1);
                            showViews();
                        } else if (firstY - currY > minTouchSlop) {
                            Log.e("action", "上");
//                            toolBarAnim(0);
//                            floatBtnAnim(0);
                            hideViews();
                        }
                        break;
                    case MotionEvent.ACTION_UP:

                        break;
                }
                return false;
            }
        });
    }

    private void hideViews() {
        mToolbar.animate().translationY(-mToolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2));

        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) btnFloat.getLayoutParams();
        int fabBottomMargin = lp.bottomMargin;
        btnFloat.animate().translationY(btnFloat.getHeight() + fabBottomMargin).setInterpolator(new AccelerateInterpolator(2)).start();
    }

    private void showViews() {
        mToolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
        btnFloat.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
    }


    private void toolBarAnim(int flag) {
        if (flag == 1) {
            toolBarAnimator = ObjectAnimator.ofFloat(mToolbar, "translationY", mToolbar.getTranslationY(), 0);
        } else {
            toolBarAnimator = ObjectAnimator.ofFloat(mToolbar, "translationY", mToolbar.getTranslationY(), -mToolbar.getHeight());
        }
        toolBarAnimator.start();
    }

    private void floatBtnAnim(int flag) {
        if (flag == 0) {
            floatBtnAnimator = ObjectAnimator.ofFloat(btnFloat, "translationY", btnFloat.getTranslationY(), 100);
        } else {
            floatBtnAnimator = ObjectAnimator.ofFloat(btnFloat, "translationY", btnFloat.getTranslationY(), btnFloat.getHeight());
        }
        floatBtnAnimator.start();
    }

    private float firstY;
    private float currY;
    private float minTouchSlop;
    private ObjectAnimator toolBarAnimator;
    private ObjectAnimator floatBtnAnimator;
}
