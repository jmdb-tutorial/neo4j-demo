package jmdb.tutorial.neo4j.jcypher.meta;

import iot.jcypher.query.JcQuery;
import iot.jcypher.query.api.IClause;
import iot.jcypher.query.factories.clause.CREATE;
import iot.jcypher.query.values.JcNode;

import java.lang.reflect.Field;
import java.util.*;

public class MetaModelBuilder {
    private static final IClause[] EMPTY_CLAUSES = new IClause[0];
    private static Map<String , JcNode> nodeIndex = new HashMap<>();
    private List<IClause> clauses = new ArrayList<>();

    public static MetaModelBuilder metaModel(Class<? extends NodeTypeMeta> nodeTypeEnumClazz) {
        return new MetaModelBuilder(nodeTypeEnumClazz);
    }

    private MetaModelBuilder(Class<? extends NodeTypeMeta> nodeTypeEnumClazz) {
        clauses.addAll(parseNodeTypes(nodeTypeEnumClazz));
    }

    public MetaModelBuilder relate(NodeTypeMeta fromNode, RelTypeMeta relType, NodeTypeMeta toNode) {
        return this;
    }

    public JcQuery asJcQuery() {

        JcQuery query = new JcQuery();

        query.setClauses(clauses.toArray(EMPTY_CLAUSES));

        return query;
    }

    private static List<IClause> parseNodeTypes(Class<? extends NodeTypeMeta> nodeTypeEnumClazz) {
        List<IClause> clauses = new ArrayList<>();
        for (Field f : nodeTypeEnumClazz.getFields()) {
            String nodeTypeName = f.getName();
            JcNode node = new JcNode(f.getName().toLowerCase());
            clauses.add(CREATE.node(node)
                        .label("META").label("Nodes")
                        .property("type").value(nodeTypeName)
                        .property("name").value(nodeTypeName));

            nodeIndex.put(f.getName(), node);

        }
        return clauses;
    }
}
