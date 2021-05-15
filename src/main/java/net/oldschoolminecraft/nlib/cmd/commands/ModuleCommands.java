package net.oldschoolminecraft.nlib.cmd.commands;

import net.oldschoolminecraft.nlib.cmd.NCommand;
import net.oldschoolminecraft.nlib.cmd.NCommandListener;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.util.Map;

public class ModuleCommands implements NCommandListener
{
    @NCommand(name = "nlib", syntax = "module <name> enable", description = "Enable plugin module", permission = "nlib.modules.enable")
    public void enableModule(final Plugin plugin, final CommandSender sender, final Map<String, String> args)
    {
        //
    }

    @NCommand(name = "nlib", syntax = "module <name> disable", description = "Disable plugin module", permission = "nlib.modules.disable")
    public void disableModule(final Plugin plugin, final CommandSender sender, final Map<String, String> args)
    {
        //
    }
}
