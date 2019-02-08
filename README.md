this project allow user to store user informations in our secure website

In order to see the detailled API REST, pleace check the swagger ui 2 using <b>http://localhost:9500|9002/swagger-ui.html</b>

to lunch the server, please 

    1/- cd ${project}
    2/- mvn clean install
    3/- docker service rm $(docker service ls | sed 's/  */ /g' | egrep -v '^ID' | cut -d ' ' -f1 | tr '\n' ' ' )
    4/- docker stack deploy --compose-file docker-compose.yml dauphine_cluster


make sure that the web servers are running by rendring the web page http://localhost:8761/




to teste the project, please access to the web pages  :

    - account micro service : [http://localhost:9500/swagger-ui.html]
    - transaction micro service : [http://localhost:9002/swagger-ui.html]





An other manner to check whether the servers are running by running the command line  : 

	-	docker service ls 
	- 	docker container ls 
