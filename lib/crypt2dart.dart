import 'dart:async';

import 'package:flutter/services.dart';

class Crypt2dart {
  static const MethodChannel _channel = const MethodChannel('crypt2dart');

  static Future<String> encrypt(String plainText, String key, String iv) async =>
      await _channel.invokeMethod("encrypt", {
        "data": plainText,
        "key": key,
        "iv": iv,
      });


  static Future<String> decrypt(String plainText, String key, String iv) async =>
      await _channel.invokeMethod("decrypt", {
        "data": plainText,
        "key": key,
        "iv": iv,
      });
}
