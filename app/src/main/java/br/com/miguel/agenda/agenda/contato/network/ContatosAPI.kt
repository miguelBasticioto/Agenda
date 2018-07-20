package br.com.miguel.agenda.agenda.contato.network

import br.com.miguel.agenda.agenda.contato.module.Contato
import io.reactivex.Observable
import retrofit2.http.*

interface ContatosAPI {
    //ainda nao testado
    @GET("/contacts")
    fun buscarContatos(@Header("uid") uid:String,
                       @Header("access-token") accessToken :String,
                       @Header("client") client:String) : Observable<List<Contato>>

    @POST("/contacts")
    fun criarContato(@Header("uid") uid:String,
                     @Header("access-token") accessToken :String,
                     @Header("client") client:String,
                     @Header("Content-Type") type: String,
                     @Body contato: Contato): Observable<Contato>

    @PUT("/contacts/{id}")
    fun editarContato(@Header("uid") uid:String,
                      @Header("access-token") accessToken :String,
                      @Header("client") client:String,
                      @Header("Content-Type") type: String, @Body contato:Contato, @Path("id") id: String): Observable<Contato>
}