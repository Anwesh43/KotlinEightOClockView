package ui.anwesome.com.eightoclockview

/**
 * Created by anweshmishra on 24/04/18.
 */

import android.view.*
import android.graphics.*
import android.content.Context

class EightOClockView (ctx : Context) : View(ctx) {

    private val paint : Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val renderer : Renderer = Renderer(this)

    override fun onDraw(canvas : Canvas) {
        renderer.render(canvas, paint)
    }

    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                renderer.handleTap()
            }
        }
        return true
    }

    data class State (var scale : Float = 0f, var dir : Float = 0f, var prevScale : Float = 0f) {

        fun update(stopcb : (Float) -> Unit) {
            scale += 0.1f * dir
            if (Math.abs(scale - prevScale) > 1) {
                scale = prevScale + dir
                dir = 0f
                prevScale = scale
                stopcb(prevScale)
            }
        }

        fun startUpdating(startcb : () -> Unit) {
            if (dir == 0f) {
                dir = 1 - 2 * prevScale
                startcb()
            }
        }
    }

    data class Animator (var view : View, var animated : Boolean = false) {

        fun animate (updatecb : () -> Unit) {
            if (animated) {
                updatecb()
                try {
                    Thread.sleep(50)
                    view.invalidate()
                } catch (ex : Exception) {

                }
            }
        }

        fun start() {
            if (!animated) {
                animated = true
                view.postInvalidate()
            }
        }

        fun stop() {
            if (animated) {
                animated = false
            }
        }
    }

    data class EightOClock (var i : Int, val state : State = State()) {

        fun draw(canvas : Canvas, paint : Paint) {
            val w : Float = canvas.width.toFloat()
            val h : Float = canvas.height.toFloat()
            paint.color = Color.parseColor("#673AB7")
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = Math.min(w, h)/50
            canvas.save()
            canvas.translate(w/2, h/2)
            canvas.drawCircle(0f, 0f, 3 * w/8, paint)
            canvas.drawRotatedLine(w/3, 90f, paint)
            canvas.drawRotatedLine(w/5, -30f, paint)
            canvas.restore()
        }

        fun update(stopcb : (Float) -> Unit) {
            state.update(stopcb)
        }

        fun startUpdating(startcb : () -> Unit) {
            state.startUpdating(startcb)
        }
    }

    data class Renderer (var view : EightOClockView) {

        private val eightOClock : EightOClock = EightOClock(0)

        private val animator : Animator = Animator(view)

        fun render(canvas : Canvas, paint : Paint) {
            canvas.drawColor(Color.parseColor("#212121"))
            eightOClock.draw(canvas, paint)
            animator.animate {
                eightOClock.update {
                    animator.stop()
                }
            }
        }

        fun handleTap() {
            eightOClock.startUpdating {
                animator.start()
            }
        }
    }
}

fun Canvas.drawRotatedLine (w : Float, deg : Float, paint : Paint) {
    paint.strokeCap = Paint.Cap.ROUND
    save()
    rotate(deg)
    drawLine(0f, 0f, -w, 0f, paint)
    restore()
}