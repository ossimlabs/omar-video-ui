properties([
    buildDiscarder( logRotator( numToKeepStr: '5' ) ),
    disableConcurrentBuilds(),
    parameters([
        string( name: 'BUILD_NODE', defaultValue: 'omar-build', description: 'The build node to run on' ),
        booleanParam(name: 'CLEAN_WORKSPACE', defaultValue: true, description: 'Clean the workspace at the end of the run' ),
        string(name: 'DOCKER_REGISTRY_DOWNLOAD_URL', defaultValue: 'nexus-docker-private-group.ossim.io', description: 'Repository of docker images')
    ]),
    pipelineTriggers([
            [ $class: "GitHubPushTrigger" ]
    ]),
    [
        $class: 'GithubProjectProperty',
        displayName: '',
        projectUrlStr: 'https://github.com/ossimlabs/omar-video-ui'
    ]
])
podTemplate(
  containers: [
    containerTemplate(
      name: 'docker',
      image: 'docker:19.03.8',
      ttyEnabled: true,
      command: 'cat',
      privileged: true
    ),
    containerTemplate(
      image: "${DOCKER_REGISTRY_DOWNLOAD_URL}/omar-builder:latest",
      name: 'builder',
      command: 'cat',
      ttyEnabled: true
    )
  ],
  volumes: [
    hostPathVolume(
      hostPath: '/var/run/docker.sock',
      mountPath: '/var/run/docker.sock'
    ),
  ]
)
{
node( POD_LABEL ) {

    stage("Checkout branch $BRANCH_NAME" ) {
        checkout( scm )
    }

    stage( "Load Variables" ) {
        withCredentials([
            string( credentialsId: 'o2-artifact-project', variable: 'o2ArtifactProject' )
        ]) {
            step ([
                $class: "CopyArtifact",
                filter: "common-variables.groovy",
                flatten: true,
                projectName: o2ArtifactProject
            ])
        }

        load "common-variables.groovy"
    }

    stage ( "Assemble" ) {
        container('builder'){
            sh """
                echo "registry = ${NPM_REGISTRY}" >> .npmrc
                cp .npmrc ~/.npmrc # Sometimes the per-project one doesn't get picked up
                export CHROMEDRIVER_SKIP_DOWNLOAD=true
                gradle assembleServerAndCLient -PossimMavenProxy=${MAVEN_DOWNLOAD_URL}
            """
            //archiveArtifacts "apps/*/build/libs/*.jar"
        }
    }

    stage('Docker build') {
        container('docker') {
            withDockerRegistry(credentialsId: 'dockerCredentials', url: "https://${DOCKER_REGISTRY_DOWNLOAD_URL}") {  //TODO
                sh """
                docker build --build-arg BASE_IMAGE=ossim-runtime-alpine-minimal --network=host -t "${DOCKER_REGISTRY_PUBLIC_UPLOAD_URL}"/omar-mensa-app:${BRANCH_NAME} ./docker
                """
            }
        }
    }
    stage('Docker push'){
        container('docker') {
            withDockerRegistry(credentialsId: 'dockerCredentials', url: "https://${DOCKER_REGISTRY_PUBLIC_UPLOAD_URL}") {
            sh """
                docker push "${DOCKER_REGISTRY_PUBLIC_UPLOAD_URL}"/omar-mensa-app:${BRANCH_NAME}
            """
            }
        }
    }
    try {
        stage( "Clean Workspace" ) {
            if ( "${ CLEAN_WORKSPACE }" == "true" ) {
                step([ $class: 'WsCleanup' ])
            }
        }
    } catch (e) {
        echo e.toString()
    }
}
}