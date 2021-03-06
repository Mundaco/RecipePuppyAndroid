package com.mundaco.recipepuppy.ui.recipelist

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.mundaco.recipepuppy.R
import com.mundaco.recipepuppy.data.model.Recipe
import com.mundaco.recipepuppy.databinding.ItemRecipeBinding
import com.mundaco.recipepuppy.ui.recipedetail.RecipeViewModel
import com.mundaco.recipepuppy.utils.extension.getParentActivity

class RecipeListAdapter: RecyclerView.Adapter<RecipeListAdapter.ViewHolder>() {

    private lateinit var recipeList: List<Recipe>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeListAdapter.ViewHolder {
        val binding: ItemRecipeBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_recipe, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeListAdapter.ViewHolder, position: Int) {
        holder.bind(recipeList[position])
    }

    override fun getItemCount(): Int {
        return if(::recipeList.isInitialized) recipeList.size else 0
    }

    fun updateRecipeList(recipeList: List<Recipe>){
        this.recipeList = recipeList
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemRecipeBinding):RecyclerView.ViewHolder(binding.root){

        private val viewModel = RecipeViewModel()

        fun bind(recipe: Recipe){

            viewModel.bind(recipe)

            itemView.setOnClickListener {

                (it.getParentActivity() as RecipeListActivity).onRecipeItemClicked(recipe)
            }

            binding.viewModel = viewModel
        }
    }
}