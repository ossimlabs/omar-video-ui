package omar.video.vrails
import grails.converters.JSON

class ScreenshotController {

    def screenshotService

    def index() {
        def screenshotDetails = screenshotService.getScreenshot(params)
        println '--------------------> screenshotDetails' + screenshotDetails.file
        println '--------------------> params.filePath' + params.filePath
        render contentType: 'image/jpeg', file: params.filePath
//        render contentType: 'image/jpeg', file: new File(screenshotDetails.file)

    }
//    def takeScreenshot() {
//        def screenshotDetails = screenshotService.getScreenshot(params)
//        render contentType: 'image/jpeg', file: new File(params.filePath)
//    }
}
