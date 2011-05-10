xquery version "1.0" encoding "UTF8";

declare namespace request="http://exist-db.org/xquery/request";

let $alben:= doc("/db/alben/alben.xml")
let $id:= request:get-parameter("id",0)
let $name:= request:get-parameter("name",0)
let $password:= request:get-parameter("password",0)
let $description:= request:get-parameter("description",0)
let $new-album:=
<album>
    <id>{$id}</id>
    <name>{$name}</name>
    <password>{$password}</password>
    <description>{$description}</description>
    {for $bild in $alben//album[id=$id]//bild
      return
        $bild
    }
</album>
  return
   (update replace $alben//album[id=$id] with $new-album)