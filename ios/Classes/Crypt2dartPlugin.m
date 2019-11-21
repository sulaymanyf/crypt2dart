#import "Crypt2dartPlugin.h"
#import <crypt2dart/crypt2dart-Swift.h>

@implementation Crypt2dartPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftCrypt2dartPlugin registerWithRegistrar:registrar];
}
@end
