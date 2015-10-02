package jmdb.tutorial.neo4j.rest;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import org.junit.Before;
import org.junit.Test;

import static java.lang.String.format;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * You'll need to make sure your neo4j user has the password 'neo4jdemo'
 * <p>
 * https://jersey.java.net/documentation/1.19/client-api.html#d4e621
 * <p>
 * https://jersey.java.net/nonav/documentation/latest/user-guide.html#d0e5274
 */
public class TestBasicCypherCommands {

    private Client client;

    @Before
    public void init_webclient() {

        ClientConfig config = new DefaultClientConfig();
        client = Client.create(config);


    }


    @Test
    public void delete_all_nodes() {
        Neo4JClient.executeCypherStatement(client, "delete_all_nodes.cypher");
    }

    /**
     * GET http://localhost:7474/user/neo4j
     */
    @Test
    public void get_user_info() {
        final String uri = Neo4JClient.SERVER_ROOT_URI + "user/neo4j";
        WebResource resource = client.resource(uri);


        ClientResponse response = resource
                .header("Authorization", "Basic " + Neo4JClient.credentials("neo4j", "neo4jdemo"))
                .accept(APPLICATION_JSON)
                .type(APPLICATION_JSON)
                .get(ClientResponse.class);

        System.out.println(format(
                "GET on [%s], status code [%d], returned data: "
                        + System.getProperty("line.separator") + "%s",
                uri, response.getStatus(),
                response.getEntity(String.class)));

        response.close();
    }

    /**
     *
     */
    @Test
    public void get_service_root() {
        final String uri = Neo4JClient.SERVER_ROOT_URI + "db/data";
        WebResource resource = client.resource(uri);


        ClientResponse response = resource
                .header("Authorization", "Basic " + Neo4JClient.credentials("neo4j", "neo4jdemo"))
                .accept(APPLICATION_JSON)
                .type(APPLICATION_JSON)
                .get(ClientResponse.class);

        System.out.println(format(
                "GET on [%s], status code [%d], returned data: "
                        + System.getProperty("line.separator") + "%s",
                uri, response.getStatus(),
                response.getEntity(String.class)));

        response.close();
    }

    @Test
    public void create_meta_model() {
        Neo4JClient.executeCypherStatement(client, "create_meta_model.cypher");
    }

    @Test
    public void create_sample_data() {
        Neo4JClient.executeCypherStatement(client, "create_sample_data.cypher");
    }


}
