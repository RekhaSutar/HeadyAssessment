package com.heady.rekha.headyassessment.presenter

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.GridLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.heady.rekha.headyassessment.R
import com.heady.rekha.headyassessment.base.BaseActivity
import com.heady.rekha.headyassessment.data.SORT_ORDER_COUNT
import com.heady.rekha.headyassessment.data.SORT_SHARES
import com.heady.rekha.headyassessment.data.SORT_VIEW_COUNT
import com.heady.rekha.headyassessment.domain.CategoryEntity
import com.heady.rekha.headyassessment.domain.entity.Product
import com.heady.rekha.headyassessment.presenter.base.SpacesItemDecoration
import com.heady.rekha.headyassessment.presenter.category.CategoryFragment
import com.heady.rekha.headyassessment.presenter.products.ARG_PRODUCT
import com.heady.rekha.headyassessment.presenter.products.ProductAdapter
import com.heady.rekha.headyassessment.presenter.products.ProductDetailsActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.row_category.*


class MainActivity : BaseActivity(), MainActivityContract.View, View.OnClickListener {


    fun onCategoryClick(categoryEntity: CategoryEntity) {
        selectedCategory.text = categoryEntity.name
        drawer_layout.closeDrawer(GravityCompat.START)
        progressBar.visibility = View.VISIBLE
        presenter.getProducts(categoryEntity.products!!)
    }

    override fun onClick(v: View) {
        val intent = Intent(this, ProductDetailsActivity::class.java)
        intent.putExtra(ARG_PRODUCT, v.tag as Product)
        startActivity(intent)
    }

    override fun onProductsFetched(products: List<Product>) {
        adapter.products = products
        adapter.notifyDataSetChanged()

        progressBar.visibility = View.GONE
    }

    override fun onError() {
        showMessage(resources.getString(R.string.errorMsg))
    }

    lateinit private var presenter: MainActivityContract.Presenter
    lateinit private var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        supportFragmentManager.beginTransaction()
                .add(R.id.flContainer, CategoryFragment()).commit();

        presenter = MainActivityPresenter(this)
        rvProductList.layoutManager = GridLayoutManager(this, 2)
        adapter = ProductAdapter(this)
        rvProductList.adapter = adapter
        rvProductList.addItemDecoration(SpacesItemDecoration(2))

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detach()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuOrdered -> {
                selectedCategory.text = SORT_ORDER_COUNT
                presenter.getSortedProducts(SORT_ORDER_COUNT)
                true
            }
            R.id.menuShared -> {
                selectedCategory.text = SORT_SHARES
                presenter.getSortedProducts(SORT_SHARES)
                true
            }
            R.id.menuViewed -> {
                selectedCategory.text = SORT_VIEW_COUNT
                presenter.getSortedProducts(SORT_VIEW_COUNT)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
