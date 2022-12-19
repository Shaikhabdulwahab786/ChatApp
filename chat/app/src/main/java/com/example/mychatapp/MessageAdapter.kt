package com.example.mychatapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase



class MessageAdapter(val context: Context,val messageList: ArrayList<Message>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){

   val  ITEM_RECIEVE = 1
    val  ITEM_SENT = 2


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == 1){
            val view: View = LayoutInflater.from(context).inflate(R.layout.recevie,parent,false)
            return ReceiveViewHolder(view)

        }else{
            val view: View = LayoutInflater.from(context).inflate(R.layout.sent,parent,false)
            return SentViewHolder(view)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMeassgae = messageList[position]

        if (holder.javaClass == SentViewHolder:: class.java){
             val viewHolder = holder as SentViewHolder
             holder.sentMessage.text = currentMeassgae.message
         }else{

             val viewHolder = holder as ReceiveViewHolder
                     holder.sentReceive.text = currentMeassgae.message
                }

    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]

        if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId) ){
            return ITEM_SENT
        }else{
            return ITEM_RECIEVE
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }
    class SentViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val sentMessage = itemView.findViewById<TextView>(R.id.sent_message)

    }

    class ReceiveViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val sentReceive = itemView.findViewById<TextView>(R.id.Receive_message)

    }
}