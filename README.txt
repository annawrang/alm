:::README:::

:::REQUIREMENTS:::
Java SDK version: 1.8
Java version: 8
MySQL version: 5.7.20
    Req.database: InnoDB
Postman version: 6.2.5
IntelliJ version: Ultimate 2018.x.x

:::DATABASE:::
Skript för att generera användare med lösenord och databas för lokal testning.
Projektet har en fil som heter application.properties som ligger under mappen resource. Denna fil är inkluderad i .gitignore.

CREATE USER 'Admin'@'LocalHost' IDENTIFIED BY 'password';
SET PASSWORD FOR 'Admin'@'LocalHost' = 'admin';
GRANT ALL PRIVILEGES ON *.* TO 'Admin'@'LocalHost' WITH GRANT OPTION;
CREATE DATABASE trello;
USE trello;

OBS!
När entiteter tas bort i databasen (via API) så ändras boolean "active" till
false vilket betyder att den kommer hanteras som ett borttaget objekt men
ligger kvar i databasen.

:::TESTING:::
Testerna körs med maven surefire plugin. Lättast är att använda sig av Maven kontrollpanel för att köra alla tester med logging av resultat. Resultat sparas i target/surefire-reports i text document.

Att köra individuella klasstester för sig går också bra!

:::PROJECT STRUCTURE:::
Projektet är uppdelat i tre huvuddelar vilka kommunicerar genom interfaces.

OBS!
Vissa api calls kräver AuthToken - denna är konfigurerad i ApplicationResources.
Default är "steam", nyckeln är “key”

