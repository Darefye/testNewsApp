package com.example.newsapp.presentation.ui.details_news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentDetailNewsBinding
import com.example.newsapp.databinding.FragmentNewsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailNewsFragment : Fragment() {

    lateinit var binding: FragmentDetailNewsBinding
    private val bundleArgs: DetailNewsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val articleArg = bundleArgs.article

        articleArg.let { article ->
            binding.apply {
                detailDescription.text = article.description
                detailAuthor.text = article.author
                detailTitle.text = article.title
                Glide.with(requireContext()).load(article.urlToImage).into(PreviewNewsDetail)
            }
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() = DetailNewsFragment()
    }
}