package com.example.newmeas.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newmeas.Data.Measures
import com.example.newmeas.R
import kotlinx.android.synthetic.main.counters_list.view.*

/*
исп-ся в аргументах лямбда-функция
 */
class CustomAdapter(private var mess: MutableList<Measures>, private val clickListener: (Measures) -> Unit) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {


    fun updateList(list: MutableList<Measures>) {

        mess.clear()
        mess.addAll(list)
        notifyDataSetChanged()
    }

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.counters_list, parent, false)
        return ViewHolder(v)
    }


    //this method is binding the data on the list
    override fun onBindViewHolder(holder: CustomAdapter.ViewHolder, position: Int) {

        holder.bind(mess[position], clickListener)
    }


    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return mess.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(mess: Measures, clickListener: (Measures) -> Unit) {
            itemView.recyclerCounterTitle.text = mess.name
            itemView.recyclerCounterCurrentValue.text = mess.currentValue.toString()
            itemView.setOnClickListener { clickListener(mess) }
        }
    }

}