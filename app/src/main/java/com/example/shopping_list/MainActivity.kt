package com.example.shopping_list

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.random.Random
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.example.shopping_list.ui.theme.Shopping_listTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Shopping_listTheme {
                ShoppingList()
            }
        }
    }
}

data class Item(
    val itemName: String,
    var itemQuantity: Number)

@SuppressLint("UnrememberedMutableState", "MutableCollectionMutableState") // 什么意思？
@Composable
fun ShoppingList() {
    var listItem: MutableList<Item> by mutableStateOf(mutableListOf())
    var input by remember { mutableStateOf("") }
    Column(modifier = Modifier.padding(20.dp)){
        Row{
            TextField(
                value = input,
                onValueChange = { input = it },
                label = { Text("Enter Expression") }
            )
        }
    }
    Text(
        text = "Hello $!",
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Shopping_listTheme {
        ShoppingList()
    }
}