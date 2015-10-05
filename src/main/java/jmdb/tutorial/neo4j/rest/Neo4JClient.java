package jmdb.tutorial.neo4j.rest;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Base64;

import static com.sun.jersey.api.client.Client.create;
import static java.lang.String.format;
import static java.lang.System.out;
import static java.lang.Thread.currentThread;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

public class Neo4JClient {
    public static final String SERVER_ROOT_URI = "http://localhost:7474/";

    private final Client httpClient;
    private final String rootUri;

    public Neo4JClient(String rootUri) {
        this(rootUri, Client.create(new DefaultClientConfig()));
    }
    public Neo4JClient(String rootUri, Client httpClient) {
        this.httpClient = httpClient;
        this.rootUri = rootUri;
    }

    public  void checkConnection() {
        WebResource resource = create().resource(rootUri);
        ClientResponse response = null;
        try {
            response = resource.get(ClientResponse.class);

            out.println(format("GET %s HTTP/1.0\nHTTP/1.0 %d", rootUri, response.getStatus()));

            if (response.getStatus() != 200) {
                throw new RuntimeException("Ouch, server isn't running :(");
            }

            out.println("\nServer is available :)");
        } finally {
            response.close();
        }

    }

    public String runCypherFromFile(String filename) {
        return executeCypherStatementFromFile(httpClient, String.format("%s.%s", filename, "cypher"));
    }

    public String runCypherStatement(String statement) {
        return executeCypherStatement(httpClient, statement);
    }

    public static String executeCypherStatementFromFile(Client client, String statementFileName) {
        String statement = loadStatement(statementFileName).replaceAll("\\n", " ").replaceAll("\"", "\\\\\"");
        return executeCypherStatement(client, statement);
    }

    public static String executeCypherStatement(Client client, String statement) {
        final String txUri = SERVER_ROOT_URI + "db/data/transaction/commit";
        WebResource resource = client.resource(txUri);


        String payload = "{\"statements\" : [ {\"statement\" : \"" + statement + "\"} ]}";
        ClientResponse response = resource
                .header("Authorization", "Basic " + credentials("neo4j", "neo4jdemo"))
                .accept(APPLICATION_JSON)
                .type(APPLICATION_JSON)
                .entity(payload)
                .post(ClientResponse.class);

        String entity = response.getEntity(String.class);

        out.println(format(
                "POST %s HTTP/1.0\n%s\nHTTP/1.0 %d\n" + "%s\n",
                txUri, payload,  response.getStatus(),
                entity));

        response.close();
        return entity;
    }

    /**
     * @param statementFileName
     * @return
     */
    public static String loadStatement(String statementFileName) {
        String fqn = CreateClimbingIndex_REST.class.getPackage().getName().replaceAll("\\.", "/") + "/" + statementFileName;
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

    public static String credentials(String username, String password) {
        return new String(Base64.getEncoder().encode((username + ":" + password).getBytes()));
    }
}
