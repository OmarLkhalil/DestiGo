package com.mobilebreakero.domain.usecase

data class AuthUseCase(
    val currentUser: CurrentUser,
    val getAuthState: GetAuthState,
    val signInWithEmailAndPassword: SignInWithEmailAndPassword,
    val SignInAnnonymously: SignInAnnonymously,
    val signUpWithEmailAndPassword: SignUpWithEmailAndPassword,
    val signOut: SignOut,
    val reloadUser: ReloadUser,
    val sendPasswordResetEmail: SendPasswordResetEmail,
    val sendEmailVerification: SendEmailVerification
)