package com.wreckingball.recyclerviewexpandingadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewExpandingAdapter(
    private val context: Context,
    list: List<ExpandingData>,
    private val parentLayout: Int,
    private val childLayout: Int,
    private val expandingViewHandler: ExpandingViewHandler
) : RecyclerView.Adapter<RecyclerView.ViewHolder>()  {
    private var itemList: MutableList<ExpandingData>
    private var expandingAdapter: RecyclerViewExpandingAdapter = this

    companion object {
        private const val TYPE_PARENT = 1
        private const val TYPE_CHILD = 2
        private const val UNEXPANDED = -1
        private var itemExpanded = UNEXPANDED
    }

    init {
        itemExpanded = UNEXPANDED
        itemList = mutableListOf()
        for ((index, item) in list.withIndex()) {
            item.index = index
            itemList.add(item)
        }
    }

    class ParentViewHolder(itemView: View,
                           private val list: MutableList<ExpandingData>,
                           private val expandingAdapter: RecyclerViewExpandingAdapter,
                           private val viewHandler: ExpandingViewHandler) : RecyclerView.ViewHolder(itemView) {
        fun bindView(item: ExpandingData) {
            viewHandler.handleParentView(itemView, item)
            itemView.setOnClickListener {view ->
                if (list[layoutPosition].isParent) {
                    expandCollapse(view)
                } else {
                    viewHandler.handleChildClick(item)
                }
            }
        }

        private fun expandCollapse(view: View) {
            val index = list[layoutPosition].index
            when (itemExpanded) {
                UNEXPANDED -> expandAlbum(list, index, view)
                layoutPosition -> collapseAlbum(list, itemExpanded, view)
                else -> {
                    //user clicked a parent while a different parent is expanded
                    collapseAlbum(list, itemExpanded, view)
                    expandAlbum(list, index, view)
                }
            }
        }

        private fun expandAlbum(list: MutableList<ExpandingData>, index: Int, view: View) {
            viewHandler.onParentExpand(view)
            expandingAdapter.notifyDataSetChanged()

            val children = list[index].children
            if (!children.isNullOrEmpty()) {
                itemExpanded = index
                for ((childIndex, i) in (index + 1 until index + 1 + children.size).withIndex()) {
                    val child = children[childIndex]
                    list.add(i, child)
                }
                expandingAdapter.notifyItemRangeInserted(layoutPosition + 1, children.size)
            }
        }

        private fun collapseAlbum(list: MutableList<ExpandingData>, expandedItem: Int, view: View) {
            val parentView: ViewGroup = view.parent as ViewGroup
            viewHandler.onParentCollapse(parentView.getChildAt(expandedItem))
            expandingAdapter.notifyDataSetChanged()

            val children = list[expandedItem].children
            if (!children.isNullOrEmpty()) {
                itemExpanded = UNEXPANDED
                for (i in (expandedItem + 1 until expandedItem + 1 + children.size)) {
                    list.removeAt(expandedItem + 1)
                }
                expandingAdapter.notifyItemRangeRemoved(layoutPosition + 1, children.size)
            }
        }
    }

    class ChildViewHolder(itemView: View, private val viewHandler: ExpandingViewHandler) : RecyclerView.ViewHolder(itemView) {
        fun bindView(item: ExpandingData) {
            viewHandler.handleChildView(itemView, item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_PARENT) {
            val view = LayoutInflater.from(context).inflate(parentLayout, parent, false)
            ParentViewHolder(view, itemList, expandingAdapter, expandingViewHandler)
        } else {
            val view = LayoutInflater.from(context).inflate(childLayout, parent, false)
            ChildViewHolder(view, expandingViewHandler)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = itemList[position]
        if (item.isParent) {
            (holder as ParentViewHolder).bindView(item)
        } else {
            (holder as ChildViewHolder).bindView(item)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (itemList[position].isParent) {
            TYPE_PARENT
        } else {
            TYPE_CHILD
        }
    }
}