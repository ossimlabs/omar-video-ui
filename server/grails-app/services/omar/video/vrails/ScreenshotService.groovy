package omar.video.vrails
import grails.transaction.Transactional

@Transactional( readOnly = true )

class ScreenshotService {
    def grailsApplication

    def getScreenshot( def params) {
        File file = File.createTempFile("tmp-omar-vrails-screenshot",".jpg", new File('/tmp'))

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

        // deletes file when the virtual machine terminate
        // file.deleteOnExit()

        [ params: params ]
    }
}

