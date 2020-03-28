package models

object CodeGen extends App {
  slick.codegen.SourceCodeGenerator.run(
    "slick.jdbc.PostgresProfile", 
    "org.postgresql.Driver",
    "jdbc:postgresql://localhost/tasklist?user=mlewis&password=password",
    "/home/mlewis/PlayVideos/play-videos/server/app/", 
    "models", None, None, true, false
  )
}