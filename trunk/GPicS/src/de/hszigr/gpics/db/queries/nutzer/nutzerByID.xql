xquery version "1.0";

declare namespace request="http://exist-db.org/xquery/request";

let $nutzers:= doc("/db/nutzer/nutzers.xml")
let $id:= request:get-parameter("id","")

return
if(not($id))
  then(
    <error>
        <message>Es wurde keine ID angegeben!</message>
    </error>
  )
  else(
    <nutzers>
      {$nutzers//nutzer[id=$id]}
    </nutzers>
  )