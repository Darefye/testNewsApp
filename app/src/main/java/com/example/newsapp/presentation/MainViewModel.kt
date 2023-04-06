package com.example.newsapp.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.api.NewsRepository
import com.example.newsapp.models.NewsResponse
import com.example.newsapp.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: NewsRepository) : ViewModel() {

    val newsLiveData: MutableLiveData<State<NewsResponse>> = MutableLiveData()
    val newsPage = 1

    val searchNewsLiveData: MutableLiveData<State<NewsResponse>> = MutableLiveData()
    var searchNewsPage = 1

    init {
        getNews("us")
        getSearchNews("")

    }

    private fun getNews(countryCode: String) = viewModelScope.launch {
        newsLiveData.postValue(State.Loading())
        val response = repository.getNews(countryCode = countryCode, pageNumber = newsPage)
        if (response.isSuccessful) {
            response.body().let {
                newsLiveData.postValue(State.Success(it))
            }
        } else {
            newsLiveData.postValue(State.Error(response.message()))
        }
    }

    fun getSearchNews(query: String) =
        viewModelScope.launch {
            searchNewsLiveData.postValue(State.Loading())
            val response = repository.getSearchNews(query = query, pageNumber = searchNewsPage)
            if (response.isSuccessful) {
                response.body().let { res ->
                    searchNewsLiveData.postValue(State.Success(res))
                }
            } else {
                searchNewsLiveData.postValue(State.Error(message = response.message()))
            }
        }
}