package com.marklogic.spring.batch.item;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemStreamSupport;

import com.marklogic.client.io.DocumentMetadataHandle;
import com.marklogic.client.io.DocumentMetadataHandle.Capability;
import org.springframework.beans.factory.annotation.Value;

/**
 * Base class for writing documents. Should be able to support both the Client API and XCC.
 */
public abstract class AbstractDocumentWriter extends ItemStreamSupport {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("#{jobParameters['output_collections']}")
    private String[] collections;

    private String directory;

    @Value("#{jobParameters['output_uri_prefix']}")
    private String outputUriPrefix;

    @Value("#{jobParameters['output_uri_suffix']}")
    private String outputUriReplace;

    @Value("#{jobParameters['output_uri_replace']}")
    private String outputUriSuffix;

    // Comma-separated list of role,read,role,update, just like in Client API
    private String permissions;

    protected DocumentMetadataHandle buildMetadata() {
        DocumentMetadataHandle h = new DocumentMetadataHandle();
        h = h.withCollections(collections);
        if (permissions != null) {
            String[] array = permissions.split(",");
            for (int i = 0; i < array.length; i += 2) {
                h.getPermissions().add(array[i], Capability.valueOf(array[i + 1].toUpperCase()));
            }
        }
        return h;
    }

    public void setCollections(String... collections) {
        this.collections = collections;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public void setOutputUriPrefix(String outputUriPrefix) {
        this.outputUriPrefix = outputUriPrefix;
    }

    public void setOutputUriSuffix(String outputUriSuffix) {
        this.outputUriSuffix = outputUriSuffix;
    }

    public void setOutputUriReplace(String outputUriReplace) {
        this.outputUriReplace = outputUriReplace;
    }

    public String getOutputUriPrefix(String outputUriPrefix) {
        return outputUriPrefix;
    }

    public String getOutputUriSuffix(String outputUriSuffix) {
        return outputUriSuffix;
    }

    public String getOutputUriReplace(String outputUriReplace) {
        return outputUriReplace;
    }

}