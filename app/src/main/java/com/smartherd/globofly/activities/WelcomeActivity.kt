package com.smartherd.globofly.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.smartherd.globofly.R
import com.smartherd.globofly.Services.MessageService
import com.smartherd.globofly.Services.ServiceBuilder
import kotlinx.android.synthetic.main.activity_welcome.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WelcomeActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_welcome)

		// To be replaced by retrofit code
		//message.text = "Black Friday! Get 50% cash back on saving your first spot."
        val messageService:MessageService = ServiceBuilder.buildService(MessageService::class.java)

        //port address reference to emulator
        val requestCall:Call<String> = messageService.getMessages("http://10.0.2.2:7000/messages")

        requestCall.enqueue(object : Callback<String>{
            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(this@WelcomeActivity,"Failed to retrieve items",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(response.isSuccessful){
                    val msg:String? = response.body()
                    msg?.let {
                        message.text = msg
                    }
                }else{
                    Toast.makeText(this@WelcomeActivity,"Failed to retrieve items",Toast.LENGTH_LONG).show()
                }
            }

        })
	}

	fun getStarted(view: View) {
		val intent = Intent(this, DestinationListActivity::class.java)
		startActivity(intent)
		finish()
	}
}
