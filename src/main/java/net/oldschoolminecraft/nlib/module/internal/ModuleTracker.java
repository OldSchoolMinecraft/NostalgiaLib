package net.oldschoolminecraft.nlib.module.internal;

import net.oldschoolminecraft.nlib.exceptions.PluginConflictException;
import net.oldschoolminecraft.nlib.module.Module;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;

public class ModuleTracker
{
    private HashMap<Plugin, ArrayList<Module>> pluginModules;

    /**
     * THIS IS AN INTERNAL CLASS. DO NOT USE THIS FUNCTION.
     * @param plugin
     * @param module
     * @throws PluginConflictException
     */
    public void trackModule(Plugin plugin, Module module) throws PluginConflictException
    {
        ArrayList<Module> mList = pluginModules.get(plugin);
        if (mList != null)
        {
            for (Module m : mList)
                if (doModulesConflict(m, module))
                    throw new PluginConflictException(String.format("%s tried to register module '%s', but %s has already registered a module with the same name"));
            mList.add(module);
            pluginModules.put(plugin, mList);
        } else {
            ArrayList<Module> freshModules = new ArrayList<>();
            freshModules.add(module);
            pluginModules.put(plugin, freshModules);
        }
    }

    private boolean doModulesConflict(Module a, Module b)
    {
        return a.name().equalsIgnoreCase(b.name());
    }
}
