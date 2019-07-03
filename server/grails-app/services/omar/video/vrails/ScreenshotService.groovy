package omar.video.vrails
import grails.transaction.Transactional

@Transactional( readOnly = true )

class ScreenshotService {
    def grailsApplication

    def getScreenshot( def params) {
        def localScreenshotDir = grailsApplication.config.screenshot.localScreenshotDir

        File file = File.createTempFile("tmp-omar-vrails-screenshot",".jpg", new File('/tmp'))

        // Expand this out to include name of video
//        params.filePath = "${localScreenshotDir}/${params.timestamp}.jpg"
        params.filePath = file

        def cmdScreenshot = [
            'ffmpeg',
            '-noaccurate_seek',
            '-y',
            '-ss', params.timestamp,
            '-i', params.videoPath,
            '-vframes', '1',
            '-q:v', '2',
            "${file}"
        ]

        def proc = cmdScreenshot.execute()
        def outputStream = new StringBuffer()
        proc.waitForProcessOutput(outputStream, System.err)

//        file.write(proc)
        /*def proc = cmdScreenshot.execute()
        proc.consumeProcessOutput()
        proc.waitFor()*/

        // deletes file when the virtual machine terminate
        // file.deleteOnExit()

        [ params: params ]
    }
}

