package com.ashish.movies.ui.people.list

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.ashish.movies.R
import com.ashish.movies.data.models.Person
import com.ashish.movies.di.modules.FragmentModule
import com.ashish.movies.di.multibindings.fragment.FragmentComponentBuilderHost
import com.ashish.movies.ui.base.recyclerview.BaseRecyclerViewFragment
import com.ashish.movies.ui.base.recyclerview.BaseRecyclerViewMvpView
import com.ashish.movies.ui.people.detail.PersonDetailActivity
import com.ashish.movies.utils.Constants.ADAPTER_TYPE_PERSON

/**
 * Created by Ashish on Dec 31.
 */
class PeopleFragment : BaseRecyclerViewFragment<Person, BaseRecyclerViewMvpView<Person>, PeoplePresenter>() {

    companion object {
        fun newInstance() = PeopleFragment()
    }

    override fun injectDependencies(builderHost: FragmentComponentBuilderHost) {
        builderHost.getFragmentComponentBuilder(PeopleFragment::class.java, PeopleComponent.Builder::class.java)
                .withModule(FragmentModule(activity))
                .build()
                .inject(this)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        emptyTextView.setText(R.string.no_people_available)
        emptyImageView.setImageResource(R.drawable.ic_people_white_100dp)
    }

    override fun getAdapterType() = ADAPTER_TYPE_PERSON

    override fun getTransitionNameId(position: Int) = R.string.transition_person_profile

    override fun getDetailIntent(position: Int): Intent? {
        val people = recyclerViewAdapter.getItem<Person>(position)
        return PersonDetailActivity.createIntent(activity, people)
    }
}