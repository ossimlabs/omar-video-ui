package omar.video.ui
import grails.converters.JSON
import grails.transaction.Transactional

@Transactional
class RestApiController {

    def RestApiService

    def index() {
        def model = restApiService.serviceMethod( params )
        render(model: [ tlvParams: model ], view: "/index.gsp")

    }
}
