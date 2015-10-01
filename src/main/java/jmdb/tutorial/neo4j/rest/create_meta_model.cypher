CREATE
    (pitch:META:Nodes {type: "PITCH", name: "PITCH"}),
    (route:META:Nodes {type: "ROUTE", name: "ROUTE"}),
    (climb:META:Nodes {type: "CLIMB", name: "CLIMB"}),
    (climber:META:Nodes {type: "CLIMBER", name: "CLIMBER"}),
    routes = (pitch - [:FROM_NODE] -> (:META:Relationships {type: "ON", name: "ON"})
                      - [:TO_NODE] -> route),
    climbs = (climb - [:FROM_NODE] -> (:META:Relationships {type: "COMPLETED", name: "COMPLETED"})
                      - [:TO_NODE] -> pitch),
    participation = (climber - [:FROM_NODE] -> (:META:Relationships {type: "PARTICIPATED_IN", name: "PARTICIPATED_IN"})
                               - [:TO_NODE] -> climb)