package com.wannacry.tngassessment.domain.data

data class User(
    val id: Int,
    val name: String?,
    val username: String?,
    val email: String?,
    val address: Address?,
    val phone: String?,
    val website: String?,
    val company: Company?
) {
    val companyName: String?
        get() = company?.name
    val fullAddress: String?
        get() = "${address?.suite}, ${address?.street}, ${address?.city}, ${address?.zipcode}"

    val location: String?
        get() = "${address?.geo?.lat}, ${address?.geo?.lng}"
}

data class Company(
    val name: String?,
    val catchPhrase: String?,
    val bs: String?
)

data class Address(
    val street: String?,
    val suite: String?,
    val city: String?,
    val zipcode: String?,
    val geo: Geo?
)

data class Geo(
    val lat: String?,
    val lng: String?
)
