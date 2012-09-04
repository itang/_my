package de.vogella.android.temperature

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.widget.EditText
import android.view.View
import android.widget.Toast
import android.widget.RadioButton

class MainActivity extends Activity {

  private var text: EditText = _
  private def findView[T](id: Int): T = findViewById(id).asInstanceOf[T]

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    text = findView[EditText](R.id.editText1)
  }

  // This method is called at button click because we assigned the name to the 
  // "onClick property" of the button
  def onClick(view: View): Unit = view.getId match {
    case R.id.button1 ⇒ {
      val celsiusButton = findView[RadioButton](R.id.radioButton1)
      val fahrenheitButton = findView[RadioButton](R.id.radioButton2)
      if (text.getText().length == 0) {
        Toast.makeText(this, "please enter a valid number", Toast.LENGTH_LONG).show()
        return
      }

      val inputValue = text.getText.toString.toFloat
      if (celsiusButton.isChecked) {
        text.setText(convertFahrenheitToCelsius(inputValue).toString)
        celsiusButton.setChecked(false)
        fahrenheitButton.setChecked(true)
      } else {
        text.setText(convertCelsiusToFahrenheit(inputValue).toString)
        celsiusButton.setChecked(true)
        fahrenheitButton.setChecked(false)
      }
    }
  }

  override def onCreateOptionsMenu(menu: Menu): Boolean = {
    getMenuInflater().inflate(R.menu.activity_main, menu)
    true
  }

  private def convertFahrenheitToCelsius(fahrenheit: Float) = (fahrenheit - 32) * 5 / 9

  private def convertCelsiusToFahrenheit(celsius: Float) = ((celsius * 9) / 5) + 32
}
