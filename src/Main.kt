import kotlin.random.Random

fun main() {
    println("Hangman")
    var gameRunning: Boolean

    do {
        val difficulty = chooseDifficulty()
        playGame(difficulty)
        gameRunning = askToPlayAgain()
    } while (gameRunning)
}

fun chooseDifficulty(): Int {
    while (true) {
        println("Choose difficulty level:")
        println("1. Easy (10 attempts)")
        println("2. Medium (8 attempts)")
        println("3. Hard (6 attempts)")
        print("Enter 1, 2, or 3: ")

        when (readLine()?.toIntOrNull()) {
            1 -> return 10
            2 -> return 8
            3 -> return 6
            else -> println("Invalid input. Please enter 1, 2, or 3.")
        }
    }
}

fun playGame(guessAttempts: Int) {
    val words = listOf("kotlin", "graz", "dominik", "programming", "challenge", "algorithm")
    val chosenWord = words[Random.nextInt(words.size)].lowercase()
    val guessedLetters = mutableSetOf<Char>()
    var attemptsLeft = guessAttempts
    var isGuessed = false
    var hintUsed = false
    var score = 0

    while (attemptsLeft > 0 && !isGuessed) {
        println("\nWord: ${getDisplayWord(chosenWord, guessedLetters)}")
        println("Guessed Letters: ${guessedLetters.joinToString(", ")}")
        println("Remaining Attempts: $attemptsLeft")
        println("Score: $score")
        println("Type 'hint' to get a hint (costs 2 attempts)")

        print("Enter a letter: ")
        val inputGuess = readLine()?.lowercase()

        if (inputGuess.isNullOrEmpty() || (inputGuess.length != 1 && inputGuess != "hint") || (!inputGuess[0].isLetter() && inputGuess != "hint")) {
            println("Please enter a valid letter or type 'hint'")
            continue
        }

        if (inputGuess == "hint" && !hintUsed) {
            hintUsed = true
            provideHint(chosenWord, guessedLetters)
            attemptsLeft -= 2
            continue
        } else if (inputGuess == "hint" && hintUsed) {
            println("You can only use the hint once!")
            continue
        }

        val guess = inputGuess[0]
        if (guessedLetters.contains(guess)) {
            println("Letter '$guess' already guessed")
            continue
        }

        guessedLetters.add(guess)

        if (chosenWord.contains(guess)) {
            println("'$guess' is in the word")
            score += 10
            if (isWordGuessed(chosenWord, guessedLetters)) {
                isGuessed = true
            }
        } else {
            println("'$guess' is not in the word")
            attemptsLeft--
            score -= 5
        }
    }

    if (isGuessed) {
        println("\nCongratulations! You've guessed the word '$chosenWord'. Your final score is $score.")
    } else {
        println("Game Over! The word was: '$chosenWord'. Your final score is $score.")
    }
}

fun getDisplayWord(chosenWord: String, guessedLetters: Set<Char>): String {
    return chosenWord.map { if (guessedLetters.contains(it)) it else '_' }.joinToString(" ")
}

fun isWordGuessed(chosenWord: String, guessedLetters: Set<Char>): Boolean {
    return chosenWord.all { guessedLetters.contains(it) }
}

fun provideHint(chosenWord: String, guessedLetters: Set<Char>) {
    val unguessedLetters = chosenWord.filter { !guessedLetters.contains(it) }
    if (unguessedLetters.isNotEmpty()) {
        val hint = unguessedLetters.random()
        println("Hint: The word contains the letter '$hint'.")
    } else {
        println("No hint available!")
    }
}

fun askToPlayAgain(): Boolean {
    while (true) {
        print("Do you want to play again? (y/n): ")
        when (readLine()?.lowercase()) {
            "y", "yes" -> return true
            "n", "no" -> return false
            else -> println("Invalid input. Please enter 'y' or 'n'.")
        }
    }
}
