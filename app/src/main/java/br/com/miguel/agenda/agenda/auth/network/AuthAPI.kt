package br.com.miguel.agenda.agenda.auth.network

import br.com.miguel.agenda.agenda.auth.model.Usuario
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthAPI {

    @POST("/auth")
    fun criarUsuario(@Body usuario: Usuario) : Observable<ApiResponse>

    @POST("/auth/sign_in")
    fun logarUsuario(@Body usuario: Usuario) : Observable<Response<ApiResponse>>

    @DELETE("auth/sign_out")
    fun logout(@Header("uid") uid: String,
               @Header("access-token") accessToken:String,
               @Header("client") client: String) : Observable<Response<Void>>

}