xquery version "1.0" encoding "UTF8";

declare namespace request="http://exist-db.org/xquery/request";

let $bilder:= doc("/db/bilder/bilder.xml")
let $name:= request:get-parameter("name","")
let $description:= request:get-parameter("description","")
let $isublic:= request:get-parameter("ispublic","true")
let $date:= request:get-parameter("date","")
let $fileposition:= request:get-parameter("fileposition","")
let $longitude:= request:get-parameter("longitude","")
let $latitude:= request:get-parameter("latitude","")
let $altitude:= request:get-parameter("altitude","")
let $direction:= request:get-parameter("direction","")
let $album:= request:get-parameter("album","")
let $id:= max($bilder//id/text())+1
let $new-bild:=
<bild>
    <id>{$id}</id>
    <name>{$name}</name>
    <description>{$description}</description>
    <ispublic>{$ispublic}</ispublic>
    <date>{$date}</date>
    <fileposition>{$fileposition}</fileposition>
    <album>{$album}</album>
    <location>
        <longitude>{$longitude}</longitude>
        <latitude>{$latitude}</latitude>
        <altitude>{$altitude}</altitude>
        <direction>{$direction}</direction>
    </location>
</bild>
return
  if(not($name))
        then(
          <error>
              <message>Es wurde kein Name angegeben!</message>
          </error>
        )
        else(
          if(not($album))
          then(
            <error>
                <message>Es wurde kein Album für das Bild angegeben!</message>
            </error>
          )
          else(
            if(count($bilder//bild[album=$album][name=$name])>0)
            then(
              <error>
                  <message>Ein Bild mit dem angegebenen Namen exisiert bereits in diesem Album!</message>
              </error>
            )
            else(
                <result>
                  <id>{$id}</id>
                  {for $item in $bilder//bilder
                    return
                      (update insert $new-bild into $item)}
               </result>
            )
          )
        )