package com.example.data.repository

import com.example.data.model.BlockedWordEntity
import com.example.data.model.ChannelEntity
import com.example.data.model.VideoEntity

object CuratedSeedData {

    val initialChannels = listOf(
        ChannelEntity(
            sourceId = "UC_qs3c0ehDvZkbiEbOj6Drg",
            title = "Alphablocks",
            thumbnail = "https://yt3.ggpht.com/4hW2cvLDW8yX5v3O_PzzyfHhQ0MCv_URg_OFOdElQ0jJ-BmiS0zXMUwikr-D1WMkC2taQO-S=s88-c-k-c0x00ffffff-no-rj-mo",
            categories = "education,stories,cartoons",
            isPreloaded = true,
            enabled = true
        ),
        ChannelEntity(
            sourceId = "UCPlwvN0w4qFSP1FllALB92w",
            title = "Numberblocks",
            thumbnail = "https://yt3.ggpht.com/lhZzvbA1gptFuwFBPKJBsyCS7eksCbOWdX0iLNZeUygKGysu6veWmSGFVNJTGd9qfvYXoPTJoIw=s88-c-k-c0x00ffffff-no-rj-mo",
            categories = "education,cartoons",
            isPreloaded = true,
            enabled = true
        ),
        ChannelEntity(
            sourceId = "UCtlcIZVBdFPSAtCoNZsTusg",
            title = "تعلم مع زكريا - Learn with Zakaria",
            thumbnail = "https://yt3.googleusercontent.com/ytc/AIdro_m53hyD-iEwEjLfQB5xKyRqmyNPUVvfJV1XI9JLztgpDdc=s88-c-k-c0x00ffffff-no-rj-mo",
            categories = "education,quran,stories",
            isPreloaded = true,
            enabled = true
        ),
        ChannelEntity(
            sourceId = "UC6IHxUdmpNoFQJkJRA77UpA",
            title = "هدهد - Hudhud",
            thumbnail = "https://yt3.ggpht.com/UdlE83XYN7xBlDzBohZ7y_QVagAvUgg8eSOGgcNZuikCQwvpvjytkydizQTmEKpFsng3_LbBig=s88-c-k-c0x00ffffff-no-rj-mo",
            categories = "quran,stories,cartoons,calm",
            isPreloaded = true,
            enabled = true
        ),
        ChannelEntity(
            sourceId = "UCEzDlTbKkx5rqDZJp01qHcQ",
            title = "قناة ريحانة للأطفال - Rayhana",
            thumbnail = "https://yt3.googleusercontent.com/oN3fr7CWUVFBLfbS9Y-3ECZNoECT1txiQQZ2X15DLEXnFdwDBxlx6VkR1K8CmOaNp-gqwdDLs0M=s88-c-k-c0x00ffffff-no-rj-mo",
            categories = "quran,calm,stories",
            isPreloaded = true,
            enabled = true
        ),
        ChannelEntity(
            sourceId = "UCazFScO30FKY3YoNNDfNY5g",
            title = "Arabian Fairy Tales - حكايات عربية",
            thumbnail = "https://yt3.googleusercontent.com/ytc/AIdro_lM_-zvamn9foYGWs_sKC1sjmMBEk6nOhsC4ZSHhg-GbPI=s88-c-k-c0x00ffffff-no-rj-mo",
            categories = "stories,calm",
            isPreloaded = true,
            enabled = true
        ),
        ChannelEntity(
            sourceId = "UCWIdqSQekeGmUWlSFeCiEnA",
            title = "قناة أسرتنا - Osratouna tv",
            thumbnail = "https://yt3.ggpht.com/e1j5eJXs-YInZyzWYahj0If0BgssXo_rxZ3WO2Yy2hxsBoQT658f1drvZuoy6t6dJl6eA1w9=s88-c-k-c0x00ffffff-no-rj-mo",
            categories = "cartoons,stories,education,cooking",
            isPreloaded = true,
            enabled = true
        ),
        ChannelEntity(
            sourceId = "UCuYZByF1E-jO6iYEL1ClJvA",
            title = "قناة كرزة - Karazah",
            thumbnail = "https://yt3.ggpht.com/ytc/AIdro_kZV9zndccMPjsERAJWTk28cVu_DVDCWpMlwF39XtLbPg=s88-c-k-c0x00ffffff-no-rj-mo",
            categories = "cartoons,education,calm",
            isPreloaded = true,
            enabled = true
        ),
        ChannelEntity(
            sourceId = "UC5XMF3Inoi8R9nSI8ChOsdQ",
            title = "Art for Kids Hub",
            thumbnail = "https://yt3.ggpht.com/2v2xIGZEuGhqBC5wVyqgal0UMtyClFzVVOw7brPTSPtR_Sii_k1BehSxI-e5X_FiKu7nkF36IR4=s88-c-k-c0x00ffffff-no-rj-mo",
            categories = "crafts",
            isPreloaded = true,
            enabled = true
        ),
        ChannelEntity(
            sourceId = "UCFBZOFHWS6KzfUhPAEy1gAQ",
            title = "تعلم الرسم والتلوين للأطفال",
            thumbnail = "https://yt3.ggpht.com/mtNGgDw9boV7Xc2YVaFIOHoh9qQO9eIstTNUE2dey-Y21-FigHakVoSdSiEMEVVl1XJYHKYGjA=s88-c-k-c0x00ffffff-no-rj-mo",
            categories = "crafts",
            isPreloaded = true,
            enabled = true
        ),
        ChannelEntity(
            sourceId = "UC5uIZ2KOZZeQDQo_Gsi_qbQ",
            title = "Cosmic Kids Yoga",
            thumbnail = "https://yt3.ggpht.com/Yc9p76OfTXkdYN5f0iGJqpfmgTZxZkmwvwmL6S7s63QA8UTuBFyBfjL6xYpsX-XdGiKqxfGWkQ=s88-c-k-c0x00ffffff-no-rj-mo",
            categories = "sports,stories",
            isPreloaded = true,
            enabled = true
        ),
        ChannelEntity(
            sourceId = "UC0Ik25PHaiHCbfGrzu-lBFQ",
            title = "All Attack Kids Sports",
            thumbnail = "https://yt3.googleusercontent.com/ytc/AIdro_kmqQt8yOlN5A8DFkGdnedC6CuZQwH5eG9Bum4lFMafYoI=s88-c-k-c0x00ffffff-no-rj-mo",
            categories = "sports",
            isPreloaded = true,
            enabled = true
        ),
        ChannelEntity(
            sourceId = "UCONtPx56PSebXJOxbFv-2jQ",
            title = "Crash Course Kids - علوم الصغار",
            thumbnail = "https://yt3.ggpht.com/mDXMqPIcDc_yfAqXGvBu0ufz7J3AJVlcstUxXs_y1yh34pf34wDy96qKGM4HEE_l_fzNeqpP=s88-c-k-c0x00ffffff-no-rj-mo",
            categories = "science",
            isPreloaded = true,
            enabled = true
        ),
        ChannelEntity(
            sourceId = "UCXVCgDuD_QCkI7gTKU7-tpg",
            title = "National Geographic Kids",
            thumbnail = "https://yt3.ggpht.com/GsBWcfJJbCKMQ-ndyaeLPqIwfqvU2dughQEl0eGRP8MfvML-HIl5HK_vamdjOWzbvMSLdLINGA=s88-c-k-c0x00ffffff-no-rj-mo",
            categories = "science",
            isPreloaded = true,
            enabled = true
        ),
        ChannelEntity(
            sourceId = "UC1W4T2HfKVCJM4ICOjzxoBg",
            title = "Kids Cooking and Crafts",
            thumbnail = "https://yt3.googleusercontent.com/ytc/AIdro_ne9xb7f9tW7YwvCFdQB-Q_PrE3Gd4eV4PJa3q4x6TjY3I=s88-c-k-c0x00ffffff-no-rj-mo",
            categories = "cooking,crafts",
            isPreloaded = true,
            enabled = true
        ),
        ChannelEntity(
            sourceId = "UCBx2mmZhRBkPC2MK4bqcSHQ",
            title = "Puffin Rock - صخرة البفن الهادئة",
            thumbnail = "https://yt3.ggpht.com/ePxZdelGH9UpfOgxN9h2lpYEiceoMcOGVggH1xbr4WvTFiuu3WSTPH5g32IMQCZD2vUcOHwQow=s88-c-k-c0x00ffffff-no-rj-mo",
            categories = "calm,stories",
            isPreloaded = true,
            enabled = true
        ),
        ChannelEntity(
            sourceId = "UC2qgn2PMJZYPWbJNQkQQU8A",
            title = "ألعاب بناء إبداعية هادئة للأطفال",
            thumbnail = "https://yt3.ggpht.com/uf-Bn3p_3olaLxSbYnIF2R2SXSkyNP8CojKfhdygKcj6Cg5_ME2nUXS6-Jh8maqNa4T9O3kV8Q=s88-c-k-c0x00ffffff-no-rj-mo",
            categories = "gaming",
            isPreloaded = true,
            enabled = true
        )
    )

    val initialVideos = listOf(
        // Education & Alphablocks
        VideoEntity(
            videoId = "s6X_Q54_PBs",
            channelId = "UC_qs3c0ehDvZkbiEbOj6Drg",
            title = "Alphablocks - مغامرة الحروف الإنجليزية والكلمات السحرية للأطفال",
            thumbnail = "https://i.ytimg.com/vi/s6X_Q54_PBs/hqdefault.jpg",
            durationSeconds = 620,
            hasMusic = true,
            viewCountText = "2.4 مليون مشاهدة",
            primaryCategory = "education"
        ),
        VideoEntity(
            videoId = "u7e33WnUf0A",
            channelId = "UCPlwvN0w4qFSP1FllALB92w",
            title = "Numberblocks - أصدقاء الأرقام الممتعة وتعلم الحساب بطريقة مبسطة",
            thumbnail = "https://i.ytimg.com/vi/u7e33WnUf0A/hqdefault.jpg",
            durationSeconds = 850,
            hasMusic = true,
            viewCountText = "3.1 مليون مشاهدة",
            primaryCategory = "education"
        ),
        VideoEntity(
            videoId = "Yt89Bv2_1kM",
            channelId = "UCtlcIZVBdFPSAtCoNZsTusg",
            title = "تعلم مع زكريا - الحروف العربية الهجائية للأطفال مع الكلمات والأمثلة",
            thumbnail = "https://i.ytimg.com/vi/Yt89Bv2_1kM/hqdefault.jpg",
            durationSeconds = 480,
            hasMusic = false,
            viewCountText = "1.8 مليون مشاهدة",
            primaryCategory = "education"
        ),

        // Quran & Faith
        VideoEntity(
            videoId = "w5k9v6_Q7dA",
            channelId = "UC6IHxUdmpNoFQJkJRA77UpA",
            title = "هدهد - قصة نبي الله يونس عليه السلام والحوت العظيم بأسلوب مبسط",
            thumbnail = "https://i.ytimg.com/vi/w5k9v6_Q7dA/hqdefault.jpg",
            durationSeconds = 720,
            hasMusic = false,
            viewCountText = "950 ألف مشاهدة",
            primaryCategory = "quran"
        ),
        VideoEntity(
            videoId = "q8v2M9_L1pQ",
            channelId = "UCEzDlTbKkx5rqDZJp01qHcQ",
            title = "قناة ريحانة - أذكار الصباح والمساء للأطفال بصوت هادئ ومؤثر بدون موسيقى",
            thumbnail = "https://i.ytimg.com/vi/q8v2M9_L1pQ/hqdefault.jpg",
            durationSeconds = 420,
            hasMusic = false,
            viewCountText = "620 ألف مشاهدة",
            primaryCategory = "quran"
        ),
        VideoEntity(
            videoId = "k4N7p8_W3rX",
            channelId = "UCtlcIZVBdFPSAtCoNZsTusg",
            title = "تعلم مع زكريا - قصار السور من جزء عم مع الترديد للأطفال الصغار",
            thumbnail = "https://i.ytimg.com/vi/k4N7p8_W3rX/hqdefault.jpg",
            durationSeconds = 960,
            hasMusic = false,
            viewCountText = "1.2 مليون مشاهدة",
            primaryCategory = "quran"
        ),

        // Stories
        VideoEntity(
            videoId = "w_gWvL8fN8g",
            channelId = "UCazFScO30FKY3YoNNDfNY5g",
            title = "Arabian Fairy Tales - حكاية الشجرة الحكيمة والطيور الملونة",
            thumbnail = "https://i.ytimg.com/vi/w_gWvL8fN8g/hqdefault.jpg",
            durationSeconds = 640,
            hasMusic = false,
            viewCountText = "850 ألف مشاهدة",
            primaryCategory = "stories"
        ),
        VideoEntity(
            videoId = "m8P3k2_V5jL",
            channelId = "UCWIdqSQekeGmUWlSFeCiEnA",
            title = "أسرتنا - حكاية الصدق والأمانة في الغابة الجميلة",
            thumbnail = "https://i.ytimg.com/vi/m8P3k2_V5jL/hqdefault.jpg",
            durationSeconds = 540,
            hasMusic = false,
            viewCountText = "1.1 مليون مشاهدة",
            primaryCategory = "stories"
        ),
        VideoEntity(
            videoId = "p2L5x7_B9nQ",
            channelId = "UCBx2mmZhRBkPC2MK4bqcSHQ",
            title = "Puffin Rock - مغامرة أونا وبابا في حماية البيض الصغير",
            thumbnail = "https://i.ytimg.com/vi/p2L5x7_B9nQ/hqdefault.jpg",
            durationSeconds = 660,
            hasMusic = false,
            viewCountText = "780 ألف مشاهدة",
            primaryCategory = "stories"
        ),

        // Cartoons & Songs
        VideoEntity(
            videoId = "c3K7v2_M5nL",
            channelId = "UCuYZByF1E-jO6iYEL1ClJvA",
            title = "قناة كرزة - أنشودة الفواكه والخضروات اللذيذة للأطفال الأبطال",
            thumbnail = "https://i.ytimg.com/vi/c3K7v2_M5nL/hqdefault.jpg",
            durationSeconds = 340,
            hasMusic = true,
            viewCountText = "2.8 مليون مشاهدة",
            primaryCategory = "cartoons"
        ),
        VideoEntity(
            videoId = "t8N2v4_K9pW",
            channelId = "UCWIdqSQekeGmUWlSFeCiEnA",
            title = "أسرتنا - مغامرات أفراد العائلة في تنظيف وترتيب البيت",
            thumbnail = "https://i.ytimg.com/vi/t8N2v4_K9pW/hqdefault.jpg",
            durationSeconds = 480,
            hasMusic = true,
            viewCountText = "1.5 مليون مشاهدة",
            primaryCategory = "cartoons"
        ),

        // Science
        VideoEntity(
            videoId = "b9V4m7_K2xP",
            channelId = "UCONtPx56PSebXJOxbFv-2jQ",
            title = "Crash Course Kids - كيف تتكون السحب ولماذا تمطر السماء؟",
            thumbnail = "https://i.ytimg.com/vi/b9V4m7_K2xP/hqdefault.jpg",
            durationSeconds = 410,
            hasMusic = false,
            viewCountText = "450 ألف مشاهدة",
            primaryCategory = "science"
        ),
        VideoEntity(
            videoId = "n3P8k4_V6tL",
            channelId = "UCXVCgDuD_QCkI7gTKU7-tpg",
            title = "National Geographic Kids - استكشاف حياة الدلافين الذكية في المحيط",
            thumbnail = "https://i.ytimg.com/vi/n3P8k4_V6tL/hqdefault.jpg",
            durationSeconds = 580,
            hasMusic = false,
            viewCountText = "1.9 مليون مشاهدة",
            primaryCategory = "science"
        ),

        // Crafts
        VideoEntity(
            videoId = "x1rB6E1oTss",
            channelId = "UC5XMF3Inoi8R9nSI8ChOsdQ",
            title = "Art for Kids Hub - تعلم رسم وتلوين الحيوانات خطوة بخطوة بالريشة",
            thumbnail = "https://i.ytimg.com/vi/x1rB6E1oTss/hqdefault.jpg",
            durationSeconds = 710,
            hasMusic = false,
            viewCountText = "1.3 مليون مشاهدة",
            primaryCategory = "crafts"
        ),
        VideoEntity(
            videoId = "UeF09e7hDbg",
            channelId = "UC57XAjJ04TY8gNxOWf-Sy0Q",
            title = "أفكار أشغال يدوية وابتكارات بالكرتون والورق الملون للأطفال",
            thumbnail = "https://i.ytimg.com/vi/UeF09e7hDbg/hqdefault.jpg",
            durationSeconds = 520,
            hasMusic = false,
            viewCountText = "820 ألف مشاهدة",
            primaryCategory = "crafts"
        ),

        // Sports
        VideoEntity(
            videoId = "02E1468SdHg",
            channelId = "UC5uIZ2KOZZeQDQo_Gsi_qbQ",
            title = "Cosmic Kids Yoga - مغامرة الحركة واليوغا والنشاط الصحي للصغار",
            thumbnail = "https://i.ytimg.com/vi/02E1468SdHg/hqdefault.jpg",
            durationSeconds = 880,
            hasMusic = true,
            viewCountText = "2.1 مليون مشاهدة",
            primaryCategory = "sports"
        ),
        VideoEntity(
            videoId = "tbCjkPlsaes",
            channelId = "UC0Ik25PHaiHCbfGrzu-lBFQ",
            title = "AllAttack - مهارات وتحديات رياضية ممتعة وتشجيعية للأبطال الصغار",
            thumbnail = "https://i.ytimg.com/vi/tbCjkPlsaes/hqdefault.jpg",
            durationSeconds = 490,
            hasMusic = true,
            viewCountText = "540 ألف مشاهدة",
            primaryCategory = "sports"
        ),

        // Cooking
        VideoEntity(
            videoId = "k5M2v8_R4pX",
            channelId = "UC1W4T2HfKVCJM4ICOjzxoBg",
            title = "Kids Cooking - طريقة صنع كوكيز الشوفان والعسل الصحية واللذيذة",
            thumbnail = "https://i.ytimg.com/vi/k5M2v8_R4pX/hqdefault.jpg",
            durationSeconds = 460,
            hasMusic = false,
            viewCountText = "390 ألف مشاهدة",
            primaryCategory = "cooking"
        ),

        // Calm
        VideoEntity(
            videoId = "z7N3p5_V8kL",
            channelId = "UCBx2mmZhRBkPC2MK4bqcSHQ",
            title = "أصوات الطبيعة الهادئة وقصص النوم المريحة للأطفال",
            thumbnail = "https://i.ytimg.com/vi/z7N3p5_V8kL/hqdefault.jpg",
            durationSeconds = 1200,
            hasMusic = false,
            viewCountText = "1.4 مليون مشاهدة",
            primaryCategory = "calm"
        ),

        // Gaming (Opt-in)
        VideoEntity(
            videoId = "g4L8p2_M6xT",
            channelId = "UC2qgn2PMJZYPWbJNQkQQU8A",
            title = "بناء قلعة ملونة وألعاب ذكاء ممتعة وهادئة للأطفال",
            thumbnail = "https://i.ytimg.com/vi/g4L8p2_M6xT/hqdefault.jpg",
            durationSeconds = 740,
            hasMusic = false,
            viewCountText = "680 ألف مشاهدة",
            primaryCategory = "gaming"
        )
    )

    val initialBlockedWords = listOf(
        BlockedWordEntity("رعب"),
        BlockedWordEntity("مقلب"),
        BlockedWordEntity("شبح"),
        BlockedWordEntity("مخيف"),
        BlockedWordEntity("تحدي الموت")
    )
}
