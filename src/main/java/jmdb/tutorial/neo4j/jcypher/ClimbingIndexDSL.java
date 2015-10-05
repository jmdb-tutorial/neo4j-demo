package jmdb.tutorial.neo4j.jcypher;

import iot.jcypher.query.JcQuery;

import static jmdb.tutorial.neo4j.jcypher.ClimbingIndexDSL.AttributeNames.*;
import static jmdb.tutorial.neo4j.jcypher.ClimbingIndexDSL.NodeTypes.*;
import static jmdb.tutorial.neo4j.jcypher.ClimbingIndexDSL.RelTypes.*;
import static jmdb.tutorial.neo4j.jcypher.MetaModelBuilder.metaModel;

public class ClimbingIndexDSL {

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
