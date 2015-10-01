package jmdb.tutorial.neo4j.embedded;

import org.neo4j.graphdb.*;

import static java.lang.System.out;

/**
 * http://www.neo4j.org/graphgist?8640853
 */
public class ClimbingIndexGraph {

    private static enum RelTypes implements RelationshipType
    {
        WITHIN,
        COMPOSED_OF,
        CLIMBED,
        COVERED,
        BOUNDED_BY,
        AT_A,
        IN_AN,
        LOCATED_AT

    }

    public void initialiseIn(GraphDatabaseService graphDb) {
        Node pitch;
        Node route;
        Relationship relationship;

        try ( Transaction tx = graphDb.beginTx() )
        {
            pitch = graphDb.createNode();
            pitch.setProperty( "number", 1 );
            route = graphDb.createNode();
            route.setProperty("name", "Anvil Chorus");

            relationship = pitch.createRelationshipTo( route, RelTypes.COMPOSED_OF );
            relationship.setProperty( "message", "brave Neo4j " );
            tx.success();

            out.println("Setup Basic graph.");
        }
    }

    public boolean existsIn(GraphDatabaseService graphDb) {
        return false;
    }
}
