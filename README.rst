BigData Scala Examples
======================
Just a repo with BigData test-examples.


Getting started
---------------
0).Install git (if not installed) and clone the repository:
1). Install sbt from http://www.scala-sbt.org/
2). Generate Project Files for IDEA for your choice:
sbt eclipse //for Eclipse
sbt gen-idea //for IntellijIdea
2) From the console type:
sbt test
3) Look at bigdata test results, they all should pass
4) Nothing more here yet

Notes
-----

Some implicit conversions are used for query results, consider looking into simple package object
If you are unfamiliar with ScalaTest that is used in the examples, look at http://www.scalatest.org/
If you are not good at scala, look at http://twitter.github.io/scala_school/