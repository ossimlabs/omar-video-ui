# OMAR VIDEO UI

## Purpose

The OMAR Video UI serves as an interactive user interface where users can view, interact with, and manipulate video data through other O2 web services.

## Installation in Openshift

**Assumption:** The omar-video-ui docker image is pushed into the OpenShift server's internal docker registry and available to the project.

### Environment variables

|Variable|Value|
|------|------|
|SPRING_PROFILES_ACTIVE|Comma separated profile tags (*e.g. production, dev*)|
|SPRING_CLOUD_CONFIG_LABEL|The Git branch from which to pull config files (*e.g. master*)|