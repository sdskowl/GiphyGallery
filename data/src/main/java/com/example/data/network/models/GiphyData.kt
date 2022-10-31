package com.example.data.network.models

import com.google.gson.annotations.SerializedName


data class GiphyData(
    @field:SerializedName("data")
    val data: List<Data?>? = null,
    @field:SerializedName("meta")
    val meta: Meta? = null,
    @field:SerializedName("pagination")
    val pagination: Pagination? = null
) {
    data class Data(
        @field:SerializedName("analytics")
        val analytics: Analytics? = null,
        @field:SerializedName("analytics_response_payload")
        val analyticsResponsePayload: String? = null,
        @field:SerializedName("bitly_gif_url")
        val bitlyGifUrl: String? = null,
        @field:SerializedName("bitly_url")
        val bitlyUrl: String? = null,
        @field:SerializedName("content_url")
        val contentUrl: String? = null,
        @field:SerializedName("embed_url")
        val embedUrl: String? = null,
        @field:SerializedName("id")
        val id: String? = null,
        @field:SerializedName("images")
        val images: Images? = null,
        @field:SerializedName("import_datetime")
        val importDatetime: String? = null,
        @field:SerializedName("is_sticker")
        val isSticker: Int? = null,
        @field:SerializedName("rating")
        val rating: String? = null,
        @field:SerializedName("slug")
        val slug: String? = null,
        @field:SerializedName("source")
        val source: String? = null,
        @field:SerializedName("source_post_url")
        val sourcePostUrl: String? = null,
        @field:SerializedName("source_tld")
        val sourceTld: String? = null,
        @field:SerializedName("title")
        val title: String? = null,
        @field:SerializedName("trending_datetime")
        val trendingDatetime: String? = null,
        @field:SerializedName("type")
        val type: String? = null,
        @field:SerializedName("url")
        val url: String? = null,
        @field:SerializedName("user")
        val user: User? = null,
        @field:SerializedName("username")
        val username: String? = null
    ) {
        data class Analytics(
            @field:SerializedName("onclick")
            val onclick: Onclick? = null,
            @field:SerializedName("onload")
            val onload: Onload? = null,
            @field:SerializedName("onsent")
            val onsent: Onsent? = null
        ) {
            data class Onclick(
                @field:SerializedName("url")
                val url: String? = null
            )

            data class Onload(
                @field:SerializedName("url")
                val url: String? = null
            )

            data class Onsent(
                @field:SerializedName("url")
                val url: String? = null
            )
        }

        data class Images(
            @field:SerializedName("downsized")
            val downsized: Downsized? = null,
            @field:SerializedName("downsized_large")
            val downsizedLarge: DownsizedLarge? = null,
            @field:SerializedName("downsized_medium")
            val downsizedMedium: DownsizedMedium? = null,
            @field:SerializedName("downsized_small")
            val downsizedSmall: DownsizedSmall? = null,
            @field:SerializedName("downsized_still")
            val downsizedStill: DownsizedStill? = null,
            @field:SerializedName("fixed_height")
            val fixedHeight: FixedHeight? = null,
            @field:SerializedName("fixed_height_downsampled")
            val fixedHeightDownsampled: FixedHeightDownsampled? = null,
            @field:SerializedName("fixed_height_small")
            val fixedHeightSmall: FixedHeightSmall? = null,
            @field:SerializedName("fixed_height_small_still")
            val fixedHeightSmallStill: FixedHeightSmallStill? = null,
            @field:SerializedName("fixed_height_still")
            val fixedHeightStill: FixedHeightStill? = null,
            @field:SerializedName("fixed_width")
            val fixedWidth: FixedWidth? = null,
            @field:SerializedName("fixed_width_downsampled")
            val fixedWidthDownsampled: FixedWidthDownsampled? = null,
            @field:SerializedName("fixed_width_small")
            val fixedWidthSmall: FixedWidthSmall? = null,
            @field:SerializedName("fixed_width_small_still")
            val fixedWidthSmallStill: FixedWidthSmallStill? = null,
            @field:SerializedName("fixed_width_still")
            val fixedWidthStill: FixedWidthStill? = null,
            @field:SerializedName("hd")
            val hd: Hd? = null,
            @field:SerializedName("looping")
            val looping: Looping? = null,
            @field:SerializedName("original")
            val original: Original? = null,
            @field:SerializedName("original_mp4")
            val originalMp4: OriginalMp4? = null,
            @field:SerializedName("original_still")
            val originalStill: OriginalStill? = null,
            @field:SerializedName("preview")
            val preview: Preview? = null,
            @field:SerializedName("preview_gif")
            val previewGif: PreviewGif? = null,
            @field:SerializedName("preview_webp")
            val previewWebp: PreviewWebp? = null,
            @field:SerializedName("480w_still")
            val wStill: WStill? = null
        ) {
            data class Downsized(
                @field:SerializedName("height")
                val height: String? = null,
                @field:SerializedName("size")
                val size: String? = null,
                @field:SerializedName("url")
                val url: String? = null,
                @field:SerializedName("width")
                val width: String? = null
            )

            data class DownsizedLarge(
                @field:SerializedName("height")
                val height: String? = null,
                @field:SerializedName("size")
                val size: String? = null,
                @field:SerializedName("url")
                val url: String? = null,
                @field:SerializedName("width")
                val width: String? = null
            )

            data class DownsizedMedium(
                @field:SerializedName("height")
                val height: String? = null,
                @field:SerializedName("size")
                val size: String? = null,
                @field:SerializedName("url")
                val url: String? = null,
                @field:SerializedName("width")
                val width: String? = null
            )

            data class DownsizedSmall(
                @field:SerializedName("height")
                val height: String? = null,
                @field:SerializedName("mp4")
                val mp4: String? = null,
                @field:SerializedName("mp4_size")
                val mp4Size: String? = null,
                @field:SerializedName("width")
                val width: String? = null
            )

            data class DownsizedStill(
                @field:SerializedName("height")
                val height: String? = null,
                @field:SerializedName("size")
                val size: String? = null,
                @field:SerializedName("url")
                val url: String? = null,
                @field:SerializedName("width")
                val width: String? = null
            )

            data class FixedHeight(
                @field:SerializedName("height")
                val height: String? = null,
                @field:SerializedName("mp4")
                val mp4: String? = null,
                @field:SerializedName("mp4_size")
                val mp4Size: String? = null,
                @field:SerializedName("size")
                val size: String? = null,
                @field:SerializedName("url")
                val url: String? = null,
                @field:SerializedName("webp")
                val webp: String? = null,
                @field:SerializedName("webp_size")
                val webpSize: String? = null,
                @field:SerializedName("width")
                val width: String? = null
            )

            data class FixedHeightDownsampled(
                @field:SerializedName("height")
                val height: String? = null,
                @field:SerializedName("size")
                val size: String? = null,
                @field:SerializedName("url")
                val url: String? = null,
                @field:SerializedName("webp")
                val webp: String? = null,
                @field:SerializedName("webp_size")
                val webpSize: String? = null,
                @field:SerializedName("width")
                val width: String? = null
            )

            data class FixedHeightSmall(
                @field:SerializedName("height")
                val height: String? = null,
                @field:SerializedName("mp4")
                val mp4: String? = null,
                @field:SerializedName("mp4_size")
                val mp4Size: String? = null,
                @field:SerializedName("size")
                val size: String? = null,
                @field:SerializedName("url")
                val url: String? = null,
                @field:SerializedName("webp")
                val webp: String? = null,
                @field:SerializedName("webp_size")
                val webpSize: String? = null,
                @field:SerializedName("width")
                val width: String? = null
            )

            data class FixedHeightSmallStill(
                @field:SerializedName("height")
                val height: String? = null,
                @field:SerializedName("size")
                val size: String? = null,
                @field:SerializedName("url")
                val url: String? = null,
                @field:SerializedName("width")
                val width: String? = null
            )

            data class FixedHeightStill(
                @field:SerializedName("height")
                val height: String? = null,
                @field:SerializedName("size")
                val size: String? = null,
                @field:SerializedName("url")
                val url: String? = null,
                @field:SerializedName("width")
                val width: String? = null
            )

            data class FixedWidth(
                @field:SerializedName("height")
                val height: String? = null,
                @field:SerializedName("mp4")
                val mp4: String? = null,
                @field:SerializedName("mp4_size")
                val mp4Size: String? = null,
                @field:SerializedName("size")
                val size: String? = null,
                @field:SerializedName("url")
                val url: String? = null,
                @field:SerializedName("webp")
                val webp: String? = null,
                @field:SerializedName("webp_size")
                val webpSize: String? = null,
                @field:SerializedName("width")
                val width: String? = null
            )

            data class FixedWidthDownsampled(
                @field:SerializedName("height")
                val height: String? = null,
                @field:SerializedName("size")
                val size: String? = null,
                @field:SerializedName("url")
                val url: String? = null,
                @field:SerializedName("webp")
                val webp: String? = null,
                @field:SerializedName("webp_size")
                val webpSize: String? = null,
                @field:SerializedName("width")
                val width: String? = null
            )

            data class FixedWidthSmall(
                @field:SerializedName("height")
                val height: String? = null,
                @field:SerializedName("mp4")
                val mp4: String? = null,
                @field:SerializedName("mp4_size")
                val mp4Size: String? = null,
                @field:SerializedName("size")
                val size: String? = null,
                @field:SerializedName("url")
                val url: String? = null,
                @field:SerializedName("webp")
                val webp: String? = null,
                @field:SerializedName("webp_size")
                val webpSize: String? = null,
                @field:SerializedName("width")
                val width: String? = null
            )

            data class FixedWidthSmallStill(
                @field:SerializedName("height")
                val height: String? = null,
                @field:SerializedName("size")
                val size: String? = null,
                @field:SerializedName("url")
                val url: String? = null,
                @field:SerializedName("width")
                val width: String? = null
            )

            data class FixedWidthStill(
                @field:SerializedName("height")
                val height: String? = null,
                @field:SerializedName("size")
                val size: String? = null,
                @field:SerializedName("url")
                val url: String? = null,
                @field:SerializedName("width")
                val width: String? = null
            )

            data class Hd(
                @field:SerializedName("height")
                val height: String? = null,
                @field:SerializedName("mp4")
                val mp4: String? = null,
                @field:SerializedName("mp4_size")
                val mp4Size: String? = null,
                @field:SerializedName("width")
                val width: String? = null
            )

            data class Looping(
                @field:SerializedName("mp4")
                val mp4: String? = null,
                @field:SerializedName("mp4_size")
                val mp4Size: String? = null
            )

            data class Original(
                @field:SerializedName("frames")
                val frames: String? = null,
                @field:SerializedName("hash")
                val hash: String? = null,
                @field:SerializedName("height")
                val height: String? = null,
                @field:SerializedName("mp4")
                val mp4: String? = null,
                @field:SerializedName("mp4_size")
                val mp4Size: String? = null,
                @field:SerializedName("size")
                val size: String? = null,
                @field:SerializedName("url")
                val url: String? = null,
                @field:SerializedName("webp")
                val webp: String? = null,
                @field:SerializedName("webp_size")
                val webpSize: String? = null,
                @field:SerializedName("width")
                val width: String? = null
            )

            data class OriginalMp4(
                @field:SerializedName("height")
                val height: String? = null,
                @field:SerializedName("mp4")
                val mp4: String? = null,
                @field:SerializedName("mp4_size")
                val mp4Size: String? = null,
                @field:SerializedName("width")
                val width: String? = null
            )

            data class OriginalStill(
                @field:SerializedName("height")
                val height: String? = null,
                @field:SerializedName("size")
                val size: String? = null,
                @field:SerializedName("url")
                val url: String? = null,
                @field:SerializedName("width")
                val width: String? = null
            )

            data class Preview(
                @field:SerializedName("height")
                val height: String? = null,
                @field:SerializedName("mp4")
                val mp4: String? = null,
                @field:SerializedName("mp4_size")
                val mp4Size: String? = null,
                @field:SerializedName("width")
                val width: String? = null
            )

            data class PreviewGif(
                @field:SerializedName("height")
                val height: String? = null,
                @field:SerializedName("size")
                val size: String? = null,
                @field:SerializedName("url")
                val url: String? = null,
                @field:SerializedName("width")
                val width: String? = null
            )

            data class PreviewWebp(
                @field:SerializedName("height")
                val height: String? = null,
                @field:SerializedName("size")
                val size: String? = null,
                @field:SerializedName("url")
                val url: String? = null,
                @field:SerializedName("width")
                val width: String? = null
            )

            data class WStill(
                @field:SerializedName("height")
                val height: String? = null,
                @field:SerializedName("size")
                val size: String? = null,
                @field:SerializedName("url")
                val url: String? = null,
                @field:SerializedName("width")
                val width: String? = null
            )
        }

        data class User(
            @field:SerializedName("avatar_url")
            val avatarUrl: String? = null,
            @field:SerializedName("banner_image")
            val bannerImage: String? = null,
            @field:SerializedName("banner_url")
            val bannerUrl: String? = null,
            @field:SerializedName("description")
            val description: String? = null,
            @field:SerializedName("display_name")
            val displayName: String? = null,
            @field:SerializedName("instagram_url")
            val instagramUrl: String? = null,
            @field:SerializedName("is_verified")
            val isVerified: Boolean? = null,
            @field:SerializedName("profile_url")
            val profileUrl: String? = null,
            @field:SerializedName("username")
            val username: String? = null,
            @field:SerializedName("website_url")
            val websiteUrl: String? = null
        )
    }

    data class Meta(
        @field:SerializedName("msg")
        val msg: String? = null,
        @field:SerializedName("response_id")
        val responseId: String? = null,
        @field:SerializedName("status")
        val status: Int? = null
    )

    data class Pagination(
        @field:SerializedName("count")
        val count: Int? = null,
        @field:SerializedName("offset")
        val offset: Int? = null,
        @field:SerializedName("total_count")
        val totalCount: Int? = null
    )
}