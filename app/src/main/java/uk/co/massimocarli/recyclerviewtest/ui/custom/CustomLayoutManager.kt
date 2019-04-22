package uk.co.massimocarli.recyclerviewtest.ui.custom

import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView

/**
 * Example of a Custom LayoutManager
 */
class CustomLayoutManager : RecyclerView.LayoutManager() {
  override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams =
    RecyclerView.LayoutParams(
      RecyclerView.LayoutParams.WRAP_CONTENT,
      RecyclerView.LayoutParams.WRAP_CONTENT
    )

//  override fun onLayoutChildren(
//    recycler: RecyclerView.Recycler,
//    state: RecyclerView.State
//  ) = fillChild(recycler, state)


  private var bottomLimit = 0
  private var scrollingOffset = 0
  private lateinit var layoutInfo: Array<Rect>

  override fun onLayoutChildren(
    recycler: RecyclerView.Recycler,
    state: RecyclerView.State
  ) {
    if (itemCount == 0) {
      return
    }
    val firstView = recycler.getViewForPosition(0)
    measureChildWithMargins(firstView, 0, 0)
    val itemWidth = getDecoratedMeasuredWidth(firstView)
    val itemHeight = getDecoratedMeasuredHeight(firstView)
    bottomLimit = 0
    layoutInfo = Array<Rect>(itemCount) {
      val rect = Rect(
        0,
        itemHeight * it,
        itemWidth,
        itemHeight * (it + 1)
      )
      bottomLimit += itemHeight
      rect
    }
    fillChild(recycler, state)
  }

  override fun canScrollHorizontally(): Boolean = false
  override fun canScrollVertically(): Boolean = true


  private fun fillChild(
    recycler: RecyclerView.Recycler,
    state: RecyclerView.State
  ) {
    detachAndScrapAttachedViews(recycler)
    layoutInfo.indices
      .filterNot { isRectVisible(it) }
      .forEach { index ->
        recycler.getViewForPosition(index).let { view ->
          addView(view)
          measureChildWithMargins(view, 0, 0)
          layoutDecorated(
            view, layoutInfo[index].left,
            layoutInfo[index].top - scrollingOffset,
            layoutInfo[index].right,
            layoutInfo[index].bottom - scrollingOffset
          )
        }
      }
  }


  override fun scrollVerticallyBy(
    dy: Int,
    recycler: RecyclerView.Recycler,
    state: RecyclerView.State
  ): Int {
    val travel: Int
    val topLimit = 0
    if (dy + scrollingOffset < topLimit) {
      travel = scrollingOffset
      scrollingOffset = topLimit
    } else if (dy + scrollingOffset + getVerticalSpace() > bottomLimit) {
      travel = bottomLimit - scrollingOffset - height
      scrollingOffset = bottomLimit - getVerticalSpace()
    } else {
      travel = dy
      scrollingOffset += dy
    }
    fillChild(recycler, state)
    return travel
  }

  /*
  private fun fillChild(
    recycler: RecyclerView.Recycler,
    state: RecyclerView.State
  ) {
    detachAndScrapAttachedViews(recycler)
    var currentHeight = 0
    var index = 0
    while (currentHeight < getHeight()) {
      recycler.getViewForPosition(index++).apply {
        addView(this)
        measureChildWithMargins(this, 0, 0)
        val itemWidth = getDecoratedMeasuredWidth(this)
        val itemHeight = getDecoratedMeasuredHeight(this)
        val rect = Rect(0, currentHeight, itemWidth, currentHeight + itemHeight)
        layoutDecorated(this, rect.left, rect.top, rect.right, rect.bottom)
        currentHeight += itemHeight
      }
    }
  }
  */

  private fun isRectVisible(index: Int): Boolean =
    layoutInfo[index].bottom < scrollingOffset
        || layoutInfo[index].top > getVerticalSpace() + scrollingOffset

  private fun getVerticalSpace(): Int {
    return height - paddingBottom - paddingTop
  }
}

