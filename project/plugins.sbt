// Comment to get more information during initialization
logLevel := Level.Warn

// The Typesafe repository 
resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

// Sonatype snapshots
resolvers += Resolver.sonatypeRepo("snapshots")

//generate Eclipse project files
addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "2.4.0")

//generate Intellij idea project files
addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.6.0")

//visualize dependency graph
addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.7.4")
