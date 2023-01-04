package id.opendesa.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.opendesa.core.data.interactors.ComplaintInteractors
import id.opendesa.core.domain.repository.ComplaintRepository
import id.opendesa.core.domain.usecase.ComplaintUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideComplaintUseCase(complaintRepository: ComplaintRepository): ComplaintUseCase {
        return ComplaintInteractors(complaintRepository)
    }
}