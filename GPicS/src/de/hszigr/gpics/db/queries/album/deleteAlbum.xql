xquery version "1.0" encoding "UTF8";

declare namespace request="http://exist-db.org/xquery/request";

let $alben:= doc("/db/alben/alben.xml")
let $id:= request:get-parameter("id",0)
  return
   (update delete $alben//album[id=$id])