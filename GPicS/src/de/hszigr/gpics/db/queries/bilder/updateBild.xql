xquery version "1.0" encoding "UTF8";

declare namespace request="http://exist-db.org/xquery/request";

let $bilder:= doc("/db/bilder/bilder.xml")
let $id:= request:get-parameter("id",0)
let $name:= request:get-parameter("name",$bilder//bild[id=$id]//name/text())
let $description:= request:get-parameter("description",$bilder//bild[id=$id]//description/text())
let $isublic:= request:get-parameter("ispublic",$bilder//bild[id=$id]//ispublic/text())
let $date:= request:get-parameter("date",$bilder//bild[id=$id]//date/text())
let $fileposition:= request:get-parameter("fileposition",$bilder//bild[id=$id]//fileposition/text())
let $longitude:= request:get-parameter("longitude",$bilder//bild[id=$id]//longitude/text())
let $latitude:= request:get-parameter("latitude",$bilder//bild[id=$id]//latitude/text())
let $altitude:= request:get-parameter("altitude",$bilder//bild[id=$id]//altitude/text())
let $direction:= request:get-parameter("direction",$bilder//bild[id=$id]//direction/text())
let $new-bild:=
<bild>
    <id>{$id}</id>
    <name>{$name}</name>
    <description>{$description}</description>
    <ispublic>{$ispublic}</ispublic>
    <date>{$date}</date>
    <fileposition>{$fileposition}</fileposition>
    {$bilder//bild[id=$id]//album}
    <location>
        <longitude>{$longitude}</longitude>
        <latitude>{$latitude}</latitude>
        <altitude>{$altitude}</altitude>
        <direction>{$direction}</direction>
    </location>
</bild>
return
if (not($id))
   then (
      <error>
        <message>Es wurde keine ID angegeben.</message>
      </error>)
    else (
        if(count($bilder//bild[id!=$id][album=$album][name=$name])>0)
        then(
          <error>
            <message>Ein Bild mit dem angegebenen Namen existiert bereits in diesem Album!</message>
          </error>
        )
        else(
          (update replace $bilder//bild[id=$id] with $new-bild)
        )
)