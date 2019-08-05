package io.sulek.permissionhandlersample

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.sulek.permissionhandler.*

abstract class BaseActivity : AppCompatActivity(), BasePermissionActivity {

    private lateinit var permissionHandler: PermissionHandler
    private lateinit var permissionBundle: PermissionBundle

    private val allActivitiesPermissions = mutableListOf(
            Permission(Manifest.permission.ACCESS_FINE_LOCATION, Scope.ALL_ACTIVITIES)
    )
    private val allActivitiesScopeRequest = PermissionBundle.ScopeRequest(
            allActivitiesPermissions,
            createAllActivitiesPermissionListener()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionHandler = PermissionHandler()
        permissionBundle = PermissionBundle(allActivitiesScopeRequest)
    }

    override fun onResume() {
        super.onResume()
        PermissionHandler.onResume {
            if (getHandlingLifecycleScope() == Scope.ALL_ACTIVITIES) requestCheckPermissions()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionHandler.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun requestSingleActivityPermissions(scopeRequest: PermissionBundle.ScopeRequest) {
        permissionBundle.singleActivityScope = scopeRequest
    }

    override fun clearSingleActivityPermission() = permissionBundle.clearSingleActivityScope()

    override fun requestSingleFragmentPermissions(scopeRequest: PermissionBundle.ScopeRequest) {
        permissionBundle.singleFragmentScope = scopeRequest
    }

    override fun clearSingleFragmentPermission() = permissionBundle.clearSingleFragmentScope()

    private fun createAllActivitiesPermissionListener() = object : PermissionListener {
        override fun onResult(allGranted: Boolean, denied: List<Permission>) {
            Log.e("PermissionHandler", "allActivitiesResult")
        }
    }

    override fun getHandlingLifecycleScope(): Scope? = permissionBundle.getHandlingLifecycleScope()

    override fun requestCheckPermissions() = permissionHandler.requestCheckPermissions(this, permissionBundle)

}