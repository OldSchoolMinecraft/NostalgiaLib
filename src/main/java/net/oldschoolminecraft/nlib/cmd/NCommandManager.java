package net.oldschoolminecraft.nlib.cmd;

import java.util.logging.*;

import net.oldschoolminecraft.nlib.exceptions.AutoCompleteChoicesException;
import net.oldschoolminecraft.nlib.utils.StringUtils;

import org.bukkit.command.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.*;
import java.util.*;
import java.util.regex.*;

/**
 * @author tehkode
 */
public class NCommandManager
{
    protected static final Logger logger;
    protected Map<String, Map<CommandSyntax, CommandBinding>> listeners;
    protected Plugin plugin;
    protected List<Plugin> helpPlugins;

    public NCommandManager(final Plugin plugin)
    {
        this.listeners = new HashMap<String, Map<CommandSyntax, CommandBinding>>();
        this.helpPlugins = new LinkedList<Plugin>();
        this.plugin = plugin;
        this.findCommandHelpPlugins();
    }

    public void register(final NCommandListener listener)
    {
        for (final Method method : listener.getClass().getMethods())
        {
            if (method.isAnnotationPresent(NCommand.class))
            {
                final NCommand cmdAnnotation = method.getAnnotation(NCommand.class);
                Map<CommandSyntax, CommandBinding> commandListeners = this.listeners.computeIfAbsent(cmdAnnotation.name(), k -> new HashMap<CommandSyntax, CommandBinding>());
                this.registerCommandHelp(cmdAnnotation);
                commandListeners.put(new CommandSyntax(cmdAnnotation.syntax()), new CommandBinding(listener, method));
            }
        }
    }

    public boolean execute(final CommandSender sender, final org.bukkit.command.Command command, final String[] args)
    {
        final Map<CommandSyntax, CommandBinding> callMap = this.listeners.get(command.getName());
        if (callMap == null)
            return false;
        CommandBinding selectedBinding = null;
        final int argumentsLength = 0;
        final String arguments = StringUtils.implode(args, " ");
        for (final Map.Entry<CommandSyntax, CommandBinding> entry : callMap.entrySet())
        {
            final CommandSyntax syntax = entry.getKey();
            if (!syntax.isMatch(arguments))
                continue;
            final CommandBinding binding = entry.getValue();
            binding.setParams(syntax.getMatchedArguments(arguments));
            selectedBinding = binding;
        }
        if (selectedBinding == null)
        {
            sender.sendMessage(ChatColor.RED + "Error in command syntax. Check command help.");
            return true;
        }
        if (sender instanceof Player && !selectedBinding.checkPermissions((Player) sender))
        {
            logger.warning("User " + ((Player) sender).getName() + " was tried to access chat command \"" + command.getName() + " " + arguments + "\"," + " but don't have permission to do this.");
            sender.sendMessage(ChatColor.RED + "Sorry, you don't have enough permissions.");
            return true;
        }
        try
        {
            selectedBinding.call(this.plugin, sender, selectedBinding.getParams());
        } catch (InvocationTargetException e)
        {
            if (!(e.getTargetException() instanceof AutoCompleteChoicesException))
            {
                throw new RuntimeException(e.getTargetException());
            }
            final AutoCompleteChoicesException autocomplete = (AutoCompleteChoicesException) e.getTargetException();
            sender.sendMessage("Autocomplete for <" + autocomplete.getArgName() + ">:");
            sender.sendMessage("    " + StringUtils.implode(autocomplete.getChoices(), "   "));
        } catch (Exception e2)
        {
            logger.severe("There is bogus command handler for " + command.getName() + " command. (Is appropriate plugin is update?)");
            if (e2.getCause() != null)
            {
                e2.getCause().printStackTrace();
            } else
            {
                e2.printStackTrace();
            }
        }
        return true;
    }

    protected void findCommandHelpPlugins()
    {
    }

    protected void registerCommandHelp(final NCommand command)
    {
        if (command.description().isEmpty())
            return;
    }

    static
    {
        logger = Logger.getLogger("NostalgiaLib");
    }

    protected class CommandSyntax
    {
        protected String originalSyntax;
        protected String regexp;
        protected List<String> arguments;

        public CommandSyntax(final String syntax)
        {
            this.arguments = new LinkedList<String>();
            this.originalSyntax = syntax;
            this.regexp = this.prepareSyntaxRegexp(syntax);
        }

        public String getRegexp()
        {
            return this.regexp;
        }

        private String prepareSyntaxRegexp(final String syntax)
        {
            String expression = syntax;
            final Matcher argMatcher = Pattern.compile("(?:[\\s]+)((\\<|\\[)([^\\>\\]]+)(?:\\>|\\]))").matcher(expression);
            int index = 0;
            while (argMatcher.find())
            {
                if (argMatcher.group(2).equals("["))
                {
                    expression = expression.replace(argMatcher.group(0), "(?:(?:[\\s]+)(\"[^\"]+\"|[^\\s]+))?");
                } else
                {
                    expression = expression.replace(argMatcher.group(1), "(\"[^\"]+\"|[\\S]+)");
                }
                this.arguments.add(index++, argMatcher.group(3));
            }
            return expression;
        }

        public boolean isMatch(final String str)
        {
            return str.matches(this.regexp);
        }

        public Map<String, String> getMatchedArguments(final String str)
        {
            final Map<String, String> matchedArguments = new HashMap<>(this.arguments.size());
            if (this.arguments.size() > 0)
            {
                final Matcher argMatcher = Pattern.compile(this.regexp).matcher(str);
                if (argMatcher.find())
                {
                    for (int index = 1; index <= argMatcher.groupCount(); ++index)
                    {
                        String argumentValue = argMatcher.group(index);
                        if (argumentValue != null)
                        {
                            if (!argumentValue.isEmpty())
                            {
                                if (argumentValue.startsWith("\"") && argumentValue.endsWith("\""))
                                    argumentValue = argumentValue.substring(1, argumentValue.length() - 1);
                                matchedArguments.put(this.arguments.get(index - 1), argumentValue);
                            }
                        }
                    }
                }
            }
            return matchedArguments;
        }
    }

    protected class CommandBinding
    {
        protected Object object;
        protected Method method;
        protected Map<String, String> params;

        public CommandBinding(final Object object, final Method method)
        {
            this.params = new HashMap<>();
            this.object = object;
            this.method = method;
        }

        public NCommand getMethodAnnotation()
        {
            return this.method.getAnnotation(NCommand.class);
        }

        public Map<String, String> getParams()
        {
            return this.params;
        }

        public void setParams(final Map<String, String> params)
        {
            this.params = params;
        }

        public boolean checkPermissions(final Player player)
        {
            String[] permissions = this.getMethodAnnotation().permissions();
            if (permissions.length == 0 && !this.getMethodAnnotation().permission().isEmpty())
            {
                permissions = new String[]{this.getMethodAnnotation().permission()};
            }
            boolean lastORValue = false;
            for (int i = 0; i < permissions.length; ++i)
            {
                String permission = permissions[i];
                if (!this.isOR(permission))
                {
                    if (permission.contains("<"))
                    {
                        final String originalPermission = permission.toString();
                        for (final Map.Entry<String, String> entry : this.getParams().entrySet())
                            if (entry.getValue() != null)
                                permission = permission.replace("<" + entry.getKey() + ">", entry.getValue());
                        if (permission.equals(originalPermission))
                            continue;
                    }
                    if (!player.hasPermission(permission))
                    {
                        if (i >= permissions.length - 2 || !this.isOR(permissions[i + 1]) || this.isOR(permissions[i + 2]))
                        {
                            if (i <= 1 || !this.isOR(permissions[i - 1]) || this.isOR(permissions[i - 2]) || !lastORValue)
                                return false;
                            lastORValue = false;
                        }
                    } else if (i < permissions.length - 2 && this.isOR(permissions[i + 1]) && !this.isOR(permissions[i + 2])) {
                        lastORValue = true;
                    }
                }
            }
            return true;
        }

        protected boolean isOR(final String permission)
        {
            return "OR".equalsIgnoreCase(permission) || "||".equals(permission);
        }

        public void call(final Object... args) throws Exception
        {
            this.method.invoke(this.object, args);
        }
    }
}
