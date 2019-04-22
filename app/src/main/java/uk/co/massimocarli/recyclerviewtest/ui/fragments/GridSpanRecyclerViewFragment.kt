package uk.co.massimocarli.recyclerviewtest.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import uk.co.massimocarli.recyclerviewtest.R
import uk.co.massimocarli.recyclerviewtest.model.MODEL
import uk.co.massimocarli.recyclerviewtest.model.ToDo


class GridSpanRecyclerViewFragment : Fragment() {

  val COLUMN_COUNT = 2

  val model: MutableList<ToDo> = mutableListOf()
  lateinit var todoAdapter: ToDoAdapter

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
    todoAdapter = ToDoAdapter(model)
    val gridLayoutManager = GridLayoutManager(
      context,
      COLUMN_COUNT,
      RecyclerView.VERTICAL,
      false
    ).apply {
      spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int = if (position % 3 == 0) 2 else 1
      }
    }
    view.findViewById<RecyclerView>(R.id.recyclerView).apply {
      adapter = todoAdapter
      layoutManager = gridLayoutManager
    }
    return view
  }
}
