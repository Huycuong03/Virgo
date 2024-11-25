package com.example.virgo.ui.screen.home


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage
import com.example.virgo.route.search.SearchRoute
import com.example.virgo.ui.theme.ColorBackground
import com.example.virgo.ui.theme.ColorGradient1
import java.time.LocalDate
import java.time.format.DateTimeFormatter
data class Reminder(
    val time: String,
    val date: String,
    val title: String
)
data class Article(
    val title: String,
    val imageUrl: String,
    val description: String,
    val htmlFilePath: String
)

@Composable
fun HomeScreen(navController: NavController) {
    val reminderlist = remember {
        mutableListOf(
            Reminder("7:00", "24/11/2024", "Bạn có lịch uống thuốc: Đơn thuốc số 1"),
            Reminder("20:00", "24/11/2024", "Bạn có lịch uống thuốc: Đơn thuốc số 1"),
            Reminder("7:00", "25/11/2024", "Bạn có lịch uống thuốc: Đơn thuốc số 1"),
            Reminder("20:00", "25/11/2024", "Bạn có lịch uống thuốc: Đơn thuốc số 1")
        )
    }
    val today = LocalDate.now()
    val upcomingReminders = reminderlist.filter {
        val reminderDate = LocalDate.parse(it.date, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        reminderDate >= today
    }.take(2)

    val articles = listOf(
        Article(
            title = "Thường xuyên bị muỗi đốt là do đâu? Khám phá 7 lý do thú vị",
            imageUrl = "https://cdn.hellobacsi.com/wp-content/uploads/2019/05/vi-sao-hay-bi-muoi-dot.jpg",
            description = "Tìm hiểu 7 lý do thú vị khiến bạn thường xuyên bị muỗi đốt.",
            htmlFilePath = "file:///android_asset/article.html"
        ),
        Article(
            title = "BHA cho da dầu mụn: Những lưu ý về cách chọn và cách dùng",
            imageUrl = "https://cdn.hellobacsi.com/wp-content/uploads/2024/10/bha-cho-da-dau-mun.jpg?w=828&q=75",
            description = "Hướng dẫn chọn và sử dụng BHA hiệu quả cho da dầu mụn.",
            htmlFilePath = "file:///android_asset/article1.html"
        ),
        Article(
            title = "Bị bệnh hở van tim có nguy hiểm không?",
            imageUrl = "https://cdn.hellobacsi.com/wp-content/uploads/2024/07/Bi-benh-ho-van-tim-co-nguy-hiem-khong.jpg?w=828&q=75",
            description = "Tìm hiểu về hở van tim và những nguy cơ tiềm ẩn.",
            htmlFilePath = "file:///android_asset/article2.html"
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        ColorGradient1,
                        ColorGradient1.copy(alpha = 0f),
                        ColorGradient1.copy(alpha = 0f),
                        ColorGradient1.copy(alpha = 0f),
                        ColorBackground
                    )
                )
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = ColorBackground, shape = RoundedCornerShape(50))
                .padding(15.dp)
                .clickable { navController.navigate(SearchRoute) }
        ) {
            Icon(imageVector = Icons.Filled.Search, contentDescription = null)
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = "Search for products, articles, ...")
        }
        Spacer(modifier = Modifier.height(40.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(modifier = Modifier.fillMaxWidth())
            {
                upcomingReminders.forEach { reminder ->
                    ReminderBox(time = reminder.time, title = reminder.title, date = reminder.date)
                }

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = "Xem thêm",
                    fontSize = 14.sp,
                    color = Color.Blue,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .clickable {}
                        .align(Alignment.CenterHorizontally)
                )
            }
            Column(modifier = Modifier.fillMaxWidth())
            {
                Text(
                    text = "Bài viết nổi bật",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                ArticleListHorizontal(
                    articles = articles,
                    onArticleClick = { filePath ->
                        navController.navigate("ArticleRoute?url=$filePath")
                    }
                )
            }
        }

    }
}

@Composable
fun ReminderBox(time: String, title: String, date: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color.White),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.DateRange,
                    contentDescription = "Time Icon",
                    tint = Color.Gray,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = time,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.width(16.dp))

                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = date,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
@Composable
fun ArticleListHorizontal(articles: List<Article>, onArticleClick: (String) -> Unit) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(articles) { article ->
            ArticleCardHorizontal(article = article, onArticleClick = onArticleClick)
        }
    }
}

@Composable
fun ArticleCardHorizontal(article: Article, onArticleClick: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onArticleClick(article.htmlFilePath) },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White), // White background
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Image
            AsyncImage(
                model = article.imageUrl,
                contentDescription = article.title,
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))


            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = if (article.title.length > 25) article.title.substring(0, 25) + "..." else article.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.Black,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = if (article.description.length > 35) article.description.substring(0, 35) + "..." else article.description,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Ngày đăng: ${LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))}",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreen(navController = NavController(LocalContext.current))
}

