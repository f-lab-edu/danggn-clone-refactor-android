package com.example.freemarket.repository

import android.app.Activity
import android.content.SharedPreferences

class LocalDB {
    fun saveLocalData(activity: Activity,key:String, phone: String, name: String, profileImage: String) {
        // SharedPreferences 객체 생성
        val pref = activity.getSharedPreferences("user", 0) // "pref"라는 이름의 SharedPreferences 파일 열기
        val edit = pref.edit() // 수정 모드로 열기

        // SharedPreferences 에 데이터 저장
        edit.putString("key", key)
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

    //쉐어드 객체가 필요한 함수
    fun getLocalData(activity:Activity): SharedPreferences? {
        // SharedPreferences 객체 생성
        val pref = activity.getSharedPreferences("user",0)
        // SharedPreferences 에서 데이터 불러오기
        // 1번째 인자는 키, 2번째 인자는 데이터가 존재하지 않을경우의 값

        return pref
    }


    fun updateLocalData(activity: Activity, name: String?, profileImage: String?) {
        // SharedPreferences 객체 생성
        val pref = activity.getSharedPreferences("user", 0) // "pref"라는 이름의 SharedPreferences 파일 열기
        val edit = pref.edit() // 수정 모드로 열기

        // SharedPreferences 에 데이터 저장
        //둘다 변경
        if (name!=null && profileImage!=null){
            edit.putString("name", name)
            edit.putString("profile_image", profileImage)
            edit.apply() // 변경사항 저장 완료
        }
        //프로필 이미지만 변경
        else if(name==null){
            edit.putString("name", name)
            edit.apply() // 변경사항 저장 완료
        }
        //닉네임만 변경
        else if(profileImage==null){
            edit.putString("name", name)
            edit.apply() // 변경사항 저장 완료
        }
    }

}