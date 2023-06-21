package com.educode.quizapp.presentation.home.question

import android.animation.Animator
import android.app.Dialog
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.educode.domain.models.Category
import com.educode.domain.models.Question
import com.educode.quizapp.R
import com.educode.quizapp.databinding.FragmentQuestionBinding
import com.educode.quizapp.presentation.home.MainActivity
import com.educode.quizapp.presentation.home.choose.ChooseCategoryFragmentDirections
import com.educode.quizapp.util.QuestionResponse
import com.educode.quizapp.util.toDomainCategory
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.w3c.dom.Text

@AndroidEntryPoint
class QuestionFragment : Fragment() {
    lateinit var binding: FragmentQuestionBinding
    private val viewModel: QuestionViewModel by viewModels()
    val args: QuestionFragmentArgs by navArgs()
    lateinit var category: Category
    lateinit var list: List<Question>

    private var count = 0
    private var position = 0
    private var score = 0

    lateinit var timer: CountDownTimer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuestionBinding.inflate(layoutInflater)
        (requireActivity() as MainActivity).supportActionBar!!.hide()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initObservers()
        category = args.category.toDomainCategory()
        viewModel.loadQuestions(category)

        resetTimer()
        timer.start()

        binding.btnNext.setOnClickListener {

            timer.cancel()

            binding.btnNext.isEnabled = false
            binding.btnNext.alpha = 0.3F
            enabledOption(true)
            position++

            if(position == list.size){
                val action = QuestionFragmentDirections.actionQuestionFragmentToScoreFragment(score)
                findNavController().navigate(action)
            }else{
                count = 0

                playAnimation(binding.tvQuestion,0,list[position].question)
            }

            timer.start()
        }
    }

    private fun resetTimer() {
        timer = object: CountDownTimer(120000,1000){
            override fun onTick(p0: Long) {
                binding.tvTimer.text = (p0/1000).toString()
            }

            override fun onFinish() {
                val dialog = context?.let { Dialog(it) }
                dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog?.setCancelable(false)
                dialog?.setContentView(R.layout.timeout_dialog)
                val tryAgain = dialog?.findViewById(R.id.btnTryAgain) as Button
                tryAgain.setOnClickListener {
                    parentFragment?.view?.let { it1 -> Navigation.findNavController(it1).navigateUp() }
                    dialog.dismiss()
                }
                dialog.show()
            }

        }
    }

    private fun playAnimation(view: View, value: Int, data: String) {
        view.animate()
            .alpha(value.toFloat())
            .scaleX(value.toFloat())
            .scaleY(value.toFloat())
            .setInterpolator(DecelerateInterpolator()).setListener(object: Animator.AnimatorListener{
                override fun onAnimationStart(p0: Animator) {
                    if(value == 0 && count < 4){
                        var option = ""
                        when(count){
                            0 -> option = list[position].answer1
                            1 -> option = list[position].answer2
                            2 -> option = list[position].answer3
                            3 -> option = list[position].answer4
                        }
                        playAnimation(binding.optionContainer.getChildAt(count),0,option)
                        count++
                    }
                }

                override fun onAnimationEnd(p0: Animator) {
                    if(value == 0){
                        try{
                            with(view as TextView){
                                this.text = data
                            }
                            binding.tvQuestionNumber.text = "${position+1}/${list.size}"
                        }catch (e: Exception){
                            with(view as TextView){
                                this.text = data
                            }
                        }

                        view.tag = data
                        playAnimation(view,1,data)
                    }
                }

                override fun onAnimationCancel(p0: Animator) {
                }

                override fun onAnimationRepeat(p0: Animator) {
                }

            })
            .setDuration(500).startDelay = 100
    }

    private fun enabledOption(enable: Boolean) {
        for(i in 0..3){
            binding.optionContainer.getChildAt(i).isEnabled = enable
            if(enable){
                binding.optionContainer.getChildAt(i).setBackgroundResource(R.drawable.shape_button_options)
            }
        }

    }

    private fun checkAnswer(selectedOption: Button) {
        timer.cancel()
        binding.btnNext.isEnabled = true
        binding.btnNext.alpha = 1F

        if(selectedOption.text.toString() == list[position].correctAnswer){
            score++
            selectedOption.setBackgroundResource(R.drawable.shape_button_right_answer)
        }else{
            selectedOption.setBackgroundResource(R.drawable.shape_button_wrong_answer)
            val correctOption: Button = binding.optionContainer.findViewWithTag(list[position].correctAnswer)
            correctOption.setBackgroundResource(R.drawable.shape_button_right_answer)
        }
    }

    private fun initObservers(){
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            launch {
                viewModel.questionUIState.collectLatest {
                    when(it){
                        is QuestionResponse.Success -> {
                            handleLoading(isLoading = false)
                            bindData(it.questions[position])
                            list = it.questions
                            for(i in 0..3){
                                binding.optionContainer.getChildAt(i).setOnClickListener { view->
                                    checkAnswer(view as Button)
                                }
                            }
                            //topRatedAdapter.movies = it.movies
                            //binding.recyclerTopRated.adapter = topRatedAdapter
                        }
                        is QuestionResponse.Failure -> {
                            handleLoading(isLoading = false)
                            Snackbar.make(
                                requireContext(), binding.questionLayout,
                                it.error,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        QuestionResponse.Loading -> {
                            handleLoading(isLoading = true)
                        }
                    }
                }
            }
        }
    }

    private fun bindData(question: Question) {
        binding.tvQuestion.text = question.question
        binding.btnOption1.text = question.answer1
        binding.btnOption2.text = question.answer2
        binding.btnOption3.text = question.answer3
        binding.btnOption4.text = question.answer4
    }

    private fun handleLoading(isLoading: Boolean) {
        with(binding) {
            if (isLoading) {
                progressBar2.visibility = View.VISIBLE
                questionLayout.visibility = View.GONE
            } else {
                progressBar2.visibility = View.GONE
                questionLayout.visibility = View.VISIBLE
            }
        }
    }
}