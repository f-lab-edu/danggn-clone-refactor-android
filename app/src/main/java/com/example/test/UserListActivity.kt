//package com.example.test
//
//import android.os.Bundle
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.recyclerview.widget.ItemTouchHelper
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.example.freemarket.dao.ProductDao
//import com.example.freemarket.databinding.ActivityUserListBinding
//import com.example.freemarket.dto.ProductDto
//import com.google.firebase.database.DataSnapshot
//import com.google.firebase.database.DatabaseError
//import com.google.firebase.database.ValueEventListener
//class UserListActivity : AppCompatActivity() {
//
//    lateinit var binding: ActivityUserListBinding
//
//    lateinit var dao: ProductDao
//
//    lateinit var adapter: UserAdapter
//
//    lateinit var userList: ArrayList<ProductDto>
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityUserListBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        //userList 초기화
//        userList = ArrayList()
//
//        //dao 초기화
//        dao = ProductDao()
//
//        //adapter 초기화
//        adapter = UserAdapter(this, userList)
//
//        //recycelrView 초기화
//        binding.recyclerView.layoutManager = LinearLayoutManager(this)
//        binding.recyclerView.adapter = adapter
//
//        //사용자 정보 가져오기
//        getUserList()
//
//        //사용자 삭제 기능
//        ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
//            override fun onMove(
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder,
//                target: RecyclerView.ViewHolder
//            ): Boolean {
//                return false
//            }
//
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                //해당 위치 값 변수에 담기
//                val position = viewHolder.position
//
//                when(direction){
//                    ItemTouchHelper.LEFT ->{
//                        val key = userList[position].productKey
//
//                        dao.userDelete(key).addOnSuccessListener { //성공 이벤트
//
//                            Toast.makeText(this@UserListActivity, "삭제 성공",
//                                Toast.LENGTH_SHORT).show()
//                        }.addOnFailureListener { //삭제 이벤트
//                            Toast.makeText(this@UserListActivity, "삭제 실패: ${it.message}",
//                                Toast.LENGTH_SHORT).show()
//                        }
//                    }//LEFT
//                }//when
//            }
//        }).attachToRecyclerView(binding.recyclerView)
//
//    }//onCreate()
//
//    private fun getUserList(){
//        dao.getUserList()?.addValueEventListener(object: ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//                //리스트 초기화
//                userList.clear()
//
//                for(dataSnapshot in snapshot.children){
//
//                    val user = dataSnapshot.getValue(ProductDto::class.java)
//
//                    //키값 가져오기
//                    val key = dataSnapshot.key
//
//                    //사용자 정보에 키 값 담기
//                    user?.productKey = key.toString()
//
//                    //리스트에 담기
//                    if(user != null){
//                        userList.add(user)
//                    }
//                }
//
//                //데이터 적용
//                adapter.notifyDataSetChanged()
//            }
//
//            override fun onCancelled(error: DatabaseError) {}
//        })
//    }
//
//
//    fun delete_Chat(position: Int) {
//        userList.get(position).setChatting_room_msg("삭제 되었습니다")
//        notifyItemChanged(position) //갱신
//    }
//}