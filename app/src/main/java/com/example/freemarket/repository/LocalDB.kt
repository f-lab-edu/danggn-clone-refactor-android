package com.example.freemarket.repository

import android.app.Activity

class LocalDB {
    fun saveLocalData(activity: Activity, phone: String, name: String, profileImage: String) {
        // SharedPreferences 객체 생성
        val pref = activity.getSharedPreferences("user", 0) // "pref"라는 이름의 SharedPreferences 파일 열기
        val edit = pref.edit() // 수정 모드로 열기

        // SharedPreferences 에 데이터 저장
        // 1번째 인자는 키, 2번째 인자는 실제 담아둘 값
        edit.putString("phone", phone)
        edit.putString("name", name)
        edit.putString("profile_image", profileImage)

        edit.apply() // 변경사항 저장 완료
    }

    // 데이터 불러오기 메서드
    fun loadLocalData(activity:Activity): String? {
        // SharedPreferences 객체 생성
        val pref = activity.getSharedPreferences("user",0)
        val phone = pref.getString("phone","")
        // SharedPreferences 에서 데이터 불러오기
        // 1번째 인자는 키, 2번째 인자는 데이터가 존재하지 않을경우의 값

        return phone
    }

}