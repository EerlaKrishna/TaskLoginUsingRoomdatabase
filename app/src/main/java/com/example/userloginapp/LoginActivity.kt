package com.example.userloginapp


import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.userloginapp.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val userViewModel: UserViewModel by viewModels {
        UserViewModelFactory(UserRepository(UserDatabase.getDatabase(this).userDao()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()
            userViewModel.getUserByUsername(username) { user ->
                if (user != null && user.password == password) {
                    // Successful login, pass user ID to MainActivity
                    val intent = Intent(this, MainActivity::class.java).apply {
                        putExtra("USER_ID", user.id)
                    }
                    startActivity(intent)
                    finish()
                } else {
                    // Show error message
                    binding.tvError.text = "Invalid credentials"
                    binding.tvError.visibility = View.VISIBLE
                }
            }
        }

        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
