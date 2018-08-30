package com.pchelina.cryptopanda

import java.security.MessageDigest

class Block constructor(lIndex:  Int, lPrevHash: String, lData: String) {
    val index: Int = lIndex;
    val timestamp: Long = System.currentTimeMillis();
    var data: String? = lData;
    private val prevHash: String = lPrevHash;
    val HEX_DIGITS = "0123456789ABCDEF"
    var hash:String = calculateHash();
    var proofOfWork: Int = 0;




    fun calculateHash() : String {
        var message: String = index.toString() + timestamp.toString() + data + prevHash;
        val hash = MessageDigest.getInstance("SHA-256").digest(message.toByteArray());
        val result = StringBuilder(hash.size * 2)
        hash.forEach {
            val i = it.toInt()
            result.append(HEX_DIGITS[i shr 4 and 0x0f])
            result.append(HEX_DIGITS[i and 0x0f])
        }
        return result.toString();
    }

    fun updateData(data: String) {
        this.data = data
        hash = calculateHash()
    }


    override fun toString(): String {
        val builder = StringBuffer("Index: ");
        builder.append(index).append(", timeStamp: ").append(timestamp).append(", data: ").append(data).append(", hash: ").append(hash);
        return builder.toString()
    }

}