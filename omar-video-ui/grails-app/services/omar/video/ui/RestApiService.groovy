package omar.video.ui

import grails.transaction.Transactional


@Transactional
class RestApiService {

    def grailsApplication

    def serviceMethod() {
	def config = grailsApplication.config
    def userInfo = grailsApplication.config.userInfo
    def requestHeaderUserName = userInfo.requestHeaderUserName
//    def userName = request.getHeader( requestHeaderUserName ) ?: userInfo.requestHeaderUserNameDefault
//    def testHeader = request.getHeader("User-Agent")

        def map = [
		securityClassification: config.securityClassification,
        userInfo: userInfo,
        userName: requestHeaderUserName,
        user: 'Your username is very long'
	]
        return map
    }
}
