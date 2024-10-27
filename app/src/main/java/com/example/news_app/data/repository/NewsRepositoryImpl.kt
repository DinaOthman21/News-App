package com.example.news_app.data.repository


import coil.network.HttpException
import com.example.news_app.data.local.NewsDao
import com.example.news_app.data.mappers.toArticle
import com.example.news_app.data.mappers.toLocalArticle
import com.example.news_app.data.remote.NewsApi
import com.example.news_app.domain.model.Article
import com.example.news_app.domain.repository.NewsRepository
import com.example.news_app.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import okio.IOException
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi ,
    private val newsDao: NewsDao
) : NewsRepository {

    override fun getNews(
        sources:List<String> ,
        page : Int
    ): Flow<Resource<List<Article>>> {
        return flow {
            emit(Resource.Loading(true))

            val articleListFromApiService =try {
                newsApi.getNews(
                    sources = sources.joinToString(separator = ",") ,
                    page = page
                )
            } catch (e:IOException){
                e.printStackTrace()
                emit(Resource.Error(message = "Loading Error" , throwable = e))
                return@flow
            } catch (e : HttpException){
                e.printStackTrace()
                emit(Resource.Error(message = "Loading Error" , throwable = e))
                return@flow
            } catch (e : Exception){
                e.printStackTrace()
                emit(Resource.Error(message = "Loading Error" , throwable = e))
                return@flow
            }
            val articles = articleListFromApiService.articles.let {
                it.map { articleDto ->
                    articleDto.toLocalArticle().toArticle()
                }
            }
            emit(Resource.Success(articles))
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
        val localArticle = article.toLocalArticle()
        newsDao.upsertArticle(localArticle = localArticle)
    }

    override suspend fun deleteArticle(article: Article) {
        val localArticle = article.toLocalArticle()
        newsDao.delete(localArticle = localArticle)
    }

    override fun getArticles(): Flow<List<Article>> {
        return newsDao.getArticles().map { localArticles ->
            localArticles.map { localArticle ->
                localArticle.toArticle()
            }
        }
    }

    override suspend fun getArticle(url: String): Article? {
        val localArticle = newsDao.getArticleByUrl(url = url)
        return localArticle?.toArticle()
    }


    }



