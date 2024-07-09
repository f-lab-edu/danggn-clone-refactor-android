package com.example.freemarket.dto

import android.net.Uri

data class ProductDto(
    var productSubject: String, //상품 제목
    var productPrice: String, //상품 가격
    var productLocation: String, //상품 거래지역
    var productContent: String, //상품 내용
    var productImage: String?, //상품 대표 이미지
    var productCategory: String? //상품 대표 이미지

)

//이게 뭐길래 있어야 돼?
{
    constructor(): this("", "", "","","","")
}