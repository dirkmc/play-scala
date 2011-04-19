package models

import java.util.Date

class ScalaCat(var name:String, var kittenCount:Long, var isNeutered:Boolean, ignored:String) {
    val created:Date = new Date
}