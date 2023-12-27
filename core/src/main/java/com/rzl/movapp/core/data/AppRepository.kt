package com.rzl.movapp.core.data



import com.rzl.movapp.core.data.source.local.LocalDataSource
import com.rzl.movapp.core.data.source.remote.RemoteDataSource
import com.rzl.movapp.core.data.source.remote.network.Result
import com.rzl.movapp.core.data.source.remote.response.PopularResponse
import com.rzl.movapp.core.domain.model.Popular
import com.rzl.movapp.core.domain.repository.IPopularRepository
import com.rzl.movapp.core.utils.AppExecutors
import com.rzl.movapp.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IPopularRepository {

    override fun getPopular(): Flow<Resource<List<Popular>>> =
        object : NetworkBoundResource<List<Popular>, List<PopularResponse>>() {
            override fun loadFromDB(): Flow<List<Popular>> {
                return localDataSource.getAllPopular().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override suspend fun createCall(): Flow<Result<List<PopularResponse>>> =
                remoteDataSource.getAllPopular()


            override suspend fun saveCallResult(data: List<PopularResponse>) {
                val popularList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertPopular(popularList)
            }

            override fun shouldFetch(data: List<Popular>?): Boolean = true

        }.asFlow()



    override fun getFavoritePopular(): Flow<List<Popular>> {
        return localDataSource.getFavoritePopular().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setFavoritePopular(popular: Popular, state: Boolean) {
        val popularEntity = DataMapper.mapDomainToEntity(popular)
        appExecutors.diskIO().execute { localDataSource.setFavoritePopular(popularEntity, state) }
    }



}