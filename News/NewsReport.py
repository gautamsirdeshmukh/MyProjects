from reader import make_reader, FeedExistsError
import time
import webbrowser


def setup():
    print("News Report Feed V8.1.7")
    time.sleep(2)
    print("Created by Gautam Sirdeshmukh")
    print("Data sourced from CNN.com, BBC.com, CNBC.com and NYTimes.com.")
    time.sleep(2)


def menu():
    print("\nChoose News Source (type number)")
    time.sleep(2)
    print("(1) CNN.com")
    print("(2) BBC.com")
    print("(3) CNBC.com")
    print("(4) NYTimes.com")
    print("Press C to search by keyword")
    time.sleep(1)
    print("Press Q to Quit\n")
    time.sleep(3)
    sourceSorter()


def sourceSorter():
    c = ""
    while c not in ["1", "2", "3", "4", "q", "Q", "quit", "c", "C"]:
        c = input("Enter choice: ").lower()
    if c == "1":
        cnn()
    if c == "2":
        bbc()
    if c == "3":
        cnbc()
    if c == "4":
        nytimes()
    if c == "q" or c == "quit":
        quit()
    if c == "c" or c == "C":
        custom()


def custom():
    compiled = []
    print("")
    term = input("Enter keyword: ")
    print("")
    reader = make_reader('db.sqlite2')
    urls = ["http://rss.cnn.com/rss/cnn_topstories.rss",
            "http://feeds.bbci.co.uk/news/rss.xml",
            "https://www.cnbc.com/id/100003114/device/rss/rss.html",
            "https://rss.nytimes.com/services/xml/rss/nyt/HomePage.xml"]
    for url in urls:
        try:
            reader.add_feed(url)
        except FeedExistsError:
            reader.remove_feed(url)
            reader.add_feed(url)
        reader.update_feeds()

        entries = list(reader.get_entries())
        limit = 6
        while limit > 0:
            for entry in entries:
                if term in entry.title.lower() or term in entry.summary.lower():
                    if entry not in compiled:
                        compiled.append(entry)
                    limit = limit - 1

    if len(compiled) == 0:
        print("No results.")
        custom()

    count = 1
    for each in compiled:
        print(str(count) + ".", each.title)
        count += 1

    time.sleep(5)
    print("\nWould you like to see more about a certain article?")
    choice = input("Enter article number or Q to quit: ")
    if choice == "Q" or choice == "q":
        quit()
    else:
        choice2 = 0
        while choice2 < 1 or choice2 > 2:
            choice2 = int(input("Press 1 to see more about article or 2 to open article: "))
        if choice2 == 1:
            print("\n" + compiled[int(choice) - 1].summary + "\n")
            time.sleep(4)
            choice3 = input("Would you like to open this article? (Y/N): ").lower()
            if choice3 == "y" or choice3 == "yes":
                webbrowser.open(compiled[int(choice) - 1].link)
        else:
            webbrowser.open(compiled[int(choice) - 1].link)

    restart = input("New search? (Y/N): ").lower()
    if restart == "y" or restart == "yes":
        custom()
    else:
        quit()

def cnn():
    print("\nChoose News Type (type number)")
    time.sleep(2)
    print("(1) Top Stories")
    print("(2) World")
    print("(3) U.S.")
    print("(4) Politics")
    print("(5) Technology")
    print("(6) Health")
    print("(7) Entertainment")
    time.sleep(1)
    print("Press Q to Quit\n")
    time.sleep(3)

    urls = ["http://rss.cnn.com/rss/cnn_topstories.rss",
            "http://rss.cnn.com/rss/cnn_world.rss",
            "http://rss.cnn.com/rss/cnn_us.rss",
            "http://rss.cnn.com/rss/cnn_allpolitics.rss",
            "http://rss.cnn.com/rss/cnn_tech.rss",
            "http://rss.cnn.com/rss/cnn_health.rss",
            "http://rss.cnn.com/rss/cnn_showbiz.rss"]
    createURL(urls)

def bbc():
    print("\nChoose News Type (type number)")
    time.sleep(2)
    print("(1) Top Stories")
    print("(2) World")
    print("(3) U.K.")
    print("(4) Politics")
    print("(5) Technology")
    print("(6) Health")
    print("(7) Entertainment")
    time.sleep(1)
    print("Press Q to Quit\n")
    time.sleep(3)

    urls = ["http://feeds.bbci.co.uk/news/rss.xml",
            "http://feeds.bbci.co.uk/news/world/rss.xml",
            "http://feeds.bbci.co.uk/news/uk/rss.xml",
            "http://feeds.bbci.co.uk/news/politics/rss.xml",
            "http://feeds.bbci.co.uk/news/technology/rss.xml",
            "http://feeds.bbci.co.uk/news/health/rss.xml",
            "http://feeds.bbci.co.uk/news/entertainment_and_arts/rss.xml"]
    createURL(urls)


def cnbc():
    print("\nChoose News Type (type number)")
    time.sleep(2)
    print("(1) Top Stories")
    print("(2) World")
    print("(3) U.S.")
    print("(4) Politics")
    print("(5) Technology")
    print("(6) Health")
    print("(7) Entertainment")
    time.sleep(1)
    print("Press Q to Quit\n")
    time.sleep(3)

    urls = ["https://www.cnbc.com/id/100003114/device/rss/rss.html",
            "https://www.cnbc.com/id/100727362/device/rss/rss.html",
            "https://www.cnbc.com/id/15837362/device/rss/rss.html",
            "https://www.cnbc.com/id/10000113/device/rss/rss.html",
            "https://www.cnbc.com/id/19854910/device/rss/rss.html",
            "https://www.cnbc.com/id/10000108/device/rss/rss.html",
            "https://www.cnbc.com/id/10000110/device/rss/rss.html"]
    createURL(urls)


def nytimes():
    print("\nChoose News Type (type number)")
    time.sleep(2)
    print("(1) Top Stories")
    print("(2) World")
    print("(3) U.S.")
    print("(4) Politics")
    print("(5) Technology")
    print("(6) Health")
    print("(7) Entertainment")
    time.sleep(1)
    print("Press Q to Quit\n")
    time.sleep(3)

    urls = ["https://rss.nytimes.com/services/xml/rss/nyt/HomePage.xml",
            "https://rss.nytimes.com/services/xml/rss/nyt/World.xml",
            "https://rss.nytimes.com/services/xml/rss/nyt/US.xml",
            "https://rss.nytimes.com/services/xml/rss/nyt/Politics.xml",
            "https://rss.nytimes.com/services/xml/rss/nyt/Technology.xml",
            "https://www.nytimes.com/services/xml/rss/nyt/Health.xml",
            "https://rss.nytimes.com/services/xml/rss/nyt/Arts.xml"]
    createURL(urls)


def createURL(urls):
    choice = input("Enter choice: ")
    choices = list("1234567")
    if choice in choices:
        url = urls[choices.index(choice)]
        feed(url)
    else:
        if choice == "q" or choice == "Q" or choice == "quit":
            quit()
        else:
            print("Error: Retype choice.")
            time.sleep(1)
            createURL()


def feed(url):
    print("")
    reader = make_reader('db.sqlite2')

    try:
        reader.add_feed(url)
    except FeedExistsError:
        reader.remove_feed(url)
        reader.add_feed(url)
    reader.update_feeds()

    entries = list(reader.get_entries())
    count = 1
    for entry in entries[:25]:
        print(str(count) + ".", entry.title)
        count += 1

    time.sleep(5)
    print("\nWould you like to see more about a certain article?")
    choice = input("Enter article number or Q to quit: ")
    if choice == "Q" or choice == "q":
        quit()
    else:
        choice2 = 0
        while choice2 < 1 or choice2 > 2:
            choice2 = int(input("Press 1 to see more about article or 2 to open article: "))
        if choice2 == 1:
            print("\n" + entries[int(choice) - 1].summary + "\n")
            time.sleep(4)
            choice3 = input("Would you like to open this article? (Y/N): ").lower()
            if choice3 == "y" or choice3 == "yes":
                webbrowser.open(entries[int(choice) - 1].link)
        else:
            webbrowser.open(entries[int(choice) - 1].link)


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