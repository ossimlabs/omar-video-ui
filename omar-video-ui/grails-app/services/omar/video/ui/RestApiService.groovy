package omar.video.ui

import grails.transaction.Transactional


@Transactional
class RestApiService {

    def grailsApplication

    def serviceMethod( params, request ) {
        def userName, userInfo

        // Get Banner things
        def securityClassification = grailsApplication.config.securityClassification
        def config = grailsApplication.config

        // grails.env is in dotnotation on the object
        def env = grailsApplication.config.'grails.env'

        if (env == 'development') {
            userName = grailsApplication.config.user.name
        } else {
            // Get PKI things
            userInfo = grailsApplication.config.userInfo
            def requestHeaderUserName = userInfo.requestHeaderUserName
            userName = request.getHeader( requestHeaderUserName ) ?: userInfo.requestHeaderUserNameDefault
        }


        def map = [
            securityClassification: securityClassification,
            userInfo: userInfo,
            userName: userName,
            config: config,
        ]
            return map
        }
}
