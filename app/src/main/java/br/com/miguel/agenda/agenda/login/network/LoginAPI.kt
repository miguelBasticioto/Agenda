package br.com.miguel.agenda.agenda.login.network

import br.com.miguel.agenda.agenda.login.module.Usuario
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginAPI {
    @POST("/auth")
    fun criarUsuario(@Body usuario: Usuario) : Observable<Usuario>

    @POST("/auth/sign_in")
    fun logarUsuario(@Body usuario: Usuario) : Observable<Usuario>
}