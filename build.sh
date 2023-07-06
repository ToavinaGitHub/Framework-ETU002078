librairieTomcat="/opt/tomcat/lib/*"

projetTomcat="/opt/tomcat/webapps"

javac -cp "temp" -d temp Framework/src/main/java/etu2078/framework/annotation/Url.java

javac -cp "temp" -d temp Framework/src/main/java/etu2078/framework/Mapping.java

javac -cp "temp" -d temp Framework/src/main/java/etu2078/framework/ModelView.java

javac -cp "temp:$librairieTomcat" -d temp Framework/src/main/java/etu2078/framework/servlet/FrontServlet.java

jar cvf test/src/main/webapp/WEB-INF/lib/framework.jar -C temp .

rm -rf temp

mkdir temp

cp -r test/src/main/webapp/* temp/

framework_jar="temp/WEB-INF/lib/framework.jar"

javac -cp "test/target/classes:$framework_jar" -d temp/WEB-INF/classes test/src/main/java/model/*.java


jar cvf "$projetTomcat"/framework.war -C temp .

rm -rf temp

