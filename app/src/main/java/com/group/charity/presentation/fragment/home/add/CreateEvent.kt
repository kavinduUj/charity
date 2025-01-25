package com.group.charity.presentation.fragment.home.add

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.group.charity.databinding.CreateEventBinding
import com.group.charity.presentation.activity.BaseActivity
import com.group.util.CommonState
import com.group.util.PrefData
import com.group.util.apiError
import com.group.util.logErr
import com.group.util.logOther
import com.group.util.toBase64
import com.group.util.toCompressedBase64
import com.pixplicity.easyprefs.library.Prefs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

@AndroidEntryPoint
class CreateEvent:Fragment() {

    lateinit var binding: CreateEventBinding
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private lateinit var imagePickerLauncher: ActivityResultLauncher<Intent>
    private lateinit var selectedImage: String
    private val createEventViewModel by viewModels<CreateEventViewModel>()
    private lateinit var baseActivity: BaseActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CreateEventBinding.inflate(layoutInflater, container,false)
        init()
        return binding.root
    }
    private fun init() {
        baseActivity = requireActivity() as BaseActivity
        binding.apply {
            goBack.setOnClickListener {
                parentFragmentManager.popBackStack()
            }

            selectImageLay.setOnClickListener {
                checkPermissionAndOpenGallery()
            }
            cover.setOnClickListener {
                checkPermissionAndOpenGallery()
            }
            createEvent.setOnClickListener {
                if (validate()) {
                    createEvent()
                }
            }
        }
        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                openGallery()
            } else {
                Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
        imagePickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK && result.data != null) {
                val selectedImageUri: Uri? = result.data?.data
                selectedImage = selectedImageUri?.toCompressedBase64(requireContext().contentResolver,800, 800, 40) ?: ""
                binding.cover.setImageURI(selectedImageUri)
                binding.selectImageLay.visibility = View.GONE
            }
        }
    }

    private fun validate():Boolean {
        if (selectedImage.isEmpty()) parentFragmentManager.apiError(message = "Please select a cover image").also { return false }
        if (binding.name.text.isEmpty()) parentFragmentManager.apiError(message = "Event name cannot be empty").also { return false }
        if (binding.start.text.isEmpty()) parentFragmentManager.apiError(message = "Event start date cannot be empty").also { return false }
        if (binding.end.text.isEmpty()) parentFragmentManager.apiError(message = "Event end date cannot be empty").also { return false }
        if (binding.about.text.isEmpty()) parentFragmentManager.apiError(message = "Event description cannot be empty").also { return false }
        return true
    }
    private fun checkPermissionAndOpenGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
        } else {
            permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        imagePickerLauncher.launch(intent)
    }
    private fun createEvent() {
        val hashMap: HashMap<String,Any> = HashMap()
        hashMap["eventName"] = binding.name.text.toString()
        hashMap["startDate"] = binding.start.text.toString()
        hashMap["endDate"] = binding.end.text.toString()
        hashMap["location"] = binding.location.text.toString()
        hashMap["aboutEvent"] = binding.about.text.toString()
        hashMap["images"] = JSONArray().put(selectedImage)
        hashMap["comments"] = JSONArray()
        hashMap["backgroundImage"] = JSONObject.NULL
        hashMap["status"] = 1
        hashMap["attendUsers"] = JSONArray()
        createEventViewModel.createEvent(
            "Bearer ${Prefs.getString(PrefData.USER_TOKEN)}",
            hashMap
        )
        lifecycleScope.launch {
            createEventViewModel.createEventStateFLow.collect { res->
                when(res) {
                    is CommonState.Loading -> {
                        baseActivity.loading.isVisible()
                        logOther("createEvent req: ${Gson().toJson(hashMap)}")
                    }
                    is CommonState.Success-> {
                        cancel()
                        baseActivity.loading.isGone()
                        parentFragmentManager.apiError(
                            title = "Success",
                            message = "Event created",
                            action = {
                                parentFragmentManager.popBackStack()
                            }
                        )
                        logOther("createEvent res: ${Gson().toJson(res.data)}")
                    }
                    is CommonState.Error -> {
                        baseActivity.loading.isGone()
                        logErr("createEvent err: ${res.message}")
                        cancel()
                        parentFragmentManager.apiError(
                            message = res.message
                        )
                    }
                }
            }
        }
    }
}