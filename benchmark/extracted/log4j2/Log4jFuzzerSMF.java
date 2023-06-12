import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Core;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.RootLoggerComponentBuilder;
import org.apache.logging.log4j.core.config.builder.impl.DefaultConfigurationBuilder;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.status.StatusLogger;

public class Log4jFuzzerSMF {

    private final static Logger log = LogManager.getLogger(Log4jFuzzer.class.getName());

    public static void FuzzOne(String SMFData) {
        log.error(SMFData);
    }

    public static void fuzzerInitialize() {
        DefaultConfigurationBuilder configBuilder = new DefaultConfigurationBuilder();
        configBuilder.setPackages(FuzzingAppender.class.getPackage().getName());
        AppenderComponentBuilder fuzzingAppender = configBuilder.newAppender("nullAppender", "FuzzingAppender");
        configBuilder.add(fuzzingAppender);
        RootLoggerComponentBuilder rootLogger = configBuilder.newRootLogger();
        rootLogger.add(configBuilder.newAppenderRef("nullAppender"));
        configBuilder.add(rootLogger);
        Configurator.reconfigure(configBuilder.build());
        StatusLogger.getLogger().reset();
        StatusLogger.getLogger().setLevel(Level.OFF);
    }

    @Plugin(name = "FuzzingAppender", category = Core.CATEGORY_NAME, elementType = Appender.ELEMENT_TYPE)
    public static class FuzzingAppender extends AbstractAppender {

        protected FuzzingAppender(String name) {
            super(name, null, PatternLayout.createDefaultLayout(), true);
        }

        @PluginFactory
        public static FuzzingAppender createAppender(@PluginAttribute("name") String name) {
            return new FuzzingAppender(name);
        }

        @Override
        public void append(LogEvent event) {
            try {
                getLayout().toByteArray(event);
            } catch (Exception ignored) {
            }
        }
    }
}
