xquery version "1.0" encoding "UTF8";

declare namespace request="http://exist-db.org/xquery/request";

let $alben:= doc("/db/alben/alben.xml")
let $name:= request:get-parameter("name","")
let $password:= request:get-parameter("password","")
let $description:= request:get-parameter("description","")
let $nutzer:= request:get-parameter("nutzer","")
let $id:= max($alben//id/text())+1
let $new-album:=
<album>
    <id>{$id}</id>
    <name>{$name}</name>
    <password>{$password}</password>
    <description>{$description}</description>
    <nutzer>{$nutzer}</nutzer>
</album>
return
  if(not($name))
        then(
          <error>
              <message>Es wurde kein Name angegeben!</message>
          </error>
        )
        else(
          if(count($alben//album[name=$name])>0)
          then(
            <error>
                <message>Ein Album mit dem angegebenen Namen exisiert bereits!</message>
            </error>
          )
          else(
            if(not($password))
            then(
              <error>
                  <message>Es wurde kein Passwort angegeben!</message>
              </error>
            )
            else(
              if(not($nutzer))
              then(
                <error>
                    <message>Es wurde kein Nutzer angegeben!</message>
                </error>
              )
              else(
                <result>
                  <id>{$id}</id>
                  {for $item in $alben//alben
                    return
                      (update insert $new-album into $item)}
               </result>
              )
            )
          )
        )