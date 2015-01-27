package friends.reader.gdfreader;

import friends.reader.domain.Person;
import friends.reader.domain.Sex;

import java.util.List;
import java.util.stream.Collectors;

public class PeopleFileLinesParser extends AbstractFileLinesParser<Person> {

    @Override
    public List<Person> retrieve() {
        return getLines().stream().map(this::toPerson).collect(Collectors.toList());
    }

    private Person toPerson(String x) {

        String[] strings = x.split(",");
        if (strings.length != 5) {
            return null;
        }
        String id = strings[0];
        String[] name = strings[1].split(" ");
        String firstname = name[0];
        String surname = name[1];
        Sex sex = Sex.fromString(strings[2]);
        return new Person(id, firstname, surname, sex);
    }

}
