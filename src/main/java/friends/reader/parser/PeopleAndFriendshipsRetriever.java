package friends.reader.parser;

import friends.reader.domain.Friendship;
import friends.reader.domain.Person;

import java.util.List;

public class PeopleAndFriendshipsRetriever {

    private FileLinesParser<Person> peopleFileLinesParser = new PeopleFileLinesParser();
    private FileLinesParser<Friendship> friendshipsFileLinesParser = new FriendshipsFileLinesParser();

    private FileLinesParser activeFileLinesParser = peopleFileLinesParser;

    public void parseLine(String line) {
        if (line.startsWith("nodedef")) {
            activeFileLinesParser = peopleFileLinesParser;
        } else if (line.startsWith("edgedef")) {
            activeFileLinesParser = friendshipsFileLinesParser;
        } else {
            activeFileLinesParser.add(line);
        }
    }

    public List<Person> getPeople() {
        return peopleFileLinesParser.retrieve();
    }

    public List<Friendship> getFriendships() {
        return friendshipsFileLinesParser.retrieve();
    }
}
