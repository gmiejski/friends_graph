package friends.reader.parser;

import friends.reader.domain.Friendship;

import java.util.List;
import java.util.stream.Collectors;

public class FriendshipsFileLinesParser extends AbstractFileLinesParser<Friendship> {

    @Override
    public List<Friendship> retrieve() {
        return getLines().stream().map(FriendshipsFileLinesParser::toFriendship).collect(Collectors.toList());
    }

    private static Friendship toFriendship(String line) {
        String[] friendsIds = line.split(",");
        if (friendsIds.length != 2) {
            return null;
        }
        return new Friendship(friendsIds[0], friendsIds[1]);
    }
}
