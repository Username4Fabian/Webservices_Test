
# Java Webservices

Dieser Leitfaden führt Sie durch die Schritte zur Erstellung eines Java-Webservices, der Längen- und Breitengrade von definierten Orten abfragen kann. Das Ziel ist es, einen RESTful-Webservice zu erstellen, der mit einem einfachen HTML-Client arbeitet.

## Schritt 1: Projekt aufsetzen und ein einfaches Location-Webservice implementieren

Erstellen Sie zunächst ein lokales REST-Webservice, wie im Beispiel beschrieben. Anschließend modifizieren Sie es, um Längen- und Breitengrade von definierten Orten abfragen zu können. Dazu benötigen wir ein `Location`-Objekt, das einem Ort auf der Landkarte entspricht. Sie können das beigefügte Java-File verwenden. 

Erzeugen Sie dann einen neuen `LocationController`, der folgende (hardcodierte) Orte kennt:

```java
        knownLocations.add(new Location("Leoben", 47.383333, 15.1));
        knownLocations.add(new Location("Leoben", 47.383333, 15.1));
        knownLocations.add(new Location("Bruck", 47.416667, 15.266667));
        knownLocations.add(new Location("Kapfenberg", 47.433333, 15.316667));
        knownLocations.add(new Location("Mariazell", 47.769722, 15.316667));
        knownLocations.add(new Location("Graz", 47.066667, 15.45));
        knownLocations.add(new Location("Vienna", 48.2082, 16.3738));
        knownLocations.add(new Location("Linz", 48.3064, 14.2858));
        knownLocations.add(new Location("Graz", 47.0707, 15.4395));
        knownLocations.add(new Location("Salzburg", 47.8095, 13.0550));
        knownLocations.add(new Location("Innsbruck", 47.2682, 11.3923));
        knownLocations.add(new Location("Klagenfurt", 46.6249, 14.3050));
        knownLocations.add(new Location("Villach", 46.6111, 13.8558));
        knownLocations.add(new Location("Wels", 48.1575, 14.0289));
        knownLocations.add(new Location("St. Pölten", 48.2047, 15.6256));
        knownLocations.add(new Location("Dornbirn", 47.4125, 9.7417));
        knownLocations.add(new Location("Wiener Neustadt", 47.8151, 16.2465));
        knownLocations.add(new Location("Bregenz", 47.5031, 9.7471));
        knownLocations.add(new Location("Eisenstadt", 47.8450, 16.5336));
        knownLocations.add(new Location("Leonding", 48.2606, 14.2406));
        knownLocations.add(new Location("Traun", 48.2203, 14.2333));
        knownLocations.add(new Location("Amstetten", 48.1219, 14.8747));
        knownLocations.add(new Location("Klosterneuburg", 48.3053, 16.3256));
        knownLocations.add(new Location("Schwechat", 48.1381, 16.4708));
        knownLocations.add(new Location("Ternitz", 47.7275, 16.0361));
        knownLocations.add(new Location("Baden bei Wien", 48.0069, 16.2308));
        knownLocations.add(new Location("Baden bei Wien", 48.0069, 16.2308));
```

Der Aufruf des Webservices sollte dann so möglich sein und das `Location`-Objekt mit dem angegebenen Namen zurückliefern. Wenn eine Location gefragt wird, die nicht existiert, dann darf eine Fehlerseite erscheinen.

## Schritt 2: HTML Client für das Webservice implementieren

Erstellen Sie einen einfachen HTML-Client, der unser Webservice konsumiert. Es sollte ein Eingabefeld geben, in das man den gesuchten Ortsnamen eingibt. Als Resultat sollten dann die Koordinaten auf der Webseite sowie ein Link zu Google Maps mit den Koordinaten erscheinen.

## Schritt 3: Webservice um Finde-nächstgelegenen-Ort erweitern

Erweitern Sie Ihr Webservice um die Funktion "Finde nächstgelegenen Ort". Es soll einen neuen Webservice-Endpoint geben, der zu einem gegebenen Koordinatenpaar herausfindet, was die nächstgelegene Location ist.

Der HTML-Client sollte ebenfalls erweitert werden, um diese neue Funktion zu unterstützen.

## Schritt 4: Webservice um Höheninformation erweitern

Erweitern Sie Ihr serverseitiges Webservice um Höheninformationen, die aus öffentlich verfügbaren SRTM-Daten bezogen werden. Ihre Aufgabe ist es, einen weiteren REST-Endpoint zu bauen, der für einen (in Form von Längen- und Breitengraden) gegebenen Ort die Höhendaten zu dem Ort ermittelt.

## Schritt 5: Höhenprofile anzeigen lassen

Erweitern Sie die `Location`-Klasse um eine Funktion, die Locations zwischen zwei Punkten errechnen kann. Implementieren Sie ein weiteres Webservice, das die Höheninformationen entlang der durch die Koordinaten vorgegebenen Strecke als Array zurückliefert.

## Schritt 6: Performance tuning

Finden Sie das Performance-Bottleneck und lösen Sie es, sodass Sie in (annähernd) konstanter Zeit beliebig viele Punkte ausgeben können. Als Test dazu erweitern Sie den HTML-Client um einen Slider, der festlegt, wie viele Datenpunkte übergeben werden sollen.

