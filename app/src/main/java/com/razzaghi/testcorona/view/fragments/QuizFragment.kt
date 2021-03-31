package com.razzaghi.testcorona.view.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.razzaghi.testcorona.R
import com.razzaghi.testcorona.db.option.OptionViewModel
import com.razzaghi.testcorona.db.quiz.QuizViewModel
import com.razzaghi.testcorona.model.Option
import com.razzaghi.testcorona.model.Quiz
import com.razzaghi.testcorona.other.Constants
import com.razzaghi.testcorona.other.Constants.KEY_FIRST_TIME_TOGGLE
import com.razzaghi.testcorona.util.SimpleRadioButton
import com.razzaghi.testcorona.util.customView.QuizOptionItem
import com.razzaghi.testcorona.util.customView.QuizItem
import com.razzaghi.testcorona.util.myToastSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.quiz_fragment.*
import javax.inject.Inject


@AndroidEntryPoint
class QuizFragment : Fragment(R.layout.quiz_fragment) {

    val TAG = "QuizFragment"

    private val quizViewModel: QuizViewModel by viewModels()
    private val optionViewModel: OptionViewModel by viewModels()


    var quizListToSave: ArrayList<Quiz> = ArrayList<Quiz>()
    var optionListToSave: ArrayList<Option> = ArrayList<Option>()


    @Inject
    lateinit var sharedPref: SharedPreferences



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()

        loadQuiz()

        saveAnswer()

        finishFragment()

    }

    private fun init() {
        if (getFirstTimeOpen()) {
            cardViewFinish.isVisible = false
        }
    }

    private fun finishFragment() {
        cardViewFinish.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }
    }

    private fun saveAnswer() {
        btnSave.setOnClickListener {

            quizListToSave.clear()
            optionListToSave.clear()


            setQuizListToSave()

            Log.i(TAG, "saveAnswer quizListToSave size:  ${quizListToSave.size}")
            Log.i(TAG, "saveAnswer quizListToSave toList:  ${quizListToSave.toList()}")

            Log.i(TAG, "saveAnswer optionListToSave size:  ${optionListToSave.size}")
            Log.i(TAG, "saveAnswer optionListToSave toList:  ${optionListToSave.toList()}")

            /* quizListToSave.forEach {
                 quizViewModel.upsertQuiz(it)
             }*/
            optionListToSave.forEach {
                optionViewModel.upsertOption(it)
            }

            myToastSnackBar.simpleToastFloating(
                requireActivity(),
                "با موفقیت ذخیره شد!",
            )


            if (getFirstTimeOpen()) {

                setFirstTimeToFalse()
                popUpTpPrevFragment()
                Log.i(TAG, "saveAnswer getFirstTimeOpen popBackStack : ")
            } else {
                Navigation.findNavController(it).popBackStack()
                Log.i(TAG, "saveAnswer getFirstTimeOpen NOT popUpTpPrevFragment: ")
            }

        }
    }

    private fun popUpTpPrevFragment() {

        val action =
            QuizFragmentDirections.actionQuizFragmentToListFragment()


        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.quizFragment, true)
            .setPopExitAnim(R.anim.slide_out_right)
            .setPopEnterAnim(R.anim.slide_in_left)
            .setEnterAnim(R.anim.slide_in_right)
            .setExitAnim(R.anim.slide_out_left)
            .build()
        findNavController().navigate(
            action,
            navOptions
        )
    }

    private fun setFirstTimeToFalse() {

        sharedPref.edit()
            .putBoolean(KEY_FIRST_TIME_TOGGLE, false)
            .apply()

    }


    fun getFirstTimeOpen(): Boolean {
        return sharedPref.getBoolean(KEY_FIRST_TIME_TOGGLE, true)
    }

    private fun setQuizListToSave() {

        val quizCount: Int = linearQuiz.childCount
        Log.i(TAG, "setQuizListToSave quizCount:$quizCount ")

        for (i in 0 until quizCount) {
            Log.i(TAG, "setQuizListToSave quizCount i: $i")
            val view: View = linearQuiz.getChildAt(i)
            val quizItem: QuizItem = view as QuizItem
            quizListToSave.add(
                Quiz(
                    (i + 1).toLong(),
                    quizItem.txtQuestion.text.toString(),
                    quizItem.txtFactor.text.toString()
                )
            )

            setOptionListToSave(quizItem, quizCount, (i + 1).toLong())


        }

    }

    private fun setOptionListToSave(quizItem: QuizItem, quizCount: Int, quizId: Long) {

        Log.i(TAG, "setOptionListToSave quizCount:$quizCount ")

        val optionCount = quizItem.linearOptions.childCount
        Log.i(TAG, "setOptionListToSave optionCount:$optionCount ")

        //val allOptionCount = optionCount * quizCount
        //Log.i(TAG, "setOptionListToSave allOptionCount:$allOptionCount ")


        for (j in 0 until optionCount) {
            Log.i(TAG, "setOptionListToSave optionCount j: $j")
            val view: View = quizItem.linearOptions.getChildAt(j)
            val quizOptionItem: QuizOptionItem = view as QuizOptionItem
            var isChecked = 0
            if (quizOptionItem.radioButtonOption.isChecked) {
                isChecked = 1
            } else {
                isChecked = 0
            }

            Log.i(TAG, "setOptionListToSave optionItem.optionId: ${quizOptionItem.optionId}")

            optionListToSave.add(
                Option(
                    quizOptionItem.optionId,
                    quizOptionItem.radioButtonOption.text.toString(),
                    isChecked.toString(),
                    j.toString(),
                    quizId
                )
            )

        }
    }

    private fun loadQuiz() {

        quizViewModel.getAllQuiz().observe(viewLifecycleOwner, {
            showQuiz(it)
        })
    }

    private fun showQuiz(quizes: List<Quiz>) {
        Log.i(TAG, "showQuiz quizes: ${quizes.toList()}")
        for (i in quizes.indices) {

            var quizItem = QuizItem(requireContext())
            quizItem.init(requireContext())
            quizItem.txtQuestion.text = quizes[i].question
            quizItem.txtFactor.text = quizes[i].factor


            optionViewModel.getAllOption(quizes[i].id!!).observe(viewLifecycleOwner, {

                quizItem.linearOptions.removeAllViews()
                showOption(it, quizItem.linearOptions)
                Log.i(TAG, "showQuiz showOption: ${it.toList()}")

            })


            linearQuiz.addView(quizItem)
        }
    }

    private fun showOption(optionList: List<Option>, linearOptions: LinearLayout) {

        for (j in optionList.indices) {

            var optionItem =
                QuizOptionItem(
                    requireContext()
                )
            optionItem.init(requireContext())
            optionItem.radioButtonOption.text = optionList[j].option
            optionItem.optionId = optionList[j].id

            optionItem.radioButtonOption.isChecked = optionList[j].answer == "1"

            optionItem.radioButtonOption.setOnClickListener {
                selectOption(j, it as SimpleRadioButton, optionList.size, linearOptions)
            }
            linearOptions.addView(optionItem)
        }

    }


    private fun selectOption(
        index: Int,
        layout: SimpleRadioButton,
        length: Int,
        linearOptions: LinearLayout
    ) {
        for (j in 0 until length) {
            Log.i(TAG, "$index:$j/$length")
            val newQuizOptionItem: QuizOptionItem =
                linearOptions.getChildAt(j) as QuizOptionItem
            if (index != j) {
                Log.i(TAG, "selectOption current index of loop is $j")
                val box: SimpleRadioButton = newQuizOptionItem.findViewById(R.id.radioButtonOption)
                box.isChecked = false
            }
        }
        val box = layout.findViewById<SimpleRadioButton>(R.id.radioButtonOption)
        box.isChecked = true
    }

}