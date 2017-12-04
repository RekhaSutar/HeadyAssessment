package com.heady.rekha.headyassessment.presenter.products

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.heady.rekha.headyassessment.R
import com.heady.rekha.headyassessment.domain.entity.Product
import kotlinx.android.synthetic.main.row_product_list.view.*
import java.text.DecimalFormat

/**
 * Created by rekha on 1/12/17.
 */
class ProductAdapter(private val listener: View.OnClickListener) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    var products: List<Product> = arrayListOf()
    override fun getItemCount(): Int {
        return products.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.row_product_list, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(products[position], listener)
    }

    open class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        open fun bindItems(product: Product, listener: View.OnClickListener) = with(itemView) {
            txtProductName.text = product.name
            txtProductPrice.text = formatPrice((product.variants[0].price + product.tax.value).toString())
            productListRow.tag = product
            productListRow.setOnClickListener(listener)
        }
    }
}

fun formatPrice(s: String): String {
    val amount = java.lang.Double.parseDouble(s)
    val formatter = DecimalFormat("#,##,###")
    return formatter.format(amount).prependIndent(indent = "Rs. ")
}
