package uk.co.massimocarli.recyclerviewtest.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import uk.co.massimocarli.recyclerviewtest.R
import uk.co.massimocarli.recyclerviewtest.model.MODEL
import uk.co.massimocarli.recyclerviewtest.model.ToDo

class AlternateViewFragment : Fragment() {

  val model: MutableList<ToDo> = mutableListOf()
  lateinit var todoAdapter: AlternateToDoAdapter

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
    todoAdapter = AlternateToDoAdapter(model)
    val linearLayoutManager = LinearLayoutManager(context).apply {
      orientation = RecyclerView.VERTICAL
      scrollToPosition(0)
    }
    view.findViewById<RecyclerView>(R.id.recyclerView).apply {
      adapter = todoAdapter
      layoutManager = linearLayoutManager
    }
    return view
  }
}


/**
 * This is the Adapter
 */
open class AlternateToDoAdapter(
  val model: List<ToDo>
) : RecyclerView.Adapter<ToDoViewHolder>() {

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): ToDoViewHolder {
    val layoutId: Int
    when (viewType) {
      0 -> layoutId = R.layout.todo_list_item
      else -> layoutId = R.layout.todo_list_item2
    }
    val itemLayout = LayoutInflater
      .from(parent.context)
      .inflate(
        layoutId,
        parent,
        false
      )
    return ToDoViewHolder(itemLayout)
  }

  override fun getItemViewType(position: Int) = position % 2

  override fun getItemCount(): Int =
    model.size

  override fun onBindViewHolder(
    holder: ToDoViewHolder,
    position: Int
  ) = holder.bind(model[position])
}
