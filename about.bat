javac -g "--module-path C:\\Users\\Korisnik\\Desktop\\javafx-sdk-17.0.1\\lib --add-modules javafx.controls" Pacman.java *.java
jar -cmf about.txt GameProject_Starter.jar Pacman*.class *.png settings.xml
jar -tvf GameProject_Starter.jar
java --module-path C:\\Users\\Korisnik\\Desktop\\javafx-sdk-17.0.1\\lib --add-modules javafx.controls -jar GameProject_Starter.jar