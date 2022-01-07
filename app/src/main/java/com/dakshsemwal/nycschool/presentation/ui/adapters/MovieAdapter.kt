package com.dakshsemwal.nycschool.presentation.ui.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dakshsemwal.nycschool.data.remote.dto.SchoolListDTOItem
import com.dakshsemwal.nycschool.databinding.ItemCardBinding

class SchoolAdapter(
    private val SchoolListDTOItems: ArrayList<SchoolListDTOItem>
) : ListAdapter<SchoolListDTOItem, SchoolAdapter.SchoolListDTOItemViewHolder>(NYCSchoolDC()) {

    private var mItemClickListener: ListItemClickListener? = null

    interface ListItemClickListener {
        fun onItemClick(listItem: SchoolListDTOItem, position: Int ,view:View)
    }

    override fun onBindViewHolder(holder: SchoolListDTOItemViewHolder, position: Int) {
        val SchoolListDTOItem = SchoolListDTOItems[position]
        holder.bind(SchoolListDTOItem, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchoolListDTOItemViewHolder {
        val binding = ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SchoolListDTOItemViewHolder(binding)
    }
    //Create Custom Click Interface for Adapter Item
    fun SetOnItemClickListener(mItemClickListener: ListItemClickListener) {
        this.mItemClickListener = mItemClickListener
    }

    inner class SchoolListDTOItemViewHolder(private val itemSchoolListDTOItemBinding: ItemCardBinding) :
        RecyclerView.ViewHolder(itemSchoolListDTOItemBinding.root) {

        fun bind(schoolListDTOItem: SchoolListDTOItem, position: Int) {
            with(itemSchoolListDTOItemBinding) {
                title.text = schoolListDTOItem.schoolName
                description.text = schoolListDTOItem.overviewParagraph
                schoolSports.text = schoolListDTOItem.schoolSports
                email.text = schoolListDTOItem.schoolEmail
                address.text = "${schoolListDTOItem.primaryAddressLine1}, ${schoolListDTOItem.city}, ${schoolListDTOItem.zip} " +
                        ",${schoolListDTOItem.stateCode}"
                itemSchoolListDTOItemBinding.root.setOnClickListener {
                    mItemClickListener?.onItemClick(schoolListDTOItem, position ,it)
                }
            }
        }
    }

    override fun getItemCount(): Int = SchoolListDTOItems.size

}