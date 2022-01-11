package com.noice.rickmortyfacts.repostiory

import android.util.Log
import com.bumptech.glide.load.HttpException
import com.noice.rickmortyfacts.model.CharacterModel
import com.noice.rickmortyfacts.network.CustomRetrofit
import com.noice.rickmortyfacts.utils.NetworkCallStatus
import com.noice.rickmortyfacts.utils.Status
import retrofit2.Response
import java.io.IOException


const val tag = "Repo"
class CharacterDetailRepository {

    private val rmService by lazy{ CustomRetrofit.Instance }

    private suspend inline fun <T> safeApiCall(apiCall:()->Response<T>):NetworkCallStatus<T>{
        var nts = NetworkCallStatus<T>()
        try{
            val response = apiCall.invoke()
            if(response.isSuccessful && response.body() != null){
                with (nts){
                    data = response.body()!!
                    status = Status.SUCCESS
                    msg = "Response Successful"
                }
                Log.i(tag, "Response Successful")
            }else{
                nts = NetworkCallStatus.error(msg = "Response not successful")
                Log.i(tag, "Response not successful")
            }
        }catch (e: IOException) {
            nts = NetworkCallStatus.error(msg = "IO Exception, You might not have Internet Connection")
            Log.i(tag, "IO Exception, You might not have Internet Connection")

        } catch (e: HttpException) {
            nts = NetworkCallStatus.error(msg = "Http Exception. Unexpected Response from server")
            Log.i(tag, "Http Exception. Unexpected Response from server")

        }catch (e: Exception){
            nts = NetworkCallStatus.error(msg ="UnKnown Error")
            Log.i(tag, "UnKnown Error")

        }
        return nts
    }

    suspend fun getCharacterbyId(id: Int): NetworkCallStatus<CharacterModel> {
        return safeApiCall { rmService.getCharacterbyId(id) }
    }
}