package com.fph.lotteryanalyze.api

import android.net.SSLCertificateSocketFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit.SECONDS
import javax.net.ssl.*

/**
 * Created by fengpeihao on 2018/4/19.
 */
object ApiModel {

    private val Base_Host = "http://www.google.com"

    private var mSslContext: SSLContext? = null
    private fun creatRetrofit(): Retrofit {
        loseAgreement();
        val loggingInterceptor = HttpLoggingInterceptor()
//        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
//                .addInterceptor(RequestInterceptor())
                .retryOnConnectionFailure(true)
                .sslSocketFactory(mSslContext!!.getSocketFactory())
                .hostnameVerifier(object : HostnameVerifier {
                    override fun verify(p0: String?, p1: SSLSession?): Boolean {
                        return true
                    }
                })
                .connectTimeout(10L, SECONDS)
                .build()
        return Retrofit.Builder()
                .baseUrl(Base_Host)
                .client(client)
                .addConverterFactory(ToStringConverterFactory())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    //忽略所有https证书
    private fun loseAgreement() {
        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
            @Throws(CertificateException::class)
            override fun checkClientTrusted(
                    chain: Array<java.security.cert.X509Certificate>,
                    authType: String) {
            }

            @Throws(CertificateException::class)
            override fun checkServerTrusted(
                    chain: Array<java.security.cert.X509Certificate>,
                    authType: String) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate?> {
                return arrayOfNulls<X509Certificate>(0)
            }
        })
        try {
            mSslContext = SSLContext.getInstance("SSL")
            mSslContext!!.init(null, trustAllCerts,
                    java.security.SecureRandom())
        } catch (e: Exception) {
            //            LogUtil.e("ssl出现异常");
        }
    }

    fun getApiService():ApiService{
        return creatRetrofit().create(ApiService::class.java)
    }
}