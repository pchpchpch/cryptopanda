package com.pchelina.cryptopanda

import org.json.JSONObject

/**
 * Created by ruinvepc on 11/30/2017.
 */
class Transaction {
    var from: String = "";
    var to: String = "";
    var amount: Double = 0.0;

    constructor( json:String) {
        var jsonObject: JSONObject = JSONObject(json)
        from = jsonObject.getString("from")
        to = jsonObject.getString("to")
        amount = jsonObject.getDouble("amount")
    }

    constructor(from: String, to: String, amount: Double) {
        this.from = from
        this.to = to
        this.amount = amount
    }


    fun toJson(): String {
        return toJsonObject().toString()
    }

    fun toJsonObject(): JSONObject {
        var jsonObject = JSONObject();
        jsonObject.put("from", from)
        jsonObject.put("to", to)
        jsonObject.put("amount", amount)
        return jsonObject;
    }


}