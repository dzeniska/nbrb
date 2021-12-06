package com.dzenis_ska.nbrb

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.dzenis_ska.nbrb.databinding.FragmentSettingsBinding
import com.dzenis_ska.nbrb.db.CurrencyTwoDay
import com.dzenis_ska.nbrb.db.MainApp
import com.dzenis_ska.nbrb.db.MainViewModel
import com.dzenis_ska.nbrb.remote_model.SettingsAdapter

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private var binding: FragmentSettingsBinding? = null

    private var adapterSettings: SettingsAdapter? = null

    private var dragCallback: ItemTouchMoveCallback? = null

    private var touchHelper: ItemTouchHelper? = null


    private val mainViewModel: MainViewModel by activityViewModels{
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingsBinding.bind(view)
        setHasOptionsMenu(true)



        adapterSettings = SettingsAdapter()
        dragCallback = ItemTouchMoveCallback(adapterSettings!!)
        touchHelper = ItemTouchHelper(dragCallback!!)
        touchHelper!!.attachToRecyclerView(binding!!.rcViewSettings)
        val layoutManager = LinearLayoutManager(context)
        binding!!.rcViewSettings.layoutManager = layoutManager
        binding!!.rcViewSettings.adapter = adapterSettings


        mainViewModel.getListForSettings()
        mainViewModel.listCurr.observe(viewLifecycleOwner, {list->
            list.sortBy { it.numb }
            adapterSettings?.courses = list
        })

    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_done, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.done -> {
                mainViewModel.isSave = true
                mainViewModel.replaseNumb(adapterSettings!!.courses)
                findNavController().popBackStack()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapterSettings?.courses?.clear()
        binding = null
    }

}