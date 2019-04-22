package uk.co.massimocarli.recyclerviewtest.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import uk.co.massimocarli.recyclerviewtest.R
import uk.co.massimocarli.recyclerviewtest.model.MODEL
import uk.co.massimocarli.recyclerviewtest.model.ToDo
import java.lang.Thread.sleep
import java.util.*

class PullToRefreshFragment : Fragment() {

  val model: MutableList<ToDo> = mutableListOf()
  lateinit var todoAdapter: ToDoAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    model.add(MODEL[0])
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val view = inflater.inflate(
      R.layout.fragment_pull_to_refresh,
      container,
      false
    )
    todoAdapter = ToDoAdapter(model)
    val linearLayoutManager = LinearLayoutManager(context).apply {
      orientation = RecyclerView.VERTICAL
      scrollToPosition(0)
    }
    view.findViewById<RecyclerView>(R.id.recyclerView).apply {
      adapter = todoAdapter
      layoutManager = linearLayoutManager
    }
    view.findViewById<SwipeRefreshLayout>(R.id.refreshLayout).apply {
      setOnRefreshListener {
        val newId = model.size + 1
        val newToDo = ToDo(newId, "ToDo #$newId", "This is $newId", Date(), false)
        model.add(newToDo)
        todoAdapter.notifyItemChanged(model.size)
        isRefreshing = false
      }
    }
    return view
  }
}

