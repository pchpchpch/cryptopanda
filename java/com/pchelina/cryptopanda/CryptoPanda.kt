package com.pchelina.cryptopanda

import org.json.JSONArray
import java.security.MessageDigest
import java.util.*

class CryptoPanda {

    var myPubKey:String;
    val REWARD: Double = 1.0;
    var transactions = LinkedList<Transaction>();
    var pandaChain = LinkedList<Block>();
    constructor() {
        myPubKey = getHash("myid")
        generateInitialBlock();
    }

    private fun generateInitialBlock() {
        var emptyTransaction = Transaction("network", myPubKey, 0.0)
        transactions.add(emptyTransaction)
        val initialBlock = Block(0, "", currentTransactionsToJson());
        println("First block created " + initialBlock)
        pandaChain.add(initialBlock);
    }

    private fun currentTransactionsToJson(): String {
        var jsonArray: JSONArray = JSONArray()
        for ( transaction in transactions) {
            jsonArray.put(transaction.toJsonObject())
        }
        return jsonArray.toString()
    }

    fun mine() {
        var prevBlock: Block = pandaChain.last();
        var newBlock = createNextBlock(prevBlock, currentTransactionsToJson())
        // block for proof of work creation
        val proof: Int = proofOfWork(newBlock, prevBlock)
        if (proof > 0) {
            newBlock.proofOfWork = proof;
            transactions.add(Transaction("network", myPubKey, REWARD))
            newBlock.updateData(currentTransactionsToJson())
            // proper block. Add block and broadcast update
            pandaChain.add(newBlock)
        }
    }


    private fun createNextBlock(prevBlock: Block, data: String): Block {
        return Block(prevBlock.index + 1, prevBlock.hash, data)
    }


    private fun proofOfWork (newBlock: Block, prevBlock: Block): Int {
        val message: String = newBlock.index.toString() + newBlock.timestamp.toString() + newBlock.data + prevBlock.hash;
        var nonce = 0;
        while (nonce < 3000) {
            if (getHash(message+nonce.toString()).startsWith("0"))
            {
                println("Found proof of work at " + (nonce ));
                return nonce;
            }
            nonce++;
        }
        println("Couldn't get prooof of work after 2000 iterations. Give up")
        return -1;
    }

    fun getHash(message: String): String {
        val HEX_DIGITS = "0123456789ABCDEF"
        val hash = MessageDigest.getInstance("SHA-256").digest(message.toByteArray());
        val result = StringBuilder(hash.size * 2)
        hash.forEach {
            val i = it.toInt()
            result.append(HEX_DIGITS[i shr 4 and 0x0f])
            result.append(HEX_DIGITS[i and 0x0f])
        }
        return result.toString();
    }
}