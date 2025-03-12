#!/bin/bash

#####################################
##
##     Génère un docker-compose 
##
#####################################

########## Variables ############

DIR="${HOME}/generator"
USER_SCRIPT=${USER}

########## Fonctions ############

help() {
    echo "Usage : 
    ${0##*/} [-h|--help] [-p|--postgres] [-i|--ip]"
    
    echo "Options:
    -h, --help : aide
    -p, --postgres : lance une instance PostgreSQL
    -i, --ip : affichage des IP"
}

ip() {
    for i in $(docker ps -q); do
        docker inspect -f "{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}} - {{.Name}} $i"
    done
}

postgres() {
    echo ""
    echo "Installation d'une instance PostgreSQL..."
    echo ""

    # Création du répertoire de données
    mkdir -p "$DIR"

    # Génération du fichier docker-compose
    cat > "$DIR/docker-compose-postgres.yml" <<EOF
version: '3.9'
services:
    postgres:
        image: postgres:latest
        container_name: postgres
        environment:
            - POSTGRES_USER=myuser
            - POSTGRES_PASSWORD=passwd
            - POSTGRES_DB=mydb
        ports:
            - "5332:5432"
        volumes:
            - postgres_data:/var/lib/postgresql/data
        networks:
            - generator

volumes:
    postgres_data:
        driver: local
        driver_opts:
            type: none
            device: ${DIR}/postgres
            o: bind

networks:
    generator:
        driver: bridge
        ipam:
            config:
                - subnet: 192.168.0.0/24
EOF

    # Lancement de l'instance PostgreSQL
    echo "2- Run de l'instance PostgreSQL"
    docker-compose -f "$DIR/docker-compose-postgres.yml" up -d

    echo ""
    echo "Crédentials :
    - Port : 5332
    - POSTGRES_USER : myuser
    - POSTGRES_PASSWORD : passwd 
    - POSTGRES_DB : mydb

    Commande : psql -h localhost -p 5332 -U myuser -d mydb"
}

parser_option() {
    while  [[ $# -gt 0 ]]; do
        case $1 in
            -h | --help )
                help
                exit 0
                ;;
            -p | --postgres ) 
                postgres
                exit 10 # Sortie avec code 10 pour le lancement de l'instance PostgreSQL
                ;;
            -i | --ip )
                ip
                exit 20 # Sortie avec code 20 pour l'affichage des IP
                ;;
            * )
                echo "Option invalide, lancer -h ou --help"
                exit 1 # Sortie avec code 1 pour une option invalide
                ;;
        esac
        shift
    done
}

########## Exécution #############

parser_option "$@"

