package com.dzenis_ska.nbrb.remote_model

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dzenis_ska.nbrb.ItemTouchAdapter
import com.dzenis_ska.nbrb.databinding.ItemFragmentSettingsBinding
import com.dzenis_ska.nbrb.db.CurrencyTwoDay


class SettingsAdapter():
    RecyclerView.Adapter<SettingsAdapter.CourseViewHolder>(),
    ItemTouchAdapter
{

    var courses: ArrayList<CurrencyTwoDay> = arrayListOf()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFragmentSettingsBinding.inflate(inflater, parent, false)

        binding.swButton.setOnCheckedChangeListener { buttonView, isChecked ->
            val cours = buttonView.tag as CurrencyTwoDay
            val indexToCheck = courses.indexOfFirst { it.id == cours.id }
            Log.d("!!!sw", "${cours.isCheck} _ $isChecked")

            if(isChecked)
                courses[indexToCheck].isCheck = 1
            else
                courses[indexToCheck].isCheck = 0

        }

        return CourseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val course = courses[position]
        with(holder.binding){

            tvCharCode.text = course.charCode
            tvName.text = "${course.scale} ${course.name}"
            swButton.tag = course
            swButton.isChecked = course.isCheck != 0
            imageButton.tag = course
        }
    }

    override fun getItemCount(): Int = courses.size
    class CourseViewHolder(val binding: ItemFragmentSettingsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onMove(startPos: Int, targetPos: Int) {
        val target = courses[targetPos]
        val start = courses[startPos]

        val targetNumb = courses[targetPos].numb
        val startNumb = courses[startPos].numb

        courses[targetPos] = start
        courses[startPos] = target
        courses[targetPos].numb = targetNumb
        courses[startPos].numb = startNumb

        notifyItemMoved(startPos, targetPos)
    }

}