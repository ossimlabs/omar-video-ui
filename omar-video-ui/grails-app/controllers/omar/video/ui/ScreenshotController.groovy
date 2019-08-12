package omar.video.ui
import grails.converters.JSON
import grails.transaction.Transactional

@Transactional
class ScreenshotController {

    def screenshotService

    def index() {
        def screenshotDetails = screenshotService.getScreenshot(params)
        render contentType: 'image/jpeg', file: params.filePath

    }
}
