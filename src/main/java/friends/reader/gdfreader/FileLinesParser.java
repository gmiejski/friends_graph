package friends.reader.gdfreader;

import java.util.List;

public interface FileLinesParser<T> {

    List<String> getLines();

    void add(String line);

    List<T> retrieve();
}
