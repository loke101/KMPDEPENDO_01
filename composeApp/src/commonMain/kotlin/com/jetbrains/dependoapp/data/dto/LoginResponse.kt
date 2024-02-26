import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val user: User?=null,
    val otp: Otp? = null,
    @SerialName("fbs_token") val fbsToken: String? = null,
    @SerialName("app_version_validate") val appVersionValidate: Boolean? = null,
    @SerialName("status_code") val statusCode: String? = null,
    val error: String? = null,
    val message: String? = null,
    val event: String? = null,
    @SerialName("emp_dob") val empDob: String?=null,
    @SerialName("emp_type") val empType: String?=null
) {
    @Serializable
    data class User(
        val id: String? = null,
        @SerialName("emp_name") val empName: String? = null,
        @SerialName("mobile_no") val mobileNo: String? = null,
        @SerialName("fbs_token") val fbsToken: String? = null,
        val password: String? = null,
        @SerialName("sip_pass") val sipPass: String? = null,
        @SerialName("sip_calling") val sipCalling: String? = null,
        @SerialName("sip_id") val sipId: String? = null
    )

    @Serializable
    data class Otp(
        val otp: String? = null
    )
}