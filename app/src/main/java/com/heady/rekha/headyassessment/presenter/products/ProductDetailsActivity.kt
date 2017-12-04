package com.heady.rekha.headyassessment.presenter.products

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.heady.rekha.headyassessment.BuildConfig
import com.heady.rekha.headyassessment.R
import com.heady.rekha.headyassessment.base.BaseActivity
import com.heady.rekha.headyassessment.domain.entity.Product
import com.heady.rekha.headyassessment.domain.entity.Variant
import com.heady.rekha.headyassessment.presenter.base.SpacesItemDecoration
import kotlinx.android.synthetic.main.activity_product_details.*
import kotlinx.android.synthetic.main.row_color.view.*
import kotlinx.android.synthetic.main.row_product_list.*
import kotlinx.android.synthetic.main.row_size.view.*

const val ARG_PRODUCT = "arg_product"

class ProductDetailsActivity : BaseActivity(), View.OnClickListener, InterCommunicator {
    override fun getSelectedColor(): Variant? {
        return selectedColor
    }

    override fun getSelectedSize(): String? {
        return selectedSize
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.txtSize -> {
                onSizeSelection(v.tag as String)
            }
            R.id.ivColor -> {
                onColorSelection(v.tag as Variant)
            }
        }
    }

    private lateinit var product: Product
    private var selectedSize: String? = null
    private var selectedColor: Variant? = null
    private lateinit var sizeAdapter: SizeAdapter
    private lateinit var colorAdapter: ColorAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        if (intent != null) {
            product = intent.getSerializableExtra(ARG_PRODUCT) as Product
        } else {
            if (BuildConfig.DEBUG) {
                throw IllegalArgumentException("arg_product is missing")
            }
        }

        title = product.name
        txtProductName.text = product.name
        txtTax.text = "Tax : " + formatPrice(product.tax.value.toString())

        var sizeList = arrayListOf<String>()
        product.variants.map {
            if (!sizeList.contains(it.size)) {
                if (it.size != null)
                sizeList.add(it.size)
            }
        }
        if (sizeList.size > 0) {
            sizeAdapter = SizeAdapter(sizeList, this, this)
            rvSize.adapter = sizeAdapter
            rvSize.addItemDecoration(SpacesItemDecoration(5))
            rvSize.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            if (sizeList.size > 0) {
                onSizeSelection(sizeList[0])
            }
        }else{

            sizeTitle.visibility = View.GONE
            var colorList = arrayListOf<Variant>();
            product.variants.map { colorList.add(it) }
            rvColor.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            colorAdapter = ColorAdapter(colorList, this, this)
            rvColor.adapter = colorAdapter
            onColorSelection(colorList[0])
        }
        rvColor.addItemDecoration(SpacesItemDecoration(5))
    }

    private fun onSizeSelection(size: String) {
        selectedSize = size
        sizeAdapter.notifyDataSetChanged()
        var colorList = arrayListOf<Variant>()
        product.variants.map {
            if (TextUtils.equals(it.size, selectedSize)) {
                colorList.add(it)
            }
        }
        rvColor.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        colorAdapter = ColorAdapter(colorList, this, this)
        rvColor.adapter = colorAdapter
        onColorSelection(colorList[0])
    }

    private fun onColorSelection(variant: Variant) {
        selectedColor = variant
        colorAdapter.notifyDataSetChanged()
        txtProductPrice.text = formatPrice(variant.price.toString())
        txtTotal.text = "Total Price : " + formatPrice((variant!!.price + product.tax.value).toString())
        rvColor.invalidate()
    }
}

class ColorAdapter(private val colorList: ArrayList<Variant>, private val listener: View.OnClickListener, private val interCommunicator: InterCommunicator)
    : RecyclerView.Adapter<ColorAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(colorList[position], listener, interCommunicator)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.row_color, parent, false))
    }

    override fun getItemCount(): Int {
        return colorList.size
    }

    open class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        open fun bindItems(variant: Variant, listener: View.OnClickListener, interCommunicator: InterCommunicator) = with(itemView) {
            ivColor.setBackgroundColor(context.resources.getColor(getColor(variant.color)))
            ivColor.tag = variant
            if (TextUtils.equals(interCommunicator.getSelectedColor()!!.color, variant.color)) {
                colorParent.setBackgroundColor(context.resources.getColor(R.color.black))
            } else {
                colorParent.setBackgroundColor(context.resources.getColor(R.color.white))
            }
            ivColor.setOnClickListener(listener)
        }
    }
}

interface InterCommunicator {
    fun getSelectedSize(): String?
    fun getSelectedColor(): Variant?
}

fun getColor(color: String): Int {
    return when (color) {
        "Blue" -> R.color.blue
        "Light Blue" -> R.color.light_blue
        "Red" -> R.color.red
        "White" -> R.color.white
        "Black" -> R.color.black
        "Yellow" -> R.color.yellow
        "Grey" -> R.color.grey
        "Green" -> R.color.green
        "Brown" -> R.color.brown
        "Silver" -> R.color.silver
        "Golden" -> R.color.golden
        else -> {
            0
        }
    }
}

class SizeAdapter(private val sizeList: ArrayList<String>, private val listener: View.OnClickListener, private val interCommunicator: InterCommunicator) : RecyclerView.Adapter<SizeAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(sizeList[position], listener, interCommunicator)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.row_size, parent, false))
    }

    override fun getItemCount(): Int {
        return sizeList.size
    }

    open class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        open fun bindItems(size: String, listener: View.OnClickListener, interCommunicator: InterCommunicator) = with(itemView) {
            txtSize.text = size
            txtSize.tag = size
            if (TextUtils.equals(interCommunicator.getSelectedSize(), size)) {
                sizeParent.setBackgroundColor(context.resources.getColor(R.color.black))
            } else {
                sizeParent.setBackgroundColor(context.resources.getColor(R.color.white))
            }
            txtSize.setOnClickListener(listener)
        }
    }
}
