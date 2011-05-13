xquery version "1.0" encoding "UTF8";

declare namespace request="http://exist-db.org/xquery/request";

let $bilder:= doc("/db/bilder/bilder.xml")
let $album:= request:get-parameter("album","")
  return
   if(not($album))
     then (
        <error>
            <message>Es wurde kein Album angegeben!</message>
        </error>
     )
     else (
        (update delete $bilder//bilder[album=$album])
     )