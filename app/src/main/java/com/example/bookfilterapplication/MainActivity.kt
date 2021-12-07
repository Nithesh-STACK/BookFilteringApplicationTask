package com.example.bookfilterapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val searchButton=findViewById<Button>(R.id.bookButton)
        val bookMsg=findViewById<TextView>(R.id.bookSearch)
        val author= findViewById<TextInputLayout>(R.id.authorId).editText?.text
        val country =findViewById<TextInputLayout>(R.id.countryId).editText?.text
        searchButton.setOnClickListener()
        {
            var count=0
            val bookApplication=application as MyApplication
            val bookService=bookApplication.books

            CoroutineScope(Dispatchers.IO).launch {
                val decodedBookData=initHttpApiService().getmybook()
                withContext(Dispatchers.Main)
                {
                    val myStringData = StringBuilder()
                    for (myData in decodedBookData) {
                        if(count<3) {
                            if (author.toString() == myData.author && country.toString() == myData.country) {
                                myStringData.append("Result : "+myData.title)
                                myStringData.append("\n")
                                count++
                            }
                        }
                    }
                    bookMsg.text = "Results : "+count+"\n$myStringData"

                }
            }
        }
    }
}