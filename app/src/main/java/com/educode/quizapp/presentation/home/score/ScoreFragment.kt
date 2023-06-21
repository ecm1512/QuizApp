package com.educode.quizapp.presentation.home.score

import android.graphics.Movie
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.educode.quizapp.databinding.FragmentScoreBinding
import com.educode.quizapp.presentation.home.MainActivity


class ScoreFragment : Fragment() {
    lateinit var binding: FragmentScoreBinding
    val args: ScoreFragmentArgs by navArgs()
    var score: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScoreBinding.inflate(layoutInflater)
        (requireActivity() as MainActivity).supportActionBar!!.hide()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        score = args.score
        binding.tvResultScore.text = "Hiciste $score preguntas correctas"

        binding.btnRetry.setOnClickListener {
            findNavController().navigate(ScoreFragmentDirections.actionScoreFragmentToChooseCategoryFragment2())
        }
    }
}