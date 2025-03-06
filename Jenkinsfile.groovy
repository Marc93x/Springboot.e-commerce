node {
    cleanWs()  // Nettoie le workspace avant de commencer

    try {
        stage('PremiereEtape') {
            sh 'echo "Hello World"'
        stage('DeuxiemeEtape')
            sh 'echo "bonjour a toi"'// Exécute la commande shell
        }
    } finally {
        cleanWs()  // Nettoie le workspace après l'exécution
    }
}
