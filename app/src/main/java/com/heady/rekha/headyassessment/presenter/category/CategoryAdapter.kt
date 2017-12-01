package com.heady.rekha.headyassessment.presenter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.heady.rekha.headyassessment.R
import com.heady.rekha.headyassessment.domain.CategoryEntity
import kotlinx.android.synthetic.main.row_category.view.*

/**
 * Created by rekha on 1/12/17.
 */
class CategoryAdapter(private val categories: List<CategoryEntity>, listener: View.OnClickListener) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        return ViewHolder(inflater.inflate(R.layout.row_category, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.bindItems(categories[position])
    }

    open class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        open fun bindItems(categoryEntity: CategoryEntity)  = with(itemView){
            txtCategoryName.text = categoryEntity.name
        }
    }

}