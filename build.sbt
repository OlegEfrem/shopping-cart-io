ThisBuild / organization := "com.example"
ThisBuild / scalaVersion := "2.13.5"

lazy val root = (project in file("."))
  .configs(IntegrationTest)
  .settings(
    Defaults.itSettings,
    name := "shopping-cart",
    libraryDependencies ++= Seq(
      // "core" module - IO, IOApp, schedulers
      // This pulls in the kernel and std modules automatically.
      "org.typelevel" %% "cats-effect" % "3.3.12",
      // concurrency abstractions and primitives (Concurrent, Sync, Async etc.)
      "org.typelevel" %% "cats-effect-kernel" % "3.3.12",
      // standard "effect" library (Queues, Console, Random etc.)
      "org.typelevel" %% "cats-effect-std" % "3.3.12",
      // better monadic for compiler plugin as suggested by documentation
      compilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1"),
      "com.beachape" %% "enumeratum" % "1.7.0",
      "com.squareup.okhttp3" % "okhttp" % "4.10.0",
      "io.circe" %% "circe-core" % "0.14.2",
      "io.circe" %% "circe-generic" % "0.14.2",
      "io.circe" %% "circe-parser" % "0.14.2",
      "com.beachape" %% "enumeratum-circe" % "1.7.0",
      "org.typelevel" %% "munit-cats-effect-3" % "1.0.7" % "it,test",
      "org.scalatest" %% "scalatest" % "3.2.13" % "it,test",
      "com.squareup.okhttp3" % "mockwebserver" % "4.10.0" % "it,test"
    )
  )

ThisBuild / coverageEnabled := true
ThisBuild / coverageFailOnMinimum := true
ThisBuild / coverageMinimumStmtTotal := 100
ThisBuild / coverageExcludedPackages := "com.shopping.cart.Main"

IntegrationTest / parallelExecution := false
