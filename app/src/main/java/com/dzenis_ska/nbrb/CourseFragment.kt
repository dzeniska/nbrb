package com.dzenis_ska.nbrb

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.dzenis_ska.nbrb.databinding.FragmentCourseBinding
import com.dzenis_ska.nbrb.db.CurrencyTwoDay
import com.dzenis_ska.nbrb.db.MainApp
import com.dzenis_ska.nbrb.db.MainViewModel


class CourseFragment : Fragment(R.layout.fragment_course) {

    private var binding: FragmentCourseBinding? = null

    private var adapter: CourseAdapter? = null

    private val mainViewModel: MainViewModel by activityViewModels{
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCourseBinding.bind(view)
        setHasOptionsMenu(true)

        adapter = CourseAdapter()
        val layoutManager = LinearLayoutManager(context)
        binding!!.rcViewCourse.layoutManager = layoutManager
        binding!!.rcViewCourse.adapter = adapter
        binding!!.progressBar.visibility = View.VISIBLE

        mainViewModel.getC(){
            if(!it) {
                binding!!.progressBar.visibility = View.GONE
                binding!!.tvError.visibility = View.VISIBLE
                adapter?.courses = emptyList()
                setHasOptionsMenu(false)
            }
        }

        mainViewModel.listCurrencyTwoDay.observe(viewLifecycleOwner, { list ->
            val listCurr = arrayListOf<CurrencyTwoDay>()
            list.forEach {
                if (it.isCheck == 1) listCurr.add(it)
            }

            listCurr.sortBy { it.numb }
            binding!!.progressBar.visibility = View.GONE
            adapter?.courses = listCurr
        })

        with(binding!!){
            tvDay1.text = mainViewModel.day1
            tvDay2.text = mainViewModel.day2
        }

    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_settings, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.settings -> {
                findNavController().navigate(R.id.action_courseFragment_to_settingsFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}