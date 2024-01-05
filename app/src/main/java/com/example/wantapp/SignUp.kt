package com.example.wantapp

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class SignUp : Fragment() {
    private lateinit var enterName      : TextInputEditText
    private lateinit var entermobileno  : TextInputEditText
    private lateinit var enteremail     : TextInputEditText
    private lateinit var enterpassword  : TextInputEditText
    private lateinit var submit         : AppCompatButton
    private lateinit var submit2        : AppCompatImageButton
    private lateinit var fauth          : FirebaseAuth
    private lateinit var fstore         : FirebaseFirestore
    private lateinit var db             : DocumentReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)
        enterName      = view.findViewById(R.id.signup_name)
        entermobileno  = view.findViewById(R.id.signup_mobileno)
        enteremail     = view.findViewById(R.id.signup_email)
        enterpassword  = view.findViewById(R.id.signup_password)
        submit    = view.findViewById(R.id.submit)
        fauth     = FirebaseAuth.getInstance()
        fstore    = FirebaseFirestore.getInstance()

        submit.setOnClickListener {
            val name = enterName.text.toString()
            val email  = enteremail.text.toString()
            val password = enterpassword.text.toString()
            if (TextUtils.isEmpty(name))
            {
                enterName.error = "plz enter your name"
            }
            else if (TextUtils.isEmpty(email))
            {
                enteremail.error = "plz enter your email"
//                Toast.makeText(this, "plz enter your name", Toast.LENGTH_SHORT).show()
            }
            else if (TextUtils.isEmpty(password))
            {
                enterpassword.error = "enter your password to create account"
            }
            else
                if (password.length<6)
                {
                    enterpassword.error = "password must be greater than 6 letters"
                }
//            else
//                if (password!=password)
//                {
//                    enterpassword.error = "password is not correct"
//                }
            else
                {
                    createaccount(email,password)
                }
        }
        return view
    }
    private fun createaccount(em : String, pass : String){
        fauth.createUserWithEmailAndPassword(em,pass).addOnCompleteListener { task->
            if(task.isSuccessful)
            {
                val userInfo = fauth.currentUser?.uid
                db = fstore.collection("users").document(userInfo.toString())
                val obj = mutableMapOf<String,String>()
                obj["useremail"] = em
                obj["userpassword"] = pass
                db.set(obj).addOnSuccessListener {
                    Log.d("onsuccess","user created succesfully")
                }
            }
        }
    }
}