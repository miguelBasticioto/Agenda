package br.com.miguel.agenda.agenda.auth.network

import br.com.miguel.agenda.agenda.auth.module.Usuario
import br.com.miguel.agenda.agenda.contato.module.Contato
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*

interface AuthAPI {

    @POST("/auth")
    fun criarUsuario(@Body usuario: Usuario) : Observable<ApiResponse>

    @POST("/auth/sign_in")
    fun logarUsuario(@Body usuario: Usuario) : Observable<Response<ApiResponse>>


    //ainda nao testado
    @GET("/contacts")
    fun buscarContatos(@Header("uid") uid:String,
                       @Header("access-token") accessToken :String,
                       @Header("client") client:String) : Observable<List<Contato>>

}