package com.ashish.movieguide.data.interactors

import com.ashish.movieguide.data.api.PeopleApi
import com.ashish.movieguide.data.models.FullDetailContent
import com.ashish.movieguide.data.models.Person
import com.ashish.movieguide.data.models.PersonDetail
import com.ashish.movieguide.data.models.Results
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Ashish on Dec 31.
 */
@Singleton
class PeopleInteractor @Inject constructor(private val peopleApi: PeopleApi) {

    fun getPopularPeople(page: Int = 1): Single<Results<Person>> {
        return peopleApi.getPopularPeople(page)
    }

    fun getFullPersonDetail(personId: Long): Single<FullDetailContent<PersonDetail>> {
        return peopleApi.getPersonDetail(personId, "combined_credits,images")
                .flatMap { Single.just(FullDetailContent(it, null)) }
    }
}