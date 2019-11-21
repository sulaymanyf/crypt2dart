import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:crypt2dart/crypt2dart.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _plainText = 'Unknown';
  String _encryptedString = '';
  String _decryptedString = '';


  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String encryptedString;
    String plainText = '我是shyandsy，never give up man';
    String key = 'xxxxxxxxxxxxxxxx';
    String iv = 'yyyyyyyyyyyyyyyy';
    String decryptedString;


  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Text('Running on:'),
        ),
      ),
    );
  }
}
