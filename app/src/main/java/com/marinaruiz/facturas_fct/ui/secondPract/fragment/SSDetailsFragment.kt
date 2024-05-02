package com.marinaruiz.facturas_fct.ui.secondPract.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.marinaruiz.facturas_fct.data.repository.model.SmartSolarDetailVO
import com.marinaruiz.facturas_fct.databinding.FragmentSsDetailsBinding
import com.marinaruiz.facturas_fct.ui.secondPract.viewmodel.SmartSolarViewModel

class SSDetailsFragment : Fragment() {
    private val smartSolarVM: SmartSolarViewModel by activityViewModels()
    private lateinit var binding: FragmentSsDetailsBinding

    companion object {
        private const val TAG = "VIEWNEXT SSDetailsFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSsDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        smartSolarVM.details.observe(viewLifecycleOwner) { details ->
            details?.let {
                bindView(it)
            }
        }
    }

    private fun bindView(details: SmartSolarDetailVO) {
        binding.etSsDetailCau.setText(details.cau)
        binding.etSsDetailRequestStatus.setText(details.requestStatus)
        binding.etSsDetailSelfConsumptionTypeLabel.setText(details.selfConsumptionType)
        binding.etSsDetailSurplusCompensation.setText(details.surplusCompensation)
        binding.etSsDetailInstallationPower.setText(details.installationPower)
    }
}