package com.educode.quizapp.presentation.home.choose

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.educode.quizapp.R
import com.educode.quizapp.databinding.ItemCategoryBinding
import com.educode.quizapp.util.Category
import kotlin.properties.Delegates

class ChooseCategoryAdapter(
    private val listenerMovieItem: (Category) -> Unit
): RecyclerView.Adapter<ChooseCategoryAdapter.ChooseCategoryViewHolder>() {
    var categories: List<Category> by Delegates.observable(emptyList()){ _, old, new ->
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            ChooseCategoryItemDiffCallback(old, new)
        )
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChooseCategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChooseCategoryViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: ChooseCategoryViewHolder, position: Int) {
        val movie = categories[position]
        holder.itemView.setOnClickListener {
            listenerMovieItem(movie)
        }
        holder.setDataCard(movie)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    class ChooseCategoryViewHolder(private val binding: ItemCategoryBinding, private val context: Context): RecyclerView.ViewHolder(binding.root){
        @SuppressLint("UseCompatLoadingForDrawables", "DiscouragedApi")
        fun setDataCard(category: Category){
            val uri = category.image
            val drawable = context.resources.getDrawable(context.resources.getIdentifier(uri,"drawable",context.packageName))
            binding.ivImageCategory.setImageDrawable(drawable)
            binding.tvCategoryName.text = category.name
        }
    }

    class ChooseCategoryItemDiffCallback(
        var oldProductList: List<Category>,
        var newProductList: List<Category>
    ): DiffUtil.Callback(){
        override fun getOldListSize(): Int {
            return oldProductList.size
        }

        override fun getNewListSize(): Int {
            return newProductList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (oldProductList[oldItemPosition].id == newProductList[newItemPosition].id)
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldProductList[oldItemPosition].equals(newProductList[newItemPosition])
        }

    }
}