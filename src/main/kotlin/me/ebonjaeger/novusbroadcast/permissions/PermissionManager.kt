package me.ebonjaeger.novusbroadcast.permissions

import me.ebonjaeger.novusbroadcast.ConsoleLogger
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.PluginManager

class PermissionManager(private val pluginManager: PluginManager)
{

    private var usingPermissionsPlugin = false

    init
    {
        checkForPlugins()
    }

    /**
     * Evaluate whether the sender has permission to perform an action.
     *
     * @param sender The sender to evaluate.
     * @param node The permission node.
     * @return If the sender has permission.
     */
    fun hasPermission(sender: CommandSender?, node: PermissionNode?): Boolean
    {
        if (node == null)
            return true

        if ((sender !is Player) || !usingPermissionsPlugin)
        {
            return node.getDefaultPermission().evaluate(sender)
        }

        return sender.hasPermission(node.getNode())
    }

    fun onPluginDisable(pluginName: String)
    {
        if (isPermissionSystem(pluginName))
        {
            checkForPlugins()
        }
    }

    fun onPluginEnable(pluginName: String)
    {
        if (isPermissionSystem(pluginName))
            checkForPlugins()
    }

    fun isPermissionSystem(name: String): Boolean
    {
        ConsoleLogger.debug("[PERMS] Checking plugin '$name'...")

        for (type in PermissionSystem.values())
        {
            if (type.pluginName == name)
            {
                return true
            }
        }

        return false
    }

    fun checkForPlugins()
    {
        ConsoleLogger.debug("[PERMS] Checking for permissions plugins")

        usingPermissionsPlugin = false

        for (type in PermissionSystem.values())
        {
            try
            {
                val plugin = pluginManager.getPlugin(type.pluginName) ?: continue

                if (!plugin.isEnabled)
                {
                    continue
                }

                usingPermissionsPlugin = true
                ConsoleLogger.debug("[PERMS] usingPermissionsPlugin is set to true")
            } catch (ex: Exception)
            {
                ConsoleLogger.warning("Error encountered while checking for permission plugin:", ex)
            }
        }
    }
}
