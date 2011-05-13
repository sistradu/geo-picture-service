xquery version "1.0";

let $collection := collection('/db/nutzer')//nutzer

return
  <nutzers>
  {for $nutzer in $collection
    return
      $nutzer
    }
  </nutzers>
