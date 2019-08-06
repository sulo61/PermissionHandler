package io.sulek.permissionhandler

data class PermissionBundle(
        var handlingScope: Scope? = null,
        var allActivitiesScope: ScopeRequest? = null,
        var singleActivityScope: ScopeRequest? = null,
        var singleFragmentScope: ScopeRequest? = null
) {

    fun clearSingleActivityScope() {
        singleActivityScope = null
    }

    fun clearSingleFragmentScope() {
        singleFragmentScope = null
    }

    fun clear() {
        clearSingleActivityScope()
        clearSingleFragmentScope()
    }

    data class ScopeRequest(
            val permissions: List<Permission> = listOf(),
            val listener: PermissionListener? = null
    )
}
