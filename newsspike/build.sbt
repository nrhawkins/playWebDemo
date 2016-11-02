name := """newsspike"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.6"

maintainer := "nhawkins"

dockerExposedPorts in Docker := Seq(9000)


val stanfordNlp = "edu.stanford.nlp" % "stanford-corenlp" % "3.5.2" artifacts (Artifact("stanford-corenlp", "models"), Artifact("stanford-corenlp"))

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  specs2 % Test,
  "com.google.code.gson" % "gson" % "2.3.1",
  "com.google.protobuf" % "protobuf-java" % "2.6.1",
  stanfordNlp,
  "net.sf.trove4j" % "trove4j" % "3.0.1",
  "commons-lang" % "commons-lang" % "2.4",
  // common-java contains Range class
  "edu.washington.cs.knowitall" % "common-java" % "2.0.2",
//  "net.sf.jwordnet" % "jwnl" % "1.3.3",
  "edu.washington.cs.knowitall" % "reverb-core" % "1.4.1",
  // reverb-core contains ChunkedBinaryExtraction
//  "edu.washington.cs.knowitall" % "reverb-core" % "1.4.3"
  "edu.washington.cs.knowitall" % "reverb-models" % "1.4.0" 
)

//resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

resolvers ++=Seq( 
"scalaz-bintray" at "http://dl.bintray.com/scalaz/releases",
"opennlp sourceforge repo" at "http://opennlp.sourceforge.net/maven2",
"Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/releases/"
)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
