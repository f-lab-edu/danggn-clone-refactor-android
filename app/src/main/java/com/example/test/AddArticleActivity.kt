// package com.example.test
//
// import android.Manifest
// import android.app.Activity
// import android.app.AlertDialog
// import android.content.Intent
// import android.content.pm.PackageManager
// import android.net.Uri
// import androidx.appcompat.app.AppCompatActivity
// import android.os.Bundle
// import android.widget.Toast
// import androidx.core.content.ContextCompat
// import androidx.core.view.isVisible
// import com.dldmswo1209.usedgoods.databinding.ActivityArticleAddBinding
// import com.dldmswo1209.usedgoods.mypage.DBKey.Companion.DB_ARTICLES
// import com.example.freemarket.databinding.ActivityAddArticleBinding
// import com.google.firebase.auth.FirebaseAuth
// import com.google.firebase.auth.ktx.auth
// import com.google.firebase.database.DatabaseReference
// import com.google.firebase.database.FirebaseDatabase
// import com.google.firebase.database.ktx.database
// import com.google.firebase.ktx.Firebase
// import com.google.firebase.storage.FirebaseStorage
// import com.google.firebase.storage.ktx.storage
//
//
// class AddArticleActivity : AppCompatActivity() {
//    private var mBinding : ActivityAddArticleBinding? = null
//    private val binding get() = mBinding!!
//    private var selectedUri: Uri? = null
//    private val auth: FirebaseAuth by lazy {
//        Firebase.auth
//    }
//    private val storage: FirebaseStorage by lazy {
//        Firebase.storage
//    }
//    private val articleDB: DatabaseReference by lazy {
//        Firebase.database.reference.child(DB_ARTICLES)
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        mBinding = ActivityAddArticleBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        binding.imageAddButton.setOnClickListener { // 이미지 등록 버튼 클릭 이벤트
//            when{
//                ContextCompat.checkSelfPermission(
//                    this,
//                    Manifest.permission.READ_EXTERNAL_STORAGE
//                ) == PackageManager.PERMISSION_GRANTED -> {
//                    // 권한이 부여된 경우
//                    startContentProvider()
//                }
//                shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE) -> {
//                    // 사용자가 권한 요청을 명시적으로 거부한 경우
//                    showPermissionContextPopup() // 팝업 요청
//                }
//                else -> {
//                    // 그 외
//                    requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1010)
//                }
//            }
//        }
//        binding.submitButton.setOnClickListener { // 게시물 등록 버튼 클릭 이벤트
//            val title = binding.titleEditText.text.toString()
//            val price = binding.priceEditText.text.toString()
//            val sellerId = auth.currentUser?.uid.orEmpty()
//
//            showProgress() // 지연시간 동안 progressbar 를 보여줌
//            // 중간에 이미지가 있으면 업로드 과정을 추가
//            if(selectedUri != null){
//                val photoUri = selectedUri ?: return@setOnClickListener
//                uploadPhoto(photoUri,
//                    successHandler = { uri->
//                        uploadArticle(sellerId, title, price, uri)
//                    },
//                    errorHandler = {
//                        Toast.makeText(this, "사진 업로드에 실패했습니다.", Toast.LENGTH_SHORT).show()
//                        hideProgress()
//                    })
//            }else{
//                // 이미지가 없는 경우 이미지 제외하고 등록
//                uploadArticle(sellerId,title,price,"")
//            }
//        }
//    }
//    private fun uploadPhoto(uri: Uri, successHandler: (String) -> Unit, errorHandler: () -> Unit){
//        val fileName = "${System.currentTimeMillis()}.png" // 이미지 파일 이름
//        storage.reference.child("article/photo").child(fileName) // storage 에 article/photo/fileName 경로로 저장
//            .putFile(uri)
//            .addOnCompleteListener{
//                if(it.isSuccessful){
//                    // 업로드 성공시
//                    storage.reference.child("article/photo").child(fileName).downloadUrl
//                        .addOnSuccessListener { uri ->
//                            // 다운로드 성공시
//                            successHandler(uri.toString()) // successHandler 실행
//                        }
//                        .addOnFailureListener {
//                            // 다운로드 실패시
//                            errorHandler() // errorHandler 실행
//                        }
//                }else{
//                    // 업로드 실패시
//                    errorHandler()
//                }
//            }
//    }
//    private fun uploadArticle(sellerId: String, title: String, price: String, imageUrl: String){
//        val model = ArticleModel(sellerId, title, System.currentTimeMillis(), "$price 원", imageUrl) // ArticleModel 생성
//        articleDB.push().setValue(model) // Realtime DB에 저장
//
//        hideProgress() // progressbar 를 숨김
//        finish()
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        when(requestCode){
//            1010->{
//                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                    startContentProvider()
//                } else{
//                    Toast.makeText(this, "권한을 거부하셨습니다.", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//    }
//    private fun startContentProvider(){
//        val intent = Intent(Intent.ACTION_GET_CONTENT)
//        intent.type = "image/*"
//        startActivityForResult(intent, 2020)
//    }
//    private fun showProgress(){
//        binding.progressbar.isVisible = true
//    }
//    private fun hideProgress(){
//        binding.progressbar.isVisible = false
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if(resultCode != RESULT_OK){
//            return
//        }
//        when(requestCode){
//            2020 -> {
//                val uri = data?.data
//                if(uri != null){
//                    binding.photoImageView.setImageURI(uri)
//                    selectedUri = uri
//                } else{
//                    Toast.makeText(this, "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
//                }
//            }else -> {
//            Toast.makeText(this, "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
//        }
//        }
//    }
//    private fun showPermissionContextPopup(){
//        AlertDialog.Builder(this)
//            .setTitle("권한이 필요합니다.")
//            .setMessage("사진을 가져오기 위해 필요합니다.")
//            .setPositiveButton("동의") { _, _ ->
//                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1010)
//            }
//            .create()
//            .show()
//    }
// }
