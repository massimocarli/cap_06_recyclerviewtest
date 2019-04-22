package uk.co.massimocarli.recyclerviewtest

import androidx.fragment.app.Fragment
import uk.co.massimocarli.recyclerviewtest.ui.fragments.*

typealias OptionItem = Pair<String, Fragment>

val OPTIONS = arrayOf<OptionItem>(
  "Simple RecyclerView" to SimpleRecyclerViewFragment(),
  "Simple Horizontal RecyclerView" to HorizontalRecyclerViewFragment(),
  "Grid RecyclerView" to GridRecyclerViewFragment(),
  "Grid Span RecyclerView" to GridSpanRecyclerViewFragment(),
  "Custom LayoutManager RecyclerView" to CustomLayoutManagerRecyclerViewFragment(),
  "Custom Decoration RecyclerView" to CustomDecorationRecyclerViewFragment(),
  "TouchHelper RecyclerView" to TouchHelperFragment(),
  "RecyclerView with DiffUtil" to DiffRecyclerViewFragment(),
  "RecyclerView with ListAdapter" to AsyncDiffRecyclerViewFragment(),
  "RecyclerView with PullToRefresh" to PullToRefreshFragment(),
  "RecyclerView with different items" to AlternateViewFragment(),
  "RecyclerView with Cards" to CardFragment()
)