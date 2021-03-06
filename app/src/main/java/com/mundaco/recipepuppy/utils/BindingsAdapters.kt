package com.mundaco.recipepuppy.utils

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.databinding.BindingAdapter
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.mundaco.recipepuppy.data.model.Recipe
import com.mundaco.recipepuppy.ui.recipelist.RecipeListAdapter
import com.mundaco.recipepuppy.utils.extension.getParentActivity


@BindingAdapter("mutableVisibility")
fun setMutableVisibility(view: View, visibility: MutableLiveData<Int>?) {
    val parentActivity: AppCompatActivity? = view.getParentActivity()
    if(parentActivity != null && visibility != null) {
        visibility.observe(parentActivity, Observer { value -> view.visibility = value?:View.VISIBLE})
    }
}

@BindingAdapter("mutableText")
fun setMutableText(view: TextView, text: MutableLiveData<String>?) {
    val parentActivity: AppCompatActivity? = view.getParentActivity()
    if(parentActivity != null && text != null) {
        text.observe(parentActivity, Observer { value -> view.text = value?:""})
    }
}

@BindingAdapter("mutableRecipeList")
fun setMutableRecipeList(view: RecyclerView, recipeList: MutableLiveData<List<Recipe>>?) {
    val parentActivity: AppCompatActivity? = view.getParentActivity()
    if(parentActivity != null && recipeList != null) {
        recipeList.observe(parentActivity, Observer { value ->
            (view.adapter as RecipeListAdapter).updateRecipeList(value!!)
        })
    }
}

@BindingAdapter("mutableScrollPosition")
fun setMutableScrollPosition(view: RecyclerView, scrollPosition: MutableLiveData<Int>?) {
    val parentActivity: AppCompatActivity? = view.getParentActivity()
    if(parentActivity != null && scrollPosition != null) {
        scrollPosition.observe(parentActivity, Observer { value ->
            view.scrollToPosition(value!!)
        })
    }
}
