package friends.reader.domain;

public class Friendship {
    public static final String FRIENDSHIP_LABEL = "Friend";

    private final String friendId1;
    private final String friendId2;

    public Friendship(String friendId1, String friendId2) {
        this.friendId1 = friendId1;
        this.friendId2 = friendId2;
    }

    public String getFriendId2() {
        return friendId2;
    }

    public String getFriendId1() {
        return friendId1;
    }
}
