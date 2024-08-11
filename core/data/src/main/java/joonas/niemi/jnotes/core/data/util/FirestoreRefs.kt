package joonas.niemi.jnotes.core.data.util

import com.google.firebase.firestore.FirebaseFirestore

fun FirebaseFirestore.user(uid: String) = collection("user").document(uid)
fun FirebaseFirestore.notes() = collection("notes")