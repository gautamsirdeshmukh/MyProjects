import random
import time
import sys


def run(filename):
    exists = True
    while exists:
        with open(filename) as file:
            purpose = input("What is this password for? : ")
            if purpose not in file.read():
                exists = False
            else:
                print("Error: Name already exists. Try again.")


    duplicate = True
    while duplicate:
        password = create()
        with open(filename) as file:
            if password not in file.read():
                file = open(filename, "a")
                file.write(purpose)
                file.write("\n")
                file.write(password)
                file.write("\n")
                file.write("\n")
                file.close()
                duplicate = False
                print("\nNew password: ", password, "\n")

    time.sleep(3)


def create():
    password = ""
    length = 0
    while length < 8 or length > 13:
        length = int(input("How many characters in password? (8-12): "))
    number = input("Does password need number(s)? (y/n): ").lower()
    special = input("Does password need special character(s)? (y/n): ").lower()
    if number == "y" or number == "yes":
        num = random.randint(1, 4)
        for n in range(num):
            password = password + random.choice("0123456789")
    if special == "y" or special == "yes":
        num = random.randint(1, 2)
        for n in range(num):
            password = password + random.choice("!@#$%&")
    seed = random.randrange(sys.maxsize)
    random.seed(seed)
    while len(password) < length:
        password = random.choice("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ") + password
    return password


if __name__ == "__main__":
    try:
        file = open("/Users/gautamsirdeshmukh/PycharmProjects/MyProjects/Data/passwords.txt", "x")
    except FileExistsError:
        pass
    file = open("/Users/gautamsirdeshmukh/PycharmProjects/MyProjects/Data/passwords.txt", "a")
    file = "/Users/gautamsirdeshmukh/PycharmProjects/MyProjects/Data/passwords.txt"
    going = True
    while going:
        run(file)
        decision = input("Restart program? (y/n):").lower()
        if decision == "y" or decision == "yes":
            going = True
        else:
            going = False
