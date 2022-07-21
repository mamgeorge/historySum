# History DB / 历史 [li4shi3]

<img src = "/history.png" alt = "history" title = "history" width = "100">

intent: build a flexible data entry DB

### features
* uses independent MySQL RDBMS 
* handles utf charsets in sql
* uses global world history principles

### todos
* update era boundaries, tags
* error handling for entries, handle duplicates
* update schema to load images
* db file read (apolo, sum, history), db cap for retrieval
* ---------
* scrolling on different options?
* read in csv (alternate entries); parse text files
* terminal show utf8 characters? add graceful close?

### process

* embedded H2 & JPA to start
* embedded syntax is specialized to use multiline
* uses std controller, repository, service,
* uses springBoot eb, thymeleaf, lombok, JUnit5
* thymeleaf includes images, loops

---
# sources
 
* cd c:\workspace\github\spring\history
* \\5Personal\History\0304HistorySum.txt
* see: \5Personal\History\icons
* http://localhost:8080/

# maven commands
* mvn clean install
* mvn compile
* mvn test
* mvn spring-boot:run

* gradle clean build --info -x test // CTRL-ENTER in IJ IDE
* gradle clean test --i | findstr /i INFO:
* gradle bootrun

# git commands

* cd ..
* git branch
* git checkout develop 
* git pull origin develop
  * [ git checkout -b newbranch ]
  * [ changes ]

* git status 
* git add . 
* git commit -m "updated "
* git push origin develop 
  * git checkout master 
  * git pull origin master 
  * git merge develop 
  * git push origin master
* git checkout develop

1964 -2064 | eramain | USA Ohio | Martin George | lived life, married a wife, had a son, blessed a ton, 历史 |
geneology, SS card, BirthCertificate, Credit Card | a2000

Copyright 2022 by Martin Lee George, Columbus Ohio