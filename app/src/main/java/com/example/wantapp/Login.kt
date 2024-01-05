package com.example.wantapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class Login : Fragment() {
    private lateinit var enteremail             : TextInputEditText
    private lateinit var enterpassword          : TextInputEditText
    private lateinit var login                  : AppCompatButton
    private lateinit var googleSignInOptions    : GoogleSignInOptions
    private lateinit var nGoogleSignInClient    : GoogleSignInClient
    private lateinit var resultLauncher         : ActivityResultLauncher<Intent>
    private val RC_SIGN_IN = 1011

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        enteremail  =view.findViewById(R.id.etloginemail)
        enterpassword = view.findViewById(R.id.loginpassword)
        login  = view.findViewById(R.id.login_button)

        login.setOnClickListener {
            val email = enteremail.text.toString()
            val password = enterpassword.toString()
            if (TextUtils.isEmpty(email))
            {
                enteremail.error = "Email is required"
            }
            else if (TextUtils.isEmpty(password))
            {
                enterpassword.error = "password is required to login"
            }
            else
            {
                signIn(email,password)
            }
        }
        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result->
            if (result.resultCode == Activity.RESULT_OK)
            {
                val launchData = result.data
                val task = GoogleSignIn.getSignedInAccountFromIntent(launchData)
                try{
                    val account = task.getResult(ApiException::class.java)
                    Log.d("Gmail ID","FirebaseAuthWith Google : $account")
                    FirebaseAuthWithGoogle(account?.idToken)
                }
                catch (e:ApiException)
                {
                    Log.w("error","google sign in failed",e)
                }
            }
        }
        return view
    }

    private fun FirebaseAuthWithGoogle(idToken: String?) {

    }

    private fun signIn(em:String,pass:String) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(em,pass).addOnCompleteListener { task->
            if (task.isSuccessful)
            {

            }
        }
    }

////    class LoginActivity : AppCompatActivity() {
//        // Initialize Firebase Authentication
//        private val auth: FirebaseAuth = FirebaseAuth.getInstance()
//
//        // Example function to handle email/password login
//        private fun loginUser(email: String, password: String) {
//            auth.signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this) { task ->
//                    if (task.isSuccessful) {
//                        // Login successful
//                        val user = auth.currentUser
//                        Log.d("Login", "User logged in: ${user?.email}")
//                        // Add your logic to navigate to the next screen or perform other actions
//                    } else {
//                        // If sign in fails, display a message to the user.
//                        Log.w("Login", "Login failed", task.exception)
//                        // You can handle errors here, show a toast, etc.
//                    }
//                }
//        }
//    }

}