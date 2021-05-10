package com.atechy.atechytwitterclone.base

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * base adapter class
 */
abstract class BaseAdapter<T> : RecyclerView.Adapter<BaseViewHolder<ViewDataBinding>>() {

    override fun onBindViewHolder(holder: BaseViewHolder<ViewDataBinding>, position: Int) {
        (holder as BaseViewHolder<*>).binding.root.setTag(getItemLayoutId(holder.adapterPosition), position)
        bind(holder.binding, holder.adapterPosition)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<ViewDataBinding>, position: Int, payloads: MutableList<Any>) {
        if (payloads.isNotEmpty()) {
            for (any in payloads) {
                (holder as BaseViewHolder<*>).binding.root.setTag(getItemLayoutId(holder.adapterPosition), position)
                bindWithPayload(holder.binding, holder.adapterPosition, any)
            }
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ViewDataBinding> {
        return getViewHolder(parent, viewType)
    }

    open fun getViewHolder(parent: ViewGroup, viewType: Int) = BaseViewHolder(createBinding(parent, viewType))

    abstract fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding

    abstract fun getItemLayoutId(viewType: Int): Int

    protected abstract fun bind(binding: ViewDataBinding, position: Int)

    protected abstract fun bindWithPayload(binding: ViewDataBinding, position: Int, any: Any)
}