package com.example.desafiogabriela.pull.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.desafiogabriela.R
import com.example.desafiogabriela.model.ItemPullrequest
import com.example.desafiogabriela.api.WebClient
import com.example.desafiogabriela.utils.log.Logger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PullrequestViewModel(
    private val getPull: WebClient,
    private val logger: Logger
) : ViewModel() {

    private val pullLiveDataSuccess: MutableLiveData<List<ItemPullrequest>> = MutableLiveData()
    val pullLiveDataNetworkSuccess: LiveData<List<ItemPullrequest>> = pullLiveDataSuccess
    private val pullLiveDataError = MutableLiveData<Int>()
    val pullLiveDataNetworkError: LiveData<Int> = pullLiveDataError

    fun getSearchPull(owner: String, repository: String) {
        getPull.searchPull(owner, repository).enqueue(object : Callback<List<ItemPullrequest>> {

            override fun onFailure(
                call: Call<List<ItemPullrequest>>,
                t: Throwable
            ) {
                logger.logMessage("Error", t.message.toString())
                pullLiveDataError.postValue(R.string.network_error)
            }

            override fun onResponse(
                call: Call<List<ItemPullrequest>>,
                response: Response<List<ItemPullrequest>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        pullLiveDataSuccess.postValue(it)
                    }
                } else {
                   pullLiveDataError.postValue(R.string.error_message)
                }
            }
        })
    }
}
