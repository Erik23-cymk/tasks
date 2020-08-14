package com.example.tasks.service.repository

import android.content.Context
import android.telecom.Call
import com.example.tasks.R
import com.example.tasks.service.HeaderModel
import com.example.tasks.service.constants.TaskConstants
import com.example.tasks.service.listener.APIListener
import com.example.tasks.service.repository.remote.PersonService
import com.example.tasks.service.repository.remote.RetrofitClient
import com.google.gson.Gson
import retrofit2.Callback
import retrofit2.Response

class PersonRepository(val context: Context) {

    private val mRemote = RetrofitClient.createService(PersonService::class.java)

    fun login(email: String, password: String, listener: APIListener) {
        val call: retrofit2.Call<HeaderModel> = mRemote.login(email, password)

        call.enqueue(object : Callback<HeaderModel> {
            override fun onFailure(call: retrofit2.Call<HeaderModel>, t: Throwable) {
                listener.onFailure(context.getString(R.string.ERROR_UNEXPECTED))
            }

            override fun onResponse(
                call: retrofit2.Call<HeaderModel>,
                response: Response<HeaderModel>
            ) {
                if (response.code() != TaskConstants.HTTP.SUCCESS) {
                    val validation =
                        Gson().fromJson(response.errorBody()!!.string(), String::class.java)
                    listener.onFailure(validation)
                } else {
                    response.body()?.let { listener.onSuccess(it) }
                }

            }
        })
    }
}

