package com.example.requisicaopost

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.requisicaopost.databinding.ActivityMainBinding
import com.example.requisicaopost.model.ApiService
import com.example.requisicaopost.service.Postagem
import com.example.requisicaopost.service.RetrofitHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val retrofit by lazy {
        RetrofitHelper.retrofit
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)


        binding.btnIniciar.setOnClickListener(){
            CoroutineScope(Dispatchers.IO).launch {
                postagem()
            }
        }

    }

    suspend fun postagem(){

        var retorno: Response<Postagem>? = null
        val postagem = Postagem(
            "Corpo da postagem",
            -1,
            "titulo da postagem",
            34
        )



        try {
            val apiService = retrofit.create(ApiService::class.java)
            retorno = apiService.salvarPostagem(postagem)


        }catch (e: Exception){

            e.printStackTrace()
            Log.i("info_jsonplace", "erro ao recuperar mensagem")

        }

        var resultado = ""

        if (retorno != null){


            if (retorno.isSuccessful){
                val postagem = retorno.body()
                val titulo = postagem?.title
                val id = postagem?.id
                val userId = postagem?.userId
                resultado = "I: $id - T: $titulo - U: $userId"

            }


        }
        withContext(Dispatchers.Main){
            binding.textTexto.setText(resultado)
        }


    }
}