package com.example.android.lifecycles.step3_solution

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.os.CountDownTimer
import android.util.Log

/**
 * Created by JiangYiDong on 2018/11/15.
 */
class LiveDataCountDownVM : ViewModel() {

    val mElapsedTime : MutableLiveData<Long> = MutableLiveData()
    var mIsFinish : MutableLiveData<Boolean> = MutableLiveData()
        private set

    init {
        mIsFinish.value = false
        val countDownTime : Long = 80 * 1000
        val countDownTimer = object : CountDownTimer(countDownTime, 1000) {
            override fun onFinish() {
                mElapsedTime.value = 0
                mIsFinish.value = true
            }

            override fun onTick(millisUntilFinished: Long) {
                mElapsedTime.value = millisUntilFinished
            }
        }
        countDownTimer.start()
        mElapsedTime.value = countDownTime
        Log.d("LiveDataCountDownVM", "init")
    }

}