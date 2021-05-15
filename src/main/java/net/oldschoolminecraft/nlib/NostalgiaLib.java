package net.oldschoolminecraft.nlib;

import net.oldschoolminecraft.nlib.cmd.NCommandManager;
import net.oldschoolminecraft.nlib.module.internal.ModuleTracker;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class NostalgiaLib extends JavaPlugin
{
    private static NostalgiaLib singleton;

    private Logger log = Logger.getLogger("NostalgiaLib");
    private NCommandManager commandManager;
    private ModuleTracker moduleTracker;

    public void onEnable()
    {
        singleton = this;

        commandManager = new NCommandManager(this);

        log.info("NostalgiaLib enabled");
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        return this.commandManager.execute(sender, command, args);
    }

    public void onDisable()
    {
        log.info("NostalgiaLib disabled");
    }

    /**
     * THIS IS AN INTERNAL CLASS. DO NOT USE THIS FUNCTION.
     * @return
     */
    public ModuleTracker getModuleTracker()
    {
        return moduleTracker;
    }

    public static NostalgiaLib getInstance()
    {
        return singleton;
    }
}
