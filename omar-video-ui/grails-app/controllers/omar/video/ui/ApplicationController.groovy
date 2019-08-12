package omar.video.ui

import grails.core.GrailsApplication
import grails.plugins.*

import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation


@Api(value = "/application",
	 description = "OMAR Video UI REST API"
)
class ApplicationController implements PluginManagerAware {

    GrailsApplication grailsApplication
    GrailsPluginManager pluginManager

    @ApiOperation(
		httpMethod = "GET",
	    produces = 'text/html',
	    value = "Set UI features from the URL."
	)
	@ApiImplicitParams([
		@ApiImplicitParam(
			dataType = 'string',
			defaultValue = '',
			name = 'filter',
			paramType = 'query',
			value = 'Specifies the video search parameters.'
		)
	])
    def index() {
        params.remove( 'action' )
        params.remove( 'controller' )
        params.remove( 'format' )

        def queryString = params.collect { key, value -> "${ key }=${ value }" }.join( "&" )
//        redirect( url: "/?${ queryString }" )
//		def model = restApiService.serviceMethod( params )
		def params = 'things'


		render(model: [ params: params ], view: "/index.gsp")
    }
}
