package com.example.virgo.ui.screen.ecommerce

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.virgo.R
import com.example.virgo.model.ecommerce.Product
import com.example.virgo.model.ecommerce.ProductWithQuantity
import com.example.virgo.model.lib.Address
import com.example.virgo.route.ecommerce.CartRoute
import com.example.virgo.route.profile.AddAddressRoute
import com.example.virgo.route.profile.ChangeAddressRoute
import com.example.virgo.ui.screen.lib.SearchBar
import com.example.virgo.ui.theme.ColorAccent
import com.example.virgo.ui.theme.ColorBackground
import com.example.virgo.viewModel.ecommerce.CartViewModel
import com.example.virgo.viewModel.ecommerce.CheckOutViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckOutScreen(cartItemIdList: List<String>, navController: NavController) {
    val viewModel : CheckOutViewModel = viewModel()
    val cartItems = viewModel.cartItems.value
    val merchandiseSubtotal = cartItems.map { (it.quantity?:1) * (it.product?.price?:1f) }.sum()
    val shippingFee = 0f
    val total = merchandiseSubtotal + shippingFee
    var showAddress by remember { mutableStateOf(false) }
    val addresses = viewModel.address.value
    val selectedAddress = viewModel.selectedAddress.value
    LaunchedEffect(key1 = cartItemIdList) {
        viewModel.load(cartItemIdList)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Xác nhận đơn hàng", fontSize = 20.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { /* Handle back navigation */ }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color.White
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            viewModel.checkout(total = total) {
                                navController.navigate(CartRoute)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = ColorAccent)
                    ) {
                        Text("Thanh toán", color = colorResource(id = R.color.white))
                    }
                }
            }
        }
    ) { innerPadding ->
        // Using LazyColumn for scrollable content
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF0F3F7))
                .padding(16.dp)
        ) {
            item {
                // Delivery Method Section
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Hình thức nhận hàng",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(onClick = { /* TODO: Handle change delivery method */ }) {
                        Text(text = "Giao hàng tận nơi", color = ColorAccent)
                    }
                }
            }

            item {
                // Delivery Address Section
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Giao hàng tới",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(onClick = { showAddress = true }) {
                        Text(text = "Chọn địa chỉ", color = ColorAccent)
                    }
                }
            }

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, shape = RoundedCornerShape(8.dp))
                        .padding(16.dp)
                ) {
                    if (selectedAddress == null) {
                        Text(text = "Vui lòng chọn địa chỉ nhận hàng")
                    } else {
                        Column (
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = selectedAddress.name.toString(),
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Số điện thoại: " + selectedAddress.phoneNumber.toString(),
                                style = MaterialTheme.typography.bodySmall.copy(),
                            )
                            Text(
                                text = selectedAddress.toString(),
                                style = MaterialTheme.typography.bodySmall.copy(),
                            )
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            items(cartItems) { cartItem ->
                ProductItem1(cartItem)
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                PaymentInfoScreen(
                    merchandiseSubtotal = merchandiseSubtotal,
                    shippingFee = shippingFee,
                    total = total
                )
            }
        }
        if(showAddress){
            ModalBottomSheet(onDismissRequest = {showAddress = false}) {
                AddressBox(
                    addresses = addresses,
                    currentAddress = selectedAddress,
                    onAddNewAddress = { navController.navigate(ChangeAddressRoute(it)) },
                ) {
                    viewModel.onSelectAddress(it)
                    showAddress = false
                }
            }
        }
    }
}

@Composable
fun AddressBox(
    addresses: List<Address>,
    currentAddress: Address?,
    onAddNewAddress: (Int) -> Unit,
    onCLick: (Address) -> Unit,
    )
{
    Column(
        modifier = Modifier
            .background(ColorBackground)
            .fillMaxWidth()
            .height(600.dp)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Button(
            onClick = { onAddNewAddress(-1) },
        ) {
            Text(text = "Thêm địa chỉ")
        }

        Box(modifier = Modifier.weight(1f)) {
            LazyColumn {
                itemsIndexed(addresses) { index, address ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color.White)
                            .clickable { onCLick(address) }
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column (
                            modifier = Modifier.weight(1f)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = address.name.toString(),
                                    fontWeight = FontWeight.Bold
                                )
                                TextButton(onClick = {
                                    onAddNewAddress(index)
                                }) {
                                    Text(
                                        text = "Sửa",
                                        style = MaterialTheme.typography.bodyMedium.copy()
                                    )
                                }
                            }
                            Text(
                                text = "Số điện thoại: " + address.phoneNumber.toString(),
                                style = MaterialTheme.typography.bodySmall.copy(),
                            )
                            Text(
                                text = address.toString(),
                                style = MaterialTheme.typography.bodySmall.copy(),
                            )
                            if (address == currentAddress) {
                                Icon(
                                    imageVector = Icons.Filled.Check,
                                    contentDescription = null,
                                    tint = Color(0xFF007BFF)
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
}

@Composable
fun ProductItem1(product: ProductWithQuantity) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {

        AsyncImage(
            model = stringResource(R.string.github_page)+"/drawable/"+ (product.product?.images?.get(0) ?: "image_holder.jpg"),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(60.dp)
        )


        Column(modifier = Modifier.weight(1f)) {
            Text(text = product.product?.name.toString(), maxLines = 2, overflow = TextOverflow.Ellipsis)
            Text(text = product.product?.getFormattedPrice()?:"0 đ", color = ColorAccent, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }

        Text(text = "${product.quantity} ${product.product?.packaging?.type}")
    }
}

@Composable
fun PaymentInfoScreen(
    merchandiseSubtotal: Float,
    shippingFee: Float,
    total: Float
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0F3F7))
            .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(8.dp))

        // Payment Information Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(8.dp))
                .padding(16.dp)
        ) {
            Text(
                text = "Thông tin thanh toán",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Payment Details Rows
            PaymentDetailRow("Tổng tiền", merchandiseSubtotal.toString(), isBold = true)
            PaymentDetailRow("Phí vận chuyển", shippingFee.toString() , Color(0xFF007BFF))

            Spacer(modifier = Modifier.height(8.dp))

            // Total Amount Row
            PaymentDetailRow("Thành tiền", total.toString(), isBold = true)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Terms and Conditions Text
        Text(
            text = "Bằng việc tiến hành đặt mua hàng, bạn đồng ý với Điều khoản dịch vụ và Chính sách xử lý dữ liệu cá nhân của Nhà thuốc Virgo",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

    }
}

@Composable
fun PaymentDetailRow(label: String, amount: String, color: Color = Color.Black, isBold: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = amount,
            fontSize = 14.sp,
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal,
            color = color
        )
    }
}