xquery version "1.0";

declare namespace request="http://exist-db.org/xquery/request";

let $id:= request:get-parameter("id",0)
let $collection := collection('/db/alben')//album[id=$id]

return
  <alben>
    {for $album in $collection
    return
      $album
    }
  </alben>