package friends.reader;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import friends.reader.domain.Person;
import friends.reader.domain.Sex;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.rest.graphdb.RestAPI;
import org.neo4j.rest.graphdb.RestAPIFacade;
import org.neo4j.rest.graphdb.RestGraphDatabase;
import org.neo4j.rest.graphdb.entity.RestNode;
import org.neo4j.rest.graphdb.query.QueryEngine;
import org.neo4j.rest.graphdb.query.RestCypherQueryEngine;
import org.neo4j.rest.graphdb.util.QueryResult;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

import static friends.reader.domain.Friendship.FRIENDSHIP_LABEL;


public class MainProgram {

    private static final String SERVER_ROOT_URI = "http://localhost:7474/db/data";
    private static final int RESPONSE_PROPER_STATUS = 200;

    public static void main(String[] args) {
        checkIfConnectionIsEstablished();

        RestAPI graphDb = new RestAPIFacade(SERVER_ROOT_URI);
        GraphDatabaseService graphDb2 = new RestGraphDatabase(SERVER_ROOT_URI);

        Object totalNodesCount = getTotalNodesCount(graphDb);
        System.out.print("Total nodes: " + totalNodesCount);

        RestNode personNode = createPersonNode(graphDb, createPerson("AAA", "BBB", Sex.MALE));
        RestNode personNode2 = createPersonNode(graphDb, createPerson("CCC", "DDD", Sex.FEMALE));

        addFriendshipEdge(graphDb, personNode, personNode2);
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

    private static Person createPerson(String firstname, String lastname, Sex sex) {
        return new Person(firstname, lastname, sex);
    }

    private static Integer getTotalNodesCount(RestAPI graphDb) {
        QueryEngine engine = new RestCypherQueryEngine(graphDb);
        QueryResult<Map<String, Object>> result = engine.query("match(n) return count(n) as total;", Collections.EMPTY_MAP);
        Iterator<Map<String, Object>> iterator = result.iterator();
        if (iterator.hasNext()) {
            Map<String, Object> row = iterator.next();
            return (Integer) row.get("total");
        }
        return null;
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
