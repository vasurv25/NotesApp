package com.example.notesapp.address

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.R
import com.example.notesapp.dashboard.DashboardClickListener
import com.example.notesapp.model.AddressDataModel
import kotlinx.android.synthetic.main.item_address_list.view.*

class AddressAdapter(
    val context: Context,
    val deleteBookMarkListener: DashboardClickListener
) : RecyclerView.Adapter<AddressAdapter.AddressViewHolder>() {

    private val mAddressDataModelList = mutableListOf<AddressDataModel>()
    private var mCheckedPosition = -1
    

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = AddressViewHolder(
        LayoutInflater.from(context).inflate(R.layout.item_address_list, parent, false)
    )

    override fun getItemCount(): Int {
        return mAddressDataModelList.size
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        holder.bindActivityView(mAddressDataModelList[position])
    }


    inner class AddressViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindActivityView(
            addressDataModel: AddressDataModel
        ) {
            itemView.tv_address.text = addressDataModel.houseNumber + "\n" +
                    addressDataModel.addressLine1 + addressDataModel.addressLine2 + "\n" +
                    addressDataModel.landmark + "\n" +
                    addressDataModel.city + "\n" +
                    addressDataModel.pincode
            if (mCheckedPosition == -1) {
                itemView.cb_address.isChecked = false
            } else {
                itemView.cb_address.isChecked = mCheckedPosition == adapterPosition
            }
            itemView.cb_address.setOnClickListener {
                if (mCheckedPosition != adapterPosition) {
                    notifyItemChanged(mCheckedPosition)
                    mCheckedPosition = adapterPosition
                } else {
                    itemView.cb_address.isChecked = true
                }
            }
//            itemView.cb_address.setOnCheckedChangeListener(null);
//            itemView.cb_address.isSelected = addressDataModel.isSelected
//            itemView.cb_address.setOnCheckedChangeListener { compoundButton, isChecked ->
//                if (isChecked) {
//                    addressDataModel.isSelected = true
//                    deleteBookMarkListener.checkAddressSelection(addressDataModel.id, addressDataModel.isSelected)
//                } else {
//                    addressDataModel.isSelected = false
//                    deleteBookMarkListener.checkAddressSelection(addressDataModel.id, addressDataModel.isSelected)
//                }
//            }
//            itemView.cb_address.isChecked = addressDataModel.isSelected
        }
    }

    fun addAddressList(addressDataModelList: ArrayList<AddressDataModel>) {
        mAddressDataModelList.addAll(addressDataModelList)
        notifyDataSetChanged()
    }

    fun addSingleAddress(addressDataModel: AddressDataModel, position: Int) {
        mAddressDataModelList.add(addressDataModel)
        notifyItemInserted(position)
    }

    fun addressItemRemove(position: Int) {
        mAddressDataModelList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount)
    }

    fun notifyData() {
        notifyDataSetChanged()
    }
}