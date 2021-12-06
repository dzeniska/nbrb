package com.dzenis_ska.nbrb

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dzenis_ska.nbrb.databinding.ItemFragmentCourceBinding
import com.dzenis_ska.nbrb.db.CurrencyTwoDay

class CourseAdapter(): RecyclerView.Adapter<CourseAdapter.CourseViewHolder>(), View.OnClickListener{

    var courses: List<CurrencyTwoDay> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFragmentCourceBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        return CourseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val course = courses[position]
        with(holder.binding){
            tvCharCode.text = course.charCode
            tvName.text = "${course.scale} ${course.name}"
            tvDay1.text = course.rate_yesterday
            tvDay2.text = course.rate
        }
    }

    override fun getItemCount(): Int = courses.size
    class CourseViewHolder(val binding: ItemFragmentCourceBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onClick(v: View?) {}
}