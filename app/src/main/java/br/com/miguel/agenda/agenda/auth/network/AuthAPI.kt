package br.com.miguel.agenda.agenda.auth.network

import br.com.miguel.agenda.agenda.auth.module.Usuario
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthAPI {

    @POST("/auth")
    fun criarUsuario(@Body usuario: Usuario) : Observable<ApiResponse>

    @POST("/auth/sign_in")
    fun logarUsuario(@Body usuario: Usuario) : Observable<Response<ApiResponse>>

}