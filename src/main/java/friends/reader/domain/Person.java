package friends.reader.domain;

import java.lang.reflect.Method;
import java.util.*;

public class Person {

    public static final String LABEL = "Person";
    public static final String GETTER_METHOD_PREFIX = "get";

    private String firstname;
    private String surname;
    private Sex sex;
    private Set<Person> friends;
    private String id;

    public Person(String id, String firstname, String surname, Sex sex) {
        this.id = id;
        this.firstname = firstname;
        this.surname = surname;
        this.sex = sex;
        friends = new HashSet<>();
    }

    public String getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getSurname() {
        return surname;
    }

    public String getSex() {
        return sex.toString().toLowerCase();
    }

    public Map<String, Object> properties() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("id", id);
        properties.put("firstname", firstname);
        properties.put("surname", surname);
        properties.put("sex", Optional.ofNullable(sex).map(Sex::toString).orElse(""));

        return properties;
    }

    private boolean isGetterMethod(Method method) {
        return method.getName().startsWith(GETTER_METHOD_PREFIX) && method.getName().length() > GETTER_METHOD_PREFIX.length() && method.getParameterCount() == 0 && !method.getName().toLowerCase().contains("class");
    }
}
