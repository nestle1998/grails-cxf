package org.grails.cxf

import spock.lang.Specification
import spock.lang.Shared
import org.grails.cxf.artefact.EndpointArtefactHandler
import org.grails.cxf.artefact.DefaultGrailsEndpointClass
import org.codehaus.groovy.grails.commons.GrailsClass
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.grails.cxf.artefact.EndpointBeanConfiguration
import grails.spring.BeanBuilder
import org.grails.cxf.utils.GrailsCxfUtils

/**
 */
class MockConfigurationSpec extends Specification {

    protected class TestOneEndpoint {}

    protected class TestTwoEndpoint {}

    @Shared
    Closure getArtefactsMock = {String type ->
        assert EndpointArtefactHandler.TYPE == type
        return [new DefaultGrailsEndpointClass(TestOneEndpoint),
                new DefaultGrailsEndpointClass(TestTwoEndpoint)] as GrailsClass[]
    }

    @Shared
    Closure getCxfConfig = {
        Class scriptClass = getClass().classLoader.loadClass('DefaultCxfConfig')
        ConfigObject config = new ConfigSlurper().parse(scriptClass)
        println config.toString()
        return config
    }

    @Shared
    GrailsApplication grailsApplicationMock = [
            getArtefacts: getArtefactsMock,
            getConfig: getCxfConfig] as GrailsApplication
    @Shared
    EndpointBeanConfiguration bc
    @Shared
    BeanBuilder bb

    def setupSpec() {
        GrailsCxfUtils.grailsApplication = grailsApplicationMock
        bc = new EndpointBeanConfiguration(grailsApplicationMock)
        bb = new BeanBuilder()
    }
}