package com.example.freemarket.dto

data class ProductDto(
    var productKey: String, //파이어베이스 키값
    var productSubject: String, //상품 제목
    var productCategory: String, //상품 카테고리
    var productPrice: String, //상품 가격
    var productLocation: String, //상품 거래지역
    var productContent: String //상품 내용
)


{
    constructor(): this("", "", "","","","")
}