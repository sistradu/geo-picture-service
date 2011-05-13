xquery version "1.0" encoding "UTF8";

declare namespace request="http://exist-db.org/xquery/request";

let $alben:= doc("/db/alben/alben.xml")
let $id:= request:get-parameter("id",0)
let $name:= request:get-parameter("name",$alben//album[id=$id]//name/text())
let $password:= request:get-parameter("password",$alben//album[id=$id]//password/text())
let $description:= request:get-parameter("description",$alben//album[id=$id]//description/text())
let $new-album:=
<album>
    <id>{$id}</id>
    <name>{$name}</name>
    <password>{$password}</password>
    <description>{$description}</description>
    {$alben//album[id=$id]//nutzer}
</album>
return
if (not($id))
   then (
      <error>
        <message>Es wurde keine ID angegeben.</message>
      </error>)
    else (
        if(count($alben//album[id!=$id][name=$name])>0)
        then(
          <error>
            <message>Ein Album mit dem angegebenen Namen existiert bereits!</message>
          </error>
        )
        else(
          (update replace $alben//album[id=$id] with $new-album)
        )
)
