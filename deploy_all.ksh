#!/bin/bash 





mvn clean install

echo "[STOP] services"
docker service rm $(docker service ls | sed 's/  */ /g' | egrep -v '^ID' | cut -d ' ' -f1 | tr '\n' ' ' )


echo "[START services]"
docker stack deploy --compose-file docker-compose.yml dauphine_cluster



echo "[DONE]"
