package com.example.mychatapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatActivity : AppCompatActivity() {

    private  lateinit var chatRecyclerView: RecyclerView
    private lateinit var messagebox : EditText
    private lateinit var  sentButton: ImageView
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList: ArrayList<Message>
    private lateinit var mDbRef : DatabaseReference


    var ReceiverRoom : String?  = null
    var SenderRoom: String? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)


        val name = intent.getStringExtra("name")
        val ReceiverUid = intent.getStringExtra("uid")
        val senderUid = FirebaseAuth.getInstance().currentUser?.uid

        mDbRef = FirebaseDatabase.getInstance().getReference()


        SenderRoom = ReceiverUid + senderUid
        ReceiverRoom = senderUid + ReceiverUid
        supportActionBar?.title = name

        chatRecyclerView = findViewById(R.id.chatRecyclerView)
        messagebox = findViewById(R.id.MessageBox)
        sentButton = findViewById(R.id.sendbtn)
        messageList = ArrayList()
        messageAdapter = MessageAdapter(this ,messageList)
        chatRecyclerView.layoutManager = LinearLayoutManager(this)
        chatRecyclerView.adapter = messageAdapter

        // logic for  addingData to RecyclreView
        mDbRef.child("chat").child(SenderRoom!!).child("messages")
            .addValueEventListener(object : ValueEventListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList.clear()

                    for (postSnapshot in snapshot.children) {
                        val message = postSnapshot.getValue(Message::class.java)
                            messageList.add(message!!)
                    }
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {

                }


            })

        sentButton.setOnClickListener{

            val message =  messagebox.text.toString()
            val messageObject = Message(message , senderUid)
            mDbRef.child("chat").child(SenderRoom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    mDbRef.child("chat").child(ReceiverRoom!!).child("messages").push()
                        .setValue(messageObject)
                }
                   messagebox.setText("")
        }

    }
}