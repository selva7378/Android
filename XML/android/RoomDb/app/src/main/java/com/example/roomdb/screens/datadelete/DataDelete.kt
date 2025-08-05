package com.example.roomdb.screens.datadelete

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.roomdb.R
import com.example.roomdb.database.EmployeeDatabase
import com.example.roomdb.databinding.FragmentDataDeleteBinding
import com.example.roomdb.viewmodel.CommonViewModel
import com.example.roomdb.viewmodel.CommonViewModelFactory


class DataDelete : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentDataDeleteBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_data_delete, container, false
        )


        val application = requireNotNull(this.activity).application
        val dataSource = EmployeeDatabase.getInstance(application).employeeDatabaseDao

        val viewModelFactory = CommonViewModelFactory(dataSource, application)

        val viewModel = ViewModelProvider(this, viewModelFactory)
            .get(CommonViewModel::class.java)

        binding.dataDeleteViewModel = viewModel
        binding.lifecycleOwner = this

        binding.buttonDelete.setOnClickListener{
            viewModel.onDelete()
        }
        binding.buttonBack.setOnClickListener{view ->
            Navigation.findNavController(view).navigate(R.id.action_dataDelete_to_dataAdd)
        }

        return binding.root
    }


}