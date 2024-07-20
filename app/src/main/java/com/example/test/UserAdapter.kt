// package com.example.test
//
// import android.app.Activity
// import android.content.Context
// import android.content.Intent
// import android.view.LayoutInflater
// import android.view.View
// import android.view.ViewGroup
// import android.widget.TextView
// import androidx.recyclerview.widget.RecyclerView
// import com.example.freemarket.R
// import com.example.freemarket.dto.ProductDto
//
// class UserAdapter(private val context: Context, private val userList:ArrayList<ProductDto>):
//    RecyclerView.Adapter<UserAdapter.UserViewHolder>(){
//
//    //화면 설정
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
//
//        val view = LayoutInflater.from(context)
//            .inflate(R.layout.user_layout, parent, false)
//
//        return UserViewHolder(view)
//    }
//
//    //데이터 설정
//    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
//
//        val user: ProductDto = userList[position]
//
//        holder.nameText.text = user.productName
//        holder.ageText.text = user.productAge
//
//        holder.itemView.setOnClickListener {
//
//            //사용자 수정화면으로 이동
//            val intent = Intent(context, UserUpdateActivity::class.java)
//            intent.putExtra("key", user.productKey)
//            intent.putExtra("name", user.productName)
//            intent.putExtra("age", user.productAge)
//            context.startActivity(intent)
//            (context as Activity).finish()
//        }
//    }
//
//    //값 갯수 리턴
//    override fun getItemCount(): Int {
//
//        return userList.size
//    }
//
//    class UserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
//
//        val nameText: TextView = itemView.findViewById(R.id.name_text)
//        val ageText: TextView = itemView.findViewById(R.id.age_text)
//    }
// }
