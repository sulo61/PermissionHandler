package io.sulek.permissionhandler

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

class PermissionHandler {
    private lateinit var bundle: PermissionBundle
    private val missingPermissions = mutableListOf<Permission>()

    fun requestCheckPermissions(activity: Activity, bundle: PermissionBundle) {
        if (canCheckPermissions()) {
            this.bundle = bundle
            checkPermissions(activity)
        }
    }

    private fun checkPermissions(activity: Activity) {
        missingPermissions.clear()
        bundle.allActivitiesScope?.let { missingPermissions.addAll(getMissingPermissions(activity, it.permissions)) }
        bundle.singleActivityScope?.let { missingPermissions.addAll(getMissingPermissions(activity, it.permissions)) }
        bundle.singleFragmentScope?.let { missingPermissions.addAll(getMissingPermissions(activity, it.permissions)) }

        if (missingPermissions.isEmpty()) {
            onPermissionsGranted()
            return
        }

        setStatusChecking()
        ActivityCompat.requestPermissions(activity, missingPermissions.map { it.name }.toTypedArray(), REQUEST_CODE)
    }

    private fun getMissingPermissions(activity: Activity, list: List<Permission>) =
        list.filter { !activity.isPermissionGranted(it.name) }

    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CODE) {
            getDeniedPermissions(grantResults, permissions).run {
                if (isEmpty()) onPermissionsGranted()
                else onPermissionsDenied(this)
                setStatusResult()
            }
        }
    }

    private fun getDeniedPermissions(result: IntArray, permissions: Array<out String>): MutableList<Permission> {
        val deniedPermissions = mutableListOf<Permission>()

        for (i in 0 until result.size) {
            if (result[i] == PackageManager.PERMISSION_DENIED) {
                permissions[i].let { p ->
                    missingPermissions.find { it.name == p }?.let { deniedPermissions.add(it) }
                }
            }
        }

        return deniedPermissions
    }

    private fun onPermissionsGranted() {
        bundle.allActivitiesScope?.let { it.listener?.onResult(true) }
        bundle.singleActivityScope?.let { it.listener?.onResult(true) }
        bundle.singleFragmentScope?.let { it.listener?.onResult(true) }
    }


    private fun onPermissionsDenied(permissions: List<Permission>) {
        bundle.allActivitiesScope?.let { it.listener?.onResult(false, permissions) }
        bundle.singleActivityScope?.let { it.listener?.onResult(false, permissions) }
        bundle.singleFragmentScope?.let { it.listener?.onResult(false, permissions) }
    }

    companion object {
        private const val REQUEST_CODE = 13579
        private var status = PermissionStatus.READY

        fun setStatusChecking() {
            status = PermissionStatus.CHECKING
        }

        fun setStatusResult() {
            status = PermissionStatus.RESULT
        }

        private fun setStatusReady() {
            status = PermissionStatus.READY
        }

        fun canCheckPermissions() = status == PermissionStatus.READY
        private fun alreadyHasPermissionsResult() = status == PermissionStatus.RESULT

        fun onResume(checkPermissionsIfPossible: () -> Unit) {
            if (canCheckPermissions()) checkPermissionsIfPossible.invoke()
            else if (alreadyHasPermissionsResult()) setStatusReady()
        }
    }
}