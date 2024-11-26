package com.dasc.nexus.ui.components.common

import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import com.dasc.nexus.data.models.SubjectEntity
import com.google.accompanist.pager.ExperimentalPagerApi

@OptIn(ExperimentalPagerApi::class)
@Composable
fun SubjectCarousel(subjects: List<SubjectEntity>) {
    val pagerState = rememberPagerState(pageCount = { subjects.size })

    HorizontalPager(
        state = pagerState,
    ) { page ->
        SubjectCard(subject = subjects[page]){

        }
    }
}