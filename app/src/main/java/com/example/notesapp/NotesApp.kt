package com.example.notesapp

import android.app.Application
import com.example.notesapp.database.RoomDBHandler
import com.example.notesapp.network.ApiInf
import com.example.notesapp.network.RetrofitApiHandler
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class NotesApp : Application() {

    override fun onCreate() {
        super.onCreate()
        api = RetrofitApiHandler(this).create()
        roomDatabaseHandler = RoomDBHandler(this)
        instance = this
//        CalligraphyConfig.initDefault(
//            CalligraphyConfig.Builder()
//                .setDefaultFontPath("fonts/segoe_ui.ttf")
//                .setFontAttrId(R.attr.fontPath)
//                .build()
//        )
//        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    companion object {
        private var api: ApiInf? = null
        private var mScheduler: Scheduler? = null
        private var instance: NotesApp? = null

        fun getApiService(): ApiInf? {
            return api!!
        }

        //
        fun subscribeScheduler(): Scheduler {
            if (mScheduler == null) {
                mScheduler = Schedulers.io()
            }

            return mScheduler!!
        }

        //
        fun setScheduler(scheduler: Scheduler) {
            this.mScheduler = scheduler
        }


        private var roomDatabaseHandler: RoomDBHandler? = null

        fun getDataHandler(): RoomDBHandler? {
            return roomDatabaseHandler
        }

        fun getInstance(): NotesApp {
            return instance!!
        }
    }
//
//    fun clearApplicationData() {
//        val cache = cacheDir
//        val appDir = File(cache.parent)
//        if (appDir.exists()) {
//            val children = appDir.list()
//            for (s in children) {
//                if (s != "lib") {
//                    deleteDir(File(appDir, s))
//                    Log.i("TAG", "File /data/data/APP_PACKAGE/$s DELETED ")
//                }
//            }
//        }
//    }
//
//    private fun deleteDir(dir: File?): Boolean {
//        if (dir != null && dir.isDirectory) {
//            val children = dir.list()
//            for (i in children.indices) {
//                val success = deleteDir(File(dir, children[i]))
//                if (!success) {
//                    return false
//                }
//            }
//        }
//        return dir!!.delete()
//    }
}