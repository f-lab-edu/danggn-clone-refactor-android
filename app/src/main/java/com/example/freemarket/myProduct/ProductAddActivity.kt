package com.example.freemarket.myProduct

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.freemarket.CategoryActivity
import com.example.freemarket.ProgressDialog
import com.example.freemarket.R
import com.example.freemarket.databinding.ActivityProductAddBinding
import com.example.freemarket.repository.LocalDB
import com.example.freemarket.repository.ProductAddDB
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.StorageReference

class ProductAddActivity : AppCompatActivity() {
    private var uriList = ArrayList<Uri>()
    private val maxNumber = 10
    lateinit var adapter: ImageAdapter
    private val binding: ActivityProductAddBinding by lazy {
        ActivityProductAddBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //카테고리 선택
        val local = LocalDB()
        val preference = local.getLocalData(this)!!
        val productCategory = preference.getString("category", "")!!



        //카테고리 선택하기
        binding.btProductAddCategory.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, CategoryActivity::class.java)
            startActivity(intent)
        })

        //사용자 등록 버튼 이벤트
        binding.btProductAddComplete.setOnClickListener {
            val productAddDB = ProductAddDB()
            val productSubject = binding.etProductAddSubject.text.toString() //이름
            val productPrice = binding.etProductAddPrice.text.toString() //가격
            val productLocation = binding.etProductAddLocation.text.toString() //위치
            val productContent = binding.etProductAddContent.text.toString() //내용

            //uriList.get(0)상품 대표 이미지
            productAddDB.productAdding(
                applicationContext,
                this,
                productSubject,
                productPrice,
                productLocation,
                productContent,
                uriList,
                productCategory
            )
            val progressbar = ProgressDialog()
            progressbar.showDialog(this)
        }


        // RecyclerView에 Adapter 연결하기
        adapter = ImageAdapter(this, uriList)
        binding.rvProductAddGallery.adapter = adapter
        // LinearLayoutManager을 사용하여 수평으로 아이템을 배치한다.
        binding.rvProductAddGallery.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // ImageView를 클릭할 경우
        // 선택 가능한 이미지의 최대 개수를 초과하지 않았을 경우에만 앨범을 호출한다.
        binding.imvProductAddGallery.setOnClickListener(View.OnClickListener {
            if (uriList.count() == maxNumber) {
                Toast.makeText(
                    this,
                    "이미지는 최대 ${maxNumber}장까지 첨부할 수 있습니다.",
                    Toast.LENGTH_SHORT
                ).show();
            }
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            registerForActivityResult.launch(intent)
        })

        // 업로드 하기 버튼을 클릭할 경우
        // for문을 통해 uriList.count()만큼 imageUpload 함수를 호출한다.
        adapter.setItemClickListener(object : ImageAdapter.onItemClickListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onItemClick(position: Int) {
                uriList.removeAt(position)
                adapter.notifyDataSetChanged()
                printCount()
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private val registerForActivityResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            when (result.resultCode) {
                Activity.RESULT_OK -> {
                    val clipData = result.data?.clipData
                    if (clipData != null) { // 이미지를 여러 개 선택할 경우
                        val clipDataSize = clipData.itemCount
                        val selectableCount = maxNumber - uriList.count()
                        if (clipDataSize > selectableCount) { // 최대 선택 가능한 개수를 초과해서 선택한 경우
                            Toast.makeText(
                                this,
                                "이미지는 최대 ${selectableCount}장까지 첨부할 수 있습니다.",
                                Toast.LENGTH_SHORT
                            ).show();
                        } else {
                            // 선택 가능한 경우 ArrayList에 가져온 uri를 넣어준다.
                            for (i in 0 until clipDataSize) {
                                uriList.add(clipData.getItemAt(i).uri)
                            }
                        }
                    } else { // 이미지를 한 개만 선택할 경우 null이 올 수 있다.
                        val uri = result?.data?.data
                        if (uri != null) {
                            uriList.add(uri)
                        }
                    }
                    // notifyDataSetChanged()를 호출하여 adapter에게 값이 변경 되었음을 알려준다.
                    adapter.notifyDataSetChanged()
                    printCount()
                }
            }
        }

    private fun printCount() {
        binding.tvProductAddGalleryCount.text =  String.format(resources.getString(R.string.image_count),uriList.count(),maxNumber)
    }
}