xquery version "1.0";

declare namespace request="http://exist-db.org/xquery/request";

let $alben:= doc("/db/alben/alben.xml")
let $id:= request:get-parameter("id","")

return
if(not($id))
  then(
    <error>
        <message>Es wurde keine ID angegeben!</message>
    </error>
  )
  else(
    <alben>
      {$alben//album[id=$id]}
    </alben>
  )