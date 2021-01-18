package com.example.desafiogabriela.repository

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiogabriela.repository.viewmodel.RepositoryViewModelFactory
import com.example.desafiogabriela.api.InicializadorDeRetrofit
import com.example.desafiogabriela.databinding.ActivityRepositoryBinding
import com.example.desafiogabriela.pull.PullrequestActivity
import com.example.desafiogabriela.repository.viewmodel.RepositoryViewModel
import com.example.desafiogabriela.utils.Constante

class RepositoryActivity : AppCompatActivity(), RepositoryAdapter.OnItemClickListener {

    private val viewModel: RepositoryViewModel by viewModels {
        RepositoryViewModelFactory(InicializadorDeRetrofit.get())
    }

    var lastPosition = 0
    var isLoading = false

    private val adapter = RepositoryAdapter(ArrayList(), this)
    private lateinit var binding: ActivityRepositoryBinding
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityRepositoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        layoutManager = LinearLayoutManager(this)
        binding.repositorio.layoutManager = layoutManager
        binding.repositorio.adapter = adapter
        binding.repositorio.setHasFixedSize(true)

        getPage()

        binding.repositorio.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastCompleteItem = layoutManager.findLastVisibleItemPosition()

                if (!isLoading) {


                    if (lastCompleteItem == adapter.itemCount - 1) {
                        isLoading = true

                        lastPosition = adapter.itemCount
                        viewModel.getSearch()
                        binding.progressBar.visibility = View.VISIBLE
                    }
                }
            }

        })
    }

    private fun getPage() {
        viewModel.liveData.observe(this, Observer {

            adapter.list = (it)
            adapter.notifyDataSetChanged()
            binding.progressBar.visibility = View.GONE

            isLoading = false


        })
        viewModel.getSearch()
    }

    override fun onItemClick(position: Int) {
        val intent = Intent(this, PullrequestActivity::class.java)
        intent.putExtra(Constante.owner, adapter.list[position].owner.username)
        intent.putExtra(Constante.repositorio, adapter.list[position].nameRepository)
        intent.putExtra(Constante.foto, adapter.list[position].owner.image)
        startActivity(intent)
    }
}