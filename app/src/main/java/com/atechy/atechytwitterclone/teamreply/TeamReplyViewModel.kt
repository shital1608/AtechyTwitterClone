package com.atechy.atechytwitterclone.teamreply

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atechy.atechytwitterclone.signup.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @author shital Awathe
 *
 * This view model contains the logic of getting messages from firebase real database
 */
class TeamReplyViewModel : ViewModel() {
    private var auth: FirebaseAuth = Firebase.auth
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private lateinit var databaseReference: DatabaseReference
    var user: User? = null

    private val listOfMessages: MutableLiveData<List<Message>> = MutableLiveData()
    val mListOfMessages: LiveData<List<Message>>
        get() = listOfMessages
    var messages: MutableList<Message> = ArrayList()

    companion object {
        val TAG = TeamReplyViewModel::class.qualifiedName
    }

    fun getMessageDb() = databaseReference

    fun getUserObject() = user

    /**
     * Get messages from real time database
     */
    fun callInOnStart() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.e(TAG, ">>>callInOnStart")
            val currentUser = auth.currentUser
            user?.let { it(currentUser.uid, currentUser.email) }

            database.getReference("Users").child(currentUser.uid)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        user = dataSnapshot.getValue(User::class.java)
                        user?.uid = currentUser.uid
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        Log.e(TAG, "onCancelled ${databaseError.message}")
                    }
                })

            databaseReference = database.getReference("messages")

            databaseReference.addChildEventListener(object : ChildEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Log.e(TAG, "onCancelled addChildEventListener")
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    Log.e(TAG, "onChildMoved")
                }

                /**
                 *
                 */
                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    Log.e(TAG, "onChildChanged")
                    val message = snapshot.getValue(Message::class.java)
                    message?.key = snapshot.key.toString()
                    val newMessages: MutableList<Message> = ArrayList()
                    for (m in messages!!) {
                        if (m.key == message?.key) {
                            message?.let { newMessages.add(it) }
                        } else {
                            newMessages.add(m)
                        }
                    }
                    messages = newMessages
                    listOfMessages.postValue(messages)

                }

                /**
                 * call after adding new message
                 */
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    Log.i(TAG, "onChildAdded")
                    val message: Message? = snapshot.getValue(Message::class.java)
                    message?.key = snapshot.key.toString()
                    message?.let { messages?.add(it) }
                    listOfMessages.postValue(messages)
                }

                /**
                 * call after removing message
                 */
                override fun onChildRemoved(snapshot: DataSnapshot) {
                    Log.i(TAG, "onChildRemoved")
                    val message = snapshot.getValue(Message::class.java)
                    message?.key = snapshot.key.toString()
                    val newMessages: MutableList<Message> =
                        ArrayList()
                    for (m in messages!!) {
                        if (m.key != message?.key) {
                            newMessages.add(m)
                        }
                    }
                    messages = newMessages
                    listOfMessages.postValue(messages)
                }
            })
        }
    }

}

private operator fun User.invoke(uid: String, email: String?): Any {
    TODO("Not yet implemented")
}


