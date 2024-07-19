package com.example.freemarket.dao

import com.example.freemarket.dto.ProductDto
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query

//Dao란 디비의 데이터에 접근하기 위한것
class ProductDao {
    private var databaseReference: DatabaseReference? = null

    init{
        val db = FirebaseDatabase.getInstance()
        databaseReference = db.getReference("product")
    }

    //조회
    fun getList(): Query?{
        return databaseReference
    }

    //수정
    fun update(key: String, hashMap: HashMap<String, Any>): Task<Void>{
        return databaseReference!!.child(key)!!.updateChildren(hashMap)
    }

    //삭제
    fun delete(key: String): Task<Void>{
        return databaseReference!!.child(key).removeValue()
    }
}