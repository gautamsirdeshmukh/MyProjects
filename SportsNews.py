from reader import make_reader, FeedExistsError
import time
import webbrowser


def setup():
    print("Sports News Reader V10.1.3")
    time.sleep(2)
    print("Created by Gautam Sirdeshmukh")
    print("Data sourced from ESPN.com")
    time.sleep(2)


def menu():
    print("\nChoose Category (type number)")
    time.sleep(2)
    print("(1) Top Headlines")
    print("(2) NFL")
    print("(3) NBA")
    print("(4) MLB")
    print("(5) NHL")
    print("(6) Soccer")
    print("(7) College Basketball")
    print("(8) College Football")
    time.sleep(1)
    print("Press Q to Quit\n")
    time.sleep(3)
    createURL()


def createURL():
    c = input("Enter choice: ")
    urls = ["https://www.espn.com/espn/rss/news",
            "https://www.espn.com/espn/rss/nfl/news",
            "https://www.espn.com/espn/rss/nba/news",
            "https://www.espn.com/espn/rss/mlb/news",
            "https://www.espn.com/espn/rss/nhl/news",
            "https://www.espn.com/espn/rss/soccer/news",
            "https://www.espn.com/espn/rss/ncb/news",
            "https://www.espn.com/espn/rss/ncf/news"]
    choices = list("12345678")
    if c in choices:
        url = urls[choices.index(c)]
        feed(url)
    else:
        if c == "q" or c == "Q" or c == "quit":
            quit()
        else:
            print("Error: Retype choice.")
            time.sleep(1)
            createURL()


def feed(url):
    print("")
    reader = make_reader('db.sqlite')

    try:
        reader.add_feed(url)
    except FeedExistsError:
        reader.remove_feed(url)
        reader.add_feed(url)
    reader.update_feeds()

    entries = list(reader.get_entries())
    count = 1
    for entry in entries[:15]:
        print(str(count) + ".", entry.title)
        count += 1

    time.sleep(5)
    print("\nWould you like to see more about a certain article?")
    choice = int(input("Enter article number or Q to quit: "))
    if choice == "Q" or choice == "q":
        quit()
    else:
        choice2 = 0
        while choice2 < 1 or choice2 > 2:
            choice2 = int(input("Press 1 to see more about article or 2 to open article: "))
        if choice2 == 1:
            print("\n" + entries[choice - 1].summary + "\n")
            time.sleep(4)
            choice3 = input("Would you like to open this article? (Y/N): ").lower()
            if choice3 == "y" or choice3 == "yes":
                webbrowser.open(entries[choice - 1].link)
        else:
            webbrowser.open(entries[choice - 1].link)


if __name__ == "__main__":
    power = True
    while power:
        setup()
        menu()
        time.sleep(5)
        decision = input("Restart Program? (Y/N): ").lower()
        if decision == "y" or decision == "yes":
            going = True
        else:
            going = False
            quit()
