package org.apache.maven.plugin.install;

import java.io.File;

import org.apache.maven.plugin.testing.AbstractMojoTestCase;

/*
 * Copyright 2001-2006 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * @author aramirez
 */

public class InstallFileMojoTest
    extends AbstractMojoTestCase
{
    private String groupId;
    
    private String artifactId;
    
    private String version;
    
    private String packaging;
    
    private File file;
    
    private final String LOCAL_REPO = "target/local-repo/";
    
    public void testInstallFileTestEnvironment()
        throws Exception
    {
        File testPom = new File( getBasedir(), 
                                 "target/test-classes/unit/install-file-basic-test/plugin-config.xml" );
        
        InstallFileMojo mojo = ( InstallFileMojo ) lookupMojo( "install-file", testPom );
        
        assertNotNull( mojo );
    }   
    
    public void testBasicInstallFile()
        throws Exception
    {
        File testPom = new File( getBasedir(), 
                                 "target/test-classes/unit/install-file-basic-test/plugin-config.xml" );
        
        InstallFileMojo mojo = ( InstallFileMojo ) lookupMojo( "install-file", testPom );
        
        assertNotNull( mojo );
        
        assignValuesForParameter( mojo );
        
        mojo.execute();
        
        File installedArtifact = new File( LOCAL_REPO + 
                                           groupId + "/" + artifactId + "/" +
                                           version + "/" + artifactId + "-" +
                                           version + "." + packaging );
     
        assertTrue( installedArtifact.exists() );
    }
    
    public void testInstallFileWithGeneratePom()
        throws Exception
    {
        File testPom = new File( getBasedir(), 
                                 "target/test-classes/unit/install-file-test-generatePom/plugin-config.xml" );
        
        InstallFileMojo mojo = ( InstallFileMojo ) lookupMojo( "install-file", testPom );
        
        assertNotNull( mojo );
        
        assignValuesForParameter( mojo );
        
        mojo.execute();
        
        File installedArtifact = new File( LOCAL_REPO + 
                                           groupId + "/" + artifactId + "/" +
                                           version + "/" + artifactId + "-" +
                                           version + "." + packaging );
     
        assertTrue( ( ( Boolean ) getVariableValueFromObject( mojo, "generatePom" ) ).booleanValue() );
        
        assertTrue( installedArtifact.exists() );
    }
    
    private void assignValuesForParameter( Object obj )
        throws Exception
    {
        this.groupId = dotToSlashReplacer( ( String ) getVariableValueFromObject( obj, "groupId" ) );
        
        this.artifactId = ( String ) getVariableValueFromObject( obj, "artifactId" );
        
        this.version = ( String ) getVariableValueFromObject( obj, "version" );
        
        this.packaging  = ( String ) getVariableValueFromObject( obj, "packaging" );
        
        this.file = ( File ) getVariableValueFromObject( obj, "file" );
    }
    
    private String dotToSlashReplacer( String parameter )
    {
        return parameter.replace( '.', '/' );
    }    
}