# Komponentenverantwortlichkeiten #

Hier die Tabelle mit der Zuordnung der Themen zum jeweiligen Bearbeiter.

| **Thema** | **Bearbeiter** | **Fortschritt in %** | **Testabdeckung** | **Kommentar** |
|:----------|:---------------|:---------------------|:------------------|:--------------|
| Funktionsklassen: CoordinateCalculator + ImageDataExtractor | Stefan | 100% | 100% |  |
| DB-Mock-Klasse | Markus | 100% | 100% |  |
| YAML | Martin | 50% | manuelle Test | Container in einem xhtml-Template aufgebaut ; CSS-Gestaltung fehlt noch |
| Breadcrumb + Menü | Rico | 75% | nicht möglich | Menüeinträge noch hinzufügen, Breadcrumb vllt nicht machbar, muss noch geschaut werden |
| **Views und Controller** |  |  |  |
| Startseite mit Login und Nutzerverwaltung | Stefan | 95% | 75% | Tests für Mailversenden leider nicht möglich, da dort der Facescontext verwendet wird. Dieser ist bei JUnit allerdings "null". Es fehlen außerdem noch, Validatoren und die Tests dazu.|
| Album erstellen/bearbeiten | Stefan | 65% | 0% |  |
| Bild bearbeiten | Martin | 95% | 0% |  |
| Album Übersicht | Martin | 100% | 0% |  |
| Eigene Alben anzeigen | Rico | 0% | 0% | Möglicherweise unnötig, da man es vllt auch bei Album-Übersicht mit reinmachen kann |
| Album betrachten | Rico | 80% | 0% | public und nicht public-Funktion einfügen |
| DB-Anbindung | Markus | 100% | 95% | (100% Testabdeckung sind fast unmöglich...) |

Wer noch etwas ändern möchte, kann das gern hier eintragen. (Bitte vorher absprechen!)

# Details für die Controller #

**Startseite/Login/Nutzerverwaltung:**
  * Scope: Session
  * Attribute:
> > | **Attributname** | **Typ** | **Info** |
|:-----------------|:--------|:---------|
> > | nutzerID | Integer |  |
> > | nutzerName | String |  |
> > | email | String |  |
> > | eingeloggt | boolean |  |
> > | passwort | String | MD5-Hash |
**Album erstellen/bearbeiten:**
  * Scope: Request
  * Attribute:
> > | **Attributname** | **Typ** | **Info** |
> > | albumID | Integer |  |
> > | albumName | String |  |
> > | albumDescription | String | maximal 200 Zeichen o.ä. |
> > | passwort | String | generiert + MD5-Hash |

**Bild bearbeiten:**
  * Scope: Request
  * Attribute:
> > | **Attributname** | **Typ** | **Info** |
|:-----------------|:--------|:---------|
> > | bildID | Integer |  |
> > | bildName | String |  |
> > | beschreibung | String | maximal 200 Zeichen o.ä. |
> > | öffentlich| boolean |  |
> > | timestamp | Date/Calendar | Format: yyyy-MM-dd |
> > | location-file | String | latitude(-decimal)/longitude(-decimal)/altitude/direction |

**Album Übersicht und Suche:**
  * Scope: Request
  * Attribute:
> > | **Attributname** | **Typ** | **Info** |
|:-----------------|:--------|:---------|
> > | sucheNach | String | Was wird gesucht (Benutzer,Name,...) |
> > | suchString | String | Wert nach dem gesucht wird |
> > | alben | List

&lt;Album&gt;

 | Alle bzw. gefundene Alben zum anzeigen |

**Eigene Alben anzeigen:**
  * Scope: Request
  * Attribute:
> > | **Attributname** | **Typ** | **Info** |
|:-----------------|:--------|:---------|
> > | alben | List

&lt;Album&gt;

 | Alle Alben des angemeldeten Nutzers (benötigt Login) |

**Album betrachten:**
  * Scope: Request
  * Attribute:
> > | **Attributname** | **Typ** | **Info** |
|:-----------------|:--------|:---------|
> > | bilder | List

&lt;Bild&gt;

 | Alle Bilder des Albums, benötigt Passwort oder eingeloggter Nutzer = Albumbesitzer zum anzeigen privater Bilder |

# Navigation #
Das folgende Bild zeigt die Navigation durch die Seiten. Die gelb Umrandeten kommen ins Menü und sind damit von überall erreichbar.

Außerdem sind auch die Benennung der Seiten zu erkennen.

![http://geo-picture-service.googlecode.com/files/navigation.png](http://geo-picture-service.googlecode.com/files/navigation.png)