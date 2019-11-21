package com.yeaile.crypt2dart

import android.annotation.TargetApi
import android.os.Build
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar
import java.util.*
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import javax.crypto.Cipher


class Crypt2dartPlugin : MethodCallHandler {
    @JvmField
    val NONCE_LENGTH_IN_BYTES = 12
    @JvmField
    val CHARSET = Charsets.UTF_8

    companion object {
        @JvmStatic
        fun registerWith(registrar: Registrar) {
            val channel = MethodChannel(registrar.messenger(), "crypt2dart")
            channel.setMethodCallHandler(Crypt2dartPlugin())
        }
    }

    override fun onMethodCall(call: MethodCall, result: Result) {
        if (call.method == "getPlatformVersion") {
            result.success("Android ${android.os.Build.VERSION.RELEASE}")
        } else if (call.method == "encrypt") {

            return encrypt(call, result)

        } else if (call.method == "decrypt") {

            return decrypt(call, result)

        } else {
            result.notImplemented()
        }
    }

    // jiami
    @TargetApi(Build.VERSION_CODES.O)
    fun encrypt(call: MethodCall, result: Result) {
        val data = call.argument<String>("data")
        val key = call.argument<String>("key")
        val iv = call.argument<String>("iv")

        if (data == null || key == null || iv == null) {
            result.error(
                    "ERROR_INVALID_PARAMETER_TYPE",
                    "the parameters data, key and iv must be all strings",
                    null
            )
            return
        }
        val dataArray = data.toByteArray(CHARSET)
        val keyArray = key.toByteArray(CHARSET)
        val ivArray = iv.toByteArray(CHARSET)

        val cipher = Cipher.getInstance("AES/CBC/NoPadding")
        val keySpec = SecretKeySpec(keyArray, "AES")
        val ivv = IvParameterSpec(keyArray)
        cipher.init(1, keySpec, ivv)
        val text = Base64.getEncoder().encodeToString(cipher.doFinal(Pkcs7Encoder.encode(dataArray)))
        result.success(text)
        return

    }

    // 解密
    @TargetApi(Build.VERSION_CODES.O)
    fun decrypt(call: MethodCall, result: Result) {
        val data = call.argument<String>("data")
        val key = call.argument<String>("key")
        val iv = call.argument<String>("iv")
        if (data == null || key == null|| iv == null) {
            result.error(
                    "ERROR_INVALID_PARAMETER_TYPE",
                    "the parameters data, key and nonce must be all strings",
                    null
            )
            return
        }

        val dataArray = data.toByteArray(CHARSET)
        val keyArray = key.toByteArray(CHARSET)

        val cipher = Cipher.getInstance("AES/CBC/NoPadding")
        val keySpec = SecretKeySpec(keyArray, "AES")
        val ivv = IvParameterSpec(keyArray)
        cipher.init(2, keySpec, ivv)
        val text = Base64.getEncoder().encodeToString(cipher.doFinal(Pkcs7Encoder.encode(dataArray)))
        result.success(text)
        return

    }

    internal class Pkcs7Encoder {
        companion object {
            var BLOCK_SIZE = 32

            fun encode(src: ByteArray): ByteArray {
                val count = src.size
                var amountToPad = BLOCK_SIZE - count % BLOCK_SIZE
                if (amountToPad == 0) {
                    amountToPad = BLOCK_SIZE
                }

                val pad = (amountToPad and 255).toByte()
                val pads = ByteArray(amountToPad)

                var length: Int
                length = 0
                while (length < amountToPad) {
                    pads[length] = pad
                    ++length
                }

                length = count + amountToPad
                val dest = ByteArray(length)
                System.arraycopy(src, 0, dest, 0, count)
                System.arraycopy(pads, 0, dest, count, amountToPad)
                return dest
            }

            @TargetApi(Build.VERSION_CODES.GINGERBREAD)
            fun decode(decrypted: ByteArray): ByteArray {
                var pad = decrypted[decrypted.size - 1].toInt()
                if (pad < 1 || pad > BLOCK_SIZE) {
                    pad = 0
                }

                return if (pad > 0) Arrays.copyOfRange(decrypted, 0, decrypted.size - pad) else decrypted
            }
        }
    }
}
