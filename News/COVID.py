from reader import make_reader, FeedExistsError
import time
import webbrowser


def setup():
    print("\nCOVID News Feed V3.0.4")
    time.sleep(2)
    print("Created by Gautam Sirdeshmukh")
    print("Data sourced from CNN.com, BBC.com, CNBC.com and NYTimes.com.")
    time.sleep(2)


def menu():
    print("\nPress (1) to begin search")
    print("Press (2) to quit\n")
    time.sleep(3)

    choice = "p"
    while choice not in "12":
        choice = input("Enter choice: ")
    if choice == "1":
        engine()
    if choice == "2":
        quit()


def engine():
    compiled = []
    print("")
    terms = ["covid", "coronavirus", "covid-19"]
    print("")
    reader = make_reader('db.sqlite3')
    urls = ["http://rss.cnn.com/rss/cnn_topstories.rss",
            "http://feeds.bbci.co.uk/news/rss.xml",
            "https://www.cnbc.com/id/100003114/device/rss/rss.html",
            "https://rss.nytimes.com/services/xml/rss/nyt/HomePage.xml"]
    for term in terms:
        for url in urls:
            try:
                reader.add_feed(url)
            except FeedExistsError:
                reader.remove_feed(url)
                reader.add_feed(url)
            reader.update_feeds()

            entries = list(reader.get_entries())
            limit = 4
            while limit > 0:
                for entry in entries:
                    if term in entry.title.lower() or term in entry.summary.lower():
                        if entry not in compiled:
                            compiled.append(entry)
                        limit = limit - 1

    if len(compiled) == 0:
        print("No results.")
        engine()

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
            choice2 = int(input("Press (1) to see more about article or (2) to open article: "))
        if choice2 == 1:
            print("\n" + compiled[int(choice) - 1].summary + "\n")
            time.sleep(4)
            choice3 = input("Would you like to open this article? (Y/N): ").lower()
            if choice3 == "y" or choice3 == "yes":
                webbrowser.open(compiled[int(choice) - 1].link)
        else:
            webbrowser.open(compiled[int(choice) - 1].link)

    restart = input("Restart search? (Y/N): ").lower()
    if restart == "y" or restart == "yes":
        engine()
    else:
        quit()


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
