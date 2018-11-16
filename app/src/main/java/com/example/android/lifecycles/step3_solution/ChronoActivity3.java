/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.lifecycles.step3_solution;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.android.codelabs.lifecycle.R;
import com.example.android.lifecycles.step3_solution.countdownview.CountdownView;

public class ChronoActivity3 extends AppCompatActivity {

    private LiveDataTimerViewModel mLiveDataTimerViewModel;

    private TextView mTimer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.chrono_activity_3);


        subscribe();
        subscribeTimer();
    }

    private void subscribe() {
        mLiveDataTimerViewModel = ViewModelProviders.of(this).get(LiveDataTimerViewModel.class);

        final Observer<Long> elapsedTimeObserver = new Observer<Long>() {
            @Override
            public void onChanged(@Nullable final Long aLong) {
                String newText = ChronoActivity3.this.getResources().getString(
                        R.string.seconds, aLong);
                ((TextView) findViewById(R.id.timer_textview)).setText(newText);
                Log.d("ChronoActivity3", "Updating timer " + newText);
            }
        };

        mLiveDataTimerViewModel.getElapsedTime().observe(this, elapsedTimeObserver);
    }

    private void subscribeTimer() {
        mTimer2 = findViewById(R.id.timer_textview2);
        final CountdownView countdownView = findViewById(R.id.countdownViewTest2);
        LiveDataCountDownVM liveDataCountDownVM = ViewModelProviders.of(this).get(LiveDataCountDownVM.class);
        final Observer<Long> stringObserver = new Observer<Long>() {
            @Override
            public void onChanged(@Nullable Long millisTime) {
                mTimer2.setText(TimeTools.getCountTimeByLong(millisTime));
                countdownView.updateShow(millisTime);
                Log.d("ChronoActivity3", "Updating timer2 " + mTimer2.getText());
            }
        };
        final Observer<Boolean> booleanObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean) {
                    mTimer2.setVisibility(View.GONE);
                }
            }
        };

        if (!liveDataCountDownVM.getMIsFinish().getValue()) {
            mTimer2.setVisibility(View.VISIBLE);
            liveDataCountDownVM.getMElapsedTime().observe(this, stringObserver);
            liveDataCountDownVM.getMIsFinish().observe(this, booleanObserver);
        } else {
            mTimer2.setVisibility(View.GONE);
        }
    }
}
