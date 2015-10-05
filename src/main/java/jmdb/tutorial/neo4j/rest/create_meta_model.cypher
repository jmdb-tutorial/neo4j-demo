CREATE
    (pitch:META:Nodes {type: "PITCH", name: "PITCH"}),
    (route:META:Nodes {type: "ROUTE", name: "ROUTE"}),
    (climb:META:Nodes {type: "CLIMB", name: "CLIMB"}),
    (climber:META:Nodes {type: "CLIMBER", name: "CLIMBER"}),
    (grade:META:Nodes {type: "GRADE", name: "GRADE"}),
    routes = (pitch - [:FROM_NODE] -> (:META:Relationships {type: "ON", name: "ON"})
                    - [:TO_NODE] -> route),
    climbs = (climb - [:FROM_NODE] -> (:META:Relationships {type: "COMPLETED", name: "COMPLETED"})
                    - [:TO_NODE] -> pitch),
    participation = (climber - [:FROM_NODE] -> (:META:Relationships {type: "PARTICIPATED_IN", name: "PARTICIPATED_IN"})
                             - [:TO_NODE] -> climb),
    pitchGrade = (pitch - [:FROM_NODE] -> (:META:Relationships {type: "GRADED", name: "GRADED"})
                        - [:TO_NODE] -> grade),
    routeGrade = (route - [:FROM_NODE] -> (:META:Relationships {type: "GRADED", name: "GRADED"})
                        - [:TO_NODE] -> grade)
