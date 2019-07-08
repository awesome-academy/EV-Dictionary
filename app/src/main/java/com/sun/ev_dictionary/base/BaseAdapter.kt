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
) : RecyclerView.Adapter<BaseAdapter.ViewHolder<T>>() {

    private var items = emptyList<T>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<T> =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                layoutResource,
                parent,
                false
            ),
            listener
        )

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder<T>, position: Int) {
        holder.bindData(items)
    }

    fun setItems(items: List<T>) {
        this.items = items
        notifyDataSetChanged()
    }

    class ViewHolder<T>(
        private val viewBinding: ViewDataBinding,
        private val listener: OnItemClickListener
    ) : RecyclerView.ViewHolder(viewBinding.root) {

        fun bindData(items: List<T>) {
            viewBinding.setVariable(BR.data, items[adapterPosition])
            viewBinding.setVariable(BR.listener, listener)
            viewBinding.executePendingBindings()
        }
    }

    interface OnItemClickListener
}
