import time as t


def calculate(expression):
    if "+" in expression:
        elist = expression.split("+")



def more():
    print("\nMenu coming later...\n")


def run():
    print("")
    expression = input("Input an expression: ")
    calculate(expression)
    print("")


if __name__ == "__main__":
    on = True
    print("WELCOME TO SUPER CALCULATOR V4\n")
    t.sleep(3)
    print("Only single statements supported in current version.\n\n")
    t.sleep(3)
    print("Example expressions:\n")
    print("9 + 10")
    print("8 x 3 (or 8 times 3)")
    print("2 to the power of 7\n")
    menu = input("Would you like to see more examples? (Y/N): ")
    if menu.lower() == "Y":
        more()

    while on:
        decision = input("Restart? (Y/N): ").lower()
        if decision == "y" or decision == "yes":
            on = True
        else:
            on = False