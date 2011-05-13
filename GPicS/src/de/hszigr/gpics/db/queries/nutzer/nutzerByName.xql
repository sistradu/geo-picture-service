xquery version "1.0";

declare namespace request="http://exist-db.org/xquery/request";

let $nutzers:= doc("/db/nutzer/nutzers.xml")
let $name:= request:get-parameter("name","")

return
if(not($name))
  then(
    <error>
        <message>Es wurde kein Name angegeben!</message>
    </error>
  )
  else(
    <nutzers>
      {$nutzers//nutzer[name=$name]}
    </nutzers>
  )