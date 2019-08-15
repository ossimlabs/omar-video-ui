package omar.video.ui

import grails.transaction.Transactional


@Transactional
class RestApiService {

    def grailsApplication

    def serviceMethod( params, request ) {
        // Get Banner things
        def securityClassification = grailsApplication.config.securityClassification
        def config = grailsApplication.config
        def env = grailsApplication.config.'grails.env'
        def userName, userInfo, defaultName

        if (env == 'development') {
            println 'DEVELOPMENT'
            userName = grailsApplication.config.user.name
        } else {
            // Get PKI things
            println 'PRODUCTION'
            userInfo = grailsApplication.config.userInfo
            def requestHeaderUserName = userInfo.requestHeaderUserName
            defaultName = userInfo.requestHeaderUserNameDefault
            userName = request.getHeader( requestHeaderUserName ) ?: userInfo.requestHeaderUserNameDefault
        }


        def map = [
            securityClassification: securityClassification,
            userInfo: userInfo,
            userName: userName,
            config: config,
            env: env
        ]
            return map
        }
}
