import webbrowser
import time
import requests


def setup():
    print("This may be a useless program, but it is pretty neat.")
    time.sleep(2)
    print("Simple enter a website (i.e. espn, apple, etc)")
    time.sleep(2)
    print("If the website exists, you will be taken to it.")
    time.sleep(2)
    print("If not, you'll be given an error and another try.")
    time.sleep(3)
    print("\n")


def run():
    try:
        site = input("Enter website: ")
        site = "http://www." + site + ".com"
        request = requests.get(site)
    except requests.exceptions.ConnectionError:
        print("Error (site not found)\n")
        run()
    else:
        webbrowser.open(site)

if __name__ == "__main__":
    setup()
    run()

