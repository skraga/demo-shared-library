#!/usr/bin/env groovy

def setBuildDescription(String description){
    currentBuild.description = description
}

@NonCPS
def setJobDescription(String description){
    def jobNameTable = env.JOB_NAME.split('/')
    def it=Jenkins.instance
    for(name in jobNameTable){
       it=it.getItem(name)
    }
    it.description = description
}
