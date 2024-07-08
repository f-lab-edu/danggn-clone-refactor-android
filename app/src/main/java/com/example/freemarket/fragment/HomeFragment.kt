package com.example.freemarket.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.freemarket.CategoryActivity
import com.example.freemarket.R
import com.example.freemarket.SignUpAndUpdateActivity
import com.example.freemarket.adapter.HomeAdapter
import com.example.freemarket.dao.ProductDao
import com.example.freemarket.databinding.FragmentHomeBinding
import com.example.freemarket.databinding.FragmentSettingBinding
import com.example.freemarket.dto.ProductDto
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class HomeFragment : Fragment() {
    lateinit var dao: ProductDao

    lateinit var adapter: HomeAdapter

    lateinit var productList: ArrayList<ProductDto>

    private lateinit var binding: FragmentHomeBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //userList 초기화
        productList = ArrayList()

        //dao 초기화
        dao = ProductDao()

        //adapter 초기화
        adapter = HomeAdapter(requireActivity(), productList)

        //recycelrView 초기화
        binding.rvHomeFragment.layoutManager = LinearLayoutManager(context)
        binding.rvHomeFragment.adapter = adapter

        //사용자 정보 가져오기
        getProductList()


        binding.imbCategoryFragment.setOnClickListener(View.OnClickListener {
            val intent = Intent(requireActivity(), CategoryActivity::class.java)
            startActivity(intent)
        })
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
//                    user?.productKey = key.toString()

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