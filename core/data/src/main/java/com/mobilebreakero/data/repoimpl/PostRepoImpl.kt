package com.mobilebreakero.data.repoimpl

import android.content.Context
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.mobilebreakero.domain.model.Comment
import com.mobilebreakero.domain.model.Post
import com.mobilebreakero.domain.repo.PostsRepo
import com.mobilebreakero.domain.repo.addPostResponse
import com.mobilebreakero.domain.repo.postDetailsResponse
import com.mobilebreakero.domain.repo.postResponse
import com.mobilebreakero.domain.repo.updatePostResponse
import com.mobilebreakero.domain.util.Response
import com.mobilebreakero.domain.util.getCollection
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PostRepoImpl @Inject constructor() : PostsRepo {

    override suspend fun addPost(
        post: Post,
        onSuccessListener: OnSuccessListener<Void>,
        onFailureListener: OnFailureListener
    ): addPostResponse {
        return try {
            val postCollection = getCollection(Post.COLLECTION_NAME)
            val postDoc = postCollection.document(post.id!!)
            postDoc.set(post)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener)
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun likePost(
        postId: String,
        like: Int,
        userId: String,
        context: Context
    ): updatePostResponse {
        return try {
            val db = FirebaseFirestore.getInstance()
            val postDocument = db.collection(Post.COLLECTION_NAME).document(postId)


            db.runTransaction { transaction ->
                val postSnapshot = transaction.get(postDocument)

                if (postSnapshot.exists()) {
                    val likedUserIds =
                        postSnapshot.get("likedUserIds") as? List<String> ?: emptyList()

                    val isLiked = likedUserIds.contains(userId)
                    var likes = postSnapshot.get("numberOfLikes") as? Int ?: 0

                    if (isLiked) {
                        if (likes > 0)
                            likes -= 1
                        transaction.update(postDocument, "numberOfLikes", likes)
                        transaction.update(postDocument, "likedUserIds", likedUserIds - userId)
                    } else {
                        likes += 1
                        transaction.update(postDocument, "numberOfLikes", likes)
                        transaction.update(postDocument, "likedUserIds", likedUserIds + userId)
                    }
                }
                Response.Success(true)
            }.await()

        } catch (e: Exception) {
            Response.Failure(e)
        }
    }


    override suspend fun getPosts(): postResponse {
        return try {
            val db = FirebaseFirestore.getInstance()
            val postDocument = db.collection(Post.COLLECTION_NAME).get().await()

            if (!postDocument.isEmpty) {
                val post = postDocument.toObjects(Post::class.java) as List<Post>
                post.let { Response.Success(it) }
            } else {
                Response.Failure(Exception("Not found any posts"))
            }
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun getPostsByUserId(userId: String): postResponse {
        return try {
            val db = FirebaseFirestore.getInstance()
            val postDocument = db.collection(Post.COLLECTION_NAME)
                .whereEqualTo("userId", userId)
                .get()
                .await()

            if (!postDocument.isEmpty) {
                val post = postDocument.toObjects(Post::class.java) as List<Post>
                post.let { Response.Success(it) }
            } else {
                Response.Failure(Exception("Not found any posts"))
            }
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun deletePost(postId: String): updatePostResponse {
        return try {
            val db = FirebaseFirestore.getInstance()
            val postDocument = db.collection(Post.COLLECTION_NAME).document(postId)
            postDocument.delete().await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun sharePost(
        postId: String,
        userId: String,
        userName: String
    ): addPostResponse {
        return try {
            val db = FirebaseFirestore.getInstance()
            val postDocument = db.collection(Post.COLLECTION_NAME).document(postId)

            val postSnapshot = postDocument.get().await()
            val postData = postSnapshot.toObject(Post::class.java)

            val newPostId = GenerateRandomIdNumber().toString()
            val shareText =
                if (userId == postData?.userId) "${postData.userName} has shared his post" else "$userName has shared ${postData?.userName} post"

            if (postData != null) {
                val newPost = Post(
                    id = newPostId,
                    userId = userId,
                    numberOfLikes = postData.numberOfLikes,
                    userName = shareText,
                    likedUserIds = postData.likedUserIds,
                    comments = postData.comments,
                    image = postData.image,
                    location = postData.location,
                    profilePhoto = postData.profilePhoto
                )

                db.collection(Post.COLLECTION_NAME).document(newPostId).set(newPost).await()
                Response.Success(true)
            } else {
                Response.Failure(Exception("Post not found"))
            }
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun addComment(
        postId: String,
        comment: String,
        userName: String,
        userId: String
    ): updatePostResponse {
        return try {
            val db = FirebaseFirestore.getInstance()
            val postDocument = db.collection(Post.COLLECTION_NAME).document(postId)
            val newComment = Comment(userId = userId, userName = userName, text = comment)
            postDocument.update("comments", FieldValue.arrayUnion(newComment))
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun getPostDetails(postId: String): postDetailsResponse {
        return try {
            val db = FirebaseFirestore.getInstance()
            val postDocument = db.collection(Post.COLLECTION_NAME).document(postId).get().await()

            if (postDocument.exists()) {
                val post = postDocument.toObject(Post::class.java)
                post?.let { Response.Success(it) }
            } else {
                Response.Failure(Exception("Not found any posts"))
            }
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }
}

fun GenerateRandomIdNumber(): Int {
    return (100000000..999999999).random()
}
