xquery version "1.0" encoding "UTF8";

declare namespace request="http://exist-db.org/xquery/request";

let $nutzers:= doc("/db/nutzer/nutzers.xml")
let $id:= request:get-parameter("id",0)
let $name:= request:get-parameter("name",$nutzers//nutzer[id=$id]//name/text())
let $password:= request:get-parameter("password",$nutzers//nutzer[id=$id]//password/text())
let $email:= request:get-parameter("email",$nutzers//nutzer[id=$id]//email/text())
let $new-nutzer:=
<nutzer>
    <id>{$id}</id>
    <name>{$name}</name>
    <password>{$password}</password>
    <email>{$email}</email>
</nutzer>
return
if (not($id))
   then (
      <error>
        <message>Es wurde keine ID angegeben.</message>
      </error>)
    else (
        if(count($nutzers//nutzer[id!=$id][name=$name])>0)
        then(
          <error>
            <message>Ein Nutzer mit dem angegebenen Namen existiert bereits!</message>
          </error>
        )
        else(
          (update replace $nutzers//nutzer[id=$id] with $new-nutzer)
        )
)