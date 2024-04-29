package com.marinaruiz.facturas_fct.ui.auth

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.CheckBox
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.marinaruiz.facturas_fct.R
import com.marinaruiz.facturas_fct.core.ErrorResponse
import com.marinaruiz.facturas_fct.databinding.ActivitySignupBinding
import com.marinaruiz.facturas_fct.ui.MainActivity

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private val authVM: AuthViewModel by viewModels()
    private var padding: Int = 0

    companion object {
        private const val TAG = "VIEWNEXT SignupActivity"

        fun create(context: Context): Intent = Intent(context, SignupActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignupBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setWindowInsets()
        initUI()
    }

    private fun setWindowInsets() {
        padding = resources.getDimension(com.marinaruiz.facturas_fct.R.dimen.dimen_size_16).toInt()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left + padding,
                systemBars.top + padding,
                systemBars.right + padding,
                systemBars.bottom + padding
            )
            insets
        }
    }

    private fun initUI() {
        initListeners()
        initObservables()
    }

    private fun initListeners() {
        with(binding) {
            btnSignupRegister.setOnClickListener {
                val email = etSignupEmail.text
                val password = etSignupPassword.text
                authVM.signUp("marina@gmail.com", "Aa123456") // TODO change this
            }
            btnSignupBackLogin.setOnClickListener { navigateLogin() }
            btnSignupEye.setOnCheckedChangeListener { buttonView, isChecked ->
                authVM.showPassword(
                    buttonView as CheckBox, isChecked, etSignupPassword, etSignupPasswordRepeat
                )
            }
        }
    }

    private fun initObservables() {
        authVM.allowAccess.observe(this) { allow ->
            if (allow) {
                navigateMain()
            }
        }
        authVM.showErrorDialog.observe(this) { showError ->
            showError?.let {
                showErrorDialog(showError)
            }
        }
    }

    private fun showErrorDialog(error: ErrorResponse) {
        MaterialAlertDialogBuilder(this).setTitle("Error: " + error.code).setMessage(error.message)
            .setNeutralButton("Aceptar") { dialog, which ->
                if (which == DialogInterface.BUTTON_NEUTRAL) dialog.dismiss()
            }.show()
    }

    private fun navigateMain() {
        startActivity(MainActivity.create(this))
        this.finish()
    }

    private fun navigateLogin() {
        startActivity(LoginActivity.create(this))
        this.finish()
    }

}
