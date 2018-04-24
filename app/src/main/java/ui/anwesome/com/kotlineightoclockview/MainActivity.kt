package ui.anwesome.com.kotlineightoclockview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ui.anwesome.com.eightoclockview.EightOClockView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EightOClockView.create(this)
    }
}
