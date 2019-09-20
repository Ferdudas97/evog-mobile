package org.agh.pracinz.evog.di.module

import dagger.Module
import dagger.Provides
import dagger.Reusable
import io.reactivex.schedulers.Schedulers
import org.agh.pracinz.evog.di.manual.BASE_URL
import org.agh.pracinz.evog.model.remote.UserService
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

//const val BASE_URL = "http://192.168.0.105:8080/"

@Module
object NetworkModule {


    @Provides
    @Reusable
    @JvmStatic
    internal fun providesUserService(retrofit: Retrofit) : UserService {
        return retrofit.create(UserService::class.java)
    }

    @Provides
    @Reusable
    @JvmStatic
    internal fun providesRetrofit() = Retrofit.Builder()
        .baseUrl(BASE_URL)
//        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .build()
}