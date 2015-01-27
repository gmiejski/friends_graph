package friends.reader.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Person {

    public static final String LABEL = "Person";

    private String id;
    private String firstname;
    private String surname;
    private Sex sex;

    public Person(String id, String firstname, String surname, Sex sex) {
        this.id = id;
        this.firstname = firstname;
        this.surname = surname;
        this.sex = sex;
    }

    public String getId() {
        return id;
    }

    public Map<String, Object> properties() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("id", id);
        properties.put("firstname", firstname);
        properties.put("surname", surname);
        properties.put("sex", Optional.ofNullable(sex).map(Sex::toString).orElse(""));

        return properties;
    }
}
