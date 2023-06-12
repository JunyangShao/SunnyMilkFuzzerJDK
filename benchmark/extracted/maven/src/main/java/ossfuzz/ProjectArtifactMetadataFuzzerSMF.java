package ossfuzz;

import org.apache.maven.project.artifact.ProjectArtifactMetadata;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.DefaultArtifact;
import org.apache.maven.artifact.InvalidArtifactRTException;
import org.apache.maven.artifact.handler.DefaultArtifactHandler;
import java.util.ArrayList;

public class ProjectArtifactMetadataFuzzerSMF {

    public static void FuzzOne(String SMFData) {
        Artifact a;
        try {
            a = new DefaultArtifact(fuzzedDataProvider.consumeString(10), fuzzedDataProvider.consumeString(10), fuzzedDataProvider.consumeString(10), fuzzedDataProvider.consumeString(10), fuzzedDataProvider.consumeString(10), fuzzedDataProvider.consumeString(10), new DefaultArtifactHandler(fuzzedDataProvider.consumeString(10)));
        } catch (IllegalArgumentException e) {
            return;
        } catch (InvalidArtifactRTException e) {
            return;
        }
        ProjectArtifactMetadata projectArtifactMetadata = new ProjectArtifactMetadata(a);
        a.getVersion();
        try {
            a.setVersion(fuzzedDataProvider.consumeString(10));
        } catch (IllegalArgumentException e) {
        }
        a.getScope();
        a.getType();
        a.getClassifier();
        a.hasClassifier();
        try {
            a.setBaseVersion(fuzzedDataProvider.consumeString(10));
        } catch (IllegalArgumentException e) {
        }
        a.getDependencyConflictId();
        a.getMetadataList();
        a.getRepository();
        a.getDownloadUrl();
        try {
            a.setDownloadUrl(fuzzedDataProvider.consumeString(10));
        } catch (IllegalArgumentException e) {
        }
        a.getDependencyFilter();
        a.getArtifactHandler();
        try {
            a.setDependencyTrail(new ArrayList<String>() {

                {
                    add(fuzzedDataProvider.consumeString(10));
                }
            });
        } catch (IllegalArgumentException e) {
        }
        try {
            a.setScope(fuzzedDataProvider.consumeString(10));
        } catch (IllegalArgumentException e) {
        }
        a.getVersionRange();
        try {
            a.selectVersion(fuzzedDataProvider.consumeString(10));
        } catch (IllegalArgumentException e) {
        }
        try {
            a.setGroupId(fuzzedDataProvider.consumeString(10));
        } catch (IllegalArgumentException e) {
        }
        try {
            a.setArtifactId(fuzzedDataProvider.consumeString(10));
        } catch (IllegalArgumentException e) {
        }
        a.isSnapshot();
        try {
            a.setResolved(fuzzedDataProvider.consumeBoolean());
        } catch (IllegalArgumentException e) {
        }
        try {
            a.setResolvedVersion(SMFData);
        } catch (IllegalArgumentException e) {
        }
        a.setRelease(fuzzedDataProvider.consumeBoolean());
        a.getAvailableVersions();
        a.isOptional();
        a.setOptional(fuzzedDataProvider.consumeBoolean());
        projectArtifactMetadata.getGroupId();
        projectArtifactMetadata.getArtifactId();
        projectArtifactMetadata.getBaseVersion();
        projectArtifactMetadata.getRemoteFilename();
        projectArtifactMetadata.storedInArtifactVersionDirectory();
        projectArtifactMetadata.getKey();
    }
}
