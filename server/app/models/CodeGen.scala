package models

object CodeGen extends App {
  slick.codegen.SourceCodeGenerator.run(
    "slick.jdbc.PostgresProfile", 
    "org.postgresql.Driver",
    "jdbc:postgresql://localhost/cshub?user=eboettch&password=password",
    "/Users/emery/OneDrive/Documents/Play-Videos-2/server/app/", //"/home/mlewis/PlayVideos/play-videos/server/app/", 
    "models", None, None, true, false
  )
}