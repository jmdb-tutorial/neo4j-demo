package jmdb.tutorial.neo4j.jcypher;

import iot.jcypher.query.writer.Format;
import jmdb.tutorial.neo4j.rest.Neo4JClient;

public class CreateClimbingIndex_REST_DSL {

    public static void main(String[] args) {
        new CreateClimbingIndex_REST_DSL().run("http://localhost:7474");
    }

    private void run(String rootUri) {

        Neo4JClient neo4j = new Neo4JClient(rootUri);

        neo4j.checkConnection();

        neo4j.runCypherFromFile("delete_all_nodes");
        neo4j.runCypherStatement(ClimbingIndexMeta_DSL.asCypher(Format.NONE));

    }

}
