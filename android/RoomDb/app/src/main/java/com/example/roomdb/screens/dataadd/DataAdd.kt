package com.example.roomdb.screens.dataadd

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
import com.example.roomdb.databinding.FragmentDataAddBinding
import com.example.roomdb.viewmodel.CommonViewModel
import com.example.roomdb.viewmodel.CommonViewModelFactory

class DataAdd : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentDataAddBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_data_add, container, false
        )

        val application = requireNotNull(this.activity).application
        val dataSource = EmployeeDatabase.getInstance(application).employeeDatabaseDao

        val viewModelFactory = CommonViewModelFactory(dataSource, application)

        val viewModel = ViewModelProvider(this, viewModelFactory)
            .get(CommonViewModel::class.java)

        binding.dataAddViewModel = viewModel
        binding.lifecycleOwner = this

        binding.buttonGo.setOnClickListener{view ->
            viewModel.onGo(binding.editTextName.text.toString(), binding.editTextTextEmail.text.toString(),
                binding.editTextPhone.text.toString(),
                binding.editTextOccupation.text.toString(), binding.editTextTextMultiLine.text.toString())
            Navigation.findNavController(view).navigate(R.id.action_dataAdd_to_dataDelete)
        }
        return binding.root
    }

}