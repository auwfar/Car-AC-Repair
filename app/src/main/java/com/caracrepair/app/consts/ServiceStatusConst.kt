package com.caracrepair.app.consts

object ServiceStatusConst {
    const val STATUS_PENDING = "pending"
    const val STATUS_PICKUP = "pickup"
    const val STATUS_CHECKING = "cheking"
    const val STATUS_CHECKING_CONFIRMATION = "cheking-confirmation"
    const val STATUS_IN_PROGRESS = "inprogress"
    const val STATUS_DELIVERY = "delivery"
    const val STATUS_WAITING_PAYMENT = "waiting-payment"
    const val STATUS_COMPLETE = "complete"

    fun getDescription(status: String, serviceType: String): String {
        return when (status) {
            STATUS_PENDING -> StringConst.ServiceStatusDescription.DESC_PENDING
            STATUS_PICKUP -> if (serviceType == ServiceTypeConst.TYPE_PICKUP) {
                StringConst.ServiceStatusDescription.DESC_MECHANIC_PICKUP
            } else {
                StringConst.ServiceStatusDescription.DESC_SELF_DELIVER
            }
            STATUS_CHECKING -> StringConst.ServiceStatusDescription.DESC_CHECKING
            STATUS_CHECKING_CONFIRMATION -> StringConst.ServiceStatusDescription.DESC_CHECKING
            STATUS_IN_PROGRESS -> StringConst.ServiceStatusDescription.DESC_IN_PROGRESS
            STATUS_DELIVERY -> if (serviceType == ServiceTypeConst.TYPE_PICKUP) {
                StringConst.ServiceStatusDescription.DESC_MECHANIC_DELIVERY
            } else {
                StringConst.ServiceStatusDescription.DESC_READY_TO_PICKUP
            }
            STATUS_WAITING_PAYMENT -> StringConst.ServiceStatusDescription.DESC_WAITING_PAYMENT_CONFIRMATION
            STATUS_COMPLETE -> StringConst.ServiceStatusDescription.DESC_COMPLETE
            else -> ""
        }
    }
}