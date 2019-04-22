package uk.co.massimocarli.recyclerviewtest.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import uk.co.massimocarli.recyclerviewtest.R
import uk.co.massimocarli.recyclerviewtest.model.MODEL
import uk.co.massimocarli.recyclerviewtest.model.ToDo
import java.util.*


class TouchHelperFragment : Fragment() {

  val model: MutableList<ToDo> = mutableListOf()
  lateinit var todoAdapter: TouchToDoAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    model.addAll(MODEL)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val view = inflater.inflate(
      R.layout.fragment_main,
      container,
      false
    )
    todoAdapter = TouchToDoAdapter(model)
    val touchHelper = ItemTouchHelper(CustomItemTouchHelper(todoAdapter))
    val linearLayoutManager = LinearLayoutManager(context).apply {
      orientation = RecyclerView.VERTICAL
      scrollToPosition(0)
    }
    view.findViewById<RecyclerView>(R.id.recyclerView).apply {
      adapter = todoAdapter
      layoutManager = linearLayoutManager
      touchHelper.attachToRecyclerView(this)
    }
    return view
  }
}

/**
 * Interface to implement to be notified of changes in the RecyclerView for swipe and
 * drag & drop
 */
internal interface OnItemTouchListener {
  /**
   * Operation to implement in case od Drag & Drop
   *
   * @param fromPosition The starting position
   * @param toPosition   The ending position
   */
  fun onItemMove(fromPosition: Int, toPosition: Int)

  /**
   * Operation to implement in case of dismiss
   *
   * @param position The dismissed position
   */
  fun onItemDismiss(position: Int)
}


private class CustomItemTouchHelper(
  private val mOnItemTouchListener: OnItemTouchListener
) : ItemTouchHelper.Callback() {

  override fun getMovementFlags(
    recyclerView: RecyclerView,
    viewHolder: RecyclerView.ViewHolder
  ): Int {
    val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
    val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
    return makeMovementFlags(dragFlags, swipeFlags)
  }

  override fun isLongPressDragEnabled(): Boolean = true

  override fun isItemViewSwipeEnabled(): Boolean = true

  override fun onMove(
    recyclerView: RecyclerView,
    src: RecyclerView.ViewHolder,
    dst: RecyclerView.ViewHolder
  ): Boolean {
    mOnItemTouchListener.onItemMove(
      src.adapterPosition,
      dst.adapterPosition
    )
    return true
  }

  override fun onSwiped(
    viewHolder: RecyclerView.ViewHolder,
    direction: Int
  ) {
    mOnItemTouchListener.onItemDismiss(viewHolder.adapterPosition)
  }

}

class TouchToDoAdapter(
  val mutableModel: MutableList<ToDo>
) : ToDoAdapter(mutableModel), OnItemTouchListener {
  override fun onItemMove(fromPosition: Int, toPosition: Int) {
    if (fromPosition < toPosition) {
      for (i in fromPosition until toPosition) {
        Collections.swap(mutableModel, i, i + 1)
      }
    } else {
      for (i in fromPosition downTo toPosition + 1) {
        Collections.swap(mutableModel, i, i - 1)
      }
    }
    notifyItemMoved(fromPosition, toPosition)
  }

  override fun onItemDismiss(position: Int) {
    mutableModel.removeAt(position);
    notifyItemRemoved(position);
  }
}
