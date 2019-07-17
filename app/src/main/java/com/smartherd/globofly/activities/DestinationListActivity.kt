package com.smartherd.globofly.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.telecom.Call
import android.util.Log
import android.widget.Toast
import com.smartherd.globofly.R
import com.smartherd.globofly.Services.DestinationService
import com.smartherd.globofly.Services.ServiceBuilder
import com.smartherd.globofly.helpers.DestinationAdapter
import com.smartherd.globofly.helpers.SampleData
import com.smartherd.globofly.models.Destination
import kotlinx.android.synthetic.main.activity_destiny_list.*
import retrofit2.Response
import javax.security.auth.callback.Callback

class DestinationListActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_destiny_list)

		setSupportActionBar(toolbar)
		toolbar.title = title

		fab.setOnClickListener {
			val intent = Intent(this@DestinationListActivity, DestinationCreateActivity::class.java)
			startActivity(intent)
		}
	}

	override fun onResume() {
		super.onResume()

		loadDestinations()
	}

	private fun loadDestinations() {

        // To be replaced by retrofit code
		//destiny_recycler_view.adapter = DestinationAdapter(SampleData.DESTINATIONS)
        val destinationService:DestinationService=ServiceBuilder.buildService(DestinationService::class.java)
		val filter = HashMap<String, String>()

		/* TO CHECK QUERY CONCEPT AS COMMENTED FILTER BECOME NULL AND GET WHOLE LIST*/
//		filter["country"] = "India"
//		filter["count"] = "1"

        val requestCall:retrofit2.Call<List<Destination>> =destinationService.getDestinationList(filter)
        requestCall.enqueue(object : retrofit2.Callback<List<Destination>>{

            override fun onResponse(call: retrofit2.Call<List<Destination>>, response: Response<List<Destination>>) {
				Log.e("response coming","success")
                if(response.isSuccessful){
					Toast.makeText(this@DestinationListActivity,"success",Toast.LENGTH_LONG).show()
					Log.e("reach sahi jgh","yes")
                    val destinationList:List<Destination> = response.body()!!
                    destiny_recycler_view.adapter = DestinationAdapter(destinationList)
                }
            }

			override fun onFailure(call: retrofit2.Call<List<Destination>>, t: Throwable) {

				Log.e("error in response",t.message)
			}

        })
    }
}
