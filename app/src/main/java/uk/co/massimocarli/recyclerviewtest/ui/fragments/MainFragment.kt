package uk.co.massimocarli.recyclerviewtest.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import uk.co.massimocarli.recyclerviewtest.OPTIONS
import uk.co.massimocarli.recyclerviewtest.OptionItem
import uk.co.massimocarli.recyclerviewtest.R
import uk.co.massimocarli.recyclerviewtest.ui.OnOptionItemSelected
import uk.co.massimocarli.recyclerviewtest.ui.navigation.Navigation

class MainFragment : Fragment() {

  private lateinit var navigation: Navigation

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val view = inflater.inflate(R.layout.fragment_main, container, false)
    val linearLayoutInflater = LinearLayoutManager(context).apply {
      orientation = RecyclerView.VERTICAL
      scrollToPosition(0)
    }
    view.findViewById<RecyclerView>(R.id.recyclerView).apply {
      adapter = OptionAdapter {
        navigation.replaceFragment(it.second, it.first)
      }
      layoutManager = linearLayoutInflater
    }
    return view
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    val activityAsNavigation = activity as? Navigation
    if (activityAsNavigation != null) {
      navigation = activityAsNavigation
    } else {
      throw IllegalStateException("Navigation Needed!")
    }
  }
}

class OptionViewHolder(val view: TextView, listener: OnOptionItemSelected? = null) :
  RecyclerView.ViewHolder(view) {

  lateinit var selectedItem: OptionItem

  init {
    listener?.let { optListener ->
      view.setOnClickListener { view ->
        optListener(selectedItem)
      }
    }
  }

  fun bindOption(item: OptionItem) {
    selectedItem = item
    view.text = item.first
  }
}


class OptionAdapter(val listener: OnOptionItemSelected? = null) : RecyclerView.Adapter<OptionViewHolder>() {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionViewHolder {
    val rowLayout = LayoutInflater.from(parent.context)
      .inflate(
        android.R.layout.simple_list_item_1,
        parent,
        false
      ) as TextView
    return OptionViewHolder(rowLayout, listener)
  }

  override fun getItemCount(): Int = OPTIONS.size

  override fun onBindViewHolder(holder: OptionViewHolder, position: Int) =
    holder.bindOption(OPTIONS[position])

}