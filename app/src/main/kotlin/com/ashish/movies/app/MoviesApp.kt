package com.ashish.movies.app

import android.app.Application
import android.content.Context
import com.ashish.movies.BuildConfig
import com.ashish.movies.di.components.AppComponent
import com.ashish.movies.di.components.DaggerAppComponent
import com.ashish.movies.di.modules.AppModule
import com.ashish.movies.utils.ReleaseTree
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import timber.log.Timber

/**
 * Created by Ashish on Dec 28.
 */
class MoviesApp : Application() {

    companion object {

        @JvmStatic lateinit var context: Context

        @JvmStatic
        fun getRefWatcher(context: Context) = (context.applicationContext as MoviesApp).refWatcher

        @JvmStatic
        fun getAppComponent(context: Context) = (context.applicationContext as MoviesApp).appComponent
    }

    private lateinit var refWatcher: RefWatcher

    private val appComponent: AppComponent by lazy(LazyThreadSafetyMode.NONE) {
        DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) return
        refWatcher = LeakCanary.install(this)

        context = this
        initTimber()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                override fun createStackElementTag(element: StackTraceElement): String {
                    return super.createStackElementTag(element) + ":" + element.lineNumber
                }
            })
        } else {
            Timber.plant(ReleaseTree())
        }
    }
}