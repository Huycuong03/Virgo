package com.example.virgo.ui.screen.home


import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.virgo.model.ecommerce.Product
import com.example.virgo.model.lib.Article
import com.example.virgo.route.ArticleRoute
import com.example.virgo.route.ecommerce.ProductDetailRoute
import com.example.virgo.route.reminder.ReminderListRoute
import com.example.virgo.route.search.SearchRoute
import com.example.virgo.ui.screen.ecommerce.ProductCard
import com.example.virgo.ui.screen.lib.Gallery
import com.example.virgo.ui.screen.lib.RowItem
import com.example.virgo.ui.theme.ColorBackground
import com.example.virgo.ui.theme.ColorGradient1
import com.example.virgo.viewModel.HomeViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun HomeScreen(navController: NavController) {
//    val reminderlist = remember {
//        mutableListOf(
//            Reminder("7:00", "24/11/2024", "Bạn có lịch uống thuốc: Đơn thuốc số 1"),
//            Reminder("20:00", "24/11/2024", "Bạn có lịch uống thuốc: Đơn thuốc số 1"),
//            Reminder("7:00", "25/11/2024", "Bạn có lịch uống thuốc: Đơn thuốc số 1"),
//            Reminder("20:00", "25/11/2024", "Bạn có lịch uống thuốc: Đơn thuốc số 1")
//        )
//    }
//    val today = LocalDate.now()
//    val upcomingReminders = reminderlist.filter {
//        val reminderDate = LocalDate.parse(it.date, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
//        reminderDate >= today
//    }.take(2)

    val viewModel : HomeViewModel = viewModel()
    val articles = viewModel.articleList.value
    val products = viewModel.productList.value

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
                .background(color = Color.White, shape = RoundedCornerShape(50))
                .padding(10.dp)
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
                        navController.navigate(ArticleRoute(filePath))
                    }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Top ban chay",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Gallery(items = products) {product ->
                    ProductCard(product = product as Product) {
                        navController.navigate(ProductDetailRoute(it.id.toString()))
                    }
                }
            }
            Button(
                onClick = {
                    navController.navigate(ReminderListRoute)
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .border(2.dp, Color.Blue, CircleShape) // Adding border with rounded corners
                    .padding(12.dp), // Adding padding
                colors = ButtonDefaults.buttonColors() // Adding background color
            ) {
                Text(
                    text = "Nhac nho",
                    fontSize = 14.sp,
                    color = Color.Blue,
                    fontWeight = FontWeight.Medium
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
           Column(modifier = Modifier.width(310.dp)) {
               RowItem(
                   name = article.name.toString(),
                   description = article.description,
                   image = article.image
               ){
                   onArticleClick(article.html.toString())
               }
           }
        }
    }
}

@Composable
fun ArticleCardHorizontal(article: Article, onArticleClick: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { article.html?.let { onArticleClick(it) } },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = article.image,
                contentDescription = article.name,
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
                    text = if (article.name?.length!! > 25) article.name.substring(0, 25) + "..." else article.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.Black,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                        text = (if (article.description?.length!! > 35) article.description.substring(0, 35) + "..." else article.description),
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