package io.sulek.permissionhandler

data class Permission(
        val name: String,
        val scope: Scope,
        val handleDenied: Boolean = false,
        var deniedMessageRes: Int? = null
)

enum class Scope {
    ALL_ACTIVITIES,
    SINGLE_ACTIVITY,
    SINGLE_FRAGMENT
}
