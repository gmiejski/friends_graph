package friends.reader.gdfreader;

import friends.reader.domain.Friendship;

import java.util.ArrayList;
import java.util.List;

public class FriendshipsFileLinesParser extends AbstractFileLinesParser<Friendship> {

    @Override
    public List<Friendship> retrieve() {
        return new ArrayList<>();
    }
}
