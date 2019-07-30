package omar.video.ui
import grails.converters.JSON

class ScreenshotController {

    def screenshotService

    def index() {
        def screenshotDetails = screenshotService.getScreenshot(params)
        render contentType: 'image/jpeg', file: params.filePath

    }
}
