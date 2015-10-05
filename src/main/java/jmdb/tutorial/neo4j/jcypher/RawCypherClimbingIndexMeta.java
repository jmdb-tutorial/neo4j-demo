package jmdb.tutorial.neo4j.jcypher;

import iot.jcypher.query.JcQuery;
import iot.jcypher.query.api.IClause;
import iot.jcypher.query.factories.clause.CREATE;
import iot.jcypher.query.values.JcNode;
import iot.jcypher.query.values.JcPath;
import iot.jcypher.query.writer.Format;

public class RawCypherClimbingIndexMeta {

    private final String cypherQuery;

    public RawCypherClimbingIndexMeta() {


        JcQuery query = new JcQuery();

        JcNode pitch = new JcNode("pitch");
        JcNode route = new JcNode("route");
        JcNode relOn = new JcNode("on");

        JcPath routes = new JcPath("routes");

        query.setClauses(new IClause[] {
                CREATE.node(pitch).label("META").label("Nodes").property("type").value("PITCH").property("name").value("PITCH"),
                CREATE.node(route).label("META").label("Nodes").property("type").value("ROUTE").property("name").value("ROUTE"),

                CREATE.path(routes).node(pitch).relation().type("FROM_NODE")
                                    .out().node(relOn).label("META").label("META").property("type").value("ON").property("name").value("ON")
                                    .relation().type("TO_NODE").out().node(route)

        });



        this.cypherQuery = iot.jcypher.util.Util.toCypher(query, Format.NONE);

    }

    public static String cypher() {
        return new RawCypherClimbingIndexMeta().cypherQuery;
    }
}
