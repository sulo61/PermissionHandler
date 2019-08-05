package io.sulek.permissionhandler

data class PermissionBundle(
    val allActivitiesScope: ScopeRequest? = null,
    var singleActivityScope: ScopeRequest? = null,
    var singleFragmentScope: ScopeRequest? = null
) {

    fun getHandlingLifecycleScope(): Scope? {
        singleFragmentScope?.let { return Scope.SINGLE_FRAGMENT }
        singleActivityScope?.let { return Scope.SINGLE_ACTIVITY }
        allActivitiesScope?.let { return Scope.ALL_ACTIVITIES }
        return null
    }

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
        val listener: PermissionListener ? = null
    )
}
