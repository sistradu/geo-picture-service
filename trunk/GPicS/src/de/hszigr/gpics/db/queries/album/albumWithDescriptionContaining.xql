xquery version "1.0" encoding "UTF8";

declare namespace request="http://exist-db.org/xquery/request";

let $description:= request:get-parameter("description","")
let $collection := collection('/db/alben')//album[contains(description, $description)]

return
  <alben>
  {for $album in $collection
    return
      $album
    }
  </alben>