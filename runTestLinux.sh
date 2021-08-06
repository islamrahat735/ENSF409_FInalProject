javac -cp .:lib/mysql-connector-java-8.0.23.jar:. edu/ucalgary/ensf409/Transaction.java
javac -cp .:lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar:lib/mysql-connector-java-8.0.23.jar:. edu.ucalgary.ensf409.TransactionTest.java
java -cp .:lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar:lib/mysql-connector-java-8.0.23.jar:. org.junit.runner.JUnitCore edu.ucalgary.ensf409.TransactionTest