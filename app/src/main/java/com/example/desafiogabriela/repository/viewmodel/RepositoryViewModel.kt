package com.example.desafiogabriela.repository.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.desafiogabriela.R
import com.example.desafiogabriela.model.ItemRepository
import com.example.desafiogabriela.model.Items
import com.example.desafiogabriela.api.WebClient
import com.example.desafiogabriela.utils.log.Logger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryViewModel(
    private val get: WebClient,
    private val logger: Logger
) : ViewModel() {

    var page = 1
    private val list = mutableListOf<ItemRepository>()

    private val liveDataListSuccess: MutableLiveData<List<ItemRepository>> = MutableLiveData()
    val liveDataNetworkSuccess: LiveData<List<ItemRepository>> = liveDataListSuccess
    private val liveDataError = MutableLiveData<Int>()
    val liveDataNetworkError: LiveData<Int> = liveDataError

    fun getSearch() {

        get.search(page).enqueue(object : Callback<Items> {

            override fun onFailure(
                call: Call<Items>,
                t: Throwable
            ) {
                logger.logMessage("unexpected error", t.message.toString())
                liveDataError.postValue(R.string.network_error)
            }

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
                    liveDataError.postValue(R.string.error_message)
                }
            }
        })
    }
}
