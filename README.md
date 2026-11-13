Djedid Aouatef


Lors de l'exécution de mes tests:
le test de BookServiceTest  reussit 
mais ,j'ai rencontré une probleme lié à liquibase. Pour résoudre ce problème, je dois vérifier le fichier de configuration XML, corriger les éventuelles erreurs et relancer les tests afin de vérifier que tout fonctionne correctement
probleme:  liquibase.exception.LiquibaseException at SpringLiquibase.java:289
liquibase.exception.ChangeLogParseException at AbstractChangeLogParser.java:25
                            Caused by: liquibase.exception.SetupException at DatabaseChangeLog.java:716
                                Caused by: liquibase.exception.LiquibaseException at DatabaseChangeLog.java:1203
                                    Caused by: liquibase.exception.ChangeLogParseException at XMLChan