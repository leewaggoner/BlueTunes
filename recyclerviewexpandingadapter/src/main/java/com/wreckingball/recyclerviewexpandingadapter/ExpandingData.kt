package com.wreckingball.recyclerviewexpandingadapter

interface ExpandingData {
    var children: MutableList<ExpandingData>?
    var isParent: Boolean
    var index: Int
}