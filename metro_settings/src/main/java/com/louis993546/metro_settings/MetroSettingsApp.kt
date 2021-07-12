package com.louis993546.metro_settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.louis993546.metro.ApplicationBar
import com.louis993546.metro.LocalTextOnBackgroundColor
import com.louis993546.metro.LocalTextOnButtonColor
import com.louis993546.metro.Text
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * TODO extract these into metro lib
 */
@ExperimentalPagerApi
@Composable
fun MetroSettingsApp(
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = "METRO SETTINGS",
            size = 20.sp,
            modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp),
        )

        val scope = rememberCoroutineScope()
        val scope2 = rememberCoroutineScope()

        val listState = rememberLazyListState()
        val pagerState = rememberPagerState(pageCount = 2)
        // TODO connect scrollState and pagerState together somehow

        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.currentPage }.collect {
                listState.animateScrollToItem(it)
            }
        }

        LazyRow(
            modifier = Modifier.padding(top = 8.dp),
            state = listState,
            contentPadding = PaddingValues(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.Start),
        ) {
            itemsIndexed(
                listOf("about", "open-source licenses"),
                key = { _, item -> item }
            ) { index, title ->
                Text(
                    text = title,
                    size = 47.sp,
                    weight = FontWeight.Light,
                    color = if (pagerState.currentPage == index) LocalTextOnBackgroundColor.current
                    else LocalTextOnBackgroundColor.current.copy(alpha = 0.4f),
                    modifier = Modifier.clickable {
                        // 2 scopes to make sure the animations won't wait for one another
                        scope.launch {
                            listState.animateScrollToItem(index)
                        }
                        scope2.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                )
            }
        }
        HorizontalPager(
            modifier = Modifier.weight(1f),
            state = pagerState,
        ) { page ->
            when (page) {
                0 -> AboutUs(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
                1 -> OpenSourceLicenses(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }
        }
        ApplicationBar(
            modifier = Modifier.fillMaxWidth(),
            count = 1,
            icon = {
                Image(
                    // TODO this icon looks terrible in center of the circle
                    painter = painterResource(id = R.drawable.ic_baseline_share_24),
                    contentDescription = "Share",
                    colorFilter = ColorFilter.tint(color = LocalTextOnButtonColor.current)
                )
            }
        ) { TODO("on click") }
    }
}

@Composable
fun AboutUs(
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxHeight()) {
        Text(text = "Under Construction")
    }
}

@Composable
fun OpenSourceLicenses(
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxHeight()) {
        Text(text = "TODO Typography")
        Text(text = "TODO iirc there is some library to scrap all the licenses right?")
    }
}

data class Library(
    val name: String,
    val url: String?,
    val license: String?,
)

val libraries = listOf(
    Library(
        name = "Selawik",
        url = "https://github.com/Microsoft/Selawik",
        license = """
        Copyright 2015, Microsoft Corporation (www.microsoft.com), with Reserved Font Name Selawik.  All Rights Reserved.  Selawik is a trademark of Microsoft Corporation in the United States and/or other countries.

        This Font Software is licensed under the SIL Open Font License, Version 1.1.
        This license is copied below, and is also available with a FAQ at:
        http://scripts.sil.org/OFL

        -----------------------------------------------------------
        SIL OPEN FONT LICENSE Version 1.1 - 26 February 2007
        -----------------------------------------------------------
        PREAMBLE
        The goals of the Open Font License (OFL) are to stimulate worldwide
        development of collaborative font projects, to support the font creation
        efforts of academic and linguistic communities, and to provide a free and
        open framework in which fonts may be shared and improved in partnership
        with others.

        The OFL allows the licensed fonts to be used, studied, modified and
        redistributed freely as long as they are not sold by themselves. The
        fonts, including any derivative works, can be bundled, embedded, 
        redistributed and/or sold with any software provided that any reserved
        names are not used by derivative works. The fonts and derivatives,
        however, cannot be released under any other type of license. The
        requirement for fonts to remain under this license does not apply
        to any document created using the fonts or their derivatives.

        DEFINITIONS
        "Font Software" refers to the set of files released by the Copyright
        Holder(s) under this license and clearly marked as such. This may
        include source files, build scripts and documentation.

        "Reserved Font Name" refers to any names specified as such after the
        copyright statement(s).

        "Original Version" refers to the collection of Font Software components as
        distributed by the Copyright Holder(s).

        "Modified Version" refers to any derivative made by adding to, deleting,
        or substituting -- in part or in whole -- any of the components of the
        Original Version, by changing formats or by porting the Font Software to a
        new environment.

        "Author" refers to any designer, engineer, programmer, technical
        writer or other person who contributed to the Font Software.

        PERMISSION & CONDITIONS
        Permission is hereby granted, free of charge, to any person obtaining
        a copy of the Font Software, to use, study, copy, merge, embed, modify,
        redistribute, and sell modified and unmodified copies of the Font
        Software, subject to the following conditions:

        1) Neither the Font Software nor any of its individual components,
        in Original or Modified Versions, may be sold by itself.

        2) Original or Modified Versions of the Font Software may be bundled,
        redistributed and/or sold with any software, provided that each copy
        contains the above copyright notice and this license. These can be
        included either as stand-alone text files, human-readable headers or
        in the appropriate machine-readable metadata fields within text or
        binary files as long as those fields can be easily viewed by the user.

        3) No Modified Version of the Font Software may use the Reserved Font
        Name(s) unless explicit written permission is granted by the corresponding
        Copyright Holder. This restriction only applies to the primary font name as
        presented to the users.

        4) The name(s) of the Copyright Holder(s) or the Author(s) of the Font
        Software shall not be used to promote, endorse or advertise any
        Modified Version, except to acknowledge the contribution(s) of the
        Copyright Holder(s) and the Author(s) or with their explicit written
        permission.

        5) The Font Software, modified or unmodified, in part or in whole,
        must be distributed entirely under this license, and must not be
        distributed under any other license. The requirement for fonts to
        remain under this license does not apply to any document created
        using the Font Software.

        TERMINATION
        This license becomes null and void if any of the above conditions are
        not met.

        DISCLAIMER
        THE FONT SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
        EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO ANY WARRANTIES OF
        MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT
        OF COPYRIGHT, PATENT, TRADEMARK, OR OTHER RIGHT. IN NO EVENT SHALL THE
        COPYRIGHT HOLDER BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
        INCLUDING ANY GENERAL, SPECIAL, INDIRECT, INCIDENTAL, OR CONSEQUENTIAL
        DAMAGES, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
        FROM, OUT OF THE USE OR INABILITY TO USE THE FONT SOFTWARE OR FROM
        OTHER DEALINGS IN THE FONT SOFTWARE.

        """.trimIndent(),
    ),
)