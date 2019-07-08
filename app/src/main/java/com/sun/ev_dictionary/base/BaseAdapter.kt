package com.sun.ev_dictionary.base

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView

class BaseAdapter<T> constructor(
    private val context: Context,
    private val layoutResource: Int,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<BaseAdapter.ViewHolder>() {

    private var items = emptyList<T>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                layoutResource,
                parent,
                false
            )
        )

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.viewBinding.setVariable(BR.data, items[position])
        holder.viewBinding.setVariable(BR.listener, listener)
        holder.viewBinding.executePendingBindings()
    }

    fun setItems(items: List<T>) {
        this.items = items
        notifyDataSetChanged()
    }

    class ViewHolder(val viewBinding: ViewDataBinding) : RecyclerView.ViewHolder(viewBinding.root)

    interface OnItemClickListener
}
