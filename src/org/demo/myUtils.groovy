package org.demo

@Grab('commons-lang:commons-lang:2.4')
import org.apache.commons.lang.WordUtils

class myUtils {
    Script script

    def helloUppercase() {
        script.echo("Hello ${WordUtils.capitalize('world')}!")
    }

}
