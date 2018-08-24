
:::README:::


:::DATABASE:::
skript för att generera användare med lösenord och databas för lokal testning.
SSL, timezone och storage engine (InnoDB) konfigureras i programmet i config filen

CREATE USER 'Admin'@'LocalHost' IDENTIFIED BY 'password';
SET PASSWORD FOR 'Admin'@'LocalHost' = 'admin';
GRANT ALL PRIVILEGES ON *.* TO 'Admin'@'LocalHost' WITH GRANT OPTION;
CREATE DATABASE trello;
USE trello;

Viktigt!
när Objekt tas bort i databasen (via API) så ändras boolean "active" till 
false vilket betyder att den kommer hanteras som ett bortaget objekt men 
ligger kvar i databasen.

:::TESTING:::
Testerna körs med maven surefire pluggin, lättast är att 
använda sig av maven controllpanelen i intellij för att köra alla tester
med logging av resultat. resultat sparas i target/surefire-reports i text document.

Att köra individuella klasstester för sig går självklart bra också!

:::PROJECT STRUCTURE:::
Projektet är uppdelat i tre huvuddelar vilka kommuniserar genom interfaces


Viktigt! 
vissa api calls kräver AuthToken - denna är konfiguerad i ApplicationResources. 
default är "steam"

