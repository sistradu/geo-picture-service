xquery version "1.0";
declare option exist:serialize "method=xml media-type=text/xml omit-xml-declaration=yes indent=yes";

(: Get the content of the editor1 parameter :)
let $editor1 := request:get-parameter("editor1", 0)

(: wrap the content in a div to make sure we have well-formed XML :)
let $wrapped-content := concat('&lt;div&gt;', $editor1, '&lt;/div&gt;')

(: parse the escaped text so that we now have true XML markup :)
let $data-to-save := util:parse($wrapped-content)
return
<results>
  {$data-to-save}
</results>
