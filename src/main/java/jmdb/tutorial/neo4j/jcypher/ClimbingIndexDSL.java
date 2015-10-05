package jmdb.tutorial.neo4j.jcypher;

import iot.jcypher.query.JcQuery;
import iot.jcypher.query.writer.Format;
import jmdb.tutorial.neo4j.jcypher.meta.NodeAttributeMeta;
import jmdb.tutorial.neo4j.jcypher.meta.NodeTypeMeta;
import jmdb.tutorial.neo4j.jcypher.meta.NodeTypeMetaContainer;
import jmdb.tutorial.neo4j.jcypher.meta.RelTypeMeta;

import static jmdb.tutorial.neo4j.jcypher.ClimbingIndexDSL.AttributeNames.*;
import static jmdb.tutorial.neo4j.jcypher.ClimbingIndexDSL.NodeTypes.*;
import static jmdb.tutorial.neo4j.jcypher.ClimbingIndexDSL.RelTypes.*;
import static jmdb.tutorial.neo4j.jcypher.meta.MetaModelBuilder.metaModel;

public class ClimbingIndexDSL {

    public static String asCypher(Format format) {
        return iot.jcypher.util.Util.toCypher(asJcQuery(), format);
    }

    public static JcQuery asJcQuery() {
        return metaModel(NodeTypes.class)
                .relate(PITCH, ON, ROUTE)
                .relate(PITCH, GRADED, GRADE)
                .relate(CLIMB, COMPLETED, PITCH)
                .asJcQuery();
    }

    public enum NodeTypes implements NodeTypeMeta {
        PITCH(name, description),
        ROUTE(name, description, number, length),
        CLIMB,
        GRADE(system, value);


        private final NodeTypeMetaContainer meta = new NodeTypeMetaContainer();

        NodeTypes(NodeAttributeMeta... attribute) {
            meta.registerAttributes(meta);
        }

    }

    public enum RelTypes implements RelTypeMeta {
        ON,
        COMPLETED,
        GRADED
    }

    public enum AttributeNames implements NodeAttributeMeta {
        name,
        description,
        number,
        length,
        system,
        value

    }


}
