package omar.video.ui


import grails.transaction.Transactional


@Transactional
class RestApiService {

    def grailsApplication

    def serviceMethod() {
	def config = grailsApplication.config
	def map = [
		securityClassification: config.securityClassification
	]
        return map
    }
}
