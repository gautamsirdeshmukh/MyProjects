'''
Author: Gautam Sirdeshmukh
'''
import random

debug = False


def handleUserInputDifficulty():
    '''
    Asks the user if they would like to play the
    game in (h)ard or (e)asy mode, then returns
    the corresponding number of misses allowed
    for the game.
    '''
    print("How many misses do you want? Hard has 8 and Easy has 12")
    response = input("(h)ard or (e)asy> ")
    if response == "e":
        print("you have 12 misses to guess word")
        return 12
    if response == "h":
        print("you have 8 misses to guess word")
        return 8


def handleUserInputLetterGuess(lettersGuessed, displayString):
    '''
    Prints displayString, then asks the user to input a
    letter to guess. This function handles the user input
    of the new letter guessed and checks if it is a repeated
    letter.
    '''
    print(displayString)
    going = True
    while going:
        letter = input("letter> ")
        if letter in lettersGuessed:
            print("you already guessed that")
        if letter not in lettersGuessed:
            lettersGuessed.append(letter)
            return letter
            break


def createDisplayString(lettersGuessed, missesLeft, hangmanWord):
    '''
    Creates the string that will be displayed to the user,
    using the information in the parameters.
    '''
    sortedLetters = sorted(lettersGuessed)
    lettersNotGuessed = []
    for letter in "abcdefghijklmnopqrstuvwxyz":
        if letter not in sortedLetters:
            lettersNotGuessed.append(letter)
        else:
            lettersNotGuessed.append(" ")
    return ("letters not yet guessed: " + "".join(lettersNotGuessed) + "\n" + "misses remaining = " + str(
        missesLeft) + "\n" + " ".join(hangmanWord))


def runGame(filename):
    '''
    This function sets up the game, runs each round,
    and prints a final message on whether or not the
    user won. True is returned if the user won the game.
    If the user lost the game, False is returned.
    '''
    f = open(filename)
    wordList = f.read().split()
    debug = handleUserInputDebugMode()
    length = handleUserInputWordLength()
    missesLeft = handleUserInputDifficulty()
    words = []
    for word in wordList:
        if len(word) == length:
            words.append(word)
    hangmanWord = ["_" for x in range(length)]
    currentTemplate = "".join(hangmanWord)
    lettersGuessed = []
    guesses = 0
    misses = 0
    while True:
        secretWord = random.choice(words)
        display = createDisplayString(lettersGuessed, missesLeft, hangmanWord)
        if debug:
            display += "\n" + "(word is " + secretWord + ")" + "\n" + "# possible words: " + str(len(words))
        guessedLetter = handleUserInputLetterGuess(lettersGuessed, display)
        currentTemplate = "".join(hangmanWord)
        if len(words) != 1:
            words = getNewWordList(currentTemplate, guessedLetter, words, debug)[1]
        secretWord = words[0]
        guesses += 1
        processUserGuessClever(guessedLetter, secretWord, missesLeft)
        missesLeft = processUserGuessClever(guessedLetter, secretWord, missesLeft)[0]
        if guessedLetter not in secretWord:
            misses += 1
            print("you missed: " + guessedLetter + " not in word")
        if "_" not in updateHangmanWord(guessedLetter, secretWord, hangmanWord) and missesLeft > 0:
            print("you guessed the word " + secretWord)
            print("you made " + str(guesses) + " guesses with " + str(misses) + " misses")
            return True
        if "_" in updateHangmanWord(guessedLetter, secretWord, hangmanWord) and missesLeft == 0:
            print("you're hung!" + "\n" + "word is " + secretWord)
            print("you made " + str(guesses) + " guesses with " + str(misses) + " misses")
            return False


def handleUserInputDebugMode():
    '''
    Asks the user if they would like to play the
    game in (d)ebug or (p)lay mode and sets the
    mode of the game
    '''
    response = input("Which mode do you want: (d)ebug or (p)lay: ")
    if response == "d":
        return True
    if response == "p":
        return False


def handleUserInputWordLength():
    """
    Asks the user how long secretWord should be
    and returns the user's input as an integer
    """
    length = int(input("How many letters in the word you'll guess: "))
    return length


def updateHangmanWord(guessedLetter, secretWord, hangmanWord):
    '''
    Updates hangmanWord according to whether guessedLetter is
    in secretWord and where in secretWord guessedLetter is in.
    '''
    if guessedLetter in secretWord:
        for idx in range(len(secretWord)):
            if secretWord[idx] == guessedLetter:
                hangmanWord[idx] = guessedLetter
    return hangmanWord


def createTemplate(currentTemplate, letterGuess, word):
    """
    Returns an updated template based on the current template,
    the letter guessed, and the secret word.
    """
    newTemplate = [x for x in currentTemplate]
    for idx in range(len(word)):
        if word[idx] == letterGuess:
            newTemplate[idx] = letterGuess
    return "".join(newTemplate)


def getNewWordList(currentTemplate, letterGuess, wordList, debug):
    '''
    This function constructs a dictionary of strings as the
    key to lists as the value.
    '''
    choices = {}
    if len(wordList) == 1:
        word = wordList[0]
        return (word, wordList)
    for word in wordList:
        if len(word) == len(currentTemplate):
            template = createTemplate(currentTemplate, letterGuess, word)
            if template not in choices:
                choices[template] = []
            choices[template].append(word)
    themax = 0
    thekey = ""
    for key, value in choices.items():
        if len(value) > themax:
            themax = len(value)
    for key, value in choices.items():
        if len(value) == themax and "_" in key:
            thekey = key
    if debug:
        for key, value in sorted(choices.items()):
            print(key + " : " + str(len(value)))
        print("# keys = " + str(len(choices)))
    return (thekey, choices[thekey])


def processUserGuessClever(guessedLetter, secretWord, missesLeft):
    '''
    Takes the user's guess, the user's current progress on
    the word, and the number of misses left; updates the
    number of misses left and indicates whether the user
    missed.
    '''
    if guessedLetter in secretWord:
        correct = True
    if guessedLetter not in secretWord:
        missesLeft = missesLeft - 1
        correct = False
    return [int(missesLeft), correct]


if __name__ == "__main__":
    '''
    Running Hangman.py should start the game, which is done 
    by calling runGame, therefore, we have provided you this 
    code below.
    '''
    wins = 0
    losses = 0
    start = True
    while start:
        winloss = runGame('/Users/gautamsirdeshmukh/PycharmProjects/MyProjects/Data/lowerwords.txt')
        if winloss == True:
            wins += 1
        else:
            losses += 1
        decision = input("\nDo you want to play again? y or n> ")
        if decision == "y":
            start = True
        else:
            print("You won", str(wins), "game(s) and lost", str(losses))
            start = False