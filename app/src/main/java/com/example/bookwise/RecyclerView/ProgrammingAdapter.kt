package com.example.bookwise.RecyclerView

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bookwise.Data.Book.BookListItem
import com.example.bookwise.R
import com.example.bookwise.SharedPreferenceHelper.SharedPreferencesHelper
import com.example.bookwise.Utils
import com.example.bookwise.ViewModels.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProgrammingAdapter(private val mainViewModel: MainViewModel) : ListAdapter<BookListItem, ProgrammingAdapter.ProgrammingViewHolder>(diffUtil()) {

    class ProgrammingViewHolder(view: View,mainViewModel: MainViewModel) : RecyclerView.ViewHolder(view) {

        private val bookName: TextView = view.findViewById(R.id.book_name)
        private val authorName: TextView = view.findViewById(R.id.author_name)
        private val categoryName: TextView = view.findViewById(R.id.category_name)
        private val quantity: TextView = view.findViewById(R.id.quantity)
        private val button :Button = view.findViewById(R.id.add_quantity_button_init)



        fun bind(item: BookListItem,mainViewModel: MainViewModel) {
            CoroutineScope(Dispatchers.Main).launch {
                bookName.text = item.title
                authorName.text = item.author.name
                categoryName.text = item.genre.name
                quantity.text = item.quantity.toString()
                if (item.quantity==0){
                    button.isEnabled = false
                }
            }
            button.setOnClickListener {
                Log.i("BUTTON ACTION","Book Id-${item.id}")
                mainViewModel.initiateTransaction(SharedPreferencesHelper.readInt(Utils.card_id,-1),item.id)

            }
        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgrammingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.book_list_items, parent, false)
        return ProgrammingViewHolder(view,mainViewModel)
    }

    override fun onBindViewHolder(holder: ProgrammingViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item,mainViewModel)
    }

    companion object {
        class diffUtil : DiffUtil.ItemCallback<BookListItem>() {
            override fun areItemsTheSame(oldItem: BookListItem, newItem: BookListItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: BookListItem, newItem: BookListItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
