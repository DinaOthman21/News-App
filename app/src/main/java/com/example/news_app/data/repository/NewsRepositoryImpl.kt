package com.example.news_app.data.repository


import coil.network.HttpException
import com.example.news_app.data.local.NewsDatabase
import com.example.news_app.data.mappers.toArticle
import com.example.news_app.data.mappers.toLocalArticle
import com.example.news_app.data.remote.NewsApi
import com.example.news_app.domain.model.Article
import com.example.news_app.domain.repository.NewsRepository
import com.example.news_app.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi ,
    private val newsDatabase: NewsDatabase
) : NewsRepository {

    override fun getNews(
        forceFetchFromRemote: Boolean,
        sources:List<String> ,
        page : Int
    ): Flow<Resource<List<Article>>> {
        return flow {
            emit(Resource.Loading(true))
            val localArticleList = newsDatabase.newsDao.getArticles()

            if (localArticleList.isNotEmpty() && ! forceFetchFromRemote){
                emit(Resource.Success(
                    data = localArticleList.map { localArticle ->
                        localArticle.toArticle()
                    }
                ))

                emit(Resource.Loading(false))
                return@flow
            }

            val articleListFromApiService =try {
                newsApi.getNews(
                    sources = sources.joinToString(separator = ",") ,
                    page = page
                )
            } catch (e:IOException){
                e.printStackTrace()
                emit(Resource.Error(message = "Loading Error"))
                return@flow
            } catch (e : HttpException){
                e.printStackTrace()
                emit(Resource.Error(message = "Loading Error"))
                return@flow
            } catch (e : Exception){
                e.printStackTrace()
                emit(Resource.Error(message = "Loading Error"))
                return@flow
            }
            val localArticles = articleListFromApiService.articles.let {
                it.map { articleDto ->
                    articleDto.toLocalArticle()
                }
            }
            newsDatabase.newsDao.upsertArticleList(localArticles)
            emit(Resource.Success(
                localArticles.map { it.toArticle() }
            ))
            emit(Resource.Loading(false))
        }
    }

    override suspend fun searchNews(
        searchQuery: String,
        sources: List<String>,
        page: Int
    ): Flow<Resource<List<Article>>> {
        return flow {
            emit(Resource.Loading(true))
            val articleListFromApiService =try {
                newsApi.searchNews(
                    searchQuery = searchQuery,
                    sources = sources.joinToString(separator = ",") ,
                    page = page
                )
            } catch (e:IOException){
                e.printStackTrace()
                emit(Resource.Error(message = "Loading Error"))
                return@flow
            } catch (e : HttpException){
                e.printStackTrace()
                emit(Resource.Error(message = "Loading Error"))
                return@flow
            } catch (e : Exception){
                e.printStackTrace()
                emit(Resource.Error(message = "Loading Error"))
                return@flow
            }
            val articles = articleListFromApiService.articles.map { articleDto ->
                articleDto.toLocalArticle().toArticle()
            }
            emit(Resource.Success(articles))
            emit(Resource.Loading(false))
        }
    }


    override suspend fun upsertArticle(article: Article) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteArticle(article: Article) {
        TODO("Not yet implemented")
    }

    override fun getArticles(): Flow<List<Article>> {
        TODO("Not yet implemented")
    }

    override suspend fun getArticle(url: String): Flow<Resource<Article>> {
        return flow {
            emit(Resource.Loading(true))
            val localArticle = newsDatabase.newsDao.getArticleByUrl(url = url)
            if (localArticle != null) {
                emit(Resource.Success(localArticle.toArticle()))
                emit(Resource.Loading(false))
                return@flow
            }
            emit(Resource.Error(message = "No Article"))
            emit(Resource.Loading(false))
            }
        }
    }



