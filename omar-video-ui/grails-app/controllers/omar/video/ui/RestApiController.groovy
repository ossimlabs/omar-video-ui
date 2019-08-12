package omar.video.ui


import grails.converters.JSON


class RestApiController {

    def restApiService


    def index() {	
	render restApiService.serviceMethod() as JSON
    }
}
