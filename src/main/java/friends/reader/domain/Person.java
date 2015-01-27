package friends.reader.domain;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Person {

    public static final String LABEL = "Person";
    public static final String GETTER_METHOD_PREFIX = "get";

    private String firstname;
    private String surname;
    private Sex sex;
    private Set<Person> friends;

    public Person(String firstname, String surname, Sex sex) {
        this.firstname = firstname;
        this.surname = surname;
        this.sex = sex;
        friends = new HashSet<>();
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

        for (Method method : this.getClass().getMethods()) {
            if (isGetterMethod(method)) {
                try {
                    Object result = method.invoke(this);
                    properties.put(method.getName().substring(3).toLowerCase(), result);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

        return properties;
    }

    private boolean isGetterMethod(Method method) {
        return method.getName().startsWith(GETTER_METHOD_PREFIX) && method.getName().length() > GETTER_METHOD_PREFIX.length() && method.getParameterCount() == 0 && !method.getName().toLowerCase().contains("class");
    }
}
