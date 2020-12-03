"""
@author: gautamsirdeshmukh
"""

import os.path
import time


def setup():
    print("\nThis program removes all M's from a given word or string.")
    time.sleep(3)
    print("\nChoose which option to mutate: \n")
    print("(1) A custom string")
    print("(2) All words in lowerwords.txt")
    time.sleep(3)


def removemWord(word):
    for letter in word:
        if letter == "m" or letter == "M":
            idx = word.index(letter)
            word = word[0:idx] + "X" + word[idx + 1:]
    return word


def removemAllWords():
    file = os.path.join("data", "lowerwords.txt")
    f = open(file)
    wordsClean = [w.strip() for w in f.read().split()]
    for word in wordsClean:
        word = removemWord(word)
        print(word)


if __name__ == '__main__':
    setup()
    decision = 3
    print("\n")
    while decision > 2 or decision < 1:
        decision = int(input("Your choice (1 or 2) --> "))
    print("\n")
    if decision == 1:
        customStr = input("Enter custom string: ")
        print(removemWord(customStr))
    else:
        removemAllWords()
