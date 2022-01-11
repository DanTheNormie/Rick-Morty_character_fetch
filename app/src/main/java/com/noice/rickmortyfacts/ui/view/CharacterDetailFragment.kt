package com.noice.rickmortyfacts.ui.view

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.epoxy.ModelProp
import com.bumptech.glide.Glide
import com.noice.rickmortyfacts.R
import com.noice.rickmortyfacts.databinding.CharacterDetailFragmentBinding
import com.noice.rickmortyfacts.model.CharacterModel
import com.noice.rickmortyfacts.ui.epoxy.CharacterDetailsEpoxyController
import com.noice.rickmortyfacts.utils.Status
import com.noice.rickmortyfacts.ui.viewmodel.CharacterDetailViewModel

class CharacterDetailFragment : Fragment() {

    companion object {
        fun newInstance() = CharacterDetailFragment()
    }

    private lateinit var bind: CharacterDetailFragmentBinding

    private lateinit var viewModel: CharacterDetailViewModel

    private val epoxyController = CharacterDetailsEpoxyController()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setup viewModel
        viewModel = ViewModelProvider(this)[CharacterDetailViewModel::class.java]
        bind = CharacterDetailFragmentBinding.inflate(layoutInflater)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        bind.epoxyRv.setControllerAndBuildModels(epoxyController)

        bind.epoxyRv.addItemDecoration(DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL))
        viewModel.characterModel.observe(viewLifecycleOwner, {
            when(it.status){
                Status.SUCCESS -> {
                    bind.progressBar.visibility = View.GONE
                    bind.epoxyRv.visibility  = View.VISIBLE
                    epoxyController.characterData = it.data!!
                }
                Status.ERROR ->{
                    bind.progressBar.visibility = View.GONE
                    bind.epoxyRv.visibility = View.GONE
                    Toast.makeText(activity,it.msg,Toast.LENGTH_SHORT).show()
                }
                Status.LOADING ->{
                    bind.progressBar.visibility = View.VISIBLE
                    bind.epoxyRv.visibility = View.GONE
                }
                else->{
                    //unreachable
                }
            }
            bind.swipeRefresh.isRefreshing = false
        })
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind.swipeRefresh.setOnRefreshListener {
            val randomNumber = (1..826).random()
            viewModel.refreshCharacterData(randomNumber)
        }
    }



}