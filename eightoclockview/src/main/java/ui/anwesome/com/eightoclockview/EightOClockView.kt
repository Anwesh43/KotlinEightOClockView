package ui.anwesome.com.eightoclockview

/**
 * Created by anweshmishra on 24/04/18.
 */

import android.view.*
import android.graphics.*
import android.content.Context

class EightOClockView (ctx : Context) : View(ctx) {

    private val paint : Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas : Canvas) {

    }

    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }
}