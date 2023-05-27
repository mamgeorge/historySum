# History DB / 历史 [li4shi3]

<img src = "src/main/resources/static/images/history.png" alt = "history" title = "history" style = "width: 400px; height: 250px;" />

intent: build a flexible data entry DB
test in http://localhost:8080

### features
* easy, global, comprehensive, reference, curated, organized
* uses independent MySQL RDBMS
* handles utf charsets in sql
* uses global world history principles

### todos
* Validations: entry screening 
* ListingController: error handling, duplicates handling
* Security: inputs security layer for addition
* listing selections of time, components range
* db cap for retrieval
* #######################
* csv read/export (alternate entries); parse text files

### process
* embedded H2 & JPA to start
* embedded syntax is specialized to use multiline
* uses std controller, repository, service,
* uses springBoot eb, MySQL, thymeleaf, lombok, JUnit5
* thymeleaf includes images, loops

---
# sources
* cd c:\workspace\github\spring\history
* \\5Personal\History\0304HistorySum.txt
* see: \5Personal\History\icons
* http://localhost:8080

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

---
1964 -2064 | eramain | USA Ohio | Martin George | lived life, married a wife, had a son, blessed a ton, 历史 |
geneology, SS card, BirthCertificate, Credit Card | a2000

Copyright 2022 by Martin Lee George, Columbus Ohio
