xquery version "1.0" encoding "UTF8";

declare namespace request="http://exist-db.org/xquery/request";

let $name:= request:get-parameter("name","")
let $collection := collection('/db/bilder')//bild[contains(upper-case(name), upper-case($name))]

return
  <bilder>
  {for $bild in $collection
    return
      $bild
    }
  </bilder>