CREATE (g4a:grade { system : "UK-TRAD", value : "4a" })
CREATE (g4b:grade { system : "UK-TRAD", value : "4b" })
CREATE (g4c:grade { system : "UK-TRAD", value : "4c" })

CREATE (VS:severity { system : "UK-TRAD", key : "VS", description : "Very Severe" })

CREATE (p1:pitch { name : "P1", number : 1 , length : "10m", description : "Climb the wall and thin cracks directly to a tricky exit onto a large belay ledge next to a thorny bush."})
CREATE (p2:pitch { name : "P2", number : 2 , length : "12m", description : "Take a good crack in a corner to a roof at its top and pull around this on the right to another good belay ledge."})
CREATE (p3:pitch { name : "P3", number : 3 , length : "6m", description : "Walk left along a narrow grassy ledge to a belay below a thin black seam, as for Venusburg."})
CREATE (p4:pitch { name : "P4", number : 4 , length : "18m", description : "Move up right to the base of a steep corner and climb this with difficulty to a horizontal break that can be followed rightwards across the wall to a belay on its edge."})
CREATE (p5:pitch { name : "P5", number : 5 , length : "8m", description : "Move up left to a break and climb the thin crack above to finish."})

CREATE (anvilChorus:route { name : "Anvil chorus" })
CREATE (anvilChorus)-[:GRADED]->(VS)

CREATE (p1)-[:ON]->(anvilChorus)
CREATE (p1)-[:GRADED]->(g4b)

CREATE (p2)-[:ON]->(anvilChorus)
CREATE (p2)-[:GRADED]->(g4b)

CREATE (p3)-[:ON]->(anvilChorus)

CREATE (p4)-[:ON]->(anvilChorus)
CREATE (p4)-[:GRADED]->(g4c)

CREATE (p5)-[:ON]->(anvilChorus)
CREATE (p5)-[:GRADED]->(g4b)

