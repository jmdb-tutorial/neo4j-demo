package jmdb.tutorial.neo4j.jcypher;

import iot.jcypher.query.JcQuery;
import iot.jcypher.query.api.IClause;
import iot.jcypher.query.factories.clause.CREATE;
import iot.jcypher.query.values.JcNode;
import iot.jcypher.query.values.JcPath;
import iot.jcypher.query.writer.Format;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;
import static jmdb.tutorial.neo4j.jcypher.ClimbingIndexData_DSL.ClimbingGrade.NOT_GRADED;
import static jmdb.tutorial.neo4j.jcypher.ClimbingIndexData_DSL.UkSubjectiveGrades.UK_VS;
import static jmdb.tutorial.neo4j.jcypher.ClimbingIndexData_DSL.UkTechnicalGrades.UK_4b;
import static jmdb.tutorial.neo4j.jcypher.ClimbingIndexData_DSL.UkTechnicalGrades.UK_4c;
import static jmdb.tutorial.neo4j.jcypher.ClimbingIndexMeta_DSL.RelTypes.ON;

public class ClimbingIndexData_DSL {

    public static String asCypher(Format format) {
        return iot.jcypher.util.Util.toCypher(asJcQuery(), format);
    }

    public static JcQuery asJcQuery() {
        return new ClimbingIndexRouteBuilder()
                .route("Anvil Chorus", UK_VS)
                .pitch("10m", UK_4b, "Climb the wall and thin cracks directly to a tricky exit onto a large belay ledge next to a thorny bush.")
                .pitch("12m", UK_4b, "Take a good crack in a corner to a roof at its top and pull around this on the right to another good belay ledge.")
                .pitch("6m", NOT_GRADED, "Walk left along a narrow grassy ledge to a belay below a thin black seam, as for Venusburg.")
                .pitch("18m", UK_4c, "Move up right to the base of a steep corner and climb this with difficulty to a horizontal break that can be followed rightwards across the wall to a belay on its edge.")
                .pitch("8m", UK_4b, "Move up left to a break and climb the thin crack above to finish.")
                .buildIndex();
    }

    public interface ClimbingGrade {
        String key();

        ClimbingGrade NOT_GRADED = () -> "not-graded";
    }

    public interface SubjectiveClimbingGrade extends ClimbingGrade {

    }

    public interface TechnicalClimbingGrade extends ClimbingGrade {

    }

    public enum UkSubjectiveGrades implements SubjectiveClimbingGrade {
        UK_VS("VS");

        private final String key;

        UkSubjectiveGrades(String key) {
            this.key = key;
        }

        public String key() {
            return this.key;
        }


    }

    public enum UkTechnicalGrades implements TechnicalClimbingGrade {
        UK_4a("4a"),
        UK_4b("4b"),
        UK_4c("4c");

        private final String key;

        UkTechnicalGrades(String key) {
            this.key = key;
        }

        public String key() {
            return this.key;
        }


    }

    public static class ClimbingIndexRouteBuilder {

        private static final IClause[] EMPTY_CLAUSES = new IClause[0];

        List<IClause> clauses = new ArrayList<>();

        RouteBuilder currentRoute;

        public ClimbingIndexRouteBuilder() {
        }

        public RouteBuilder route(String name, ClimbingGrade climbingGrade) {
            currentRoute = new RouteBuilder(this, name, climbingGrade);
            clauses.add(currentRoute.build());
            return currentRoute;
        }


        public JcQuery buildIndex() {
            JcQuery query = new JcQuery();

            query.setClauses(clauses.toArray(EMPTY_CLAUSES));

            return query;
        }

        public void addClauses(IClause... clause) {
            clauses.addAll(Arrays.asList(clause));
        }
    }

    private static class RouteBuilder {
        private final ClimbingIndexRouteBuilder parent;

        private final String name;
        private final ClimbingGrade climbingGrade;
        private final JcNode route;
        private final String routeId;

        private int pitchNumber = 0;

        public RouteBuilder(ClimbingIndexRouteBuilder parent, String name, ClimbingGrade climbingGrade) {
            this.parent = parent;
            this.name = name;
            this.climbingGrade = climbingGrade;
            this.routeId = name.replaceAll("\\ ", "").toLowerCase();
            this.route = new JcNode(routeId);
        }

        public PitchBuilder pitch(String length, ClimbingGrade climbingGrade, String description) {
            PitchBuilder pitchBuilder = new PitchBuilder(this);

            parent.addClauses(pitchBuilder.length(length)
                    .climbingGrade(climbingGrade)
                    .description(description)
                    .build());

            return pitchBuilder;
        }

        ClimbingIndexRouteBuilder parent() {
            return parent;
        }

        IClause build() {
            return CREATE.node(route)
                        .label("route")
                        .property("name").value(name);
        }

        int nextPitch() {
            return ++pitchNumber;
        }


        JcNode route() {
            return route;
        }

        public String routeId() {
            return routeId;
        }
    }

    private static class PitchBuilder {
        private final RouteBuilder parent;
        private String length;
        private ClimbingGrade climbingGrade;
        private String description;

        public PitchBuilder(RouteBuilder parent) {
            this.parent = parent;
        }

        public PitchBuilder pitch(String length, ClimbingGrade climbingGrade, String description) {
            return parent.pitch(length, climbingGrade, description);
        }

        JcQuery buildIndex() {
            return parent.parent().buildIndex();
        }

        PitchBuilder length(String length) {
            this.length = length;
            return this;
        }

        PitchBuilder climbingGrade(ClimbingGrade climbingGrade) {
            this.climbingGrade = climbingGrade;
            return this;
        }

        PitchBuilder description(String description) {
            this.description = description;
            return this;
        }

        public IClause[] build() {

            int pitchNumber = parent.nextPitch();
            String pitchName = format("p%d", pitchNumber);
            JcNode route = parent.route();
            JcNode pitch = new JcNode(pitchName);
            JcPath path = new JcPath(pitchName + "_" + parent.routeId());

            return new IClause[]{
                    CREATE.path(path)
                            .node(pitch).label("pitch")
                                .property("name").value(pitchName.toUpperCase())
                                .property("number").value(pitchNumber)
                                .property("length").value(length)
                                .property("description").value(description)
                            .relation().type(ON.name()).out()
                            .node(parent.route())
            };

        }
    }
}
