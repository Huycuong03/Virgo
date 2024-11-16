package com.example.virgo.ui.screen.search

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SearchScreen() {
    var searchText by remember { mutableStateOf(TextFieldValue("")) }
    val recentSearches = listOf("sửa rửa mặt", "thuốc nhỏ mắt")
    val frequentSearches = listOf(
        "thuốc nhỏ mắt", "omega", "Men vi sinh",
        "kem chống nắng", "siro ho", "Kẽm", "Dhc", "vitamin C", "vitamin A"
    )

    val allProducts = listOf("A", "a", "B", "C", "D")
    val allDiseases = listOf("Bệnh A", "Bệnh B", "Bệnh C")
    val allArticles = listOf("Bài viết A", "Bài viết B", "Bài viết C")

    val filteredRecent = recentSearches.filter { recent ->
        recent.contains(searchText.text, ignoreCase = true) }

    val filteredProducts = allProducts.filter { product ->
        product.contains(searchText.text, ignoreCase = true)
    }
    val filteredDiseases = allDiseases.filter { disease ->
        disease.contains(searchText.text, ignoreCase = true)
    }
    val filteredArticles = allArticles.filter { article ->
        article.contains(searchText.text, ignoreCase = true)
    }

    var selectedTag by remember { mutableStateOf("Product") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { /* Handle back press */ },
                modifier = Modifier.size(20.dp)
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.Black)
            }

            SearchTextField(
                searchText = searchText,
                onSearchTextChange = { searchText = it },
                onClearSearch = { searchText = TextFieldValue("") },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (searchText.text.isEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Lịch sử tìm kiếm",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            if(recentSearches.isEmpty()){
                Text(
                    text = "Chưa có lịch sử",
                    color = Color.Red
                )
            }
            else{
                val limitedRecentSearches = recentSearches.takeLast(5).reversed()

                limitedRecentSearches.forEach { search ->
                    Text(
                        text = search,
                        color = Color.Blue,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Tra cứu hàng đầu",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            TagSection(items = frequentSearches, fullWidth = true)
        } else {

            Row(
                modifier = Modifier.padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Tag(text = "Product") { selectedTag = "Product" }
                Tag(text = "Disease") { selectedTag = "Disease" }
                Tag(text = "Article") { selectedTag = "Article" }
            }
            Text(
                text = "Lịch sử tìm kiếm",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            if (filteredRecent.isNotEmpty()){
                Spacer(modifier = Modifier.height(8.dp))
                filteredRecent.forEach{recent ->
                    Text(
                        text = recent,
                        color = Color.Blue,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )}
            }
            else{
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Chưa có lịch sử",
                    color = Color.Red
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            when (selectedTag) {
                "Product" -> {
                    if (filteredProducts.isNotEmpty()) {
                        Text(
                            text = "Đề xuất sản phẩm:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        filteredProducts.forEach { product ->
                            Text(
                                text = product,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                    } else {
                        Text(
                            text = "Không tìm thấy sản phẩm nào.",
                            color = Color.Red
                        )
                    }
                }
                "Disease" -> {
                    if (filteredDiseases.isNotEmpty()) {
                        Text(
                            text = "Đề xuất bệnh lý:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        // Display each disease on a new line
                        filteredDiseases.forEach { disease ->
                            Text(
                                text = disease,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                    } else {
                        Text(
                            text = "Không tìm thấy bệnh lý nào.",
                            color = Color.Red
                        )
                    }
                }
                "Article" -> {
                    if (filteredArticles.isNotEmpty()) {
                        Text(
                            text = "Đề xuất bài viết:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        filteredArticles.forEach { article ->
                            Text(
                                text = article,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                    } else {
                        Text(
                            text = "Không tìm thấy bài viết nào.",
                            color = Color.Red
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TagSection(items: List<String>, fullWidth: Boolean = false) {
    var currentRowWidth = 0.dp
    val rowItems = mutableListOf<MutableList<String>>()
    var currentRow = mutableListOf<String>()

    items.forEach { item ->
        val itemWidth = (item.length * 10).dp

        if (currentRowWidth + itemWidth > (if (fullWidth) 360.dp else 300.dp)) {
            rowItems.add(currentRow)
            currentRow = mutableListOf()
            currentRowWidth = 0.dp
        }
        currentRow.add(item)
        currentRowWidth += itemWidth + 8.dp // 8.dp as spacing
    }
    if (currentRow.isNotEmpty()) rowItems.add(currentRow)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = if (fullWidth) 0.dp else 8.dp) // Wider for fullWidth items
    ) {
        rowItems.forEach { row ->
            Row(
                modifier = Modifier.padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                row.forEach { item ->
                    Tag(text = item) { /* Handle tag click */ }
                }
            }
        }
    }
}

@Composable
fun Tag(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .background(Color(0xFFE0E0E0), shape = RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            color = Color.Black,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun SearchTextField(
    searchText: TextFieldValue,
    onSearchTextChange: (TextFieldValue) -> Unit,
    onClearSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = searchText,
        onValueChange = onSearchTextChange,
        placeholder = { Text("Tìm tên thuốc, bệnh lý") },
        singleLine = true,
        trailingIcon = {
            if (searchText.text.isNotEmpty()) {
                IconButton(onClick = onClearSearch) {
                    Icon(Icons.Default.Close, contentDescription = "Clear")
                }
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 8.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun SearchPreview() {
    SearchScreen()
}

