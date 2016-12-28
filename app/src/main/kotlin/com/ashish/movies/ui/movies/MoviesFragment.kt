package com.ashish.movies.ui.movies

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.ashish.movies.R
import com.ashish.movies.di.components.AppComponent
import com.ashish.movies.ui.base.common.BaseFragment
import kotlinx.android.synthetic.main.fragment_movies.*
import kotlinx.android.synthetic.main.layout_empty_view.*

/**
 * Created by Ashish on Dec 26.
 */
class MoviesFragment : BaseFragment<MoviesPresenter>(), MoviesMvpView, SwipeRefreshLayout.OnRefreshListener {

    private var moviesAdapter: MoviesAdapter? = null

    companion object {

        val ARG_MOVIE_TYPE = "movie_type"

        val NOW_PLAYING_MOVIES = 0
        val POPULAR_MOVIES = 1
        val TOP_RATED_MOVIES = 2
        val UPCOMING_MOVIES = 3

        fun newInstance(movieType: Int): MoviesFragment {
            val extras = Bundle()
            extras.putInt(ARG_MOVIE_TYPE, movieType)
            val fragment = MoviesFragment()
            fragment.arguments = extras
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun injectDependencies(appComponent: AppComponent) {
        appComponent.plus(MoviesModule()).inject(this)
    }

    override fun getLayoutId() = R.layout.fragment_movies

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.setHasFixedSize(true)
        recyclerView.emptyView = emptyView
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        moviesAdapter = MoviesAdapter()
        recyclerView.adapter = moviesAdapter

        swipeRefresh.setSwipeableViews(emptyView)
        swipeRefresh.setOnRefreshListener(this)
    }

    override fun onRefresh() {

    }
}