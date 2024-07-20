package com.example.freemarket.dao

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ProfileDao {
    private var databaseReference: DatabaseReference? = null // db의 시작점을 알려주고 읽고 쓰기 위한 준비 코드

    init {
        val db = FirebaseDatabase.getInstance() // 파이어베이스 db를 인스턴스화 한것
        databaseReference = db.getReference("user") // db안에 user 경로로 참조 할수있게 해줌
    }

    fun update(
        key: String,
        hashMap: HashMap<String, Any>,
    ): Task<Void> {
        return databaseReference!!.child(key)!!.updateChildren(hashMap) // 무엇을 접근하는가?
        // databaseReference는 user를 인도해줌
        // child(key)는 -O0xA1bCYBS__lf6wsPk 키값까지 인도해줌
    }
}
