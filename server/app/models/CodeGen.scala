package models

object CodeGen extends App {
                      
  slick.codegen.SourceCodeGenerator.run(
    "slick.jdbc.PostgresProfile",
    "org.postgresql.Driver",
    "jdbc:postgresql://localhost/hxiao?user=hxiao&password=0851643",
    "/users/hxiao/Local/HTML-Documents/WebApps/5-11/server/app",
    "models", None, None, true, false
  )
}
