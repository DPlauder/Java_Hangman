import kotlin.random.Random
fun main() {
    println("Hangman")
    var gameRunning: Boolean
    val words = listOf("kotlin", "Graz", "Dominik")

    do{
        playGame(words)
        gameRunning = askToPlayAgain()
    }while(gameRunning)
}
fun playGame(words: List<String>){
    val chosenWord = words[Random.nextInt(words.size)].lowercase()
    val guessedLetters = mutableSetOf<Char>()
    var guessAttempts = 8
    var isGuessed = false

    while (guessAttempts > 0 && !isGuessed){
        println("\nWord: ${getDisplayWord(chosenWord, guessedLetters)}")
        println("Guessed Letters: ${guessedLetters.joinToString(", ")}")
        println("Remaining Attempts: $guessAttempts")
        print("Enter a letter: ")

        val inputGuess = readLine()?.lowercase()

        //Überprüfung input
        if(inputGuess.isNullOrEmpty() || inputGuess.length != 1 || !inputGuess[0].isLetter()){
            println("Please enter a letter")
            continue
        }
        val guess = inputGuess[0]

        if(guessedLetters.contains(guess)){
            println("Letter '$guess already guessed")
            continue
        }
        guessedLetters.add(guess)
        if (chosenWord.contains(guess)){
            println("'$guess' is in the word")
            if(isWordGuessed(chosenWord, guessedLetters)){
                isGuessed = true
            }
        }else{
            println("'$guess' is not in the word")
            guessAttempts--
        }
    }
    if(isGuessed){
        println("\n You have guessed the word '$chosenWord")
    }else{
        println("Game Over! The word was: '$chosenWord")
    }
}
fun getDisplayWord(chosenWord : String, guessedLetters: Set<Char>): String{
    return chosenWord.map {if(guessedLetters.contains(it)) it else '_'}.joinToString(" ")
}

fun isWordGuessed(chosenWord: String, guessedLetters: Set<Char>): Boolean{
    return chosenWord.all{ guessedLetters.contains(it)}
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