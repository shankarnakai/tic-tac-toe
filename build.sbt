name := "tictactoe"

version := "0.1"

scalaVersion := "2.13.1"

libraryDependencies ++= Seq(
  "com.beachape" %% "enumeratum"   % "1.5.13",
  "org.tpolecat" %% "atto-core"    % "0.7.0",
  "org.tpolecat" %% "atto-refined" % "0.7.0",
  "dev.zio" %% "zio" % "1.0.0-RC16",
  "dev.zio" %% "zio-macros-core" % "0.5.0",

  "dev.zio" %% "zio-test" % "1.0.0-RC16" % "test",
  "dev.zio" %% "zio-macros-test" % "0.5.0",
  "dev.zio" %% "zio-test-sbt" % "1.0.0-RC16" % "test"
)

testFrameworks := Seq(new TestFramework("zio.test.sbt.ZTestFramework"))

scalacOptions += "-Ymacro-annotations"
