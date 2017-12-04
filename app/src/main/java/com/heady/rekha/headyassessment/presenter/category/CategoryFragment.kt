package com.heady.rekha.headyassessment.presenter.category


import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.heady.rekha.headyassessment.R
import com.heady.rekha.headyassessment.domain.CategoryEntity
import com.heady.rekha.headyassessment.presenter.MainActivity
import kotlinx.android.synthetic.main.fragment_category.*


/**
 * A simple [Fragment] subclass.
 * Use the [CategoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CategoryFragment : Fragment(), CategoryContract.View, View.OnClickListener {
    override fun onClick(v: View?) {
        activity.onCategoryClick(v?.tag as CategoryEntity)
    }

    override fun onCategoriesFetched(categories: List<CategoryEntity>) {
        rvCategoryList.adapter = CategoryAdapter(categories, this)
        activity.onCategoryClick(categories[0])
    }

    override fun onError() {
        activity.showMessage(resources.getString(R.string.errorMsg))
    }

    lateinit private var activity: MainActivity
    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        if (activity is MainActivity)
            this.activity = activity
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_category, container, false)
    }

private lateinit var presenter: CategoryPresenter
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rvCategoryList.layoutManager = LinearLayoutManager(activity)
        presenter = CategoryPresenter(this)
        presenter.getCategories()
    }

    override fun onDetach() {
        super.onDetach()
        presenter.detach()
    }

}
