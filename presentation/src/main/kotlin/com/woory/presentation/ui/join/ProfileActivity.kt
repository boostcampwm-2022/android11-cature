package com.woory.presentation.ui.join

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.woory.presentation.R
import com.woory.presentation.databinding.ActivityProfileBinding
import com.woory.presentation.ui.BaseActivity
import com.woory.presentation.ui.main.MainActivity
import com.woory.presentation.ui.promiseinfo.PromiseInfoActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.threeten.bp.OffsetDateTime

@AndroidEntryPoint
class ProfileActivity : BaseActivity<ActivityProfileBinding>(R.layout.activity_profile) {

    internal val viewModel: ProfileViewModel by viewModels()

    private var code: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.lifecycleOwner = this@ProfileActivity
        binding.vm = viewModel
        code = intent.getStringExtra(PROMISE_CODE_KEY)

        initToolbar()
        bindViews()
    }

    private fun initToolbar() = with(binding) {
        setSupportActionBar(toolbar)

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }
    }

    private fun bindViews() = with(binding) {
        joinButton.setOnClickListener {
            code?.let { code ->
                viewModel.join(code)
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.promise.collectLatest { promise ->
                    promise ?: return@collectLatest

                    if (OffsetDateTime.now() >= promise.data.gameDateTime) {
                        showDialog(CodeState.ALREADY_STARTED.getMessage(this@ProfileActivity))
                    } else {
                        viewModel.insertPromise(promise)
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.errorType.collectLatest { codeState ->
                    codeState ?: return@collectLatest

                    showDialog(codeState.getMessage(this@ProfileActivity))
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.error.collectLatest { isError ->
                    if (isError) {
                        Snackbar.make(
                            binding.root,
                            getString(R.string.join_failure),
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.success.collectLatest { isSuccess ->
                    if (isSuccess) {
                        val code = viewModel.promise.value?.code ?: return@collectLatest

                        PromiseInfoActivity.startActivity(this@ProfileActivity, code)
                        finish()
                    }
                }
            }
        }
    }

    private fun showDialog(message: String) {
        MaterialAlertDialogBuilder(this)
            .setMessage(message)
            .setNeutralButton(getString(R.string.back_main)) { _, _ ->
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            .show()
    }

    companion object {
        const val PROMISE_CODE_KEY = "PROMISE_CODE_KEY"

        fun startActivity(context: Context, promiseCode: String) =
            context.startActivity(Intent(context, ProfileActivity::class.java).apply {
                putExtra(PROMISE_CODE_KEY, promiseCode)
            })
    }
}