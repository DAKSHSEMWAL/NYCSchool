package com.dakshsemwal.nycschool.presentation.ui.sat;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.dakshsemwal.nycschool.R;
import com.dakshsemwal.nycschool.data.remote.dto.SATResultDtoItem;
import com.dakshsemwal.nycschool.databinding.FragmentSatResultsBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SchoolSATResultFragment extends Fragment {

    private SchoolSATViewModel schoolSATViewModel;
    private FragmentSatResultsBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        schoolSATViewModel =
                new ViewModelProvider(this).get(SchoolSATViewModel.class);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //Set  View Binding For School Stat Listing
        binding = FragmentSatResultsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.backPress.setOnClickListener(this::onBackPressed);
        setObserver();
    }

    private void onBackPressed(View view) {
        NavOptions navOptions = new NavOptions.Builder().setPopUpTo(R.id.nav_School, true).build();
        Navigation.findNavController(view).navigate(R.id.action_nav_satResult_to_nav_School,
                null, navOptions);
    }

    private void setObserver() {
        String dbn = SchoolSATResultFragmentArgs.fromBundle(getArguments()).getDbnID();
        Log.e("TEST", dbn);
        schoolSATViewModel.getState().observe(getViewLifecycleOwner(), statScore -> {
            assert statScore != null;
            if (statScore.isLoading()) {
                //When Data is Loading
                setViewVisibility(View.VISIBLE,View.GONE,View.GONE,View.GONE);
            } else {
                if (!statScore.getError().isEmpty()) {
                    //When any error occurs during network call
                    setViewVisibility(View.GONE,View.GONE,View.GONE,View.VISIBLE);
                } else {
                    SATResultDtoItem satResultDtoItem = statScore.getSchoolSat(dbn);
                    if (satResultDtoItem == null) {
                        //If Network call is successful but there is no data
                        setViewVisibility(View.GONE,View.GONE,View.VISIBLE,View.GONE);
                    } else {
                        setData(satResultDtoItem);
                        setViewVisibility(View.GONE,View.VISIBLE,View.GONE,View.GONE);
                    }
                }
            }
        });
    }

    private void setData(SATResultDtoItem satResultDtoItem) {
        binding.schoolName.setText(satResultDtoItem.getSchool_name());
        binding.noOfSatTakenNumber.setText(satResultDtoItem.getNum_of_sat_test_takers());
        binding.readingAvgScoreNumber.setText(satResultDtoItem.getSat_critical_reading_avg_score());
        binding.satMathAvgScore.setText(satResultDtoItem.getSat_math_avg_score());
        binding.satWritingAvgScoreNumber.setText(satResultDtoItem.getSat_math_avg_score());
    }

    //Set Visibility of views representing different states of network calls
    private void setViewVisibility(int loader, int dataView, int noData, int error) {
        binding.loading.setVisibility(loader);
        binding.layout.setVisibility(dataView);
        binding.noData.setVisibility(noData);
        binding.error.setVisibility(error);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}