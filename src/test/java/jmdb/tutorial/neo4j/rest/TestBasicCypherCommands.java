package jmdb.tutorial.neo4j.rest;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Base64;

import static java.lang.String.format;
import static java.lang.Thread.currentThread;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * You'll need to make sure your neo4j user has the password 'neo4jdemo'
 * <p>
 * https://jersey.java.net/documentation/1.19/client-api.html#d4e621
 * <p>
 * https://jersey.java.net/nonav/documentation/latest/user-guide.html#d0e5274
 */
public class TestBasicCypherCommands {

    public static final String SERVER_ROOT_URI = "http://localhost:7474/";
    private Client client;

    @Before
    public void init_webclient() {

        ClientConfig config = new DefaultClientConfig();
        client = Client.create(config);


    }


    @Test
    public void delete_all_nodes() {
        executeCypherStatement(client, "delete_all_nodes.cypher");
    }

    /**
     * GET http://localhost:7474/user/neo4j
     */
    @Test
    public void get_user_info() {
        final String uri = SERVER_ROOT_URI + "user/neo4j";
        WebResource resource = client.resource(uri);


        ClientResponse response = resource
                .header("Authorization", "Basic " + credentials("neo4j", "neo4jdemo"))
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
        final String uri = SERVER_ROOT_URI + "db/data";
        WebResource resource = client.resource(uri);


        ClientResponse response = resource
                .header("Authorization", "Basic " + credentials("neo4j", "neo4jdemo"))
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
        executeCypherStatement(client, "create_meta_model.cypher");
    }

    public static String credentials(String username, String password) {
        return new String(Base64.getEncoder().encode((username + ":" + password).getBytes()));
    }


    private static void executeCypherStatement(Client client, String statementFileName) {
        String statement = loadStatement(statementFileName).replaceAll("\\n", " ").replaceAll("\"", "\\\\\"");
        final String txUri = SERVER_ROOT_URI + "db/data/transaction/commit";
        WebResource resource = client.resource(txUri);


        String payload = "{\"statements\" : [ {\"statement\" : \"" + statement + "\"} ]}";
        ClientResponse response = resource
                .header("Authorization", "Basic " + credentials("neo4j", "neo4jdemo"))
                .accept(APPLICATION_JSON)
                .type(APPLICATION_JSON)
                .entity(payload)
                .post(ClientResponse.class);

        System.out.println(format(
                "POST [%s] to [%s], status code [%d], returned data: "
                        + System.getProperty("line.separator") + "%s",
                payload, txUri, response.getStatus(),
                response.getEntity(String.class)));

        response.close();
    }

    /**
     * @param statementFileName
     * @return
     */
    private static String loadStatement(String statementFileName) {
        String fqn = TestBasicCypherCommands.class.getPackage().getName().replaceAll("\\.", "/") + "/" + statementFileName;
        InputStream resourceAsStream = currentThread().getContextClassLoader().getResourceAsStream(fqn);

        if (resourceAsStream == null) {
            throw new RuntimeException(format("Could not find resource [%s]", fqn));
        }
        StringBuffer sb = new StringBuffer();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(resourceAsStream, "UTF-8"));
            for (int c = br.read(); c != -1; c = br.read()) sb.append((char) c);
        } catch (Exception e) {
            throw new RuntimeException(format("Could not load file [%s] (See stack trace)", fqn), e);
        } finally {
            tryToClose(resourceAsStream);
        }
        return sb.toString();

    }

    private static void tryToClose(InputStream resourceAsStream) {
        try {
            resourceAsStream.close();
        } catch (IOException e) {
            throw new RuntimeException("Could not close stream (See stack trace)", e);
        }
    }
}
