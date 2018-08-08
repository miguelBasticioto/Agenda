package br.com.miguel.agenda.agenda.module.auth.network

import br.com.miguel.agenda.agenda.module.auth.model.Usuario
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthAPI {

    @POST(AuthNetwork.auth)
    fun criarUsuario(@Body usuario: Usuario): Observable<ApiResponse>

    @POST(AuthNetwork.signIn)
    fun logarUsuario(@Body usuario: Usuario): Observable<Response<ApiResponse>>

    @DELETE(AuthNetwork.signOut)
    fun logout(@Header(AuthNetwork.uid) uid: String,
               @Header(AuthNetwork.accessToken) accessToken: String,
               @Header(AuthNetwork.client) client: String): Observable<Response<Void>>

}