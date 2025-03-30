package com.example.airhop.data.repositoryImpl

import com.example.airhop.data.FavoriteDao
import com.example.airhop.data.model.Favorite
import com.example.airhop.data.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow

class OfflineFavoritesRepository(
    private val favoriteDao: FavoriteDao
) : FavoritesRepository {

    override suspend fun insertFlight(favorite: Favorite) {
        favoriteDao.insert(favorite)
    }

    override suspend fun deleteFlight(favorite: Favorite) {
        favoriteDao.delete(favorite)
    }

    override fun getFavoriteFlights(): Flow<List<Favorite>> {
        return favoriteDao.getFavorites()
    }
}