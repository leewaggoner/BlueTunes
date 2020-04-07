package com.wreckingball.recyclerviewexpandingadapter

import android.view.View

interface ExpandingViewHandler {
    fun handleParentView(itemView: View, item: ExpandingData)
    fun onParentExpand(itemView: View)
    fun onParentCollapse(itemView: View)
    fun handleChildView(itemView: View, item: ExpandingData)
    fun handleChildClick(item: ExpandingData)
}