package com.dakshsemwal.nycschool.presentation.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dakshsemwal.nycschool.data.remote.dto.SchoolListDTOItem;
import com.dakshsemwal.nycschool.databinding.FragmentSchoolBinding;
import com.dakshsemwal.nycschool.presentation.ui.adapters.SchoolAdapter;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends Fragment {

    private SchoolListingViewModel schoolListingViewModel;
    private FragmentSchoolBinding fragmentSchoolBinding;
    //Recycler View Adapter
    private SchoolAdapter schoolAdapter;
    //List to store the data from the api
    private ArrayList<SchoolListDTOItem> schoolListDTOItemArrayList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Registering ViewModel to Lifecycle of the Activity
        schoolListingViewModel = new ViewModelProvider(requireActivity()).get(SchoolListingViewModel.class);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //SetupViewBinding For School Listing Screen
        fragmentSchoolBinding = FragmentSchoolBinding.inflate(inflater, container, false);
        return fragmentSchoolBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //SetUpUiElements
        setupUI();
        //SetUpDataObserver
        setupObservers();
    }

    private void setupObservers() {
        schoolListingViewModel.getState().observe(getViewLifecycleOwner(), schoolState -> {
            assert schoolState != null;
            if (schoolState.isLoading()) {
                //When Data is Loading
                setViewVisibility(View.VISIBLE,View.GONE,View.GONE,View.GONE);
            } else {
                if (!schoolState.getError().isEmpty()) {
                    //When any error occurs during network call
                    setViewVisibility(View.GONE,View.GONE,View.GONE,View.VISIBLE);

                } else {
                    if (schoolState.getSortedSchoolListDTO() == null) {
                        //If Network call is successful but there is no data
                        setViewVisibility(View.GONE,View.GONE,View.VISIBLE,View.GONE);
                    } else {
                        //If Network call is successful and there no data
                        schoolListDTOItemArrayList.addAll(schoolState.getSortedSchoolListDTO());
                        schoolAdapter.submitList(schoolListDTOItemArrayList);
                        setViewVisibility(View.GONE,View.VISIBLE,View.GONE,View.GONE);
                    }
                }
            }
        });
    }
    //Set Visibility of views representing different states of network calls
    private void setViewVisibility(int loader, int recyclerView, int noData, int error) {
        fragmentSchoolBinding.loading.setVisibility(loader);
        fragmentSchoolBinding.recyclerView.setVisibility(recyclerView);
        fragmentSchoolBinding.noData.setVisibility(noData);
        fragmentSchoolBinding.error.setVisibility(error);
    }

    private void setupUI() {
        //Setup Recycler View
        schoolListDTOItemArrayList = new ArrayList<>();
        schoolAdapter = new SchoolAdapter(schoolListDTOItemArrayList);
        fragmentSchoolBinding.recyclerView.setAdapter(schoolAdapter);
        fragmentSchoolBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        schoolAdapter.SetOnItemClickListener((listItem, position, view) -> {
            //Navigate From Home Screen To SAT Result Screen
            HomeFragmentDirections.ActionNavSchoolToNavSatResult actionNavSchoolToNavSatResult =
                    HomeFragmentDirections.actionNavSchoolToNavSatResult(listItem.getDbn());
            Navigation.findNavController(view).navigate(actionNavSchoolToNavSatResult);
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //releasing binding object
        fragmentSchoolBinding = null;
    }
}