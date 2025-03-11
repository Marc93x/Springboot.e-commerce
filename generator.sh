#!/bin/bash 

#####################################
##
##     Genere un docker-compose 
##
#####################################

########## Variable ############

DIR="${HOME}/generator"
USER_Script=${USER} 

########## Fonction ############

help() {

echo "Usage : 

    ${0##*/} [-h ] [--help]

Options:
-h , --help : aide
-p , --postgres : lance une instance une postgres
-i , --ip : affichage des ip
 "    
}

ip() {

 for i in $(docker ps -q);do docker inspect -f "{{range.NetworkSettings.Network}} {{.IPAdress}}{{end}} -  {{.Name}} $i "  ;done 

    }

parser_option() {

case $@ in 
        
        -h | --help )
        help
        ;;
        -p | --postgres ) 
        postgres
        ;;
        *) 
       echo "option invalide, lancer -help ou -h"      
esac  
}

postgres() {

echo""
echo "Installation d une instance..."
echo""
echo "1- Creation du repertoir de data..."

mkdir -p $DIR 
echo""
echo"
 version: 27.5.1
 service: 
    postgress:
        image: postgress: latest
        container_name: postgress
        environment: 
        -POSTGRES_USER : myuser
        -POSTGRES_PASSWRD : passwd 
        -POSTGRES_DATABASE : mydb 
        -ports:
        -5332:5332
        volumes: 
        - postgres_data:var/lib/postgres
        networks:
        -generator
    Volumes:
        postgres_data:
        driver: local
        driver_opts:
        o: bind
        type : none 
        device : ${DIR}/postgres
    newtorks:
        generator:
            driver:bridge
            ipam:
                config: 
                    subnet : 192.169.0.0/24
 
 "$DIR>docker-compose-postgres.yml
 
 echo "2- Run de l instance postgres"
     docker-compose -f  $DIR>docker-compose-postgres.yml up -d
echo""
echo "
Crendantials :
-Port :5332:5332
-POSTGRES_USER : myuser
-POSTGRES_PASSWRD : passwd 
-POSTGRE_DATA : mydb

Command : psql -h  <ip> -u myuser -d mydb
" 
 }  


########## Execute #############

parser_option $@
ip
