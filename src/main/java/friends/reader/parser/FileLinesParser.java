package friends.reader.parser;

import java.util.List;

public interface FileLinesParser<T> {

    List<String> getLines();

    void add(String line);

    List<T> retrieve();
}
