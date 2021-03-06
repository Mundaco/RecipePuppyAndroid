package com.mundaco.recipepuppy.ui.recipedetail

import android.arch.lifecycle.MutableLiveData
import com.mundaco.recipepuppy.base.BaseViewModel
import com.mundaco.recipepuppy.data.model.Recipe

class RecipeViewModel: BaseViewModel() {

    private val recipeTitle = MutableLiveData<String>()
    private val recipeHref = MutableLiveData<String>()
    private val recipeIngredients = MutableLiveData<String>()
    private val recipeThumbnail = MutableLiveData<String>()

    fun bind(recipe: Recipe) {
        recipeTitle.value = recipe.title
        recipeHref.value = recipe.href
        recipeIngredients.value = recipe.ingredients
        recipeThumbnail.value = recipe.thumbnail
    }

    fun getRecipeTitle():MutableLiveData<String>{
        return recipeTitle
    }

    fun getRecipeHref():MutableLiveData<String>{
        return recipeHref
    }

    fun getRecipeIngredients():MutableLiveData<String>{
        return recipeIngredients
    }

    fun getRecipeThumbnail():MutableLiveData<String>{
        return recipeThumbnail
    }
}