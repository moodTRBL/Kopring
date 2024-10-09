package com.kopring.learning

import io.kotest.core.spec.style.BehaviorSpec
import org.jsoup.Jsoup

class JsoupLearningTest(
) : BehaviorSpec({
    Given("dd") {
        //val opt = "?--disable-blink-features=AutomationControlled"
        val url =
            "https://www.coupang.com/vp/products/7951307334?itemId=21947497386&vendorItemId=89468540615&q=%EB%9D%BC%EC%A6%88%EB%B2%A0%EB%A6%AC+%ED%8C%8C%EC%9D%B4&itemsCount=36&searchId=296e6c2412e5492eb286b361f9820914&rank=1&isAddedCart="
        val doc = Jsoup.connect(url)
            .header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7'")
            .timeout(30000).get()
        //println(doc.toString())
        val title = doc.select(
                    ".prod-buy-header " +
                    ".prod-buy-header__title"
        )
        val originPrice = doc.select(
            ".prod-origin-price " +
            ".origin-price"
        )
        val totalPrice = doc.select(
            ".prod-price-onetime " +
                    ".total-price"
        )
        println(title.text())
        //쿠팡 상품 크롤링시 할인가격과 원래가격이 같으면 원래 가격은 빈문자열이 반환됨
        println(originPrice.text())
        //특가 세일이 진행중이라면 할인된 가격과 와우 가격이 모두 반환됨
        val prices = totalPrice.text()
            .split(" ")
            .toList()
        println(prices)
    }
})