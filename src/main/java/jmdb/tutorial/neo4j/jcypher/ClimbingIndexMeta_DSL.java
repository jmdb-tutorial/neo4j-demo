package jmdb.tutorial.neo4j.jcypher;

import iot.jcypher.query.JcQuery;
import iot.jcypher.query.writer.Format;
import jmdb.tutorial.neo4j.jcypher.meta.NodeAttributeMeta;
import jmdb.tutorial.neo4j.jcypher.meta.NodeTypeMeta;
import jmdb.tutorial.neo4j.jcypher.meta.NodeTypeMetaContainer;
import jmdb.tutorial.neo4j.jcypher.meta.RelTypeMeta;

import static jmdb.tutorial.neo4j.jcypher.ClimbingIndexMeta_DSL.AttributeNames.*;
import static jmdb.tutorial.neo4j.jcypher.ClimbingIndexMeta_DSL.NodeTypes.*;
import static jmdb.tutorial.neo4j.jcypher.ClimbingIndexMeta_DSL.RelTypes.*;
import static jmdb.tutorial.neo4j.jcypher.meta.MetaModelBuilder.metaModel;

public class ClimbingIndexMeta_DSL {

    public static String asCypher(Format format) {
        return iot.jcypher.util.Util.toCypher(asJcQuery(), format);
    }

    public static JcQuery asJcQuery() {
        return metaModel(NodeTypes.class)
                .relate(PITCH, ON, ROUTE)
                .relate(CLIMB, COMPLETED, PITCH)
                .relate(PITCH, GRADED, GRADE)
                .relate(ROUTE, GRADED, GRADE)
                .asJcQuery();
    }

    public enum NodeTypes implements NodeTypeMeta {
        PITCH(name, description),
        ROUTE(name, description, number, length),
        CLIMB,
        GRADE(system, value);


        private final NodeTypeMetaContainer meta = new NodeTypeMetaContainer();

        NodeTypes(NodeAttributeMeta... attributes) {meta.registerAttributes(this, attributes);}

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
