name := "kuronometer-server"

libraryDependencies ++= Seq(
  "com.twitter"       %% "finatra-http"        % Dependencies.FinatraVersion,
  "com.twitter"       %% "finatra-jackson"     % Dependencies.FinatraVersion,
  "ch.qos.logback"    % "logback-classic"      % Dependencies.LogbackVersion,
  "com.jakehschwartz" % "finatra-swagger_2.12" % "2.10.0",
  "com.twitter" %% "bijection-util" % "0.8.1"
)

mainClass in assembly := Some("com.emaginalabs.kuronometer.server.Application")

artifact in (Compile, assembly) := {
  val art = (artifact in (Compile, assembly)).value
  art.copy(`classifier` = Some("assembly"))
}

addArtifact(artifact in (Compile, assembly), assembly)

assemblyMergeStrategy in assembly := {
  case "BUILD"                                              => MergeStrategy.discard
  case PathList("org", "apache", "commons", "logging", _ *) => MergeStrategy.first
  case PathList("META-INF", "io.netty.versions.properties") => MergeStrategy.first
  case PathList("META-INF", "MANIFEST.MF")                  => MergeStrategy.discard
  case PathList("META-INF", "native", "libnetty-transport-native-epoll.so") => MergeStrategy.first
  case other                                                => (assemblyMergeStrategy in assembly).value(other)
}
