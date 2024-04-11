package com.example.fittracker

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fittracker.ui.theme.FitTrackerTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.ResolverStyle
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FitTrackerTheme {
                MyApp();
            }
        }
    }
}

data class NavItemState(
    val title : String,
    val selectedIcon : ImageVector,
    val unselectedIcon : ImageVector
    )

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp(modifier: Modifier = Modifier) {
val items = listOf(
    NavItemState(
        title = "Kalkulator BMI",
        selectedIcon = Icons.Filled.Menu,
        unselectedIcon = Icons.Outlined.Menu
    ),
    NavItemState(
        title = "Trening",
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person
    ),
    NavItemState(
        title = "Pomiary",
        selectedIcon = Icons.Filled.DateRange,
        unselectedIcon = Icons.Outlined.DateRange
    )
)
    var bottomNavState by mutableStateOf(0)
    var currentDayOfWeek by mutableStateOf(getCurrentDayOfWeek())
    var bodyHeight by remember { mutableStateOf("") }
    var bodyWeight by remember { mutableStateOf("") }
    var BMI by remember { mutableStateOf("") }
    var weightChart by remember { mutableStateOf("") }
    Scaffold (
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar (
                containerColor = Color(0xFFC1F0F0)
            ) {
                items.forEachIndexed { index, item ->

                    NavigationBarItem(selected = bottomNavState == index,
                        onClick = { bottomNavState = index },
                        icon = {
                                Icon(
                                    imageVector =
                                    if(bottomNavState == index)
                                        item.selectedIcon
                                    else
                                        item.unselectedIcon
                                    , contentDescription = item.title
                                )
                        },
                        label = {
                            Text(text = item.title)
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFF000000),
                            selectedTextColor = Color(0xFF000000),
                            indicatorColor = Color( 0xFF29A3A3)
                        )
                    )
                }
            }
        }

    ) { contentPadding ->
        Column(
            modifier
                .padding(top = contentPadding.calculateTopPadding())
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (items[bottomNavState].title == "Trening") {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(color = Color(0xFFC1F0F0))
                ) {
                    Text(
                        text = "$currentDayOfWeek",
                        modifier = Modifier
                            .padding(top = 45.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 30.sp,
                        color = LocalContentColor.current
                    )
                }
                Text(
                    text = "Dzień treningu",
                    modifier = Modifier
                        .padding(top = 20.dp, bottom = 15.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    color = LocalContentColor.current
                )
                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color(0xFFC1F0F0),
                    thickness = 2.dp
                )
                Text(
                    text = "Twój zestaw ćwiczeń na \ndzień dzisiejszy:",
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    color = LocalContentColor.current
                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 20.dp)
                        .weight(0.5f),
                    verticalArrangement = Arrangement.spacedBy(25.dp)
                ) {
                    items(listOf("Ćwiczenie 1 \nOpis", "Ćwiczenie 2 \nOpis", "Ćwiczenie 3 \nOpis", "Ćwiczenie 4 \nOpis")) { item ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 32.dp, vertical = 5.dp)
                                .size(width = 300.dp, height = 70.dp)
                                .background(
                                    color = Color(0xFFC1F0F0),
                                    shape = RoundedCornerShape(16.dp)
                                )
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = item,
                                    fontSize = 18.sp,
                                    color = LocalContentColor.current,
                                    textAlign = TextAlign.Start
                                )
                            }
                        }
                    }
                }
            }
            if (items[bottomNavState].title == "Kalkulator BMI") {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(color = Color(0xFFC1F0F0))
                ) {
                    Text(
                        text = "Kalkulator BMI",
                        modifier = Modifier
                            .padding(top = 45.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 30.sp,
                        color = LocalContentColor.current
                    )
                }
                Text(
                    text = "Oblicz swoje zapotrzebowanie \nkaloryczne",
                    modifier = Modifier
                        .padding(top = 20.dp, bottom = 15.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    color = LocalContentColor.current
                )
                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color(0xFFC1F0F0),
                    thickness = 2.dp
                )
                Text(
                    text = "Wprowadź dane:",
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    color = LocalContentColor.current
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                        .height(40.dp)
                        .background(color = Color(0xFFC1F0F0))
                ) {
                    Text(
                        text = "Wzrost w cm",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 5.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp,
                        color = LocalContentColor.current
                    )
                }
                TextField(
                    modifier = Modifier
                        .padding(15.dp)
                        .size(width = 200.dp, height = 50.dp),
                    value = bodyWeight,
                    onValueChange = { newText -> bodyWeight = newText },
                    placeholder = { Text("Wprowadź...") }
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                        .height(40.dp)
                        .background(color = Color(0xFFC1F0F0))
                ) {
                    Text(
                        text = "Masa ciała w kg",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 5.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp,
                        color = LocalContentColor.current
                    )
                }
                TextField(
                    modifier = Modifier
                        .padding(15.dp)
                        .size(width = 200.dp, height = 50.dp),
                    value = bodyHeight,
                    onValueChange = { newText -> bodyHeight = newText },
                    placeholder = { Text("Wprowadź...") }
                )
                Button(
                    onClick = {
                        val height = bodyHeight.toFloatOrNull() ?: 0f
                        val weight = bodyWeight.toFloatOrNull() ?: 0f
                        val bmiValue = weight / height * height
                        BMI = "$bmiValue"
                    },
                    modifier = Modifier.padding(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.Black,
                        containerColor = Color(0xFF29A3A3)
                    )
                ) {
                    Text(text = "Oblicz")
                }
                if (BMI.isNotBlank()) {
                    Text(
                        text = BMI,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
            if (items[bottomNavState].title == "Pomiary") {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(color = Color(0xFFC1F0F0))
                ) {
                    Text(
                        text = "Pomiary",
                        modifier = Modifier
                            .padding(top = 45.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 30.sp,
                        color = LocalContentColor.current
                    )
                }
                Text(
                    text = "Wykres zmiany masy \nciała",
                    modifier = Modifier
                        .padding(top = 20.dp, bottom = 15.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    color = LocalContentColor.current
                )
                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color(0xFFC1F0F0),
                    thickness = 2.dp
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                        .height(40.dp)
                        .background(color = Color(0xFFC1F0F0))
                ) {
                    Text(
                        text = "Wprowadź masę ciała w kg:",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 5.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp,
                        color = LocalContentColor.current
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                TextField(
                    modifier = Modifier
                        .padding(15.dp)
                        .size(width = 200.dp, height = 50.dp),
                    value = bodyWeight,
                    onValueChange = { newText -> bodyWeight = newText },
                    placeholder = { Text("Wprowadź...") }
                )
                Button(
                    onClick = {
                        val newWeightValue = bodyWeight.toFloatOrNull() ?: 0f
                        weightChart = "$newWeightValue"
                    },
                    modifier = Modifier.padding(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.Black,
                        containerColor = Color(0xFF29A3A3)
                    )
                ) {
                    Text(text = "Dodaj")
                }
                if (BMI.isNotBlank()) {
                    Text(
                        text = BMI,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }

}

@Composable
fun getCurrentDayOfWeek(): String {
    val currentDayOfWeek = LocalDate.now().format(DateTimeFormatter.ofPattern("EEEE", Locale("pl")))
    val formattedCurrentDayOfWeek = currentDayOfWeek.replaceFirstChar {
        if (it.isLowerCase())
            it.titlecase(Locale.getDefault())
        else
            it.toString()
    }
    return formattedCurrentDayOfWeek;
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FitTrackerTheme {
        MyApp();
    }
}