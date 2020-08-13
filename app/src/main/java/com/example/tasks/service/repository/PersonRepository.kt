package com.example.tasks.service.repository

import android.telecom.Call
import com.example.tasks.service.HeaderModel
import com.example.tasks.service.repository.remote.PersonService
import com.example.tasks.service.repository.remote.RetrofitClient
import retrofit2.Callback
import retrofit2.Response

class PersonRepository {

    private val mRemote = RetrofitClient.createService(PersonService::class.java)

    fun login(email: String, password: String) {
        val call: retrofit2.Call<HeaderModel> = mRemote.login(email, password)

        call.enqueue(object  : Callback<HeaderModel>, {
            override fun onFailure(call: retrofit2.Call<HeaderModel>, t: Throwable) {
                val s = " "
            }

            override fun onResponse(
                call: retrofit2.Call<HeaderModel>,
                response: Response<HeaderModel>
            ) {
                val header = response.body()
            }

        })

    }

}

