package com.example.moviewithpaging.views.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviewithpaging.R
import com.example.moviewithpaging.adapter.MovieAdapter
import com.example.moviewithpaging.api.MovieDBClient
import com.example.moviewithpaging.databinding.FragmentFirstBinding
import com.example.moviewithpaging.model.MovieModel
import com.example.moviewithpaging.repository.MovieRepository
import com.example.moviewithpaging.storage.MovieDatabase
import com.example.moviewithpaging.viewModel.MovieViewModel
import com.example.moviewithpaging.viewModel.ViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@ExperimentalPagingApi
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private lateinit var adapter: MovieAdapter
    private lateinit var viewModel: MovieViewModel
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val api = MovieDBClient.getClient()
        val db =MovieDatabase.getDatabase(requireContext())
        val repository = MovieRepository(api,db)

        viewModel = ViewModelProvider(this, ViewModelFactory(repository))[MovieViewModel::class.java]

        adapter = MovieAdapter(requireContext())
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerView.adapter = adapter

        viewModel.movielist.observe(viewLifecycleOwner, Observer {
            adapter.submitData(lifecycle,it)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


