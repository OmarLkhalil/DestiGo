package com.example.domain.util

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resumeWithException


@OptIn(ExperimentalCoroutinesApi::class)
suspend fun <T> Task<T>.await(): T {
    return suspendCancellableCoroutine { cont ->
        addOnCompleteListener {
            if (it.exception != null) {
                cont.resumeWithException(it.exception!!)
            } else {
                cont.resume(it.result, null)
            }
        }
    }
}


fun getCollection (collectionName:String): CollectionReference {
    val db = Firebase.firestore
    return  db.collection(collectionName)
}

fun sendEmail(email: String, context: Context){
    FirebaseAuth.getInstance().sendPasswordResetEmail(email)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.e("sendEmail", "Email sent.")
                mToast(context = context)
            } else {
                Log.e("sendEmail", "Email not sent.")
            }
        }
}

private fun mToast(context: Context){
    Toast.makeText(context, "This is a Sample Toast", Toast.LENGTH_LONG).show()
}

//fun sendMessage(context: Context) {
//
//    val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
//            // تم التحقق بنجاح
//            // يمكنك تنفيذ الإجراءات اللازمة هنا، مثل تسجيل الدخول
//            Log.e("SendCode", "Success")
//        }
//
//        override fun onVerificationFailed(exception: FirebaseException) {
//            // فشل التحقق
//            // يمكنك تنفيذ الإجراءات اللازمة هنا للتعامل مع الخطأ
//            Log.e("SendCode", exception.message.toString())
//        }
//
//        override fun onCodeSent(
//            verificationId: String,
//            token: PhoneAuthProvider.ForceResendingToken
//        ) {
//            Log.e("SendCode", "Success11")
//            // تم إرسال رمز التحقق بنجاح
//            // يمكنك تخزين `verificationId` لاستخدامه في التحقق من رمز التحقق لاحقًا
//        }
//    }
//
//    val auth = Firebase.auth
//    val options = PhoneAuthOptions.newBuilder(auth)
//        .setPhoneNumber("+201002841160") // Phone number to verify
//        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
//        .setActivity(context as Activity) // Activity (for callback binding)
//        .setCallbacks(callbacks)
//        .build()
//    PhoneAuthProvider.verifyPhoneNumber(options)
//}