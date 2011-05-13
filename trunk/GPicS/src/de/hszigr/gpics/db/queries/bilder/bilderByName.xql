xquery version "1.0" encoding "UTF8";

declare namespace request="http://exist-db.org/xquery/request";

let $name:= request:get-parameter("name","")
let $collection := collection('/db/bilder')//bild[contains(name, $name)]

return
  <bilder>
  {for $bild in $collection
    return
      $bild
    }
  </bilder>