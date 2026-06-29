package com.example.testcity.core.common

object CountryNameMapper {
    private val names = mapOf(
        "CN" to "Китай",
        "RU" to "Россия",
        "US" to "США",
        "GB" to "Великобритания",
        "DE" to "Германия",
        "FR" to "Франция",
        "JP" to "Япония",
        "IN" to "Индия",
        "BR" to "Бразилия",
        "AU" to "Австралия",
        "CA" to "Канада",
        "IT" to "Италия",
        "ES" to "Испания",
        "KR" to "Южная Корея",
        "MX" to "Мексика",
        "TR" to "Турция",
        "UA" to "Украина",
        "PL" to "Польша",
        "NL" to "Нидерланды",
        "SE" to "Швеция",
    )

    fun map(countryCode: String): String = names[countryCode.uppercase()] ?: countryCode
}
