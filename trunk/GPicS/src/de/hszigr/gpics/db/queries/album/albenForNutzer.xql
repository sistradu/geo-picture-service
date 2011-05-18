xquery version "1.0" encoding "UTF8";

declare namespace request="http://exist-db.org/xquery/request";

let $nutzer:= request:get-parameter("nutzer","")
let $collection := collection('/db/alben')//album[nutzer=$nutzer]

return
  if(not($nutzer))
    then(
      <error>
          <message>Es wurde keine Nutzer ID angegeben.</message>
      </error>
    )
    else(
      <alben>
        {for $album in $collection
          return
            $album
        }
      </alben>
    )