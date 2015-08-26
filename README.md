# Gradle SSL Certificate Manager plugin

Its common to use self-signed certificates in a community such as within an organisation. 
The purpose of this plugin is to help pull those certificates, and install them with minimal effort.

## Latest Version

WIP

## Build Status

[![Build Status](https://travis-ci.org/byte-shifter-ltd/gradle-certmanager-plugin.svg)](https://travis-ci.org/byte-shifter-ltd/gradle-certmanager-plugin)

## Usage

To use the plugin's functionality, you will need to add the binary artifact to your build script's classpath and apply the plugin.

### Adding the plugin binary to the build

The plugin JAR needs to be defined in the classpath of your build script. It is directly available on

The following code snippet shows an example on how to retrieve it from Bintray:

```groovy
buildscript {
    repositories {
        jcenter()
    }    
    
    dependencies {        
        classpath 'io.byteshifter:gradle-certmanager-plugin:VERSION'
    }
}
```

### Provided plugins

    apply plugin: 'io.byteshifter.certman'


## Tasks

## Additional resources

[Wikipedia](https://en.wikipedia.org/wiki/HTTP_Secure)
[What is SSL and what are Certificates?](http://www.tldp.org/HOWTO/SSL-Certificates-HOWTO/x64.html)
[How to install ssl certs](http://www.opentox.org/tutorials/q-edit/how-to-install-ssl-certificates)

