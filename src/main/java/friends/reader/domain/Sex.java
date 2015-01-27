package friends.reader.domain;

import static java.util.Arrays.stream;

public enum Sex {
    MALE, FEMALE;

    public static Sex fromString(String sexName) {
        return stream(Sex.values()).filter(x -> x.toString().toLowerCase().equals(sexName.toLowerCase())).findFirst().orElse(null);
    }
}
