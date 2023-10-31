package com.example.ivr_nurse_app.android

import android.annotation.SuppressLint
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import viewmodel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@SuppressLint("NewApi")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun patientdatascreen(viewmodel: viewmodel,  myfunction: () -> Unit = {}) {

     var context = LocalContext.current

    var eid by remember {
        mutableStateOf(generateUniqueID(context))
    }
    var id by remember {
        mutableStateOf("")
    }
    var name by remember {
        mutableStateOf("")
    }
    var cno by remember {
        mutableStateOf("")
    }
    var optype by remember {
        mutableStateOf("")
    }
    var lang by remember {
        mutableStateOf("")
    }
    var duedays by remember {
        mutableStateOf("")
    }

    fun senddata()
    {
        if(eid.isNotBlank() && id.isNotBlank() && name.isNotBlank() && cno.isNotBlank() && optype.isNotBlank() && lang.isNotBlank() && duedays.isNotBlank())
        {
            CoroutineScope(Dispatchers.IO).launch{

                var duedate = getdate(duedays)
                try{
                   var status =  viewmodel.send(
                        patientdata(
                            eid.toLowerCase(),
                            id.toLowerCase(),
                            name,
                            cno.trim(),
                            optype.toLowerCase().trim(),
                            lang.toLowerCase().trim(),
                            duedate,
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            ""
                        )
                    )
                   GlobalScope.launch(Dispatchers.Main){ Toast.makeText(context, status.toString(), Toast.LENGTH_LONG).show() }
                    eid = ""
                    id = ""
                    name = ""
                    cno = ""
                    optype = ""
                    lang = ""
                    duedays = ""
                    eid = generateUniqueID(context)
                }
                catch (e:Exception)
                {
                    Toast.makeText(context,e.message.toString(),Toast.LENGTH_LONG).show()
                }
            }
        }
        else
        {
            Toast.makeText(context,"Fields Empty",Toast.LENGTH_LONG).show()
        }
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White),
    contentAlignment = Alignment.Center)
    {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.verticalScroll(
            rememberScrollState())) {
            Text(text = "Patient Data", fontFamily = FontFamily.Default, fontStyle = FontStyle.Normal, fontSize = 30.sp, fontWeight = FontWeight.ExtraBold, color =  Color.Black, modifier = Modifier.padding(top = 20.dp))
//            Spacer(modifier = Modifier.height(25.dp))
//            OutlinedTextField(
//                value = eid,
//                onValueChange = { eid = it },
//                colors = TextFieldDefaults.outlinedTextFieldColors(
//                    focusedBorderColor = Color.Green,
//                    textColor = Color.Black
//                ),
//                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
//                label = { Text(text = "ENGAGEMENT ID")},
//                placeholder = { Text(text = "Enter EID")}
//            )
            Spacer(modifier = Modifier.height(25.dp))
            OutlinedTextField(
                value = id,
                onValueChange = { id = it },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Green,
                    textColor = Color.Black
                ),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
                label = { Text(text = "Patient ID")},
                placeholder = { Text(text = "Enter Patient ID")}
            )
            Spacer(modifier = Modifier.height(15.dp))
            OutlinedTextField(
                value = name,
                onValueChange = {name = it  },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Green,
                    textColor = Color.Black
                ),
                label = { Text(text = "Patient Name")},
                placeholder = { Text(text = "Enter Patient Name")}
            )
            Spacer(modifier = Modifier.height(15.dp))
            OutlinedTextField(
                value = cno ,
                onValueChange = {cno = it  },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Green,
                    textColor = Color.Black
                )
                ,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
                label = { Text(text = "Contact No.")},
                placeholder = { Text(text = "Enter Contact No.")}
            )

            Spacer(modifier = Modifier.height(15.dp))
            OutlinedTextField(
                value = optype,
                onValueChange = {optype = it  },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Green,
                    textColor = Color.Black
                ),
                label = { Text(text = "Operation Type")},
                placeholder = { Text(text = "Enter Operation Type")}
            )
            Spacer(modifier = Modifier.height(15.dp))
            OutlinedTextField(
                value = lang,
                onValueChange = {lang = it  },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Green,
                    textColor = Color.Black
                ),
                label = { Text(text = "Patient's Language")},
                placeholder = { Text(text = "Enter Language")}
            )
            Spacer(modifier = Modifier.height(15.dp))
            OutlinedTextField(
                value = duedays,
                onValueChange = { duedays = it },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Green,
                    textColor = Color.Black
                ),keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                label = { Text(text = "Days due -> Implant Removal")},
                placeholder = { Text(text = "Enter No. of Days")}
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = { senddata() }, colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)) {
                Text(text ="Add", color = Color.Black)
            }

        }

    }
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Bottom) {
        Button(onClick = { myfunction() }, colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)) {
            Text(text = "Remove Patients", color = Color.Black)
        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
fun getdate(days: String):String
{
    var date = LocalDate.now().plusDays(days.toLong())
    var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    return date.format(formatter)
}