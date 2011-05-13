xquery version "1.0" encoding "UTF8";

declare namespace request="http://exist-db.org/xquery/request";

let $bilder:= doc("/db/bilder/bilder.xml")
let $id:= request:get-parameter("id","")
  return
   if(not($id))
     then (
        <error>
            <message>Es wurde keine ID angegeben!</message>
        </error>
     )
     else (
        (update delete $bilder//bild[id=$id])
     )