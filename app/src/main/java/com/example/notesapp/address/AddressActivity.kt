package com.example.notesapp.address

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notesapp.R
import com.example.notesapp.base.BaseActivity
import com.example.notesapp.dashboard.DashboardClickListener
import com.example.notesapp.database.dbmodel.AddressDBModel
import com.example.notesapp.databinding.ActivityAddressBinding
import com.example.notesapp.model.AddressDataModel
import kotlinx.android.synthetic.main.activity_address.*
import kotlinx.coroutines.launch

class AddressActivity : BaseActivity(), DashboardClickListener {

    private lateinit var mAddressViewModel: AddressViewModel
    private lateinit var mAddressBinding: ActivityAddressBinding
    private var mAddressDataModelList: ArrayList<AddressDataModel> = arrayListOf()
    private lateinit var mAdapter: AddressAdapter
    private var isUpdated: Boolean = false

    override fun init() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    override fun bookmarkCheckBoxClicked(isCheck: Boolean, id: String) {
    }

    override fun deleteBookMark(id: String, position: Int) {
    }

    override fun chooseOptionListener(id: String, position: Int) {
    }

    override fun checkAddressSelection(id: Int, isSelected: Boolean) {
        lifecycleScope.launch {
            mAddressViewModel.updateAddressSelection(id, isSelected)
            isUpdated = true
        }
    }

    private fun initView() {
        mAddressBinding = DataBindingUtil.setContentView(this, R.layout.activity_address)
        mAddressBinding.clickHandler = this
        mAddressViewModel = ViewModelProviders.of(this).get(AddressViewModel::class.java)
        mAddressBinding.addressViewModel = mAddressViewModel
        mAddressViewModel.getAddress().observe(this, Observer<List<AddressDBModel>> {
            if (!isUpdated) {
                it.mapTo(mAddressDataModelList) { t ->
                    AddressDataModel(
                        t.id,
                        t.houseNumber,
                        t.addressLine1,
                        t.addressLine2,
                        t.city,
                        t.pincode
                    )
                }
                setUpAdapter()
                mAdapter.apply {
                    addAddressList(mAddressDataModelList)
                }
                isUpdated = false
                if (mAddressDataModelList.size > 0) {
                    tv_delivery_address.visibility = View.VISIBLE
                    rv_address.visibility = View.VISIBLE
                } else {
                    tv_delivery_address.visibility = View.GONE
                    rv_address.visibility = View.GONE
                }
            } else {
                mAdapter.notifyData()
            }
        })
    }

    private fun setUpAdapter() {
        val layoutManager = LinearLayoutManager(this)
        rv_address!!.layoutManager = layoutManager
        mAdapter =
            AddressAdapter(this, this)

        rv_address.apply {
            setHasFixedSize(true)
            adapter = mAdapter
        }
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.bt_save_address -> {
                lifecycleScope.launch {
                    val saveAddress = mAddressViewModel.saveAddress()
                    if (saveAddress > 0) {
                        mAddressDataModelList.clear()
                        tv_new_delivery_address.visibility = View.GONE
                        et_house_number.visibility = View.GONE
                        et_address_line_1.visibility = View.GONE
                        et_address_line_2.visibility = View.GONE
                        et_landmark.visibility = View.GONE
                        et_city.visibility = View.GONE
                        et_pin_code.visibility = View.GONE
                        bt_save_address.visibility = View.GONE
                        rv_address.visibility = View.VISIBLE
                        tv_delivery_address.visibility = View.VISIBLE
                        tv_add_address_click.visibility = View.VISIBLE
                    }
                }
            }
            R.id.tv_add_address_click -> {
                et_house_number.text.clear()
                et_address_line_1.text.clear()
                et_address_line_2.text.clear()
                et_landmark.text.clear()
                et_city.text.clear()
                et_pin_code.text.clear()
                tv_new_delivery_address.visibility = View.VISIBLE
                et_house_number.visibility = View.VISIBLE
                et_address_line_1.visibility = View.VISIBLE
                et_address_line_2.visibility = View.VISIBLE
                et_landmark.visibility = View.VISIBLE
                et_city.visibility = View.VISIBLE
                et_pin_code.visibility = View.VISIBLE
                bt_save_address.visibility = View.VISIBLE
                tv_add_address_click.visibility = View.GONE
            }
        }
    }
}