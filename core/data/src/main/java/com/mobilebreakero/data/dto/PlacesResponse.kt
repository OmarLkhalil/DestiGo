package com.mobilebreakero.data.dto

import com.google.gson.annotations.SerializedName

data class PlacesResponse(

	@field:SerializedName("html_attributions")
	val htmlAttributions: List<Any?>? = null,

	@field:SerializedName("status")
	val status: String,

	val page: Long,

	val totalPage: Int,

	@field:SerializedName("places")
	val places: List<PlacesItem>
)

data class DisplayName(

	@field:SerializedName("text")
	val text: String? = null,

	@field:SerializedName("languageCode")
	val languageCode: String? = null
)

data class High(

	@field:SerializedName("latitude")
	val latitude: Any? = null,

	@field:SerializedName("longitude")
	val longitude: Any? = null
)

data class AuthorAttributionsItem(

	@field:SerializedName("displayName")
	val displayName: String? = null,

	@field:SerializedName("photoUri")
	val photoUri: String? = null,

	@field:SerializedName("uri")
	val uri: String? = null
)

data class Viewport(

	@field:SerializedName("high")
	val high: High? = null,

	@field:SerializedName("low")
	val low: Low? = null
)

data class PaymentOptions(

	@field:SerializedName("acceptsCreditCards")
	val acceptsCreditCards: Boolean? = null,

	@field:SerializedName("acceptsDebitCards")
	val acceptsDebitCards: Boolean? = null,

	@field:SerializedName("acceptsCashOnly")
	val acceptsCashOnly: Boolean? = null
)

data class EditorialSummary(

	@field:SerializedName("text")
	val text: String? = null,

	@field:SerializedName("languageCode")
	val languageCode: String? = null
)

data class Text(

	@field:SerializedName("text")
	val text: String? = null,

	@field:SerializedName("languageCode")
	val languageCode: String? = null
)

data class Open(

	@field:SerializedName("hour")
	val hour: Int? = null,

	@field:SerializedName("day")
	val day: Int? = null,

	@field:SerializedName("minute")
	val minute: Int? = null,

	@field:SerializedName("date")
	val date: Date? = null,

	@field:SerializedName("truncated")
	val truncated: Boolean? = null
)

data class PrimaryTypeDisplayName(

	@field:SerializedName("text")
	val text: String? = null,

	@field:SerializedName("languageCode")
	val languageCode: String? = null
)

data class Date(

	@field:SerializedName("month")
	val month: Int? = null,

	@field:SerializedName("year")
	val year: Int? = null,

	@field:SerializedName("day")
	val day: Int? = null
)

data class PlacesItem(

	@field:SerializedName("displayName")
	val displayName: DisplayName? = null,

	@field:SerializedName("rating")
	val rating: Any? = null,

	@field:SerializedName("primaryTypeDisplayName")
	val primaryTypeDisplayName: PrimaryTypeDisplayName? = null,

	@field:SerializedName("photos")
	val photos: List<PhotoItem?>? = null,

	@field:SerializedName("websiteUri")
	val websiteUri: String? = null,

	@field:SerializedName("iconMaskBaseUri")
	val iconMaskBaseUri: String? = null,

	@field:SerializedName("googleMapsUri")
	val googleMapsUri: String? = null,

	@field:SerializedName("formattedAddress")
	val formattedAddress: String? = null,

	@field:SerializedName("reviews")
	val reviews: List<Reviews?>? = null,

	@field:SerializedName("paymentOptions")
	val paymentOptions: PaymentOptions? = null,

	@field:SerializedName("primaryType")
	val primaryType: String? = null,

	@field:SerializedName("addressComponents")
	val addressComponents: List<AddressComponentsItem?>? = null,

	@field:SerializedName("userRatingCount")
	val userRatingCount: Int? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("nationalPhoneNumber")
	val nationalPhoneNumber: String? = null,

	@field:SerializedName("editorialSummary")
	val editorialSummary: EditorialSummary? = null,

	@field:SerializedName("utcOffsetMinutes")
	val utcOffsetMinutes: Int? = null,

	@field:SerializedName("shortFormattedAddress")
	val shortFormattedAddress: String? = null,

	@field:SerializedName("types")
	val types: List<String?>? = null,

	@field:SerializedName("plusCode")
	val plusCode: PlusCode? = null,

	@field:SerializedName("businessStatus")
	val businessStatus: String? = null,

	@field:SerializedName("goodForChildren")
	val goodForChildren: Boolean? = null,

	@field:SerializedName("adrFormatAddress")
	val adrFormatAddress: String? = null,

	@field:SerializedName("viewport")
	val viewport: Viewport? = null,

	@field:SerializedName("iconBackgroundColor")
	val iconBackgroundColor: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("location")
	val location: Location? = null,

	@field:SerializedName("internationalPhoneNumber")
	val internationalPhoneNumber: String? = null,

	@field:SerializedName("regularOpeningHours")
	val regularOpeningHours: RegularOpeningHours? = null,

	@field:SerializedName("currentOpeningHours")
	val currentOpeningHours: CurrentOpeningHours? = null,

	@field:SerializedName("accessibilityOptions")
	val accessibilityOptions: AccessibilityOptions? = null
)

data class Reviews(

	@field:SerializedName("originalText")
	val originalText: OriginalText? = null,

	@field:SerializedName("publishTime")
	val publishTime: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("rating")
	val rating: Int? = null,

	@field:SerializedName("relativePublishTimeDescription")
	val relativePublishTimeDescription: String? = null,

	@field:SerializedName("text")
	val text: Text? = null,

	@field:SerializedName("authorAttribution")
	val authorAttribution: AuthorAttribution? = null
)

data class PhotoItem(

	@field:SerializedName("authorAttributions")
	val authorAttributions: List<AuthorAttributionsItem?>? = null,

	@field:SerializedName("widthPx")
	val widthPx: Int? = null,

	@field:SerializedName("heightPx")
	val heightPx: Int? = null,

	@field:SerializedName("name")
	val name: String? = null
)

data class PeriodsItem(

	@field:SerializedName("open")
	val open: Open? = null,

	@field:SerializedName("close")
	val close: Close? = null
)

data class PlusCode(

	@field:SerializedName("globalCode")
	val globalCode: String? = null,

	@field:SerializedName("compoundCode")
	val compoundCode: String? = null
)

data class OriginalText(

	@field:SerializedName("text")
	val text: String? = null,

	@field:SerializedName("languageCode")
	val languageCode: String? = null
)

data class AuthorAttribution(

	@field:SerializedName("displayName")
	val displayName: String? = null,

	@field:SerializedName("photoUri")
	val photoUri: String? = null,

	@field:SerializedName("uri")
	val uri: String? = null
)

data class Close(

	@field:SerializedName("date")
	val date: Date? = null,

	@field:SerializedName("hour")
	val hour: Int? = null,

	@field:SerializedName("truncated")
	val truncated: Boolean? = null,

	@field:SerializedName("day")
	val day: Int? = null,

	@field:SerializedName("minute")
	val minute: Int? = null
)

data class AccessibilityOptions(

	@field:SerializedName("wheelchairAccessibleEntrance")
	val wheelchairAccessibleEntrance: Boolean? = null,

	@field:SerializedName("wheelchairAccessibleParking")
	val wheelchairAccessibleParking: Boolean? = null
)

data class Location(

	@field:SerializedName("latitude")
	val latitude: Any? = null,

	@field:SerializedName("longitude")
	val longitude: Any? = null
)

data class RegularOpeningHours(

	@field:SerializedName("openNow")
	val openNow: Boolean? = null,

	@field:SerializedName("periods")
	val periods: List<PeriodsItem?>? = null,

	@field:SerializedName("weekdayDescriptions")
	val weekdayDescriptions: List<String?>? = null
)

data class CurrentOpeningHours(

	@field:SerializedName("openNow")
	val openNow: Boolean? = null,

	@field:SerializedName("periods")
	val periods: List<PeriodsItem?>? = null,

	@field:SerializedName("weekdayDescriptions")
	val weekdayDescriptions: List<String?>? = null
)

data class AddressComponentsItem(

	@field:SerializedName("types")
	val types: List<String?>? = null,

	@field:SerializedName("longText")
	val longText: String? = null,

	@field:SerializedName("shortText")
	val shortText: String? = null,

	@field:SerializedName("languageCode")
	val languageCode: String? = null
)

data class Low(

	@field:SerializedName("latitude")
	val latitude: Any? = null,

	@field:SerializedName("longitude")
	val longitude: Any? = null
)
