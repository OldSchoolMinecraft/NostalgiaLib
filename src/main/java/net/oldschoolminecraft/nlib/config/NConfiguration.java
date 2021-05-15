package net.oldschoolminecraft.nlib.config;

import org.bukkit.util.config.Configuration;

import java.io.File;

/**
 * @author JohnyMuffin (sorry for stealing your code)
 * @author moderator_man
 */
public class NConfiguration extends Configuration
{
    public NConfiguration(File file)
    {
        super(file);

        addOption("config-version", 1);
        addOption("features.updater", true);
        addOption("features.pex-framework", true);
        addOption("features.module-framework", true);
    }

    private void updateConfig()
    {
        // nothing to do, because this is the first version
    }

    private void addOption(String key, Object defaultValue)
    {
        if (this.getProperty(key) == null)
            this.setProperty(key, defaultValue);
        Object value = this.getProperty(key);
        this.removeProperty(key);
        this.setProperty(key, value);
    }

    public Object getOption(String key)
    {
        return this.getProperty(key);
    }

    public Object getOption(String key, Object defaultValue)
    {
        Object value = getOption(key);
        if (value == null)
            return defaultValue;
        return value;
    }
}
