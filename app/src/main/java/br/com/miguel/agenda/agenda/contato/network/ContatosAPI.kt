package br.com.miguel.agenda.agenda.contato.network

import br.com.miguel.agenda.agenda.contato.model.Contato
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*

interface ContatosAPI {

    @GET(ContatosNetwork.contacts)
    fun buscarContatos(@Header(ContatosNetwork.uid) uid: String,
                       @Header(ContatosNetwork.accessToken) accessToken: String,
                       @Header(ContatosNetwork.client) client: String): Observable<List<Contato>>

    @POST(ContatosNetwork.contacts)
    fun criarContato(@Header(ContatosNetwork.uid) uid: String,
                     @Header(ContatosNetwork.accessToken) accessToken: String,
                     @Header(ContatosNetwork.client) client: String,
                     @Header(ContatosNetwork.contentType) type: String,
                     @Body contato: Contato): Observable<Contato>

    @PUT(ContatosNetwork.contactsId)
    fun editarContato(@Header(ContatosNetwork.uid) uid: String,
                      @Header(ContatosNetwork.accessToken) accessToken: String,
                      @Header(ContatosNetwork.client) client: String,
                      @Header(ContatosNetwork.contentType) type: String,
                      @Body contato: Contato,
                      @Path(ContatosNetwork.id) id: String): Observable<Contato>

    @DELETE(ContatosNetwork.contactsId)
    fun deletarContato(@Header(ContatosNetwork.uid) uid: String,
                       @Header(ContatosNetwork.accessToken) accessToken: String,
                       @Header(ContatosNetwork.client) client: String,
                       @Header(ContatosNetwork.contentType) type: String,
                       @Path(ContatosNetwork.id) id: String): Observable<Response<Void>>

}