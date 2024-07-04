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
import com.example.freemarket.dao.ProductDao
import com.example.freemarket.databinding.ActivityProductAddBinding
import com.example.freemarket.dto.ProductDto
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import java.text.SimpleDateFormat
import java.util.Date

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


        //데이터베이스 클래스 객체 생성
        val dao = ProductDao()

        //사용자 등록 버튼 이벤트
        binding.btProductAddComplete.setOnClickListener {

            val productSubject = binding.etProductAddSubject.text.toString() //이름
            val productPrice = binding.etProductAddPrice.text.toString() //나이
            val productLocation = binding.etProductAddLocation.text.toString() //나이
            val productContent = binding.etProductAddContent.text.toString() //나이

            //데이터 셋팅
            val product = ProductDto("", productSubject, productPrice,productLocation,productContent)

            dao.add(product).addOnSuccessListener {
                for (i in 0 until uriList.count()) {
                    imageUpload(uriList.get(i), i)
                    try {
                        Thread.sleep(500)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
                Toast.makeText(this, "등록 성공", Toast.LENGTH_SHORT).show()

            }.addOnFailureListener {
                Toast.makeText(this, "등록 실패: ${it.message}", Toast.LENGTH_SHORT).show()
            }
        }


        // RecyclerView에 Adapter 연결하기
        adapter = ImageAdapter(this, uriList)
        binding.recyclerview.adapter = adapter
        // LinearLayoutManager을 사용하여 수평으로 아이템을 배치한다.
        binding.recyclerview.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)


        // ImageView를 클릭할 경우
        // 선택 가능한 이미지의 최대 개수를 초과하지 않았을 경우에만 앨범을 호출한다.

        binding.imageArea.setOnClickListener(View.OnClickListener {
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
        val text = "${uriList.count()}/${maxNumber}"
        binding.countArea.text = text
    }


    // 파일 업로드
    // 파일을 가리키는 참조를 생성한 후 putFile에 이미지 파일 uri를 넣어 파일을 업로드한다.
    private fun imageUpload(uri: Uri, count: Int) {
        // storage 인스턴스 생성
        val storage = Firebase.storage
        // storage 참조
        val storageRef = storage.getReference("product_image")
        // storage에 저장할 파일명 선언
        val fileName = SimpleDateFormat("yyyyMMddHHmmss_${count}").format(Date())

        val mountainsRef = storageRef.child("${fileName}.png")
        val uploadTask = mountainsRef.putFile(uri)

        uploadTask.addOnSuccessListener { taskSnapshot ->
            // 파일 업로드 성공
            Toast.makeText(this, "사진 업로드 성공", Toast.LENGTH_SHORT).show();
        }.addOnFailureListener {
            // 파일 업로드 실패
            Toast.makeText(this, "사진 업로드 실패", Toast.LENGTH_SHORT).show();
        }
    }
}