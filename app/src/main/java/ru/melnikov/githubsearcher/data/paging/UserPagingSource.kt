package ru.melnikov.githubsearcher.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import ru.melnikov.githubsearcher.data.remote.GitHubApi
import ru.melnikov.githubsearcher.data.model.toUser
import ru.melnikov.githubsearcher.domain.model.User
import ru.melnikov.githubsearcher.utils.Constants.PAGING_FIRST_PAGE
import ru.melnikov.githubsearcher.utils.Constants.PAGING_SIZE
import java.io.IOException

class UserPagingSource(
    private val api: GitHubApi,
    private val query: String
) : PagingSource<Int, User>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        return try {
            val pageNumber = params.key ?: PAGING_FIRST_PAGE
            val result = api.searchUser(query, PAGING_SIZE, pageNumber)
            LoadResult.Page(
                data = result.items.map {
                    val followers = api.getFollowers(it.followersUrl).size
                    it.toUser(followers)
                },
                prevKey = if (pageNumber > PAGING_FIRST_PAGE) pageNumber - 1 else null,
                nextKey = if (result.items.isNotEmpty()) pageNumber + 1 else null
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.inc() ?: anchorPage?.nextKey?.dec()
        }

}
