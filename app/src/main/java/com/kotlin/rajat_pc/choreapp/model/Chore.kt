package com.kotlin.rajat_pc.choreapp.model

import java.text.DateFormat
import java.util.*

/**
 * Created by RAJAT-PC on 26-07-2017.
 */
class Chore() {
    var choreName: String? = null
    var assignedBy: String? = null
    var assignedTo: String? = null
    var timeAssigned: Long? = null
    var id: Int? = null

//    init {
//        this.choreName = choreName
//        this.assignedBy = assignedBy
//        this.assignedTo = assignedTo
//        this.timeAssigned = timeAssigned
//        this.id = id
//    }

    constructor(choreName: String, assignedBy: String,
                assignedTo: String, timeAssigned: Long,
                id: Int): this() {

        this.choreName = choreName
        this.assignedBy = assignedBy
        this.assignedTo = assignedTo
        this.timeAssigned = timeAssigned
        this.id = id

    }

    fun showHumanDate(timeAssigned: Long): String {

        var dateFormat: java.text.DateFormat = DateFormat.getDateInstance()
        var formattedDate: String = dateFormat.format(Date(timeAssigned).time)

        return "Created: ${formattedDate}"

    }

    override fun toString(): String {
        return "Chore(choreName=$choreName, assignedBy=$assignedBy, assignedTo=$assignedTo, timeAssigned=$timeAssigned, id=$id)"
    }

}