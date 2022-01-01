package com.example.formvalidationwithregex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.appcompat.app.AlertDialog
import androidx.core.text.isDigitsOnly
import com.example.formvalidationwithregex.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //initialize your view binding
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //inflate layout with view binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //these three focus listeners are called when we move away from a particular input field;
        // ie, when we are done typing in that field
        emailFocusListener()
        passwordFocusListener()
        phoneNumberFocusListener()

        binding.btnSubmit.setOnClickListener {
            submitForm()
        }
    }

    private fun submitForm(){

        //before submitting the form you do a final check of the validity of the inputs
        // if they are still true with the validity functions
        binding.emailContainer.helperText = validEmail()
        binding.passwordContainer.helperText = validPassword()
        binding.phoneContainer.helperText = validPhoneNumber()


       val validatedEmail = binding.emailContainer.helperText ==null
        val validatedpassword = binding.passwordContainer.helperText ==null
        val validatedPhoneNumber = binding.phoneContainer.helperText ==null

        if (validatedEmail && validatedpassword && validatedPhoneNumber){

            resetForm()
        }else{

            invalidForm()
        }

    }

    private fun invalidForm() {

        var message = ""
        if (binding.emailContainer.helperText !=null){

            message += "\n\nEmail: " + binding.emailContainer.helperText
        }
        if (binding.passwordContainer.helperText != null){

            message += "\n\nPassword: " + binding.passwordContainer.helperText
        }

        if (binding.phoneContainer.helperText != null){

            message += "\n\nPhone Number: " + binding.phoneContainer.helperText
        }

        AlertDialog.Builder(this)
            .setTitle("Invalid Form")
            .setMessage(message)
            .setPositiveButton("Okay"){_,_,->
                //do nothing
            }.show()

    }



    private fun resetForm() {

        var message = "Email: " + binding.emailEditText.text

            message += "\nPassword: " + binding.passwordEditText.text

            message += "\nPhone Number: " + binding.phoneEditText.text


        AlertDialog.Builder(this)
            .setTitle("Form Submitted Successfully")
            .setMessage(message)
            .setPositiveButton("Okay"){_,_,->
                binding.emailEditText.text = null
                binding.passwordEditText.text = null
                binding.phoneEditText.text = null

                binding.emailContainer.helperText = getString(R.string.required)
                binding.passwordContainer.helperText = getString(R.string.required)
                binding.phoneContainer.helperText = getString(R.string.required)

            }.show()
    }


    private fun  emailFocusListener(){

        binding.emailEditText.setOnFocusChangeListener{_, focused ->

            if (!focused){
                binding.emailContainer.helperText = validEmail()
            }
        }
    }

    private fun validEmail():String?{
        val emailText = binding.emailEditText.text.toString()
        if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()){

          return "Invalid Email Address"
        }

        return null
    }


    private fun  passwordFocusListener(){

        binding.emailEditText.setOnFocusChangeListener{_, focused ->

            if (!focused){
                binding.passwordContainer.helperText = validPassword()
            }
        }
    }

    private fun validPassword():String?{
        val passwordText = binding.passwordEditText.text.toString()
        if (passwordText.length < 8){

            return "Require minimum of 8 characters"
        }

        if (!passwordText.matches(".*[A-Z].*".toRegex())){

            return "Must Contain at least one upper case character"
        }

        if (!passwordText.matches(".*[a-z].*".toRegex())){

            return "Must Contain at least one lower case character"
        }

        if (!passwordText.matches(".*[@#\$%^&+=].*".toRegex())){

            return "Must Contain at least one of @#\$%^&+="
        }

        return null
    }


    private fun  phoneNumberFocusListener(){

        binding.phoneEditText.setOnFocusChangeListener{_, focused ->

            if (!focused){
                binding.phoneContainer.helperText = validPhoneNumber()
            }
        }
    }

    private fun validPhoneNumber():String?{
        val phoneText = binding.phoneEditText.text.toString()

        if(!phoneText.isDigitsOnly()){
            return "Must be all digits"
        }

//        if (!(phoneText.matches(".*[0-9].*".toRegex()))){
//
//            return "Must be all digits"
//        }


        if (phoneText.length != 11){

            return "Phone number must be 10 digits"
        }

        return null
    }

}