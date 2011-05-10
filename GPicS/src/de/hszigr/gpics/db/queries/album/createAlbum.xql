xquery version "1.0" encoding "UTF8";

declare namespace request="http://exist-db.org/xquery/request";

let $alben:= doc("/db/alben/alben.xml")
let $name:= request:get-parameter("name",0)
let $password:= request:get-parameter("password",0)
let $description:= request:get-parameter("description",0)
let $id:= max($alben//id/text())+1
let $new-album:=
<album>
    <id>{$id}</id>
    <name>{$name}</name>
    <password>{$password}</password>
    <description>{$description}</description>
</album>
return
  <result>
  <id>{$id}</id>
  {for $item in $alben//alben
  return
   (update insert $new-album into $item)}
  </result>