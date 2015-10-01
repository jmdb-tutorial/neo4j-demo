package jmdb.tutorial.neo4j.rest;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import static com.sun.jersey.api.client.Client.create;
import static java.lang.System.out;

public class CreateClimbingMeta_REST {

    public static void main(String[] args) {
        new CreateClimbingMeta_REST().run("http://localhost:7474");
    }

    private void run(String rootUri) {
        checkConnection(rootUri);



    }

    private void checkConnection(String rootUri) {
        WebResource resource = create().resource(rootUri);
        ClientResponse response = null;
        try {
             response = resource.get(ClientResponse.class);

            out.println(String.format("GET on [%s], status code [%d]", rootUri, response.getStatus()));

            if (response.getStatus() != 200) {
                throw new RuntimeException("Ouch, server isn't running :(");
            }

            out.println("Server is available :)");
        } finally {
            response.close();
        }

    }
}
