package jmdb.tutorial.neo4j.embedded;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import static java.lang.String.format;
import static java.lang.System.out;
import static java.lang.Thread.sleep;

/**
 * Taken from http://neo4j.com/docs/stable/tutorials-java-embedded-setup.html
 */
public class CreateClimbingIndex_Embedded {

    private GraphDatabaseService graphDb;

    /**
     * Make sure you own /var/neo4j-demo folder!
     * @param args
     */
    public static void main(String[] args) {
        out.println("Going to setup and run an embedded neo4j database...");

        new CreateClimbingIndex_Embedded().run("/var/neo4j-demo/embedded-index");
    }

    private void run(String dbPath) {
        graphDb = initEmbeddedDb(dbPath);


        ClimbingIndexGraph climbingIndexGraph = new ClimbingIndexGraph();

        if (!climbingIndexGraph.existsIn(graphDb)) {
            climbingIndexGraph.initialiseIn(graphDb);
        }

    }

    private static GraphDatabaseService initEmbeddedDb(String dbPath) {
        out.println(format("Creating or opening db at [%s]...", dbPath));
        GraphDatabaseService graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(dbPath);
        registerShutdownHook(graphDb);
        out.println("Opened graphdb ok.");
        return graphDb;
    }


    private static void registerShutdownHook( final GraphDatabaseService graphDb )
    {
        // Registers a shutdown hook for the Neo4j instance so that it
        // shuts down nicely when the VM exits (even if you "Ctrl-C" the
        // running application).
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                graphDb.shutdown();
                out.println("Shutdown graphdb.");
            }
        });
    }
}
