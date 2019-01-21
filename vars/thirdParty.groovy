#!/usr/bin/env groovy

import org.demo.myUtils

def call(body) {
    new myUtils(script:this).helloUppercase()
    return this
}
