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
import androidx.annotation.Nullable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.random.Random
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFontLoader
import androidx.compose.ui.text.Paragraph
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.ui.semantics.Role.Companion.Checkbox
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
    var itemQuantity: String)

@Composable
fun ShoppingItem(shoppingItem: Item){
    var checked by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp),
    ) {
        Row{
            Checkbox(
                checked = checked,
                onCheckedChange = {
                    checked = it
                    if (checked){
                        shoppingItem.itemQuantity = (shoppingItem.itemQuantity.toInt() - 1).toString()
                    }
                                  } ,
                modifier = Modifier.padding(20.dp).align(Alignment.CenterVertically)
            )
            Text(text = "Name: " + shoppingItem.itemName,
                modifier = Modifier.padding(20.dp).align(Alignment.CenterVertically))
            Text(text = "Qty: " + shoppingItem.itemQuantity,
                modifier = Modifier.padding(20.dp).align(Alignment.CenterVertically))
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnrememberedMutableState", "MutableCollectionMutableState") // 什么意思？
@Composable
fun ShoppingList() {
    val options = listOf("1","2","3","4","5")
    var listItem: List<Item> by remember{mutableStateOf(emptyList()) }
    var input by remember { mutableStateOf("") }
    var itemQuantity by remember { mutableStateOf("1") }
    var expanded by remember { mutableStateOf(false) }
    var warning by remember {mutableStateOf("")}


//    Text("Hello")
    Column(modifier = Modifier.padding(20.dp)){
        Row(modifier = Modifier.fillMaxWidth()){

            TextField(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                value = input,
                onValueChange = { input = it },
                label = { Text("Name") },

            )
//            TextField(
//                modifier = Modifier
//                    .weight(1f)
//                    .padding(8.dp)
//                    .align(Alignment.CenterVertically),
//                value = itemQuantity,
//                onValueChange = { itemQuantity = it },
//                label = { Text("Quantity") }
//            )
            ExposedDropdownMenuBox(
                expanded = expanded,
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
                    .align(Alignment.CenterVertically),
                onExpandedChange = {
                    expanded = !expanded
                }
            ) {
                // Text Field to display the selected option
                TextField(
                    value = itemQuantity,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Qty") },
                    modifier = Modifier.menuAnchor(),
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors()
                )

                // Dropdown Menu
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    }
                ) {
                    // Populate dropdown with options
                    options.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                itemQuantity = option
                                expanded = false
                            }
                        )
                    }
                }
            }

            Button(onClick = {
                if (input == ""){
                    // cannot add to the list
                    warning = "Must Enter A Name"
                    Log.d("mutableShoppingListItems", "don't do anything")
                } else{
                    var found = false
                    for (item in listItem){
                        if (item.itemName == input){
                            item.itemQuantity = (item.itemQuantity.toInt() + itemQuantity.toInt()).toString()
                            found = true
                            break
                        }
                    }
                    val newItem = Item(itemName = input, itemQuantity = itemQuantity)
                    if (! found){
                        listItem += newItem
                    }else{
                        listItem += newItem
                        listItem = listItem.dropLast(1)
                    }
                    Log.d("mutableShoppingListItems", listItem.toString())
                    warning = ""
                }


            },
                modifier = Modifier.align(Alignment.CenterVertically)){
                Text("Add Item")
            }
        }
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(items = listItem) { item ->
                if (item.itemQuantity.toInt() > 0) {
                    ShoppingItem(item)
                }
            }

        }
        Text(text = warning)
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Shopping_listTheme {
        ShoppingList()
    }
}