package com.ashish.movies.ui.tvshow.episode

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.ashish.movies.R
import com.ashish.movies.data.models.Episode
import com.ashish.movies.data.models.EpisodeDetail
import com.ashish.movies.di.components.AppComponent
import com.ashish.movies.ui.base.detail.BaseDetailMvpView
import com.ashish.movies.ui.base.detail.FullDetailContentActivity
import com.ashish.movies.ui.common.adapter.RecyclerViewAdapter.Companion.ADAPTER_TYPE_EPISODE
import com.ashish.movies.utils.extensions.getOriginalImageUrl
import com.ashish.movies.utils.extensions.setTitleAndYear
import com.ashish.movies.utils.extensions.setTransitionName

/**
 * Created by Ashish on Jan 08.
 */
class EpisodeDetailActivity : FullDetailContentActivity<EpisodeDetail, BaseDetailMvpView<EpisodeDetail>, EpisodeDetailPresenter>() {

    private var tvShowId: Long? = null
    private var episode: Episode? = null

    companion object {
        private const val EXTRA_EPISODE = "episode"
        private const val EXTRA_TV_SHOW_ID = "tv_show_id"

        fun createIntent(context: Context, tvShowId: Long?, episode: Episode?): Intent {
            return Intent(context, EpisodeDetailActivity::class.java)
                    .putExtra(EXTRA_TV_SHOW_ID, tvShowId)
                    .putExtra(EXTRA_EPISODE, episode)
        }
    }

    override fun injectDependencies(appComponent: AppComponent) {
        appComponent.plus(EpisodeDetailModule()).inject(this)
    }

    override fun getLayoutId() = R.layout.activity_detail_episode

    override fun getIntentExtras(extras: Bundle?) {
        tvShowId = extras?.getLong(EXTRA_TV_SHOW_ID)
        episode = extras?.getParcelable(EXTRA_EPISODE)
        posterImage.setTransitionName(R.string.transition_episode_image)
    }

    override fun loadDetailContent() {
        presenter.setSeasonAndEpisodeNumber(episode?.seasonNumber!!, episode?.episodeNumber!!)
        presenter.loadDetailContent(tvShowId)
    }

    override fun getBackdropPath() = episode?.stillPath.getOriginalImageUrl()

    override fun getPosterPath() = getBackdropPath()

    override fun showDetailContent(detailContent: EpisodeDetail) {
        detailContent.apply {
            setTMDbRating(detailContent.voteAverage)
            titleText.setTitleAndYear(name, airDate)
            imdbId = detailContent.externalIds?.imdbId
        }
        super.showDetailContent(detailContent)
    }

    override fun getDetailContentType() = ADAPTER_TYPE_EPISODE

    override fun getItemTitle() = episode?.name ?: ""
}