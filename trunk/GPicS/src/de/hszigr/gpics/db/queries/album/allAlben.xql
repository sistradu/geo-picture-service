xquery version "1.0";

let $collection := collection('/db/alben')//album

return
  <alben>
  {for $album in $collection
    return
      $album
    }
  </alben>

