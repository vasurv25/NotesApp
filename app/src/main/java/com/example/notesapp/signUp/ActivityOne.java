package com.example.notesapp.signUp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.example.notesapp.R;
import com.example.notesapp.login.LoginActivity;

public class ActivityOne extends AppCompatActivity {

    private FrameLayout mLoginFrame;
    private ProgressBar mProgressBar;
    private View mRevealView;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        mLoginFrame = findViewById(R.id.bt_login);
        mProgressBar = findViewById(R.id.pb_login);
        mRevealView = findViewById(R.id.reveal_view);

        mLoginFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateButtonWidth();
                fadeOutTextAndSetProgressDialog();
                actionNext();
            }
        });
    }

    public void animateButtonWidth() {
        ValueAnimator anim = ValueAnimator.ofInt(mLoginFrame.getMeasuredWidth(), getFinalWidth());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (Integer) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = mLoginFrame.getLayoutParams();
                layoutParams.width = value;
                mLoginFrame.requestLayout();
            }
        });

        anim.setDuration(250);
        anim.start();
    }

    private void fadeOutTextAndSetProgressDialog() {
        mLoginFrame.animate().alpha(0f).setDuration(250).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                showProgressDialog();
            }
        }).start();
    }

    private void showProgressDialog() {
        mProgressBar.getIndeterminateDrawable().setColorFilter(new PorterDuffColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN));
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void actionNext() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                revealButton();
                fadeOutProgressDialog();
                delayStartNextActivity();
            }
        }, 2000);
    }

    private void revealButton() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mLoginFrame.setElevation(0f);
        }
        mRevealView.setVisibility(View.VISIBLE);

        int x =  mRevealView.getWidth();
        int y = mRevealView.getHeight();

        int startX = (int) (getFinalWidth() / 2 + mLoginFrame.getX());
        int startY = (int) (getFinalWidth() / 2 + mLoginFrame.getY());

        float radius = Math.max(x, y) * 1.2f;

        Animator reveal = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            reveal = ViewAnimationUtils.createCircularReveal(mRevealView, startX, startY, getFinalWidth(), radius);
        }
        reveal.setDuration(350);
        reveal.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                finish();
            }
        });

        reveal.start();
    }

    private void fadeOutProgressDialog() {
        //mProgressBar.animate().alpha(0f).setDuration(200).start();
    }

    private void delayStartNextActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(ActivityOne.this, LoginActivity.class));
            }
        }, 100  );
    }

    private int getFinalWidth() {
        return (int) getResources().getDimension(R.dimen.get_width);
    }
}

