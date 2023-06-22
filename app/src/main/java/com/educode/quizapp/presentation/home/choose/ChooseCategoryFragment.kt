package com.educode.quizapp.presentation.home.choose

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.educode.quizapp.databinding.FragmentChooseCategoryBinding
import com.educode.quizapp.presentation.home.MainActivity
import com.educode.quizapp.util.Category
import com.educode.quizapp.util.CategoryResponse
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChooseCategoryFragment : Fragment() {

    lateinit var binding: FragmentChooseCategoryBinding
    private val viewModel: ChooseCategoryViewModel by viewModels()
    lateinit var adapter: ChooseCategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChooseCategoryBinding.inflate(layoutInflater)
        (requireActivity() as MainActivity).supportActionBar!!.hide()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initObservers()
        viewModel.loadCategories()

        adapter = ChooseCategoryAdapter{
            goToQuestionFragment(it)
        }
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            launch {
                viewModel.chooseCategoryUIState.collectLatest {
                    when(it){
                        is CategoryResponse.Success -> {
                            handleLoading(isLoading = false)
                            adapter.categories = it.categories
                            binding.recyclerView.adapter = adapter
                        }
                        is CategoryResponse.Failure -> {
                            handleLoading(isLoading = false)
                            Snackbar.make(
                                requireContext(), binding.constraintChooseCategory,
                                "Error ${it.error}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        CategoryResponse.Loading -> {
                            handleLoading(isLoading = true)
                        }
                    }
                }
            }
        }
    }

    private fun handleLoading(isLoading: Boolean) {
        with(binding) {
            if (isLoading) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE

            }
        }
    }

    private fun goToQuestionFragment(category: Category){
        val action = ChooseCategoryFragmentDirections.actionChooseCategoryFragmentToQuestionFragment(category)
        findNavController().navigate(action)
    }
}