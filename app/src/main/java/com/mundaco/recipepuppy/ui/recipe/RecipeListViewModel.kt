package com.mundaco.recipepuppy.ui.recipe

import android.arch.lifecycle.MutableLiveData
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.View
import com.mundaco.recipepuppy.R
import com.mundaco.recipepuppy.base.BaseViewModel
import com.mundaco.recipepuppy.data.RecipeRepository
import com.mundaco.recipepuppy.data.model.Recipe
import com.mundaco.recipepuppy.data.model.RecipeRequest
import com.mundaco.recipepuppy.domain.ListItemClickedUseCase
import com.mundaco.recipepuppy.domain.ListScrolledToBottomUseCase
import com.mundaco.recipepuppy.domain.RetryButtonClickedUseCase
import com.mundaco.recipepuppy.domain.SearchTextChangedUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class RecipeListViewModel(private val recipeRepository: RecipeRepository):
    BaseViewModel(),
    SearchTextChangedUseCase,
    ListItemClickedUseCase,
    ListScrolledToBottomUseCase,
    RetryButtonClickedUseCase
{
    private lateinit var subscription: Disposable

    private var recipeRequest = RecipeRequest()

    val recipeList: MutableLiveData<List<Recipe>> = MutableLiveData()

    val scrollPosition: MutableLiveData<Int> = MutableLiveData()
    lateinit var scrollListener: EndlessRecyclerViewScrollListener

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()

    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { sendCurrentRequest() }

    val selectedRecipe: MutableLiveData<Recipe> = MutableLiveData()

    val onQueryTextListener = object: SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            sendNewRequest(query!!)
            return false
        }

        override fun onQueryTextChange(query: String?): Boolean {
            sendNewRequest(query!!)
            return false
        }
    }

    override fun showRecipe(recipe: Recipe) {
        selectedRecipe.value = recipe
    }

    override fun sendNewRequest(query: String) {

        recipeList.value = emptyList()

        scrollPosition.value = 0

        recipeRequest.query = query
        recipeRequest.page = 1

        if(recipeRequest.query.isNotEmpty()) sendCurrentRequest()
    }

    override fun requestNextPage(page: Int) {

        recipeRequest.page = page + 1

        sendCurrentRequest()

    }


    override fun sendCurrentRequest() {

        Log.d("RecipeListViewModel", "sendCurrentRequest(${recipeRequest.query},${recipeRequest.page},${recipeRequest.results})")

        subscription = recipeRepository.searchRecipes(recipeRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveRecipeListStart() }
            .doOnTerminate { onRetrieveRecipeListFinish() }
            .subscribe(
                { result -> onRetrieveRecipeListSuccess(result) },
                { error -> onRetrieveRecipeListError(error) }
            )

    }

    private fun onRetrieveRecipeListStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrieveRecipeListFinish() {
        loadingVisibility.value = View.GONE
    }

    private fun onRetrieveRecipeListSuccess(recipeRequest: RecipeRequest) {
        recipeList.value = (recipeList.value!! + recipeRequest.results!!)
    }

    private fun onRetrieveRecipeListError(error: Throwable) {
        error.printStackTrace()
        errorMessage.value = R.string.recipe_error
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    fun onEndOfPageReached(page: Int) {
        Log.d("RecipeListViewModel", "endOfPageReached($page)")
        requestNextPage(page)
    }

}