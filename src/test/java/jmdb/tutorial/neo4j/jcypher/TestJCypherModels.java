package jmdb.tutorial.neo4j.jcypher;

import iot.jcypher.query.writer.Format;
import org.junit.Test;

import static java.lang.System.out;

public class TestJCypherModels {

    @Test
    public void creates_meta_model() {
        out.println(RawCypherClimbingIndexMeta.cypher(Format.PRETTY_3));
    }

    @Test
    public void create_meta_model_with_dsl() {
        out.println(ClimbingIndexDSL.asCypher(Format.PRETTY_3));
    }

}
