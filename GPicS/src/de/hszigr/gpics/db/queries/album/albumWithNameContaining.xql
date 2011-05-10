xquery version "1.0" encoding "UTF8";

declare namespace request="http://exist-db.org/xquery/request";

let $name:= request:get-parameter("name",0)
let $collection := collection('/db/alben')//album[contains(name, $name)]

return
  <alben>
  {for $album in $collection
    return
      $album
    }
  </alben>