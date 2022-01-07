package com.dakshsemwal.nycschool.presentation.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import com.dakshsemwal.nycschool.data.remote.dto.SchoolListDTOItem

class NYCSchoolDC : DiffUtil.ItemCallback<SchoolListDTOItem>() {
        override fun areItemsTheSame(
            oldItem: SchoolListDTOItem,
            newItem: SchoolListDTOItem
        ): Boolean = oldItem.dbn == newItem.dbn

        override fun areContentsTheSame(
            oldItem: SchoolListDTOItem,
            newItem: SchoolListDTOItem
        ): Boolean = oldItem == newItem
    }