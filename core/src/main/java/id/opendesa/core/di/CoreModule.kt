package id.opendesa.core.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.opendesa.core.data.source.remote.network.ApiService
import id.opendesa.core.data.source.remote.network.RemoteDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @Singleton
    @Provides
    fun provideRemoteDataSource(): RemoteDataSource {
        return RemoteDataSource()
    }

    @Singleton
    @Provides
    fun provideRetrofitInstance(remoteDataSource: RemoteDataSource) : ApiService {
        return remoteDataSource.buildApi(ApiService::class.java)
    }
}