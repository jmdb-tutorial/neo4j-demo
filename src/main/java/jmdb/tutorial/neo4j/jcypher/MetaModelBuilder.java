package jmdb.tutorial.neo4j.jcypher;

import iot.jcypher.query.JcQuery;

public class MetaModelBuilder {
    public static MetaModelBuilder metaModel(Class nodeTypeEnum) {
        return new MetaModelBuilder();
    }

    private MetaModelBuilder() {
    }

    public MetaModelBuilder relate(NodeTypeMeta fromNode, RelTypeMeta relType, NodeTypeMeta toNode) {
        return this;
    }

    public JcQuery asJcQuery() {
        return new JcQuery();
    }
}
