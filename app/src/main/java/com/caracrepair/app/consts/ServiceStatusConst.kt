package com.caracrepair.app.consts

object ServiceStatusConst {
    private const val STATUS_PENDING = "pending"
    private const val STATUS_PICKUP = "pickup"
    private const val STATUS_CHECKING = "checking"
    private const val STATUS_CHECKING_CONFIRMATION = "checking-confirmation"
    private const val STATUS_IN_PROGRESS = "inprogress"
    private const val STATUS_WAITING_PAYMENT = "waiting-payment"
    private const val STATUS_WAITING_PAYMENT_CONFIRMATION = "waiting-payment-confirmation"
    private const val STATUS_DELIVERY = "delivery"
    private const val STATUS_COMPLETE = "complete"
    private const val STATUS_RESCHEDULE = "reschedule"

    fun isShowFee(status: String): Boolean {
        return status == STATUS_WAITING_PAYMENT
    }

    fun isShowStaticDescription(status: String): Boolean {
        return status != STATUS_CHECKING_CONFIRMATION
    }

    fun getDescription(status: String, serviceType: String): String {
        return when (status) {
            STATUS_PENDING -> StringConst.ServiceStatusDescription.DESC_PENDING
            STATUS_PICKUP -> if (serviceType == ServiceTypeConst.TYPE_PICKUP) {
                StringConst.ServiceStatusDescription.DESC_MECHANIC_PICKUP
            } else {
                StringConst.ServiceStatusDescription.DESC_SELF_DELIVER
            }
            STATUS_CHECKING -> StringConst.ServiceStatusDescription.DESC_CHECKING
            STATUS_IN_PROGRESS -> StringConst.ServiceStatusDescription.DESC_IN_PROGRESS
            STATUS_DELIVERY -> if (serviceType == ServiceTypeConst.TYPE_PICKUP) {
                StringConst.ServiceStatusDescription.DESC_MECHANIC_DELIVERY
            } else {
                StringConst.ServiceStatusDescription.DESC_READY_TO_PICKUP
            }
            STATUS_WAITING_PAYMENT -> StringConst.ServiceStatusDescription.DESC_WAITING_PAYMENT
            STATUS_WAITING_PAYMENT_CONFIRMATION -> StringConst.ServiceStatusDescription.DESC_WAITING_PAYMENT_CONFIRMATION
            STATUS_COMPLETE -> StringConst.ServiceStatusDescription.DESC_COMPLETE
            STATUS_RESCHEDULE -> StringConst.ServiceStatusDescription.DESC_RESCHEDULE
            else -> ""
        }
    }
}