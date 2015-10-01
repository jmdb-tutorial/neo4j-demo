package jmdb.tutorial.neo4j;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import static java.lang.String.format;
import static java.lang.System.out;

/**
 * Taken from http://neo4j.com/docs/stable/tutorials-java-embedded-setup.html
 */
public class CreateClimbingIndex {

    /**
     * Make sure you own /var/neo4j-demo folder!
     * @param args
     */
    public static void main(String[] args) {
        out.println("Going to setup and run an embedded neo4j database...");

        new CreateClimbingIndex().run("/var/neo4j-demo/climbing-index");
    }

    private void run(String dbPath) {
        out.println(format("Creating or opening db at [%s]...", dbPath));
        GraphDatabaseService graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(dbPath);
        registerShutdownHook(graphDb);
        out.println("Opened graphdb ok.");
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
