package com.fcbox.locker.ai.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fcbox.locker.ai.project.databinding.FragmentWebviewBinding

class WebViewFragment : Fragment() {

    private var _binding: FragmentWebviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWebviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupWebView()
        initToolbar()
        
        val url = arguments?.getString("url") ?: "https://www.baidu.com"
        binding.webView.loadUrl(url)

        // 处理物理返回键
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (_binding != null && binding.webView.canGoBack()) {
                    binding.webView.goBack()
                } else {
                    isEnabled = false
                    requireActivity().onBackPressed()
                }
            }
        })
    }

    private fun initToolbar() {
        val activity = activity as? AppCompatActivity ?: return
        activity.setSupportActionBar(binding.toolbar)
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            if (_binding != null && binding.webView.canGoBack()) {
                binding.webView.goBack()
            } else {
                findNavController().navigateUp()
            }
        }
    }

    private fun setupWebView() {
        binding.webView.apply {
            settings.apply {
                javaScriptEnabled = true
                domStorageEnabled = true
                loadWithOverviewMode = true
                useWideViewPort = true
                cacheMode = WebSettings.LOAD_DEFAULT
                mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            }
            
            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    if (_binding == null || !isAdded) return
                    val activity = activity as? AppCompatActivity
                    if (activity?.supportActionBar?.title.isNullOrEmpty()) {
                        activity?.supportActionBar?.title = getString(R.string.agreement_link)
                    }
                }
            }

            webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    if (_binding == null) return
                    if (newProgress == 100) {
                        binding.progressBar.visibility = View.GONE
                    } else {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.progressBar.progress = newProgress
                    }
                }

                override fun onReceivedTitle(view: WebView?, title: String?) {
                    super.onReceivedTitle(view, title)
                    if (_binding == null || !isAdded) return
                    if (!title.isNullOrEmpty()) {
                        (activity as? AppCompatActivity)?.supportActionBar?.title = title
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        _binding?.webView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        _binding?.webView?.onPause()
    }

    override fun onDestroyView() {
        _binding?.webView?.apply {
            stopLoading()
            // 使用默认实例替换，避免 null 安全错误，同时断开与当前 Fragment 的引用
            webViewClient = WebViewClient()
            webChromeClient = WebChromeClient()
            loadDataWithBaseURL(null, "", "text/html", "utf-8", null)
            clearHistory()
            (parent as? android.view.ViewGroup)?.removeView(this)
            destroy()
        }
        _binding = null
        super.onDestroyView()
    }
}
