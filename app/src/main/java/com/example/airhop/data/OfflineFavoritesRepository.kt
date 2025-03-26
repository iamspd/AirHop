package com.example.airhop.data

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