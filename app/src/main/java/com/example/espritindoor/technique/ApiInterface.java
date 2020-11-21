package com.example.espritindoor.technique;


import com.example.espritindoor.Model.Comment;
import com.example.espritindoor.Model.Event;
import com.example.espritindoor.Model.Feed;
import com.example.espritindoor.Model.Friend;
import com.example.espritindoor.Model.Salle;
import com.example.espritindoor.Model.SdkSetupId;
import com.example.espritindoor.Model.user;

import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiInterface {

// walid

    @GET("login/{email}/{password}")
    public Call<user> GetUser(@Path("email") String userEmail, @Path("password") String userPassword);


    @POST("contacts")
    public  Call<user> SetUser(@Body HashMap<Object, Object> map);

    @GET("contacts")
    public  Call<List<Friend>> GetFriend ();

    @GET("friend/{id}")
    public Call<List<Friend>> GetMyFriend (@Path("id") String id);

    @FormUrlEncoded
    @POST("amis/envoyer/{id_amis}")
    public  Call<user> SetDemade(@Field("id_user")String id_user, @Path("id_amis") String id_amis);


    @GET("amis/getdemande/{id}")
    public Call<user> GetLesDemande (@Path("id") String id );

    @FormUrlEncoded
    @PUT("amis/accept/{id_user}")
    public Call<user> Demandeaccepter (@Path("id_user") String id_user , @Field("id_demande")String id_demande);

    @FormUrlEncoded
    @PUT("amis/remove/{id_user}")
    public Call<user> Refuseraccepter(@Path("id_user") String id_user , @Field("id_demande")String id_demande);


    @FormUrlEncoded
    @PUT("friends/{id}")
    public Call<user> DeleteFriend (@Path("id") String id , @Field("_id")String id3);

    @GET("saisson/{email}")
    public Call<user> saisson (@Path("email") String email);


    @POST("profiluser/{id_user}")
    public  Call<user> profiluser(@Path("id_user") String id_user ,@Body HashMap<Object, Object> map);




    //////////////////////////////////////////////
    @GET("2020/06/10?key=e24658c6-953a-4cea-9630-3174f840b6d2")
    public Call<List<SdkSetupId>> getSdkSetupId();

    /*@GET("{SDK_SETUP_ID}/events?key=e24658c6-953a-4cea-9630-3174f840b6d2")
    public Call<List<CoordinateInfos>> getCoordinate(@Path("SDK_SETUP_ID")String id);*/

    @GET("{SDK_SETUP_ID}/events?key=e24658c6-953a-4cea-9630-3174f840b6d2")
    public Call<List<Feed>> getCoordinate(@Path("SDK_SETUP_ID")String id);



    @POST("userId/{sender}/{username}/{contenu}")
    public  Call<Comment> addComment(@Path("sender")String id ,@Path("username")String username ,@Path("contenu") String contenu);

    @POST("addComment/{salleName}/commentId/{cid}")
    public  Call<Comment> addCommentToSalle(@Path("salleName")String id , @Path("cid") String contenu);

    @GET("{salleName}")
    public  Call<Salle> getComments(@Path("salleName")String id);

    @Multipart

    @POST("/events/testupload/")
    Call<Event> uploadImage(@Part MultipartBody.Part photo,@Part("photo") RequestBody name);




}
