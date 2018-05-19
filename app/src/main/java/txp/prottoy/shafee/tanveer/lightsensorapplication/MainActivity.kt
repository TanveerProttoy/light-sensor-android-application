package txp.prottoy.shafee.tanveer.lightsensorapplication

import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private lateinit var lightSensor: Sensor
    private var maxValue: Float by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        if(lightSensor == null) {
            Toast.makeText(applicationContext, "No light sensor detected", Toast.LENGTH_LONG).show()
        }
        maxValue = lightSensor.maximumRange
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(sensorEvent: SensorEvent) {
        val value: Float = sensorEvent.values[0]
        supportActionBar?.title = ("Luminosity: $value lux")
        val backgroundValue: Int = (255f * value / maxValue).toInt()
        main_root.setBackgroundColor(Color.rgb(backgroundValue, backgroundValue, backgroundValue))
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}
