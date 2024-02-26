package com.jetbrains.dependoapp.data.api

object ApiUtils {
    const val BASEURL = "https://logistics.dependo.com/"
    const val JSON = "json"
    const val DEVICE_TYPE: Int = 1
    const val SHIPMENT_TYPE_KEY = "shipmentType"
    const val AIRBILL_NO_KEY = "shipmentType"
    const val IS_FROM_BRANCH = "fromBranch"
    const val congeeseName: String = "name"
    const val phoneNumber: String = "phone"
    const val awbNumber: String = "awbNumber"
    const val TRID: String = "trID"
    const val DELIVERED_SHIPMENT: Int = 1
    const val UNDELIVERED_SHIPMENT: Int = 2
    const val NOT_ATTEMPTED_SHIPMENT: Int = 3
    const val TOTAL_SHIPMENT: Int = 0
    const val ABOUT_US_URL: String = "https://logistics.dependo.com/delivery_app/about_us/"
    const val API_KEY = "628"
    const val API_KEY_NEW = "9067"
    const val API_KEY_Dash = "8527"
    const val VENDOR_NAME = "nykaa"

    const val VENDOR_NAME_DASH = "allvendors"
    const val DELIVERED_REMARKS = "Delivered"
    const val UNDELIVERED_REMARKS = "Undelivered"
    const val RATTING = "0"
    const val ALL_VENDOR = "allvendors"
    const val  SECRET_KEY = "(ycRz=%NANY|gDcSBvqEtzK);t70q_aCH+H!a)YT:c=J7P]fG*2wV`_U0GEAf"

}
object EndPoint {
    const val loginUrl = "index.php/delivery_app/login_v5"
    const val genOtpUrl = "index.php/delivery_app/login_v5/generate_otp"
    const val verifyOTPURL  = "index.php/delivery_app/login_v5/validate_otp"
    const val resendOTP = "index.php/delivery_app/login_v5/resend_otp"
    const val setMpinURL = "index.php/delivery_app/login_v5/set_mpin"
    const val validateMpinURL = "index.php/delivery_app/login_v5/validate_mpin"
    const val resendOTPURL = "index.php/delivery_app/login_v5/resend_otp"
    const val fetchAllShipmentsListURL = "delivery_app/get_shipment_list_v2"
    const val getDashboardDataURL = "delivery_app/get_dashboard_data_v2"
    const val sendCustomerSMSURL = "delivery_app/send_cust_sms_v2"
    const val checkSMSStatusURL = "delivery_app/get_ogcalls_status_v2"
    const val getSMSTemplatesURL = "delivery_app/get_sms_templates_v2"
    const val getShipmentsDetailsURL = "delivery_app/get_shipment_detail_v2"
    const val uploadStartKmURL = "delivery_app/startendkm/startkm_detail"
    const val getStatusListURL = "delivery_app/get_undelivered_reason_v2"
    const val updateStatusListURL = "delivery_app/status_update_v3"
    const val getRelationListURL = "delivery_app/lists/relation_list"
    const val uploadEndKmURL = "delivery_app/startendkm/endkm_detail"
    const val fetchNotificationsListURL = "delivery_app/notification/notification_list"
    const val countNotificationURL = "delivery_app/notification/notification_count"
    const val deleteNotificationURL = "delivery_app/notification/notification_delete"
    const val readNotificationURL = "delivery_app/notification/notification_read"
    const val getProofListURL = "delivery_app/lists/proof_list"
    const val checkPaymentStatusURL = "delivery_app/list/check_payment_status"
    const val getMissedCallListURL = "delivery_app/get_missedcalls_list_v2"
    const val getCodCollectedListURL = "delivery_app/cod_collected_list_v2"
    const val sendMoneyServiceURL = "delivery_app/cod_collected_init_v2"
    const val getCodNotCollected_tdURL = "delivery_app/get_codnotcollected_td_v2"
    const val getTransactionDetailURL = "delivery_app/get_transaction_detail_v2"
    const val getBannersURL = "delivery_app/get_banners_v2"


}
