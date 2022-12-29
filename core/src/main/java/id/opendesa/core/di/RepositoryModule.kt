package id.opendesa.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.opendesa.core.data.repository.ComplaintRepositoryImpl
import id.opendesa.core.data.source.remote.network.ApiService
import id.opendesa.core.domain.repository.ComplaintRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideComplaintRepository(apiService: ApiService): ComplaintRepository {
        return ComplaintRepositoryImpl(apiService)
    }
}