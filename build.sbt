name := "bigdata-scala-examples"

version := "0.2"


// For apache libs
resolvers += "apache-repo-releases" at "http://repository.apache.org/content/repositories/releases/"   

// For BigData database
resolvers += "Bigdata releases" at "http://systap.com/maven/releases/"

// For BigData
resolvers += "nxparser-repo" at "http://nxparser.googlecode.com/svn/repository/"

libraryDependencies ++= Seq(
    "com.bigdata" % "bigdata" % "1.4.0",
    "org.scalatest" %% "scalatest" % "2.2.1"
)
 
