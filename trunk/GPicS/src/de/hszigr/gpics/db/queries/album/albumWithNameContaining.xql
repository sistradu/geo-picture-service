xquery version "1.0" encoding "UTF8";

declare namespace request="http://exist-db.org/xquery/request";

let $name:= request:get-parameter("name","")
let $collection := collection('/db/alben')//album[contains(upper-case(name), upper-case($name))]

return
  <alben>
  {for $album in $collection
    return
      $album
    }
  </alben>