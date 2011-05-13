xquery version "1.0" encoding "UTF8";

declare namespace request="http://exist-db.org/xquery/request";

let $album:= request:get-parameter("album","")
let $collection := collection('/db/bilder')//bild[album=$album]

return
if(not($album))
  then(
    <error>
        <message>Es wurde kein Album angegeben!</message>
    </error>
  )
  else(
    <bilder>
      {for $bild in $collection
        return
          $bild
      }
  </bilder>
  )