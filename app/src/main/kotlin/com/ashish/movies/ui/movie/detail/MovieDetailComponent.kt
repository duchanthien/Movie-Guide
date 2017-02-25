package com.ashish.movies.ui.movie.detail

import com.ashish.movies.di.modules.ActivityModule
import com.ashish.movies.di.multibindings.AbstractComponent
import com.ashish.movies.di.multibindings.activity.ActivityComponentBuilder
import com.ashish.movies.di.scopes.ActivityScope
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = arrayOf(ActivityModule::class))
interface MovieDetailComponent : AbstractComponent<MovieDetailActivity> {

    @Subcomponent.Builder
    interface Builder : ActivityComponentBuilder<MovieDetailActivity, MovieDetailComponent> {
        fun withModule(module: ActivityModule): Builder
    }
}