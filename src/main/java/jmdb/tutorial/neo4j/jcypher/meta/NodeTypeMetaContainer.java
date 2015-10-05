package jmdb.tutorial.neo4j.jcypher.meta;

import jmdb.tutorial.neo4j.jcypher.ClimbingIndexMeta_DSL;

import java.util.HashMap;
import java.util.Map;

public class NodeTypeMetaContainer {

    private final Map<String, String[]> attributeMap = new HashMap<>();


    public NodeTypeMetaContainer() {
    }


    public void registerAttributes(ClimbingIndexMeta_DSL.NodeTypes nodeTypes, NodeAttributeMeta[] meta) {

    }
}
