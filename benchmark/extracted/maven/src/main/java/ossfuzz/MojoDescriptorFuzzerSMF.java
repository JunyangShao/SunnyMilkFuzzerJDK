package ossfuzz;

import org.apache.maven.plugin.descriptor.MojoDescriptor;
import org.apache.maven.plugin.descriptor.Parameter;
import org.apache.maven.plugin.descriptor.PluginDescriptor;
import java.util.ArrayList;
import java.util.List;
import org.apache.maven.plugin.descriptor.DuplicateParameterException;

public class MojoDescriptorFuzzerSMF {

    public static void FuzzOne(String SMFData) {
        MojoDescriptor mojoDescriptor = new MojoDescriptor();
        List<Parameter> listParameters = new ArrayList<Parameter>();
        int n = fuzzedDataProvider.consumeInt(1, 100);
        for (int i = 0; i <= n; i++) {
            Parameter param1 = new Parameter();
            param1.setName(fuzzedDataProvider.consumeString(10));
            param1.setDefaultValue(fuzzedDataProvider.consumeString(10));
            listParameters.add(param1);
        }
        try {
            mojoDescriptor.setParameters(listParameters);
        } catch (DuplicateParameterException e) {
        }
        mojoDescriptor.setPluginDescriptor(new PluginDescriptor());
        mojoDescriptor.getComponentType();
        mojoDescriptor.getLanguage();
        mojoDescriptor.setLanguage(fuzzedDataProvider.consumeString(10));
        mojoDescriptor.getDeprecated();
        mojoDescriptor.setDeprecated(fuzzedDataProvider.consumeString(10));
        mojoDescriptor.setDependencyCollectionRequired(fuzzedDataProvider.consumeString(10));
        mojoDescriptor.getDependencyCollectionRequired();
        mojoDescriptor.setProjectRequired(fuzzedDataProvider.consumeBoolean());
        mojoDescriptor.isProjectRequired();
        mojoDescriptor.setOnlineRequired(fuzzedDataProvider.consumeBoolean());
        mojoDescriptor.isOnlineRequired();
        mojoDescriptor.requiresOnline();
        mojoDescriptor.setPhase(fuzzedDataProvider.consumeString(10));
        mojoDescriptor.getPhase();
        mojoDescriptor.setSince(fuzzedDataProvider.consumeString(10));
        mojoDescriptor.getSince();
        mojoDescriptor.setGoal(fuzzedDataProvider.consumeString(10));
        mojoDescriptor.getGoal();
        mojoDescriptor.setExecutePhase(fuzzedDataProvider.consumeString(10));
        mojoDescriptor.getExecutePhase();
        mojoDescriptor.alwaysExecute();
        mojoDescriptor.setExecutionStrategy(fuzzedDataProvider.consumeString(10));
        mojoDescriptor.getExecutionStrategy();
        mojoDescriptor.getRole();
        mojoDescriptor.getRoleHint();
        mojoDescriptor.getId();
        mojoDescriptor.getFullGoalName();
        mojoDescriptor.getComponentType();
        mojoDescriptor.getPluginDescriptor();
        mojoDescriptor.setInheritedByDefault(fuzzedDataProvider.consumeBoolean());
        mojoDescriptor.isInheritedByDefault();
        mojoDescriptor.hashCode();
        mojoDescriptor.setExecuteLifecycle(fuzzedDataProvider.consumeString(10));
        mojoDescriptor.getExecuteLifecycle();
        mojoDescriptor.setAggregator(fuzzedDataProvider.consumeBoolean());
        mojoDescriptor.isAggregator();
        mojoDescriptor.setDirectInvocationOnly(fuzzedDataProvider.consumeBoolean());
        mojoDescriptor.isDirectInvocationOnly();
        mojoDescriptor.setRequiresReports(fuzzedDataProvider.consumeBoolean());
        mojoDescriptor.isRequiresReports();
        mojoDescriptor.setExecuteGoal(SMFData);
        mojoDescriptor.getExecuteGoal();
        mojoDescriptor.setThreadSafe(fuzzedDataProvider.consumeBoolean());
        mojoDescriptor.isThreadSafe();
        mojoDescriptor.isForking();
        mojoDescriptor.getParameters();
    }
}
