package com.example.data.model

data class Category(
    val id: String,
    val label: String,
    val emoji: String,
    val description: String,
    val tags: List<String> = emptyList()
) {
    companion object {
        val ALL = Category("all", "الكل", "✨", "جميع الفيديوهات والبرامج المعتمدة")
        val QURAN = Category("quran", "قرآن وأذكار", "🕌", "تلاوات خاشعة وأذكار وقصص الأنبياء", listOf("faith", "quran", "islamic"))
        val STORIES = Category("stories", "قصص وحكايات", "📖", "قصص ممتعة ومغامرات هادفة للأطفال", listOf("stories", "reading"))
        val CARTOONS = Category("cartoons", "كرتون وأناشيد", "📺", "أناشيد كرتونية وبرامج رسوم متحركة مبهجة", listOf("shows", "songs"))
        val EDUCATION = Category("education", "تعليم ولغات", "💡", "حروف وأرقام وتعلم اللغات والمفاهيم", listOf("learn", "reading"))
        val SCIENCE = Category("science", "علوم واستكشاف", "🔬", "تجارب علمية واكتشاف العالم والطبيعة", listOf("learn", "science"))
        val CRAFTS = Category("crafts", "رسم وفنون", "🎨", "تعلم الرسم والتلوين والأشغال اليدوية", listOf("arts", "drawing"))
        val SPORTS = Category("sports", "حركة ورياضة", "⚽", "تمارين وألعاب حركية وتحديات للأبطال", listOf("sports", "active"))
        val GAMING = Category("gaming", "ألعاب مناسبة", "🎮", "ألعاب ذكاء ومرح عائلي مناسبة للأطفال", listOf("gaming"))
        val COOKING = Category("cooking", "طبخ الصغار", "🍳", "وصفات لذيذة وسهلة بمشاركة الأطفال", listOf("cooking"))
        val CALM = Category("calm", "هدوء ونوم", "🌙", "محتوى هادئ وموسيقى استرخاء وقصص النوم", listOf("calm"))

        val DEFAULT_CATEGORIES = listOf(
            ALL, QURAN, STORIES, CARTOONS, EDUCATION, SCIENCE, CRAFTS, SPORTS, GAMING, COOKING, CALM
        )
    }
}
