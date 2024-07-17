package com.caracrepair.app.utils

import com.google.firebase.messaging.FirebaseMessaging
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FirebaseUtil {
    suspend fun getInstanceId(): String {
        return suspendCoroutine { cont ->
            FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val token = task.result
                    cont.resume(token)
                } else {
                    cont.resumeWithException(task.exception!!)
                }
            }
        }
    }
}