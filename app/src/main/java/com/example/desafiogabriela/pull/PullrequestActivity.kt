package com.example.desafiogabriela.pull

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.desafiogabriela.api.RetrofitLauncher
import com.example.desafiogabriela.utils.Constant
import com.example.desafiogabriela.model.ItemPullrequest
import com.example.desafiogabriela.databinding.ActivityPullrequestBinding
import com.example.desafiogabriela.pull.viewmodel.PullrequestViewModel
import com.example.desafiogabriela.pull.viewmodel.PullrequestViewModelFactory

class PullrequestActivity : AppCompatActivity(), PullrequestAdapter.ClickListener {

    private var owner = ""
    private var repository = ""

    private val pullViewModel: PullrequestViewModel by viewModels {
        PullrequestViewModelFactory(RetrofitLauncher.get())
    }

    private val list = ArrayList<ItemPullrequest>()
    private val pullAdapter = PullrequestAdapter(list, this)
    private lateinit var bindingPull: ActivityPullrequestBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        owner = intent.getStringExtra(Constant.owner).toString()
        repository = intent.getStringExtra(Constant.repository).toString()

        super.onCreate(savedInstanceState)
        bindingPull = ActivityPullrequestBinding.inflate(layoutInflater)
        setContentView(bindingPull.root)

        setSupportActionBar(bindingPull.pullToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        bindingPull.pullrequest.adapter = pullAdapter
        bindingPull.pullrequest.layoutManager = LinearLayoutManager(this)
        bindingPull.pullrequest.setHasFixedSize(true)
        bindingPull.pullToolbar.title = repository

        pullView()
        pullViewModel.getSearchPull(owner, repository)
    }

    private fun pullView() {
        pullViewModel.pullLiveDataNetworkSuccess.observe(this, Observer {

            pullAdapter.list = it
            pullAdapter.notifyDataSetChanged()
        })

        pullViewModel.pullLiveDataNetworkError.observe(this, Observer {
            showError(it)
        })
    }
    fun showError(@StringRes errorRes: Int) {
        AlertDialog.Builder(this)
            .setMessage(errorRes)
            .show()
    }

    override fun setOnClickListener(itemClick: ItemPullrequest) {
        val intentPull = Intent(Intent.ACTION_VIEW, Uri.parse(itemClick.html))
        startActivity(intentPull)
    }
}
