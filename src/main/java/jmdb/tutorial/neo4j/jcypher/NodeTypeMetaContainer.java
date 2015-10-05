package jmdb.tutorial.neo4j.jcypher;

public class NodeTypeMetaContainer {

    private final NodeAttributeMeta[] attributes;


    public NodeTypeMetaContainer(NodeAttributeMeta... attributes) {
        this.attributes = attributes;
    }

    public NodeTypeMetaContainer() {
        this(new NodeAttributeMeta[]{});
    }

    public static NodeTypeMetaContainer nodeTypeMeta(NodeAttributeMeta... attributes) {
        return new NodeTypeMetaContainer(attributes);
    }

    public void registerAttributes(NodeTypeMetaContainer meta) {

    }
}
