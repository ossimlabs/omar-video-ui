package omar.video.vrails
import grails.transaction.Transactional

@Transactional( readOnly = true )

class ScreenshotService {
    def grailsApplication

    def getScreenshot( def params) {
        def localScreenshotDir = grailsApplication.config.screenshot.localScreenshotDir


        // Expand this out to include name of video
        params.filePath = "${localScreenshotDir}/${params.timestamp}.jpg"

        def cmdScreenshot = [
            'ffmpeg',
            '-noaccurate_seek',
            '-y',
            '-ss', params.timestamp,
            '-i', params.videoPath,
            '-vframes', '1',
            '-q:v', '2',
            "${localScreenshotDir}/${params.timestamp}.jpg"
        ]
        def proc = cmdScreenshot.execute()
        proc.consumeProcessOutput()
        proc.waitFor()

        [ params: params ]
    }
}

