package com.mobilebreakero.domain.usecase.firestore.post

data class PostUseCase(
    val addPost: AddPostUseCase,
    val getPosts: GetPostsUseCase,
    val likePost: LikePostUseCase,
    val getPostsByUserId: GetPostsById,
    val deletePost: DeletePostUseCase,
    val sharePost: SharePostUseCase,
    val addComment: AddCommentUseCase,
    val getPostDetails: GetPostDetails
)
