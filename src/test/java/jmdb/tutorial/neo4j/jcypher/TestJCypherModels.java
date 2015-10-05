package jmdb.tutorial.neo4j.jcypher;

import org.junit.Test;

public class TestJCypherModels {

    @Test
    public void creates_meta_model() {
        System.out.println(RawCypherClimbingIndexMeta.cypher());
    }

}
