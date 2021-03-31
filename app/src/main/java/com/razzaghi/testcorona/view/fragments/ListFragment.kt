package com.razzaghi.testcorona.view.fragments

import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.razzaghi.testcorona.R
import com.razzaghi.testcorona.db.option.OptionViewModel
import com.razzaghi.testcorona.db.quiz.QuizViewModel
import com.razzaghi.testcorona.model.Option
import com.razzaghi.testcorona.model.Quiz
import com.razzaghi.testcorona.other.Constants
import com.razzaghi.testcorona.other.Constants.CLOSE_ANSWER
import com.razzaghi.testcorona.other.Constants.MUCH_OPTION
import com.razzaghi.testcorona.other.Constants.NEVER_OPTION
import com.razzaghi.testcorona.other.Constants.OPEN_ANSWER
import com.razzaghi.testcorona.other.Constants.RARE_OPTION
import com.razzaghi.testcorona.other.Constants.SOME_TIME_OPTION
import com.razzaghi.testcorona.other.Constants.USUALLY_OPTION
import com.razzaghi.testcorona.util.animation.ViewAnimation
import com.razzaghi.testcorona.util.customView.AnswerItem
import com.razzaghi.testcorona.util.customView.AnswerOptionItem
import com.razzaghi.testcorona.util.customView.QuizOptionItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.list_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONException
import javax.inject.Inject

@AndroidEntryPoint
class ListFragment : Fragment(R.layout.list_fragment) {

    var score = 0

    private val optionViewModel: OptionViewModel by viewModels()
    private val quizViewModel: QuizViewModel by viewModels()

    val TAG = "ListFragment"

    @Inject
    lateinit var sharedPref: SharedPreferences


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        checkIsFirstTime(savedInstanceState)
        goToQuestion()
        showAnswer()
        getAnswer()

    }

    private fun showAnswer() {
        btnShowAnswer.setOnClickListener {
            toggleSectionView(btnShowAnswer)
        }
    }

    private fun checkIsFirstTime(savedInstanceState: Bundle?) {

        if (getFirstTimeOpen()) {

            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.listFragment, true)
                .setPopExitAnim(R.anim.slide_out_right)
                .setPopEnterAnim(R.anim.slide_in_left)
                .setEnterAnim(R.anim.slide_in_right)
                .setExitAnim(R.anim.slide_out_left)
                .build()
            findNavController().navigate(
                R.id.action_listFragment_to_quizFragment,
                savedInstanceState,
                navOptions
            )
        }
    }


    fun getFirstTimeOpen(): Boolean {
        return sharedPref.getBoolean(Constants.KEY_FIRST_TIME_TOGGLE, true)
    }

    private fun init() {

        fabAdd.apply {
            val tf = Typeface.createFromAsset(context.assets, "fonts/vazir.ttf")
            typeface = tf
        }

        playAnimation()


        txtTitleIranSuffering.setSingleLine()
        txtTitleIranSuffering.isSelected = true

        txtTitleIranHealed.setSingleLine()
        txtTitleIranHealed.isSelected = true

        txtTitleIranDied.setSingleLine()
        txtTitleIranDied.isSelected = true

        txtTitleWorldDied.setSingleLine()
        txtTitleWorldDied.isSelected = true

        txtTitleWorldHealed.setSingleLine()
        txtTitleWorldHealed.isSelected = true

        txtTitleWorldSuffering.setSingleLine()
        txtTitleWorldSuffering.isSelected = true

    }

    private fun playAnimation() {
        CoroutineScope(Dispatchers.Main).launch {

            coronaAnimation.playAnimation()
        }

    }

    private fun getAnswer() {

        quizViewModel.getAllQuiz().observe(viewLifecycleOwner, {
            showQuiz(it)
        })
    }


    private fun showQuiz(quizes: List<Quiz>) {
        try {
            Log.i(TAG, "showQuiz quizes: ${quizes.toList()}")
            for (i in quizes.indices) {

                var answerItem = AnswerItem(requireContext())
                answerItem.init(requireContext())
                answerItem.txtQuestion.text = quizes[i].question
                answerItem.txtFactor.text = quizes[i].factor


                optionViewModel.getAllOption(quizes[i].id!!).observe(viewLifecycleOwner, {

                    answerItem.linearOptions.removeAllViews()
                    showOption(it, answerItem)
                    Log.i(TAG, "showQuiz showOption: ${it.toList()}")

                })


                linearAnswer.addView(answerItem)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
            Log.i(TAG, "showQuiz e: $e")
        }
    }

    private fun showOption(optionList: List<Option>, answerItem: AnswerItem) {

        for (j in optionList.indices) {

            var optionItem =
                AnswerOptionItem(
                    requireContext()
                )
            optionItem.init(requireContext())
            optionItem.txtAnswerOption.text = optionList[j].option
            optionItem.optionId = optionList[j].id

            if (optionList[j].answer == "1") {
                optionItem.linearAnswerOption.visibility = View.VISIBLE
                score += ((answerItem.txtFactor.text.toString().trim()).toInt() * j)
                checkAnswerColor(optionItem.txtAnswerOption)
            } else {
                optionItem.linearAnswerOption.visibility = View.GONE
            }

            checkCondition(score)


            answerItem.linearOptions.addView(optionItem)
        }

    }

    private fun checkAnswerColor(txtAnswerOption: TextView?) {
        when (txtAnswerOption!!.text) {
            NEVER_OPTION -> {
                txtAnswerOption.setTextColor(Color.parseColor("#FFFFFF"));
            }
            RARE_OPTION -> {
                txtAnswerOption.setTextColor(Color.parseColor("#e6e6e6"));
            }
            SOME_TIME_OPTION -> {
                txtAnswerOption.setTextColor(Color.parseColor("#FDD835"));
            }
            USUALLY_OPTION -> {
                txtAnswerOption.setTextColor(Color.parseColor("#F9A825"));
            }
            MUCH_OPTION -> {
                txtAnswerOption.setTextColor(Color.parseColor("#FF5722"));
            }
        }
    }

    private fun checkCondition(score: Int) {

        if (score < 5) {
            txtCondition.text = "سالم "
        } else if (score in 5..10) {
            txtCondition.text = "مشکوک با احتمال کم  "
        } else if (score >= 10) {
            txtCondition.text = "مشکوک با احتمال زیاد "
        } else {
            txtCondition.text = "نامشخص"
        }

    }


    private fun goToQuestion() {
        fabAdd.setOnClickListener {


            val action =
                ListFragmentDirections.actionListFragmentToQuizFragment()
            findNavController().navigate(action)
        }
    }


    private fun toggleSectionView(view: Button) {
        val show: Boolean = toggleTextButton(view)
        if (show) {
            ViewAnimation.expand(
                linearAnswer
            ) { }
        } else {
            ViewAnimation.collapse(linearAnswer)
        }
    }


    fun toggleTextButton(view: Button): Boolean {
        return if (view.text == OPEN_ANSWER) {
            view.text = CLOSE_ANSWER
            true
        } else {
            view.text = OPEN_ANSWER
            false
        }
    }

}