
java -cp "target/lib/*;target/classes" -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=4005  MasterMemberDistributed 
