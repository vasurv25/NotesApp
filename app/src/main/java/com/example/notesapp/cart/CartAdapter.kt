package com.example.notesapp.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.R
import com.example.notesapp.dashboard.DashboardClickListener
import com.example.notesapp.model.NotesDataModel
import kotlinx.android.synthetic.main.item_my_cart.view.*
import java.util.ArrayList

class CartAdapter(
    val context: Context,
    val deleteBookMarkListener: DashboardClickListener
) : RecyclerView.Adapter<CartAdapter.CartListViewHolder>() {

    private val mCartList = mutableListOf<NotesDataModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CartListViewHolder(
        LayoutInflater.from(context).inflate(R.layout.item_my_cart, parent, false)
    )

    override fun getItemCount(): Int {
        return mCartList.size
    }

    override fun onBindViewHolder(holder: CartListViewHolder, position: Int) {
        holder.bindActivityView(mCartList[position], position)
    }


    inner class CartListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindActivityView(
            notesDataModel: NotesDataModel,
            position: Int
        ) {
            itemView.tv_cart_subject_name.text = notesDataModel.subjectName
            itemView.tv_cart_price.text = context.getString(R.string.Rs) + notesDataModel.price
            itemView.iv_cart_option_selection.setOnClickListener {
                deleteBookMarkListener.chooseOptionListener(notesDataModel.id, position)
            }
            itemView.iv_cart_remove.setOnClickListener {
                deleteBookMarkListener.deleteBookMark(notesDataModel.id, position)
            }
            itemView.cv_minus.setOnClickListener {
                if (notesDataModel.quantity >= 1) {
                    var quantity = notesDataModel.quantity--
                    itemView.tv_cart_quantity.text = quantity--.toString()
                    notesDataModel.quantity = quantity
                }
            }
            itemView.cv_plus.setOnClickListener {
                if (notesDataModel.quantity <= 50) {
                    var quantity = notesDataModel.quantity++
                    itemView.tv_cart_quantity.text = quantity++.toString()
                    notesDataModel.quantity = quantity
                }
            }
        }
    }

    fun addAllCart(notesDataModel: ArrayList<NotesDataModel>) {
        mCartList.addAll(notesDataModel)
        notifyDataSetChanged()
    }

    fun cartItemRemoved(position: Int) {
        mCartList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount)
    }
}