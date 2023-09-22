package com.adisalagic.a22bytetest.ui

import androidx.lifecycle.ViewModel
import com.adisalagic.a22bytetest.objects.Book
import com.adisalagic.a22bytetest.utils.hardCodedBooks
import com.adisalagic.a22bytetest.utils.takeRandom
import com.adisalagic.a22bytetest.utils.uniqueAmountOfRandom
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class QuizViewModel : ViewModel() {

    private val _state =
        MutableStateFlow(QuizDataState(emptyList(), 0, emptyList(), QuizState.NOT_STARTED))
    val state = _state.asStateFlow()

    private val data get() = _state.value

    fun resetGame() {
        _state.update {
            it.copy(
                history = emptyList(),
                currentRound = 0,
                questions = hardCodedBooks.uniqueAmountOfRandom(10),
                quizState = QuizState.IN_PROGRESS
            )
        }
    }

    fun giveIncorrect(): List<Book> {
        val copy = data.questions.toMutableList().apply { remove(data.questions[data.currentRound]) }
        return listOf(copy.takeRandom(), copy.takeRandom())
    }

    fun selectBook(book: Book) {
        _state.update {
            it.copy(
                history = data.history.toMutableList().apply { add(HistoryItem(book, data.questions[data.currentRound])) },
            )
        }
        if (data.currentRound == data.questions.lastIndex) {
            _state.update { it.copy(quizState = QuizState.ENDED) }
            return
        }
        _state.update { it.copy(currentRound = data.currentRound + 1) }
    }

    fun backToStart() {
        _state.update { it.copy(quizState = QuizState.NOT_STARTED) }
    }

    data class QuizDataState(
        val history: List<HistoryItem>,
        val currentRound: Int,
        val questions: List<Book>,
        val quizState: QuizState
    ) {
        val currentQuestion get() = questions[currentRound]

        val result: Int get() {
            var right = 0
            history.forEach {
                if (it.rightAnswer == it.selectedBook) {
                    right++
                }
            }
            return right
        }
    }

    data class HistoryItem(val selectedBook: Book, val rightAnswer: Book)

    enum class QuizState {
        NOT_STARTED,
        IN_PROGRESS,
        ENDED
    }
}