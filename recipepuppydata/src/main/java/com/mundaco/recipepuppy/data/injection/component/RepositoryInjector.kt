package com.mundaco.recipepuppy.data.injection.component

import com.mundaco.recipepuppy.data.RecipeRepository
import com.mundaco.recipepuppy.data.injection.module.DaoModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DaoModule::class])
interface RepositoryInjector {

    fun inject(recipeRepository: RecipeRepository)

    @Component.Builder
    interface Builder {
        fun build(): RepositoryInjector

        fun daoModule(daoModule: DaoModule): Builder
    }
}