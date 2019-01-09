package webconnect.com.webconnect

/**
 * Created by amit on 10/8/17.
 */

import android.util.Log
import okhttp3.Call
import okhttp3.Response
import org.apache.commons.io.IOUtils
import webconnect.com.webconnect.listener.AnalyticsListener
import webconnect.com.webconnect.observer.ErrorLiveData
import webconnect.com.webconnect.observer.FailureLiveData
import webconnect.com.webconnect.observer.SuccessLiveData
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.security.cert.CertificateException
import java.util.concurrent.TimeoutException


/**
 * The type Call back.
 *
 * @param <T> the type parameter
</T> */
class Callback<T> {

    // Enqueue
    internal class GetRequestCallbackEnqueue(private val param: WebParam) : okhttp3.Callback {
        var startTime = 0L

        init {
            startTime = System.currentTimeMillis()
            try {
                param.dialog?.let {
                    if (!param.dialog?.isShowing!!) {
                        param.dialog?.show()
                    }
                }
            } catch (e: Exception) {
                e.stackTrace
            }
        }

        override fun onFailure(call: Call, e: IOException) {
            try {
                param.dialog?.let {
                    if (param.dialog?.isShowing!!) {
                        param.dialog?.dismiss()
                    }
                }
            } catch (e: Exception) {
                e.stackTrace
            }
            param.callback?.onError(e, getError(param, e), param.taskId)
            param.failure?.onFailure(e, getError(param, e))
            FailureLiveData.getInstance().postValue(getError(param, e))
        }

        override fun onResponse(call: Call, response: Response) {
            val timeTaken = System.currentTimeMillis() - startTime
            try {
                param.dialog?.let {
                    if (param.dialog?.isShowing!!) {
                        param.dialog?.dismiss()
                    }
                }
            } catch (e: Exception) {
                e.stackTrace
            }
            if (response.body() != null) {
                val responseString = response.body()?.string()!!
                if (response.isSuccessful) {
                    val obj = ApiConfiguration.getGson().fromJson(responseString, param.model)
                    param.analyticsListener?.onReceived(timeTaken, if (call.request().body() == null) -1 else call.request().body()?.contentLength()!!, response.body()?.contentLength()!!, response.cacheResponse() != null)
                    param.callback?.onSuccess(obj, param.taskId)
                    param.success?.onSuccess(obj)
                    SuccessLiveData.getInstance().postValue(responseString)
                } else {
                    val obj = ApiConfiguration.getGson().fromJson(responseString, param.error)
                    param.analyticsListener?.onReceived(timeTaken, if (call.request().body() == null) -1 else call.request().body()?.contentLength()!!, response.body()?.contentLength()!!, response.cacheResponse() != null)
                    param.callback?.onError(obj, "", param.taskId)
                    param.err?.onError(obj)
                    ErrorLiveData.getInstance().postValue(responseString)
                }
            }
        }

    }

    // Enqueue
    internal class PostRequestCallbackEnqueue(private val param: WebParam) : okhttp3.Callback {
        var startTime = 0L

        init {
            startTime = System.currentTimeMillis()
            try {
                param.dialog?.let {
                    if (!param.dialog?.isShowing!!) {
                        param.dialog?.show()
                    }
                }
            } catch (e: Exception) {
                e.stackTrace
            }
        }

        override fun onFailure(call: Call, e: IOException) {
            try {
                param.dialog?.let {
                    if (param.dialog?.isShowing!!) {
                        param.dialog?.dismiss()
                    }
                }
            } catch (e: Exception) {
                e.stackTrace
            }
            param.callback?.onError(e, getError(param, e), param.taskId)
            param.failure?.onFailure(e, getError(param, e))
            FailureLiveData.getInstance().postValue(getError(param, e))
        }

        override fun onResponse(call: Call, response: Response) {
            val timeTaken = System.currentTimeMillis() - startTime
            try {
                param.dialog?.let {
                    if (param.dialog?.isShowing!!) {
                        param.dialog?.dismiss()
                    }
                }
            } catch (e: Exception) {
                e.stackTrace
            }
            if (response.body() != null) {
                val responseString = response.body()?.string()!!
                if (response.isSuccessful) {
                    val obj = ApiConfiguration.getGson().fromJson(responseString, param.model)
                    param.analyticsListener?.onReceived(timeTaken, if (call.request().body() == null) -1 else call.request().body()?.contentLength()!!, response.body()?.contentLength()!!, response.cacheResponse() != null)
                    param.callback?.onSuccess(obj, param.taskId)
                    param.success?.onSuccess(obj)
                    SuccessLiveData.getInstance().postValue(responseString)
                } else {
                    val obj = ApiConfiguration.getGson().fromJson(responseString, param.error)
                    param.analyticsListener?.onReceived(timeTaken, if (call.request().body() == null) -1 else call.request().body()?.contentLength()!!, response.body()?.contentLength()!!, response.cacheResponse() != null)
                    param.callback?.onError(obj, "", param.taskId)
                    param.err?.onError(obj)
                    ErrorLiveData.getInstance().postValue(responseString)
                }
            }
        }

    }


    // Enqueue
    internal class DownloadRequestCallbackEnqueue(private val param: WebParam) : okhttp3.Callback {
        var startTime = 0L

        init {
            startTime = System.currentTimeMillis()
            try {
                param.dialog?.let {
                    if (!param.dialog?.isShowing!!) {
                        param.dialog?.show()
                    }
                }
            } catch (e: Exception) {
                e.stackTrace
            }
        }

        override fun onFailure(call: Call, e: IOException) {
            try {
                param.dialog?.let {
                    if (param.dialog?.isShowing!!) {
                        param.dialog?.dismiss()
                    }
                }
            } catch (e: Exception) {
                e.stackTrace
            }
            param.callback?.onError(e, getError(param, e), param.taskId)
            param.failure?.onFailure(e, getError(param, e))
            FailureLiveData.getInstance().postValue(getError(param, e))
        }

        override fun onResponse(call: Call, response: Response) {
            val timeTaken = System.currentTimeMillis() - startTime
            try {
                param.dialog?.let {
                    if (param.dialog?.isShowing!!) {
                        param.dialog?.dismiss()
                    }
                }
            } catch (e: Exception) {
                e.stackTrace
            }
            var `object`: Any? = null
            if (response.body() != null) {
                if (response.isSuccessful) {
                    val body = response.body()
                    var out: OutputStream? = null
                    try {
                        out = FileOutputStream(param.file!!)

                        IOUtils.copy(body!!.byteStream(), out)
                        `object` = param.file
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    param.analyticsListener?.onReceived(timeTaken, if (call.request().body() == null) -1 else call.request().body()?.contentLength()!!, response.body()?.contentLength()!!, response.cacheResponse() != null)
                    param.callback?.onSuccess(this.param.file, this.param.taskId)
                    param.success?.onSuccess(this.param.file!!)
                } else {
                    param.analyticsListener?.onReceived(timeTaken, if (call.request().body() == null) -1 else call.request().body()?.contentLength()!!, response.body()?.contentLength()!!, response.cacheResponse() != null)
                    if (response.body() != null) {
                        val error = response.body()!!.string()
                        param.callback?.onError(error, "", param.taskId)
                        param.err?.onError(error)
                        ErrorLiveData.getInstance().postValue(error)
                    } else {
                        param.callback?.onError(Throwable(""), "", param.taskId)
                        param.err?.onError("")
                        ErrorLiveData.getInstance().postValue("")
                    }
                }
            }
        }

    }

    internal class Analytics : AnalyticsListener {
        var TAG = "Analytics"

        override fun onReceived(timeTakenInMillis: Long, bytesSent: Long, bytesReceived: Long, isFromCache: Boolean) {
            if (ApiConfiguration.isDebug()) {
                Log.d(TAG, " timeTakenInMillis : " + timeTakenInMillis)
                Log.d(TAG, " bytesSent : " + bytesSent)
                Log.d(TAG, " bytesReceived : " + bytesReceived)
                Log.d(TAG, " isFromCache : " + isFromCache)
            }
        }
    }


    companion object {

        private fun getError(param: WebParam, t: Throwable): String {
            var errors = ""
            if (param.context == null) return errors

            if (t.javaClass.name.contains(UnknownHostException::class.java.name)) {
                errors = param.context?.getString(R.string.error_internet_connection).toString()
            } else if (t.javaClass.name.contains(TimeoutException::class.java.name)
                    || t.javaClass.name.contains(SocketTimeoutException::class.java.name)
                    || t.javaClass.name.contains(ConnectException::class.java.name)) {
                errors = param.context?.getString(R.string.error_server_connection).toString()
            } else if (t.javaClass.name.contains(CertificateException::class.java.name)) {
                errors = param.context?.getString(R.string.error_certificate_exception).toString()
            } else {
                errors = t.toString()
            }
            return errors
        }
    }

}
