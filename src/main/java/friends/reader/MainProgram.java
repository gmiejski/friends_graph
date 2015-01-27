package friends.reader;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import friends.reader.domain.Friendship;
import friends.reader.domain.Person;
import friends.reader.parser.PeopleAndFriendshipsRetriever;
import org.neo4j.rest.graphdb.RestAPI;
import org.neo4j.rest.graphdb.RestAPIFacade;
import org.neo4j.rest.graphdb.entity.RestNode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static friends.reader.domain.Friendship.FRIENDSHIP_LABEL;
import static java.util.stream.Collectors.toMap;


public class MainProgram {

    private static final String SERVER_ROOT_URI = "http://localhost:7474/db/data";
    private static final int RESPONSE_PROPER_STATUS = 200;

    public static void main(String[] args) {
        checkIfConnectionIsEstablished();
        RestAPI graphDb = new RestAPIFacade(SERVER_ROOT_URI);

        try {
            List<String> lines = Files.readAllLines(Paths.get("./src/main/resources/friends.gdf"));
            PeopleAndFriendshipsRetriever peopleAndFriendshipsRetriever = new PeopleAndFriendshipsRetriever();

            lines.stream().forEach(peopleAndFriendshipsRetriever::parseLine);

            List<Friendship> friendships = peopleAndFriendshipsRetriever.getFriendships();
            List<Person> people = peopleAndFriendshipsRetriever.getPeople();

            Map<String, RestNode> peopleNodesById = people.stream().collect(toMap(Person::getId, x -> createPersonNode(graphDb, x)));
            friendships.stream().forEach(friendship -> addFriendshipEdge(graphDb, peopleNodesById.get(friendship.getFriendId1()), peopleNodesById.get(friendship.getFriendId2())));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addFriendshipEdge(RestAPI graphDb, RestNode personNode, RestNode personNode2) {
        graphDb.createRelationship(personNode, personNode2, () -> FRIENDSHIP_LABEL, null);
    }

    private static RestNode createPersonNode(RestAPI graphDb, Person person) {
        Map<String, Object> properties = person.properties();

        RestNode personNode = graphDb.createNode(properties);
        personNode.addLabel(() -> Person.LABEL);

        return personNode;
    }

    private static void checkIfConnectionIsEstablished() {
        WebResource resource = Client.create().resource(SERVER_ROOT_URI);
        ClientResponse response = resource.get(ClientResponse.class);
        System.out.println(String.format("GET on [%s], status code [%d]", SERVER_ROOT_URI, response.getStatus()));
        try {
            if (response.getStatus() != RESPONSE_PROPER_STATUS) {
                System.exit(-1);
            }
        } finally {
            response.close();
        }
    }
}
