package com.mobilebreakero.data.repoimpl

import android.util.Log
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.mobilebreakero.domain.model.Post
import com.mobilebreakero.domain.repo.PostsRepo
import com.mobilebreakero.domain.repo.addPostResponse
import com.mobilebreakero.domain.repo.postResponse
import com.mobilebreakero.domain.util.Response
import com.mobilebreakero.domain.util.getCollection
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PostRepoImpl @Inject constructor(
    private val postsRef: CollectionReference
): PostsRepo {
    override suspend fun addPost(
        post: Post,
        onSuccessListener: OnSuccessListener<Void>,
        onFailureListener: OnFailureListener
    ): addPostResponse {
        return try {
            Log.e("SuccessPosst", "DocumentSnapshot successfully written!")
            val postCollection = getCollection(Post.COLLECTION_NAME)
            val postDoc = postCollection.document(post.id!!)
            postDoc.set(post)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener)
            Log.e("SuccessPost", "DocumentSnapshot successfully written!")
            Response.Success(true)
        } catch (e: Exception) {
            Log.e("FailedPost", "Error writing document", e)
            Response.Failure(e)
        }
    }

    override suspend fun getPosts(): postResponse {
        return try {
            val db = FirebaseFirestore.getInstance()
            val postDocument = db.collection(Post.COLLECTION_NAME).get().await()

            if (!postDocument.isEmpty) {
                Log.e("A7A124", "posts.size.toString()")
                val post = postDocument.toObjects(Post::class.java) as List<Post>
                post.let { Response.Success(it) }
            } else {
                Response.Failure(Exception("Not found any posts"))
            }
        } catch (e: Exception) {
            Log.e("A7A", "LoL4")
            Response.Failure(e)
        }
    }
}