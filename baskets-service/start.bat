echo "Deploying Application....."


set JAVA_OPTS=-Xms512m -Xmx1024m

java %JAVA_OPTS%  -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -jar ./build/libs/baskets-service-1.0.0-SNAPSHOT.jar &

echo "Deployment process complete...."