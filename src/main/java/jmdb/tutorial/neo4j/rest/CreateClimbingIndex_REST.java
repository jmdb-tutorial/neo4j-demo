package jmdb.tutorial.neo4j.rest;

public class CreateClimbingIndex_REST {

    public static void main(String[] args) {
        new CreateClimbingIndex_REST().run("http://localhost:7474");
    }

    private void run(String rootUri) {

        Neo4JClient neo4j = new Neo4JClient(rootUri);

        neo4j.checkConnection();

        neo4j.runCypher("delete_all_nodes");
        neo4j.runCypher("create_meta_model");
        neo4j.runCypher("create_sample_data");
    }

}
