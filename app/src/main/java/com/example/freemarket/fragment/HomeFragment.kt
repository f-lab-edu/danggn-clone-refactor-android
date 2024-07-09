package com.example.freemarket.fragment
// 홈 수정
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.freemarket.R
import com.example.freemarket.adapter.HomeAdapter
import com.example.freemarket.dao.ProductDao
import com.example.freemarket.dto.ProductDto
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class HomeFragment : Fragment() {
    lateinit var dao: ProductDao

    lateinit var adapter: HomeAdapter

    lateinit var productList: ArrayList<ProductDto>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rvHomeFragment = view.findViewById<RecyclerView>(R.id.rv_home_fragment)


        //userList 초기화
        productList = ArrayList()

        //dao 초기화
        dao = ProductDao()

        //adapter 초기화
        adapter = HomeAdapter(requireActivity(),productList)

        //recycelrView 초기화
        rvHomeFragment.layoutManager = LinearLayoutManager(context)
        rvHomeFragment.adapter = adapter

        //사용자 정보 가져오기
        getProductList()
    }

    private fun getProductList() {
        dao.getList()?.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                //리스트 초기화
                productList.clear()

                for (dataSnapshot in snapshot.children) {

                    val user = dataSnapshot.getValue(ProductDto::class.java)

                    //키값 가져오기
                    val key = dataSnapshot.key

                    //사용자 정보에 키 값 담기
                    user?.productKey = key.toString()

                    //리스트에 담기
                    if (user != null) {
                        productList.add(user)
                    }
                }

                //데이터 적용
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun onDeleteClick(v: View?, positon: Int) {
        productList.removeAt(positon)
        adapter.notifyItemRemoved(positon)
    }

}