package com.example.ui_character_list.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.character_domain.Character
import com.example.character_interactors.GetCharacters
import com.example.core.DataState

class GetCharactersPagingSource(
    private val getCharactersUsecase: GetCharacters
) : PagingSource<Int, Character>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        return try {
            val currentLoadingPageKey = params.key ?: 1
            val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1

            var dataState: DataState<List<Character>>? = null
            getCharactersUsecase(page = currentLoadingPageKey).collect { data: DataState<List<Character>> ->
                dataState = data
            }

            LoadResult.Page(
                data = dataState?.data ?: throw Exception("Couldn't get characters on page: $currentLoadingPageKey"),
                prevKey = prevKey,
                nextKey = currentLoadingPageKey.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}