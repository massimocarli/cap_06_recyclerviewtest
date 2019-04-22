package uk.co.massimocarli.recyclerviewtest.ui.custom

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class LineItemDecoration : RecyclerView.ItemDecoration {

  companion object {
    private const val DEFAULT_LINE_WIDTH = 2.0f
  }

  private val lineWidth: Float
  private var paint: Paint

  constructor(lineWidth: Float) : super() {
    this.lineWidth = lineWidth
    paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
      strokeWidth = lineWidth
      color = Color.GRAY
    }
  }

  constructor() : this(DEFAULT_LINE_WIDTH)

  override fun getItemOffsets(
    outRect: Rect,
    view: View,
    parent: RecyclerView,
    state: RecyclerView.State
  ) {
    super.getItemOffsets(
      outRect,
      view,
      parent,
      state
    )
    outRect.set(0, 0, 0, Math.floor(lineWidth.toDouble()).toInt())
  }

  override fun onDrawOver(
    c: Canvas,
    parent: RecyclerView,
    state: RecyclerView.State
  ) {
    super.onDrawOver(c, parent, state)
    parent.let {
      val layoutManager = parent.layoutManager
      for (i in 0 until parent.childCount) {
        val child = parent.getChildAt(i)
        layoutManager?.let {
          c.drawLine(
            it.getDecoratedLeft(child).toFloat(),
            it.getDecoratedBottom(child).toFloat(),
            it.getDecoratedRight(child).toFloat(),
            it.getDecoratedBottom(child).toFloat(),
            paint
          )
        }
      }
    }
  }
}