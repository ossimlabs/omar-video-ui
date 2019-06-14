properties([
    buildDiscarder( logRotator( numToKeepStr: '5' ) ),
    disableConcurrentBuilds(),
    parameters([
        string( name: 'BUILD_NODE', defaultValue: 'omar-build', description: 'The build node to run on' ),
        booleanParam(name: 'CLEAN_WORKSPACE', defaultValue: true, description: 'Clean the workspace at the end of the run' )
    ]),
    pipelineTriggers([
            [ $class: "GitHubPushTrigger" ]
    ]),
    [
        $class: 'GithubProjectProperty',
        displayName: '',
        projectUrlStr: 'https://github.com/ossimlabs/omar-video-vrails'
    ]
])

node( "${ BUILD_NODE }" ) {

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
        //sh "gradle assembleServerAndCLient -PossimMavenProxy=${ OSSIM_MAVEN_PROXY }"
	sh "gradle assembleServerAndCLient -PossimMavenProxy=https://nexus-old.ossim.io/repository/all-repos"
        //archiveArtifacts "apps/*/build/libs/*.jar"
    }

    stage ( "Publish Docker App" ) {
        withCredentials([[
            $class: 'UsernamePasswordMultiBinding',
            credentialsId: 'dockerCredentials',
            passwordVariable: 'DOCKER_REGISTRY_PASSWORD',
            usernameVariable: 'DOCKER_REGISTRY_USERNAME'
        ]]) {
            // Run all tasks on the app. This includes pushing to OpenShift and S3.
            //sh "gradle pushDockerImage -PossimMavenProxy=${ OSSIM_MAVEN_PROXY }"
            sh "gradle pushDockerImage -PossimMavenProxy=https://nexus-old.ossim.io/repository/all-repos"
        }
    }
    try {
        stage ( "OpenShift Tag Image" ) {
            withCredentials([[
                $class: 'UsernamePasswordMultiBinding',
                credentialsId: 'openshiftCredentials',
                usernameVariable: 'OPENSHIFT_USERNAME',
                passwordVariable: 'OPENSHIFT_PASSWORD'
            ]]) {
                // Run all tasks on the app. This includes pushing to OpenShift and S3.
                //sh "gradle openshiftTagImage -PossimMavenProxy=${ OSSIM_MAVEN_PROXY }"
                  sh "gradle openshiftTagImage -PossimMavenProxy=https://nexus-old.ossim.io/repository/all-repos"
            }
        }

        stage( "Clean Workspace" ) {
            if ( "${ CLEAN_WORKSPACE }" == "true" ) {
                step([ $class: 'WsCleanup' ])
            }
        }
    } catch (e) {
        echo e.toString()
    }
}
