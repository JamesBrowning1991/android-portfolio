package com.example.james.portfolio;

import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity implements SideMenuFragment.SideMenuListener{

    ViewGroup activityMain;
    ViewGroup mainFragment;
    View sideMenuFragment;
    View sideMenuTabButton;
    int sideMenuWidth;
    int sideMenuTabWidth;
    int sideMenuTabTopMargin;
    boolean sideMenuIsDisplayed = true;
    RelativeLayout.LayoutParams sideMenuTabPositionRules;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityMain = (ViewGroup)findViewById(R.id.activity_main);
        mainFragment = (ViewGroup)findViewById(R.id.fragment2);
        sideMenuFragment = findViewById(R.id.fragment);
        sideMenuTabButton = findViewById(R.id.side_menu_showhide_button);

        // This runnable bit gets widths etc once sidemenu is drawn
        sideMenuFragment.post(new Runnable()
        {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            public void run()
            {
                sideMenuWidth = sideMenuFragment.getWidth();
                sideMenuTabWidth = sideMenuTabButton.getWidth();
                sideMenuTabTopMargin = sideMenuTabButton.getTop();
                moveSideMenu(false);
            }
        });

        mainFragment.setOnClickListener(
                new RelativeLayout.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onClick(View v) {
                        moveSideMenu(false);
                    }
                }
        );

        sideMenuTabButton.setOnClickListener(
                new RelativeLayout.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onClick(View v) {
                        moveSideMenu(true);
                    }
                }
        );

        sideMenuFragment.setOnClickListener(
                new RelativeLayout.OnClickListener() {
                    @Override
                    public void onClick(View v){ }
                }
        );
    }

    @Override
    public void changeMainTxt(String headerText, String contentText) {
        MainFragment mainFragment = (MainFragment)getSupportFragmentManager().findFragmentById(R.id.fragment2);
        mainFragment.changeText(headerText, contentText);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void moveSideMenu(boolean show) {
        setTransitionStuff();

        RelativeLayout.LayoutParams sideMenuPositionRules = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        sideMenuTabPositionRules = new RelativeLayout.LayoutParams(
                sideMenuTabWidth, sideMenuTabWidth);
        sideMenuTabPositionRules.topMargin = sideMenuTabTopMargin;

        if (show) {
            sideMenuPositionRules.leftMargin = 0;
            sideMenuTabPositionRules.leftMargin = 0 - sideMenuTabWidth;
            sideMenuTabButton.setLayoutParams(sideMenuTabPositionRules);
            sideMenuIsDisplayed = true;
        } else {
            sideMenuPositionRules.leftMargin = 0 - sideMenuWidth;
            sideMenuTabPositionRules.leftMargin = 0;
            new Thread(waitThenUpdateTabLocation).start();
            sideMenuIsDisplayed = false;
        }

        sideMenuFragment.setLayoutParams(sideMenuPositionRules);
    }

    Handler handler = new Handler(){
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void handleMessage(Message msg) {
            setTransitionStuff();
            sideMenuTabButton.setLayoutParams(sideMenuTabPositionRules);
        }
    };

    public Runnable waitThenUpdateTabLocation = new Runnable() {
        @Override
        public void run() {
            synchronized (this){
                try{
                    wait(1000);
                }catch(Exception e){}
            }
            handler.sendEmptyMessage(0);
        }
    };


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void setTransitionStuff() {
        Transition changeBounds = new ChangeBounds();
        changeBounds.setDuration(500);
        changeBounds.setInterpolator(new AccelerateDecelerateInterpolator()); // I think this is set by default
        TransitionManager.beginDelayedTransition(activityMain, changeBounds);
    }

}
