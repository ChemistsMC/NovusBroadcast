package me.ebonjaeger.novusbroadcast.listeners

import me.ebonjaeger.novusbroadcast.permissions.PermissionManager
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.server.PluginDisableEvent
import org.bukkit.event.server.PluginEnableEvent

class PluginListener(private val permissionManager: PermissionManager) : Listener
{

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onPluginDisable(event: PluginDisableEvent)
    {
        val name = event.plugin.name

        permissionManager.onPluginDisable(name)
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onPluginEnable(event: PluginEnableEvent)
    {
        val name = event.plugin.name

        permissionManager.onPluginEnable(name)
    }
}
