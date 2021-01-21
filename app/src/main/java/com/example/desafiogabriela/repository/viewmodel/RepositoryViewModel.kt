package com.example.desafiogabriela.repository.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.desafiogabriela.model.ItemRepository
import com.example.desafiogabriela.model.Items
import com.example.desafiogabriela.api.WebClient
import com.example.desafiogabriela.utils.Logger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryViewModel(
    private val get: WebClient,
    private val logger: Logger
) : ViewModel() {

    var page = 1
    private val list = mutableListOf<ItemRepository>()

    private val liveDataError = MutableLiveData<Any>()
    private val liveDataListSuccess: MutableLiveData<List<ItemRepository>> = MutableLiveData()
    val liveData: LiveData<List<ItemRepository>> = liveDataListSuccess
    val liveDataNetworkError: LiveData<Any> = liveDataError

    fun getSearch() {

        get.search(page).enqueue(object : Callback<Items> {
            override fun onResponse(
                call: Call<Items>,
                response: Response<Items>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {

                        list.addAll(it.items)
                        liveDataListSuccess.value = list
                        page++
                    }
                } else {
                    Throwable(response.errorBody()?.string())
                }
            }

            override fun onFailure(
                call: Call<Items>,
                t: Throwable
            ) {
                logger.logMessage("unexpected error", t.message.toString())
                liveDataError.postValue(t)
            }
        })
    }
}
