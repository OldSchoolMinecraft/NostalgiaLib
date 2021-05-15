package net.oldschoolminecraft.nlib.module;

import net.oldschoolminecraft.nlib.NostalgiaLib;
import net.oldschoolminecraft.nlib.exceptions.PluginConflictException;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.logging.Logger;

public class ModuleManager
{
    private static Logger log = Logger.getLogger("NostalgiaLib");
    private Plugin plugin;
    private ArrayList<Module> modules;

    public ModuleManager(Plugin plugin)
    {
        this.plugin = plugin;
    }

    public void register(Module module) throws PluginConflictException
    {
        NostalgiaLib.getInstance().getModuleTracker().trackModule(plugin, module);
        this.modules.add(module);
        log.info(plugin.getDescription().getName() + " registered module: " + module);
    }

    public boolean isModuleEnabled(Class<?> moduleClass)
    {
        if (!Module.class.isAssignableFrom(moduleClass))
            throw new IllegalArgumentException("Class is not a module");
        return moduleClass.getAnnotation(Module.class).enabled();
    }
}
