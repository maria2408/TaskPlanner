package com.thirdlection.taskplanner.database

class Task {
    var id: Int = 0
    var name: String = ""
    var desc: String = ""
    var deadlineDate: String = ""
    var deadlineTime: String = ""
    var durStartDate: String = ""
    var durEndDate: String = ""
    var durStartTime: String = ""
    var durEndTime: String = ""
    var importance: Int = 0

    constructor (
        name: String,
        desc: String,
        dDate: String,
        dTime: String,
        durSDate: String,
        durEDate: String,
        durSTime: String,
        durETime: String,
        imp: Int
    ) {
        this.name = name
        this.desc = desc
        this.deadlineDate = dDate
        this.deadlineTime = dTime
        this.durStartDate = durSDate
        this.durEndDate = durEDate
        this.durStartTime = durSTime
        this.durEndTime = durETime
        this.importance = imp
    }
    constructor(
        id: Int,
        name: String,
        desc: String,
        dDate: String,
        dTime: String
    ) {
        this.id = id
        this.name = name
        this.desc = desc
        this.deadlineDate = dDate
        this.deadlineTime = dTime
    }
}
