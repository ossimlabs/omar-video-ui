package omar.video.vrails
import grails.converters.JSON

class ScreenshotController {

    def ScreenshotService

    def index() {
        def screenshotDetails = ScreenshotService.getScreenshot(params)
        render contentType: 'image/jpeg', file: new File(params.filePath)
    }
    def takeScreenshot() {
        def screenshotDetails = ScreenshotService.getScreenshot(params)
        render contentType: 'image/jpeg', file: new File(params.filePath)
    }
}
