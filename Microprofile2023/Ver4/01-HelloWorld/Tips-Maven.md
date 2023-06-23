Windows:
mvn package --% -Dmaven.test.skip=false

POM.xml:

<plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-surefire-plugin</artifactId>
        <version>2.22.0</version>
        <configuration>
          <skipTests>true</skipTests>
        </configuration>
</plugin>


https://mkyong.com/maven/how-to-skip-maven-unit-test/#:~:text=skip%3Dtrue%20to%20skip%20the,of%20the%20cases%20are%20failed.