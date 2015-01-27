package friends.reader.parser;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractFileLinesParser<T> implements FileLinesParser<T> {

    private final List<String> lines;

    public AbstractFileLinesParser() {
        this.lines = new ArrayList<>();
    }

    @Override
    public List<String> getLines() {
        return lines;
    }

    @Override
    public void add(String line) {
        lines.add(line);
    }
}
