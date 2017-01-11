package com.ashish.movies.ui.base.detail

import com.ashish.movies.R
import com.ashish.movies.data.models.CreditResults
import com.ashish.movies.data.models.FullDetailContent
import com.ashish.movies.ui.base.mvp.RxPresenter
import com.ashish.movies.utils.Utils
import io.reactivex.Observable
import timber.log.Timber
import java.io.IOException
import java.util.*

/**
 * Created by Ashish on Jan 03.
 */
abstract class BaseDetailPresenter<I, V : BaseDetailMvpView<I>> : RxPresenter<V>() {

    protected var contentList: ArrayList<String> = ArrayList()

    open fun loadDetailContent(id: Long?) {
        if (Utils.isOnline()) {
            if (id != null) {
                getView()?.showProgress()
                addDisposable(getDetailContent(id)
                        .subscribe({ showDetailContent(it) }, { onLoadDetailError(it, getErrorMessageId()) }))
            }
        } else {
            getView()?.apply {
                showToastMessage(R.string.error_no_internet)
                finishActivity()
            }
        }
    }

    abstract fun getDetailContent(id: Long): Observable<FullDetailContent<I>>

    protected open fun showDetailContent(fullDetailContent: FullDetailContent<I>) {
        getView()?.apply {
            hideProgress()
            addContents(fullDetailContent)
            showDetailContentList(contentList)

            val detailContent = fullDetailContent.detailContent
            if (detailContent != null) showDetailContent(detailContent)
            showCredits(getCredits(detailContent))

            val omdbDetail = fullDetailContent.omdbDetail
            if (omdbDetail != null) showOMDbDetail(omdbDetail)
        }
    }

    abstract fun addContents(fullDetailContent: FullDetailContent<I>)

    abstract fun getCredits(detailContent: I?): CreditResults?

    protected fun showCredits(creditResults: CreditResults?) {
        getView()?.apply {
            showItemList(creditResults?.cast) { showCastList(it) }
            showItemList(creditResults?.crew) { showCrewList(it) }
        }
    }

    protected fun onLoadDetailError(t: Throwable, messageId: Int) {
        Timber.e(t)
        getView()?.apply {
            showErrorToast(t, messageId)
            finishActivity()
        }
    }

    abstract fun getErrorMessageId(): Int

    protected fun showErrorToast(t: Throwable, messageId: Int) {
        getView()?.apply {
            if (t is IOException) {
                showToastMessage(R.string.error_no_internet)
            } else {
                showToastMessage(messageId)
            }
        }
    }

    protected fun <T> showItemList(itemList: List<T>?, showData: (List<T>) -> Unit) {
        if (itemList != null && itemList.isNotEmpty()) showData(itemList)
    }
}