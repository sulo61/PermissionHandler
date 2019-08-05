package io.sulek.permissionhandler

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

fun Context.isPermissionGranted(permission: String) = ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED

fun Activity.shouldAskForPermission(permission: String) = ActivityCompat.shouldShowRequestPermissionRationale(this, permission)