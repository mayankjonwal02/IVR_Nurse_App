package com.example.ivr_nurse_app.android

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.net.wifi.WifiManager
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.LifecycleCoroutineScope
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.get
import io.ktor.client.request.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import viewmodel
import java.net.InetAddress
import java.net.UnknownHostException
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


@Composable
fun getIP() {
    var ipaddress by remember {
        mutableStateOf("")
    }
    var context = LocalContext.current
    var viewmodel : viewmodel = viewmodel(context)

//    LaunchedEffect(Unit )
//    {
//        try {
//            ipaddress = viewmodel.connect()
//        }
//        catch (e:Exception)
//        {
//            ipaddress = e.message.toString()
//        }
//    }


    val clipboardManager = LocalContext.current.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager


    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally){
            Text(text = ipaddress, textAlign = TextAlign.Center,modifier = Modifier.selectable(selected = true,enabled = true,null,{clipboardManager.setPrimaryClip(
                ClipData.newPlainText("Copied Text", ipaddress))}))

        Button(onClick = {
           CoroutineScope(Dispatchers.IO).launch{
                try {
                    ipaddress = viewmodel.connect()
                } catch (e: Exception) {
                    ipaddress = e.message.toString()
                }
            }
        }) {
            Text(text = "connect")
        }

            Button(onClick = {
                CoroutineScope(Dispatchers.IO).launch{
                    try {
                        ipaddress = viewmodel.data().map { it.patientid }.toString()
                    } catch (e: Exception) {
                        ipaddress = e.message.toString()
                    }
                }
            }) {
                Text(text = "read")
            }
            var demodata = patientdata("e1","mmt12","micheal","6547389210","knee","hindi","1-2-3")

            Button(onClick = {
                CoroutineScope(Dispatchers.IO).launch{
                    try {
                        var status = viewmodel.send(demodata)
                        ipaddress = status
                    } catch (e: Exception) {
                        ipaddress = e.message.toString()
                    }
                }
            }) {
                Text(text = "send data")
            }

            Button(onClick = {
                CoroutineScope(Dispatchers.IO).launch{
                    try {
                        var status = viewmodel.delete("mmt12")
                        ipaddress = status
                    } catch (e: Exception) {
                        ipaddress = e.message.toString()
                    }
                }
            }) {
                Text(text = "delete data")
            }


        }

        }
    }

