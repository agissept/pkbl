package id.agis.pkbl.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import id.agis.pkbl.data.remote.ApiResponse
import id.agis.pkbl.data.remote.StatusResponse
import id.agis.pkbl.util.LiveRealmData
import id.agis.pkbl.vo.Resource
import io.realm.RealmModel
import io.realm.RealmResults
import org.jetbrains.anko.doAsync


abstract class NetworkBoundResource<ResultType : RealmModel, RequestType> {

    private val result = MediatorLiveData<Resource<RealmResults<ResultType>>>()

    private fun onFetchFailed() {}

    protected abstract fun loadFromDB(): LiveRealmData<ResultType>

    protected abstract fun shouldFetch(data: RealmResults<ResultType>?): Boolean

    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>?

    protected abstract fun saveCallResult(data: RequestType)

    init {
        result.value = Resource.loading(null)

        val dbSource = this.loadFromDB()
        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData ->
                    result.setValue(Resource.success(newData))
                }
            }
        }
    }

    private fun fetchFromNetwork(dbSource: LiveRealmData<ResultType>) {
        val apiResponse = createCall()

        result.addSource(apiResponse!!) { response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)

            when (response.status) {
                StatusResponse.SUCCESS -> {
                    response.body?.let { saveCallResult(it) }

                    result.addSource(loadFromDB()) { newData ->
                        result.value = Resource.success(newData)
                    }
                }
                StatusResponse.EMPTY -> {
                    doAsync {
                        result.addSource(loadFromDB()) { newData ->
                            result.value = Resource.success(newData)
                        }
                    }
                }
                StatusResponse.ERROR -> {
                    onFetchFailed()
                    result.addSource(dbSource) { newData ->
                        result.value = response.message?.let { Resource.error(it, newData) }
                    }
                }
            }
        }
    }

    fun asLiveData(): LiveData<Resource<RealmResults<ResultType>>> {
        return result
    }
}