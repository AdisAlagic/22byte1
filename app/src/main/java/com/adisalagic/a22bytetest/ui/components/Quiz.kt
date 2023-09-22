package com.adisalagic.a22bytetest.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.adisalagic.a22bytetest.objects.Book
import com.adisalagic.a22bytetest.ui.QuizViewModel
import com.adisalagic.a22bytetest.utils.toList
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun Quiz(paddingValues: PaddingValues) {
    val model = viewModel<QuizViewModel>(LocalViewModelStoreOwner.current!!)
    val data by model.state.collectAsState()
    val animatedProgress = animateFloatAsState(
        targetValue = data.currentRound / data.questions.size.toFloat(),
        label = "",
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    )
    var completeQuestions: List<Book> by remember {
        mutableStateOf(emptyList())
    }
    LaunchedEffect(key1 = data.quizState, key2 = data.currentRound) {
        if (data.quizState == QuizViewModel.QuizState.IN_PROGRESS) {
            completeQuestions =
                mutableListOf(data.currentQuestion).apply { addAll(model.giveIncorrect()) }
        }
    }
    AnimatedVisibility(visible = data.quizState == QuizViewModel.QuizState.IN_PROGRESS, enter = fadeIn(), exit = fadeOut()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            Text(text = data.currentQuestion.quote, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(10.sdp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.sdp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                for (book in completeQuestions) {
                    BookImage(
                        id = book.id,
                        isCorrect = book == data.currentQuestion,
                        onClick = {
                            model.selectBook(book)
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.sdp))
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
                progress = animatedProgress.value
            )
        }
    }
    AnimatedVisibility(visible = data.quizState == QuizViewModel.QuizState.ENDED, enter = fadeIn(), exit = fadeOut()) {
        Column(modifier = Modifier.padding(paddingValues)) {
            Text(
                text = "Ваш результат: ${data.result} / ${data.questions.size}",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 20.ssp
            )
            Spacer(modifier = Modifier.height(20.sdp))
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val modifier = Modifier.weight(0.4f)
                Text(text = "Вы выбрали", modifier = modifier, textAlign = TextAlign.Center)
                Text(text = "Правильный ответ", modifier = modifier, textAlign = TextAlign.Center)
            }
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.padding(bottom = 40.sdp),
                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
                verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically)
            ) {
                items(data.history.toList()) {
                    BookImage(id = it.id, isCorrect = false, onClick = {})
                    BookImage(id = it.id, isCorrect = false, onClick = {})
                }
            }
            Spacer(modifier = Modifier.height(20.sdp))
        }
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.BottomCenter) {
            Button(modifier = Modifier.fillMaxWidth(), onClick = { model.backToStart() }) {
                Text(text = "Вернуться")
            }
        }
    }
    AnimatedVisibility(visible = data.quizState == QuizViewModel.QuizState.NOT_STARTED, enter = fadeIn(), exit = fadeOut()) {
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
            Button(modifier = Modifier.fillMaxWidth(), onClick = { model.resetGame() }) {
                Text(text = "Начать квиз")
            }
        }
    }
}