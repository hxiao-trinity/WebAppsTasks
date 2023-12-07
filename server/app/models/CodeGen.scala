package models

object CodeGen extends App {
  slick.codegen.SourceCodeGenerator.run(
    "slick.jdbc.PostgresProfile",
    "org.postgresql.Driver",
    "jdbc:postgresql://pandora05.cs.trinity.edu/hxiao?user=hxiao&password=0851643",
    "users/hxiao/Local/HTML-Documents/WebApps/server/app",
    "models", None, None, true, false
  )
}