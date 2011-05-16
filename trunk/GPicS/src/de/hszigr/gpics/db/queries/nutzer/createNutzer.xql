xquery version "1.0" encoding "UTF8";

declare namespace request="http://exist-db.org/xquery/request";

let $nutzers:= doc("/db/nutzer/nutzers.xml")
let $name:= request:get-parameter("name","")
let $password:= request:get-parameter("password","")
let $email:= request:get-parameter("email","")
let $id:= max($nutzers//id/text())+1
let $new-nutzer:=
<nutzer>
    <id>{$id}</id>
    <name>{$name}</name>
    <password>{$password}</password>
    <email>{$email}</email>
</nutzer>
return
  if(not($name))
        then(
          <error>
              <message>Es wurde kein Name angegeben!</message>
          </error>
        )
        else(
          if(count($nutzers//nutzer[name=$name])>0)
          then(
            <error>
                <message>Ein Nutzer mit dem angegebenen Namen exisiert bereits!</message>
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
              if(not($email))
              then(
                <error>
                    <message>Es wurde keine Email-Adresse angegeben!</message>
                </error>
              )
              else(
                <result>
                  <id>{$id}</id>
                  {for $item in $nutzers//nutzers
                    return
                      (update insert $new-nutzer into $item)}
               </result>
              )
            )
          )
        )