import time as t
import random


def setup():
    welcome = "WELCOME TO SUPER CALCULATOR V9.3"
    for ch in welcome:
        print(ch, end = "")
        t.sleep(0.25)
    t.sleep(2)
    print("\nOnly single statements supported in current version.\n")
    t.sleep(2)
    print("Example expressions:")
    print("9 + 10 (or 9 plus 10)")
    print("8 x 3 (or 8 times 3)\n")
    t.sleep(3)
    menu = input("Would you like to see more examples? (Y/N): ")
    if menu == "y" or menu == "yes" or menu == "Y":
        more()


def calculate(expression):
    result = 0
    try:
        if "+" in expression or "plus" in expression:
            try:
                idx = expression.index("+")
            except ValueError:
                expression = expression.replace("plus", "+")
                idx = expression.index("+")
            lst = expression.strip().split(expression[idx])
            result = eval(lst[0] + "+" + lst[1])
        elif "-" in expression or "minus" in expression:
            try:
                idx = expression.index("-")
            except ValueError:
                expression = expression.replace("minus", "-")
                idx = expression.index("-")
            lst = expression.strip().split(expression[idx])
            result = eval(lst[0] + "-" + lst[1])
        elif "x" in expression or "times" in expression or "*" in expression:
            try:
                idx = expression.index("*")
            except ValueError:
                try:
                    expression = expression.replace("x", "*")
                    idx = expression.index("*")
                except ValueError:
                    expression = expression.replace("times", "*")
                    idx = expression.index("*")
            lst = expression.strip().split(expression[idx])
            result = eval(lst[0] + "*" + lst[1])
        elif "/" in expression or "divided by" in expression:
            try:
                idx = expression.index("/")
            except ValueError:
                expression = expression.replace("divided by", "/")
                idx = expression.index("/")
            lst = expression.strip().split(expression[idx])
            result = eval(lst[0] + "/" + lst[1])
        elif "^" in expression or "to the power of" in expression:
            try:
                idx = expression.index("^")
            except ValueError:
                expression = expression.replace("to the power of", "^")
                idx = expression.index("^")
            lst = expression.strip().split(expression[idx])
            result = eval(lst[0] + "**" + lst[1])
        else:
            print("Error: Please retype expression.", end = "")
            return ""
    except SyntaxError:
        print("Error: Cannot process multiple expressions.", end = "")
        return ""

    if len(str(result)) > 2:
        sn = input("Scientific notation? (Y/N): ")
        if sn == "y" or sn == "Y" or sn == "yes":
            working = True
            while working:
                sf = input("Precision: ")
                if sf.isnumeric():
                    working = False
                else:
                    print("Error: Must enter an integer\n")
            precision = "0." + str(sf) + "e"
            result = format(result, precision)

    return "Answer: " + str(result)


def more():
    print("\n12 - 8 (or 12 minus 8)")
    t.sleep(1)
    print("14 / 2 (or 14 divided by 2)")
    t.sleep(1)
    print("2^7 (or 2 to the power of 7)\n")
    t.sleep(2)


def run():
    expression = input("Input an expression (or type Q to quit): ")
    if expression == "q" or expression == "Q" or expression == "quit":
        quit()
    print(calculate(expression))
    print("")


if __name__ == "__main__":
    random.seed(random.randint(0, 100))
    on = True
    while on:
        setup()
        run()
        t.sleep(3)
        decision = input("Restart? (Y/N): ").lower()
        if decision == "y" or decision == "yes":
            on = True
        else:
            on = False
