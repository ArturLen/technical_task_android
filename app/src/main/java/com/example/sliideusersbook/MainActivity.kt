package com.example.sliideusersbook

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sliideusersbook.databinding.ActivityUserListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserListBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}