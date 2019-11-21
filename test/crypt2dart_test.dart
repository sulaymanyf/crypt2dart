import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:crypt2dart/crypt2dart.dart';

void main() {
  const MethodChannel channel = MethodChannel('crypt2dart');

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await Crypt2dart.platformVersion, '42');
  });
}
