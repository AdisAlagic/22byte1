package com.adisalagic.a22bytetest.utils

import com.adisalagic.a22bytetest.R
import com.adisalagic.a22bytetest.objects.Book
import com.adisalagic.a22bytetest.ui.QuizViewModel
import kotlin.random.Random

fun <T> List<T>.uniqueAmountOfRandom(number: Int): List<T> {
    val finalList = mutableListOf<T>()
    val copy = this.toMutableList()
    repeat(number) {
        finalList.add(copy.takeRandom())
    }
    return finalList
}

fun <T> MutableList<T>.takeRandom(): T {
    val randomIndex = Random.nextInt(0, size)
    return removeAt(randomIndex);
}

fun List<QuizViewModel.HistoryItem>.toList(): List<Book> {
    val list = mutableListOf<Book>()
    this.forEach {
        list.add(it.selectedBook)
        list.add(it.rightAnswer)
    }
    return list
}

val hardCodedBooks = listOf(
    Book("1984", R.drawable.b1984, "Был холодный ясный апрельский день, и часы пробили тринадцать."),
    Book("Метель", R.drawable.blizzard, "– Неделя прошла, и никаких результатов! – орет Власов. – Чем вы занимаетесь? А?"),
    Book("Время выбора", R.drawable.choice_time, "Лето в городе, похоже, окончательно вступило в свои права, не дожидаясь календарных дат и климатических значений."),
    Book("Собачья смерть", R.drawable.dog_s_death, "Мы все хотим жить. Большинство из нас руководствуется этим желанием в своих поступках."),
    Book("Эхо орбитального удара", R.drawable.echo_orbital_hit, "Пустотная верфь танланов больше не выглядела разрушенной."),
    Book("Любимая адептика его величества", R.drawable.favorite_adeptic_his_highness, "– Вообще-то я не беру плату с красивых девушек, – заявил Псих, – но ты, Марго, исключение. Будешь должна услугу!"),
    Book("Крылья бумажной птицы", R.drawable.fether_of_paper_birdd, "Их было трое. Мать, отец и юноша лет шестнадцати: молчаливый, худощавый, с коротко стриженной головой и тонкими синеватыми губами."),
    Book("Прыжок", R.drawable.jump, "«Твен» лежал на поверхности. Вообще-то межзвездные корабли для этого не предназначены."),
    Book("Одиночка", R.drawable.loner, "Перестрелка стихла, и Елисей, привычным движением сменив магазин в пистолете, приподнялся над валуном, за которым присел, укрываясь от пуль."),
    Book("Лунный Император", R.drawable.moon_imperator, "Точно помню, как очнулась на чём-то мягком с дикой головной болью."),
    Book("Школа чернокнижников", R.drawable.school_of_black_magic, "– Пошевеливайся, гусыня неповоротливая!"),
    Book("Швейцарский счет", R.drawable.swedish_account, "Ослепительное сияние девственно-белого снега и оглушительная, неземная тишина…"),
    Book("Тревожные люди", R.drawable.worried_people, "Ограбление банка. Захват заложников. Выстрел.")
)