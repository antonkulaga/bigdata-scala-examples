name := "bigdata-scala-examples"

version := "0.1"

scalaVersion := "2.11.0"

// For apache libs
resolvers += "apache-repo-releases" at "http://repository.apache.org/content/repositories/releases/"   

// For BigData database
resolvers += "Bigdata releases" at "http://systap.com/maven/releases/"

// For BigData
resolvers += "nxparser-repo" at "http://nxparser.googlecode.com/svn/repository/"

libraryDependencies ++= Seq(
    "com.bigdata" % "bigdata" % "1.3.0",
    "org.scalatest" %% "scalatest" % "2.2.0-M1"
)
 
