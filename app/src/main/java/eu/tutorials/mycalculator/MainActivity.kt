package eu.tutorials.mycalculator

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.ArithmeticException


class MainActivity : AppCompatActivity() {
    private var input : TextView? = null
    var lastNumeric : Boolean = false
    var lastDot : Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        input = findViewById(R.id.input)

    }

    fun onDigit(view:View){
        input?.append((view as Button).text)
        lastNumeric = true



    }
    fun onClear(view : View){
        input?.text =""
        lastNumeric = false
        lastDot = false
    }
    fun onDecimalPoint(view : View){
        if(lastNumeric && !lastDot){
            input?.append(".")
            lastNumeric = false
            lastDot = true
        }

    }
    fun onOperator(view: View) {
        input?.text?.let {

            if(lastNumeric && !isOperatorAdded(it.toString())){
                input?.append((view as Button).text)
                lastNumeric = false
                lastDot = false

            }
        }
    }
    fun onEqual(view : View){
        if(lastNumeric){
            var tvValue = input?.text.toString()
            var prefix =""
            try {
                if(tvValue.startsWith("-")){
                    prefix ="-"

                    tvValue = tvValue.substring(1)
                }
                when {
                    tvValue.contains("-") -> {
                        val splitValue = tvValue.split("-")
                        var one = splitValue[0]
                        var two = splitValue[1]
                        if (prefix.isNotEmpty()) {
                            one = prefix + one
                        }

                        input?.text =
                            removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                    }
                    tvValue.contains("/") -> {
                        val splitValue = tvValue.split("/")
                        var one = splitValue[0]
                        var two = splitValue[1]

                        if (prefix.isNotEmpty()) {
                            one = prefix + one
                        }
                        input?.text =
                            removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                    }
                    tvValue.contains("+") -> {
                        val splitValue = tvValue.split("+")
                        var one = splitValue[0]
                        var two = splitValue[1]

                        if (prefix.isNotEmpty()) {
                            one = prefix + one
                        }
                        input?.text =
                            removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                    }
                    tvValue.contains("*") -> {
                        val splitValue = tvValue.split("*")
                        var one = splitValue[0]
                        var two = splitValue[1]

                        if (prefix.isNotEmpty()) {
                            one = prefix + one
                        }
                        input?.text =
                            removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                    }
                }
            }catch (e: ArithmeticException){
                e.printStackTrace()
            }
        }
    }
    private fun removeZeroAfterDot(result: String) : String{
        var value = result
        if(result.contains("0"))
            value = result.substring(0, result.length - 2)
        return value
    }
    private fun isOperatorAdded(value: String) : Boolean{
        return if(value.startsWith("-")){
            false
        }else{
            value.contains("/") || value.contains("*")
                    || value.contains("+")||value.contains("-")
        }
    }
}