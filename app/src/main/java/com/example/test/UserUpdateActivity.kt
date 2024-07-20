// package com.example.test
//
// import android.content.Intent
// import android.os.Bundle
// import android.widget.Toast
// import androidx.appcompat.app.AppCompatActivity
// import com.example.freemarket.dao.ProductDao
// import com.example.freemarket.databinding.ActivityUserUpdateBinding
//
// class UserUpdateActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityUserUpdateBinding
//
//    lateinit var sKey: String
//    lateinit var sName: String
//    lateinit var sAge: String
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityUserUpdateBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        //데이터베이스 객체
//        val dao = ProductDao()
//
//        //데이터 null체크
//        if(intent.hasExtra("key") && intent.hasExtra("name")
//            && intent.hasExtra("age")){
//
//            //데이터 담기
//            sKey = intent.getStringExtra("key")!!
//            sName = intent.getStringExtra("name")!!
//            sAge = intent.getStringExtra("age")!!
//
//            //데이터 보여주기
//            binding.upNameEdit.setText(sName)
//            binding.upAgeEdit.setText(sAge)
//
//            //사용자정보 수정버튼 이벤트
//            binding.updateBtn.setOnClickListener {
//
//                //입력값
//                val uName = binding.upNameEdit.text.toString()
//                val uAge = binding.upAgeEdit.text.toString()
//
//                //파라미터 셋팅
//                val hasMap: HashMap<String, Any> =  HashMap()
//                hasMap["userName"] = uName
//                hasMap["userAge"] = uAge
//
//                //사용자 업데이트 이벤트
//                dao.userUpdate(sKey, hasMap).addOnSuccessListener {
//
//
//                    Toast.makeText(applicationContext, "수정 성공",
//                        Toast.LENGTH_SHORT).show()
//
//                    //사용자 목록으로 이동
//                    val intent = Intent(this@UserUpdateActivity,
//                        UserListActivity::class.java)
//                    startActivity(intent)
//                    finish()
//
//                }.addOnFailureListener {
//                    Toast.makeText(applicationContext, "수정 실패: ${it.message}",
//                        Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//    }
// }
