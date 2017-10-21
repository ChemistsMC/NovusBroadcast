package me.ebonjaeger.novusbroadcast.permissions

enum class AdminPermission (private val node: String,
                            private val defaultPermission: DefaultPermission) : PermissionNode
{

    HELP("novusbroadcast.help", DefaultPermission.OP_ONLY),

    INFO("novusbroadcast.info", DefaultPermission.OP_ONLY),

    LIST("novusbroadcast.list", DefaultPermission.OP_ONLY),

    RELOAD("novusbroadcast.reload", DefaultPermission.OP_ONLY),

    SEND("novusbroadcast.send", DefaultPermission.OP_ONLY),

    VERSION("novusbroadcast.version", DefaultPermission.OP_ONLY);

    override fun getNode(): String
    {
        return node
    }

    override fun getDefaultPermission(): DefaultPermission
    {
        return defaultPermission
    }
}
