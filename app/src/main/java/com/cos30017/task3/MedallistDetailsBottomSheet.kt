package com.cos30017.task3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cos30017.task3.databinding.FragmentMedallistDetailsBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MedallistDetailsBottomSheet : BottomSheetDialogFragment() {

    private var binding: FragmentMedallistDetailsBottomSheetBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMedallistDetailsBottomSheetBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val countryName = arguments?.getString("COUNTRY") ?: "N/A"
        val iocCode = arguments?.getString("IOC_CODE") ?: "N/A"
        val timesCompeted = arguments?.getInt("TIMES_COMPETED") ?: 0
        val gold = arguments?.getInt("GOLD") ?: 0
        val silver = arguments?.getInt("SILVER") ?: 0
        val bronze = arguments?.getInt("BRONZE") ?: 0

        binding?.apply {
            textViewCountry.text = countryName
            textViewIocCode.text = "IOC Code: $iocCode"
            textViewTimesCompeted.text = "Times Competed: $timesCompeted"
            textViewGold.text = "Gold: $gold"
            textViewSilver.text = "Silver: $silver"
            textViewBronze.text = "Bronze: $bronze"
        }
    }

    companion object {
        fun newInstance(medallist: Medallist): MedallistDetailsBottomSheet {
            val bottomSheet = MedallistDetailsBottomSheet()
            val args = Bundle().apply {
                putString("COUNTRY", medallist.country)
                putString("IOC_CODE", medallist.iocCode)
                putInt("TIMES_COMPETED", medallist.timesCompeted)
                putInt("GOLD", medallist.gold)
                putInt("SILVER", medallist.silver)
                putInt("BRONZE", medallist.bronze)
            }
            bottomSheet.arguments = args
            return bottomSheet
        }
    }
}
