Djedid Aouatef


Lors de l'exécution de mes tests:
le test de BookServiceTest  reussit 
mais ,j'ai rencontré une probleme lié à liquibase. Pour résoudre ce problème, je dois vérifier le fichier de configuration XML, corriger les éventuelles erreurs et relancer les tests afin de vérifier que tout fonctionne correctement
probleme:  liquibase.exception.LiquibaseException at SpringLiquibase.java:289
liquibase.exception.ChangeLogParseException at AbstractChangeLogParser.java:25
                            Caused by: liquibase.exception.SetupException at DatabaseChangeLog.java:716
                                Caused by: liquibase.exception.LiquibaseException at DatabaseChangeLog.java:1203
                                    Caused by: liquibase.exception.ChangeLogParseException at XMLChan


                                    
Liquibase plantait parce qu’il manquait constraintName sur les clés étrangères dans les deux fichiers XML. Je l’ai ajouté.

Après ça, il restait quelques petites erreurs qui cassaient les tests :

dans le XML, user est devenu username et uuid est devenu bigint ;
Member : ajout de @Table(name = "members") et de @JsonIgnore sur books ;
Book : SEQUENCE est devenu IDENTITY ;
BookRepository : OR est devenu AND ;
BookService : ajout de @Transactional ;
BookController : l’auteur n’était pas passé à la création, et /borrow appelait returnBook au lieu de borrowBook.                                    